package com.het.open.sdk.sleepble.api;

import com.het.open.lib.biz.init.SdkManager;
import com.het.open.lib.model.DeviceModel;
import com.het.open.sdk.sleepble.biz.bledevice.bind.BleBiz;
import com.het.open.sdk.sleepble.callback.IBleBind;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  蓝牙设备绑定api（带子版本）<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetBleBindApi {
    private final String TAG = "蓝牙设备绑定api";
    private static HetBleBindApi mInstance;


    private HetBleBindApi() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetBleBindApi getInstance() {
        if (mInstance == null) {
            synchronized (HetBleBindApi.class) {
                if (mInstance==null) mInstance = new HetBleBindApi();
            }
        }
        return mInstance;
    }

    /**
     * 初始化蓝牙api
     *
     * @return 0 成功 1 未开启蓝牙 2该手机不支持蓝牙功能
     */
    public int init() {
        return BleBiz.getInstance().init(SdkManager.getInstance().getApplication());
    }

    /**
     * 开始绑定设备
     *
     * @param deviceModel 绑定的设备类型
     * @param iBleBind    绑定接口回调
     */
    public void startBind(DeviceModel deviceModel, IBleBind iBleBind) {
        BleBiz.getInstance().startBind(deviceModel,iBleBind);


    }


    /**
     * 开始绑定设备
     *
     * @param productId 产品id
     * @param iBleBind  绑定接口回调
     */
    public void startBind(String productId, IBleBind iBleBind) {
        BleBiz.getInstance().startBind(productId,iBleBind);
    }


    /**
     * 连接设备
     *
     * @param deviceModel 连接设备model
     */
    public void connect(DeviceModel deviceModel){
        BleBiz.getInstance().connect(deviceModel.getMacAddress());
    }








}
