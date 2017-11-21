package com.het.open.lib.api;

import android.support.annotation.NonNull;

import com.het.open.lib.biz.deal.DeviceControlDeal;
import com.het.open.lib.callback.IHetCallback;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称: wifi设备控制api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/10/28. 16:42<br>
 */
public class HetDeviceWifiControlApi {

    private static HetDeviceWifiControlApi mInstance;


    private HetDeviceWifiControlApi() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetDeviceWifiControlApi getInstance() {
        if (mInstance == null) {
            synchronized (HetDeviceWifiControlApi.class) {
                mInstance = new HetDeviceWifiControlApi();
            }
        }
        return mInstance;
    }

    /**
     * 向设备下发控制数据
     *
     * @param iHetCallback 调用成功的回调监听
     * @param deviceId     设备ID
     * @param json         设备控制数据
     */
    public void setDataToDevice(final IHetCallback iHetCallback, String deviceId, String json) {
        DeviceControlDeal.setData(iHetCallback,deviceId,json);
    }

    /**
     * 获取设备运行数据
     *
     * @param iHetCallback 调用成功的回调监听
     * @param deviceId     设备ID
     */
    public void getRunFromDevice(final IHetCallback iHetCallback, String deviceId) {
        DeviceControlDeal.getRunData(iHetCallback,deviceId);
    }

    /**
     * 获取设备控制数据
     *
     * @param iHetCallback 调用成功的回调监听
     * @param deviceId     设备ID
     */
    public void getConfigFromDevice(final IHetCallback iHetCallback, @NonNull  String deviceId) {
        DeviceControlDeal.getConfigData(iHetCallback,deviceId);
    }

    /**
     * 获取设备异常数据
     *
     * @param iHetCallback 调用成功的回调监听
     * @param deviceId     设备ID
     */
    public void getErrorDataFromDevice(final IHetCallback iHetCallback, String deviceId) {
        DeviceControlDeal.getErrorData(iHetCallback,deviceId);
    }


}
