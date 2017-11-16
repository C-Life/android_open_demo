package com.het.sdk.demo.event;



public interface DeviceStatusEvent {
    /**
     * 设备在线
     */
    int DEVICE_ONLINE = 1;
    /**
     * 设备离线
     */
    int DEVICE_OFFLINE = 2;

    public final static String DEVICE_STATUS = "status_devcie";
    /**
     * 设备不存在
     */
    public final static String DEVICE_NOT_EXIST = "not_exist_device";
}
