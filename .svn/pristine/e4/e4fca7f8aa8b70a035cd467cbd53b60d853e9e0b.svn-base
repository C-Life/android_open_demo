package com.het.sdk.demo.ui.activity.singlelayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.ui.activity.feedback.FeedbackAddActivity;
import com.het.sdk.demo.utils.UIJsonConfig;
import com.het.sdk.demo.widget.ItemView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mindray on 2017/9/6.
 */

public class MyActivity extends BaseHetActivity {


    @Bind(R.id.v_current_count)
    ItemView vCurrentCount;
    @Bind(R.id.v_update_password)
    ItemView vUpdatePassword;
    @Bind(R.id.itemview_mymsg)
    ItemView itemviewMymsg;
    @Bind(R.id.iv_unread)
    ImageView ivUnread;
    @Bind(R.id.v_normal_question)
    ItemView vNormalQuestion;
    @Bind(R.id.v_feedback)
    ItemView vFeedback;
    @Bind(R.id.v_privacy)
    ItemView vPrivacy;
    @Bind(R.id.v_copyright)
    ItemView vCopyright;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initTopBarView() {
        mTitleView.setTitle(R.string.my_info);
        mTitleView.setUpNavigateMode();
        mTitleView.setBackground(UIJsonConfig.getInstance(mContext).setNavBackground_color());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {


    }

    @OnClick({R.id.v_update_password, R.id.itemview_mymsg, R.id.iv_unread, R.id.v_normal_question, R.id.v_feedback, R.id.v_privacy, R.id.v_copyright})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.v_update_password:
                break;
            case R.id.itemview_mymsg:
                break;
            case R.id.iv_unread:
                break;
            case R.id.v_normal_question:
                jumpToTarget(QuestionActivity.class);
                break;
            case R.id.v_feedback:
                jumpToTarget(FeedbackAddActivity.class);
                break;
            case R.id.v_privacy:
                break;
            case R.id.v_copyright:
                break;
        }
    }


}
