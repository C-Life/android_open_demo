package com.het.open.lib.biz.deal;


import com.het.basic.data.http.retrofit2.exception.ApiException;
import com.het.basic.model.ApiResult;
import com.het.basic.utils.GsonUtil;
import com.het.log.Logc;
import com.het.open.lib.biz.api.DeviceRomUpgradeApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.api.HetCodeConstants;

import rx.functions.Action1;


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
public class DeviceRomUpgradeDeal {

    /**
     * 检查设备固件版本升级信息
     *
     * @param iCallback 回调
     * @param deviceId  设备id
     */
    public static void check(final IHetCallback iCallback, String deviceId) {
       DeviceRomUpgradeApi.getInstance().check(deviceId).subscribe(new Action1<ApiResult<Object>>() {
           @Override
           public void call(ApiResult<Object> objectApiResult) {
               if (objectApiResult.getCode()==0){
                   String json= GsonUtil.getInstance().getGson().toJson(objectApiResult.getData());
                   if (iCallback!=null){
                       iCallback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS,json);
                   }

               }
           }
       }, new Action1<Throwable>() {
           @Override
           public void call(Throwable throwable) {
               if (throwable instanceof ApiException) {
                   ApiException apiException = (ApiException) throwable;
                   Logc.e(apiException.getMessage());
                   iCallback.onFailed(apiException.getCode(), apiException.getMessage());
               }
           }
       });



    }

    /**
     * 检查设备固件版本升级信息
     *
     * @param iCallback 回调
     * @param deviceId          设备id
     * @param deviceVersionType 设备版本类型（1-WIFI，2-PCB（目前蓝牙设备、wifi设备都只升级pcb）,3-蓝牙模块升级）
     * @param deviceVersionId   设备固件版本id
     */
    public static void confirmUpgrade(final IHetCallback iCallback, String deviceId,String deviceVersionType, String deviceVersionId) {
        DeviceRomUpgradeApi.getInstance().confirmUpgrade(deviceId,deviceVersionType,deviceVersionId).subscribe(new Action1<ApiResult>() {
            @Override
            public void call(ApiResult apiResult) {
                if (apiResult!=null){
                    if (apiResult.getCode()==0){
                        iCallback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS,null);
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (throwable instanceof ApiException) {
                    ApiException apiException = (ApiException) throwable;
                    Logc.e(apiException.getMessage());
                    iCallback.onFailed(apiException.getCode(), apiException.getMessage());
                }
            }
        });



    }

    /**
     * 检查设备固件版本升级信息
     *
     * @param iCallback 回调
     * @param deviceId          设备id
     * @param deviceVersionId   设备固件版本id
     */
    public static void confirmSuccess(final IHetCallback iCallback, String deviceId,String deviceVersionId) {
        DeviceRomUpgradeApi.getInstance().confirmSuccess(deviceId,deviceVersionId).subscribe(new Action1<ApiResult>() {
            @Override
            public void call(ApiResult apiResult) {
                if (apiResult!=null){
                    if (apiResult.getCode()==0){
                        iCallback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS,null);
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (throwable instanceof ApiException) {
                    ApiException apiException = (ApiException) throwable;
                    Logc.e(apiException.getMessage());
                    iCallback.onFailed(apiException.getCode(), apiException.getMessage());
                }
            }
        });



    }

    /**
     * 检查设备固件版本升级信息
     *
     * @param iCallback 回调
     * @param deviceId          设备id
     * @param deviceVersionId   设备固件版本id
     */
    public static void getUpgradeProgres(final IHetCallback iCallback, String deviceId,String deviceVersionId) {
        DeviceRomUpgradeApi.getInstance().getUpgradeProgres(deviceId,deviceVersionId).subscribe(new Action1<ApiResult>() {
            @Override
            public void call(ApiResult apiResult) {
                if (apiResult!=null){
                    if (apiResult.getCode()==0){
                        iCallback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS,null);
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (throwable instanceof ApiException) {
                    ApiException apiException = (ApiException) throwable;
                    Logc.e(apiException.getMessage());
                    iCallback.onFailed(apiException.getCode(), apiException.getMessage());
                }
            }
        });



    }


}
