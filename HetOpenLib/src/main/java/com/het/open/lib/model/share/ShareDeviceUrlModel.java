package com.het.open.lib.model.share;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-10-10.
 */

public class ShareDeviceUrlModel implements Serializable {
    private String schema;
    private String deviceName;
    private String deviceIcon;
    private String shareCode;
    private String androidUrl;
    private String iosUrl;
    private String shareType;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceIcon() {
        return deviceIcon;
    }

    public void setDeviceIcon(String deviceIcon) {
        this.deviceIcon = deviceIcon;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public String getAndroidUrl() {
        return androidUrl;
    }

    public void setAndroidUrl(String androidUrl) {
        this.androidUrl = androidUrl;
    }

    public String getIosUrl() {
        return iosUrl;
    }

    public void setIosUrl(String iosUrl) {
        this.iosUrl = iosUrl;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }
}
