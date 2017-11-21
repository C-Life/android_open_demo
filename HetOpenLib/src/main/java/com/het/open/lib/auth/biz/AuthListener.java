package com.het.open.lib.auth.biz;

import android.content.Context;

import com.het.basic.base.RxManage;
import com.het.basic.data.api.token.TokenManager;
import com.het.open.lib.api.HetCodeConstants;
import com.het.open.lib.auth.callback.HetAuthListener;
import com.het.open.lib.callback.AuthCallback;
import com.het.open.lib.utils.HetException;


/**
 * 授权登录处理
 */
public class AuthListener implements HetAuthListener {
    private Context mContext;
    private String TAG = "授权登录处理";
    private AuthCallback authCallback;

    public AuthListener(Context mContext, AuthCallback authCallback) {
        this.mContext = mContext;
        this.authCallback = authCallback;
    }

    @Override
    public void onComplete(String value) {
        if (authCallback != null) {
            //授权登录成功
            int code = HetCodeConstants.HTTP_RET_SUCCESS;
            authCallback.onSuccess(code, value);
            RxManage.getInstance().post(HetCodeConstants.Login.LOGIN_SUCCESS, value);
        }


    }

    @Override
    public void onCancel() {

        if (authCallback != null) {
            int code = HetCodeConstants.AUTH_ERROR_NO_INPUT;
            if (!TokenManager.getInstance().isLogin()) {
                String msg = "授权未登录返回";
                authCallback.onFailed(code, msg);
            }

        }
    }

    @Override
    public void onHetException(HetException e) {
        // TODO Auto-generated method stub
        if (authCallback != null) {
            int code = HetCodeConstants.AUTH_ERROR;
            String msg = "授权异常";
            authCallback.onFailed(code, msg);
        }
    }
}
