package com.het.sdk.demo.push;

import android.content.Context;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.android.pushservice.PushSettings;
import com.het.log.Logc;
import com.het.sdk.demo.utils.SDKAppUtil;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * 作者: liuzh<br>
 * 版本: 2.0<br>
 * 日期: 2017-11-13<br>
 * 描述: 百度推送 服务
 **/
public class BdPushService implements BasePushService {

    private Context context;

    public BdPushService(Context context) {
        this.context = context;
    }


    @Override
    public void initPush() {
        PushManager.startWork(context, PushConstants.LOGIN_TYPE_API_KEY, SDKAppUtil.getMetaValue(context, "api_key"));
        PushSettings.enableDebugMode(context, true);
    }

    @Override
    public void stopPush() {

    }

    @Override
    public void bindCloudService(String userId) {
        //bind 到 云端  自行实现
        Logc.e(" Baidu_ChannelId = " + BaiduPushReceiver.Baidu_ChannelId);
    }

    @Override
    public void pushUnBindService() {
        PushManager.stopWork(context);
    }
}
