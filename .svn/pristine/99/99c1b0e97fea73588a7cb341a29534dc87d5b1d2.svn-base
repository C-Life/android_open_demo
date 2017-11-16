package com.het.open.lib.auth.thirdauth;

import com.het.basic.base.helper.RxSchedulers;
import com.het.basic.constact.AppConstant;
import com.het.basic.data.api.token.HetParamsMerge;
import com.het.basic.data.http.okhttp.util.OkHttpTag;
import com.het.basic.data.http.retrofit2.RetrofitManager;
import com.het.basic.model.ApiResult;
import com.het.basic.utils.StringUtils;
import com.het.open.lib.biz.constans.GlobalAddr;
import com.het.open.lib.biz.services.ThirdService;
import com.het.open.lib.model.AuthRandomCodeModel;
import com.het.open.lib.model.AuthorizationCodeModel;

import rx.Observable;

/**
 * Created by Administrator on 2017-10-16.
 */

public class ThirdAuthApi {
    private static ThirdAuthApi instance = null;
    private ThirdService api;

    private void createapi() {
        api = RetrofitManager.getRetrofit(new OkHttpTag(this.getClass().getName())).create(ThirdService.class);
    }

    public static ThirdAuthApi getInstance() {
        if (instance == null) {
            synchronized (ThirdAuthApi.class) {
                if (instance == null) {
                    instance = new ThirdAuthApi();
                }
            }
        }
        instance.createapi();
        return instance;
    }

    public Observable<ApiResult<AuthorizationCodeModel>> getAuthorizationCode(String account,String openId) {
        String path = "/v1/oauth2/getAuthorizationCode";
        String host= GlobalAddr.HOST;
        if (host.contains("dp")||host.contains("50")){
            path="/v1/app/open/oauth2/getAuthorizationCode";
        }
        HetParamsMerge paramsMerge = new HetParamsMerge();
        paramsMerge.add("appId", AppConstant.APPID);
        paramsMerge.add("account",account);
        paramsMerge.add("openId",openId);

        paramsMerge.setPath(path).isHttps(true).sign(false).accessToken(true).timeStamp(true);
        return this.api.getAuthorizationCode(path, paramsMerge.getParams()).compose(RxSchedulers.io_main());
    }

    /**
     * @param verificationCode 验证码,不提交则默认为二次授权
     * @param randomCode       随机码
     * @return
     */
    public Observable<ApiResult<AuthRandomCodeModel>> checkRandomCode(String verificationCode, String randomCode) {
        String path = "/v1/oauth2/checkRandomCode";
        String host= GlobalAddr.HOST;
        if (host.contains("dp")||host.contains("50")){
            path="/v1/app/open/oauth2/checkRandomCode";
        }
        HetParamsMerge paramsMerge = new HetParamsMerge();
        if (!StringUtils.isNull(verificationCode))
            paramsMerge.add("verificationCode", verificationCode);

        paramsMerge.add("randomCode", randomCode);
        paramsMerge.add("appId", AppConstant.APPID);

        paramsMerge.setPath(path).isHttps(true).sign(false).accessToken(true).timeStamp(true);
        return this.api.checkRandomCode(path, paramsMerge.getParams()).compose(RxSchedulers.io_main());
    }

}
