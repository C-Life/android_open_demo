package com.het.open.lib.api;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.het.basic.utils.GsonUtil;
import com.het.log.Logc;
import com.het.open.lib.biz.api.DeviceControlApi;
import com.het.open.lib.biz.device.ContextStratrgy;
import com.het.open.lib.biz.device.DeviceSendDataStrategy;
import com.het.open.lib.callback.ICtrlCallback;
import com.het.open.lib.callback.ISendDataStrategyCb;
import com.het.open.lib.callback.IWifiDeviceData;
import com.het.open.lib.callback.OnUpdateInView;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.model.SendPacketData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：wifi设备控制代理类</p>
 * 名称: HetDeviceControlDelegate <br>
 * 作者: uuxia<br>
 * 版本: 1.0<br>
 * 日期: 2016/8/5 15:56<br>
 **/

public class HetDeviceControlDelegate {
    //当前设备
    private DeviceModel device = null;
    //暂停标识，一般activity被暂停了，就用这个变量标记
    private boolean isPause = false;
    //数据状态数据查询间隔时间
    private int intervalUpdateViewDelay = 5 * 1000;
    //主线程句柄
    private Handler mDelivery = new Handler(Looper.getMainLooper());
    //数据回调界面
    private OnUpdateInView onUpdateInView;
    //界面发送控制数据策略
    private ContextStratrgy contextStratrgy;
    private Context context;

    /**
     * 初始化nDevice不能为空，如果设备协议走xml，idc为null，如果不走xml协议，请实现IDataConver接口
     *
     * @param nDevice
     */
    public void onCreate(Context context, DeviceModel nDevice) {
        if (nDevice == null)
            return;
        if (context == null)
            return;
        this.context = context;
        this.device = nDevice;
        if (TextUtils.isEmpty(device.getMacAddress()))
            return;
        /**发送控制数据策略**/
        contextStratrgy = new ContextStratrgy(new DeviceSendDataStrategy(iSendDataStrategyCb));

    }

    /**
     * 初始化wifi设备控制
     */
    public void onStart() {
        if (context == null)
            return;
        if (device != null) {
           // HetWifiDeviceControlApi.getInstance().init(context, iWifiDeviceData);
            HetWifiDeviceControlApi.getInstance().start(device.getDeviceId(),iWifiDeviceData);
        }
    }

    private IWifiDeviceData iWifiDeviceData = new IWifiDeviceData() {
        @Override
        public void onGetConfigData(String jsonData) {
           Logc.d("onGetConfigData: " + jsonData);
            if (onUpdateInView != null && !HetDeviceControlDelegate.this.isPause) {
                onUpdateInView.updateConfig(jsonData);
            }
        }

        @Override
        public void onGetRunData(String jsonData) {
            Logc.d("onGetRunData: " + jsonData);
            if (onUpdateInView != null && !HetDeviceControlDelegate.this.isPause) {
                onUpdateInView.updateRun(jsonData);
            }
        }

        @Override
        public void onGetErrorData(String jsonData) {
            Logc.d("onGetErrorData: " + jsonData);
            if (onUpdateInView != null && !HetDeviceControlDelegate.this.isPause) {
                onUpdateInView.updateWarm(jsonData);
            }
        }

        @Override
        public void onDeviceStatues(int onlineStatus) {
            if (onUpdateInView != null && !HetDeviceControlDelegate.this.isPause) {
                onUpdateInView.updateDeviceStatus(onlineStatus);
            }
        }

        @Override
        public void onDataError(int code, String msg) {
            Logc.e("onDataError: " + msg + " code " + code);
        }
    };

    public void setIntervalUpdateViewDelay(int intervalUpdateViewDelay) {
        this.intervalUpdateViewDelay = intervalUpdateViewDelay;
    }

    public void onResume() {
        Logc.i("### Delegate..onResume " + this);
        isPause = false;
    }

    public void onPause() {
        Logc.i("### Delegate..onPause " + this + " isPause:" + isPause);
        isPause = true;
    }

    public void onDestroy() {
        HetWifiDeviceControlApi.getInstance().stop(this.device.getDeviceId());
    }

    /**
     * 设置界面回调
     *
     * @param onUpdateInView
     */
    public void setOnUpdateInView(OnUpdateInView onUpdateInView) {
        this.onUpdateInView = onUpdateInView;
    }

    /**
     * 设备刷新UI标志
     **/
    private void setUpdateViewStatus(boolean on) {
        if (this.onUpdateInView != null) {
            this.onUpdateInView.setUpdateViewStatus(on);
        }
    }


    /**
     * 发送控制数据
     **/
    public synchronized void send(Map map, ICtrlCallback callback) {
        if (device == null) {
            callback.onFailed(new Exception("device is null."));
            return;
        }
        if (map == null) {
            callback.onFailed(new Exception("map is null."));
            return;
        }
        String json = GsonUtil.getInstance().toJson(map);
        send(json, callback);
    }

    /**
     * 发送控制数据
     *
     * @param json     发送数据格式为json
     * @param callback 发送后反馈状态回调
     */
    public synchronized void send(String json, ICtrlCallback callback) {
        send(json, callback, false);
    }

