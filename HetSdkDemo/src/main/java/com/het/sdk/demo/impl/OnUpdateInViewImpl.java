package com.het.sdk.demo.impl;

import com.het.basic.base.RxManage;
import com.het.h5.sdk.manager.HtmlFiveManager;
import com.het.open.lib.callback.OnUpdateInView;
import com.het.sdk.demo.event.DeviceStatusEvent;

/**
 * Created by: Administrator.
 * Created time: 2016-12-14.
 * Desc:
 */
public class OnUpdateInViewImpl extends OnUpdateInView {
    private HtmlFiveManager mHtmlFiveManager;

    public OnUpdateInViewImpl(HtmlFiveManager htmlFiveManager) {
        this.mHtmlFiveManager = htmlFiveManager;
    }

    @Override
    protected void onUpdateConfig(String s) {
        if (mHtmlFiveManager != null && s != null) {
            mHtmlFiveManager.updateConfigData(s);
        }
    }

    @Override
    protected void onUpdateRun(String s) {
        if (mHtmlFiveManager != null && s != null) {
            mHtmlFiveManager.updateRunData(s);
        }
    }

    @Override
    protected void onUpdateWarm(String s) {
        if (mHtmlFiveManager != null && s != null) {
            mHtmlFiveManager.updateErrorData(s);
        }
    }

    @Override
    protected void onDeviceOffline() {
        RxManage.getInstance().post(DeviceStatusEvent.DEVICE_STATUS, DeviceStatusEvent.DEVICE_OFFLINE);
    }

    @Override
    protected void onDeviceOnline() {
        RxManage.getInstance().post(DeviceStatusEvent.DEVICE_STATUS, DeviceStatusEvent.DEVICE_ONLINE);
    }

    @Override
    protected void onDeviceError(String s) {
        RxManage.getInstance().post(DeviceStatusEvent.DEVICE_NOT_EXIST, 0);
    }

}
