package com.het.open.lib.biz.deal;

import com.het.basic.data.http.retrofit2.exception.ApiException;
import com.het.open.lib.biz.api.FeedbackApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.api.HetCodeConstants;


/**
 * Created by Administrator on 2017-09-08.
 */

public class FeedbackDeal {
    /**
     * 意见反馈
     *
     * @param callback the callback
     * @param contact  联系方式
     * @param content  反馈内容
     */
    public static void addFeedback(final IHetCallback callback, String contact, String content) {
        FeedbackApi.getInstance().addFeedback(contact, content).subscribe(objectApiResult -> {
            if (objectApiResult != null) {
                if (objectApiResult.getCode() == 0) {
                    Object data = objectApiResult.getData();
                    if (callback != null) {
                        callback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS, data != null ? data.toString() : null);
                    }
                } else {
                    if (callback != null) {
                        callback.onSuccess(HetCodeConstants.HTTP_RET_FAILED, "server error");
                    }
                }
            } else {
                if (callback != null) {
                    callback.onSuccess(HetCodeConstants.HTTP_RET_FAILED, "server error");
                }
            }
        }, throwable -> {
            if (throwable instanceof ApiException) {
                if (callback != null) {
                    callback.onFailed(((ApiException) throwable).getCode(), throwable.getMessage());
                }
            } else {
                if (callback != null) {
                    callback.onSuccess(HetCodeConstants.HTTP_RET_FAILED, "server error");
                }
            }
        });

    }
}
