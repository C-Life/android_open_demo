package com.het.sdk.demo.utils;


import android.content.Context;
import android.content.Intent;

import com.het.log.Logc;

import java.util.HashSet;
import java.util.Set;

public class ApFitManager {
    private static ApFitManager instance;
    private boolean isfit;
    private Set<String> models = new HashSet<>();

    private ApFitManager() {
    }

    static {
        ApFitManager.getInstance().addModel("ALP-AL00");
        ApFitManager.getInstance().addModel("BLA-AL00");
        ApFitManager.getInstance().addModel("MI 6X");
        ApFitManager.getInstance().addModel("MI 8");
        ApFitManager.getInstance().addModel("MIX 3");
    }

    public static ApFitManager getInstance() {
        if (instance == null) {
            synchronized (ApFitManager.class) {
                if (instance == null) {
                    instance = new ApFitManager();
                }
            }
        }
        return instance;
    }

    public void addModel(String model) {
        models.add(model);
    }

    public void setFit(boolean isfit) {
        this.isfit = isfit;
    }

    public boolean isFit() {
        Logc.e("ApFitManager","model:" + android.os.Build.MODEL);
        if (models.contains(android.os.Build.MODEL) || isfit) {
            return true;
        }
        return false;
    }

    public void gotoWiFiSetting(Context context) {
        Intent intent = new Intent();
        if(android.os.Build.VERSION.SDK_INT >= 11){
            intent .setClassName("com.android.settings", "com.android.settings.Settings$WifiSettingsActivity");
        }else{
            intent .setClassName("com.android.settings" ,"com.android.settings.wifi.WifiSettings");
        }
        context.startActivity( intent);
    }

}