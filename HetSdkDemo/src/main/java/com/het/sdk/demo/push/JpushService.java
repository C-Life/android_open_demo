package com.het.sdk.demo.push;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.het.basic.utils.ACache;
import com.het.basic.utils.SharePreferencesUtil;
import com.het.basic.utils.StringUtils;
import com.het.log.Logc;
import com.het.sdk.demo.utils.WeakHandler;

import java.io.Serializable;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * 作者: liuzh<br>
 * 版本: 2.0<br>
 * 日期: 2017-11-13<br>
 * 描述: 极光推送 服务
 **/
public class JpushService implements BasePushService {
    private String TAG = "JPushManager";
    private static final String KEY = "JpushConfig";
    private Context context;

    public JpushService(Context context) {
        this.context = context;
    }

    /**
     * 初始化极光，一般可以放到程序的启动Activity或是Application的onCreate方法中调用
     */
    @Override
    public void initPush() {
        JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
        JPushInterface.init(context); // 初始化 JPush
    }

    @Override
    public void stopPush() {
        setAliasAndTags("null");//通过清空别名来停止极光
        SharePreferencesUtil.removeKey(context, KEY);
    }

    private SetAliasAndTagsCallBack setAliasAndTagsCallBack;

    public interface SetAliasAndTagsCallBack {
        void setSuccess();

        void setFailure();
    }

    public void setAliasAndTags(final String alias, SetAliasAndTagsCallBack aliasAndTagsCallBack) {
        setAliasAndTagsCallBack = aliasAndTagsCallBack;
        if (TextUtils.isEmpty(alias)) {
            return;
        }
        // 调用 Handler 来异步设置别名
        AliasAndTagsInfo aliasAndTagsInfo = new AliasAndTagsInfo();
        aliasAndTagsInfo.setAlias(alias);
        aliasAndTagsInfo.setTag(null);
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS,
                aliasAndTagsInfo));
    }

    @Override
    public void bindCloudService(String userId) {
        if (userId == null) return;
        setAliasAndTags(userId, new SetAliasAndTagsCallBack() {
            @Override
            public void setSuccess() {
                //绑定到云端
                String registrationId = "";
                registrationId = ACache.get(context)
                        .getAsString(JPushInterface.EXTRA_REGISTRATION_ID);
                if (StringUtils.isNull(registrationId)) {
                    registrationId = JPushInterface.getRegistrationID(context);
                }
                Logc.e(TAG,"bind registrationId : " + registrationId);
//                接口未迁移  先注释
//                PushApi.getInstance().bindPush(registrationId).subscribe(objectApiResult -> {
//                    //推送绑定成功
//                }, throwable -> {
//                    throwable.printStackTrace();
//                });
            }

            @Override
            public void setFailure() {

            }
        });
    }

    /**
     * 极光推送解绑
     */
    @Override
    public void pushUnBindService() {
        stopPush();
        //                接口未迁移  先注释
//        PushApi.getInstance().unBindPush().subscribe(s -> {
//        }, e -> {
//            e.printStackTrace();
//        });
    }

    /**
     * 设置AliasAndTag,设置多组tag,如果不需要设置tag的化，直接将此参数设为null;
     * 一般在程序登录成功，注册成功等地方调用。别名一般是用户的唯一标识，如userId等
     *
     * @param alias 签名
     * @param tags  标签
     */
    public void setAliasAndTags(final String alias, Set<String> tags) {
        if (TextUtils.isEmpty(alias)) {
            return;
        }
        // 调用 Handler 来异步设置别名
        // TODO Auto-generated method stub
        AliasAndTagsInfo aliasAndTagsInfo = new AliasAndTagsInfo();
        aliasAndTagsInfo.setAlias(alias);
        aliasAndTagsInfo.setTag(tags);
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS,
                aliasAndTagsInfo));
    }

    /**
     * 设置AliasAndTag,设置一组tag,如果不需要设置tag的化，直接将此参数设为null;
     *
     * @param alias 别名
     */
    public void setAliasAndTags(final String alias) {
        if (TextUtils.isEmpty(alias)) {
            return;
        }
        // 调用 Handler 来异步设置别名
        // TODO Auto-generated method stub
        AliasAndTagsInfo aliasAndTagsInfo = new AliasAndTagsInfo();
        aliasAndTagsInfo.setAlias(alias);
        aliasAndTagsInfo.setTag(null);
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS,
                aliasAndTagsInfo));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Logc.d(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    if (!alias.equals("null")) {
                        saveAlias(alias);
                    }
                    if (setAliasAndTagsCallBack != null) {
                        setAliasAndTagsCallBack.setSuccess();
                    }
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Logc.d(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    AliasAndTagsInfo aliasAndTagsInfo = new AliasAndTagsInfo();
                    aliasAndTagsInfo.setAlias(alias);
                    aliasAndTagsInfo.setTag(tags);
                    mHandler.sendMessageDelayed(
                            mHandler.obtainMessage(MSG_SET_ALIAS, aliasAndTagsInfo), 1000 * 60);
                    if (setAliasAndTagsCallBack != null) {
                        setAliasAndTagsCallBack.setFailure();
                    }
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Logc.d(TAG, logs);
            }
        }
    };

    /**
     * 保存别名到属性文件。
     *
     * @param alias 别名
     */
    private void saveAlias(String alias) {
        SharePreferencesUtil.putString(context, KEY, alias);
    }

    /**
     * 从属性文件取得别名
     */
    private String getAlias() {
        return SharePreferencesUtil.getString(context, KEY);
    }

    private static final int MSG_SET_ALIAS = 1001;

    private WeakHandler mHandler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            AliasAndTagsInfo aliasAndTagsInfo = (AliasAndTagsInfo) msg.obj;
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Logc.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置c别名。
                    if (getAlias() == null || aliasAndTagsInfo.getAlias().equals("null")) {
                        JPushInterface.setAliasAndTags(context,
                                aliasAndTagsInfo.getAlias(), aliasAndTagsInfo.getTag(),
                                mAliasCallback);
                    }
                    break;
                default:
                    Logc.d(TAG, "Unhandled msg - " + msg.what);
            }
            return true;
        }
    });


    public static class AliasAndTagsInfo implements Serializable {
        private static final long serialVersionUID = 1L;
        private String alias;
        private Set<String> tag;

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public Set<String> getTag() {
            return tag;
        }

        public void setTag(Set<String> tag) {
            this.tag = tag;
        }
    }


}
