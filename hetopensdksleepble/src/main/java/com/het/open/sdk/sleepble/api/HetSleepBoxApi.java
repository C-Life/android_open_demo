package com.het.open.sdk.sleepble.api;


import com.het.open.lib.callback.IHetCallback;
import com.het.open.sdk.sleepble.biz.csleep.SleepBoxDeal;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  睡眠盒子相关api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetSleepBoxApi {
    private static HetSleepBoxApi mInstance;

    private HetSleepBoxApi() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetSleepBoxApi getInstance() {
        if (mInstance == null) {
            synchronized (HetSleepBoxApi.class) {
                if (mInstance == null) mInstance = new HetSleepBoxApi();
            }
        }
        return mInstance;
    }


    /**
     * 修改某设备下单个的mac状态（打开或关闭）
     *
     * @param callback 调用成功的回调监听
     * @param deviceId the device id
     * @param mac      the mac
     * @param status   the status
     */
    public  void updateMacstatus(final IHetCallback callback,String deviceId, String mac, String status) {
        SleepBoxDeal.updateMacstatus(callback,deviceId,mac,status);

    }

    /**
     * 根据设备ID获取蓝牙列表
     *
     * @param callback 调用成功的回调监听
     * @param deviceId 设备id
     */
    public  void getLinkmacList(final IHetCallback callback, String deviceId) {
        SleepBoxDeal.getLinkmacList(callback,deviceId);

    }


}
