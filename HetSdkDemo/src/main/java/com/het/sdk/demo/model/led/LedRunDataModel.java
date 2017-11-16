package com.het.sdk.demo.model.led;

import java.io.Serializable;

/**
 * Created by mindray on 2017/9/11.
 */

public class LedRunDataModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String recordTime;
    private String lightness;
    private String colorTemp;
    private String sceneMode;
    private String wakeMode;
    private String switchStatus;
    private String onlineStatus;

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getLightness() {
        return lightness;
    }

    public void setLightness(String lightness) {
        this.lightness = lightness;
    }

    public String getColorTemp() {
        return colorTemp;
    }

    public void setColorTemp(String colorTemp) {
        this.colorTemp = colorTemp;
    }

    public String getSceneMode() {
        return sceneMode;
    }

    public void setSceneMode(String sceneMode) {
        this.sceneMode = sceneMode;
    }

    public String getWakeMode() {
        return wakeMode;
    }

    public void setWakeMode(String wakeMode) {
        this.wakeMode = wakeMode;
    }

    public String getSwitchStatus() {
        return switchStatus;
    }

    public void setSwitchStatus(String switchStatus) {
        this.switchStatus = switchStatus;
    }


    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    @Override
    public String toString() {
        return "LightRunDataModel{" +
                "recordTime='" + recordTime + '\'' +
                ", lightness='" + lightness + '\'' +
                ", colorTemp='" + colorTemp + '\'' +
                ", sceneMode='" + sceneMode + '\'' +
                ", wakeMode='" + wakeMode + '\'' +
                ", switchStatus='" + switchStatus + '\'' +
                ", onlineStatus='" + onlineStatus + '\'' +
                '}';
    }
}
