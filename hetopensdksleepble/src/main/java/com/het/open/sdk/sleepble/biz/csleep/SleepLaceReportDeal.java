package com.het.open.sdk.sleepble.biz.csleep;

import com.het.open.lib.biz.deal.HttpDeal;
import com.het.open.lib.callback.IHetCallback;

import java.util.TreeMap;


/**
 * 睡眠智慧盒子api
 * Created by xuchao on 2016/6/1.
 */
public class SleepLaceReportDeal {

    /**
     *获取睡眠带子天报告详细
     * @param callback
     * @param deviceId
     * @param dataTime 数据时间（例如：2016-12-21）
     * @param timeZone 时区分钟数（默认：480）
     */
    public static void getSummaryDayData(IHetCallback callback, String deviceId, String dataTime, int timeZone) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("deviceId", deviceId);
        params.put("dataTime", dataTime);
        params.put("timeZone", timeZone+"");
        String path = "v1/csleep/dreamu/getSummaryDayData";;
        HttpDeal.hetGet(path,params,callback);
    }

    /**
     * 获取睡眠带子时间段内的统计报告
     * @param callback
     * @param deviceId
     * @param startDate 起始时间，查询结果包含该时间（例如：2016-12-21）
     * @param endDate   结束时间，查询结果包含该时间（例如：2016-12-22）
     */
    public static void getDayDataList(IHetCallback callback, String deviceId, String startDate, String endDate) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("deviceId", deviceId);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        String path =  "v1/csleep/dreamu/getDayDataList";;
        HttpDeal.hetPost(path,params,callback);
    }

    /**
     * 获取带子电池电量值
     * @param callback
     * @param deviceId
     */
    public static void getBatteryPower(IHetCallback callback, String deviceId) {
       TreeMap<String, String> params = new TreeMap<String, String>();
       params.put("deviceId", deviceId);
        String path =  "v1/csleep/dreamu/getBatteryPower";;
        HttpDeal.hetPost(path,params,callback);
    }


}
