package com.het.open.lib.biz.deal;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.het.basic.data.http.retrofit2.exception.ApiException;
import com.het.basic.model.ApiResult;
import com.het.basic.model.DeviceBean;
import com.het.basic.utils.GsonUtil;
import com.het.log.Logc;
import com.het.open.lib.biz.api.DeviceGetListApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.DeviceSubModel;
import com.het.open.lib.model.DeviceTypeModel;
import com.het.open.lib.api.HetCodeConstants;

import java.lang.reflect.Type;
import java.util.List;

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
public class DeviceListDeal {

    /**
     * 获取绑定设备列表
     *
     * @param iCallback the callback
     */
    public static void getBindList(final IHetCallback iCallback) {
        DeviceGetListApi.getInstance().getBind().subscribe(new Action1<ApiResult<Object>>() {
            @Override
            public void call(ApiResult<Object> objectApiResult) {
                if (objectApiResult != null) {
                    if (objectApiResult.getCode() == 0) {
                        String json = GsonUtil.getInstance().getGson().toJson(objectApiResult.getData());
                        iCallback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS, json);
                    }
                }
            }


        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                iCallback.onFailed(-1, throwable.getMessage());
            }
        });


    }

    /**
     * 获取大类型设备列表
     *
     * @param iCallback the callback
     */
    public static void getTypeList(final IHetCallback iCallback) {

        DeviceGetListApi.getInstance().getType().subscribe(new Action1<List<DeviceTypeModel>>() {
            @Override
            public void call(List<DeviceTypeModel> deviceTypeModel) {
                if (deviceTypeModel != null) {
                    Type type = new TypeToken<List<DeviceBean>>() {
                    }.getType();
                    String json = GsonUtil.getInstance().getGson().toJson(deviceTypeModel, type);
                    if (!TextUtils.isEmpty(json)) {
                        iCallback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS, json);
                    }
                }
            }
        }, throwable -> {
            if (throwable instanceof ApiException) {
                ApiException apiException = (ApiException) throwable;
                Logc.e(apiException.getMessage());
                iCallback.onFailed(apiException.getCode(), apiException.getMessage());
            }
        });


    }

    /**
     * 获取设备小类型列表
     *
     * @param iCallback the callback
     * @param type      设备大类型
     */
    public static void getSubTypeList(final IHetCallback iCallback, String type) {
        DeviceGetListApi.getInstance().getSubType(type).subscribe(new Action1<List<DeviceBean>>() {
            @Override
            public void call(List<DeviceBean> deviceBeen) {
                if (deviceBeen != null) {
                    Type type = new TypeToken<List<DeviceBean>>() {
                    }.getType();
                    String json =GsonUtil.getInstance().getGson().toJson(deviceBeen, type);
                    if (!TextUtils.isEmpty(json)) {
                        iCallback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS, json);
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
     * 获取设备小类型列表
     *
     * @param iCallback the callback
     * @param type      设备大类型
     */
    public static void getSubTypeListProduct(final IHetCallback iCallback, String type) {

        DeviceGetListApi.getInstance().getSubTypeProduct(type).subscribe(new Action1<List<DeviceSubModel>>() {
            @Override
            public void call(List<DeviceSubModel> deviceBeen) {
                if (deviceBeen != null) {
                    Type type = new TypeToken<List<DeviceSubModel>>() {
                    }.getType();
                    String json =GsonUtil.getInstance().getGson().toJson(deviceBeen, type);
                    if (!TextUtils.isEmpty(json)) {
                        iCallback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS, json);
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
