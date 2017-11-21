package com.het.open.lib.api;

import com.het.open.lib.biz.deal.FeedbackDeal;
import com.het.open.lib.callback.IHetCallback;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称: 意见反馈<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/10/28. 16:42<br>
 */
public class HetFeedbackApi {

    private static HetFeedbackApi mInstance;

    private HetFeedbackApi() {
    }


    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetFeedbackApi getInstance() {
        if (mInstance == null) {
            synchronized (HetFeedbackApi.class) {
                mInstance = new HetFeedbackApi();
            }
        }
        return mInstance;
    }


    /**
     * 意见反馈
     *
     * @param iCallback the callback
     * @param contact   联系方式
     * @param content   反馈内容
     */
    public void addFeedback(final IHetCallback iCallback, String contact, String content) {
        FeedbackDeal.addFeedback(iCallback, contact, content);
    }

}
