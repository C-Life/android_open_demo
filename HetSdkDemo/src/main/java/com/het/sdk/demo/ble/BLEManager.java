package com.het.sdk.demo.ble;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.het.basic.utils.GsonUtil;
import com.het.basic.utils.StringUtils;
import com.het.bluetoothbase.callback.IConnectCallback;
import com.het.bluetoothbase.exception.BleException;
import com.het.bluetoothbase.utils.BleUtil;
import com.het.bluetoothbase.utils.ConvertUtil;
import com.het.bluetoothoperate.device.HetPubBleDevice;
import com.het.bluetoothoperate.listener.IBleModelParse;
import com.het.bluetoothoperate.listener.IReceiveCallback;
import com.het.bluetoothoperate.listener.ISendCallback;
import com.het.bluetoothoperate.manager.BluetoothDeviceManager;
import com.het.h5.sdk.callback.IMethodCallBack;
import com.het.log.Logc;
import com.het.open.lib.manager.base.BaseHtmlFiveFactory;
import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.base.BaseHetH5Activity;
import com.het.ui.sdk.CommonToast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BLEManager implements IBleModelParse {
    private static BLEManager bleManager;

    public HetPubBleDevice getHetPubBleDevice() {
        return hetPubBleDevice;
    }

    private HetPubBleDevice hetPubBleDevice;

    private BaseHtmlFiveFactory mHtmlFiveManager;
    private BaseHetActivity mActivity;

    private String mMac;

    private int count;

    private Handler mainHandle = new Handler(Looper.getMainLooper());

    private BLEManager(BaseHetActivity activity) {
        mActivity = activity;
        hetPubBleDevice = new HetPubBleDevice(mActivity ,this,null);
    }

    private BLEManager() {
    }

    public static BLEManager getInstance(BaseHetActivity activity) {
        if (bleManager == null) {
            synchronized (BLEManager.class) {
                if (bleManager == null) {
                    bleManager = new BLEManager(activity);
                }
            }
        }
        return bleManager;
    }

    public static BLEManager getInstance() {
        if (bleManager == null) {
            synchronized (BLEManager.class) {
                if (bleManager == null) {
                    bleManager = new BLEManager();
                }
            }
        }
        return bleManager;
    }

    public void init(BaseHetActivity activity) {
        mActivity = activity;
        BluetoothDeviceManager.getInstance().init(activity);
        hetPubBleDevice = new HetPubBleDevice(mActivity , this, null);
    }

    public void init(BaseHetActivity activity, String mac) {
        mActivity = activity;
        hetPubBleDevice = new HetPubBleDevice(mActivity , this, mac);
    }

    public void refreshAty(BaseHetActivity activity) {
        if (mActivity != activity) {
            mActivity = activity;
        }
    }

    public boolean isBlueToothEnable(Context context) {
        return BleUtil.isBleEnable(context);
    }

    public void enableBlueTooth(Activity activity, int requestCode) {
        BleUtil.enableBluetooth(activity, requestCode);
    }

    public boolean isSupportBle(Context context) {
        return BleUtil.isSupportBle(context);
    }

    private String formatMac(String mac) {
        StringBuilder sb = new StringBuilder(mac);
        if (!StringUtils.isNull(mac) && !mac.contains(":")) {
            sb.delete(0, mac.length());
            char[] macArray = mac.toCharArray();
            int length = mac.length();
            for (int i = 0; i < length; i++) {
                sb.append(macArray[i]);
                if (i % 2 == 1 && i != length - 1) {
                    sb.append(":");
                }
            }
        }
        return sb.toString();
    }

    private ConnModel connModel = new ConnModel();
    private Runnable connRunnable = () -> connDevice();

    public void stopScan(){
        hetPubBleDevice.getViseBluetooth().release();//停止扫描
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void connDevice() {
        if (hetPubBleDevice != null) {
            if (!hetPubBleDevice.getViseBluetooth().isConnected()) {
                //连接
                hetPubBleDevice.getViseBluetooth().connectByMac(mMac, false, new IConnectCallback() {
                    @Override
                    public void onConnectSuccess(BluetoothGatt bluetoothGatt, int i) {
                        //认证
                        Logc.e("连接成功");
                        authAndSetDeviceTime();
                    }

                    @Override
                    public void onConnectFailure(BleException e) {
                        Logc.e("连接失败" + e);
                        setDataToH5(false);
                        if (++count < 4 && mainHandle != null) {
                            mainHandle.postDelayed(connRunnable, 2000);
                        }
                    }

                    @Override
                    public void onDisconnect(String mac) {
                        Logc.e("断开连接");
                        setDataToH5(false);
                    }
                });
            } else if (hetPubBleDevice.getViseBluetooth().getBluetoothGatt() != null) {
                authAndSetDeviceTime();
            }
        }
    }

    public void connDeviceByMac(String mac, BaseHtmlFiveFactory htmlFiveManager) {
        Logc.d("开始连接蓝牙设备.");
        mHtmlFiveManager = htmlFiveManager;
        mMac = formatMac(mac);
        hetPubBleDevice.setMac(mac);
        mainHandle.postDelayed(connRunnable, 200);
    }

    public void connect(final String mac, final IConnectCallback connectCallback) {
        if (hetPubBleDevice != null) {
            hetPubBleDevice.getViseBluetooth().connectByMac(formatMac(mac), false, connectCallback);
        }

    }

    public void conncetBle(BaseHetActivity activity) {
        if (!TextUtils.isEmpty(mMac) && mHtmlFiveManager != null) {
            mActivity = activity;
            connDevice();
        }
    }

    public void getSoftVersion(ISendCallback sendCallback) {
        if (isConnected()) {
            hetPubBleDevice.getSoftwareRevision(new ISendCallback<String>() {
                @Override
                public void onSendSuccess(String s, int i) {
                    if (sendCallback != null) {
                        sendCallback.onSendSuccess(s, i);
                    }
                }

                @Override
                public void onSendFail(String s, int i) {
                    if (sendCallback != null) {
                        sendCallback.onSendFail(s, i);
                    }
                }
            });
        }
    }

    /**
     * 判断设备是否连接
     * @return
     */
    public boolean isConnected() {
        return hetPubBleDevice != null && hetPubBleDevice.getViseBluetooth().isConnected();
    }

    public boolean stopConnect() {
        if (isConnected()) {
            hetPubBleDevice.getViseBluetooth().disconnect();
            hetPubBleDevice.getViseBluetooth().release();
            return true;
        }
        return false;
    }

    private boolean mIsStartSetDeviceTime = false;//是否开始同步时间

    private void authAndSetDeviceTime() {
        setDataToH5(true);
        if (hetPubBleDevice != null) {
            hetPubBleDevice.setNotify(new IReceiveCallback<String>() {
                @Override
                public void onReceive(String data, int id) {
                }

                @Override
                public void onReceiveFail(String msg, int id) {
                    if (mActivity != null) mActivity.hideLoadingDialog();
                }
            }, false);
        }
        //同步设备时间
        mainHandle.postDelayed(() -> {
            if (hetPubBleDevice != null) {
                mIsStartSetDeviceTime = true;
                hetPubBleDevice.setDeviceTime(new ISendCallback<String>() {
                    @Override
                    public void onSendSuccess(String s, int i) {
                        Logc.e("同步时间成功");
                        mIsStartSetDeviceTime = false;
                        if (hetPubBleDevice != null) {
                            hetPubBleDevice.getHistoryData();//同步历史数据
                        }
                    }

                    @Override
                    public void onSendFail(String s, int i) {
                        Logc.e("同步时间失败" + s);
                        mIsStartSetDeviceTime = false;
                        mActivity.hideLoadingDialog();
                    }
                }, "json");
                mainHandle.postDelayed(mHideLoadingRunnable, 1000 * 10);
            }
        }, 800);
        mainHandle.post(() -> mActivity.showLoadingDialog(mActivity.getString(R.string.ble_history_start)));

    }

    private Runnable mHideLoadingRunnable = () -> {
        if (mIsStartSetDeviceTime && mActivity != null) {
            mActivity.hideLoadingDialog();//10s后, 如果还有该loading, 一般是同步时间没回复, 则直接隐藏loading
        }
    };

    private void setDataToH5(boolean flag) {
        connModel.setIsDeviceConnected(flag);
        if (mHtmlFiveManager != null) {
            mHtmlFiveManager.updateRunData(GsonUtil.getInstance().toJson(connModel));
        }
    }

    public void stopConnDevice() {
        count = 0;
        mainHandle.removeCallbacks(mHideLoadingRunnable);
        mainHandle.removeCallbacks(connRunnable);
        mainHandle.removeCallbacksAndMessages(null);
    }

    public void disconnect() {
        mainHandle.removeCallbacks(mHideLoadingRunnable);
        if (hetPubBleDevice != null) {
            hetPubBleDevice.getViseBluetooth().disconnect();
        }
    }

    public void clear() {
        if (hetPubBleDevice != null) {
            hetPubBleDevice = null;
            bleManager = null;
            mainHandle = null;
            count = 0;
        }
    }

    public void close() {
        if (hetPubBleDevice != null) {
            hetPubBleDevice.getViseBluetooth().clear();
            hetPubBleDevice.getViseBluetooth().close();
            hetPubBleDevice = null;
            bleManager = null;
            count = 0;
        }
    }

    public void setConfigData(String json, IMethodCallBack iMethodCallBack) {
        bleBean = GsonUtil.getInstance().toObject(json, BLEBean.class);
        if (bleBean != null) {
            if (bleBean.getBleData().equals("03FE")) {
                hetPubBleDevice.getRealData(new ISendCallback<String>() {
                    @Override
                    public void onSendSuccess(String s, int i) {
                        iMethodCallBack.onSucess(0, 0);
                    }

                    @Override
                    public void onSendFail(String s, int i) {
                        iMethodCallBack.onFailed(0, 0);
                    }
                });
            } else {
                hetPubBleDevice.sendConfigData(new ISendCallback<String>() {
                    @Override
                    public void onSendSuccess(String s, int i) {
                        iMethodCallBack.onSucess(0, 0);
                    }

                    @Override
                    public void onSendFail(String s, int i) {
                        iMethodCallBack.onFailed(0, 0);
                    }
                }, json);
            }
        }
    }


    @Override
    public byte[] parseJsonToByte(int i, String s) {
        if (i == HetPubBleDevice.SET_DEVICE_TIME) {
            return getTime();
        }
        bleBean = GsonUtil.getInstance().toObject(s, BLEBean.class);
        if (bleBean == null) {
            throw new IllegalArgumentException("参数错误");
        }
        return BytesUtil.hexStringToBytes(bleBean.getBleData());
    }

    private BLEBean bleBean = new BLEBean();


    @Override
    public String parseByteToJson(int i, byte[] bytes) {
        Logc.e("type=" + i + "bytes=" + BytesUtil.bytesToHexString(bytes));
        if (i == HetPubBleDevice.GET_HISTORY_DATA) {
            if (mainHandle != null && mActivity != null) {
                mainHandle.post(() -> {
                    mActivity.hideLoadingDialog();
                    if (mActivity instanceof BaseHetH5Activity) {
                        CommonToast.showToast(mActivity, mActivity.getResources().getString(R.string.ble_success));
                    }
                });
            }
        }
        if (mHtmlFiveManager != null && bytes != null) {
            bleBean.setBleData(BytesUtil.bytesToHexString(bytes));
            bleBean.setCmdType(i);
            mHtmlFiveManager.updateConfigData(GsonUtil.getInstance().toJson(bleBean));
        }
        return BytesUtil.bytesToHexString(bytes);
    }

    private static byte[] getTime() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        long l = System.currentTimeMillis();
        Date date = new Date(l);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);//年
        int month = calendar.get(Calendar.MONTH);//月
        int day = calendar.get(Calendar.DAY_OF_MONTH);//日
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        gregorianCalendar.set(year, month, day, hour, minute, second);
        int time = (int) (gregorianCalendar.getTimeInMillis() / 1000);
        time -= 28800;
        return ConvertUtil.intToBytesHigh(time, 4);
    }


}
