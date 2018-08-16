package com.het.sdk.demo.utils;

import com.het.bind.logic.bean.device.DeviceProductBean;
import com.het.sdk.demo.model.QrCodeModel;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * 作者: liuzh<br>
 * 版本: 2.0<br>
 * 日期: 2018-01-16<br>
 * 描述:
 **/
public class MacIMEIBindHelper {
    public static final int BIND_TYPE_IMEI = 4;

    public static final int BIND_TYPE_MAC = 6;

    public static final int BIND_TYPE_MZigBee = 8;

    private MacIMEIBindHelper() {
        //no instance
    }

    private static class SingleInstance {
        private static final MacIMEIBindHelper INSTANCE = new MacIMEIBindHelper();
    }

    public static MacIMEIBindHelper getInstance() {
        return SingleInstance.INSTANCE;
    }

    public boolean isBindTypeMac(int type) {
        return type == BIND_TYPE_MAC;
    }

    public boolean isBindTypeIMEI(int type) {
        return type == BIND_TYPE_IMEI;
    }

    public boolean isBindTypeMacOrIMEI(int type) {
        return type == BIND_TYPE_MAC || type == BIND_TYPE_IMEI || type == BIND_TYPE_MZigBee;
    }

    private OnQrScanListener mOnQrScanListener;

    public boolean hasQrScanListener() {
        return mOnQrScanListener != null;
    }

    public OnQrScanListener getOnQrScanListener() {
        return mOnQrScanListener;
    }

    public void setOnQrScanListener(OnQrScanListener onQrScanListener) {
        mOnQrScanListener = onQrScanListener;
    }

    public interface OnQrScanListener {
        /**
         * 判断是否是Mac地址绑定或者是IMEI码绑定的设备
         *
         * @param bean        设备信息
         * @param qrCodeModel 扫描到的数据
         * @return
         */
        boolean isBindMacImei(DeviceProductBean bean, QrCodeModel qrCodeModel);
    }

    private OnBindListener mOnBindListener;

    public boolean hasBindListener() {
        return mOnBindListener != null;
    }

    public OnBindListener getOnBindListener() {
        return mOnBindListener;
    }

    public void setOnBindListener(OnBindListener onBindListener) {
        mOnBindListener = onBindListener;
    }

    public interface OnBindListener {
        /**
         * 设备绑定成功的回调
         */
        void bindSuccess(String deviceBean);
    }
}
