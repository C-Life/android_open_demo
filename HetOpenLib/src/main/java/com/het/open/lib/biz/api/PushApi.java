package com.het.open.lib.biz.api;

import com.het.basic.base.helper.RxSchedulers;
import com.het.basic.data.api.token.HetParamsMerge;
import com.het.basic.data.http.okhttp.util.OkHttpTag;
import com.het.basic.data.http.retrofit2.RetrofitManager;
import com.het.basic.model.ApiResult;
import com.het.open.lib.biz.constans.GlobalAddr;
import com.het.open.lib.biz.services.HetPushService;

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
public class PushApi {
    private static PushApi instance = null;
    private HetPushService api;

    public PushApi() {
    }

    private void createapi() {
        this.api = RetrofitManager.getRetrofit(new OkHttpTag(this.getClass().getName())).create(HetPushService.class);
    }

    public static PushApi getInstance() {
        if (instance == null) {
            synchronized (PushApi.class) {
                instance = new PushApi();
            }
        }

        instance.createapi();
        return instance;
    }

    /**
     * 推送绑定
     */
    public Observable<ApiResult<Object>> bindPush(String registrationId) {
        HetParamsMerge params = new HetParamsMerge();
        String path = "/v1/push/bind";
        String host = GlobalAddr.HOST;
        if (host.contains("dp") || host.contains("50")) {
            path = "/v1/app/open" + path;
        }
        params.add("deviceType", "3");
        params.add("pushPattern", "2");
        params.add("deviceId", registrationId);
        return api.bindPush(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }

    /**
     * 推送绑定
     */
    public Observable<ApiResult<Object>> unBindPush() {
        HetParamsMerge params = new HetParamsMerge();
        String path = "/v1/push/unbind";
        String host = GlobalAddr.HOST;
        if (host.contains("dp") || host.contains("50")) {
            path = "/v1/app/open" + path;
        }
        return api.bindPush(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }
}
