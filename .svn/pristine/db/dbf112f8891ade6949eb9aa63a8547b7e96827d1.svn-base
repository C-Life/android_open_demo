package com.het.sdk.demo.ui.activity.singlelayout;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.utils.UIJsonConfig;

import butterknife.Bind;

/**
 * Created by mindray on 2017/9/6.
 */

public class QuestionActivity extends BaseHetActivity {

    @Bind(R.id.webview)
    WebView mWebView;

    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_question_layout;
    }

    @Override
    protected void initTopBarView() {
        mTitleView.setTitle(R.string.setting_questions);
        mTitleView.setUpNavigateMode();
        mTitleView.setBackground(UIJsonConfig.getInstance(mContext).setNavBackground_color());
    }

    @Override
    protected void initData() {
        mWebView.requestFocus();
        mWebView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            mWebView.getSettings().setMixedContentMode(0);
        }
        url = "https://cms.clife.cn/manages/mobile/cLife/faq/questions.html";
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                hideDialog();
            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        mWebView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

}
