package com.het.open.lib.callback;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  <br>
 * 作者: 80010814 4<br>
 * 日期: 2017/6/15<br>
 **/


public interface IWifiDeviceData {
    /**
     * 获取控制数据
     * @param jsonData
     */
    void onGetConfigData(String jsonData);

    /**
     * 获取运行数据
     * @param jsonData
     */
    void onGetRunData(String jsonData);

    /**
     * 获取异常数据
     * @param jsonData
     */
    void onGetErrorData(String jsonData);

    /**
     * 设备状态
     * @param onlineStatus 在线状态（1-正常，2-异常不在线）
     */
    void onDeviceStatues(int onlineStatus);

    /**
     * 收取数据异常
     * @param code 错误码
     * @param msg  错误信息
     *  1000 无法连接服务器
     *  1001 设备不在线
     */
    void onDataError(int code, String msg);
}
