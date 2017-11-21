package com.het.open.lib.biz.api;


import android.text.TextUtils;

import com.het.basic.base.helper.RxSchedulers;
import com.het.basic.data.api.token.HetParamsMerge;
import com.het.basic.data.http.okhttp.util.OkHttpTag;
import com.het.basic.data.http.retrofit2.RetrofitManager;
import com.het.open.lib.biz.constans.GlobalAddr;
import com.het.open.lib.biz.services.AuthService;
import com.het.open.lib.model.AuthLoginModel;

import rx.Observable;

/**
 * Auth逻辑
 * Created by xuchao on 2016/3/14.
 */
public class AuthApi  {

    private static AuthApi instance = null;
    private AuthService api;

    private void createapi() {
        api = RetrofitManager.getRetrofit(new OkHttpTag(this.getClass().getName())).create(AuthService.class);
    }

    public static AuthApi getInstance() {
        if (instance == null) {
            synchronized (AuthApi.class) {
                if (instance == null) {
                    instance = new AuthApi();
                }
            }
        }
        instance.createapi();
        return instance;
    }


    /**
     * 获取authtoken
     * @param code
     */
    public Observable<AuthLoginModel> auth(String code) {
        HetParamsMerge params = new HetParamsMerge();
        params.add("code", code);
        params.add("grantType", "authorization_code");
        String path = "/oauth2/accessToken";
        String host= GlobalAddr.HOST;
        if (host.contains("dp")||host.contains("50")){
            path="/v1/app/open"+path;
        }
        return api.auth(path, params
                .setPath(path)
                .isHttps(true)
                .sign(false)
                .accessToken(false)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers._io_main());
    }

    /**
     * 获取openId（自动授权）
     *
     * @param openId
     * @param account

     */
    public Observable<AuthLoginModel> openAuth( String openId,String account) {

        HetParamsMerge params = new HetParamsMerge();
        if (!TextUtils.isEmpty(openId)){
            params.add("openId", openId);
        }
        if (!TextUtils.isEmpty(account)){
            params.add("account", account);
        }
        String authUrl= GlobalAddr.HOST;
        if (authUrl.contains("dp")||authUrl.contains("50")){
            authUrl=authUrl+"v1/app/open/";
        }
        String path = "/v1/oauth2/create";
        return api.auth(path, params
                .setPath(path)
                .isHttps(true)
                .sign(false)
                .accessToken(false)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers._io_main());
    }






}
