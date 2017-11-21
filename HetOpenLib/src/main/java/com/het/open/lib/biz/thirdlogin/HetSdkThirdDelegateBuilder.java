package com.het.open.lib.biz.thirdlogin;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

/**
 * Created by Administrator on 2017-09-01.
 *
 */

public class HetSdkThirdDelegateBuilder {
    private HetSdkThirdDelegate mLoginManger = HetSdkThirdDelegate.getInstance();
    private Context context;
    private String mWXAppId;
    private String mWXAppSecret;
    private String mSinaAppKey;
    private String mSinaAppSecret;
    private String mSinaRedirectUrl;
    private String mQQAppId;
    private IWXAPI mIWeixinApi;
    private Tencent mTencent;
    private IWeiboShareAPI mWeibo;

    public HetSdkThirdDelegateBuilder(Context cxt) {
        this.context = cxt;
        this.mLoginManger.setContext(cxt);
    }

    public HetSdkThirdDelegateBuilder registerWeixin(@NonNull String weixinAppId, @NonNull String wxSeceret) {
        this.mWXAppId = weixinAppId;
        this.mWXAppSecret = wxSeceret;
        this.mIWeixinApi = WXAPIFactory.createWXAPI(this.context, weixinAppId, false);
        this.mIWeixinApi.registerApp(weixinAppId);
        this.mLoginManger.setIWeixinApi(this.mIWeixinApi);
        return this;
    }

    public HetSdkThirdDelegateBuilder registerQQ(@NonNull String tencentAppId) {
        this.mQQAppId = tencentAppId;
        if (this.mTencent == null) {
            this.mTencent = Tencent.createInstance(tencentAppId, this.context);
        }

        this.mLoginManger.setTencent(this.mTencent);
        return this;
    }

    public HetSdkThirdDelegateBuilder registerSinaWeibo(@NonNull String sinaAppKey, @NonNull String sinaAppSecret, @NonNull String redicrectUrl) {
        this.mSinaAppKey = sinaAppKey;
        this.mSinaAppSecret = sinaAppSecret;
        this.mSinaRedirectUrl = redicrectUrl;
        if (this.mWeibo == null) {
            this.mWeibo = WeiboShareSDK.createWeiboAPI(this.context, sinaAppKey);
        }

        this.mWeibo.registerApp();
        this.mLoginManger.setWeibo(this.mWeibo);
        this.mLoginManger.setSinaAppKey(sinaAppKey);
        this.mLoginManger.setSinaRedirectUrl(redicrectUrl);
        return this;
    }

    public HetSdkThirdDelegate create() {
        this.mLoginManger.setQQAppId(this.mQQAppId);
        this.mLoginManger.setWXAppId(this.mWXAppId);
        this.mLoginManger.setWXAppSecret(this.mWXAppSecret);
        this.mLoginManger.setSinaAppKey(this.mSinaAppKey);
        this.mLoginManger.setSinaAppSecret(this.mSinaAppSecret);
        this.mLoginManger.setSinaRedirectUrl(this.mSinaRedirectUrl);
        this.mLoginManger.initThirdPlatformKey();
        return this.mLoginManger;
    }
}
