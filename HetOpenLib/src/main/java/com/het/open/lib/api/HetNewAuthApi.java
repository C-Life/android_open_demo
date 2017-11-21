package com.het.open.lib.api;

import android.content.Context;
import android.text.TextUtils;

import com.het.basic.utils.StringUtils;
import com.het.open.lib.auth.HetAuthMess;
import com.het.open.lib.auth.biz.AuthInfo;
import com.het.open.lib.auth.biz.AuthListener;
import com.het.open.lib.auth.biz.BaseWebParams;
import com.het.open.lib.auth.biz.HetParameters;
import com.het.open.lib.auth.biz.ModifyPasswordLister;
import com.het.open.lib.biz.constans.GlobalAddr;
import com.het.open.lib.callback.AuthCallback;
import com.het.open.lib.ui.H5CommonActivity;

import static com.het.open.lib.auth.biz.AuthDeal.REDIRECT_URL;

/**
 * Created by Administrator on 2017-08-18.
 */
public class HetNewAuthApi {

    private static HetNewAuthApi mInstance;

    private HetNewAuthApi() {
    }


    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetNewAuthApi getInstance() {
        if (mInstance == null) {
            synchronized (HetNewAuthApi.class) {
                mInstance = new HetNewAuthApi();
            }
        }
        return mInstance;
    }


    /**
     * 授权登录
     *
     * @param mContext               the m context
     * @param authCallback           回调
     * @param specifyTitle           title 文字
     * @param navigationBarTextColor title 文字颜色
     * @param navBackground          title 背景颜色
     * @throws Exception the exception
     */
    public void authorize(Context mContext, AuthCallback authCallback, String specifyTitle, int navigationBarTextColor, int navBackground) {
        if (mContext == null) throw new IllegalArgumentException("mContext can't  be null");

        AuthInfo mAuthInfo = new AuthInfo(mContext, HetAuthMess.getInstance().getAppId(), REDIRECT_URL, HetAuthMess.getInstance().getAppKey());
        HetParameters requestParams = new HetParameters(
                mAuthInfo.getAppKey());
        requestParams.put("appId", mAuthInfo.getAppKey());
        requestParams.put("redirectUri", mAuthInfo.getRedirectUrl());
        requestParams.put("scope", mAuthInfo.getAppSecret());
        requestParams.put("state", "code");
        requestParams.put("isNew", "1");
        String title = mAuthInfo.getTitle();
        if (!TextUtils.isEmpty(title)) {
            requestParams.put("loginTitle", title);
        }
        String authUrl = GlobalAddr.HOST;
        if (authUrl.contains("dp") || authUrl.contains("50")) {
            authUrl = authUrl + "/v1/app/open";
        }
        String url = authUrl + "/oauth2/authorize?" + requestParams.encodeUrl();

        BaseWebParams params = new BaseWebParams.Builder(mContext)
                .setAuthInfo(mAuthInfo)
                .setAuthListener(new AuthListener(mContext, authCallback))
                .setSpecifyTitle(specifyTitle)
                .setUrl(url)
                .setNavigationBarTextColor(navigationBarTextColor)
                .setNavBackground(navBackground)
                .create();
        H5CommonActivity.startH5CommonActivity(mContext, params.createRequestParamBundle());
    }
    //com.nnddkj.facialmaskble
    //191f295eb5059a82650fd425d6284881

