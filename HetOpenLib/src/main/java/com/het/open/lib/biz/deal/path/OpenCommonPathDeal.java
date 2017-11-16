package com.het.open.lib.biz.deal.path;

import com.het.basic.constact.ComParamContact;
import com.het.h5.sdk.manager.H5PathConstants;
import com.het.open.lib.biz.constans.GlobalAddr;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  <br>
 * 作者: 80010814 4<br>
 * 日期: 2017/11/6<br>
 **/


public class OpenCommonPathDeal {

    public static void  dealPath(){
        String host= GlobalAddr.HOST;
        if (host.contains("dp")||host.contains("50")){
            H5PathConstants.H5_GET_PATH="/v1/app/cms/app/upgrade/get";
            H5PathConstants.H5_GETLATEST_PATH="/v1/app/cms/app/upgrade/getLatestVersion";
        }else {
            H5PathConstants.H5_GET_PATH="/v1/app/upgrade/get";
            H5PathConstants.H5_GETLATEST_PATH="/v1/app/upgrade/getLatestVersion";
            ComParamContact.Token.PATH="oauth2/refreshToken";
        }
    }
}
