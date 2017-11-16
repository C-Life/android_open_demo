package com.het.open.lib.biz.api;

import com.het.basic.base.helper.RxSchedulers;
import com.het.basic.data.api.token.HetParamsMerge;
import com.het.basic.data.http.okhttp.util.OkHttpTag;
import com.het.basic.data.http.retrofit2.RetrofitManager;
import com.het.basic.model.ApiResult;
import com.het.basic.utils.StringUtils;
import com.het.open.lib.biz.constans.GlobalAddr;
import com.het.open.lib.biz.services.DeviceShareService;
import com.het.open.lib.biz.constans.DeviceParamContant;
import com.het.open.lib.model.share.AuthDeviceModel;
import com.het.open.lib.model.share.DeviceAuthUserAll;
import com.het.open.lib.model.share.DeviceAuthUserModel;
import com.het.open.lib.model.share.ShareCodeModel;
import com.het.open.lib.model.share.ShareDeviceUrlModel;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2017-10-10.
 * 设备分享接口 2.0
 */

public class DeviceShareApi {

    private static DeviceShareApi instance = null;
    private DeviceShareService api;

    private void createapi() {
        api = RetrofitManager.getRetrofit(new OkHttpTag(this.getClass().getName())).create(DeviceShareService.class);
    }

    public static DeviceShareApi getInstance() {
        if (instance == null) {
            synchronized (DeviceShareApi.class) {
                if (instance == null) {
                    instance = new DeviceShareApi();
                }
            }
        }
        instance.createapi();
        return instance;
    }

    /**
     * 1.请求手机账号所有设备的授权
     *
     * @param phone      待授权的手机号([手机号+密钥]，再MD5加密)
     * @param authOpenId 授权的openId
     * @param openId     待授权的openId
     * @return code 0
     */

