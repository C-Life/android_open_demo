package com.het.sdk.demo.ui.activity.bind;

import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.utils.UIJsonConfig;

import butterknife.Bind;

public class DeviceReadmeActivity extends BaseHetActivity {

    @Bind(R.id.bind_readme)
    WebView webview;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_readme;
    }

    @Override
    protected void initData() {
        if (webview != null) {
            webview.loadUrl(getUrl());
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initWebView();
    }

    private void initWebView() {
        webview.setHorizontalScrollBarEnabled(false);
        WebSettings webSetting = webview.getSettings();
        webSetting.setDefaultTextEncodingName("GBK");
        // User settings
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        webSetting.setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        webSetting.setSupportZoom(false);//是否可以缩放，默认true
        webSetting.setBuiltInZoomControls(false);//是否显示缩放按钮，默认false
        webSetting.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        webSetting.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        webSetting.setAppCacheEnabled(true);//是否使用缓存
        webSetting.setDomStorageEnabled(true);//DOM Storage
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) //5.0以上不能加载http与https混合内容
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
    }

    @Override
    protected void initTopBarView() {
        mTitleView.setTitle(getString(R.string.bind_readme));
        mTitleView.setUpNavigateMode();
        mTitleView.setBackground(UIJsonConfig.getInstance(mContext).setNavBackground_color());
    }


    private String getUrl() {
        return "file:///android_asset/bindreadme/bindHelp.html";
    }

}
