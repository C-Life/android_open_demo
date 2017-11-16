package com.het.open.lib.biz.services;

import com.het.basic.model.ApiResult;
import com.het.open.lib.model.AuthRandomCodeModel;
import com.het.open.lib.model.AuthorizationCodeModel;

import java.util.Map;

import retrofit2.http.GET;
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
 * 日期: 2017/10/16<br>
 **/

public interface ThirdService {
    /**
     * 1、SDK请求CLife获取授权码
     */
    @GET("{path}")
    Observable<ApiResult<AuthorizationCodeModel>> getAuthorizationCode(@Path("path") String path, @QueryMap Map<String, String> map);

    /**
     * 2、SDK请求CLife验证验证码和随机码
     */
    @GET("{path}")
    Observable<ApiResult<AuthRandomCodeModel>> checkRandomCode(@Path("path") String path, @QueryMap Map<String, String> map);

}
