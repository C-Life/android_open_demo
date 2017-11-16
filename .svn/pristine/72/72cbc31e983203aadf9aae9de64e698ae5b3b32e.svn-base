package com.het.open.lib.biz.api;

import com.het.basic.base.helper.RxSchedulers;
import com.het.basic.data.api.token.HetParamsMerge;
import com.het.basic.data.http.okhttp.util.OkHttpTag;
import com.het.basic.data.http.retrofit2.RetrofitManager;
import com.het.basic.model.ApiResult;
import com.het.basic.utils.StringUtils;
import com.het.open.lib.biz.constans.GlobalAddr;
import com.het.open.lib.biz.services.FeedbackService;

import rx.Observable;

/**
 * Created by Administrator on 2017-09-08.
 */

public class FeedbackApi {
    private static FeedbackApi instance = null;
    private FeedbackService api;

    private void createapi() {
        api = RetrofitManager.getRetrofit(new OkHttpTag(this.getClass().getName())).create(FeedbackService.class);
    }

    public static FeedbackApi getInstance() {
        if (instance == null) {
            synchronized (FeedbackApi.class) {
                if (instance == null) {
                    instance = new FeedbackApi();
                }
            }
        }
        instance.createapi();
        return instance;
    }


    /**
     * 意见反馈
     */
    public Observable<ApiResult<Object>> addFeedback(String contact, String content) {
        HetParamsMerge params = new HetParamsMerge();
        if (!StringUtils.isNull(contact)) {
            params.add("contact", contact);
        }
        params.add("feedbackType", "4");
        params.add("content", content);
        params.add("source", "2");
        String path = "/v1/feedback/addFeedback";
        String host= GlobalAddr.HOST;
        if (host.contains("dp")||host.contains("50")){
            path="/v1/app/open/feedback/addFeedback";
        }
        return api.addFeedback(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }

}
