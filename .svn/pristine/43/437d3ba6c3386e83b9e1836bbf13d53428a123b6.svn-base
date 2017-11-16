package com.het.open.lib.api;

import android.content.Context;

import com.het.basic.model.DeviceBean;
import com.het.open.lib.biz.deal.H5ControlPlugDeal;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  用户api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetH5Api {
    private static HetH5Api mInstance;

    private HetH5Api() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetH5Api getInstance() {
        if (mInstance == null) {
            synchronized (HetH5Api.class) {
                mInstance = new HetH5Api();
            }
        }
        return mInstance;
    }


    /**
     * 获取用户信息
     *
     * @param context 回调
     * @param deviceBean 设备
     */
    public void getH5ControlPlug(Context context, DeviceBean deviceBean) {
        H5ControlPlugDeal.getH5ControlPlug(context,deviceBean);
    }




}
