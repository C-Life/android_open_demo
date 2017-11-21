package com.het.open.lib.biz.bind;

import android.app.Activity;

import com.google.gson.JsonSyntaxException;
import com.het.basic.data.api.token.TokenManager;
import com.het.basic.data.http.retrofit2.exception.ApiException;
import com.het.basic.model.DeviceBean;
import com.het.basic.utils.GsonUtil;
import com.het.bind.logic.HeTBindApi;
import com.het.bind.logic.bean.SSidInfoBean;
import com.het.bind.logic.bean.device.DeviceProductBean;
import com.het.bind.logic.sdk.callback.OnBindListener;
import com.het.bind.logic.sdk.callback.OnScanListener;
import com.het.log.Logc;
import com.het.open.lib.api.HetCodeConstants;
import com.het.open.lib.biz.api.DeviceGetListApi;
import com.het.open.lib.biz.constans.HetWifibindConstant;
import com.het.open.lib.callback.HetWifiBindState;
import com.het.open.lib.callback.IWifiBind;
import com.het.open.lib.model.DeviceModel;

import rx.functions.Action1;


/**
 * Created by 80010814 on 2016/12/30.
 */

public class HetWifiBind {

    private final String TAG = "wifi设备绑定api";
    private static HetWifiBind mInstance;
    private DeviceProductBean curDeviceModel;  //要绑定设备类想
    private HetWifiBindState hetWifiBindState = HetWifiBindState.NONE;
    private IWifiBind iWifiBind;
    private SSidInfoBean sSidInfoBean=new SSidInfoBean();
    private Activity activity;

    private HetWifiBind() {

    }

    public static HetWifiBind getInstance() {
        if (mInstance == null) {
            synchronized (HetWifiBind.class) {
                if (mInstance == null) mInstance = new HetWifiBind();
            }
        }
        return mInstance;
    }






