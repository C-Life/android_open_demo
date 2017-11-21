package com.het.sdk.demo.ui.activity.feedback;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.utils.UIJsonConfig;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mindray on 2017/9/6.
 */

public class FeedbackAddActivity extends BaseHetActivity<FeedbackPersenter> {


    @Bind(R.id.ev_suggestion)
    EditText evSuggestion;
    @Bind(R.id.tv_submint)
    TextView tvSubmint;
    @Bind(R.id.text_total)
    TextView textTotal;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback_add;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        evSuggestion.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int after_length = s.length();
            setTextLenth(after_length);
        }
    };

    private void setTextLenth(int length) {
        runOnUiThread(() -> {
            textTotal.setText(length + "/300");
        });
    }

    @Override
    protected void initTopBarView() {
        mTitleView.setTitle(R.string.feedback);
        mTitleView.setBackground(UIJsonConfig.getInstance(mContext).setNavBackground_color());
    }

    @OnClick({R.id.tv_submint})
    public void onClick() {
        String content = evSuggestion.getText().toString();
        mPresenter.addFeedback(content);
    }

}
