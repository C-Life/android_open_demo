package com.het.open.lib.biz.services;

import com.het.basic.model.ApiResult;
import com.het.open.lib.model.message.DeviceDetailBean;
import com.het.open.lib.model.message.MessageBean;
import com.het.open.lib.model.message.MessageListByPageBean;
import com.het.open.lib.model.message.MessageTypeBean;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2017-10-11.
 */

public interface MessageService {
    /**
     * 主消息列表
     */
    @GET("{path}")
    Observable<ApiResult<List<MessageTypeBean>>> getMsgTypeList(@Path("path") String path, @QueryMap Map<String, String> map);

    /**
     * 消息列表
     */
    @GET("{path}")
    Observable<ApiResult<MessageListByPageBean>> getListByPage(@Path("path") String path, @QueryMap Map<String, String> map);

    /**
     * 删除消息
     */
    @GET("{path}")
    Observable<ApiResult> delete(@Path("path") String path, @QueryMap Map<String, String> map);

    /**
     * 读取消息
     */
    @GET("{path}")
    Observable<ApiResult> msgReaded(@Path("path") String path, @QueryMap Map<String, String> map);

    /**
     * 获取设备详情
     */
    @GET("{path}")
    Observable<ApiResult<DeviceDetailBean>> getBydeviceId(@Path("path") String path, @QueryMap Map<String, String> map);

    /**
     * 同意设备邀请
     */
    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult> deviceAgree(@Path("path") String path, @FieldMap Map<String, String> map);

    /**
     * 更新消息状态
     */
    @GET("{path}")
    Observable<ApiResult> updateMsg(@Path("path") String path, @QueryMap Map<String, String> map);

    /**
     * 同意添加好友
     */
    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult> agreeFriend(@Path("path") String path, @FieldMap Map<String, String> map);

    /**
     * 获取消息详情
     */
    @GET("{path}")
    Observable<ApiResult<MessageBean>> getMsgDetail(@Path("path") String path, @QueryMap Map<String, String> map);
}
