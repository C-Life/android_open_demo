package com.het.open.lib.model.romUdgrade;

/**
 * Created by weatherfish on 2015/12/22.
 */

import java.io.Serializable;

/***
 * 升级进度model
 */
public class DeviceUpdateProgessModel implements Serializable {
    private String deviceVersionId;//设备版本标示
    private String progress;//进度（0-100）
    private String upgradeStatus;

    public String getDeviceVersionId() {
        return deviceVersionId;
    }

    public void setDeviceVersionId(String deviceVersionId) {
        this.deviceVersionId = deviceVersionId;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getUpgradeStatus() {
        return upgradeStatus;
    }

    public void setUpgradeStatus(String upgradeStatus) {
        this.upgradeStatus = upgradeStatus;
    }
}

