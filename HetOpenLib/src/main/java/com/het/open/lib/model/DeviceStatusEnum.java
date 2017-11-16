package com.het.open.lib.model;

public enum DeviceStatusEnum {
    SERVER_ONLINE(1, "大循环在线"),
    OFFLINE(2, "设备离线"),
    UDP_ONLINE(3, "小循环在线");

    private int status;

    private DeviceStatusEnum(int var3, String var4) {
        this.status = var3;
    }

    public int getStatus() {
        return this.status;
    }
}
