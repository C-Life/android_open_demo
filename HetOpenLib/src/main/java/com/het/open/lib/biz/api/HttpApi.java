package com.het.open.lib.biz.api;

import com.het.basic.base.helper.RxSchedulers;
import com.het.basic.data.api.token.HetParamsMerge;
import com.het.basic.data.http.okhttp.util.OkHttpTag;
import com.het.basic.data.http.retrofit2.RetrofitManager;
import com.het.basic.model.ApiResult;
import com.het.open.lib.biz.services.HttpService;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import rx.Observable;

/**
 * 通用http请求api
 * Created by 80010814 on 2016/12/5.
 */

public class HttpApi {

    private static HttpApi instance = null;
    private HttpService api;

    private void createapi() {
        api = RetrofitManager.getRetrofit(new OkHttpTag(this.getClass().getName())).create(HttpService.class);
    }

    public static HttpApi getInstance() {
        if (instance == null) {
            synchronized (HttpApi.class) {
                if (instance == null) {
                    instance = new HttpApi();
                }
            }
        }
        instance.createapi();
        return instance;
    }
    /**
     * post请求
     *
     * @param url      请求地址
     * @param params   请求参数
     */
    public  Observable<ApiResult<Object>> hetPost(String url, TreeMap params) {
        HetParamsMerge paramsRequest = new HetParamsMerge();
        Iterator titer=params.entrySet().iterator();
        while(titer.hasNext()){
            Map.Entry ent=(Map.Entry )titer.next();
            String keyt=ent.getKey().toString();
            String valuet=ent.getValue().toString();
            paramsRequest.add(keyt,valuet);
        }
        String path = url;
        return api.hetPost(path, paramsRequest
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }

    /**
     * get请求
     *
     * @param url      请求地址
     */
    public Observable<ApiResult<Object>> hetGet(String url) {
        HetParamsMerge params = new HetParamsMerge();
        String path = url;
        return api.hetGet(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }

    /**
     * get请求
     *
     * @param url      请求地址
     * @param params   请求参数
     */
    public  Observable<ApiResult<Object>> hetGet(String url, TreeMap params) {

        HetParamsMerge paramsRequest = new HetParamsMerge();
        Iterator titer=params.entrySet().iterator();
        while(titer.hasNext()){
            Map.Entry ent=(Map.Entry )titer.next();
            String keyt=ent.getKey().toString();
            String valuet=ent.getValue().toString();
            paramsRequest.add(keyt,valuet);
        }
        String path = url;
        return api.hetGet(path, paramsRequest
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());

    }

}
