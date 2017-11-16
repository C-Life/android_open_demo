package com.het.open.sdk.sleepble.biz.bledevice.bind;

import android.content.Context;

import com.het.ble.BleModel;
import com.het.ble.HetBleDevice;
import com.het.ble.HetBleSupporter;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.utils.HetCodeConstants;
import com.het.open.lib.utils.LogUtils;
import com.het.open.sdk.sleepble.callback.IBleBind;

import java.util.ArrayList;
import java.util.List;

/**
 * 蓝牙设备绑定api
 * Created by xuchao on 2016/3/15.
 */
public class BleBiz {
    private final String TAG = "蓝牙设备绑定api";
    private static BleBiz mInstance;
    private DeviceModel curDeviceModel;  //要绑定设备类想
    private static final int START_SCAN = 0x01;    //获取认证服务器地址
    private static final int COMMIT_DEVICE_MESS = 0x02;    //提交设备信息
    private static final int START_BIND = 0x03;    //开始绑定
    private List<DeviceModel> scanDeviceModels = new ArrayList<>();
    private IBleBind iBleBind;
    private int scanTimeOut = 20000;
    private DeviceModel  getDeviceModel=null;


    private BleBiz() {
    }

    public static BleBiz getInstance() {
        if (mInstance == null) {
            synchronized (BleBiz.class) {
                mInstance = new BleBiz();
            }
        }
        return mInstance;
    }





    /**
     * 初始化蓝牙设备
     *
     * @return
     * @throws Exception
     */
    public int init(Context mContext) {

        if (HetBleSupporter.supporter().isSupportBle()) {
            if (HetBleSupporter.supporter().isBleEnable()) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 2;
        }
    }

    /**
     * 设置扫描时间
     * @param time
     */
    public void setScanTime(int time) {
        this.scanTimeOut = time;
    }


    /**
     * 开始绑定设备
     *
     * @param deviceModel 绑定的设备类型
     * @param iBleBind    绑定接口回调
     */
    public void startBind(DeviceModel deviceModel, IBleBind iBleBind) {

        this.curDeviceModel = deviceModel;
        this.iBleBind = iBleBind;
        scan(curDeviceModel.getDeviceTypeId()+"");


    }