    public Observable<ApiResult<String>> getAllAuthDevice(String phone, String authOpenId, String openId) {
        HetParamsMerge params = new HetParamsMerge();
        params.add("phone", phone);
        params.add("authOpenId", authOpenId);
        params.add("openId", openId);
        String path = "/v1/auth/getAllAuthDevice";
        String url = GlobalAddr.HOST;
        if (url.contains("dp") || url.contains("50")) {
            path = "/v1/app/open/auth/getAllAuthDevice";
        }
        return api.getAllAuthDevice(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }

    /**
     * 2、设备授权邀请
     *
     * @param deviceId 设备标识
     * @param account  帐号（手机、邮箱）
     * @return 0
     */
    public Observable<ApiResult<String>> deviceInvite(String deviceId, String account) {
        HetParamsMerge params = new HetParamsMerge();
        params.add("deviceId", deviceId);
        params.add("account", account);
        String path = "/v1/auth/invite";
        String url = GlobalAddr.HOST;
        if (url.contains("dp") || url.contains("50")) {
            path = "/v1/app/open/auth/invite";
        }
        return api.deviceInvite(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }

    /**
     * 3、多用户设备授权
     *
     * @param deviceId  设备标识
     * @param friendIds 好友标识（多个标识用','（逗号）隔开）
     * @return 0
     */
    public Observable<ApiResult<String>> userAuth(String deviceId, String friendIds) {
        HetParamsMerge params = new HetParamsMerge();
        params.add("deviceId", deviceId);
        params.add("friendIds", friendIds);
        String path = "/v1/auth";
        String url = GlobalAddr.HOST;
        if (url.contains("dp") || url.contains("50")) {
            path = "/v1/app/open/auth";
        }
        return api.userAuth(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }

    /**
     * 4、设备授权删除
     *
     * @param deviceId 设备标识
     * @param userId   用户标识，为空时表示删除所有授权信息或被授权人的授权信息 【2015-11-24修改为非必填参数】
     * @return 0
     */
    public Observable<ApiResult<String>> deviceDel(String deviceId, String userId) {
        HetParamsMerge params = new HetParamsMerge();
        params.add("deviceId", deviceId);
        if (!StringUtils.isNull(userId))
            params.add("userId", userId);
        String path = "/v1/auth/del";
        String url = GlobalAddr.HOST;
        if (url.contains("dp") || url.contains("50")) {
            path = "/v1/app/open/auth/del";
        }
        return api.deviceDel(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }

    /**
     * 5、设备授权同意
     *
     * @param deviceId 设备标识
     * @return 0
     */
    public Observable<ApiResult<String>> deviceAgree(String deviceId) {
        HetParamsMerge params = new HetParamsMerge();
        params.add("deviceId", deviceId);
        String path = "/v1/auth/agree";
        String url = GlobalAddr.HOST;
        if (url.contains("dp") || url.contains("50")) {
            path = "/v1/app/open/auth/agree";
        }
        return api.deviceAgree(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }


    /**
     * 6、获取设备授权的用户
     *
     * @param deviceId 设备ID
     */
    public Observable<ApiResult<List<DeviceAuthUserModel>>> getDeviceAuthUser(String deviceId) {
        HetParamsMerge params = new HetParamsMerge();
        params.add(DeviceParamContant.Device.DEVICE_ID, deviceId);
        String path = "/v1/auth/getDeviceAuthUser";
        String url = GlobalAddr.HOST;
        if (url.contains("dp") || url.contains("50")) {
            path = "/v1/app/open/auth/getDeviceAuthUser";
        }

        return api.getDeviceAuthUser(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }

    /**
     * 7、多设备授权邀请
     *
     * @param deviceIds 设备标识（多个标识用','（逗号）隔开）
     * @param account   帐号（手机、邮箱）
     * @return 0
     */
    public Observable<ApiResult<String>> multiInvite(String deviceIds, String account) {
        HetParamsMerge params = new HetParamsMerge();
        params.add("deviceIds", deviceIds);
        params.add("account", account);
        String path = "/v1/auth/multiInvite";
        String url = GlobalAddr.HOST;
        if (url.contains("dp") || url.contains("50")) {
            path = "/v1/app/open/auth/multiInvite";
        }
        return api.multiInvite(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }

    /**
     * 8、获取已分享出去的设备列表信息
     * appType 应用类型（1-安卓 2-ios）,默认 1 。【2016-09-02 新增】
     */
    public Observable<ApiResult<AuthDeviceModel>> getAuthDevice() {
        HetParamsMerge params = new HetParamsMerge();
        params.add("appType", "1");
        String path = "/v1/auth/getAuthDevice";
        String url = GlobalAddr.HOST;
        if (url.contains("dp") || url.contains("50")) {
            path = "/v1/app/open/auth/getAuthDevice";
        }
        return api.getAuthDevice(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }

    /**
     * 9、获取APP上关联设备授权的所有用户
     */
    public Observable<ApiResult<DeviceAuthUserAll>> getAppDeviceAuthUser() {
        HetParamsMerge params = new HetParamsMerge();
        String path = "/v1/auth/getAppDeviceAuthUser";
        String url = GlobalAddr.HOST;
        if (url.contains("dp") || url.contains("50")) {
            path = "/v1/app/open/auth/getAppDeviceAuthUser";
        }
        return api.getAppDeviceAuthUser(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }

    /**
     * 10、获取分享码
     *
     * @param deviceId  分享的设备（加密的）
     * @param shareType 分享方式 （5 -面对面；6-远程分享）
     * @return ShareCodeModel
     */
    public Observable<ApiResult<ShareCodeModel>> getShareCode(String deviceId, String shareType) {
        HetParamsMerge params = new HetParamsMerge();
        params.add("shareType", shareType);
        params.add("deviceId", deviceId);
        String path = "/v1/auth/getShareCode";
        String url = GlobalAddr.HOST;
        if (url.contains("dp") || url.contains("50")) {
            path = "/v1/app/open/auth/getShareCode";
        }
        return api.getShareCode(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }

    /**
     * 11、根据分享码获取产品信息
     *
     * @param shareCode 分享码
     * @return ShareCodeModel
     */
    public Observable<ApiResult<ShareDeviceUrlModel>> getShareDevice(String shareCode) {
        HetParamsMerge params = new HetParamsMerge();
        params.add("shareCode", shareCode);
        String path = "/v1/auth/getShareDevice";
        String url = GlobalAddr.HOST;
        if (url.contains("dp") || url.contains("50")) {
            path = "/v1/app/open/auth/getShareDevice";
        }
        return api.getShareDevice(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }

    /**
     * 12、授权分享
     *
     * @param shareCode 分享码
     * @param shareType 分享方式 （5 -面对面；6-远程分享）
     * @return ShareCodeModel
     */
    public Observable<ApiResult<String>> authShareDevice(String shareCode, String shareType) {
        HetParamsMerge params = new HetParamsMerge();
        params.add("shareCode", shareCode);
        params.add("shareType", shareType);
        String path = "/v1/auth/authShareDevice";
        String url = GlobalAddr.HOST;
        if (url.contains("dp") || url.contains("50")) {
            path = "/v1/app/open/auth/authShareDevice";
        }
        return api.authShareDevice(path, params
                .setPath(path)
                .isHttps(true)
                .sign(true)
                .accessToken(true)
                .timeStamp(true)
                .getParams()).compose(RxSchedulers.io_main());
    }
}
