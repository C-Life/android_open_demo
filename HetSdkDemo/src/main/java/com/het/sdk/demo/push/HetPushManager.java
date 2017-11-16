package com.het.sdk.demo.push;

import android.content.Context;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * 作者: liuzh<br>
 * 版本: 2.0<br>
 * 日期: 2017-11-13<br>
 * 描述: 推送管理 百度推送和极光推送2种方案
 *
 **/
public class HetPushManager {
    private static Context context;
    private BasePushService basePushService;

    public HetPushManager() {

    }

    public static HetPushManager getInstance() {
        return HetPushManagerSingleHolder.hetPushManagersingle;
    }

    private static class HetPushManagerSingleHolder {
        private static final HetPushManager hetPushManagersingle = new HetPushManager();
    }

    /**
     * 初始化 推送服务
     */
    public void init(BasePushService basePushService) {
        this.basePushService = basePushService;
        if (basePushService != null) {
            basePushService.initPush();
        }
    }

    /**
     * 绑定推送服务 userId
     */
    public void startPushService(String userId) {
        if (basePushService != null) {
            basePushService.bindCloudService(userId);
        }
    }

    /**
     * 解绑推送服务
     */
    public void unbindPushService() {
        if (basePushService != null) {
            basePushService.pushUnBindService();
        }
    }

    public void stopPush() {
        if (basePushService != null) {
            basePushService.stopPush();
        }
    }

}
