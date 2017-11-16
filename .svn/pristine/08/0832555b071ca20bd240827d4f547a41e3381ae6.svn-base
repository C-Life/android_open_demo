package com.het.open.lib.api;

import android.text.TextUtils;

import com.het.open.lib.biz.deal.DeviceRomUpgradeDeal;
import com.het.open.lib.callback.IHetCallback;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  设备固件升级api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetDeviceRomUpgradeApi {
    private static HetDeviceRomUpgradeApi mInstance;

    private HetDeviceRomUpgradeApi() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetDeviceRomUpgradeApi getInstance() {
        if (mInstance == null) {
            synchronized (HetDeviceRomUpgradeApi.class) {
                mInstance = new HetDeviceRomUpgradeApi();
            }
        }
        return mInstance;
    }

    /**
     * 检查设备固件版本升级信息
     *
     * @param iCallback 回调
     * @param deviceId  设备id
     */
    public  void check(final IHetCallback iCallback,String deviceId) {
        if (TextUtils.isEmpty(deviceId)){
            throw new IllegalArgumentException("deviceId is null");
        }
        DeviceRomUpgradeDeal.check(iCallback,deviceId);

    }

    /**
     * 确认固件版本升级
     *
     * @param iCallback         回调
     * @param deviceId          设备标识
     * @param deviceVersionType 设备版本类型（1-PCB，2-WiFi）
     * @param deviceVersionId   设备固件版本id
     */
    public  void confirm(final IHetCallback iCallback,String deviceId,String deviceVersionType, String deviceVersionId) {
        if (TextUtils.isEmpty(deviceId)){
            throw new IllegalArgumentException("deviceId is null");
        }
        DeviceRomUpgradeDeal.confirmUpgrade(iCallback,deviceId,deviceVersionType,deviceVersionId);

    }

    /**
     * 确认固件版本升级完成
     *
     * @param iCallback       回调
     * @param deviceId        设备id
     * @param deviceVersionId 设备固件版本id
     */
    public  void confirmSucUpgrade(final IHetCallback iCallback,String deviceId, String deviceVersionId) {
        if (TextUtils.isEmpty(deviceId)){
            throw new IllegalArgumentException("deviceId is null");
        }
        DeviceRomUpgradeDeal.confirmSuccess(iCallback,deviceId,deviceVersionId);


    }

    /**
     * 获取固件版本升级进度
     *
     * @param iCallback       回调
     * @param deviceId        设备id
     * @param deviceVersionId 设备固件版本id
     */
    public  void getUpgradeProgress(final IHetCallback iCallback,String deviceId,String deviceVersionId) {
        if (TextUtils.isEmpty(deviceId)){
            throw new IllegalArgumentException("deviceId is null");
        }
        DeviceRomUpgradeDeal.getUpgradeProgres(iCallback,deviceId,deviceVersionId);

    }





}
