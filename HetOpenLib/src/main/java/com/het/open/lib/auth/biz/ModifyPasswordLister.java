package com.het.open.lib.auth.biz;

import android.content.Context;

import com.het.basic.data.api.token.TokenManager;
import com.het.open.lib.auth.callback.HetAuthListener;
import com.het.open.lib.callback.AuthCallback;
import com.het.open.lib.api.HetCodeConstants;
import com.het.open.lib.utils.HetException;

/**
 * Created by Administrator on 2017-09-18.
 */

public class ModifyPasswordLister implements HetAuthListener {
    private Context mContext;
    private String TAG = "修改密码处理";
    private AuthCallback authCallback;

    public ModifyPasswordLister(Context mContext, AuthCallback authCallback) {
        this.mContext = mContext;
        this.authCallback = authCallback;
    }

    @Override
    public void onComplete(String value) {
        if (authCallback != null) {
            //修改密码成功
            int code = HetCodeConstants.HTTP_RET_SUCCESS;
            authCallback.onSuccess(code, value);
        }


    }

    @Override
    public void onCancel() {

        if (authCallback != null) {
            int code = HetCodeConstants.PWD_ERROR_NO_INPUT;
            if (!TokenManager.getInstance().isLogin()) {
                String msg = "修改密码返回";
                authCallback.onFailed(code, msg);
            }

        }
    }

    @Override
    public void onHetException(HetException e) {
        // TODO Auto-generated method stub
        if (authCallback != null) {
            int code = HetCodeConstants.PWD_ERROR;
            String msg = "修改密码失败";
            authCallback.onFailed(code, msg);
        }
    }
}
