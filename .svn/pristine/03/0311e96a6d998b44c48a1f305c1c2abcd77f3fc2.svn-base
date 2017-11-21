package com.het.open.lib.biz.deal;

import com.het.basic.data.http.retrofit2.exception.ApiException;
import com.het.basic.model.ApiResult;
import com.het.basic.utils.GsonUtil;
import com.het.open.lib.biz.api.HttpApi;
import com.het.open.lib.biz.constans.GlobalAddr;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.api.HetCodeConstants;

import java.util.TreeMap;

import rx.functions.Action1;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  <br>
 * 作者: 80010814 4<br>
 * 日期: 2017/8/4<br>
 **/


public class HttpDeal {

    /**
     * post请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 请求结果回调
     */
    public static void hetClifePost(String url, TreeMap params, final IHetCallback callback){
        GlobalAddr.setClifeUrl();
        HttpApi.getInstance().hetPost(url,params).subscribe(new Action1<ApiResult<Object>>() {
            @Override
            public void call(ApiResult<Object> objectApiResult) {
                GlobalAddr.setOpenUrl();
                if (objectApiResult!=null){
                    if (objectApiResult.getCode()==0){
                        Object data=objectApiResult.getData();
                        if (callback!=null){
                            String json= GsonUtil.getInstance().getGson().toJsonTree(data).toString();
                            callback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS,json);
                        }
                    }else {
                        if (callback!=null){
                            int code=objectApiResult.getCode();
                            String msg=objectApiResult.getMsg();
                            callback.onFailed(code,msg!=null?msg:"server error");
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
                GlobalAddr.setOpenUrl();
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
     * post请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 请求结果回调
     */
    public static  void hetPost(String url, TreeMap params, final IHetCallback callback){

        HttpApi.getInstance().hetPost(url,params).subscribe(new Action1<ApiResult<Object>>() {
            @Override
            public void call(ApiResult<Object> objectApiResult) {
                if (objectApiResult!=null){
                    if (objectApiResult.getCode()==0){
                        Object data=objectApiResult.getData();
                        if (callback!=null){
                            String json=GsonUtil.getInstance().getGson().toJsonTree(data).toString();
                            callback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS,json);

                        }
                    }else {
                        if (callback!=null){
                            int code=objectApiResult.getCode();
                            String msg=objectApiResult.getMsg();
                            callback.onFailed(code,msg!=null?msg:"server error");
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
     * get请求
     *
     * @param url      请求地址
     * @param callback 请求结果回调
     */
    public static void hetClifeGet(String url, final IHetCallback callback){
        GlobalAddr.setClifeUrl();
        HttpApi.getInstance().hetGet(url).subscribe(new Action1<ApiResult<Object>>() {
            @Override
            public void call(ApiResult<Object> objectApiResult) {
                GlobalAddr.setOpenUrl();
                if (objectApiResult!=null){
                    if (objectApiResult.getCode()==0){
                        Object data=objectApiResult.getData();
                        if (callback!=null){
                            String json=GsonUtil.getInstance().getGson().toJsonTree(data).toString();
                            callback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS,json);
                        }
                    }else {
                        if (callback!=null){
                            int code=objectApiResult.getCode();
                            String msg=objectApiResult.getMsg();
                            callback.onFailed(code,msg!=null?msg:"server error");
                        }
                    }
                }else {
                    if (callback!=null){
                        callback.onFailed(HetCodeConstants.HTTP_RET_FAILED,"server error");
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                GlobalAddr.setOpenUrl();
                if (throwable instanceof ApiException){
                    if (callback!=null){
                        callback.onFailed(((ApiException) throwable).getCode(),throwable.getMessage());
                    }
                }else {
                    if (callback!=null){
                        callback.onFailed(HetCodeConstants.HTTP_RET_FAILED,"server error");
                    }
                }
            }
        });
    }


    /**
     * get请求
     *
     * @param url      请求地址
     * @param callback 请求结果回调
     */
    public static void hetGet(String url, final IHetCallback callback){
        HttpApi.getInstance().hetGet(url).subscribe(new Action1<ApiResult<Object>>() {
            @Override
            public void call(ApiResult<Object> objectApiResult) {
                if (objectApiResult!=null){
                    if (objectApiResult.getCode()==0){
                        Object data=objectApiResult.getData();
                        if (callback!=null){
                            String json=GsonUtil.getInstance().getGson().toJsonTree(data).toString();
                            callback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS,json);
                        }
                    }else {
                        if (callback!=null){
                            int code=objectApiResult.getCode();
                            String msg=objectApiResult.getMsg();
                            callback.onFailed(code,msg!=null?msg:"server error");
                        }
                    }
                }else {
                    if (callback!=null){
                        callback.onFailed(HetCodeConstants.HTTP_RET_FAILED,"server error");
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
                        callback.onFailed(HetCodeConstants.HTTP_RET_FAILED,"server error");
                    }
                }
            }
        });
    }

    /**
     * get请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 请求结果回调
     */
    public static void hetGet(String url, TreeMap params, final IHetCallback callback){


        HttpApi.getInstance().hetGet(url,params).subscribe(new Action1<ApiResult<Object>>() {
            @Override
            public void call(ApiResult<Object> objectApiResult) {
                if (objectApiResult!=null){
                    if (objectApiResult.getCode()==0){
                        Object data=objectApiResult.getData();
                        if (callback!=null){
                            String json=GsonUtil.getInstance().getGson().toJsonTree(data).toString();
                            callback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS,json);
                        }
                    }else {
                        if (callback!=null){
                            int code=objectApiResult.getCode();
                            String msg=objectApiResult.getMsg();
                            callback.onFailed(code,msg!=null?msg:"server error");
                        }
                    }
                }else {
                    if (callback!=null){
                        callback.onFailed(HetCodeConstants.HTTP_RET_FAILED,"server error");
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
                        callback.onFailed(HetCodeConstants.HTTP_RET_FAILED,"server error");
                    }
                }
            }
        });
    }

    /**
     * get请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 请求结果回调
     */
    public static void hetClifeGet(String url, TreeMap params, final IHetCallback callback){

        GlobalAddr.setClifeUrl();
        HttpApi.getInstance().hetGet(url,params).subscribe(new Action1<ApiResult<Object>>() {
            @Override
            public void call(ApiResult<Object> objectApiResult) {
                GlobalAddr.setOpenUrl();
                if (objectApiResult!=null){
                    if (objectApiResult.getCode()==0){
                        Object data=objectApiResult.getData();
                        if (callback!=null){
                            String json=GsonUtil.getInstance().getGson().toJsonTree(data).toString();
                            callback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS,json);

                        }
                    }else {
                        if (callback!=null){
                            int code=objectApiResult.getCode();
                            String msg=objectApiResult.getMsg();
                            callback.onFailed(code,msg!=null?msg:"server error");
                        }
                    }
                }else {
                    if (callback!=null){
                        callback.onFailed(HetCodeConstants.HTTP_RET_FAILED,"server error");
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                GlobalAddr.setOpenUrl();
                if (throwable instanceof ApiException){
                    if (callback!=null){
                        callback.onFailed(((ApiException) throwable).getCode(),throwable.getMessage());
                    }
                }else {
                    if (callback!=null){
                        callback.onFailed(HetCodeConstants.HTTP_RET_FAILED,"server error");
                    }
                }
            }
        });
    }
}
