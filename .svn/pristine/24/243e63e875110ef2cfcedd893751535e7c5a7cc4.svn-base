package com.het.open.lib.biz.deal;

import android.net.Uri;
import android.text.TextUtils;

import com.het.basic.data.http.retrofit2.exception.ApiException;
import com.het.open.lib.biz.api.DeviceBindApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.api.HetCodeConstants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 消息api
 * Created by xuchao on 2016/6/1.
 */
public class QrcodeDeal {

    /**
     * 获取消息
     *
     * @param callback 调用成功的回调监听
     */
    public static void qrcodeDeal(String url, IHetCallback callback) {
        String param = Uri.parse(url).getQueryParameter("param");
        if (TextUtils.isEmpty(param)) {
            callback.onFailed(HetCodeConstants.HTTP_RET_FAILED, "qcode error");
            return;
        }
        try {
            JSONObject mJsonObject = new JSONObject(param);
            String productId = mJsonObject.getString("a");
            if (TextUtils.isEmpty(productId)) {
                callback.onFailed(HetCodeConstants.HTTP_RET_FAILED, "qcode error");
                return;
            }
            //根据productId获取品设备息
            DeviceBindApi.getInstance().getProductFromSdk(productId).subscribe(s -> {
                if (s.getCode() == 0) {
                    if (s.getData() == null) {
                        callback.onFailed(HetCodeConstants.HTTP_RET_FAILED, "qcode error");
                        return;
                    }
                    // DeviceProductBean deviceProductBean = (DeviceProductBean)s.getData();
                    callback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS, s.getData().toString());
                } else {
                    callback.onFailed(HetCodeConstants.HTTP_RET_FAILED, "qcode no support");
                }
            }, e -> {
                if (e instanceof ApiException) {
                    callback.onFailed(((ApiException) e).getCode(), e.getMessage());
                } else {
                    callback.onFailed(HetCodeConstants.HTTP_RET_FAILED, "qcode error");
                }

            });
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onFailed(HetCodeConstants.HTTP_RET_FAILED, "JSON error");
        }

    }


}
