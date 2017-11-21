package com.het.open.lib.api;

import com.het.open.lib.biz.deal.DeviceListDeal;
import com.het.open.lib.callback.IHetCallback;


/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  设备列表api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetDeviceListApi {
    private static HetDeviceListApi mInstance;


    private HetDeviceListApi() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetDeviceListApi getInstance() {
        if (mInstance == null) {
            synchronized (HetDeviceListApi.class) {
                mInstance = new HetDeviceListApi();
               
            }
        }
        return mInstance;
    }

    /**
     * 获取绑定设备列表
     *
     * @param iCallback the callback
     */
    public void getBindList(final IHetCallback iCallback) {
        DeviceListDeal.getBindList(iCallback);



    }

    /**
     * 获取大类型设备列表
     *
     * @param iCallback the callback
     */
    public void getTypeList(final IHetCallback iCallback) {
        DeviceListDeal.getTypeList(iCallback);
    }

    /**
     * 获取设备小类型列表
     *
     * @param iCallback the callback
     * @param type      设备大类型
     */
    public void getSubTypeList(final IHetCallback iCallback, String type) {
        DeviceListDeal.getSubTypeList(iCallback,type);


    }

    /**
     * 获取设备小类型列表
     *
     * @param iCallback the callback
     * @param type      设备大类型
     */
    public void getSubTypeListProduct(final IHetCallback iCallback, String type) {
        DeviceListDeal.getSubTypeListProduct(iCallback,type);


    }


}
