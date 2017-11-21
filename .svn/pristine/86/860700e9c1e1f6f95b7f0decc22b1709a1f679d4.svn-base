package com.het.open.lib.biz.thirdlogin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.het.share.model.ThirdPlatformKey;
import com.het.thirdlogin.SinaWeiboLogin;
import com.het.thirdlogin.callback.IHetThirdLogin;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

/**
 * 微博登录 实现
 */

public class ThirdWeiboLogin implements IthirdLogin {
    private Context context;
    private IHetThirdLogin iHetThirdLogin;
    private ThirdPlatformKey mThirdKeyConstant;
    private SinaWeiboLogin mSinaLogin;
    private AuthInfo mSinaAuthInfo;
    private SsoHandler mSinaSsoHandler;

    public ThirdWeiboLogin(Context context, IHetThirdLogin listener) {
        this.context = context;
        this.iHetThirdLogin = listener;
        this.mThirdKeyConstant = ThirdPlatformKey.getInstance();
        this.mSinaAuthInfo = new AuthInfo(context, this.mThirdKeyConstant.getSinaAppKey(), this.mThirdKeyConstant.getSinaDirectUrl(), "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write");
        this.mSinaSsoHandler = new SsoHandler((Activity) context, this.mSinaAuthInfo);
        this.mSinaLogin = new SinaWeiboLogin((Activity) context, "");
        this.mSinaLogin.setSinaSsoHandler(this.mSinaSsoHandler);
    }

    @Override
    public void startLogin() {
        mSinaLogin.startSinaWeiboLogin(iHetThirdLogin);
    }

    public void authorizeCallBack(int requestCode, int resultCode, Intent data) {
        if (this.mSinaSsoHandler != null) {
            this.mSinaSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

}
