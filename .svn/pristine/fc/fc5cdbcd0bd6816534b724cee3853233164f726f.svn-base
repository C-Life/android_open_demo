package com.het.open.lib.biz.services;

import com.het.basic.model.ApiResult;

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


public interface DeviceRomUpgradeService {

    @GET("{path}")
    Observable<ApiResult<Object>> check(@Path("path") String path, @QueryMap Map<String, String> map);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult> confirm(@Path("path")String path, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult<Object>> progress(@Path("path")String path, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult> confirmSuccess(@Path("path")String path, @FieldMap Map<String, String> map);
}
