package com.het.open.lib.auth;

import android.text.TextUtils;

import com.het.basic.AppDelegate;
import com.het.basic.utils.Base64;
import com.het.basic.utils.SharePreferencesUtil;
import com.het.open.lib.model.AuthLoginModel;

/**
 * Created by Administrator on 2017-09-18.
 */

public class LogUserInfoManager {
    private static LogUserInfoManager instance = null;
    private AuthLoginModel authLoginModel = new AuthLoginModel();
    private static final String  LoginUser= "login_model_new";


    public LogUserInfoManager() {
        this.authLoginModel.setAccessToken("");
    }

    public static LogUserInfoManager getInstance() {
        if(instance == null) {

            synchronized(LogUserInfoManager.class) {
                instance = new LogUserInfoManager();
            }
        }

        return instance;
    }


    public AuthLoginModel getAuthLoginModel() {
        if(this.authLoginModel != null && !TextUtils.isEmpty(this.authLoginModel.getAccessToken())) {
            return this.authLoginModel;
        } else {
            AuthLoginModel authLogin = (AuthLoginModel) Base64.strBase64Obj(SharePreferencesUtil.getString(AppDelegate.getAppContext(), LoginUser));
            if(authLogin != null) {
                this.authLoginModel = authLogin;
            }

            return this.authLoginModel;
        }
    }

    public void setAuthLoginModel(AuthLoginModel model) {
        if(model != null) {
            this.authLoginModel = model;
        }

        SharePreferencesUtil.putString(AppDelegate.getAppContext(), LoginUser, Base64.objBase64Str(model));
    }

    public void clearLoginUser() {
        if(this.authLoginModel != null) {
            this.authLoginModel.setAccessToken("");
        }

        SharePreferencesUtil.removeKey(AppDelegate.getAppContext(), LoginUser);
    }
}
