package com.het.sdk.demo.ui.activity.h5control;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.het.basic.base.RxManage;
import com.het.bluetoothbase.utils.HexUtil;
import com.het.bluetoothoperate.manager.BluetoothDeviceManager;
import com.het.bluetoothoperate.mode.CmdIndexConstant;
import com.het.bluetoothoperate.proxy.IHetHistoryListener;
import com.het.h5.sdk.base.H5BaseBleControlActivity;
import com.het.h5.sdk.bean.H5PackParamBean;
import com.het.h5.sdk.callback.IH5ArchieveInterface;
import com.het.h5.sdk.callback.IH5BleCallBack;
import com.het.h5.sdk.callback.IH5BleHistroyCallBack;
import com.het.h5.sdk.callback.IMethodCallBack;
import com.het.h5.sdk.utils.H5VersionUtil;
import com.het.log.Logc;
import com.het.open.lib.callback.ICtrlCallback;
import com.het.open.lib.callback.OnUpdateBleDataInView;
import com.het.open.lib.callback.OnUpdateBleDataInViewImpl;
import com.het.open.lib.control.BleControlDelegate;
import com.het.open.lib.model.BleConfig;
import com.het.sdk.demo.ui.activity.device.DeviceDetailActivity;
import com.het.xml.protocol.ProtocolManager;

import java.io.UnsupportedEncodingException;
import java.util.TreeMap;


