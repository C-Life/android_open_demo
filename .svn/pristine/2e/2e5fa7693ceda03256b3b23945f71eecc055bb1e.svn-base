package com.het.open.sdk.sleepble.biz.csleep;

import com.het.open.lib.biz.deal.HttpDeal;
import com.het.open.lib.callback.IHetCallback;
import java.io.InputStream;
import java.util.TreeMap;

/**
 * 蓝牙控制相关api
 */
public class SleepBleDeal {

    /**
     * 获取蓝牙设备历史数据
     * @param callback
     * @param deviceId
     * @param startTime
     * @param endTime
     */
    public static void listByDay(IHetCallback callback, String deviceId, String startTime, String endTime) {
        TreeMap params = new TreeMap();
        params.put("deviceId", deviceId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        String path =  "v1/device/data/day/list";;
        HttpDeal.hetClifePost(path,params,callback);
    }

    /**
     * 获取蓝牙设备每日报告
     * @param callback
     * @param deviceId
     * @param count
     * @param date
     */
    public static void listDetailByDay(IHetCallback callback, String deviceId, String count, String date) {
        TreeMap params = new TreeMap();
        params.put("deviceId", deviceId);
        params.put("count", count);
        params.put("date", date);
        String path =  "v1/device/data/day/detail/list";;
        HttpDeal.hetClifePost(path,params,callback);
    }




    /**
     * 上传蓝牙数据
     * @param callback
     * @param deviceId
     * @param input
     * @param fileName
     * @param version
     */
    public static void updateRun(IHetCallback callback, String deviceId, InputStream input, String fileName, String version) {
//        TreeMap params = new TreeMap();
//        params.put("protocolVersion", version);
//        params.put("deviceId", deviceId);
//        String url=GlobalAddr.getClifeUrl(GlobalAddr.HOST);
//        (new HetNetworkBuilder(new HetMultipartNetwork("data", input, fileName))).
//                setId(-1).
//                setParams(params).
//                setCallBack(callback).
//                setUrl(url + "v1/device/data/upload").
//                commit();
    }



}
