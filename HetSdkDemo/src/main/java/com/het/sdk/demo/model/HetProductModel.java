package com.het.sdk.demo.model;

import java.io.Serializable;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * 作者: liuzh<br>
 * 版本: 2.0<br>
 * 日期: 2017-12-27<br>
 * 描述: 产品信息
 **/
public class HetProductModel implements Serializable {
    private int productId;
    private int deviceSubtypeId;
    private String deviceSubTypeName;
    private String productIcon;
    private String productName;
    private String productCode;

    private int bindType;
    private int moduleId;
    private String moduleName;
    private int moduleType;
    private int deviceTypeId;
    private String radiocastName;
    private String deviceTypeName;
    private String deviceCode;
    private String guideUrl;

    public int getBindType() {
        return bindType;
    }

    public void setBindType(int bindType) {
        this.bindType = bindType;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public int getDeviceSubtypeId() {
        return deviceSubtypeId;
    }

    public void setDeviceSubtypeId(int deviceSubtypeId) {
        this.deviceSubtypeId = deviceSubtypeId;
    }

    public String getDeviceSubTypeName() {
        return deviceSubTypeName;
    }

    public void setDeviceSubTypeName(String deviceSubTypeName) {
        this.deviceSubTypeName = deviceSubTypeName;
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

    public String getGuideUrl() {
        return guideUrl;
    }

    public void setGuideUrl(String guideUrl) {
        this.guideUrl = guideUrl;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getModuleType() {
        return moduleType;
    }

    public void setModuleType(int moduleType) {
        this.moduleType = moduleType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRadiocastName() {
        return radiocastName;
    }

    public void setRadiocastName(String radiocastName) {
        this.radiocastName = radiocastName;
    }

    @Override
    public String toString() {
        return "HetProductModel{" +
                "bindType=" + bindType +
                ", productId=" + productId +
                ", deviceSubtypeId=" + deviceSubtypeId +
                ", deviceSubTypeName='" + deviceSubTypeName + '\'' +
                ", productIcon='" + productIcon + '\'' +
                ", productName='" + productName + '\'' +
                ", productCode='" + productCode + '\'' +
                ", moduleId=" + moduleId +
                ", moduleName='" + moduleName + '\'' +
                ", moduleType=" + moduleType +
                ", deviceTypeId=" + deviceTypeId +
                ", radiocastName='" + radiocastName + '\'' +
                ", deviceTypeName='" + deviceTypeName + '\'' +
                ", deviceCode='" + deviceCode + '\'' +
                ", guideUrl='" + guideUrl + '\'' +
                '}';
    }
}
