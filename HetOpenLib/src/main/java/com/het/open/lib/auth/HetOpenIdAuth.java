package com.het.open.lib.auth;

import com.het.open.lib.callback.AuthCallback;

/**
 * Created by xuchao on 2016/4/6.
 */
public class HetOpenIdAuth {

    /**
     * 获取openId
     * @param openId
     * @param authCallback
     */
    public void getOpenId(String openId,String account, final AuthCallback authCallback){

//        new AuthApi().openAuth(new ICallback<String>() {
//
//
//            @Override
//            public void onSuccess(String s, int id) {
//                AuthModel authModel = GsonUtil.getGsonInstance().fromJson(s, AuthModel.class);
//                TokenManager.getInstance().setAuthModel(authModel);
//                authCallback.onSuccess(HetCodeConstants.HTTP_RET_SUCCESS,authModel.getOpenId());
//            }
//
//            @Override
//            public void onFailure(int code, String msg, int id) {
//                authCallback.onFailed(code,msg);
//
//            }
//        },openId,account,-1);

    }


}
