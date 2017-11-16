package com.het.open.lib.api;

import com.het.open.lib.auth.biz.AuthDeal;
import com.het.open.lib.callback.AuthCallback;


/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>he授权登录：</p>
 * 名称:  授权api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetAuthApi {

    private static HetAuthApi mInstance;
    private HetAuthApi(){
    }


    /**
     * Get instance het auth api.
     *
     * @return the het auth api
     */
    public static HetAuthApi getInstance(){
        if (mInstance==null){
            synchronized (HetAuthApi.class){
                mInstance=new HetAuthApi();
            }
        }
        return  mInstance;
    }


    /**
     * 授权登录
     *
     * @param authCallback 回调
     */
    public void authorize(AuthCallback authCallback){
        new AuthDeal().authorize(authCallback);
    }


    /**
     * 授权登录
     *
     * @param title        登录页面标题
     * @param authCallback 回调
     */
    public void authorize(String title ,AuthCallback authCallback){

        new AuthDeal().authorize(title,authCallback);
    }



}
