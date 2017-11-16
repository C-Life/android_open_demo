package com.het.open.lib.api;

import android.app.Activity;

import com.het.open.lib.biz.bind.HetWifiBind;
import com.het.open.lib.callback.IWifiBind;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  wifi设备绑定api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetWifiBindApi {

    private static HetWifiBindApi mInstance;


    private HetWifiBindApi() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetWifiBindApi getInstance() {
        if (mInstance == null) {
            synchronized (HetWifiBindApi.class) {
                mInstance = new HetWifiBindApi();
            }
        }
        return mInstance;
    }


//    /**
//     * 开始绑定设备
//     *
//     * @param wifiPassword wifi密码
//     * @param deviceModel  绑定的设备model
//     * @param iWifiBind    绑定接口回调
//     */
//    public void startBind(String wifiPassword, DeviceModel deviceModel, IWifiBind iWifiBind) {
//        HetWifiBind.getInstance().startBind(wifiPassword,deviceModel, iWifiBind);
//
//    }
//
//    /**
//     * 开始绑定设备
//     *
//     * @param wifiPassword wifi密码
//     * @param deviceModel  绑定的设备model
//     * @param iWifiBind    绑定接口回调
//     */
//    public void startBind(Activity activity, String wifiPassword, DeviceModel deviceModel, IWifiBind iWifiBind) {
//        HetWifiBind.getInstance().startBind(activity,wifiPassword,deviceModel, iWifiBind);
//
//    }

    /**
     * 开始绑定设备
     *
     * @param activity     the activity
     * @param ssid         the ssid
     * @param wifiPassword wifi密码
     * @param productId    the product id
     * @param iWifiBind    绑定接口回调
     */
    public void startBind(Activity activity,String ssid,String wifiPassword, String productId, IWifiBind iWifiBind) {
            HetWifiBind.getInstance().startBind(activity,ssid,wifiPassword,productId, iWifiBind);
        }

//    /**
//     * 开始绑定设备
//     *
//     * @param wifiPassword wifi密码
//     * @param productId    the product id
//     * @param iWifiBind    绑定接口回调
//     */
//    public void startBind(String wifiPassword, String productId, IWifiBind iWifiBind) {
//        HetWifiBind.getInstance().startBind(wifiPassword,productId, iWifiBind);
//    }

//    /**
//     * Start bind.
//     *
//     * @param wifiPassword  the wifi password
//     * @param productId     the product id
//     * @param radiocastName the radiocast name
//     * @param iWifiBind     the wifi bind
//     */
//    public void startBind(String wifiPassword, String productId, String radiocastName,IWifiBind iWifiBind) {
//       // HetWifiBind.getInstance().startBind(wifiPassword,productId, radiocastName,iWifiBind);
//    }

    /**
     * 结束扫描绑定
     */
    public void stopBind() {
        HetWifiBind.getInstance().stopBind();
    }





}
