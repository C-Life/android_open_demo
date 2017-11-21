package com.het.open.lib.api;

import android.app.Activity;

import com.het.bind.logic.bean.device.DeviceProductBean;
import com.het.open.lib.biz.bind.HetBleBind;
import com.het.open.lib.callback.ICommonBleBind;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  和而泰通用蓝牙设备绑定api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetCommonBleBindApi {
    private final String TAG = "和而泰通用蓝牙设备绑定api";
    private static HetCommonBleBindApi mInstance;


    private HetCommonBleBindApi() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetCommonBleBindApi getInstance() {
        if (mInstance == null) {
            synchronized (HetCommonBleBindApi.class) {
                mInstance = new HetCommonBleBindApi();
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
      //  return BleBiz.getInstance().init(SdkManager.getInstance().getApplication());
        return 0;
    }


    /**
     * 开始绑定设备
     *
     * @param activity  the activity
     * @param productId 产品id
     * @param iBleBind  绑定接口回调
     */
    public void startBind(Activity activity,String productId, ICommonBleBind iBleBind) {
        HetBleBind.getInstance().startBind(activity,productId,iBleBind);
    }


    /**
     * 绑定到服务器
     *
     * @param deviceModel 连接设备model
     */
    public void bindToServer(DeviceProductBean deviceModel){
        HetBleBind.getInstance().doBind(deviceModel);
    }


    /**
     * 释放资源
     *
     */
    public void stopBind(){
        HetBleBind.getInstance().stopBind();
    }






}
