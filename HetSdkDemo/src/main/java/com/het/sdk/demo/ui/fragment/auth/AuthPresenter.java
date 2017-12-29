package com.het.sdk.demo.ui.fragment.auth;

import com.het.open.lib.api.HetNewAuthApi;
import com.het.open.lib.api.HetSdk;
import com.het.open.lib.api.HetUserApi;
import com.het.open.lib.callback.AuthCallback;
import com.het.open.lib.callback.IHetCallback;
import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.base.BaseHetPresenter;
import com.het.sdk.demo.base.BaseHetView;
import com.het.sdk.demo.utils.UIJsonConfig;

/**
 * Created by Administrator on 2017-08-31.
 */

public class AuthPresenter extends BaseHetPresenter<BaseHetView> {

    protected void auth() {

        HetNewAuthApi.getInstance().authorize(activity, new AuthCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                ((BaseHetActivity) activity).showToast(msg);
            }

            @Override
            public void onFailed(int code, String msg) {
                ((BaseHetActivity) activity).showToast(msg);
            }
        }, activity.getString(R.string.login_security), UIJsonConfig.getInstance(activity).setNavigationBarTextColor(), UIJsonConfig.getInstance(activity).setNavBackground_color_string(), UIJsonConfig.getInstance(activity).getLoginType());

    }


    /**
     * 获取用户信息
     */
    protected void getUserMess() {
        if (HetSdk.getInstance().isAuthLogin()) {
            HetUserApi.getInstance().getUserMess(new IHetCallback() {
                @Override
                public void onSuccess(int code, String msg) {
                    if (code == 0) {
                        ((BaseHetActivity) activity).showToast("用户信息" +
                                msg);
                    }
                }

                @Override
                public void onFailed(int code, String msg) {
                    ((BaseHetActivity) activity).showToast(code + msg);

                }
            });
        } else {
            ((BaseHetActivity) activity).showToast("登录后获取用户信息");
        }
    }
}
