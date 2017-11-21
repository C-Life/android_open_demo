package com.het.open.lib.auth.webviewclient;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;

import com.het.basic.data.api.token.TokenManager;
import com.het.basic.data.api.token.model.AuthModel;
import com.het.basic.utils.GsonUtil;
import com.het.open.lib.auth.LogUserInfoManager;
import com.het.open.lib.auth.biz.AuthInfo;
import com.het.open.lib.auth.biz.BaseWebParams;
import com.het.open.lib.auth.biz.HetCallbackManager;
import com.het.open.lib.auth.callback.HetAuthListener;
import com.het.open.lib.biz.api.AuthApi;
import com.het.open.lib.utils.HetException;
import com.het.open.lib.utils.Utility;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebView;


public class AuthHetWebViewClient extends HetWebViewClient {
    private Activity mAct;
    private BaseWebParams mAuthRequestParam;
    private HetAuthListener mListener;
    private boolean isCallBacked = false;
    private final String TAG = "AuthHetWebViewClient";

    public AuthHetWebViewClient(Activity activity, BaseWebParams requestParam) {
        this.mAct = activity;
        this.mAuthRequestParam = requestParam;
        this.mListener = this.mAuthRequestParam.getParams().getHetAuthListener();
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (this.mCallBack != null) {
            this.mCallBack.onPageStartedCallBack(view, url, favicon);
        }

        AuthInfo authInfo = this.mAuthRequestParam.getParams().getAuthInfo();
        if (authInfo != null && (url.startsWith(authInfo.getRedirectUrl()))
                && (!(this.isCallBacked))) {
            this.isCallBacked = true;
            handleRedirectUrl(url);
            view.stopLoading();

            HetCallbackManager manager = HetCallbackManager.getInstance(this.mAct
                    .getApplicationContext());
            if (!(TextUtils.isEmpty(this.mAuthRequestParam.getParams().getAuthListenerKey()))) {
                manager.removeHetAuthListener(this.mAuthRequestParam.getParams().getAuthListenerKey());
                this.mAct.finish();
            }

            return;
        }

        super.onPageStarted(view, url, favicon);
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (this.mCallBack != null) {
            this.mCallBack.shouldOverrideUrlLoadingCallBack(view, url);
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    public void onPageFinished(WebView view, String url) {
        if (this.mCallBack != null) {
            this.mCallBack.onPageFinishedCallBack(view, url);
        }

        super.onPageFinished(view, url);
    }

    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
        if (this.mCallBack != null) {
            this.mCallBack.onReceivedErrorCallBack(view, errorCode,
                    description, failingUrl);
        }
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                   SslError error) {
        if (this.mCallBack != null) {
            this.mCallBack.onReceivedSslErrorCallBack(view, handler, error);
        }
        // 证书错误继续访问
        handler.proceed();
        super.onReceivedSslError(view, handler, error);

    }

    private void handleRedirectUrl(String url) {
        Bundle values = Utility.parseUrl(url);
        String errorType = values.getString("error");
        String errorCode = values.getString("error_code");
        String errorDescription = values.getString("error_description");
        if ((errorType == null) && (errorCode == null)) {
            String code = values.getString("code");
            if (!TextUtils.isEmpty(code)) {
                getAuth(code);
            } else {
                this.mListener.onHetException(new HetException(
                        errorDescription, new Throwable()));
            }

        } else if (this.mListener != null) {
            this.mListener.onHetException(new HetException(errorDescription,
                    new Throwable()));

        }
    }

    /**
     * 获取auth信息
     *
     * @param code
     */
    public void getAuth(String code) {

        AuthApi.getInstance().auth(code).subscribe(authLoginModel -> {
            if (authLoginModel != null) {
                AuthModel authModel = new AuthModel();
                authModel.setAccessToken(authLoginModel.getAccessToken());
                authModel.setAccessTokenExpires(authLoginModel.getExpiresIn());
                authModel.setRefreshToken(authLoginModel.getRefreshToken());
                LogUserInfoManager.getInstance().setAuthLoginModel(authLoginModel);
                TokenManager.getInstance().setAuthModel(authModel);
                AuthHetWebViewClient.this.toResponse(GsonUtil.getInstance().getGson().toJson(authLoginModel));
            }
        }, throwable -> {
            toError(throwable.getMessage());
        });
    }


    public void toResponse(String openString) {

        if (mListener != null) {
            mListener.onComplete(openString);
        }

    }

    public void toError(String error) {
        if (mListener != null) {
            this.mListener.onHetException(new HetException(error,
                    new Throwable()));
        }

    }

}