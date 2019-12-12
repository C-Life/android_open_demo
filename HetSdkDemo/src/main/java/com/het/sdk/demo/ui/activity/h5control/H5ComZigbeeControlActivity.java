package com.het.sdk.demo.ui.activity.h5control;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.het.basic.base.RxManage;
import com.het.h5.sdk.base.H5CommonBaseControlActivity;
import com.het.h5.sdk.bean.H5PackParamBean;
import com.het.h5.sdk.callback.IH5ArchieveInterface;
import com.het.h5.sdk.callback.IMethodCallBack;
import com.het.h5.sdk.utils.H5VersionUtil;
import com.het.log.Logc;
import com.het.open.lib.api.AbsDeviceControl;
import com.het.open.lib.api.DeviceContolStrategyApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.callback.IWifiDeviceData;
import com.het.sdk.demo.ui.activity.device.DeviceDetailActivity;
import com.het.xml.protocol.ProtocolManager;

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
    private String configString;
    private String runString;
    private int onlineStatusString = 0;
    private AbsDeviceControl absDeviceControl;

    public static void startH5ComZigbeeControlActivity(Context context, H5PackParamBean h5PackParamBean) {
        Intent intent = new Intent(context, H5ComZigbeeControlActivity.class);
        intent.putExtra(H5VersionUtil.H5_PACK_PARAM_BEAN, h5PackParamBean);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setIh5ArchieveInterfaceImpl(new IH5ArchieveInterface() {
            @Override
            public void sendData(String data, IMethodCallBack methodCallBack) {
                send(data, methodCallBack);
            }

            @Override
            public void onWebViewShow() {
                initControlData();
            }

            @Override
            public void onWebViewCreate() {
                //设置H5导航栏的高度
                sendNavigationBarHeight();

                String runData = ProtocolManager.getInstance().getRunJson(deviceBean.getProductId());
                String configData = ProtocolManager.getInstance().getConfigJson(deviceBean.getProductId());
                //初始化数据
                if (h5BridgeManager == null) return;
                if (!TextUtils.isEmpty(configString) || !TextUtils.isEmpty(configData)) {
                    h5BridgeManager.updateConfigData(!TextUtils.isEmpty(configString) ? configString : configData);
                }
                if (!TextUtils.isEmpty(runString) || !TextUtils.isEmpty(runData)) {
                    h5BridgeManager.updateRunData(!TextUtils.isEmpty(runString) ? runString : runData);
                }
                h5BridgeManager.updateDeviceState(onlineStatusString);
            }
        });
        super.onCreate(savedInstanceState);
        RxManage.getInstance().register("Qr_device_url", url -> {
            this.h5BridgeManager.loadUrl((String) url);
        });
    }


    @Override
    protected void initControlData() {

        if (deviceBean.getModuleId() == 190) {
            absDeviceControl = DeviceContolStrategyApi.getInstance().provideDeviceControl(DeviceContolStrategyApi.DeviceType.ZIGBEE3);
        } else {
            absDeviceControl = DeviceContolStrategyApi.getInstance().provideDeviceControl(DeviceContolStrategyApi.DeviceType.ZIGBEE2);
        }

        if (absDeviceControl != null) {
            absDeviceControl.start(deviceBean, iWifiDeviceData);
        }
    }

    @Override
    protected void send(String data, IMethodCallBack iMethodCallBack) {
        Logc.d(TAG, data);
        if (!TextUtils.isEmpty(data) && absDeviceControl != null) {
            absDeviceControl.send(new IHetCallback() {
                @Override
                public void onSuccess(int i, String s) {
                    iMethodCallBack.onSucess(i, s);
                }

                @Override
                public void onFailed(int i, String s) {
                    iMethodCallBack.onSucess(i, s);
                }
            }, deviceBean, data);

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
        if (deviceBean != null && absDeviceControl != null){
            absDeviceControl.stop(deviceBean.getDeviceId());
        }
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
                configString = jsonData;
                if (h5BridgeManager != null) {
                    h5BridgeManager.updateConfigData(jsonData);
                }
            }

        }

        @Override
        public void onGetRunData(String jsonData) {
            Logc.d("onGetRunData: ", jsonData);
            if (!TextUtils.isEmpty(jsonData)) {
                runString = jsonData;
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
            onlineStatusString = onlineStatus;
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
