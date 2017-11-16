package com.het.open.lib.biz.deal;

import com.het.basic.data.http.retrofit2.exception.ApiException;
import com.het.basic.model.ApiResult;
import com.het.open.lib.biz.api.DeviceControlApi;
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
public class DeviceControlDeal {

    /**
     * 获取异常数据
     *
     * @param callback
     */
    public static void getErrorData(final IHetCallback callback,String deviceId) {
        DeviceControlApi.getInstance().getErrorData(deviceId).subscribe(new Action1<ApiResult<Object>>() {
            @Override
            public void call(ApiResult<Object> objectApiResult) {
                if (objectApiResult!=null){
                    if (objectApiResult.getCode()==0){
                        Object data=objectApiResult.getData();
                        if (callback!=null){
                            callback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS,data!=null?data.toString():null);
                        }
                    }else {
                        if (callback!=null){
                            callback.onSuccess(HetCodeConstants.HTTP_RET_FAILED,"server error");
                        }
                    }
                }else {
                    if (callback!=null){
                        callback.onSuccess(HetCodeConstants.HTTP_RET_FAILED,"server error");
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (throwable instanceof ApiException){
                    if (callback!=null){
                        callback.onFailed(((ApiException) throwable).getCode(),throwable.getMessage());
                    }
                }else {
                    if (callback!=null){
                        callback.onSuccess(HetCodeConstants.HTTP_RET_FAILED,"server error");
                    }
                }
            }
        });



    }

    /**
     * 获取异常数据
     *
     * @param callback
     */
    public static void getRunData(final IHetCallback callback,String deviceId) {
        DeviceControlApi.getInstance().getRun(deviceId).subscribe(new Action1<ApiResult<Object>>() {
            @Override
            public void call(ApiResult<Object> objectApiResult) {
                if (objectApiResult!=null){
                    if (objectApiResult.getCode()==0){
                        Object data=objectApiResult.getData();

                        if (callback!=null){
                            callback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS,data!=null?data.toString():null);
                        }
                    }else {
                        if (callback!=null){
                            callback.onSuccess(HetCodeConstants.HTTP_RET_FAILED,"server error");
                        }
                    }
                }else {
                    if (callback!=null){
                        callback.onSuccess(HetCodeConstants.HTTP_RET_FAILED,"server error");
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (throwable instanceof ApiException){
                    if (callback!=null){
                        callback.onFailed(((ApiException) throwable).getCode(),throwable.getMessage());
                    }
                }else {
                    if (callback!=null){
                        callback.onSuccess(HetCodeConstants.HTTP_RET_FAILED,"server error");
                    }
                }
            }
        });



    }

    /**
     * 获取异常数据
     *
     * @param callback
     */
    public static void getConfigData(final IHetCallback callback,String deviceId) {
        DeviceControlApi.getInstance().getConfig(deviceId).subscribe(new Action1<ApiResult<Object>>() {
            @Override
            public void call(ApiResult<Object> objectApiResult) {
                if (objectApiResult!=null){
                  if (objectApiResult.getCode()==0){
                        Object data=objectApiResult.getData();
                        if (callback!=null){
                            callback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS,data!=null?data.toString():null);
                        }
                    }else {
                        if (callback!=null){
                            callback.onSuccess(HetCodeConstants.HTTP_RET_FAILED,"server error");
                        }
                    }
                }else {
                    if (callback!=null){
                        callback.onSuccess(HetCodeConstants.HTTP_RET_FAILED,"server error");
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (throwable instanceof ApiException){
                    if (callback!=null){
                        callback.onFailed(((ApiException) throwable).getCode(),throwable.getMessage());
                    }
                }else {
                    if (callback!=null){
                        callback.onSuccess(HetCodeConstants.HTTP_RET_FAILED,"server error");
                    }
                }
            }
        });



    }

    /**
     * 获取异常数据
     *
     * @param callback
     */
    public static void setData(final IHetCallback callback,String deviceId,String json) {
        DeviceControlApi.getInstance().sendDataToDevice(deviceId,json).subscribe(new Action1<ApiResult<Object>>() {
            @Override
            public void call(ApiResult<Object> objectApiResult) {
                if (objectApiResult!=null){
                    if (objectApiResult.getCode()==0){
                        Object data=objectApiResult.getData();
                        if (callback!=null){
                            callback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS,data!=null?data.toString():null);
                        }
                    }else {
                        int code = objectApiResult.getCode();
                        String msg = objectApiResult.getMsg();
                        if (callback != null) {
                            callback.onFailed(code, msg);
                        }
                    }
                }else {
                    if (callback!=null){
                        callback.onSuccess(HetCodeConstants.HTTP_RET_FAILED,"server error");
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (throwable instanceof ApiException){
                    if (callback!=null){
                        callback.onFailed(((ApiException) throwable).getCode(),throwable.getMessage());
                    }
                }else {
                    if (callback!=null){
                        callback.onSuccess(HetCodeConstants.HTTP_RET_FAILED,"server error");
                    }
                }
            }
        });



    }




}
