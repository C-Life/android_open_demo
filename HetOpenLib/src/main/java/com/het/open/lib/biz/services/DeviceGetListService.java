package com.het.open.lib.biz.services;

import com.het.basic.model.ApiResult;
import com.het.basic.model.DeviceBean;
import com.het.open.lib.model.DeviceSubModel;
import com.het.open.lib.model.DeviceTypeModel;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
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


public interface DeviceGetListService {

    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult<Object>> getBind(@Path("path")String path, @FieldMap Map<String, String> map);

    @GET("{path}")
    Observable<ApiResult<List<DeviceTypeModel>>> getType(@Path("path")String path, @QueryMap Map<String, String> map);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult<List<DeviceBean>>> getSubType(@Path("path")String path, @FieldMap Map<String, String> map);

    @GET("{path}")
    Observable<ApiResult<List<DeviceSubModel>>> getSubTypeProduct(@Path("path")String path, @QueryMap Map<String, String> map);

    @GET("{path}")
    Observable<ApiResult<DeviceBean>> getProduct(@Path("path")String path, @QueryMap Map<String, String> map);


}
