package com.het.open.lib.callback;


import android.os.Handler;
import android.os.Looper;

import com.het.log.Logc;


/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：更新设备控制页面</p>
 * 名称: OnUpdateInView <br>
 * 作者: uuxia<br>
 * 版本: 1.0<br>
 * 日期: 2016/8/5 15:56<br>
 **/
public abstract class OnUpdateInView {

    /**
     * 主线程句柄
     **/
    private Handler mDelivery = new Handler(Looper.getMainLooper());

    private boolean bStopUpdateView = false;

    public void setUpdateViewStatus(boolean bUpdateView) {
        this.bStopUpdateView = bUpdateView;
    }

    public void updateConfig(final String config) {
        if (bStopUpdateView)
            return;
        mDelivery.post(() -> onUpdateConfig(config));
    }

    public void updateRun(final String run) {
        if (bStopUpdateView)
            return;
        mDelivery.post(() -> onUpdateRun(run));
    }

    public void updateWarm(final String warm) {
        if (bStopUpdateView)
            return;
        mDelivery.post(() -> onUpdateWarm(warm));
    }

    public void onNoThisDeviceError(final String error) {
        mDelivery.post(() -> onDeviceError(error));
    }

    public void updateDeviceStatus(final int status) {
        Logc.w(" uu. updateDeviceStatus:" + status);
        if (status == 2) {
            mDelivery.post(() -> {
                Logc.w(" 大循环离线 updateDeviceStatus:" + status);
                onDeviceOffline();
            });
        } else if (status == 1){
            mDelivery.post(() -> onDeviceOnline());
        }
    }

    /**
     * 控制数据回调UI
     **/
    protected abstract void onUpdateConfig(String config);

    /**
     * 运行数据回调UI
     **/
    protected abstract void onUpdateRun(String run);

    /**
     * 警报数据回调UI
     **/
    protected abstract void onUpdateWarm(String warm);

    /**
     * 设备在线状态 在线状态（1-正常，2-异常, 3-小循环在线（局域网））
     **/
    protected abstract void onDeviceOffline();

    protected abstract void onDeviceOnline();

    /**
     * 设备不存在
     *
     * @param msg
     */
    protected abstract void onDeviceError(String msg);
}
