package com.het.open.lib.api;

import android.app.Application;
import android.support.annotation.NonNull;

import com.het.open.lib.biz.init.SdkManager;
import com.het.open.lib.model.ConfigModel;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>sdk初始化管理：</p>
 * 名称:  sdk初始化<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetSdk {

    private final String TAG = HetSdk.class.getSimpleName();
    private static HetSdk mInstance;

    private HetSdk() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetSdk getInstance() {
        if (mInstance == null) {
            synchronized (HetSdk.class) {
                mInstance = new HetSdk();

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
    public void init(@NonNull Application application, @NonNull String appId,
                     @NonNull String secret, @NonNull ConfigModel configModel) {
        SdkManager.getInstance().init(application, appId, secret, configModel);
    }


    /**
     * 关闭小循环服务
     */
    public void destroy() {
        SdkManager.getInstance().destroy();
    }

    /**
     * 是否已授权登录
     *
     * @return boolean boolean
     */
    public boolean isAuthLogin() {
        return SdkManager.getInstance().isAuthLogin();
    }

    /**
     * 获取 h5登录界面的配置数据
     *
     * @return h 5 u iconfig
     */
    public String getH5UIconfig() {
        return SdkManager.getInstance().getH5Config();
    }

    /**
     * 退出登录
     */
    public void logout() {
        SdkManager.getInstance().logout();

    }


}
