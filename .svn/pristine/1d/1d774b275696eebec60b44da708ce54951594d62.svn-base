package com.het.sdk.demo.ui.activity.feedback;

import com.het.basic.utils.StringUtils;
import com.het.open.lib.api.HetFeedbackApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.base.BaseHetPresenter;
import com.het.sdk.demo.base.BaseHetView;

/**
 * Created by Administrator on 2017-09-08.
 * 意见反馈
 */

public class FeedbackPersenter extends BaseHetPresenter<BaseHetView> {

    public void addFeedback(String content) {
        if (StringUtils.isNull(content)) {
            ((BaseHetActivity) activity).showToast("意见反馈的内容不能为空");
            return;
        }
        HetFeedbackApi.getInstance().addFeedback(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (code == 0) {
                    ((BaseHetActivity) activity).showToast("意见反馈发送成功");
                } else {
                    ((BaseHetActivity) activity).showToast(msg);
                }
                activity.finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                ((BaseHetActivity) activity).showToast("意见反馈发送失败");
            }
        }, null, content);
    }
}
