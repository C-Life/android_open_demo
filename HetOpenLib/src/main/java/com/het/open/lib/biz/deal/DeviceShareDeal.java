package com.het.open.lib.biz.deal;

import com.het.basic.utils.GsonUtil;
import com.het.open.lib.biz.api.DeviceShareApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.share.AuthDeviceModel;
import com.het.open.lib.model.share.DeviceAuthUserAll;
import com.het.open.lib.model.share.DeviceAuthUserModel;
import com.het.open.lib.model.share.ShareCodeModel;
import com.het.open.lib.model.share.ShareDeviceUrlModel;

import java.util.List;


/**
 * @author martin
 */
public class DeviceShareDeal {


    public static void getAllAuthDevice(IHetCallback iHetCallback, String phone, String authOpenId, String openId) {
        DeviceShareApi.getInstance().getAllAuthDevice(phone, authOpenId, openId).subscribe(s -> {
            if (s.getCode() != 0) {
                iHetCallback.onFailed(s.getCode(), s.getMsg());
                return;
            }
            iHetCallback.onSuccess(s.getCode(), s.getData());
        }, throwable -> {
            iHetCallback.onFailed(-1, throwable.getMessage());
        });
    }


    public static void deviceInvite(IHetCallback iHetCallback, String deviceId, String account) {
        DeviceShareApi.getInstance().deviceInvite(deviceId, account).subscribe(s -> {
            if (s.getCode() != 0) {
                iHetCallback.onFailed(s.getCode(), s.getMsg());
                return;
            }
            iHetCallback.onSuccess(s.getCode(), s.getData());
        }, throwable -> {
            iHetCallback.onFailed(-1, throwable.getMessage());
        });
    }

    public static void userAuth(IHetCallback iHetCallback, String deviceId, String friendIds) {
        DeviceShareApi.getInstance().userAuth(deviceId, friendIds).subscribe(s -> {
            if (s.getCode() != 0) {
                iHetCallback.onFailed(s.getCode(), s.getMsg());
                return;
            }
            iHetCallback.onSuccess(s.getCode(), s.getData());
        }, throwable -> {
            iHetCallback.onFailed(-1, throwable.getMessage());
        });
    }

    public static void deviceDel(IHetCallback iHetCallback, String deviceId, String userId) {
        DeviceShareApi.getInstance().deviceDel(deviceId, userId).subscribe(s -> {
            if (s.getCode() != 0) {
                iHetCallback.onFailed(s.getCode(), s.getMsg());
                return;
            }
            iHetCallback.onSuccess(s.getCode(), s.getData());
        }, throwable -> {
            iHetCallback.onFailed(-1, throwable.getMessage());
        });
    }

    public static void deviceAgree(IHetCallback iHetCallback, String deviceId) {
        DeviceShareApi.getInstance().deviceAgree(deviceId).subscribe(s -> {
            if (s.getCode() != 0) {
                iHetCallback.onFailed(s.getCode(), s.getMsg());
                return;
            }
            iHetCallback.onSuccess(s.getCode(), s.getData());
        }, throwable -> {
            iHetCallback.onFailed(-1, throwable.getMessage());
        });
    }

    public static void getDeviceAuthUser(IHetCallback iHetCallback, String deviceId) {
        DeviceShareApi.getInstance().getDeviceAuthUser(deviceId).subscribe(s -> {
            if (s.getCode() != 0) {
                iHetCallback.onFailed(s.getCode(), s.getMsg());
                return;
            }
            List<DeviceAuthUserModel> deviceAuthUserModel = s.getData();
            String json = GsonUtil.getInstance().getGson().toJsonTree(deviceAuthUserModel).toString();
            iHetCallback.onSuccess(s.getCode(), json);
        }, throwable -> {
            iHetCallback.onFailed(-1, throwable.getMessage());
        });
    }

    public static void multiInvite(IHetCallback iHetCallback, String deviceIds, String account) {
        DeviceShareApi.getInstance().multiInvite(deviceIds, account).subscribe(s -> {
            if (s.getCode() != 0) {
                iHetCallback.onFailed(s.getCode(), s.getMsg());
                return;
            }
            iHetCallback.onSuccess(s.getCode(), s.getData());
        }, throwable -> {
            iHetCallback.onFailed(-1, throwable.getMessage());
        });
    }

    public static void getAuthDevice(IHetCallback iHetCallback) {
        DeviceShareApi.getInstance().getAuthDevice().subscribe(s -> {
            if (s.getCode() != 0) {
                iHetCallback.onFailed(s.getCode(), s.getMsg());
                return;
            }
            AuthDeviceModel authDeviceModel = s.getData();
            String json = GsonUtil.getInstance().getGson().toJsonTree(authDeviceModel).toString();
            iHetCallback.onSuccess(s.getCode(), json);
        }, throwable -> {
            iHetCallback.onFailed(-1, throwable.getMessage());
        });
    }

    public static void getAppDeviceAuthUser(IHetCallback iHetCallback) {
        DeviceShareApi.getInstance().getAppDeviceAuthUser().subscribe(s -> {
            if (s.getCode() != 0) {
                iHetCallback.onFailed(s.getCode(), s.getMsg());
                return;
            }
            DeviceAuthUserAll deviceAuthUserAll = s.getData();
            String json = GsonUtil.getInstance().getGson().toJsonTree(deviceAuthUserAll).toString();
            iHetCallback.onSuccess(s.getCode(), json);
        }, throwable -> {
            iHetCallback.onFailed(-1, throwable.getMessage());
        });
    }

    public static void getShareCode(IHetCallback iHetCallback, String deviceId, String shareType) {
        DeviceShareApi.getInstance().getShareCode(deviceId, shareType).subscribe(s -> {
            if (s.getCode() != 0) {
                iHetCallback.onFailed(s.getCode(), s.getMsg());
                return;
            }
            ShareCodeModel shareCodeModel = s.getData();
            String json = GsonUtil.getInstance().getGson().toJsonTree(shareCodeModel).toString();
            iHetCallback.onSuccess(s.getCode(), json);
        }, throwable -> {
            iHetCallback.onFailed(-1, throwable.getMessage());
        });
    }

    public static void getShareDevice(IHetCallback iHetCallback, String shareCode) {
        DeviceShareApi.getInstance().getShareDevice(shareCode).subscribe(s -> {
            if (s.getCode() != 0) {
                iHetCallback.onFailed(s.getCode(), s.getMsg());
                return;
            }
            ShareDeviceUrlModel shareDeviceUrlModel = s.getData();
            String json = GsonUtil.getInstance().getGson().toJsonTree(shareDeviceUrlModel).toString();
            iHetCallback.onSuccess(s.getCode(), json);
        }, throwable -> {
            iHetCallback.onFailed(-1, throwable.getMessage());
        });
    }

    public static void authShareDevice(IHetCallback iHetCallback, String shareCode, String shareType) {
        DeviceShareApi.getInstance().authShareDevice(shareCode, shareType).subscribe(s -> {
            if (s.getCode() != 0) {
                iHetCallback.onFailed(s.getCode(), s.getMsg());
                return;
            }
            iHetCallback.onSuccess(s.getCode(), s.getData());
        }, throwable -> {
            iHetCallback.onFailed(-1, throwable.getMessage());
        });
    }
}
