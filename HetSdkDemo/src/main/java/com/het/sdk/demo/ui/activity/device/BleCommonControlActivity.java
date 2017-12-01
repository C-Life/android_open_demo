package com.het.sdk.demo.ui.activity.device;

import android.bluetooth.BluetoothGatt;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.het.bluetoothbase.callback.IBleCallback;
import com.het.bluetoothbase.callback.IConnectCallback;
import com.het.bluetoothbase.callback.IHookCallBack;
import com.het.bluetoothbase.exception.BleException;
import com.het.bluetoothbase.utils.ConvertUtil;
import com.het.bluetoothbase.utils.HexUtil;
import com.het.bluetoothbase.utils.HookManager;
import com.het.bluetoothoperate.mode.CmdIndexConstant;
import com.het.bluetoothoperate.mode.HetOpenPlatformCmdInfo;
import com.het.bluetoothoperate.proxy.IHetHistoryListener;
import com.het.log.Logc;
import com.het.open.lib.api.HetCommonBleControlApi;
import com.het.open.lib.model.DeviceModel;
import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.utils.UIJsonConfig;

/**
 * <p>描述：新版蓝牙库demo</p>
 * 1.<br/>
 * 作者： aaron <br>
 * 日期： 2017/6/19 0019 16:27 <br>
 * 版本： V 1.0 <br>
 */
public class BleCommonControlActivity extends BaseHetActivity implements View.OnClickListener {

    private Button conDevice;
    /**
     * log text
     */
    private TextView tv_info;

    /**
     * 滚动条
     */
    private ScrollView sv_log;
    /**
     * lamp *  send config data
     */
    private Button ctrlLamp;

    private boolean isGettingInfo = false;
    /**
     * list index of getting device info
     */
    private int what;
    /**
     * control color of lamp
     */
    private byte colorLamp = 2;

