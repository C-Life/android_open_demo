package com.het.open.lib.auth.biz;

import android.content.Context;

import com.het.open.lib.auth.HetAuthMess;
import com.het.open.lib.biz.init.SdkManager;
import com.het.open.lib.callback.AuthCallback;


/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  授权api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class AuthDeal {

    private HetHandler mHetHandler;
    private AuthInfo mAuthInfo;
    private Context mContext;
    /**
     * The constant REDIRECT_URL.
     */
    public static final String REDIRECT_URL = "http://www.clife.com";
//    private static AuthDeal mInstance;
//
//    private AuthDeal(){
//    }
//
//
//    /**
//     * Get instance het auth api.
//     *
//     * @return the het auth api
//     */
//    public static AuthDeal getInstance(){
//        if (mInstance==null){
//            synchronized (AuthDeal.class){
//                if (mInstance==null) mInstance=new AuthDeal();
//            }
//        }
//        return  mInstance;
//    }


    /**
     * 授权登录
     *
     * @param authCallback the auth callback
     */
    public void authorize(AuthCallback authCallback){
        mContext= SdkManager.getInstance().getApplication();
        mAuthInfo=new AuthInfo(mContext, HetAuthMess.getInstance().getAppId(),REDIRECT_URL,HetAuthMess.getInstance().getAppKey());
        mHetHandler = new HetHandler(mContext, mAuthInfo);
        mHetHandler.authorizeWeb(new AuthListener(mContext,authCallback));
    }


    /**
     * 授权登录
     *
     * @param authCallback the auth callback
     */
    public void authorize(String title,AuthCallback authCallback){
        mContext= SdkManager.getInstance().getApplication();
        mAuthInfo=new AuthInfo(mContext, HetAuthMess.getInstance().getAppId(),REDIRECT_URL,HetAuthMess.getInstance().getAppKey(),title);
        mHetHandler = new HetHandler(mContext, mAuthInfo);
        mHetHandler.authorizeWeb(new AuthListener(mContext,authCallback));
    }








}
