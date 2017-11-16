package com.het.open.sdk.sleepble.api;

import com.het.open.lib.callback.ICallback;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.utils.HetCodeConstants;
import com.het.open.sdk.sleepble.biz.bledevice.control.BizControlBiz;
import com.het.open.sdk.sleepble.biz.csleep.SleepBleDeal;


/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称: 睡眠蓝牙牙设备控制api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetSleepBleControlApi {
    private final String TAG = "蓝牙设备控制api";
    private static HetSleepBleControlApi mInstance;


    private HetSleepBleControlApi() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetSleepBleControlApi getInstance() {
        if (mInstance == null) {
            synchronized (HetSleepBleControlApi.class) {
                if (mInstance==null)mInstance = new HetSleepBleControlApi();
            }
        }
        return mInstance;
    }

    /**
     * 同步蓝牙数据
     *
     * @param callback    回调
     * @param deviceModel 设备model
     */
    public void syncData(final IHetCallback callback,DeviceModel deviceModel) {
        BizControlBiz.getInstance().syncData(new ICallback<String>() {
            @Override
            public void onSuccess(String s, int id) {
                callback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS, s);
            }

            @Override
            public void onFailure(int code, String msg, int id) {
                callback.onFailed(code, msg);
            }
        },deviceModel);


    }

    /**
     * 获取蓝牙实时数据
     *
     * @param iCallback   接口回调
     * @param deviceModel 设备model
     */
    public void getRealData(final IHetCallback iCallback,DeviceModel deviceModel ) {
        BizControlBiz.getInstance().getRealData(new ICallback<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes, int id) {
                String data = new String(bytes);
                iCallback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS, data);
            }

            @Override
            public void onFailure(int code, String msg, int id) {
                iCallback.onFailed(code, msg);
            }
        },deviceModel);


    }


    /**
     * 获取蓝牙设备历史数据
     *
     * @param iCallback   the callback
     * @param deviceModel the device model
     * @param startTime   the start time
     * @param endTime     the end time
     */
    public void getHistroyData(final IHetCallback iCallback, DeviceModel deviceModel, String startTime, String endTime) {
        String deviceId = deviceModel.getDeviceId();
        SleepBleDeal.listByDay(iCallback,deviceId,startTime,endTime);

    }

    /**
     * 获取蓝牙设备每日报告
     *
     * @param deviceModel the device model
     * @param iCallback   the callback
     * @param count       the count
     * @param data        the data
     */
    public void getDetailData(DeviceModel deviceModel, final IHetCallback iCallback, String count, String data) {
        String deviceId = deviceModel.getDeviceId();
        SleepBleDeal.listDetailByDay(iCallback,deviceId,count,data);

    }


}
