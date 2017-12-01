package com.het.sdk.demo.manager;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.reflect.TypeToken;
import com.het.basic.base.RxManage;
import com.het.basic.utils.ACache;
import com.het.basic.utils.GsonUtil;
import com.het.basic.utils.SharePreferencesUtil;
import com.het.basic.utils.StringUtils;
import com.het.bind.logic.bean.UserInfoBean;
import com.het.log.Logc;
import com.het.open.lib.api.HetSdk;


/**
 * Created by liuzh on 2017-10-23.
 * 用户模块管理
 */

public class HetUserManager {
    private UserInfoBean mUserModel;
    private static HetUserManager mUserManger;
    private Context mContext = HetSdk.getInstance().getApplication();
    private static final String USER_MODEL = "userModel";
    private ACache mACache;

    private HetUserManager() {
        if (this.mContext == null) {
            throw new NullPointerException("make sure you have invoked the HetSdk.getInstance().init() in your application!");
        } else {
            this.mACache = ACache.get(this.mContext);
        }
    }

    public static HetUserManager getInstance() {
        if (mUserManger == null) {
            Class var0 = HetUserManager.class;
            synchronized (HetUserManager.class) {
                if (mUserManger == null) {
                    mUserManger = new HetUserManager();
                }
            }
        }

        return mUserManger;
    }

    @Nullable
    public UserInfoBean getUserModel() {
        if (HetSdk.getInstance().isAuthLogin()) {
            this.mUserModel = (UserInfoBean) this.mACache.getAsObject("userModel");
            if (this.mUserModel == null) {
                String userString = SharePreferencesUtil.getString(mContext, "userModel");
                if (!StringUtils.isNull(userString)) {
                    this.mUserModel = GsonUtil.getInstance().toObject(userString, new TypeToken<UserInfoBean>() {
                    }.getType());
                }
            }
            return this.mUserModel;
        } else {
            return null;
        }
    }

    public void setUserModel(UserInfoBean userModel) {
        if (userModel != null) {
            this.mUserModel = userModel;
            this.mACache.put("userModel", this.mUserModel);
            SharePreferencesUtil.putString(mContext, "userModel", GsonUtil.getInstance().toJson(userModel));
            this.notifyUserInfoChange();
        }
    }

    public void clearUserModel() {
        try {
            this.mACache.remove("userModel");
            SharePreferencesUtil.removeKey(mContext, "userModel");
        } catch (Exception var2) {
            Logc.e(var2.getMessage(), false);
        }
    }

    private void notifyUserInfoChange() {
        RxManage.getInstance().post("updateSuccess", this.getUserModel());
    }
}
