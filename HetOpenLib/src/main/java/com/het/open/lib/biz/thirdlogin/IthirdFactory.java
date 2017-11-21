package com.het.open.lib.biz.thirdlogin;

import android.content.Context;

import com.het.thirdlogin.callback.IHetThirdLogin;

/**
 * 第三方登录 抽象工厂接口
 */

public interface IthirdFactory {
    IthirdLogin initQQLogin(Context context, IHetThirdLogin listener);

    IthirdLogin initWeiXinLogin(Context context, IHetThirdLogin listener);

    IthirdLogin initWeiboLogin(Context context, IHetThirdLogin listener);
}