    /**
     * 授权登录
     *
     * @param mContext               the m context
     * @param authCallback           回调
     * @param specifyTitle           title 文字
     * @throws Exception the exception
     */
    public void authorize(Context mContext, AuthCallback authCallback, String specifyTitle) {
        if (mContext == null) throw new IllegalArgumentException("mContext can't  be null");

        AuthInfo mAuthInfo = new AuthInfo(mContext, HetAuthMess.getInstance().getAppId(), REDIRECT_URL, HetAuthMess.getInstance().getAppKey());
        HetParameters requestParams = new HetParameters(
                mAuthInfo.getAppKey());
        requestParams.put("appId", mAuthInfo.getAppKey());
        requestParams.put("redirectUri", mAuthInfo.getRedirectUrl());
        requestParams.put("scope", mAuthInfo.getAppSecret());
        requestParams.put("state", "code");
        requestParams.put("isNew", "1");
        String title = mAuthInfo.getTitle();
        if (!TextUtils.isEmpty(title)) {
            requestParams.put("loginTitle", title);
        }
        String authUrl = GlobalAddr.HOST;
        if (authUrl.contains("dp") || authUrl.contains("50")) {
            authUrl = authUrl + "/v1/app/open";
        }
        String url = authUrl + "/oauth2/authorize?" + requestParams.encodeUrl();

        BaseWebParams params = new BaseWebParams.Builder(mContext)
                .setAuthInfo(mAuthInfo)
                .setAuthListener(new AuthListener(mContext, authCallback))
                .setSpecifyTitle(specifyTitle)
                .setUrl(url)
                .create();
        H5CommonActivity.startH5CommonActivity(mContext, params.createRequestParamBundle());
    }


    /**
     * 修改密码
     *
     * @param mContext               上下文
     * @param authCallback           回调
     * @param phone                  手机号
     * @param specifyTitle           title 文字
     * @param navigationBarTextColor title 文字颜色
     * @param navBackground          title 背景颜色
     * @throws Exception the exception
     */
    public void alterPassword(Context mContext, AuthCallback authCallback, String phone, String specifyTitle, int navigationBarTextColor, int navBackground) {
        if (mContext == null) throw new IllegalArgumentException("mContext can't  be null");
        if (StringUtils.isNull(phone)) throw new IllegalArgumentException("phone can't  be null");
        String authUrl = GlobalAddr.HOST;
        if (authUrl.contains("dp") || authUrl.contains("50")) {
        } else if (authUrl.contains("open.api.clife.cn")) {
            authUrl = GlobalAddr.PREVIEW_MODIFY_PWD_HOST ;
        } else if (authUrl.contains("test.open.api.clife.cn")) {
            authUrl = GlobalAddr.RELEASW_MODIFY_PWD_HOST ;
        }

        String url = authUrl + "/wifiSetting/clife-open-app/page/alterPassword.html";
        BaseWebParams params = new BaseWebParams.Builder(mContext)
                .setAuthListener(new ModifyPasswordLister(mContext, authCallback))
                .setSpecifyTitle(specifyTitle)
                .setUrl(url)
                .setPhone(phone)
                .setNavigationBarTextColor(navigationBarTextColor)
                .setNavBackground(navBackground)
                .create();
        H5CommonActivity.startH5CommonActivity(mContext, params.createRequestParamBundle());
    }


    /**
     * 修改密码
     *
     * @param mContext               上下文
     * @param authCallback           回调
     * @param phone                  手机号
     * @param specifyTitle           title 文字
     * @throws Exception the exception
     */
    public void alterPassword(Context mContext, AuthCallback authCallback, String phone, String specifyTitle) {
        if (mContext == null) throw new IllegalArgumentException("mContext can't  be null");
        if (StringUtils.isNull(phone)) throw new IllegalArgumentException("phone can't  be null");
        String authUrl = GlobalAddr.HOST;
        if (authUrl.contains("dp") || authUrl.contains("50")) {
        } else if (authUrl.contains("open.api.clife.cn")) {
            authUrl = GlobalAddr.PREVIEW_MODIFY_PWD_HOST + "/v1/app/open";
        } else if (authUrl.contains("test.open.api.clife.cn")) {
            authUrl = GlobalAddr.RELEASW_MODIFY_PWD_HOST + "/v1/app/open";
        }

        String url = authUrl + "/wifiSetting/clife-open-app/page/alterPassword.html";
        BaseWebParams params = new BaseWebParams.Builder(mContext)
                .setAuthListener(new ModifyPasswordLister(mContext, authCallback))
                .setSpecifyTitle(specifyTitle)
                .setUrl(url)
                .setPhone(phone)
                .create();
        H5CommonActivity.startH5CommonActivity(mContext, params.createRequestParamBundle());
    }
}
