package com.het.sdk.demo.ui.activity.h5control;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.het.basic.base.RxManage;
import com.het.basic.model.DeviceBean;
import com.het.h5.sdk.base.H5CommonBaseControlActivity;
import com.het.h5.sdk.callback.IMethodCallBack;
import com.het.h5.sdk.utils.H5VersionUtil;
import com.het.log.Logc;
import com.het.open.lib.api.HetZigbeeDeviceControlApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.callback.IWifiDeviceData;
import com.het.sdk.demo.ui.activity.device.DeviceDetailActivity;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>zigbee设备控制逻辑：</p>
 * 名称:  <br>
 * 作者: created by xuchao(80010814) <br>
 * 日期: 2018/4/24<br>
 **/


public class H5ComZigbeeControlActivity extends H5CommonBaseControlActivity {
    private final String TAG = H5ComZigbeeControlActivity.class.getSimpleName();


    public static void startH5ComZigbeeControlActivity(Context context, DeviceBean deviceBean) {
        Intent intent = new Intent(context, H5ComZigbeeControlActivity.class);
        intent.putExtra(H5VersionUtil.DEVICE_BEAN, deviceBean);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxManage.getInstance().register("Qr_device_url", url -> {
            this.h5BridgeManager.loadUrl((String) url);
        });

    }


    @Override
    protected void initControlData() {

        HetZigbeeDeviceControlApi.getInstance().start(deviceBean.getDeviceId(), iWifiDeviceData);
    }

    @Override
    protected void send(String data, IMethodCallBack iMethodCallBack) {
        Logc.d(TAG, data);
        if (!TextUtils.isEmpty(data)) {
            HetZigbeeDeviceControlApi.getInstance().sendDataToDevice(new IHetCallback() {
                @Override
                public void onSuccess(int i, String s) {
                    iMethodCallBack.onSucess(i, s);
                }

                @Override
                public void onFailed(int i, String s) {
                    iMethodCallBack.onSucess(i, s);
                }
            }, deviceBean.getDeviceId(), data);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HetZigbeeDeviceControlApi.getInstance().stop(deviceBean.getDeviceId());
        RxManage.getInstance().unregister("Qr_device_url");
    }


    @Override
    public void onRightClick() {
        DeviceDetailActivity.startDeviceDetailActivity(mContext, deviceBean);
    }

    private IWifiDeviceData iWifiDeviceData = new IWifiDeviceData() {
        @Override
        public void onGetConfigData(String jsonData) {
            Logc.d("onGetConfigData: ", jsonData);
            if (!TextUtils.isEmpty(jsonData)) {
                if (h5BridgeManager != null) {
                    h5BridgeManager.updateConfigData(jsonData);
                }
            }

        }

        @Override
        public void onGetRunData(String jsonData) {
            Logc.d("onGetRunData: ", jsonData);
            if (!TextUtils.isEmpty(jsonData)) {
                if (h5BridgeManager != null) {
                    h5BridgeManager.updateConfigData(jsonData);
                }
            }

        }

        @Override
        public void onGetErrorData(String jsonData) {
            Logc.d("onGetErrorData: " + jsonData);
            if (!TextUtils.isEmpty(jsonData)) {
                if (h5BridgeManager != null) {
                    h5BridgeManager.updateErrorData(jsonData);
                }
            }

        }

        @Override
        public void onDeviceStatues(int onlineStatus) {
            if (h5BridgeManager != null) {
                h5BridgeManager.updateDeviceState(onlineStatus);
            }
        }

        @Override
        public void onDataError(int code, String msg) {
            Logc.d("onDataError: " + msg + " code " + code);
        }
    };


}
