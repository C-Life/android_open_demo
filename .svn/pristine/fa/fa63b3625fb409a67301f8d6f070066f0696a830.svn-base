package com.het.open.lib.biz.services;

import com.het.basic.model.ApiResult;
import com.het.bind.logic.bean.ServerInfoBean;
import com.het.bind.logic.bean.device.DeviceProductBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  <br>
 * 作者: 80010814 4<br>
 * 日期: 2017/8/3<br>
 **/


public interface DeviceBindService {

    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult<ServerInfoBean>> auth(@Path("path") String code, @FieldMap Map<String, String> var2);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult<ServerInfoBean>> openAuth(@Path("path") String openId, String account, @FieldMap Map<String, String> var2);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult<ServerInfoBean>> refersh(@Path("path") @FieldMap Map<String, String> var2);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult<DeviceProductBean>> getProductFromSdk(@Path("path") String var1, @FieldMap Map<String, String> var2);
}
