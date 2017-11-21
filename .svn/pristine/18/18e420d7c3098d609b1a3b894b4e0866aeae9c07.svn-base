package com.het.open.lib.api;

import android.text.TextUtils;

import com.het.open.lib.biz.deal.DeviceWifiControlDeal;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.callback.IWifiDeviceData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>和而泰wifi设备控制api：</p>
 * 名称:  授权api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetWifiDeviceControlApi {

    private static HetWifiDeviceControlApi mInstance;

    private HetWifiDeviceControlApi(){
    }


    /**
     * Get instance het wifi device contro api.
     *
     * @return the het wifi device contro api
     */
    public static HetWifiDeviceControlApi getInstance(){
        if (mInstance==null){
            synchronized (HetWifiDeviceControlApi.class){
                mInstance=new HetWifiDeviceControlApi();
            }
        }
        return  mInstance;
    }

//    /**
//     * 初始化
//     *
//     * @param mContext        the m context
//     * @param iWifiDeviceData the wifi device data
//     */
//    public void init(Context mContext, IWifiDeviceData iWifiDeviceData){
//       DeviceWifiControlDeal.getInstance().init(mContext,iWifiDeviceData);
//    }

    /**
     * 开始接收数据
     *
     * @param deviceId 设备id
     */
    public void start(final String deviceId,IWifiDeviceData iWifiDeviceData){
        if (TextUtils.isEmpty(deviceId)){
            throw new IllegalArgumentException("deviceId is null");
        }
        DeviceWifiControlDeal.getInstance().start(deviceId,iWifiDeviceData);
    }


    /**
     * 停止接收数据
     */
    public void stop(final String deviceId){
        DeviceWifiControlDeal.getInstance().stop(deviceId);
    }

    /**
     * 获取异常数据
     *
     * @param callback 回调
     * @param deviceId 设备id
     * @param json     下发数据
     */
    public void sendDataToDevice(final IHetCallback callback, String deviceId, String json) {
        if (TextUtils.isEmpty(deviceId)){
            throw new IllegalArgumentException("deviceId is null");
        }
        if (TextUtils.isEmpty(json)){
            throw new IllegalArgumentException("json is null");
        }
        if (json.contains("updateTime")){
            JSONObject jsonObject= null;
            try {
                jsonObject = new JSONObject(json);
                if (jsonObject.has("updateTime")){
                    jsonObject.remove("updateTime");
                }
                json = jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        DeviceWifiControlDeal.getInstance().sendDataToDevice(callback,deviceId,json);
    }






}
