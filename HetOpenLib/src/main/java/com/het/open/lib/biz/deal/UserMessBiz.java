package com.het.open.lib.biz.deal;

import com.het.basic.data.http.retrofit2.exception.ApiException;
import com.het.basic.model.ApiResult;
import com.het.basic.utils.GsonUtil;
import com.het.open.lib.biz.api.UserApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.api.HetCodeConstants;

import rx.functions.Action1;

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


public class UserMessBiz {

    /**
     * 获取用户信息
     *
     * @param iCallback 回调
     */
    public static void getUserMess(final IHetCallback iCallback) {

        UserApi.getInstance().getUserMess().subscribe(new Action1<ApiResult<Object>>() {
            @Override
            public void call(ApiResult<Object> objectApiResult) {
                if (objectApiResult != null) {
                    if (objectApiResult.getCode() == 0) {
                        iCallback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS, GsonUtil.getInstance().getGson().toJson(objectApiResult.getData()));
                    }
                }

            }
        }, throwable -> {
            if (throwable instanceof ApiException) {
                ApiException apiException = (ApiException) throwable;
                //Logc.e(apiException.getMessage());
                iCallback.onFailed(apiException.getCode(), apiException.getMessage());
            }

        });


    }
}
