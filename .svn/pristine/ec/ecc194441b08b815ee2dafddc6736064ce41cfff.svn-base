package com.het.open.sdk.sleepble.biz.bledevice.control;

import android.util.Log;

import com.het.ble.HetBleDevice;
import com.het.ble.HetBleSupporter;
import com.het.ble.util.StringUtils;
import com.het.open.lib.callback.ICallback;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.utils.FileUtils;
import com.het.open.lib.utils.LogUtils;
import com.het.open.sdk.sleepble.biz.csleep.SleepBleDeal;

import java.io.InputStream;

/**
 * 蓝牙控制业务逻辑
 * Created by xuchao on 2016/3/15.
 */
public class BizControlBiz {
    private final String TAG = "蓝牙控制业务逻辑";
    private static BizControlBiz mInstance;


    private BizControlBiz() {
    }

    public static BizControlBiz getInstance() {
        if (mInstance == null) {
            synchronized (BizControlBiz.class) {
                mInstance = new BizControlBiz();
            }
        }
        return mInstance;
    }




    /**
     * 同步蓝牙数据
     * @param deviceModel 设备model
     */
    public void syncData(final ICallback<String> iCallback,final DeviceModel deviceModel) {
        if (deviceModel == null) {
            return;
        }
        String mac = deviceModel.getMacAddress();
        HetBleSupporter.connecter().connect(mac, new com.het.ble.ICallback<HetBleDevice>() {
            @Override
            public void onSuccess(final HetBleDevice dev, int id) {
                dev.syncData(new com.het.ble.ICallback<byte[]>() {
                    @Override
                    public void onSuccess(final byte[] datas, int id) {

                        if (datas != null && datas.length > 0) {
                            LogUtils.i("uploadDataToCloud", "data : " + StringUtils.byteArrayToHexString(datas));
                           InputStream in = FileUtils.bytes2inputStream(datas);
                           String version = dev.getLastRecHead().protocolVersion>0?dev.getLastRecHead().protocolVersion+"":"0";
                           SleepBleDeal.updateRun(new IHetCallback() {
                              @Override
                              public void onSuccess(int code, String msg) {
                                  dev.clearData(new com.het.ble.ICallback<byte[]>() {
                                      @Override
                                      public void onSuccess(byte[] t, int id) {
                                          iCallback.onSuccess("syncData OK", id);
                                      }

                                      @Override
                                      public void onFailure(int code, String msg, int id) {
                                          iCallback.onFailure(code, msg, id);
                                      }
                                  });
                              }

                              @Override
                              public void onFailed(int code, String msg) {

                                  iCallback.onFailure(code, msg,-1);
                              }

                          },deviceModel.getDeviceId(),  in, "", version);
                        } else {
                            iCallback.onSuccess("syncData OK", id);
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg, int id) {
                        iCallback.onFailure(code, msg, id);
                    }
                });
            }

            @Override
            public void onFailure(int code, String msg, int id) {
                Log.i(TAG, "syncData fail ====== connect fail");
                iCallback.onFailure(code, msg, id);
            }

        });


    }

    /**
     * 获取蓝牙实时数据
     * @param deviceModel  设备model
     * @param iCallback    接口回调
     */
    public void getRealData(final ICallback<byte[]> iCallback,DeviceModel deviceModel){
        if (deviceModel == null) {
            return;
        }
        String mac = deviceModel.getMacAddress();
        HetBleSupporter.connecter().connect(mac, new com.het.ble.ICallback<HetBleDevice>() {
            @Override
            public void onSuccess(final HetBleDevice dev, int id) {
                dev.getRealTimeData(new com.het.ble.ICallback<byte[]>() {
                    @Override
                    public void onSuccess(byte[] datas, int id) {
                        iCallback.onSuccess(datas, id);
                    }

                    @Override
                    public void onFailure(int code, String msg, int id) {
                        iCallback.onFailure(code, msg, id);
                    }
                });
            }
            @Override
            public void onFailure(int code, String msg, int id) {
                LogUtils.e(TAG, "getRealTimeData fail ====== connect fail");
                iCallback.onFailure(code, msg, id);
            }
        });

    }
}
