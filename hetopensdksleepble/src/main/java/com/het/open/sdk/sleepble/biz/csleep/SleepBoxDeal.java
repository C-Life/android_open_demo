package com.het.open.sdk.sleepble.biz.csleep;


import com.het.open.lib.biz.deal.HttpDeal;
import com.het.open.lib.callback.IHetCallback;

import java.util.TreeMap;

/**
 * 睡眠智慧盒子api
 * Created by xuchao on 2016/6/1.
 */
public class SleepBoxDeal {

    /**
     * 根据设备ID获取蓝牙列表
     * @param callback 调用成功的回调监听
     * @param deviceId 设备id
     */
    public static void getLinkmacList(IHetCallback callback, String deviceId) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("deviceId", deviceId);
        String path = "v1/app/clife/wisdomBox/getLinkmacList";;
        HttpDeal.hetClifeGet(path,params,callback);
    }

    /**
     * 修改某设备下单个的mac状态（打开或关闭）
     *
     * @param callback  调用成功的回调监听
     * @param mac  设备的mac地址
     * @param status  设备的状态（（1-可用，2-禁止，0-删除）
     * @param deviceId  设备的id
     */
    public static void updateMacstatus(IHetCallback callback, String deviceId, String mac, String status) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("deviceId", deviceId);
        params.put("mac", mac);
        params.put("status", status);
        String path =  "v1/app/clife/wisdomBox/updateMacstatus";;
        HttpDeal.hetClifePost(path,params,callback);

    }


}
