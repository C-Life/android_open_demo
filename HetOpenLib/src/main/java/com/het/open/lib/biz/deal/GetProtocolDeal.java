package com.het.open.lib.biz.deal;

import com.het.open.lib.biz.constans.GlobalAddr;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.biz.constans.DeviceParamContant;

import java.util.TreeMap;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  <br>
 * 作者: 80010814 4<br>
 * 日期: 2017/8/4<br>
 **/


public class GetProtocolDeal {

    /**
     * 获取用户信息
     *
     * @param iCallback 回调
     * @param productId 产品id
     */
    public static void getProtocol(final IHetCallback iCallback,String productId, String data) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put(DeviceParamContant.Device.PRODUCTID, productId);
        params.put(DeviceParamContant.Device.APP_TYPE, "1");
        params.put("protocolDate", data);
        String path = "/v1/protoManage/getProtocolListByProductId";
        String url= GlobalAddr.HOST;
        if (url.contains("dp")||url.contains("50")){
            path="v1/app/open/protoManage/getProtocolListByProductId";
        }
        HttpDeal.hetPost(path,params,iCallback);


    }


}