    /**
     * 发送控制数据
     *
     * @param json     发送数据格式为json
     * @param callback 发送后反馈状态回调
     */
    public synchronized void send(String json, int type, ICtrlCallback callback) {
        send(json, type, callback, false);
    }

    /**
     * 重载发送控制数据
     *
     * @param json              发送数据格式为json
     * @param callback          发送后反馈状态回调
     * @param isDeleyUpdateView 是否在发送数据后5s内不刷UI
     */
    public synchronized void send(String json, ICtrlCallback callback, boolean isDeleyUpdateView) {
        if (device == null) {
            callback.onFailed(new Exception("device is null."));
            return;
        }
        if (TextUtils.isEmpty(json)) {
            callback.onFailed(new Exception("json is null."));
            return;
        }
        if (isDeleyUpdateView) {
            //当发送数据的时候，不应该刷UI
            setUpdateViewStatus(true);
            //延时5s关闭禁止刷UI开关
            postUpdateViewDelay();
        }
        //添加发送数据到过滤器
        contextStratrgy.addFilter(new SendPacketData(json, callback));
    }

    /**
     * 重载发送控制数据
     *
     * @param type              蓝牙数据类型
     * @param json              发送数据格式为json
     * @param callback          发送后反馈状态回调
     * @param isDeleyUpdateView 是否在发送数据后5s内不刷UI
     */
    public synchronized void send(String json, int type, ICtrlCallback callback, boolean isDeleyUpdateView) {
        if (device == null) {
            callback.onFailed(new Exception("device is null."));
            return;
        }
        if (TextUtils.isEmpty(json)) {
            callback.onFailed(new Exception("json is null."));
            return;
        }
        if (isDeleyUpdateView) {
            //当发送数据的时候，不应该刷UI
            setUpdateViewStatus(true);
            //延时5s关闭禁止刷UI开关
            postUpdateViewDelay();
        }
        //添加发送数据到过滤器
        contextStratrgy.addFilter((new SendPacketData(json, callback)));
    }

    /**
     * 重载发送控制数据
     *
     * @param type              蓝牙数据类型
     * @param callback          发送后反馈状态回调
     * @param isDeleyUpdateView 是否在发送数据后5s内不刷UI
     */
    public synchronized void send(int type, byte[] data, ICtrlCallback callback, boolean isDeleyUpdateView) {
        if (device == null) {
            callback.onFailed(new Exception("device is null."));
            return;
        }
        if (isDeleyUpdateView) {
            //当发送数据的时候，不应该刷UI
            setUpdateViewStatus(true);
            //延时5s关闭禁止刷UI开关
            postUpdateViewDelay();
        }
        //添加发送数据到过滤器
        contextStratrgy.addFilter(new SendPacketData(null, callback).setType(type).setData(data));
    }

    /**
     * 通知回调，发送数据
     **/
    ISendDataStrategyCb iSendDataStrategyCb = new ISendDataStrategyCb<SendPacketData>() {
        @Override
        public void filterData(SendPacketData packetData) {
            send(packetData);
        }
    };


    private void send(SendPacketData packetData) {
        if (packetData == null) {
            return;
        }
        String json = packetData.getJson();
        ICtrlCallback callback = packetData.getCallback();
        if (device == null) {
            if (callback != null) {
                callback.onFailed(new Exception("device is null."));
            }
            return;
        }

        if (TextUtils.isEmpty(json)) {
            if (callback != null) {
                callback.onFailed(new Exception("json is null."));
            }
            return;
        }

        Logc.i("大循环发送数据");
        if (json.contains("updateTime")){
            JSONObject jsonObject= null;
            try {
                jsonObject = new JSONObject(json);
                if (jsonObject.has("updateTime")){
                    jsonObject.remove("updateTime");
                }
                json = jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        DeviceControlApi.getInstance().sendDataToDevice(device.getDeviceId(), json).
                subscribe(s -> {
                    if (s != null) {
                        if (callback != null) {
                            if (s.getCode() == 0) {
                                Logc.i("大循环发送数据 成功");
                                callback.onSucess();
                            } else if (s.getCode() == 100022000) {
                                HetDeviceControlDelegate.this.noThisDeviceError(s.getData() != null ? s.getData().toString() : null);
                                callback.onFailed(new Exception(s.getMsg()));
                            } else {
                                Logc.e("大循环发送数据 失败");
                                callback.onFailed(new Exception(s.getMsg()));
                            }
                        }
                    }
                }, e -> {
                    Logc.e("大循环发送数据 失败");
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onFailed(e);
                    }
                });

    }


    private void noThisDeviceError(String var1) {
        this.onPause();
        if (this.onUpdateInView != null) {
            this.onUpdateInView.onNoThisDeviceError(var1);
        }

    }

    /**
     * 延时5s设置UpdateViewFlag标志
     **/
    private void postUpdateViewDelay() {
        mDelivery.removeCallbacks(updateViewFlagRunnable);
        mDelivery.postDelayed(updateViewFlagRunnable, intervalUpdateViewDelay);
    }

    private Runnable updateViewFlagRunnable = () -> setUpdateViewStatus(false);
}
