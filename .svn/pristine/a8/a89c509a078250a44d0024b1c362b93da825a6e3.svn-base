package com.het.open.lib.api;

import android.support.annotation.NonNull;
import com.het.open.lib.biz.deal.HttpDeal;
import com.het.open.lib.callback.IHetCallback;
import java.util.TreeMap;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  http请求api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetHttpApi {
    private static HetHttpApi mInstance;

    private HetHttpApi() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetHttpApi getInstance() {
        if (mInstance == null) {
            synchronized (HetHttpApi.class) {
                mInstance = new HetHttpApi();
            }
        }
        return mInstance;
    }

    /**
     * post请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 请求结果回调
     */
    public void hetPost(@NonNull String url, TreeMap params, IHetCallback callback){
        HttpDeal.hetClifePost(url,params,callback);
    }

    /**
     * get请求
     *
     * @param url      请求地址
     * @param callback 请求结果回调
     */
    public void hetGet(@NonNull String url,  IHetCallback callback){
        HttpDeal.hetClifeGet(url,callback);
    }

    /**
     * get请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 请求结果回调
     */
    public void hetGet(@NonNull String url, TreeMap params,  IHetCallback callback){
        HttpDeal.hetClifeGet(url,params,callback);
    }
}
