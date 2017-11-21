package com.het.open.lib.biz.thirdlogin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.het.thirdlogin.QQLogin;
import com.het.thirdlogin.callback.IHetThirdLogin;

/**
 * QQ登录 实现
 */

public class ThirdQQLogin implements IthirdLogin {
    private Context context;
    private IHetThirdLogin iHetThirdLogin;
    private QQLogin qqLogin;

    public ThirdQQLogin(Context context, IHetThirdLogin listener) {
        this.context = context;
        this.iHetThirdLogin = listener;
        if (qqLogin == null) {
            qqLogin = new QQLogin((Activity) context);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (qqLogin != null)
            this.qqLogin.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void startLogin() {
        qqLogin.startQQLogin(iHetThirdLogin, false);
    }
}
