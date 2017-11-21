package com.het.open.lib.api;

import com.het.open.lib.biz.deal.UserMessDeal;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.UserModel;

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
public class HetPushApi {
    private static HetPushApi mInstance;

    private HetPushApi() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetPushApi getInstance() {
        if (mInstance == null) {
            synchronized (HetPushApi.class) {
                mInstance = new HetPushApi();
            }
        }
        return mInstance;
    }


    /**
     * 获取用户信息
     *
     * @param iCallback 回调
     */
    public void getUserMess(final IHetCallback iCallback) {
        UserMessDeal.getUserMess(iCallback);
    }

    /**
     * 更新用户信息
     *
     * @param userModel 用户model
     * @param iCallback 回调
     */
    public void updateUserMess(UserModel userModel,final IHetCallback iCallback) {

    }




}