    /**
     * 开始绑定设备
     * @param activity   扫描界面
     * @param wifiPassword wifi密码
     * @param productId    产品id
     * @param iWifiBind    绑定接口回调
     */
    public void startBind(Activity activity,String ssid ,final String wifiPassword, String productId, final IWifiBind iWifiBind) {
        if (!TokenManager.getInstance().isLogin()){
            if (iWifiBind != null) {
                iWifiBind.onFailed(HetWifibindConstant.ERROR_SCAN_NO_DEVICE, HetWifibindConstant.MSG_SCAN_NO_DEVICE_LOGIN);
            }
        }else {
            sSidInfoBean.setPass(wifiPassword);
            sSidInfoBean.setSsid(ssid);
            this.activity=activity;
            HeTBindApi.getInstance().init();
            curDeviceModel=new DeviceProductBean();
            this.iWifiBind = iWifiBind;
            hetWifiBindState = HetWifiBindState.START_SCAN;
            if (iWifiBind != null) {
                iWifiBind.onStatus(hetWifiBindState, HetWifibindConstant.MSG_START_SCAN);
            }
            DeviceGetListApi.getInstance().getProduct(productId).subscribe(new Action1<DeviceBean>() {
                @Override
                public void call(DeviceBean deviceBean) {
                    try {
                        if (deviceBean != null) {
                            curDeviceModel=enerateDeviceProductBean(deviceBean);
                            doScan();
                        }else {
                            if (iWifiBind != null) {
                                iWifiBind.onFailed(HetWifibindConstant.ERROR_DEVICE_NO_BIND, HetWifibindConstant.MSG_ERROR_DEVICE_NO_BIND);
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
                    if (iWifiBind != null) {
                        iWifiBind.onFailed(HetWifibindConstant.ERROR_DEVICE_NO_BIND, HetWifibindConstant.MSG_ERROR_DEVICE_NO_BIND);
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
        try {
            HeTBindApi.getInstance().getBindApi().setOnBindListener(onBindListener);
            HeTBindApi.getInstance().getBindApi().setOnScanListener(onScanListener);
            HeTBindApi.getInstance().getBindApi().startScan(activity, curDeviceModel, sSidInfoBean);
        }catch (Exception e){
            Logc.e(TAG,e.toString());
            if (iWifiBind != null) {
                iWifiBind.onFailed(HetWifibindConstant.ERROR_SCAN_NO_DEVICE, HetWifibindConstant.MSG_SCAN_NO_DEVICE);
            }
        }


    }

    /**
     * 开始绑定设备
     * @param deviceModel
     */
    private void doBind(DeviceProductBean deviceModel){
        try {
            HeTBindApi.getInstance().getBindApi().bind(deviceModel);
            hetWifiBindState = HetWifiBindState.START_BIND;
            if (iWifiBind != null) {
                iWifiBind.onStatus(hetWifiBindState, HetWifibindConstant.MSG_START_BIND);
            }
        } catch (Exception e) {
            Logc.e(TAG,e.toString());
            if (iWifiBind != null) {
                iWifiBind.onFailed(HetWifibindConstant.ERROR_BIND_DEVICE, HetWifibindConstant.MSG_ERROR);
            }
        }
    }

    private OnScanListener onScanListener = new OnScanListener() {

        @Override
        public void onUpdateScanList(DeviceProductBean... devices) {
            Logc.d(TAG, "成功扫描到设备");
            if (devices.length > 0) {
                DeviceProductBean deviceModel = devices[0];
                int type = deviceModel.getDeviceTypeId();
               // int subType = deviceModel.getDeviceSubtypeId();
                int defaultType = curDeviceModel.getDeviceTypeId();
                try {
                    if (type == defaultType) {
                        Logc.d(TAG, "开始绑定设备");
                        doBind(deviceModel);
                    } else {
                        Logc.d(TAG, "过滤掉该设备");
                    }


                } catch (Exception e) {
                    Logc.e(TAG, e.toString());
                }
            }

        }

        @Override
        public void onScanProgress(int progress) {
            if (iWifiBind != null) {
                if (hetWifiBindState == HetWifiBindState.START_SCAN) {
                    iWifiBind.onProgress(HetCodeConstants.SCAN_PROGESS, progress);
                }

            }

        }

        @Override
        public void onScanFailed(String reason) {
            Logc.e(TAG,reason);
            String errorMsg;
            if (reason.contains("ModuleId is unregistered")){
                errorMsg=HetWifibindConstant.MSG_MODULE_UN_REGISTER;
            }else {
                errorMsg=HetWifibindConstant.MSG_SCAN_NO_DEVICE;
            }
            if (iWifiBind != null) {
                iWifiBind.onFailed(HetWifibindConstant.ERROR_SCAN_NO_DEVICE, errorMsg);
            }

        }
    };



    private OnBindListener onBindListener = new OnBindListener() {
        @Override
        public void onBindProgress(int progress) {
            if (iWifiBind != null) {
                iWifiBind.onProgress(HetCodeConstants.BIND_PROGESS, progress);
            }
        }

        @Override
        public void onBindSucess(DeviceBean device) {
            Logc.d(TAG,"绑定成功");
            if (iWifiBind != null) {
                DeviceModel deviceModel = null;
                try {
                    String json= GsonUtil.getInstance().getGson().toJson(device,DeviceBean.class);
                    deviceModel = GsonUtil.getInstance().getGson().fromJson(json,DeviceModel.class);
                } catch (JsonSyntaxException e) {
                    Logc.e(TAG,e.toString());
                }
                //绑定成功后停止绑定
                hetWifiBindState = HetWifiBindState.BIND_SUCCESS;
                if (iWifiBind!=null){
                    iWifiBind.onStatus(hetWifiBindState, HetWifibindConstant.MSG_FINISH_SUCCESS);
                    iWifiBind.onSuccess(deviceModel);
                }
            }

        }

        @Override
        public void onBindFailed(String reason) {
            Logc.e(TAG,reason);
            HeTBindApi.getInstance().getBindApi().stopScan();
            if (iWifiBind != null) {
                iWifiBind.onFailed(HetWifibindConstant.ERROR_DEVICE_NO_BIND, HetWifibindConstant.MSG_ERROR);
            }
        }
    };







}
