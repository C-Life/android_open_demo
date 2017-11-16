package com.het.open.lib.api;

import com.het.open.lib.biz.deal.QrcodeDeal;
import com.het.open.lib.callback.IHetCallback;


/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>he授权登录：</p>
 * 名称:  授权api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetQrCodeApi {

    private static HetQrCodeApi mInstance;
    private HetQrCodeApi(){
    }


    /**
     * Get instance het qr code api.
     *
     * @return the het qr code api
     */
    public static HetQrCodeApi getInstance(){
        if (mInstance==null){
            synchronized (HetQrCodeApi.class){
                mInstance=new HetQrCodeApi();
            }
        }
        return  mInstance;
    }


    /***
     * 获取协议
     * @param iHetCallback 回调
     * @param url 二维码扫码结果
     */
    public void dealQrCode(final IHetCallback iHetCallback,String url){
        QrcodeDeal.qrcodeDeal(url,iHetCallback);
    }







}
