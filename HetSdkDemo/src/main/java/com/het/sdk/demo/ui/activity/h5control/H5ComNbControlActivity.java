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
import com.het.open.lib.api.HetNbDeviceControlApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.callback.IWifiDeviceData;
import com.het.sdk.demo.ui.activity.device.DeviceDetailActivity;


/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>nb设备控制逻辑：</p>
 * 名称:  <br>
 * 作者: created by xuchao(80010814) <br>
 * 日期: 2018/4/24<br>
 **/

public class H5ComNbControlActivity extends H5CommonBaseControlActivity {
    private final String TAG = H5ComNbControlActivity.class.getSimpleName();


    public static void startH5ComNbControlActivity(Context context, DeviceBean deviceBean) {
        Intent intent = new Intent(context, H5ComNbControlActivity.class);
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

        HetNbDeviceControlApi.getInstance().start(deviceBean.getDeviceId(), iWifiDeviceData);
    }

    @Override
    protected void send(String data, IMethodCallBack iMethodCallBack) {
        Logc.d(TAG, data);
        if (!TextUtils.isEmpty(data)) {
            HetNbDeviceControlApi.getInstance().sendOfflineDataToDevice(new IHetCallback() {
                @Override
                public void onSuccess(int code, String msg) {
                    iMethodCallBack.onSucess(code, msg);
                }

                @Override
                public void onFailed(int code, String msg) {
                    iMethodCallBack.onFailed(code, msg);
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
        HetNbDeviceControlApi.getInstance().stop(deviceBean.getDeviceId());
        RxManage.getInstance().unregister("Qr_device_url");
    }


    @Override
    public void onRightClick() {
        DeviceDetailActivity.startDeviceDetailActivity(mContext, deviceBean);
    }

    IWifiDeviceData iWifiDeviceData = new IWifiDeviceData() {
        @Override
        public void onGetConfigData(String jsonData) {
            Logc.d("onGetConfigData: ", jsonData);
            if (h5BridgeManager != null) {
                h5BridgeManager.updateConfigData(jsonData);
            }
        }

        @Override
        public void onGetRunData(String jsonData) {
            Logc.d("onGetRunData: ", jsonData);
            if (h5BridgeManager != null) {
                h5BridgeManager.updateRunData(jsonData);
            }
        }

        @Override
        public void onGetErrorData(String jsonData) {
            Logc.d("onGetErrorData: " + jsonData);
            if (h5BridgeManager != null) {
                h5BridgeManager.updateConfigData(jsonData);
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
