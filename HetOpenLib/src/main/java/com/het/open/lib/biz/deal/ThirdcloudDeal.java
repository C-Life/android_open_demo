package com.het.open.lib.biz.deal;


import com.het.basic.data.api.token.TokenManager;
import com.het.basic.data.api.token.model.AuthModel;
import com.het.basic.utils.GsonUtil;
import com.het.log.Logc;
import com.het.open.lib.auth.thirdauth.ThirdAuthApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.AuthRandomCodeModel;
import com.het.open.lib.model.AuthorizationCodeModel;

public class ThirdcloudDeal {
    /**
     * @param callback 返回结果的回调
     */
    public static void getAuthorizationCode(IHetCallback callback,String account,String openId) {
        ThirdAuthApi.getInstance().getAuthorizationCode(account,openId).subscribe(s -> {
            if (s.getCode() != 0) {
                Logc.e("getAuthorizationCode e =" + s.getMsg());
                callback.onFailed(s.getCode(), s.getMsg());
                return;
            }
            AuthorizationCodeModel codeModel = s.getData();
            String json = GsonUtil.getInstance().getGson().toJsonTree(codeModel).toString();
            callback.onSuccess(s.getCode(), json);
        }, e -> {
            Logc.e("getAuthorizationCode e =" + e.getMessage());
            callback.onFailed(-1, e.getMessage());
        });
    }

    /**
     * @param callback 返回结果的回调
     */
    public static void checkRandomCode(IHetCallback callback, String verificationCode, String randomCode) {
        ThirdAuthApi.getInstance().checkRandomCode(verificationCode, randomCode).subscribe(s -> {
            if (s.getCode() != 0) {
                Logc.e("checkRandomCode e =" + s.getMsg());
                callback.onFailed(s.getCode(), s.getMsg());
                return;
            }
            AuthRandomCodeModel codeModel = s.getData();
            if (codeModel != null) {
                AuthModel authModel = new AuthModel();
                authModel.setAccessToken(codeModel.getAccessToken());
                authModel.setAccessTokenExpires(codeModel.getExpiresIn());
                authModel.setRefreshToken(codeModel.getRefreshToken());
                TokenManager.getInstance().setAuthModel(authModel);
            }
//            String json = GsonUtil.getInstance().getGson().toJsonTree(codeModel).toString();
            callback.onSuccess(s.getCode(), codeModel == null ? null : codeModel.getOpenId());
        }, e -> {
            Logc.e("checkRandomCode e =" + e.getMessage());
            callback.onFailed(-1, e.getMessage());
        });
    }

}
