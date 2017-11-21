package com.het.open.lib.biz.services;

import com.het.basic.model.ApiResult;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * 作者: liuzh<br>
 * 版本: 2.0<br>
 * 日期: 2017-11-13<br>
 * 描述:
 **/
public interface HetPushService {

    @GET("{path}")
    Observable<ApiResult<Object>> bindPush(@Path("path") String path, @QueryMap Map<String, String> map);

    @GET("{path}")
    Observable<ApiResult<Object>> unBindPush(@Path("path") String path, @QueryMap Map<String, String> map);
}
