package com.het.sdk.demo.ui.fragment.device;

import android.app.AlertDialog;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.het.basic.utils.GsonUtil;
import com.het.basic.utils.StringUtils;
import com.het.bind.logic.bean.UserInfoBean;
import com.het.open.lib.api.HetDeviceListApi;
import com.het.open.lib.api.HetDeviceManagerApi;
import com.het.open.lib.api.HetDeviceShareApi;
import com.het.open.lib.api.HetNewAuthApi;
import com.het.open.lib.api.HetSdk;
import com.het.open.lib.api.HetUserApi;
import com.het.open.lib.callback.AuthCallback;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.DeviceModel;
import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.base.BaseHetPresenter;
import com.het.sdk.demo.manager.HetUserManager;
import com.het.sdk.demo.push.HetPushManager;
import com.het.sdk.demo.ui.fragment.UserHetView;
import com.het.sdk.demo.utils.UIJsonConfig;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017-09-06.
 */

public class LoginPresenter extends BaseHetPresenter<DeviceListHetView> {

    public void startLogin() {

        HetNewAuthApi.getInstance().authorize(activity, new AuthCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                //登录成功
            }

            @Override
            public void onFailed(int code, String msg) {
                //登录失败
                ((BaseHetActivity) activity).showToast(msg);
            }
        }, activity.getString(R.string.login_security), UIJsonConfig.getInstance(activity).setNavigationBarTextColor(), UIJsonConfig.getInstance(activity).setNavBackground_color_string());

    }

    public void getDevicelist() {
        if (!HetSdk.getInstance().isAuthLogin()) {
            mHetView.showTipText("授权登录后查看绑定设备信息");
            return;
        }
        HetDeviceListApi.getInstance().getBindList(new IHetCallback() {

            @Override
            public void onSuccess(int code, String s) {
                if (code == 0) {
                    if (!TextUtils.isEmpty(s)) {
                        mHetView.getDeviceList(s);
                    } else {
                        mHetView.showTipText("未绑定任何设备");
                    }
                }

            }

            @Override
            public void onFailed(int code, String msg) {
                ((BaseHetActivity) activity).showToast(msg);
                mHetView.showTipText("服务器错误，请稍后再试！");
            }
        });
    }


    public void delDevice(DeviceModel deviceModel, IHetCallback iHetCallback) {
        if (!HetSdk.getInstance().isAuthLogin()) {
            ((BaseHetActivity) activity).showToast("亲，还没有授权登录呢");
            return;
        }
        new AlertDialog.Builder(activity).setMessage("是否确认删除该设备")
                .setPositiveButton("删除", (dialog, which) -> {
                    if (deviceModel.getShare() == 1) {
                        HetDeviceShareApi.getInstance().deviceDel(new IHetCallback() {
                            @Override
                            public void onSuccess(int code, String msg) {
                                iHetCallback.onSuccess(code, msg);
                            }

                            @Override
                            public void onFailed(int code, String msg) {
                                iHetCallback.onFailed(code, msg);
                            }
                        }, deviceModel.getDeviceId(), null);
                    } else if (deviceModel.getShare() == 2) {
                        HetDeviceManagerApi.getInstance().unBind(deviceModel, new IHetCallback() {
                            @Override
                            public void onSuccess(int code, String msg) {
                                iHetCallback.onSuccess(code, msg);
                            }

                            @Override
                            public void onFailed(int code, String msg) {
                                iHetCallback.onFailed(code, msg);
                            }
                        });
                    }
                })
                .setNegativeButton(activity.getString(R.string.btn_cancel), null)
                .show();
    }

    public void logout() {
        new AlertDialog.Builder(activity)
                .setMessage(activity.getString(R.string.confirm_logout))
                .setPositiveButton(activity.getString(R.string.logout_sure), (dialog, which) -> {
                    dialog.dismiss();
                    HetPushManager.getInstance().unbindPushService();
                    HetSdk.getInstance().logout();
                })
                .setNegativeButton(activity.getString(R.string.logout_cancel), null)
                .show();
    }

    public void getUserInfo() {
        if (!HetSdk.getInstance().isAuthLogin()) return;
        UserInfoBean userInfoBean = HetUserManager.getInstance().getUserModel();
        if (userInfoBean != null) {
            ((UserHetView) mBaseHetView).showUser(userInfoBean);
            return;
        }
        HetUserApi.getInstance().getUserMess(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                Type type = new TypeToken<UserInfoBean>() {
                }.getType();
                UserInfoBean users = GsonUtil.getInstance().toObject(msg, type);
                HetUserManager.getInstance().setUserModel(users);
                ((UserHetView) mBaseHetView).showUser(users);
            }

            @Override
            public void onFailed(int code, String msg) {
                ((BaseHetActivity) activity).showToast(msg);
            }
        });
    }

    /**
     * 获取手机号之后 去修改密码
     * 也可以登录成功之后就去获取手机号  在修改密码的时候就不需要去获取了
     */
    public void editPwd() {
        if (!HetSdk.getInstance().isAuthLogin()) return;
        UserInfoBean userInfoBean = HetUserManager.getInstance().getUserModel();
        if (userInfoBean != null) {
            startEdit(userInfoBean);
        } else {
            HetUserApi.getInstance().getUserMess(new IHetCallback() {
                @Override
                public void onSuccess(int code, String msg) {
                    Type type = new TypeToken<UserInfoBean>() {
                    }.getType();
                    UserInfoBean users = GsonUtil.getInstance().toObject(msg, type);
                    HetUserManager.getInstance().setUserModel(users);
                    if (users != null) {
                        startEdit(users);
                    }
                }

                @Override
                public void onFailed(int code, String msg) {
                    ((BaseHetActivity) activity).showToast(msg);
                }
            });
        }
    }

    public void startEdit(UserInfoBean userInfoBean) {

        String account = null;
        if (userInfoBean != null) {
            if (!StringUtils.isNull(userInfoBean.getPhone())) {
                account = userInfoBean.getPhone();
            } else if (!StringUtils.isNull((String) userInfoBean.getEmail())) {
                account = (String) userInfoBean.getEmail();
            }
        }

        if (account != null) {
            HetNewAuthApi.getInstance().alterPassword(activity, new AuthCallback() {
                @Override
                public void onSuccess(int code, String msg) {
                    ((BaseHetActivity) activity).showToast(msg);
                }

                @Override
                public void onFailed(int code, String msg) {
                    ((BaseHetActivity) activity).showToast(msg);
                }
            }, account, activity.getString(R.string.update_password), UIJsonConfig.getInstance(activity).setNavigationBarTextColor(), UIJsonConfig.getInstance(activity).setNavBackground_color_string());
        }
    }
}
