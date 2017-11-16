package com.het.open.lib.model;

import com.het.open.lib.callback.ICtrlCallback;

import java.io.Serializable;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：发送数据报文</p>
 * 名称: SendPacketData <br>
 * 作者: uuxia<br>
 * 版本: 1.0<br>
 * 日期: 2016/8/8 14:18<br>
 **/
public class SendPacketData<T> implements Serializable {
    /**
     * 数据类型
     */
    private int type;
    /**
     * 设备数据，数据格式为json
     **/
    private String json;
    /**
     * 数据
     */
    private T data;
    /**
     * 发送数据回调函数
     **/
    private ICtrlCallback callback;

    public int getType() {
        return type;
    }

    public SendPacketData setType(int type) {
        this.type = type;
        return this;
    }

    public SendPacketData(String json, ICtrlCallback callback) {
        this.json = json;
        this.callback = callback;
    }

    public T getData() {
        return data;
    }

    public SendPacketData setData(T data) {
        this.data = data;
        return this;
    }

    public String getJson() {
        return json;
    }

    public ICtrlCallback getCallback() {
        return callback;
    }
}