package com.het.open.lib.biz.deal;

import android.text.TextUtils;

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
public class DeviceDataHistroyDeal {

    /**
     * 获取设备运行数据列表（七天之内）
     *
     * @param iCallback the callback
     */
    public static void getRunDataList(final IHetCallback iCallback, String deviceId,String startDate,String  endDate,int pageRows,int pageIndex) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("deviceId", deviceId);
        params.put("startDate",startDate);
        if (!TextUtils.isEmpty(endDate)){
            params.put("endDate",endDate);
        }
        params.put("pageRows",pageRows+"");
        params.put("pageIndex",pageIndex+"");
        String path = "/v1/device/data/getRunDataList";;
        HttpDeal.hetPost(path,params,iCallback);



    }

    /**
     * 获取设备配置数据列表（七天之内）
     *
     * @param iCallback the callback
     */
    public static void getConfigDataList(final IHetCallback iCallback, String deviceId,String startDate,String  endDate,int pageRows,int pageIndex) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("deviceId", deviceId);
        params.put("startDate",startDate);
        if (!TextUtils.isEmpty(endDate)){
            params.put("endDate",endDate);
        }
        params.put("pageRows",pageRows+"");
        params.put("pageIndex",pageIndex+"");
        String path = "/v1/device/data/getConfigDataList";;
        HttpDeal.hetPost(path,params,iCallback);



    }


    /**
     * 获取设备异常数据列表（七天之内）
     *
     * @param iCallback the callback
     */
    public static void getErrorDataList(final IHetCallback iCallback, String deviceId,String startDate,String  endDate,int pageRows,int pageIndex) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("deviceId", deviceId);
        params.put("startDate",startDate);
        if (!TextUtils.isEmpty(endDate)){
            params.put("endDate",endDate);
        }
        params.put("pageRows",pageRows+"");
        params.put("pageIndex",pageIndex+"");
        String path = "/v1/device/data/getErrorDataList";;
        HttpDeal.hetPost(path,params,iCallback);



    }


}
