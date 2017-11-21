package com.het.open.lib.thirdlogin;

import android.content.Context;
import android.content.Intent;

import com.het.thirdlogin.callback.IHetThirdLogin;

/**
 * 第三方登录 工厂接口具体实现
 */

public class IthirdFactoryImp implements IthirdFactory {
    private ThirdQQLogin thirdQQLogin;
    private ThirdWeixinLogin thirdWeixinLogin;
    private ThirdWeiboLogin thirdWeiboLogin;

    @Override
    public ThirdQQLogin initQQLogin(Context context, IHetThirdLogin listener) {
        if (thirdQQLogin == null)
            thirdQQLogin = new ThirdQQLogin(context, listener);
        return thirdQQLogin;
    }

    @Override
    public IthirdLogin initWeiXinLogin(Context context, IHetThirdLogin listener) {
        if (thirdWeixinLogin == null)
            thirdWeixinLogin = new ThirdWeixinLogin(context, listener);
        return thirdWeixinLogin;
    }

    @Override
    public IthirdLogin initWeiboLogin(Context context, IHetThirdLogin listener) {
        if (thirdWeiboLogin == null)
            thirdWeiboLogin = new ThirdWeiboLogin(context, listener);
        return thirdWeiboLogin;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (thirdQQLogin != null) {
            this.thirdQQLogin.onActivityResult(requestCode, resultCode, data);
        }
        if (thirdWeiboLogin != null) {
            thirdWeiboLogin.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