/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>蓝牙设备h5控制界面容器(3a标准协议：</p>
 * 名称:  <br>
 * 作者: created by xuchao(80010814) <br>
 * 日期: 2018/4/24<br>
 **/

public class H5ComBle3AControlActivity extends H5BaseBleControlActivity {
    private final String TAG = H5ComBle3AControlActivity.class.getSimpleName();
    /**
     * 蓝牙控制代理类
     */
    private BleControlDelegate deviceControlDelegate;
    private BleConfig bleConfig;
    private IH5BleCallBack ih5BleCallBack;
    private IH5BleHistroyCallBack curh5BleHistroyCallBack;
    private int newestStatus = 0;//最新的蓝牙连接状态，在H5加载完成时把状态传给H5
    private String bleStatusString;
    private String configString;

    public static void startH5Ble3AControlActivity(Context context, H5PackParamBean h5PackParamBean) {
        Intent intent = new Intent(context, H5ComBle3AControlActivity.class);
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

                String configData = ProtocolManager.getInstance().getDeviceJson(deviceBean.getProductId(),(short) CmdIndexConstant.HET_COMMAND_CONFIG_DATA_APP);
                Logc.i("protocol config :" + configData);
                Logc.i("config :" + configString);
                Logc.i("status data :" + bleStatusString);
                Logc.i("newestStatus :" + newestStatus);
                //初始化数据
                if (h5BridgeManager == null) return;
                //初始化控制数据
                if (!TextUtils.isEmpty(configString) || !TextUtils.isEmpty(configData)) {
                    h5BridgeManager.updateConfigData(!TextUtils.isEmpty(configString) ? configString : configData);
                }
                //初始化状态数据
                if (!TextUtils.isEmpty(bleStatusString)) {
                    sendBLEStatusData(bleStatusString);
                }
                //初始化状态
                sendBleState(newestStatus);
            }
        });

        super.onCreate(savedInstanceState);
        RxManage.getInstance().register("Qr_device_url", url -> {
            this.h5BridgeManager.loadUrl((String) url);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxManage.getInstance().unregister("Qr_device_url");
    }

    OnUpdateBleDataInView onUpdateBleDataInView = new OnUpdateBleDataInViewImpl() {
        @Override
        protected void onUpdateData(int type, byte[] data) {
            Logc.d(TAG, "xml:----type " + type + "xml :=== data  " + HexUtil.encodeHexStr(data));
            String dataJson = null;
            if (type == CmdIndexConstant.HET_COMMAND_GET_TIME_APP) {
                try {
                    dataJson = new String(data, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    Logc.e(TAG, e.toString());
                }
                if (ih5BleCallBack != null) {
                    ih5BleCallBack.onSucess(dataJson);
                }
            }
        }

        @Override
        protected void onUpdateJson(int type, String json) {
            Logc.d(TAG, ":----type " + type + "xml :=== json  " + json);
            if (!TextUtils.isEmpty(json)) {
                //在开放平台配置蓝牙xml数据，回调协议解析后的json数据给
                if (type == CmdIndexConstant.HET_COMMAND_GET_REAL_TIME_DATA_DEV) {
                    //实时数据
                    if (ih5BleCallBack != null) {
                        Logc.d(TAG, "上报jiso:" + json);
                        ih5BleCallBack.onSucess(json);
                    }
                    //上传实时数据
                    updataRealData(json);
                    //将实时数据当做控制指令下发到H5
                    configString = json;
                    h5BridgeManager.updateConfigData(json);
                } else if (type == CmdIndexConstant.HET_COMMAND_RUN_DATA_DEV) {
                    bleStatusString = json;
                    //状态数据下发到H5
                    sendBLEStatusData(json);
                    //将状态数据当做控制指令下发到H5
                    h5BridgeManager.updateConfigData(json);
                }else if (type == CmdIndexConstant.HET_COMMAND_CONFIG_DATA_DEV){
                    //控制数据
                    configString = json;
                    h5BridgeManager.updateConfigData(json);
                }
            }
        }

        @Override
        protected void onPowerChange(int power) {
            super.onPowerChange(power);
            Logc.d(TAG, ":----power " + power);
            //上报蓝牙设备电量
            if (power > -1 && power <= 100) {
                sendBLEPower(power);
            }
        }

        @Override
        protected void onDeviceError(String error) {
            super.onDeviceError(error);
            Logc.e(TAG, ":----error " + error);
            //上报蓝牙设备电量
//            ToastUtil.showToast(mContext,error);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void send(String data, IMethodCallBack methodCallBack) {
        if (deviceControlDelegate != null) {
            deviceControlDelegate.send(CmdIndexConstant.HET_COMMAND_CONFIG_DATA_APP, data, iCtrlCallback, false);
        }
    }

    private synchronized void updataRealData(String json) {
        if (deviceBean != null) {
            String path = "/v1/device/data/common/upload";
            String deviceId = deviceBean.getDeviceId();
            TreeMap<String, String> params = new TreeMap<>();
            params.put("deviceId", deviceId);
            params.put("json", json);
            params.put("dataType", "2");
            params.put("protocolVersion", "");
            if (h5BridgeManager != null) {
                h5BridgeManager.relNativeProxyHttp(path, params, 0, 1, new IMethodCallBack() {
                    @Override
                    public void onSucess(int i, Object o) {
                        Logc.d(TAG, i + "");
                    }

                    @Override
                    public void onFailed(int i, Object o) {
                        Logc.e(TAG, i + "");
                    }
                });
            }
        }
    }

    @Override
    protected void initBleManagerCom() {
        BluetoothDeviceManager.getInstance().init(this);
        deviceControlDelegate = new BleControlDelegate();
        bleConfig = new BleConfig();
        bleConfig.setDeviceBean(deviceBean);
        bleConfig.setConnectTimeOut(5);
        bleConfig.setConnectStatusCallback(i -> {
            Logc.d(TAG + "connect_status", i + "");
            newestStatus = i;
            sendBleState(i);
        });
        deviceControlDelegate.onCreate(bleConfig);
        deviceControlDelegate.setOnUpdateBleDataInView(onUpdateBleDataInView);
    }

    @Override
    public void initControlData() {
        super.initControlData();
        //在H5页面显示后把最新的蓝牙连接状态给H5，避免在蓝牙完成初始化后可H5没加载完成便收不到蓝牙连接状态
        sendBleState(newestStatus);
    }

    @Override
    protected void destroyBleManagerCom() {
        if (ih5BleCallBack != null) {
            ih5BleCallBack = null;
        }
        if (deviceControlDelegate != null) {
            deviceControlDelegate.onDestroy();
            deviceControlDelegate = null;
        }
    }

    @Override
    protected void getBLERealTimeDataCom(IH5BleCallBack ih5CallBack) {
        ih5BleCallBack = ih5CallBack;
        Logc.d(TAG, "getBLERealTimeData");

        if (deviceControlDelegate != null) {
            deviceControlDelegate.send(CmdIndexConstant.HET_COMMAND_GET_REAL_TIME_DATA_APP, null, iCtrlCallback, false);
        }
    }

    @Override
    protected void getBLETimeDataCom(IH5BleCallBack ih5CallBack) {
        Logc.d(TAG, "getBLETimeData");
        ih5BleCallBack = ih5CallBack;
        if (deviceControlDelegate != null) {
            deviceControlDelegate.send(CmdIndexConstant.HET_COMMAND_GET_TIME_APP, null, iCtrlCallback, false);
        }
    }

    /**
     * 同步BLE时间
     *
     * @param ih5CallBack H5回调
     */
    @Override
    protected void setBLETimeDataCom(IH5BleCallBack ih5CallBack) {
        Logc.d(TAG, "setBLETimeDataCom");
        ih5BleCallBack = ih5CallBack;
        if (deviceControlDelegate != null) {
            deviceControlDelegate.send(CmdIndexConstant.HET_COMMAND_SET_TIME_APP, new byte[]{0}, iCtrlCallback, false);
        }
    }

    @Override
    protected void getBLEHistoryDataCom(IH5BleHistroyCallBack ih5BleHistroyCallBack) {
        Logc.d(TAG, "getBLEHistoryData");
        this.curh5BleHistroyCallBack = ih5BleHistroyCallBack;
        if (deviceControlDelegate != null) {
            getHistroyData();
        }

    }

    private void getHistroyData() {
        Logc.d(TAG, "getHistroyData");
        deviceControlDelegate.send(CmdIndexConstant.HET_COMMAND_GET_HISTORY_DATA_APP, new IHetHistoryListener() {
            @Override
            public void receiveFinish(byte[] bytes) {
                if (curh5BleHistroyCallBack != null) {
                    curh5BleHistroyCallBack.onSucess(bytes);
                }
            }

            @Override
            public void onFail(String s) {
                if (curh5BleHistroyCallBack != null) {
                    curh5BleHistroyCallBack.onFailed(s);
                }
            }

            @Override
            public void onProgress(int i) {
                if (i >= 0 && i <= 100) {
                    if (curh5BleHistroyCallBack != null) {
                        curh5BleHistroyCallBack.onProgess(i);
                    }
                }

            }
        }, iCtrlCallback, false);
    }

    @Override
    protected void clearBleHistroyDateCom() {

    }


    private ICtrlCallback iCtrlCallback = new ICtrlCallback() {
        @Override
        public void onSucess() {
            Logc.d(TAG, "success");
            if (ih5BleCallBack != null) {
                ih5BleCallBack.onSucess(null);
            }
        }

        @Override
        public void onFailed(Throwable throwable) {
            Logc.d(TAG + "onFailed:", throwable.getMessage());
            String errMsg = throwable.getMessage();
            if (!TextUtils.isEmpty(errMsg)) {
                if (ih5BleCallBack != null) {
                    ih5BleCallBack.onFailed(errMsg);
                }
            }
        }

        @Override
        public void onProtocolError(Throwable throwable) {
            Logc.d(TAG + "onProtocolError", throwable.getMessage());
            String errMsg = throwable.getMessage();
            if (!TextUtils.isEmpty(errMsg)) {
                if (ih5BleCallBack != null) {
                    ih5BleCallBack.onFailed(errMsg);
                }
            }
        }
    };


    @Override
    public void onRightClick() {
        DeviceDetailActivity.startDeviceDetailActivity(mContext, deviceBean);
    }
}
