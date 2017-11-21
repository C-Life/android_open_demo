package com.het.open.lib.biz.deal;

import com.het.basic.base.RxManage;
import com.het.basic.data.http.retrofit2.exception.ApiException;
import com.het.basic.model.ApiResult;
import com.het.basic.utils.GsonUtil;
import com.het.log.Logc;
import com.het.mqtt.sdk.bean.MqttMsgData;
import com.het.mqtt.sdk.constants.HetMqttConstant;
import com.het.mqtt.sdk.manager.HetMqttManager;
import com.het.open.lib.api.HetCodeConstants;
import com.het.open.lib.biz.api.DeviceControlApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.callback.IWifiDeviceData;

import rx.functions.Action1;


/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  设备列表api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class DeviceWifiControlDeal {
    private final String TAG=DeviceWifiControlDeal.class.getSimpleName();
    private static DeviceWifiControlDeal mInstance;
    private IWifiDeviceData iWifiDeviceData;
    private int lastCode = -1;
    private String rxMsgTopic;


    private DeviceWifiControlDeal() {
    }


    public static DeviceWifiControlDeal getInstance() {
        if (mInstance == null) {
            synchronized (DeviceWifiControlDeal.class) {
                if (mInstance == null) mInstance = new DeviceWifiControlDeal();
            }
        }
        return mInstance;
    }



    /**
     * 开始接收数据
     *
     * @param deviceId 设备id
     */
    public void start(final String deviceId,IWifiDeviceData iWifiDeviceData) {
        this.iWifiDeviceData=iWifiDeviceData;
        getDataFromServer(deviceId);
        HetMqttManager.getInstances().registerDevice(deviceId, HetMqttConstant.TYPE_DEVICE_CONTROL);
        rxMsgTopic=deviceId+HetMqttConstant.TYPE_DEVICE_CONTROL;
        registerRxMsg();
    }

    private void getDataFromServer(String deviceId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getRunData(deviceId);
                getConfigData(deviceId);
                getErrorData(deviceId);
            }
        }).start();

    }

    /**
     * 停止接收数据
     */
    public void stop(final String deviceId) {
        HetMqttManager.getInstances().unRegisterDevice(deviceId,HetMqttConstant.TYPE_DEVICE_CONTROL);
        if (rxMsgTopic!=null){
            unRegisterRxMsg();
        }
    }

    /**
     * 获取异常数据
     */
    public void getErrorData(String deviceId) {
        DeviceControlApi.getInstance().getErrorData(deviceId).subscribe(new Action1<ApiResult<Object>>() {
            @Override
            public void call(ApiResult<Object> objectApiResult) {
                if (objectApiResult != null) {
                    if (objectApiResult.getCode() == 0) {
                        Object data = objectApiResult.getData();
                        if (data!=null){
                            String json=GsonUtil.getInstance().getGson().toJson(data).toString();
                            iWifiDeviceData.onGetErrorData(json != null ? json : null);
                        }

                    } else {
                        dealGetDataError(objectApiResult);
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (throwable instanceof ApiException) {
                    iWifiDeviceData.onDataError(((ApiException) throwable).getCode(), throwable.getMessage());
                }
            }
        });


    }

    /**
     * 获取运行数据
     */
    public void getRunData(String deviceId) {
        DeviceControlApi.getInstance().getRun(deviceId).subscribe(new Action1<ApiResult<Object>>() {
            @Override
            public void call(ApiResult<Object> objectApiResult) {
                if (objectApiResult != null) {
                    if (objectApiResult.getCode() == 0) {
                        Object data = objectApiResult.getData();
                        if (data!=null){
                            String json=GsonUtil.getInstance().getGson().toJson(data).toString();
                            iWifiDeviceData.onGetRunData(json != null ? json : null);
                        }
                    } else {
                        dealGetDataError(objectApiResult);
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (throwable instanceof ApiException) {
                    iWifiDeviceData.onDataError(((ApiException) throwable).getCode(), throwable.getMessage());
                }
            }
        });


    }

    /**
     * 获取配置数据
     */
    public void getConfigData(String deviceId) {
        DeviceControlApi.getInstance().getConfig(deviceId).subscribe(new Action1<ApiResult<Object>>() {
            @Override
            public void call(ApiResult<Object> objectApiResult) {
                if (objectApiResult != null) {
                    if (objectApiResult.getCode() == 0) {
                        Object data = objectApiResult.getData();
                        if (data!=null){
                            String json=GsonUtil.getInstance().getGson().toJson(data).toString();
                            iWifiDeviceData.onGetConfigData(json != null ? json : null);
                        }
                    } else {
                        dealGetDataError(objectApiResult);
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (throwable instanceof ApiException) {
                    iWifiDeviceData.onDataError(((ApiException) throwable).getCode(), throwable.getMessage());
                }
            }
        });


    }

    /**
     * 下发数据
     *
     * @param callback
     */
    public void sendDataToDevice(final IHetCallback callback, String deviceId, String json) {
        DeviceControlApi.getInstance().sendDataToDevice(deviceId, json).subscribe(new Action1<ApiResult<Object>>() {
            @Override
            public void call(ApiResult<Object> objectApiResult) {
                if (objectApiResult != null) {
                    if (objectApiResult.getCode() == 0) {
                        Object data = objectApiResult.getData();
                        if (callback != null) {
                            callback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS, data != null ? data.toString() : null);
                        }
                    } else {
                        int code = objectApiResult.getCode();
                        String msg = objectApiResult.getMsg();
                        if (callback != null) {
                            callback.onFailed(code, msg);
                        }
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (throwable instanceof ApiException) {
                    if (callback != null) {
                        callback.onFailed(((ApiException) throwable).getCode(), throwable.getMessage());
                    }
                }
            }
        });
    }

    /**
     * 统一处理拉取数据异常
     */
    private void dealGetDataError(ApiResult<Object> objectApiResult) {
        int code = objectApiResult.getCode();
        String msg = objectApiResult.getMsg();
        if (code == 100022006) {
            //设备不在线
            if (code != lastCode) {
                iWifiDeviceData.onDeviceStatues(2);
            }
        } else {
            if (code != lastCode) {
                iWifiDeviceData.onDataError(code, msg);
            }
        }
        lastCode = code;

    }

    private void registerRxMsg(){

            RxManage.getInstance().register(rxMsgTopic, new Action1<Object>() {
                @Override
                public void call(Object o) {
                    if (o!=null){
                        MqttMsgData mqttMsgData=(MqttMsgData)o;
                        int code=mqttMsgData.getCode();
                        if (code== HetMqttConstant.DEVICE_MQTT_CONFIG_DATA){
                            String jsonConfig=mqttMsgData.getJsonData();
                            Logc.d(TAG+"控制数据",jsonConfig);
                              iWifiDeviceData.onGetConfigData(jsonConfig);
                        }else if  (code== HetMqttConstant.DEVICE_MQTT_RUN_DATA){
                            String jsonRun=mqttMsgData.getJsonData();
                            Logc.d(TAG+"运行数据",jsonRun);
                             iWifiDeviceData.onGetRunData(jsonRun);
                        }else if  (code== HetMqttConstant.DEVICE_MQTT_ERROR_DATA){
                            String jsonError=mqttMsgData.getJsonData();
                            Logc.d(TAG+"异常数据", jsonError);
                             iWifiDeviceData.onGetErrorData(jsonError);
                        }else if  (code== HetMqttConstant.DEVICE_MQTT_ONLINE_STATUS){
                            String json=mqttMsgData.getJsonData();
                            if (json.contains("onlineStatus=1")){
                                iWifiDeviceData.onDeviceStatues(1);
                            }else if (json.contains("onlineStatus=2")){
                                  iWifiDeviceData.onDeviceStatues(2);
                            }
                        }
                        else {
                             iWifiDeviceData.onDataError(code,mqttMsgData.getErrMsg());
                        }


                    }
                }
            });


    }

    private void unRegisterRxMsg(){
            RxManage.getInstance().unregister(rxMsgTopic);

    }


}