    private String macAddress;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    HetCommonBleControlApi.getInstance().read(macAddress, CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_SYSTEM_ID, readCallback);
//                    manager.read(
//                            new HetOpenPlatformCmdInfo(readCallback)
//                                    .setMac(bluetoothLeDevice.getAddress())
//                                    .setCmd(CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_SYSTEM_ID));
                    break;
                case 2:
                    HetCommonBleControlApi.getInstance().read(macAddress, CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_FIRMWARE_REVISION, readCallback);
//                    manager.read(
//                            new HetOpenPlatformCmdInfo(readCallback)
//                                    .setMac(bluetoothLeDevice.getAddress())
//                                    .setCmd(CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_FIRMWARE_REVISION));
                    break;
                case 3:
                    HetCommonBleControlApi.getInstance().read(macAddress, CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_HARDWARE_REVISION, readCallback);
//                    manager.read(
//                            new HetOpenPlatformCmdInfo(readCallback)
//                                    .setMac(bluetoothLeDevice.getAddress())
//                                    .setCmd(CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_HARDWARE_REVISION));
                    break;
                case 4:
                    HetCommonBleControlApi.getInstance().read(macAddress, CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_SOFTWARE_REVISION, readCallback);
//                    manager.read(
//                            new HetOpenPlatformCmdInfo(readCallback)
//                                    .setMac(bluetoothLeDevice.getAddress())
//                                    .setCmd(CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_SOFTWARE_REVISION));
                    break;
                case 5:
                    HetCommonBleControlApi.getInstance().read(macAddress, CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_SERIAL_NUMBER, readCallback);
//                    manager.read(
//                            new HetOpenPlatformCmdInfo(readCallback)
//                                    .setMac(bluetoothLeDevice.getAddress())
//                                    .setCmd(CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_SERIAL_NUMBER));
                    break;
                case 6:
                    HetCommonBleControlApi.getInstance().read(macAddress, CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_MANUFACTURE_NAME, readCallback);
//                    manager.read(
//                            new HetOpenPlatformCmdInfo(readCallback)
//                                    .setMac(bluetoothLeDevice.getAddress())
//                                    .setCmd(CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_MANUFACTURE_NAME));
                    break;
                case 7:
                    HetCommonBleControlApi.getInstance().read(macAddress, CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_MODEL_NUMBER, readCallback);
//                    manager.read(
//                            new HetOpenPlatformCmdInfo(readCallback)
//                                    .setMac(bluetoothLeDevice.getAddress())
//                                    .setCmd(CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_MODEL_NUMBER));
                    break;
                case 8:
                    HetCommonBleControlApi.getInstance().read(macAddress, CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_PNP_ID, readCallback);
//                    manager.read(
//                            new HetOpenPlatformCmdInfo(readCallback)
//                                    .setMac(bluetoothLeDevice.getAddress())
//                                    .setCmd(CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_PNP_ID));
                    break;
                case 9:
                    HetCommonBleControlApi.getInstance().read(macAddress, CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_BATTERY, readCallback);
//                    manager.read(
//                            new HetOpenPlatformCmdInfo(readCallback)
//                                    .setMac(bluetoothLeDevice.getAddress())
//                                    .setCmd(CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_BATTERY));
                    break;
                case 10:
                    isGettingInfo = false;
                    break;
            }
            what++;
        }
    };


    private void connect() {
        HetCommonBleControlApi.getInstance().connect(macAddress, connectCallback);
        conDevice.setText("connecting");
    }

    private final IConnectCallback connectCallback = new IConnectCallback() {
        @Override
        public void onConnectSuccess(BluetoothGatt gatt, int status) {
            conDevice.setText("连接成功");
            HookManager.getInstance().enableHook(true, "enable@hook");
            HookManager.getInstance().addHook(hookCallBack);
        }

        @Override
        public void onConnectFailure(final BleException exception) {
            conDevice.setText("连接失败");
        }

        @Override
        public void onDisconnect(String mac) {
            conDevice.setText("连接断开");
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        hetFirewareUpgradeApi.stop();
        HetCommonBleControlApi.getInstance().disConnect(macAddress);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_ble_control;
    }

    @Override
    protected void initData() {
        DeviceModel deviceModel = (DeviceModel) getIntent().getSerializableExtra("DeviceModel");
        if (deviceModel != null) {
            macAddress = deviceModel.getMacAddress();
        }
        HetCommonBleControlApi.getInstance().init(this);

// 升级测试
//        deviceModel = new DeviceModel();
//        deviceModel.setMacAddress("D6:61:3D:DD:FA:23");
//        String  file =  Environment.getExternalStorageDirectory() + File.separator + "WQM3H_AP.bin";
//        Logc.e("file =" + file);
//        if (SDKIOUtil.fileIsExists(file)){
//            hetFirewareUpgradeApi = new HetFirewareUpgradeApi.BleUpgradeBuilder()
//                    .setContext(this)
//                    .setFilePath(file)
//                    .setDeviceModel(deviceModel)
//                    .setiUpInterface(iFirwareUpCallback).build();
//            hetFirewareUpgradeApi.init();
//            hetFirewareUpgradeApi.check();
//        }

    }

//    private HetFirewareUpgradeApi hetFirewareUpgradeApi;
//
//    private IFirwareUpCallback iFirwareUpCallback = new IFirwareUpCallback() {
//        @Override
//        public void checkHasNewVersion(UpgradeType upgradeType, DeviceVersionUpgradeModel deviceVersionUpgradeModel) {
//            Logc.e("---->" + deviceVersionUpgradeModel.toString());
//            //开始检查新版本
//        }
//
//        @Override
//        public void checkNoVersion(UpgradeType upgradeType, DeviceVersionUpgradeModel deviceVersionUpgradeModel) {
//            Logc.e("---->" + deviceVersionUpgradeModel.toString());
//        }
//
//        @Override
//        public void upgradeReady(UpgradeType upgradeType) {
//            hetFirewareUpgradeApi.start();
//        }
//
//        @Override
//        public void onStartUpgrade(UpgradeType upgradeType) {
//            Logc.e("--onStartUpgrade-->");
//        }
//
//        @Override
//        public void onUpgradeProgress(UpgradeType upgradeType, int progress) {
//            Logc.e("--onProgress-->" + progress);
//        }
//
//        @Override
//        public void onUpgradeSuccess(UpgradeType upgradeType) {
//            Logc.e("--onUpgradeSuccess-->");
//        }
//
//        @Override
//        public void onUpgradeFail(UpgradeType upgradeType, Throwable var1) {
//            Logc.e("--onUpgradeFail-->" + var1.getMessage());
//        }
//    };

    @Override
    protected void initView(Bundle savedInstanceState) {
        conDevice = (Button) findViewById(R.id.conDevice);
        sv_log = (ScrollView) findViewById(R.id.sv_log);
        tv_info = (TextView) findViewById(R.id.tv_log);
        ctrlLamp = (Button) findViewById(R.id.sendConfigData);
    }

    @Override
    protected void initTopBarView() {
        mTitleView.setTitle("设备控制");
        mTitleView.setBackground(UIJsonConfig.getInstance(mContext).setNavBackground_color());
    }

    @Override
    public void onClick(View v) {
        if (isGettingInfo)
            return;
        switch (v.getId()) {
            case R.id.conDevice:
                connect();
                break;
            case R.id.getDeviceInfo:
                what = 1;
                isGettingInfo = true;
                handler.sendEmptyMessage(what);
                break;
            case R.id.cleanLog:
                tv_info.setText("");
                break;
            case R.id.bind:
                HetCommonBleControlApi.getInstance().write(macAddress, CmdIndexConstant.HET_COMMAND_BIND_APP, writeCallback);
//                manager.write(
//                        new HetOpenPlatformCmdInfo(writeCallback)
//                                .setMac(bluetoothLeDevice.getAddress())
//                                .setCmd(CmdIndexConstant.HET_COMMAND_BIND_APP)
//                );
                break;
            case R.id.getDeviceTime:
                HetCommonBleControlApi.getInstance().write(macAddress, CmdIndexConstant.HET_COMMAND_GET_TIME_APP, writeCallback);
//                manager.write(
//                        new HetOpenPlatformCmdInfo(writeCallback)
//                                .setMac(bluetoothLeDevice.getAddress())
//                                .setCmd(CmdIndexConstant.HET_COMMAND_GET_TIME_APP)
//                );
                break;
            case R.id.setDeviceTime:
                HetCommonBleControlApi.getInstance().write(macAddress, CmdIndexConstant.HET_COMMAND_SET_TIME_APP, new byte[]{1}, writeCallback);
//                manager.write(
//                        new HetOpenPlatformCmdInfo(writeCallback)
//                                .setMac(bluetoothLeDevice.getAddress())
//                                .setSendParameter(new byte[]{1})
//                                .setCmd(CmdIndexConstant.HET_COMMAND_SET_TIME_APP)
//                );
                break;
            case R.id.getRealData:
                HetCommonBleControlApi.getInstance().write(macAddress, CmdIndexConstant.HET_COMMAND_GET_REAL_TIME_DATA_APP, writeCallback);
//                manager.write(
//                        new HetOpenPlatformCmdInfo(writeCallback)
//                                .setMac(bluetoothLeDevice.getAddress())
//                                .setCmd(CmdIndexConstant.HET_COMMAND_GET_REAL_TIME_DATA_APP)
//                );
                break;
            case R.id.sendConfigData:
                byte[] bytes;
                colorLamp++;
                if (colorLamp > 8) {
                    colorLamp = 0;
                    bytes = new byte[]{0x0, 0x03, colorLamp};//0x03关灯，0x01开灯。
                } else
                    bytes = new byte[]{0x0, 0x01, colorLamp};//0x03关灯，0x01开灯。
                showColor();
                HetCommonBleControlApi.getInstance().write(macAddress, CmdIndexConstant.HET_COMMAND_CONFIG_DATA_APP, bytes, writeCallback);
//                manager.write(
//                        new HetOpenPlatformCmdInfo(writeCallback)
//                                .setMac(bluetoothLeDevice.getAddress())
//                                .setSendParameter(bytes)
//                                .setCmd(CmdIndexConstant.HET_COMMAND_CONFIG_DATA_APP)
//                );
                break;
            case R.id.getHistoryData:
                IHetHistoryListener listener = new IHetHistoryListener() {
                    @Override
                    public void receiveFinish(byte[] bytes) {
                        Logc.i(HexUtil.encodeHexStr(bytes));
                    }

                    @Override
                    public void onFail(String msg) {

                    }

                    @Override
                    public void onProgress(int progress) {
                        Logc.i("history:" + progress);
                    }
                };
                HetCommonBleControlApi.getInstance().writeHistroy(macAddress, CmdIndexConstant.HET_COMMAND_GET_HISTORY_DATA_APP, writeCallback, listener);
//                manager.write(
//                        new HetOpenPlatformCmdInfo(writeCallback)
//                                .setMac(bluetoothLeDevice.getAddress())
//                                .setCmd(CmdIndexConstant.HET_COMMAND_GET_HISTORY_DATA_APP)
//                                .setReceivePacket(listener));

                break;
        }
    }

    /**
     * 在主线程显示，需要同步
     * 超过最大行数，删除头部的一段落
     */
    private void showInfo(final String info) {
        if (info == null) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                synchronized (tv_info) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(tv_info.getText());
                    if (stringBuilder.length() > 1000) {
                        int index = stringBuilder.indexOf("\n", 1);
                        stringBuilder.delete(0, index);
                    }
                    stringBuilder.append("\n");
                    stringBuilder.append(info);
                    tv_info.setText(stringBuilder.toString());
                    sv_log.setSelected(true);
                    sv_log.fullScroll(View.FOCUS_DOWN);
                }
            }
        });
    }

    private IHookCallBack hookCallBack = new IHookCallBack() {
        @Override
        public void onWrite(byte[] bytes) {
            showInfo("Send:" + HexUtil.encodeHexStr(bytes));
        }

        @Override
        public void onRead(byte[] bytes) {
            showInfo("Read:" + HexUtil.encodeHexStr(bytes));
        }

        @Override
        public void onReceived(byte[] bytes) {
            showInfo("Rec:" + HexUtil.encodeHexStr(bytes));
        }
    };

    private IBleCallback<HetOpenPlatformCmdInfo> readCallback = new IBleCallback<HetOpenPlatformCmdInfo>() {
        @Override
        public void onSuccess(HetOpenPlatformCmdInfo cmdInfo, int type) {
            byte[] bytes = (byte[]) cmdInfo.getReceivePacket();
            String msg = "Read:" + ConvertUtil.convertHexToString(HexUtil.encodeHexStr(bytes));
            switch (cmdInfo.getCmd()) {
                case CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_SYSTEM_ID:
                    msg += "(System Id)";
                    break;
                case CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_FIRMWARE_REVISION:
                    msg += "(Firmware Revision)";
                    break;
                case CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_HARDWARE_REVISION:
                    msg += "(Hardware Revision)";
                    break;
                case CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_SOFTWARE_REVISION:
                    msg += "(Software Revision)";
                    break;
                case CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_SERIAL_NUMBER:
                    msg += "(Serial Number)";
                    break;
                case CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_MANUFACTURE_NAME:
                    msg += "(Manufacture Name)";
                    break;
                case CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_MODEL_NUMBER:
                    msg += "(Model Number)";
                    break;
                case CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_PNP_ID:
                    msg += "(PnP ID)";
                    break;
                case CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_BATTERY:
                    msg += "(Battery Level)";
                    break;
            }
            if (isGettingInfo)
                handler.sendEmptyMessage(what);
            showInfo(msg);
        }

        @Override
        public void onFailure(BleException exception) {
            if (isGettingInfo)
                isGettingInfo = false;
            showInfo(exception.getDescription());
        }
    };

    private IBleCallback<HetOpenPlatformCmdInfo> writeCallback = new IBleCallback<HetOpenPlatformCmdInfo>() {
        @Override
        public void onSuccess(HetOpenPlatformCmdInfo cmdInfo, int type) {
//            byte[] bytes = (byte[]) cmdInfo.getReceivePacket();
//            showInfo(HexUtil.encodeHexStr(bytes));
        }

        @Override
        public void onFailure(BleException exception) {
            showInfo(exception.getDescription());
        }
    };

    private void showColor() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (colorLamp) {
                    case 0:
                        ctrlLamp.setBackgroundColor(Color.LTGRAY);
                        break;
                    case 1:
                        ctrlLamp.setBackgroundColor(Color.RED);
                        break;
                    case 2:
                        ctrlLamp.setBackgroundColor(getResources().getColor(R.color.orange));
                        break;
                    case 3:
                        ctrlLamp.setBackgroundColor(Color.YELLOW);
                        break;
                    case 4:
                        ctrlLamp.setBackgroundColor(Color.GREEN);
                        break;
                    case 5:
                        ctrlLamp.setBackgroundColor(Color.CYAN);
                        break;
                    case 6:
                        ctrlLamp.setBackgroundColor(Color.BLUE);
                        break;
                    case 7:
                        ctrlLamp.setBackgroundColor(getResources().getColor(R.color.purple));
                        break;
                    case 8:
                        ctrlLamp.setBackgroundColor(Color.WHITE);
                        break;
                }
            }
        });
    }

}
