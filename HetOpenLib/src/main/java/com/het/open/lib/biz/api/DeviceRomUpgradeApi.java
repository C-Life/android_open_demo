package com.het.open.lib.biz.api;

import com.het.basic.base.helper.RxSchedulers;
import com.het.basic.data.api.token.HetParamsMerge;
import com.het.basic.data.http.okhttp.util.OkHttpTag;
import com.het.basic.data.http.retrofit2.RetrofitManager;
import com.het.basic.model.ApiResult;
import com.het.open.lib.biz.services.DeviceRomUpgradeService;
import com.het.open.lib.biz.constans.DeviceParamContant;

import rx.Observable;

/**
 * 设备固件升级相关api
 */
public class DeviceRomUpgradeApi {

    private static DeviceRomUpgradeApi instance = null;
    private DeviceRomUpgradeService api;

    private void createapi() {
        api = RetrofitManager.getRetrofit(new OkHttpTag(this.getClass().getName())).create(DeviceRomUpgradeService .class);
    }

    public static DeviceRomUpgradeApi getInstance() {
        if (instance == null) {
            synchronized (DeviceRomUpgradeApi.class) {
                if (instance == null) {
                    instance = new DeviceRomUpgradeApi();
                }
            }
        }
        instance.createapi();
        return instance;
    }
    /**
     * 检查设备固件版本升级信息
     *
     * @param deviceId 设备标识
     */
    public Observable<ApiResult<Object>>  check(String deviceId) {

        HetParamsMerge params = new HetParamsMerge();
        params.add(DeviceParamContant.Device.DEVICE_ID, deviceId);;
        String path = "v1/upgrade/check";
        return api.check(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());

    }

    /**
     * 确认升级设备固件版本
     *
     * @param deviceId          设备标识
     * @param deviceVersionType 设备版本类型（1-WIFI，2-PCB（目前蓝牙设备、wifi设备都只升级pcb）,3-蓝牙模块升级）
     * @param deviceVersionId   设备固件版本id
     */
    public Observable<ApiResult>  confirmUpgrade(String deviceId, String deviceVersionType, String deviceVersionId) {
        HetParamsMerge params = new HetParamsMerge();
        params.add(DeviceParamContant.Device.DEVICE_ID, deviceId);
        params.add("deviceVersionType", deviceVersionType);;
        params.add("deviceVersionId", deviceVersionId);;
        String path = "v1/upgrade/confirm";
        return api.confirm(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }

    /**
     * 查询设备固件版本升级进度
     *
     * @param deviceId        设备标识
     * @param deviceVersionId 设备版本标识
     */
    public Observable<ApiResult<Object>>   getUpgradeProgres(String deviceId, String deviceVersionId) {
        HetParamsMerge params = new HetParamsMerge();
        params.add(DeviceParamContant.Device.DEVICE_ID, deviceId);
        params.add("deviceVersionId", deviceVersionId);;
        String path = "v1/upgrade/progress";
        return api.progress(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }

    /**
     * 升级成功确认
     *
     * @param deviceId        设备标识
     * @param deviceVersionId 设备版本标识
     */
    public Observable<ApiResult> confirmSuccess(String deviceId, String deviceVersionId) {
        HetParamsMerge params = new HetParamsMerge();
        params.add(DeviceParamContant.Device.DEVICE_ID, deviceId);
        params.add("deviceVersionId", deviceVersionId);;
        String path = "v1/upgrade/confirmSuccess";
        return api.confirmSuccess(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }

}
