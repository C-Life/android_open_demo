package com.het.sdk.demo.push;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.het.basic.utils.ACache;
import com.het.basic.utils.StringUtils;
import com.het.log.Logc;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * 作者: liuzh<br>
 * 版本: 2.0<br>
 * 日期: 2017-11-13<br>
 * 描述: 极光推送的广播接收器
 **/
public class JPushReceiver extends BroadcastReceiver {

    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Logc.e(TAG, "[JPushReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            ACache.get(context).put(JPushInterface.EXTRA_REGISTRATION_ID, regId);
            Logc.d(TAG, "[JPushReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Logc.d(TAG, "[JPushReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Logc.d(TAG, "[JPushReceiver] 接收到推送下来的通知的ID: " + notifactionId);
        } else {

        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuffer sb = new StringBuffer();
        for (String key : bundle.keySet()) {
            if (TextUtils.equals(key, JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getInt(key));
            } else if (TextUtils.equals(key, JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getBoolean(key));
            } else if (TextUtils.equals(key, JPushInterface.EXTRA_EXTRA)) {
                String xx = bundle.getString(JPushInterface.EXTRA_EXTRA);

                if (StringUtils.isNull(xx)) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:").append(key).append(", value:[").append(myKey).append(" - ").append(json.optString(myKey)).append("]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getString(key));
            }
        }
        return sb.toString();
    }

    /**
     * 自定义消息
     *
     * @param context
     * @param bundle
     */
    private void processCustomMessage(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
        Logc.d(TAG, "[JPushReceiver] 接收到推送下来的自定义消息: extras = " + extras + " ##title = " + title);

    }

    /**
     * 打开应用. 应用在前台不处理,在后台就直接在前台展示当前界面, 未开启则重新启动
     */
    @SuppressWarnings("deprecation")
    private void openApplicationFromBackground(Context context) {
        Intent intent;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        if (!list.isEmpty() && list.get(0).topActivity.getPackageName().equals(context.getPackageName())) {
            //此时应用正在前台, 不作处理
            return;
        }
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(context.getPackageName())) {
                intent = new Intent();
                intent.setComponent(info.topActivity);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                if (!(context instanceof Activity)) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                context.startActivity(intent);
                return;
            }
        }
        intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        context.startActivity(intent);
    }
}