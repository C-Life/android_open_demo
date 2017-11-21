package com.het.open.lib.biz.init;

import android.app.Application;
import android.content.Context;

import com.het.basic.AppDelegate;
import com.het.basic.base.RxManage;
import com.het.basic.constact.AppGlobalHost;
import com.het.basic.data.api.token.TokenManager;
import com.het.basic.data.http.okhttp.OkHttpManager;
import com.het.basic.data.http.retrofit2.RetrofitManager;
import com.het.basic.utils.SharePreferencesUtil;
import com.het.bind.logic.HeTBindApi;
import com.het.log.Logc;
import com.het.mqtt.sdk.manager.HetMqttManager;
import com.het.open.lib.api.HetCodeConstants;
import com.het.open.lib.auth.HetAuthMess;
import com.het.open.lib.auth.LogUserInfoManager;
import com.het.open.lib.biz.constans.GlobalAddr;
import com.het.open.lib.biz.deal.H5ControlPlugDeal;
import com.het.open.lib.biz.deal.path.OpenCommonPathDeal;
import com.het.open.lib.model.ConfigModel;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  sdk初始化<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 **/
public class SdkManager {

    private Context mContext;
    private static SdkManager mInstance;
    public static final String appSign = "com.het.household.common";  //sdk名称

    private SdkManager() {
    }

    public static SdkManager getInstance() {
        if (mInstance == null) {
            synchronized (SdkManager.class) {
                if (mInstance == null) {
                    mInstance = new SdkManager();
                }

            }
        }
        return mInstance;
    }


    /**
     * 初始化SDK
     *
     * @param application 上下文常量
     * @param appId       用户appid
     * @param secret      用户密匙
     * @param configModel 初始配置
     */
    public void init(Application application, String appId,
                     String secret, ConfigModel configModel) {
        this.mContext = application.getApplicationContext();
        AppDelegate.init(application);
        HeTBindApi.getInstance().init();
        HetAuthMess.getInstance().setAppId(appId);
        HetAuthMess.getInstance().setAppKey(secret);
        OkHttpManager.setConnectTimeout(10 * 1000);
        OkHttpManager.setReadTimeout(10 * 1000);
        OkHttpManager.setWriteTimeout(10 * 1000);
        AppDelegate.setAppId(appId);
        AppDelegate.setAppSecret(secret);
        AppDelegate.initActiveAndroid();
  //      AppDelegate.isOpenPlatform(true);
        if (configModel.isLog()) {
            Logc.DEBUG=true;
        }else {
            Logc.DEBUG=false;
        }
        if (configModel.isSupportH5Plug()){
            //H5库的初始化  设备控制是h5开发的时候使用
            H5ControlPlugDeal.init(mContext,appSign);
        }
        setHostAddr(configModel.getHost());
        setH5Config(configModel.getH5UIconfig());
        OpenCommonPathDeal.dealPath();
        HetMqttManager.getInstances().init(mContext);
    }


    public void setH5Config(String h5UIconfig) {
        SharePreferencesUtil.putString(AppDelegate.getAppContext(), "configurationFiles", h5UIconfig);
    }

    public String getH5Config() {
        return SharePreferencesUtil.getString(AppDelegate.getAppContext(), "configurationFiles");
    }


    /**
     * 设置请求host
     *
     * @param type the type
     */
    public void setHostAddr(int type) {
        switch (type) {
            case HetCodeConstants.TYPE_LOCAL_HOST:
                GlobalAddr.HOST = GlobalAddr.LOCAl_HOST;
                HeTBindApi.getInstance().setHostType(2);
                break;
            case HetCodeConstants.TYPE_PREVIEW_HOST:
                GlobalAddr.HOST = GlobalAddr.PREVIEW_HOST;
                HeTBindApi.getInstance().setHostType(1);
                break;
            case HetCodeConstants.TYPE_PRODUCE_HOST:
                GlobalAddr.HOST = GlobalAddr.PRODUCE_HOST;
                HeTBindApi.getInstance().setHostType(0);
                break;
            case HetCodeConstants.TYPE_INHOT_HOST:
                GlobalAddr.HOST = GlobalAddr.INHOST;
                HeTBindApi.getInstance().setHostType(2);
                break;
        }
        AppDelegate.setHost(GlobalAddr.HOST);
        if (RetrofitManager.getBuilder() != null) {
            RetrofitManager.getBuilder().baseUrl(AppGlobalHost.getHost()).build();
        }
    }

    /**
     * 获取全局常量
     *
     * @return application application
     */
    public Context getApplication() {
        return mContext;
    }


    /**
     *
     */
    public void destroy() {
        HetMqttManager.getInstances().destroy();
    }

    /**
     * 是否已授权登录
     *
     * @return boolean boolean
     */
    public boolean isAuthLogin() {
        return TokenManager.getInstance().isLogin();
    }

    /**
     * 退出登录
     */
    public void logout() {
        if (isAuthLogin()) {
            TokenManager.getInstance().clearAuth();
            LogUserInfoManager.getInstance().clearLoginUser();
            RxManage.getInstance().post(HetCodeConstants.Login.LOGINOUT_SUCCESS, "");
        }

    }

}
