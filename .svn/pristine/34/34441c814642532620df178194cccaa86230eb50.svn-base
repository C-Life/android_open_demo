package com.het.open.lib.biz.services;

import com.het.basic.model.ApiResult;
import com.het.open.lib.model.share.AuthDeviceModel;
import com.het.open.lib.model.share.DeviceAuthUserAll;
import com.het.open.lib.model.share.DeviceAuthUserModel;
import com.het.open.lib.model.share.ShareCodeModel;
import com.het.open.lib.model.share.ShareDeviceUrlModel;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  <br>
 * 作者: 80010814 4<br>
 * 日期: 2017/8/3<br>
 **/

public interface DeviceShareService {

    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult<String>> getAllAuthDevice(@Path("path") String path, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult<String>> deviceInvite(@Path("path") String path, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult<String>> userAuth(@Path("path") String path, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult<String>> deviceDel(@Path("path") String path, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult<String>> deviceAgree(@Path("path") String path, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult<List<DeviceAuthUserModel>>> getDeviceAuthUser(@Path("path") String path, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult<String>> multiInvite(@Path("path") String path, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult<AuthDeviceModel>> getAuthDevice(@Path("path") String path, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult<DeviceAuthUserAll>> getAppDeviceAuthUser(@Path("path") String path, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult<ShareCodeModel>> getShareCode(@Path("path") String path, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult<ShareDeviceUrlModel>> getShareDevice(@Path("path") String path, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("{path}")
    Observable<ApiResult<String>> authShareDevice(@Path("path") String path, @FieldMap Map<String, String> map);


}
