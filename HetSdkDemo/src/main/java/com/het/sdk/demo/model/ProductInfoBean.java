package com.het.sdk.demo.model;

import com.het.bind.logic.api.bind.modules.ble.model.BelDetailBean;
import com.het.bind.logic.bean.SSidInfoBean;
import com.het.bind.logic.bean.device.DeviceBrandBean;
import com.het.bind.logic.bean.device.DeviceTypeIdBean;

import java.io.Serializable;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * 作者: liuzh<br>
 * 版本: 2.0<br>
 * 日期: 2017-12-27<br>
 * 描述: 产品详情
 **/
public class ProductInfoBean implements Serializable {
    private int productId;
    private int deviceSubtypeId;
    private int developerId;
    private int deviceTypeId;
    private int productVersion;
    private String productIcon;
    private String productName;
    private String productCode;
    private String deviceKey;
    private int bindType;
    private int moduleId;
    private String createTime;
    private long modifyTime;
    private String brandName;
    private String brandIcon;
    private String radioCastName;

    private DeviceTypeIdBean deviceTypeIdBean;

    private DeviceBrandBean deviceBrandBean;
    private String apPassword;
    private String apSsid;
    private int mergeComm;
    private String ip;
    private int port;
    private String deviceMacAddr;
    private int brandId = 1;
    private int protocolVersion;
    private BelDetailBean bleBean;
    private int signal;
    private String deviceId;
    private boolean isBind;
    private String bindMode;
    private String scanResult;
    private String bindCode;
    private SSidInfoBean router;
    private boolean autoBind;
    private int moduleType;
    private String mcuVersion;
    private String bluetoothVersion;


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setDeviceSubtypeId(int deviceSubtypeId) {
        this.deviceSubtypeId = deviceSubtypeId;
    }

    public int getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(int developerId) {
        this.developerId = developerId;
    }

    public int getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(int deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public int getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(int productVersion) {
        this.productVersion = productVersion;
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

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public int getBindType() {
        return bindType;
    }

    public void setBindType(int bindType) {
        this.bindType = bindType;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandIcon() {
        return brandIcon;
    }

    public void setBrandIcon(String brandIcon) {
        this.brandIcon = brandIcon;
    }

    public String getRadioCastName() {
        return radioCastName;
    }

    public void setRadioCastName(String radioCastName) {
        this.radioCastName = radioCastName;
    }

    public DeviceTypeIdBean getDeviceTypeIdBean() {
        return deviceTypeIdBean;
    }

    public void setDeviceTypeIdBean(DeviceTypeIdBean deviceTypeIdBean) {
        this.deviceTypeIdBean = deviceTypeIdBean;
    }

    public DeviceBrandBean getDeviceBrandBean() {
        return deviceBrandBean;
    }

    public void setDeviceBrandBean(DeviceBrandBean deviceBrandBean) {
        this.deviceBrandBean = deviceBrandBean;
    }

    public String getApPassword() {
        return apPassword;
    }

    public void setApPassword(String apPassword) {
        this.apPassword = apPassword;
    }

    public String getApSsid() {
        return apSsid;
    }

    public void setApSsid(String apSsid) {
        this.apSsid = apSsid;
    }

    public int getMergeComm() {
        return mergeComm;
    }

    public void setMergeComm(int mergeComm) {
        this.mergeComm = mergeComm;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDeviceMacAddr() {
        return deviceMacAddr;
    }

    public void setDeviceMacAddr(String deviceMacAddr) {
        this.deviceMacAddr = deviceMacAddr;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public BelDetailBean getBleBean() {
        return bleBean;
    }

    public void setBleBean(BelDetailBean bleBean) {
        this.bleBean = bleBean;
    }

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isBind() {
        return isBind;
    }

    public void setBind(boolean bind) {
        isBind = bind;
    }

    public String getBindMode() {
        return bindMode;
    }

    public void setBindMode(String bindMode) {
        this.bindMode = bindMode;
    }

    public String getScanResult() {
        return scanResult;
    }

    public void setScanResult(String scanResult) {
        this.scanResult = scanResult;
    }

    public String getBindCode() {
        return bindCode;
    }

    public void setBindCode(String bindCode) {
        this.bindCode = bindCode;
    }

    public SSidInfoBean getRouter() {
        return router;
    }

    public void setRouter(SSidInfoBean router) {
        this.router = router;
    }

    public boolean isAutoBind() {
        return autoBind;
    }

    public void setAutoBind(boolean autoBind) {
        this.autoBind = autoBind;
    }

    public int getModuleType() {
        return moduleType;
    }

    public void setModuleType(int moduleType) {
        this.moduleType = moduleType;
    }

    public String getMcuVersion() {
        return mcuVersion;
    }

    public void setMcuVersion(String mcuVersion) {
        this.mcuVersion = mcuVersion;
    }

    public String getBluetoothVersion() {
        return bluetoothVersion;
    }

    public void setBluetoothVersion(String bluetoothVersion) {
        this.bluetoothVersion = bluetoothVersion;
    }

    public int getDeviceSubtypeId() {
        if (this.deviceSubtypeId > 11000) {
            this.deviceSubtypeId -= 11000;
        }

        if (this.deviceSubtypeId > 1000) {
            this.deviceSubtypeId %= 1000;
        }

        return this.deviceSubtypeId;
    }

    @Override
    public String toString() {
        return "ProductInfoBean{" +
                "productId=" + productId +
                ", deviceSubtypeId=" + deviceSubtypeId +
                ", developerId=" + developerId +
                ", deviceTypeId=" + deviceTypeId +
                ", productVersion=" + productVersion +
                ", productIcon='" + productIcon + '\'' +
                ", productName='" + productName + '\'' +
                ", productCode='" + productCode + '\'' +
                ", deviceKey='" + deviceKey + '\'' +
                ", bindType=" + bindType +
                ", moduleId=" + moduleId +
                ", createTime='" + createTime + '\'' +
                ", modifyTime=" + modifyTime +
                ", brandName='" + brandName + '\'' +
                ", brandIcon='" + brandIcon + '\'' +
                ", radioCastName='" + radioCastName + '\'' +
                ", deviceTypeIdBean=" + deviceTypeIdBean +
                ", deviceBrandBean=" + deviceBrandBean +
                ", apPassword='" + apPassword + '\'' +
                ", apSsid='" + apSsid + '\'' +
                ", mergeComm=" + mergeComm +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", deviceMacAddr='" + deviceMacAddr + '\'' +
                ", brandId=" + brandId +
                ", protocolVersion=" + protocolVersion +
                ", bleBean=" + bleBean +
                ", signal=" + signal +
                ", deviceId='" + deviceId + '\'' +
                ", isBind=" + isBind +
                ", bindMode='" + bindMode + '\'' +
                ", scanResult='" + scanResult + '\'' +
                ", bindCode='" + bindCode + '\'' +
                ", router=" + router +
                ", autoBind=" + autoBind +
                ", moduleType='" + moduleType + '\'' +
                ", mcuVersion='" + mcuVersion + '\'' +
                ", bluetoothVersion='" + bluetoothVersion + '\'' +
                '}';
    }
}
