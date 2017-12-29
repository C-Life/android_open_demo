package com.het.sdk.demo.ui.activity.message;

import com.het.log.Logc;
import com.het.open.lib.api.HetDeviceShareApi;
import com.het.open.lib.api.HetMessageApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.sdk.demo.base.BaseHetPresenter;

/**
 * Created by Administrator on 2017-10-11.
 */

public class MsgDevicePresenter extends BaseHetPresenter<MsgInviteView> {

    public final static int MSG_TYPE_LIST_UPDATE_SUCCESS = 1009;
    public final static int MSG_TYPE_LIST_UPDATE_FAIL = 1010;
    public final static int MSG_DEVICE_ACCEPT_SUCCESS = 1011;
    public final static int MSG_DEVICE_ACCEPT_FAIL = 1012;

    /**
     * 同意设备授权
     *
     * @param deviceId 设备标识
     */
    void deviceAgree(String deviceId) {
        HetDeviceShareApi.getInstance().deviceAgree(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (code != 0) {
                    mHetView.Failed(MSG_DEVICE_ACCEPT_FAIL);
                    return;
                }
                mHetView.success(MSG_DEVICE_ACCEPT_SUCCESS);
            }

            @Override
            public void onFailed(int code, String msg) {
                mHetView.Failed(MSG_DEVICE_ACCEPT_FAIL);
            }
        }, deviceId);


    }

    /**
     * 更新消息列表
     *
     * @param messageId 消息ID
     */
    public void updateMsg(String messageId) {
        HetMessageApi.getInstance().updateMsg(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (code != 0) {
                    mHetView.Failed(MSG_TYPE_LIST_UPDATE_FAIL);
                    return;
                }
                mHetView.success(MSG_TYPE_LIST_UPDATE_SUCCESS);
            }

            @Override
            public void onFailed(int code, String msg) {
                Logc.e("updateMsg e =" + msg);
                mHetView.Failed(MSG_TYPE_LIST_UPDATE_FAIL);
            }
        }, messageId);
    }

}
