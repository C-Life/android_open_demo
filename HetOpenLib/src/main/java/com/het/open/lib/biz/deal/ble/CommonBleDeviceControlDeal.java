package com.het.open.lib.biz.deal.ble;

import com.het.bluetoothbase.callback.IBleCallback;
import com.het.bluetoothbase.callback.IConnectCallback;
import com.het.bluetoothoperate.device.HetOpenPlatformBluetoothDevice;
import com.het.bluetoothoperate.manager.BluetoothDeviceManager;
import com.het.bluetoothoperate.manager.model.BluetoothDeviceState;
import com.het.bluetoothoperate.mode.HetOpenPlatformCmdInfo;
import com.het.bluetoothoperate.proxy.IHetHistoryListener;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  设备列表api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class CommonBleDeviceControlDeal {


    /**
     * 连接蓝牙设备
     * @param mac 蓝牙设备地址
     * @param connectCallback 连接回调
     */
   public static void connect(String mac,IConnectCallback connectCallback) {
        HetOpenPlatformBluetoothDevice device = new HetOpenPlatformBluetoothDevice(mac);
        BluetoothDeviceState state = new BluetoothDeviceState(device).setConnectCallback(connectCallback);
        BluetoothDeviceManager.getInstance().monitor(state);

    }

    /**
     * 断开蓝牙设备
     * @param mac 蓝牙设备地址
     */
    public static void disConnect(String mac) {
        BluetoothDeviceManager.getInstance().disconnect(mac);

    }

    /**
     * 发送命令
     * @param mac 蓝牙设备地址
     * @param writeType 下发数据命令类型
     * @param bleCallback 下发回调
     */
    public static void write(String mac,int writeType,IBleCallback bleCallback) {
        BluetoothDeviceManager.getInstance().write(
                new HetOpenPlatformCmdInfo(bleCallback)
                        .setMac(mac)
                        .setCmd(writeType)

        );
    }

    /**
     * 发送命令
     * @param mac 蓝牙设备地址
     * @param writeType 下发数据命令类型
     * @param bleCallback 下发回调
     */
    public static void writeHistroy(String mac,int writeType,IBleCallback bleCallback, IHetHistoryListener listener) {
        BluetoothDeviceManager.getInstance().write(
                new HetOpenPlatformCmdInfo(bleCallback)
                        .setMac(mac)
                        .setCmd(writeType)
                        .setReceivePacket(listener)

        );
    }

    /**
     * 发送命令
     * @param mac 蓝牙设备地址
     * @param writeType 下发数据命令类型
     * @param datas 下发数据
     * @param bleCallback 下发回调
     */
    public static void write(String mac,int writeType,byte[] datas,IBleCallback bleCallback) {
        BluetoothDeviceManager.getInstance().write(
                new HetOpenPlatformCmdInfo(bleCallback)
                        .setMac(mac)
                        .setSendParameter(datas)
                        .setCmd(writeType));
    }



    /**
     * 发送命令
     * @param mac 蓝牙设备地址
     * @param readType 读取命令类型
     * @param bleCallback 读取回调
     */
    public static void read(String mac,int readType,IBleCallback bleCallback) {
        BluetoothDeviceManager.getInstance().read(
                new HetOpenPlatformCmdInfo(bleCallback)
                        .setMac(mac)
                        .setCmd(readType));
    }








}
