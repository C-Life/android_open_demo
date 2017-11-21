package com.het.open.lib.biz.api;

import com.het.basic.base.helper.RxSchedulers;
import com.het.basic.data.api.token.HetParamsMerge;
import com.het.basic.data.http.okhttp.util.OkHttpTag;
import com.het.basic.data.http.retrofit2.RetrofitManager;
import com.het.basic.model.ApiResult;
import com.het.basic.model.DeviceBean;
import com.het.bind.logic.api.ApiBind;
import com.het.open.lib.biz.constans.GlobalAddr;
import com.het.open.lib.biz.services.DeviceGetListService;
import com.het.open.lib.model.DeviceSubModel;
import com.het.open.lib.model.DeviceTypeModel;

import java.util.List;

import rx.Observable;

import static com.het.basic.base.helper.RxSchedulers._io_main;
import static com.het.basic.base.helper.RxSchedulers.io_main;

/**
 * 设备列表api
 * Created by xuchao on 2016/3/14.
 */
public class DeviceGetListApi {

    private static DeviceGetListApi instance = null;
    private DeviceGetListService api;

    private void createapi() {
        api = RetrofitManager.getRetrofit(new OkHttpTag(this.getClass().getName())).create(DeviceGetListService.class);
    }

    public static DeviceGetListApi getInstance() {
        if (instance == null) {
            synchronized (ApiBind.class) {
                if (instance == null) {
                    instance = new DeviceGetListApi();
                }
            }
        }
        instance.createapi();
        return instance;
    }

    /**
     * 获取绑定设备列表
     */
    public Observable<ApiResult<Object>> getBind() {

        HetParamsMerge params = new HetParamsMerge();
        params.add("appType","1");
        params.add("version", "1.1");
        String authUrl= GlobalAddr.HOST;
        String path = "/v1/device/getBind";
        if (authUrl.contains("dp")||authUrl.contains("50")){
            path = "/v1/app/open/device/getBind";
        }
        return api.getBind(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(io_main());



    }


    /**
     * 获取设备大类型列表
     *
     */
    public Observable<List<DeviceTypeModel>> getType() {
        HetParamsMerge params = new HetParamsMerge();
        String path = "/v1/device/type/list";
        String authUrl= GlobalAddr.HOST;
        if (authUrl.contains("dp")||authUrl.contains("50")){
            path = "/v1/app/open/device/type/list";
        }
        return api.getType(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(_io_main());



    }

    /**
     * 获取设备小类型列表
     *
     * @param type
     */
    public Observable<List<DeviceBean>> getSubType(String type) {
        HetParamsMerge params = new HetParamsMerge();
        params.add("deviceTypeId", type);
        params.add("version", "1.1");
        String path = "/v1/device/subtype/list";
        String authUrl= GlobalAddr.HOST;
        if (authUrl.contains("dp")||authUrl.contains("50")){
            path = "/v1/app/open/device/subtype/list";
        }
        return api.getSubType(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(_io_main());


    }

    /**
     * 获取设备小类型列表
     *
     */
    public Observable<List<DeviceSubModel>> getSubTypeProduct(String type) {

        HetParamsMerge params = new HetParamsMerge();
        params.add("deviceTypeId", type);
        params.add("appType", "1");
        String path = "/v1/device/product/list";
        String authUrl= GlobalAddr.HOST;
        if (authUrl.contains("dp")||authUrl.contains("50")){
            path = "/v1/app/open/device/product/list";
        }
        return api.getSubTypeProduct(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers._io_main());

    }

    /**
     * 获取设备model信息
     *
     * @param productId 产品id
     */
    public Observable<DeviceBean> getProduct(String productId ) {
        HetParamsMerge params = new HetParamsMerge();
        params.add("productId", productId);
        String path ="/v1/product/getProduct";
        String url= GlobalAddr.HOST;
        if (url.contains("dp")||url.contains("50")){
            path="/v1/app/open/product/getProduct";
        }
        return api.getProduct(path, params
                .setPath(path)
                .isHttps(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers._io_main());

    }


}
