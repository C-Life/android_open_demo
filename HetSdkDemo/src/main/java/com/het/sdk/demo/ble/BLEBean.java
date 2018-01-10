package com.het.sdk.demo.ble;

/**
 * Created by: Administrator.
 * Created time: 2017-03-01.
 * Desc:
 */
public class BLEBean {
    private String bleData="";
    private int cmdType;

    public String getBleData() {
        return bleData;
    }

    public void setBleData(String bleData) {
        this.bleData = bleData;
    }

    public int getCmdType() {
        return cmdType;
    }

    public void setCmdType(int cmdType) {
        this.cmdType = cmdType;
    }
}
