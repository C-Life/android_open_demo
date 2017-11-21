package com.het.open.lib.biz.api;

import com.het.basic.base.helper.RxSchedulers;
import com.het.basic.data.api.token.HetParamsMerge;
import com.het.basic.data.http.okhttp.util.OkHttpTag;
import com.het.basic.data.http.retrofit2.RetrofitManager;
import com.het.basic.model.ApiResult;
import com.het.open.lib.biz.constans.GlobalAddr;
import com.het.open.lib.biz.services.DeviceControlService;
import com.het.open.lib.biz.constans.DeviceParamContant;

import rx.Observable;

public class DeviceControlApi {


    private static DeviceControlApi  instance = null;
    private DeviceControlService api;

    private void createapi() {
        api = RetrofitManager.getRetrofit(new OkHttpTag(this.getClass().getName())).create(DeviceControlService.class);
    }

    public static DeviceControlApi getInstance() {
        if (instance == null) {
            synchronized (DeviceControlApi.class) {
                if (instance == null) {
                    instance = new DeviceControlApi();
                }
            }
        }
        instance.createapi();
        return instance;
    }

    /**
     * 下发设备控制数据
     *
     * @param deviceId 设备ID
     * @param json     设备控制数据
     */
    public Observable<ApiResult<Object>> sendDataToDevice( String deviceId, String json) {
        HetParamsMerge params = new HetParamsMerge();
        params.add(DeviceParamContant.Device.DEVICE_ID, deviceId);
        params.add(DeviceParamContant.Device.JSON, json);
        params.add(DeviceParamContant.Device.SOURCE, "4");
        String path = "/v1/device/config/set";
        String url= GlobalAddr.HOST;
        if (url.contains("dp")||url.contains("50")){
            path="/v1/app/open/device/config/set";
        }

        return api.setDataToServer(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }

    /**
     * 查询设备控制数据
     * @param deviceId 设备ID
     */
    public Observable<ApiResult<Object>>  getConfig(String deviceId) {

        HetParamsMerge params = new HetParamsMerge();
        params.add(DeviceParamContant.Device.DEVICE_ID, deviceId);
        String path = "/v1/device/config/get";
        String url=GlobalAddr.HOST;
        if (url.contains("dp")||url.contains("50")){
            path="/v1/app/open/device/config/get";
        }

        return api.getConfigData(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());

    }

    /**
     * 获取设备运行数据
     *
     * @param deviceId 设备ID
     */
    public Observable<ApiResult<Object>> getRun(String deviceId) {
        HetParamsMerge params = new HetParamsMerge();
        params.add(DeviceParamContant.Device.DEVICE_ID, deviceId);
        String path = "/v1/device/data/get";
        String url=GlobalAddr.HOST;
        if (url.contains("dp")||url.contains("50")){
            path="/v1/app/open/device/data/get";
        }

        return api.getRunData(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());

    }

    /**
     * 获取设备异常数据
     *
     * @param deviceId 设备ID
     */
    public Observable<ApiResult<Object>> getErrorData(String deviceId) {
        HetParamsMerge params = new HetParamsMerge();
        params.add(DeviceParamContant.Device.DEVICE_ID, deviceId);
        String path = "/v1/device/data/getErrorData";
        String url=GlobalAddr.HOST;
        if (url.contains("dp")||url.contains("50")){
            path="/v1/app/open/device/data/getErrorData";
        }

        return api.getErrorData(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }



}
