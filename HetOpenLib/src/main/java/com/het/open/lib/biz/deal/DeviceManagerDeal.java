package com.het.open.lib.biz.deal;

import com.het.open.lib.biz.constans.ComParamContant;
import com.het.open.lib.biz.constans.GlobalAddr;
import com.het.open.lib.callback.IHetCallback;

import java.util.TreeMap;


/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  设备列表api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class DeviceManagerDeal {

    /**
     * 获取绑定设备列表
     *
     * @param iCallback the callback
     */
    public static void update(final IHetCallback iCallback, String deviceId, String deviceName) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put(ComParamContant.Device.DEVICE_ID, deviceId);
        params.put(ComParamContant.Device.DEVICE_NAME, deviceName);
        String path = "/v1/device/update";
        String url=GlobalAddr.HOST;
        if (url.contains("dp")||url.contains("50")){
            path = "/v1/app/open/device/update";
        }
        HttpDeal.hetPost(path,params,iCallback);





    }

    /**
     * 获取大类型设备列表
     *
     * @param iCallback the callback
     */
    public static void unbind(final IHetCallback iCallback,String deviceId) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put(ComParamContant.Device.DEVICE_ID, deviceId);
        String path = "/v1/device/unbind";
        String url= GlobalAddr.HOST;
        if (url.contains("dp")||url.contains("50")){
            path="/v1/app/open/device/unbind";
        }
        HttpDeal.hetPost(path,params,iCallback);


    }





}
