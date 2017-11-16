package com.het.open.lib.thirdlogin;

import android.app.Activity;
import android.content.Context;

import com.het.thirdlogin.WeiXinLogin;
import com.het.thirdlogin.callback.IHetThirdLogin;

/**
 * 微信登录 实现
 */

public class ThirdWeixinLogin implements IthirdLogin {
    private Context context;
    private IHetThirdLogin iHetThirdLogin;

    public ThirdWeixinLogin(Context context, IHetThirdLogin listener) {
        this.context = context;
        this.iHetThirdLogin = listener;
    }

    @Override
    public void startLogin() {
        WeiXinLogin.getInstance().startWeixinLogin((Activity) context, iHetThirdLogin);
    }
}
