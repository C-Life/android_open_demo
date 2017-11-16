package com.het.open.lib.api;

import com.het.open.lib.biz.deal.ThirdcloudDeal;
import com.het.open.lib.callback.IHetCallback;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>验证码三方授权：</p>
 * 名称:  验证码三方授权api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetThirdCloudAuthApi {
    private static HetThirdCloudAuthApi mInstance;


    private HetThirdCloudAuthApi() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetThirdCloudAuthApi getInstance() {
        if (mInstance == null) {
            synchronized (HetThirdCloudAuthApi.class) {
                mInstance = new HetThirdCloudAuthApi();

            }
        }
        return mInstance;
    }


    /**
     * 1、SDK请求CLife获取授权码
     *
     * @param iCallback 回调
     */
    public void getAuthorizationCode(IHetCallback iCallback,String account,String openId) {

        ThirdcloudDeal.getAuthorizationCode(iCallback,account,openId);
    }

    /**
     * 2、SDK请求CLife验证验证码和随机码
     *
     * @param iCallback        回调
     * @param verificationCode 验证码,不提交则默认为二次授权  （可以为null）
     * @param randomCode       随机码  不能null
     * @throws Exception the exception
     */
    public void checkRandomCode(IHetCallback iCallback, String verificationCode, String randomCode){

        ThirdcloudDeal.checkRandomCode(iCallback, verificationCode, randomCode);
    }
}
