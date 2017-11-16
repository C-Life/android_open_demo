package com.het.open.lib.biz.api;

import android.text.TextUtils;

import com.het.basic.base.helper.RxSchedulers;
import com.het.basic.data.api.token.HetParamsMerge;
import com.het.basic.data.http.okhttp.util.OkHttpTag;
import com.het.basic.data.http.retrofit2.RetrofitManager;
import com.het.basic.model.ApiResult;
import com.het.open.lib.biz.constans.GlobalAddr;
import com.het.open.lib.biz.services.MessageService;
import com.het.open.lib.model.message.DeviceDetailBean;
import com.het.open.lib.model.message.MessageListByPageBean;

import rx.Observable;

/**
 * Created by  on 2017-10-11.
 *
 */

public class MessageApi {
    private static MessageApi instance = null;
    private MessageService api;

    private void createapi() {
        api = RetrofitManager.getRetrofit(new OkHttpTag(this.getClass().getName())).create(MessageService.class);
    }

    public static MessageApi getInstance() {
        if (instance == null) {
            synchronized (MessageApi.class) {
                if (instance == null) {
                    instance = new MessageApi();
                }
            }
        }
        instance.createapi();
        return instance;
    }

    public Observable<ApiResult<MessageListByPageBean>> getListByPage(String messageId, String messageType, String pageRows) {
        String path = "/v1/message/getListByPage";
        String url = GlobalAddr.HOST;
        if (url.contains("dp") || url.contains("50")) {
            path = "/v1/app/open/message/getListByPage";
        }
        HetParamsMerge paramsMerge = new HetParamsMerge();
        if (!TextUtils.isEmpty(messageId)) {
            paramsMerge.add("messageId", messageId);
        }
        paramsMerge.add("messageType", messageType);
        paramsMerge.add("pageRows", pageRows);
        paramsMerge.setPath(path).isHttps(true).sign(false).accessToken(true).timeStamp(true);
        return this.api.getListByPage(path, paramsMerge.getParams()).compose(RxSchedulers.io_main());
    }

    public Observable<ApiResult> delete( String messageId) {
        String path = "/v1/message/delete";
        String url = GlobalAddr.HOST;
        if (url.contains("dp") || url.contains("50")) {
            path = "/v1/app/open/message/delete";
        }
        return this.api.delete(path, (new HetParamsMerge()).add("messageId", messageId).setPath(path).isHttps(true).sign(false).accessToken(true).timeStamp(true).getParams()).compose(RxSchedulers.io_main());
    }

    public Observable<ApiResult> msgReaded(String messageId) {
        String path = "/v1/message/msgReaded";
        String url = GlobalAddr.HOST;
        if (url.contains("dp") || url.contains("50")) {
            path = "/v1/app/open/message/msgReaded";
        }
        return this.api.msgReaded(path, (new HetParamsMerge()).add("messageId", messageId).setPath(path).isHttps(true).sign(false).accessToken(false).timeStamp(true).getParams()).compose(RxSchedulers.io_main());
    }

    public Observable<ApiResult<DeviceDetailBean>> getBydeviceId(String deviceId) {
        String path = "/v1/device/product/getBydeviceId";
        String url = GlobalAddr.HOST;
        if (url.contains("dp") || url.contains("50")) {
            path = "/v1/app/open/device/product/getBydeviceId";
        }
        return this.api.getBydeviceId(path, (new HetParamsMerge()).add("deviceId", deviceId).setPath(path).isHttps(true).sign(false).accessToken(true).timeStamp(true).getParams()).compose(RxSchedulers.io_main());
    }

    public Observable<ApiResult> agreeFriend(String friendId) {
        String path = "/v1/friend/agree";
        String url = GlobalAddr.HOST;
        if (url.contains("dp") || url.contains("50")) {
            path = "/v1/app/open/friend/agree";
        }
        return this.api.agreeFriend(path, (new HetParamsMerge()).add("friendId", friendId).setPath(path).isHttps(true).sign(true).accessToken(true).timeStamp(true).getParams()).compose(RxSchedulers.io_main());
    }

    public Observable<ApiResult> updateMsg(String messageId) {
        String path = "/v1/message/update";
        String url = GlobalAddr.HOST;
        if (url.contains("dp") || url.contains("50")) {
            path = "/v1/app/open/message/update";
        }
        return this.api.updateMsg(path, (new HetParamsMerge()).add("messageId", messageId).setPath(path).isHttps(true).sign(false).accessToken(true).timeStamp(true).getParams()).compose(RxSchedulers.io_main());
    }


}
