package com.het.open.lib.biz.bind;

import android.app.Activity;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.het.basic.data.api.token.TokenManager;
import com.het.basic.data.http.retrofit2.exception.ApiException;
import com.het.basic.model.DeviceBean;
import com.het.basic.utils.GsonUtil;
import com.het.bind.logic.HeTBindApi;
import com.het.bind.logic.bean.device.DeviceProductBean;
import com.het.bind.logic.sdk.callback.OnBindListener;
import com.het.bind.logic.sdk.callback.OnScanListener;
import com.het.bluetoothoperate.manager.BluetoothDeviceManager;
import com.het.log.Logc;
import com.het.open.lib.api.HetCodeConstants;
import com.het.open.lib.biz.api.DeviceGetListApi;
import com.het.open.lib.biz.constans.HetWifibindConstant;
import com.het.open.lib.callback.ICommonBleBind;
import com.het.open.lib.model.DeviceModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;


/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p</p>
 * 名称:  蓝牙绑定逻辑类<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetBleBind {

    private final String TAG = "蓝牙绑定逻辑类<";
    private static HetBleBind mInstance;
    private DeviceProductBean curDeviceModel;  //要绑定设备类想
    private ICommonBleBind iCommonBleBind;
    private Activity activity;


    private HetBleBind() {

    }

    public static HetBleBind getInstance() {
        if (mInstance == null) {
            synchronized (HetBleBind.class) {
                if (mInstance == null) mInstance = new HetBleBind();
            }
        }
        return mInstance;
    }



  




    /**
     * 开始绑定设备
     *
     * @param productId    产品id
     * @param iBleBind    绑定接口回调
     */
    public void startBind(Activity activity, String productId, final ICommonBleBind iBleBind) {
        this.activity=activity;
        this.iCommonBleBind=iBleBind;
        curDeviceModel=new DeviceProductBean();
        HeTBindApi.getInstance().init();
        BluetoothDeviceManager.getInstance().init(activity);
        if (!TokenManager.getInstance().isLogin()){
            if (iBleBind != null) {
                iBleBind.onFailed(HetWifibindConstant.ERROR_SCAN_NO_DEVICE, HetWifibindConstant.MSG_SCAN_NO_DEVICE_LOGIN);
            }
        }else {
            DeviceGetListApi.getInstance().getProduct(productId).subscribe(new Action1<DeviceBean>() {
                @Override
                public void call(DeviceBean deviceBean) {
                    try {
                        if (deviceBean != null) {
                            curDeviceModel=enerateDeviceProductBean(deviceBean);
                            doScan();
                        }else {
                            if (iCommonBleBind != null) {
                                iCommonBleBind.onFailed(HetWifibindConstant.ERROR_DEVICE_NO_BIND, HetWifibindConstant.MSG_ERROR_DEVICE_NO_BIND);
                            }
                        }
                    } catch (Exception e) {
                       Logc.e(TAG,e.toString());
                    }

                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    if (throwable instanceof ApiException){
                        Logc.e(TAG,((ApiException) throwable).getCode()+throwable.getMessage());
                    }
                    if (iCommonBleBind != null) {
                        iCommonBleBind.onFailed(HetWifibindConstant.ERROR_DEVICE_NO_BIND, HetWifibindConstant.MSG_ERROR_DEVICE_NO_BIND);
                    }
                }
            });
        }



    }



    public DeviceProductBean enerateDeviceProductBean(DeviceBean deviceBean){
        DeviceProductBean deviceProductBean=new DeviceProductBean();
        try {
            deviceProductBean.setDeviceTypeId(deviceBean.getDeviceTypeId());
            deviceProductBean.setDeviceSubtypeId(deviceBean.getDeviceSubtypeId());
            deviceProductBean.setModuleId(deviceBean.getModuleId());
            deviceProductBean.setProductId(deviceBean.getProductId());
            deviceProductBean.setBrandId(deviceBean.getDeviceBrandId());
        } catch (Exception e) {
            deviceProductBean=null;
            Logc.e(TAG,e.toString());

        }
        return deviceProductBean;
    }

    /**
     * 结束扫描绑定
     */
    public void stopBind() {
        HeTBindApi.getInstance().getBindApi().stopScan();
    }


    /**
     * 开始扫描设备
     */
    private void doScan () {
        HeTBindApi.getInstance().getBindApi().setOnBindListener(onBindListener);
        HeTBindApi.getInstance().getBindApi().setOnScanListener(onScanListener);
        HeTBindApi.getInstance().getBindApi().startScan(activity, curDeviceModel, null);


    }

    /**
     * 开始绑定设备
     * @param deviceModel
     */
    public void doBind(DeviceProductBean deviceModel){
        try {
            HeTBindApi.getInstance().getBindApi().bind(deviceModel);
        } catch (Exception e) {
            Logc.e(TAG,e.toString());
            if (iCommonBleBind!= null) {
                iCommonBleBind.onFailed(HetWifibindConstant.ERROR_BIND_DEVICE, HetWifibindConstant.MSG_ERROR);
            }
        }
    }

    private OnScanListener onScanListener = new OnScanListener() {

        @Override
        public void onUpdateScanList(DeviceProductBean... devices) {
            Logc.d(TAG, "成功扫描到设备");
            if (devices.length > 0) {
                ArrayList<DeviceProductBean> deviceProductBeens=new ArrayList<>();
                for (DeviceProductBean deviceProductBean:devices){
                    deviceProductBeens.add(deviceProductBean);
                }
                try {
                    Type type = new TypeToken<List<DeviceProductBean>>() {
                    }.getType();
                    String json = GsonUtil.getInstance().getGson().toJson(deviceProductBeens, type);
                    if (iCommonBleBind!=null){
                        iCommonBleBind.onScanDevices(0,json);
                        HeTBindApi.getInstance().getBindApi().stopScan();
                    }
                }catch (JsonSyntaxException e){
                    if (iCommonBleBind != null) {
                        iCommonBleBind.onFailed(HetWifibindConstant.ERROR_SCAN_NO_DEVICE, HetWifibindConstant.MSG_SCAN_NO_DEVICE);
                    }
                }


            }

        }

        @Override
        public void onScanProgress(int progress) {
            if (iCommonBleBind != null) {
                 iCommonBleBind.onProgress(1,progress);
            }
        }

        @Override
        public void onScanFailed(String reason) {

            HeTBindApi.getInstance().getBindApi().stopScan();
            Logc.e(TAG, reason);
            if (iCommonBleBind != null) {
                iCommonBleBind.onFailed(HetWifibindConstant.ERROR_SCAN_NO_DEVICE, HetWifibindConstant.MSG_SCAN_NO_DEVICE);
            }

        }
    };



    private OnBindListener onBindListener = new OnBindListener() {
        @Override
        public void onBindProgress(int progress) {
            if (iCommonBleBind != null) {
               iCommonBleBind.onProgress(HetCodeConstants.BIND_PROGESS, progress);
            }
        }

        @Override
        public void onBindSucess(DeviceBean device) {

            Logc.i(TAG,"绑定成功");
            if (iCommonBleBind!= null) {
                DeviceModel deviceModel = null;
                try {
                    String json= GsonUtil.getInstance().getGson().toJson(device,DeviceBean.class);
                    deviceModel = GsonUtil.getInstance().getGson().fromJson(json,DeviceModel.class);
                } catch (JsonSyntaxException e) {
                    Logc.e(TAG,e.toString());
                }
                //绑定成功后停止绑定
                if (iCommonBleBind!=null){
                    iCommonBleBind.onSuccess(deviceModel);
                }
            }

        }

        @Override
        public void onBindFailed(String reason) {
            Logc.e(TAG,reason);
            HeTBindApi.getInstance().getBindApi().stopScan();
            if (iCommonBleBind!= null) {
                iCommonBleBind.onFailed(HetWifibindConstant.ERROR_DEVICE_NO_BIND, HetWifibindConstant.MSG_ERROR);
            }
        }
    };







}
