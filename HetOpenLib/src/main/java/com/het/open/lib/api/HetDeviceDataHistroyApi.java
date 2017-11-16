package com.het.open.lib.api;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.het.open.lib.biz.deal.DeviceDataHistroyDeal;
import com.het.open.lib.callback.IHetCallback;


/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  获取设备的历史数据（7天）api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetDeviceDataHistroyApi {

    private static HetDeviceDataHistroyApi mInstance;


    private HetDeviceDataHistroyApi() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetDeviceDataHistroyApi getInstance() {
        if (mInstance == null) {
            synchronized (HetDeviceDataHistroyApi.class) {
                if (mInstance==null)mInstance = new HetDeviceDataHistroyApi();
            }
        }
        return mInstance;
    }


    /**
     * 获取设备运行数据列表（七天之内）
     *
     * @param iHetCallback the het callback
     * @param deviceId     是	string	设备标识
     * @param startDate    是	Date	开始时间
     * @param endDate      否	Date	结束时间（默认为当天）
     * @param pageRows     否	int	每页显示的行数，默认为20
     * @param pageIndex    否	int	当前页，默认为1
     */
    public void getRunDataList(final IHetCallback iHetCallback, @NonNull String deviceId, String startDate, String  endDate, int pageRows, int pageIndex) {
        if (TextUtils.isEmpty(deviceId)) {
            if (iHetCallback != null) {
                iHetCallback.onFailed(HetCodeConstants.HTTP_RET_FAILED, "设备id不能为空");
            }
            return;
        }
        if (TextUtils.isEmpty(startDate)) {
            if (iHetCallback != null) {
                iHetCallback.onFailed(HetCodeConstants.HTTP_RET_FAILED, "开始时间不能为空");
            }
            return;
        }
        DeviceDataHistroyDeal.getRunDataList(iHetCallback, deviceId, startDate, endDate, pageRows, pageIndex);
    }


    /**
     * 获取设备控制数据列表（七天之内）
     *
     * @param iHetCallback the het callback
     * @param deviceId     是	string	设备标识
     * @param startDate    是	Date	开始时间
     * @param endDate      否	Date	结束时间（默认为当天）
     * @param pageRows     否	int	每页显示的行数，默认为20
     * @param pageIndex    否	int	当前页，默认为1
     */
    public void getConfigDataList(final IHetCallback iHetCallback, @NonNull String deviceId, String startDate,String  endDate,int pageRows, int pageIndex) {
        if (TextUtils.isEmpty(deviceId)){
            if (iHetCallback != null) {
                iHetCallback.onFailed(HetCodeConstants.HTTP_RET_FAILED, "设备id不能为空");
            }
            return;
        }
        if (TextUtils.isEmpty(startDate)){
            if (iHetCallback != null) {
                iHetCallback.onFailed(HetCodeConstants.HTTP_RET_FAILED, "开始时间不能为空");
            }
            return;
        }
        DeviceDataHistroyDeal.getConfigDataList(iHetCallback, deviceId, startDate, endDate, pageRows, pageIndex);


    }

    /**
     * 获取设备故障数据列表（七天之内）
     *
     * @param iHetCallback the het callback
     * @param deviceId     是	string	设备标识
     * @param startDate    是	Date	开始时间
     * @param endDate      否	Date	结束时间（默认为当天）
     * @param pageRows     否	int	每页显示的行数，默认为20
     * @param pageIndex    否	int	当前页，默认为1
     */
    public void getErrorDataList(final IHetCallback iHetCallback, @NonNull String deviceId, String startDate,String  endDate,int pageRows, int pageIndex) {
        if (TextUtils.isEmpty(deviceId)) {
            if (iHetCallback != null) {
                iHetCallback.onFailed(HetCodeConstants.HTTP_RET_FAILED, "设备id不能为空");
            }
            return;
        }
        if (TextUtils.isEmpty(startDate)) {
            if (iHetCallback != null) {
                iHetCallback.onFailed(HetCodeConstants.HTTP_RET_FAILED, "开始时间不能为空");
            }
            return;
        }
        DeviceDataHistroyDeal.getErrorDataList(iHetCallback, deviceId, startDate, endDate, pageRows, pageIndex);
    }


}
