package com.het.open.lib.api;

import com.het.open.lib.biz.deal.DeviceShareDeal;
import com.het.open.lib.callback.IHetCallback;


/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  设备分享api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetDeviceShareApi {
    private static HetDeviceShareApi mInstance;

    private HetDeviceShareApi() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetDeviceShareApi getInstance() {
        if (mInstance == null) {
            synchronized (HetDeviceShareApi.class) {
                mInstance = new HetDeviceShareApi();
            }
        }
        return mInstance;
    }

    /**
     * 1.请求手机账号所有设备的授权
     *
     * @param iHetCallback the het callback
     * @param phone        the phone
     * @param authOpenId   the auth open id
     * @param openId       the open id
     */
    public void getAllAuthDevice(final IHetCallback iHetCallback, String phone, String authOpenId, String openId) {
        DeviceShareDeal.getAllAuthDevice(iHetCallback, phone, authOpenId, openId);
    }

    /**
     * 2、设备授权邀请
     *
     * @param iHetCallback the het callback
     * @param deviceId     the device id
     * @param account      the account
     */
    public void deviceInvite(final IHetCallback iHetCallback, String deviceId, String account) {
        DeviceShareDeal.deviceInvite(iHetCallback, deviceId, account);
    }

    /**
     * 3、多用户设备授权
     *
     * @param iHetCallback the het callback
     * @param deviceId     the device id
     * @param friendIds    the friend ids
     */
    public void userAuth(final IHetCallback iHetCallback, String deviceId, String friendIds) {
        DeviceShareDeal.userAuth(iHetCallback, deviceId, friendIds);
    }

    /**
     * 4、设备授权删除
     *
     * @param iHetCallback the het callback
     * @param deviceId     the device id
     * @param userId       the user id
     */
    public void deviceDel(final IHetCallback iHetCallback, String deviceId, String userId) {
        DeviceShareDeal.deviceDel(iHetCallback, deviceId, userId);
    }

    /**
     * 5、设备授权同意
     *
     * @param iHetCallback the het callback
     * @param deviceId     the device id
     */
    public void deviceAgree(final IHetCallback iHetCallback, String deviceId) {
        DeviceShareDeal.deviceAgree(iHetCallback, deviceId);
    }

    /**
     * 6、获取设备授权的用户
     *
     * @param iHetCallback the het callback
     * @param deviceId     the device id
     */
    public void getDeviceAuthUser(final IHetCallback iHetCallback, String deviceId) {
        DeviceShareDeal.getDeviceAuthUser(iHetCallback, deviceId);
    }

    /**
     * 7、多设备授权邀请
     *
     * @param iHetCallback the het callback
     * @param deviceIds    the device ids
     * @param account      the account
     */
    public void multiInvite(IHetCallback iHetCallback, String deviceIds, String account) {
        DeviceShareDeal.multiInvite(iHetCallback, deviceIds, account);
    }

    /**
     * 8、获取已分享出去的设备列表信息
     *
     * @param iHetCallback the het callback
     */
    public void getAuthDevice(IHetCallback iHetCallback) {
        DeviceShareDeal.getAuthDevice(iHetCallback);
    }

    /**
     * 9、获取APP上关联设备授权的所有用户
     *
     * @param iHetCallback the het callback
     */
    public void getAppDeviceAuthUser(IHetCallback iHetCallback) {
        DeviceShareDeal.getAppDeviceAuthUser(iHetCallback);
    }

    /**
     * 10、获取分享码
     *
     * @param iHetCallback the het callback
     * @param deviceId     the device id
     * @param shareType    the share type
     */
    public void getShareCode(IHetCallback iHetCallback, String deviceId, String shareType) {
        DeviceShareDeal.getShareCode(iHetCallback, deviceId, shareType);
    }

    /**
     * 11、根据分享码获取产品信息
     *
     * @param iHetCallback the het callback
     * @param shareCode    the share code
     */
    public void getShareDevice(IHetCallback iHetCallback, String shareCode) {
        DeviceShareDeal.getShareDevice(iHetCallback, shareCode);
    }

    /**
     * 12、授权分享
     *
     * @param iHetCallback the het callback
     * @param shareCode    the share code
     * @param shareType    the share type
     */
    public void authShareDevice(IHetCallback iHetCallback, String shareCode, String shareType) {
        DeviceShareDeal.authShareDevice(iHetCallback, shareCode, shareType);
    }
}
