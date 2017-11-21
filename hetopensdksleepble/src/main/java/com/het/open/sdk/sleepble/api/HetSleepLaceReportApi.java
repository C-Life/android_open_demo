package com.het.open.sdk.sleepble.api;


import com.het.open.lib.callback.IHetCallback;
import com.het.open.sdk.sleepble.biz.csleep.SleepLaceReportDeal;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  睡眠带子报告api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetSleepLaceReportApi {
    private static HetSleepLaceReportApi mInstance;

    private HetSleepLaceReportApi() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetSleepLaceReportApi getInstance() {
        if (mInstance == null) {
            synchronized (HetSleepLaceReportApi.class) {
                if (mInstance == null) mInstance = new HetSleepLaceReportApi();
            }
        }
        return mInstance;
    }


    /**
     * 获取睡眠带子天报告详细
     *
     * @param callback the callback
     * @param deviceId the device id
     * @param dataTime 数据时间（例如：2016-12-21）
     * @param timeZone 时区分钟数（默认：480）
     */
    public void getSummaryDayData(final IHetCallback callback,String deviceId,String dataTime,int timeZone) {
        SleepLaceReportDeal.getSummaryDayData(callback,deviceId,dataTime,timeZone);

    }

    /**
     * 获取睡眠带子时间段内的统计报告
     *
     * @param callback  the callback
     * @param deviceId  the device id
     * @param startDate 起始时间，查询结果包含该时间（例如：2016-12-21）
     * @param endDate   结束时间，查询结果包含该时间（例如：2016-12-22）
     */
    public  void getDayDataList(final IHetCallback callback, String deviceId,String startDate,String endDate) {
        SleepLaceReportDeal.getDayDataList(callback,deviceId,startDate,endDate);

    }

    /**
     * 获取带子电池电量值
     *
     * @param callback the callback
     * @param deviceId the device id
     */
    public void getBatteryPower(final IHetCallback callback, String deviceId) {
        SleepLaceReportDeal.getBatteryPower(callback,deviceId);

    }


}