    /**
     * 开始绑定设备
     *
     * @param productId  产品id
     * @param iBleBind    绑定接口回调
     */
    public void startBind(String productId, final  IBleBind iBleBind) {
        this.iBleBind = iBleBind;
//        DeviceGetListApi.getInstance().getProduct(productId).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                if (!StringUtils.isNull(s)){
//                    getDeviceModel= GsonUtil.getGsonInstance().fromJson(s,DeviceModel.class);
//
//                    if (getDeviceModel!=null){
//                        curDeviceModel = getDeviceModel;
//                        scan(curDeviceModel.getDeviceTypeId());
//                    }else {
//                        if (iBleBind != null) {
//                            iBleBind.onFailed(HetCodeConstants.BLE_ERROR_BIND_SCAN_NO_DEVICE, "无法获取设备信息");
//                        }
//                    }
//                }
//            }
//        }, new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//                //LogUtils.e(TAG,code+msg);
//               // LogUtils.e(TAG,code+msg);
//                if (iBleBind != null) {
//                    iBleBind.onFailed(HetCodeConstants.BLE_ERROR_BIND_SCAN_NO_DEVICE, "无法获取设备信息");
//                }
//            }
//        });
//        DeviceGetListApi.getProduct(new ICallback<String>() {
//            @Override
//            public void onSuccess(String s, int id) {
//                if (!StringUtils.isNull(s)){
//                    getDeviceModel= GsonUtil.getGsonInstance().fromJson(s,DeviceModel.class);
//
//                    if (getDeviceModel!=null){
//                        curDeviceModel = getDeviceModel;
//                        scan(curDeviceModel.getDeviceTypeId());
//                    }else {
//                        if (iBleBind != null) {
//                            iBleBind.onFailed(HetCodeConstants.BLE_ERROR_BIND_SCAN_NO_DEVICE, "无法获取设备信息");
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(int code, String msg, int id) {
//                LogUtils.e(TAG,code+msg);
//                if (iBleBind != null) {
//                    iBleBind.onFailed(HetCodeConstants.BLE_ERROR_BIND_SCAN_NO_DEVICE, "无法获取设备信息");
//                }
//            }
//        },productId);





    }

//    public DeviceModel getScanDeviceModel(String productId){
//        getDeviceModel=null;
//
//        return  getDeviceModel;
//    }



    public void scan(String type) {

        HetBleSupporter.scanner().startScanByType(type, scanTimeOut, new com.het.ble.ICallback<List<BleModel>>() {
            @Override
            public void onSuccess(List<BleModel> bleModels, int id) {
                if (bleModels != null && bleModels.size() > 0) {
                    List<DeviceModel> deviceList = new ArrayList<DeviceModel>();
                    for (BleModel bleModel : bleModels) {
                        if (bleModel != null) {
                            DeviceModel deviceInfo = new DeviceModel();
                            deviceInfo.setDeviceName(bleModel.getName());
                            deviceInfo.setMacAddress(bleModel.getMac());
                            deviceInfo.setDeviceSubtypeId(Integer.parseInt(bleModel.getDevSubTypeId()));
                            deviceInfo.setDeviceTypeId(Integer.parseInt(bleModel.getDevTypeId()));
                            deviceInfo.setProductId(curDeviceModel.getProductId());
                            deviceList.add(deviceInfo);
                        }
                    }
                    if (iBleBind != null) {
                        String device = GsonUtil.getGsonInstance().toJson(deviceList);
                        iBleBind.onScanDevices(device, "成功扫描到以下设备");
                    }
                } else {
                    if (iBleBind != null) {
                        iBleBind.onFailed(HetCodeConstants.BLE_ERROR_BIND_SCAN_NO_DEVICE, "没有扫描到设备");
                    }
                }


            }

            @Override
            public void onFailure(int code, String msg, int id) {
                LogUtils.e(TAG, code + msg);
                if (iBleBind != null) {
                    iBleBind.onFailed(HetCodeConstants.BLE_ERROR_BIND_SCAN_NO_DEVICE, "没有扫描到设备");
                }

            }
        });
    }


    /**
     * 连接设备
     *
     * @param mac
     * @throws Exception
     */
    public void connect(String mac) {
        HetBleSupporter.connecter().connect(mac, new com.het.ble.ICallback<HetBleDevice>() {
            @Override
            public void onSuccess(HetBleDevice hetBleDevice, int id) {
                if (hetBleDevice != null) {
                    BleModel bleModel = hetBleDevice.getBleModle();
                    if (bleModel != null) {
                        DeviceModel deviceInfo = new DeviceModel();
                        deviceInfo.setDeviceName(bleModel.getName());
                        deviceInfo.setMacAddress(bleModel.getMac());
                        deviceInfo.setDeviceSubtypeId(Integer.parseInt(bleModel.getDevSubTypeId()));
                        deviceInfo.setDeviceTypeId(Integer.parseInt(bleModel.getDevTypeId()));
                        deviceInfo.setProductId(curDeviceModel.getProductId());
                        submit(deviceInfo);
                    } else {
                        if (iBleBind != null) {
                            iBleBind.onFailed(HetCodeConstants.BLE_ERROR_BIND_CONNET_FAILED, "连接蓝牙设备失败");
                        }
                    }
                } else {
                    if (iBleBind != null) {
                        iBleBind.onFailed(HetCodeConstants.BLE_ERROR_BIND_SCAN_NO_DEVICE, "没有扫描到设备");
                    }
                }
            }

            @Override
            public void onFailure(int code, String msg, int id) {
                LogUtils.e(TAG, code + msg);
                if (iBleBind != null) {
                    iBleBind.onFailed(HetCodeConstants.BLE_ERROR_BIND_SCAN_NO_DEVICE, "没有扫描到设备");
                }
            }
        });
    }

    /**
     * 提交设备信息到服务器
     *
     * @param deviceModel
     */
    private void submit(final DeviceModel deviceModel) {
//        DeviceBindApi.bindBle(new ICallback<String>() {
//            @Override
//            public void onSuccess(String s, int id) {
//                if (!TextUtils.isEmpty(s)) {
//                    BindModel bindModel = GsonUtil.getGsonInstance().fromJson(s, BindModel.class);
//                    if (bindModel != null) {
//                        deviceModel.setDeviceId(bindModel.getDeviceId());
//                        deviceModel.setUserKey(bindModel.getUserKey());
//                        if (iBleBind != null) {
//                            iBleBind.onSuccess(deviceModel);
//                        }
//                    }
//                } else {
//                    if (iBleBind != null) {
//                        iBleBind.onFailed(HetCodeConstants.BLE_ERROR_BIND_SUBMIT_FAILED, "绑定设备失败");
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(int code, String msg, int id) {
//                LogUtils.e(TAG, code + msg);
//                if (iBleBind != null) {
//                    iBleBind.onFailed(HetCodeConstants.BLE_ERROR_BIND_SUBMIT_FAILED, "绑定设备失败"+code+msg);
//                }
//            }
//        }, deviceModel, -1);

    }


}
