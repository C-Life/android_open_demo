package com.het.sdk.demo.model.led;

import java.io.Serializable;

/**
 * Created by mindray on 2017/9/11.
 */

public class LedConfigModel implements Serializable{

    /*
         * @param colorTemp 色温（0-100）Y
		 *
		 * @param switchStatus 开关状态（1-关，0-开）Y
		 *
		 * @param status 状态（0-未下发，1-正在下发，2-已下发）Y
		 *
		 * @param lightness 亮度（范围0-100） Y
		 *
		 * @param sceneMode 情景模式（0-不设置，1-阅读，2-休息，3-夜起） Y
		 *
		 * @param repeatStatus 是否重复唤醒（0-不重复；1-重复） Y
		 *
		 * @param wakeMode 唤醒模式（0-不设置，1-直接唤醒，2-日出日落） Y
		 *
		 * @param sunWeek 日出唤醒日（1-7依次代表星期一到星期日）
		 *
		 * @param sunriseHour 日出唤醒小时（0-23）
		 *
		 * @param sunriseMinute 日出唤醒分钟（0-59）
		 *
		 * @param sunsetHour 日落唤醒小时（0-23）
		 *
		 * @param sunsetMinute 日落唤醒分钟（0-59）
		 *
		 * @param directWeek 直接唤醒日（1-7依次代表星期一到星期日）
		 *
		 * @param directHour 直接唤醒小时（0-23）
		 *
		 * @param directMinute 直接唤醒分钟（0-59）
		 *
		 * @param source 配置来源(1-设备，2-WEB，3-IOS，4-Android，5、WP) Y
		 */

    private static final long serialVersionUID = 1L;

    private String colorTemp="1";
    private String switchStatus="90";
    private String lightness="1";
    private String sceneMode="1";
    private String updateFlag;

    public String getColorTemp() {
        return colorTemp;
    }

    public void setColorTemp(String colorTemp) {
        this.colorTemp = colorTemp;
    }

    public String getSwitchStatus() {
        return switchStatus;
    }

    public void setSwitchStatus(String switchStatus) {
        this.switchStatus = switchStatus;
    }

    public String getLightness() {
        return lightness;
    }

    public void setLightness(String lightness) {
        this.lightness = lightness;
    }

    public String getSceneMode() {
        return sceneMode;
    }

    public void setSceneMode(String sceneMode) {
        this.sceneMode = sceneMode;
    }

    public String getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(String updateFlag) {
        this.updateFlag = updateFlag;
    }

//    @Override
//    public byte[] paserM2B(Object object) {
//        byte[] bytes = LedOutPacket.toConfigBytes((LedConfigModel) object);
//        return bytes;
//    }

    @Override
    public String toString() {
        return "LightConfigModel{" +
                "colorTemp='" + colorTemp + '\'' +
                ", switchStatus='" + switchStatus + '\'' +
                ", lightness='" + lightness + '\'' +
                ", sceneMode='" + sceneMode + '\'' +
                ", updateFlag='" + updateFlag + '\'' +
                '}';
    }
}
