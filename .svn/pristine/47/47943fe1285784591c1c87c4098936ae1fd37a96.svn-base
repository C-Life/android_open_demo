package com.het.open.lib.biz.api;


import android.text.TextUtils;

import com.het.basic.base.helper.RxSchedulers;
import com.het.basic.data.api.token.HetParamsMerge;
import com.het.basic.data.http.okhttp.util.OkHttpTag;
import com.het.basic.data.http.retrofit2.RetrofitManager;
import com.het.basic.model.ApiResult;
import com.het.bind.logic.bean.device.DeviceProductBean;
import com.het.open.lib.biz.constans.GlobalAddr;
import com.het.open.lib.biz.services.DeviceBindService;

import rx.Observable;


public class DeviceBindApi {
    private static DeviceBindApi instance = null;
    private DeviceBindService api;

    public DeviceBindApi() {
    }

    private void createapi() {
        this.api = RetrofitManager.getRetrofit(new OkHttpTag(this.getClass().getName())).create(DeviceBindService.class);
    }

    public static DeviceBindApi getInstance() {
        if(instance == null) {
            synchronized(DeviceBindApi.class) {
                instance = new DeviceBindApi();
            }
        }

        instance.createapi();
        return instance;
    }


    public Observable<ApiResult<DeviceProductBean>> getProductFromSdk(String productId) {
        String path = "/v1/product/getProductFromSdk";
        HetParamsMerge param = new HetParamsMerge();
        if(!TextUtils.isEmpty(productId)) {
            param.add("productId", productId);
        }
        String authUrl= GlobalAddr.HOST;
        if (authUrl.contains("dp")||authUrl.contains("50")){
            path="/v1/app/open/product/getProductFromSdk";
        }
        param.setPath(path).isHttps(true).sign(false).accessToken(true).timeStamp(true);
        return this.api.getProductFromSdk(path, param.getParams()).compose(RxSchedulers.io_main());
    }

}
