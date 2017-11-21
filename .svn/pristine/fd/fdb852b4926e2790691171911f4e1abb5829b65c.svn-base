package com.het.open.sdk.sleepble.callback;

import com.het.open.lib.model.DeviceModel;


/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  蓝牙设备绑定接口回调<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public interface IBleBind {

    /**
     * 绑定状态
     *
     * @param devices 扫描到设备(多个)
     * @param msg      扫描绑定状态描述
     */
    void onScanDevices(String devices, String msg);

    /**
     * 绑定失败
     *
     * @param errId 绑定失败代码
     * @param msg   绑定失败描述
     */
     void onFailed(int errId, String msg);

    /**
     * 绑定成功
     *
     * @param deviceModel 绑定成功的设备model
     */
    void onSuccess(DeviceModel deviceModel);


}
