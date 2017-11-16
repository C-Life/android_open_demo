package com.het.open.lib.biz.api;

import com.het.basic.base.helper.RxSchedulers;
import com.het.basic.data.api.token.HetParamsMerge;
import com.het.basic.data.http.okhttp.util.OkHttpTag;
import com.het.basic.data.http.retrofit2.RetrofitManager;
import com.het.basic.model.ApiResult;
import com.het.open.lib.biz.constans.GlobalAddr;
import com.het.open.lib.biz.services.UserService;

import rx.Observable;

/**
 * 消息api
 * Created by xuchao on 2016/6/1.
 */
public class UserApi {

    private static UserApi instance = null;
    private UserService api;

    private void createapi() {
        api = RetrofitManager.getRetrofit(new OkHttpTag(this.getClass().getName())).create(UserService.class);
    }

    public static UserApi getInstance() {
        if (instance == null) {
            synchronized (UserApi.class) {
                if (instance == null) {
                    instance = new UserApi();
                }
            }
        }
        instance.createapi();
        return instance;
    }

    /**
     * 获取用户信息
     *
     */
    public Observable<ApiResult<Object>> getUserMess() {
        HetParamsMerge params = new HetParamsMerge();
        String path = "/oauth2/get";
        String host= GlobalAddr.HOST;
        if (host.contains("dp")||host.contains("50")){
            path="/v1/app/open"+path;
        }
        return api.getUserMess(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }




}
