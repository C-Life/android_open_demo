package com.het.open.lib.auth.biz;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.het.open.lib.auth.callback.HetAuthListener;

import static com.het.open.lib.auth.biz.AuthRequestParam.EXTRA_KEY_AUTHINFO;
import static com.het.open.lib.auth.biz.AuthRequestParam.EXTRA_KEY_LISTENER;

/**
 * Created by Administrator on 2017-08-18.
 */

public class BaseWebParams {
    public static final String EXTRA_KEY_REQUEST = "key_request";
    protected Context mContext;
    public Parsms mParams;

    public Parsms getParams() {
        return mParams;
    }

    public BaseWebParams(Context context) {
        this.mParams.mLaucher = BrowserLauncher.AUTH;
        this.mContext = context;
    }

    public BaseWebParams(Parsms parsms) {
        this.mParams = parsms;
        this.mParams.mLaucher = BrowserLauncher.AUTH;
        this.mContext = parsms.mContext;
    }

    public void setupRequestParam(Bundle data) {
        mParams.mUrl = data.getString("key_url");
        mParams.mPhone = data.getString("key_phone");
        mParams.mLaucher = ((BrowserLauncher) data.getSerializable("key_launcher"));
        mParams.mSpecifyTitle = data.getString("key_specify_title");
        mParams.navBackground = data.getInt("key_nav_background");
        mParams.navigationBarTextColor = data.getInt("key_navigation_color");
        Bundle authInfoBundle = data.getBundle(EXTRA_KEY_AUTHINFO);
        if (authInfoBundle != null) {
            mParams.authInfo = AuthInfo.parseBundleData(this.mContext,
                    authInfoBundle);
        }
        mParams.mAuthListenerKey = data.getString(EXTRA_KEY_LISTENER);
        if (!(TextUtils.isEmpty(mParams.mAuthListenerKey)))
            mParams.hetAuthListener = HetCallbackManager.getInstance(this.mContext).getHetAuthListener(mParams.mAuthListenerKey);
    }

    public Bundle createRequestParamBundle() {
        Bundle data = new Bundle();
        if (!(TextUtils.isEmpty(mParams.mUrl))) {
            data.putString("key_url", mParams.mUrl);
        }
        if (!(TextUtils.isEmpty(mParams.mPhone))) {
            data.putString("key_phone", mParams.mPhone);
        }
        if (mParams.mLaucher != null) {
            data.putSerializable("key_launcher", mParams.mLaucher);
        }
        if (!(TextUtils.isEmpty(mParams.mSpecifyTitle))) {
            data.putString("key_specify_title", mParams.mSpecifyTitle);
        }
        if (mParams.navBackground != 0) {
            data.putInt("key_nav_background", mParams.navBackground);
        }
        if (mParams.navigationBarTextColor != 0) {
            data.putInt("key_navigation_color", mParams.navigationBarTextColor);
        }
        if (mParams.authInfo != null) {
            data.putBundle(EXTRA_KEY_AUTHINFO, this.mParams.authInfo.getAuthBundle());
        }
        if (mParams.hetAuthListener != null) {
            HetCallbackManager manager = HetCallbackManager
                    .getInstance(this.mContext);
            mParams.mAuthListenerKey = manager.genCallbackKey();
            manager.setHetAuthListener(mParams.mAuthListenerKey, mParams.hetAuthListener);
            data.putString(EXTRA_KEY_LISTENER, mParams.mAuthListenerKey);
        }

        return data;
    }


    public void execRequest(Activity act, int action) {
        if (action == 3) {
            if (mParams.hetAuthListener != null) {
                mParams.hetAuthListener.onCancel();
            }
            HetCallbackManager manager = HetCallbackManager.getInstance(act
                    .getApplicationContext());
            if (!(TextUtils.isEmpty(mParams.mAuthListenerKey))) {
                manager.removeHetAuthListener(mParams.mAuthListenerKey);
                act.finish();
            }
        }
    }

    public static class Parsms {
        Context mContext;
        String mUrl;
        BrowserLauncher mLaucher;
        String mSpecifyTitle;
        AuthInfo authInfo;
        HetAuthListener hetAuthListener;
        String mAuthListenerKey;
        String mPhone;
        int navigationBarTextColor;
        int navBackground;

        public int getNavBackground() {
            return navBackground;
        }

        public int getNavigationBarTextColor() {
            return navigationBarTextColor;
        }

        public String getAuthListenerKey() {
            return mAuthListenerKey;
        }

        public void setAuthListenerKey(String mAuthListenerKey) {
            this.mAuthListenerKey = mAuthListenerKey;
        }

        public String getUrl() {
            return mUrl;
        }

        public String getPhone() {
            return mPhone;
        }

        public BrowserLauncher getLaucher() {
            return mLaucher;
        }

        public String getSpecifyTitle() {
            return mSpecifyTitle;
        }

        public AuthInfo getAuthInfo() {
            return authInfo;
        }

        public HetAuthListener getHetAuthListener() {
            return hetAuthListener;
        }

    }

    public static class Builder {
        private static Parsms P;

        public Builder(Context context) {
            P = new BaseWebParams.Parsms();
            P.mContext = context;
        }

        public Context getContext() {
            return P.mContext;
        }

        public Builder setUrl(String url) {
            P.mUrl = url;
            return this;
        }

        public Builder setPhone(String phone) {
            P.mPhone = phone;
            return this;
        }

        public Builder setLaucher(BrowserLauncher launcher) {
            P.mLaucher = launcher;
            return this;
        }

        public Builder setSpecifyTitle(String title) {
            P.mSpecifyTitle = title;
            return this;
        }

        public Builder setAuthInfo(AuthInfo authInfo) {
            P.authInfo = authInfo;
            return this;
        }
        public Builder setNavigationBarTextColor(int navigationBarTextColor) {
            P.navigationBarTextColor = navigationBarTextColor;
            return this;
        }
        public Builder setNavBackground(int navBackground) {
            P.navBackground = navBackground;
            return this;
        }


        public Builder setAuthListener(HetAuthListener mAuthListener) {
            P.hetAuthListener = mAuthListener;
            if (mAuthListener != null) {
                HetCallbackManager manager = HetCallbackManager
                        .getInstance(P.mContext);
                P.mAuthListenerKey = manager.genCallbackKey();
                manager.setHetAuthListener(P.mAuthListenerKey, mAuthListener);
            }
            return this;
        }

        public BaseWebParams create() {
            // Context has already been wrapped with the appropriate theme.
            final BaseWebParams baseWebParams = new BaseWebParams(P);
            return baseWebParams;
        }

    }
}
