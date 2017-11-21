package com.het.open.lib.api;

import android.content.Context;
import android.text.TextUtils;

import com.het.bluetoothbase.callback.IBleCallback;
import com.het.bluetoothbase.callback.IConnectCallback;
import com.het.bluetoothoperate.manager.BluetoothDeviceManager;
import com.het.bluetoothoperate.proxy.IHetHistoryListener;
import com.het.open.lib.biz.deal.ble.CommonBleDeviceControlDeal;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  和而泰通用蓝牙控制api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetCommonBleControlApi {
    private final String TAG = "和而泰通用蓝牙控制api";
    private static HetCommonBleControlApi mInstance;


    private HetCommonBleControlApi() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetCommonBleControlApi getInstance() {
        if (mInstance == null) {
            synchronized (HetCommonBleControlApi.class) {
                mInstance = new HetCommonBleControlApi();
            }
        }
        return mInstance;
    }

    /**
     * 初始化蓝牙控制
     *
     * @param context the context
     */
    public void init(Context context){
        BluetoothDeviceManager.getInstance().init(context);
    }

    /**
     * 连接蓝牙设备
     *
     * @param mac             蓝牙设备地址
     * @param connectCallback 连接回调
     */
    public  void connect(String mac,IConnectCallback connectCallback) {
        if (TextUtils.isEmpty(mac)){
            throw new IllegalArgumentException("mac is null");
        }
        CommonBleDeviceControlDeal.connect(mac,connectCallback);
    }


    /**
     * 断开蓝牙设备
     *
     * @param mac 蓝牙设备地址
     */
    public  void disConnect(String mac) {
        if (TextUtils.isEmpty(mac)){
            throw new IllegalArgumentException("mac is null");
        }
        CommonBleDeviceControlDeal.disConnect(mac);
    }

    /**
     * 发送命令
     *
     * @param mac         蓝牙设备地址
     * @param writeType   下发数据命令类型
     * @param bleCallback 下发回调
     */
    public void write(String mac,int writeType,IBleCallback bleCallback) {
        if (TextUtils.isEmpty(mac)){
            throw new IllegalArgumentException("mac is null");
        }
        CommonBleDeviceControlDeal.write(mac,writeType,bleCallback);
    }

    /**
     * 发送命令
     *
     * @param mac                 蓝牙设备地址
     * @param writeType           下发数据命令类型
     * @param bleCallback         下发回调
     * @param iHetHistoryListener the het history listener
     */
    public void writeHistroy(String mac, int writeType, IBleCallback bleCallback, IHetHistoryListener iHetHistoryListener) {
        if (TextUtils.isEmpty(mac)){
            throw new IllegalArgumentException("mac is null");
        }
        CommonBleDeviceControlDeal.writeHistroy(mac,writeType,bleCallback,iHetHistoryListener);
    }

    /**
     * 发送命令
     *
     * @param mac         蓝牙设备地址
     * @param writeType   下发数据命令类型
     * @param datas       下发数据
     * @param bleCallback 下发回调
     */
    public  void write(String mac,int writeType,byte[] datas,IBleCallback bleCallback) {
        if (TextUtils.isEmpty(mac)){
            throw new IllegalArgumentException("mac is null");
        }
        CommonBleDeviceControlDeal.write(mac,writeType,datas,bleCallback);
    }

    /**
     * 读取数据
     *
     * @param mac         蓝牙设备地址
     * @param readType    读取命令类型
     * @param bleCallback 读取回调
     */
    public  void read(String mac,int readType,IBleCallback bleCallback) {
        if (TextUtils.isEmpty(mac)){
            throw new IllegalArgumentException("mac is null");
        }
        CommonBleDeviceControlDeal.read(mac,readType,bleCallback);
    }



}
