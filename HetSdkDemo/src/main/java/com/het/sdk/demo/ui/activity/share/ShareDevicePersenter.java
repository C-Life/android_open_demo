package com.het.sdk.demo.ui.activity.share;

import android.text.TextUtils;

import com.het.open.lib.api.HetDeviceShareApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.base.BaseHetPresenter;

/**
 * Created  on 2017-10-10.
 * 设备分享控制类
 */

public class ShareDevicePersenter extends BaseHetPresenter<DeviceShareView> {

    //新版设备分享页面参数
    private static final String SHARE_CODE_NEWURL = "1";

    public void getUserShareList(String deviceId) {
        HetDeviceShareApi.getInstance().getDeviceAuthUser(new IHetCallback() {
            @Override
            public void onSuccess(int code, String s) {
                if (code == 0) {
                    if (!TextUtils.isEmpty(s)) {
                        mHetView.getUserShareList(s);
                    } else {
                        mHetView.showTipText("您还未分享设备给好友!");
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ((BaseHetActivity) activity).showToast(msg);
                mHetView.showTipText("服务器错误 请稍后再试!");
            }
        }, deviceId);
    }

    public void delshareDevice(String deviceId, String userId) {
        HetDeviceShareApi.getInstance().deviceDel(new IHetCallback() {
            @Override
            public void onSuccess(int code, String s) {
                if (code == 0) {
                    ((BaseHetActivity) activity).showToast("解除分享成功");
                    getUserShareList(deviceId);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ((BaseHetActivity) activity).showToast(msg);
            }
        }, deviceId, userId);
    }

    /**
     * shareType 5是面对面分享
     * shareType 6是远程分享  （微信 QQ的分享）
     *
     * @param deviceId 设备ID
     * @param pos      分享类型标识
     */
    public void getShareCode(String deviceId, final int pos) {
        String shareType = "";
        if (pos == 0) {
            shareType = "5";
        } else if (pos == 1) {
            shareType = "6";
        }
        HetDeviceShareApi.getInstance().getShareCode(new IHetCallback() {
            @Override
            public void onSuccess(int code, String s) {
                if (code == 0) {
                    mHetView.getShareCode(s, pos);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ((BaseHetActivity) activity).showToast(msg);
            }
        }, deviceId, shareType, SHARE_CODE_NEWURL);
    }

}
