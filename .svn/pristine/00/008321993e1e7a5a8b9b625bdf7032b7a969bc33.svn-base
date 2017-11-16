package com.het.open.lib.biz.device;

import android.os.Handler;
import android.os.Looper;

import com.het.open.lib.callback.ISendDataStrategyCb;
import com.het.open.lib.model.SendPacketData;


/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：设备发送控制数据策略：
 * 1.快速点击，每次点击都在1s内，那么不发送数据；
 * 2.当快速点击结束，1s后发送最后一条数据；
 * </p>
 * 名称: DeviceSendDataStrategy <br>
 * 作者: uuxia<br>
 * 版本: 1.0<br>
 * 日期: 2016/8/8 14:14<br>
 **/

public class DeviceSendDataStrategy implements IStrategy {
    /**
     * 通知发送数据
     **/
    private ISendDataStrategyCb sendDataStrategyCb;

    /**
     * 记录第一帧数据发送时间
     **/
    private long mFirstTime = 0;

    private Handler mHander = new Handler(Looper.getMainLooper());

    private SendDataRunnable delayRunable = new SendDataRunnable();

    /**
     * 发送数据最小间隔时间
     **/
    private final int INTERVAL = 1 * 1000;

    public DeviceSendDataStrategy() {
    }

    public DeviceSendDataStrategy(ISendDataStrategyCb sendDataStrategyCb) {
        this();
        this.sendDataStrategyCb = sendDataStrategyCb;
    }

    public void setSendDataStrategyCb(ISendDataStrategyCb sendCb) {
        this.sendDataStrategyCb = sendCb;
    }

    /**
     * 将要发送数据添加到过滤器，连续点击在1s内需要做处理
     **/
    @Override
    public void addFilter(SendPacketData packetData) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mFirstTime <= INTERVAL) {
            mFirstTime = currentTime;
            delayRunable.setPacketData(packetData);
            mHander.removeCallbacks(delayRunable);
            mHander.postDelayed(delayRunable, INTERVAL);
        } else {
            if (sendDataStrategyCb != null) {
                sendDataStrategyCb.filterData(packetData);
            }
        }
    }

    class SendDataRunnable implements Runnable {
        private SendPacketData packetData;

        public void setPacketData(SendPacketData packetData) {
            this.packetData = packetData;
        }


        @Override
        public void run() {
            if (packetData != null) {
                addFilter(packetData);
            }
        }
    }
}
