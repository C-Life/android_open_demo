package com.het.open.lib.auth.callback;

import android.graphics.Bitmap;

import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebView;

public  interface BrowserRequestCallBack {
    void onPageStartedCallBack(WebView paramWebView,
                               String paramString, Bitmap paramBitmap);

    boolean shouldOverrideUrlLoadingCallBack(
            WebView paramWebView, String paramString);

    void onPageFinishedCallBack(WebView paramWebView,
                                String paramString);

    void onReceivedErrorCallBack(WebView paramWebView,
                                 int paramInt, String paramString1, String paramString2);

    void onReceivedSslErrorCallBack(WebView paramWebView,
                                    SslErrorHandler paramSslErrorHandler, SslError paramSslError);
}
