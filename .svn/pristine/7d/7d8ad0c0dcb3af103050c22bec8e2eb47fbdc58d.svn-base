package com.het.open.lib.api;

import android.text.TextUtils;

import com.het.open.lib.biz.deal.DeviceManagerDeal;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.DeviceModel;


/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  设备管理api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetDeviceManagerApi {

    private static HetDeviceManagerApi mInstance;

    private HetDeviceManagerApi() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetDeviceManagerApi getInstance() {
        if (mInstance == null) {
            synchronized (HetDeviceManagerApi.class) {
                mInstance = new HetDeviceManagerApi();
            }
        }
        return mInstance;
    }

    /**
     * 更新设备信息
     *
     * @param deviceModel  设备model
     * @param iHetCallback 回调
     * @param deviceName   设备名称
     */
    public void update(DeviceModel deviceModel, final IHetCallback iHetCallback, String deviceName) {
        String deviceId=deviceModel.getDeviceId();
        if (TextUtils.isEmpty(deviceId)){
            throw new IllegalArgumentException("deviceId is null");
        }
        DeviceManagerDeal.update(iHetCallback,deviceId,deviceName);

    }

    /**
     * 解绑设备
     *
     * @param deviceModel  the device model
     * @param iHetCallback the het callback
     */
    public void unBind(DeviceModel deviceModel, final IHetCallback iHetCallback) {
        String deviceId=deviceModel.getDeviceId();
        if (TextUtils.isEmpty(deviceId)){
            throw new IllegalArgumentException("deviceId is null");
        }
        DeviceManagerDeal.unbind(iHetCallback,deviceId);

    }
}
