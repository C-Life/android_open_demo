package com.het.open.lib.model;


import java.io.Serializable;

/**
 * 设备model
 * @author xuchao
 */
public class DeviceModel  implements Serializable {
    private String deviceId;//设备标识
    private String macAddress; //	MAC地址
    private int deviceBrandId;  //	设备品牌标识
    private String deviceBrandName; //设备品牌名称
    private int deviceTypeId;  //设备大分类标识
    private String deviceTypeName;//设备大分类名称
    private int deviceSubtypeId;//设备子分标识
    private String deviceSubtypeName;//设备子分类名称
    private String deviceName;//	设备名称
    private String roomId;//房间标识
    private String roomName;//房间名称
    private String authUserId;//授权设备用户标识
    private String bindTime;//	绑定时间
    private volatile int onlineStatus;    //在线状态（1-正常，2-异常）
    private int share;        //设备分享（1-是，2-否
    private int controlType;//	控制类型（1-原生，2-插件，3-H5插件）
    private String userKey;//MAC与设备ID生成的KEY
    private int productId;//设备型号标识
    private String productIcon;//设备型号名称
    private String productName;//设备型号编码
    private String productCode;//设备型号图标
    private int moduleId;//模块标识
    private int moduleType;  //模块类型（1-WiFi，2-蓝牙，3-音频，4-GSM，5-红外）
    private String moduleName;//模块名称
    private String radiocastName;//设备广播名

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }



    public int getControlType() {
        return controlType;
    }

    public void setControlType(int controlType) {
        this.controlType = controlType;
    }



    public int getModuleType() {
        return moduleType;
    }

    public void setModuleType(int moduleType) {
        this.moduleType = moduleType;
    }




    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }



    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }



    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public int getModuleId() {
        return moduleId;
    }



    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }



    public String getDeviceBrandName() {
        return deviceBrandName;
    }

    public void setDeviceBrandName(String deviceBrandName) {
        this.deviceBrandName = deviceBrandName;
    }

    public int getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(int deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public int getDeviceSubtypeId() {
        return deviceSubtypeId;
    }

    public void setDeviceSubtypeId(int deviceSubtypeId) {
        this.deviceSubtypeId = deviceSubtypeId;
    }

    public String getDeviceSubtypeName() {
        return deviceSubtypeName;
    }

    public void setDeviceSubtypeName(String deviceSubtypeName) {
        this.deviceSubtypeName = deviceSubtypeName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }


    public String getBindTime() {
        return bindTime;
    }

    public void setBindTime(String bindTime) {
        this.bindTime = bindTime;
    }


    public int getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(int onlineStatus) {
        this.onlineStatus = onlineStatus;
    }


    public String getAuthUserId() {
        return authUserId;
    }

    public void setAuthUserId(String authUserId) {
        this.authUserId = authUserId;
    }



    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }



    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }





    public String getRadiocastName() {
        return radiocastName;
    }

    public void setRadiocastName(String radiocastName) {
        this.radiocastName = radiocastName;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DeviceModel) {
            if (((DeviceModel) o).getDeviceId().equals(deviceId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return deviceId.hashCode();
    }

    public void setDeviceBrandId(int deviceBrandId) {
        this.deviceBrandId = deviceBrandId;
    }

    @Override
    public String toString() {
        return "DeviceModel{" +
                "deviceId='" + deviceId + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", deviceBrandId=" + deviceBrandId +
                ", deviceBrandName='" + deviceBrandName + '\'' +
                ", deviceTypeId='" + deviceTypeId + '\'' +
                ", deviceTypeName='" + deviceTypeName + '\'' +
                ", deviceSubtypeId='" + deviceSubtypeId + '\'' +
                ", deviceSubtypeName='" + deviceSubtypeName + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", roomId='" + roomId + '\'' +
                ", roomName='" + roomName + '\'' +
                ", authUserId='" + authUserId + '\'' +
                ", bindTime='" + bindTime + '\'' +
                ", onlineStatus='" + onlineStatus + '\'' +
                ", share=" + share +
                ", controlType=" + controlType +
                ", userKey='" + userKey + '\'' +
                ", productId=" + productId +
                ", productIcon='" + productIcon + '\'' +
                ", productName='" + productName + '\'' +
                ", productCode='" + productCode + '\'' +
                ", moduleId=" + moduleId +
                ", moduleType=" + moduleType +
                ", moduleName='" + moduleName + '\'' +
                ", radiocastName='" + radiocastName + '\'' +
                '}';
    }
}

