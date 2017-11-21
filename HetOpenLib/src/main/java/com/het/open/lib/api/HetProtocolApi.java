package com.het.open.lib.api;

import com.het.open.lib.biz.deal.GetProtocolDeal;
import com.het.open.lib.callback.IHetCallback;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  获取设备协议api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetProtocolApi {
    private static HetProtocolApi mInstance;

    private HetProtocolApi() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetProtocolApi getInstance() {
        if (mInstance == null) {
            synchronized (HetProtocolApi.class) {
                mInstance = new HetProtocolApi();
            }
        }
        return mInstance;
    }

    /***
     * 获取协议
     * @param iHetCallback 回调
     * @param productId 产品id
     * @param type 协议类型 0或者不传-完整协议	，包括以下协议内容 1-设备基本信息 2-控制数据 3-运行数据 4-故障数据
     */
    public  void getProtocol(final IHetCallback iHetCallback,int productId,int type){
        GetProtocolDeal.getProtocol(iHetCallback,productId+"",type+"");
    }





}
