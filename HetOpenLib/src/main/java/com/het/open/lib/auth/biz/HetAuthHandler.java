package com.het.open.lib.auth.biz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.het.open.lib.auth.callback.HetAuthListener;
import com.het.open.lib.biz.constans.GlobalAddr;
import com.het.open.lib.ui.HetWebViewActivity;


class HetAuthHandler {
	private static final String TAG = HetAuthHandler.class.getName();
	private static final String NETWORK_NOT_AVAILABLE_EN = "Network is not available";
	private static final String NETWORK_NOT_AVAILABLE_ZH_CN = "无法连接到网络，请检查网络配置";
	private static final String NETWORK_NOT_AVAILABLE_ZH_TW = "無法連接到網络，請檢查網络配置";
	private static final int OBTAIN_AUTH_CODE = 0;
	private static final int OBTAIN_AUTH_TOKEN = 1;
	private Context mContext;
	private AuthInfo mAuthInfo;

	public HetAuthHandler(Context context, AuthInfo authInfo) {
		this.mContext = context;
		this.mAuthInfo = authInfo;
	}

	public AuthInfo getAuthInfo() {
		return this.mAuthInfo;
	}

	public void anthorize(HetAuthListener listener) {
		authorize(listener, 1);
	}

	public void authorize(HetAuthListener listener, int type) {
		startDialog(listener, type);
		// WbAppInstallActivator.getInstance(this.mContext,
		// this.mAuthInfo.getAppKey()).activateWeiboInstall();
	}

	private void startDialog(HetAuthListener listener, int type) {
		if (listener == null) {
			return;
		}
		HetParameters requestParams = new HetParameters(
				this.mAuthInfo.getAppKey());
		requestParams.put("appId", this.mAuthInfo.getAppKey());
		requestParams.put("redirectUri", this.mAuthInfo.getRedirectUrl());
		requestParams.put("scope", this.mAuthInfo.getAppSecret());
		requestParams.put("state", "code");
		//requestParams.put("isNew", "1");
        String title=this.mAuthInfo.getTitle();
		if (!TextUtils.isEmpty(title)){
			requestParams.put("loginTitle", title);
		}
		// requestParams.put("response_type", "code");
		// requestParams.put("version", "001");
		// String aid = Utility.getAid(this.mContext,
		// this.mAuthInfo.getAppKey());
		// if (!(TextUtils.isEmpty(aid))) {
		// requestParams.put("aid", aid);
		// }
		//
		// if (1 == type) {
		// requestParams.put("packagename", this.mAuthInfo.getPackageName());
		// requestParams.put("key_hash", this.mAuthInfo.getKeyHash());
		// }
		//String authUrl=GlobalAddr.LOCAl_HOST;
		String authUrl= GlobalAddr.HOST;
		if (authUrl.contains("dp")||authUrl.contains("50")){
			authUrl=authUrl+"/v1/app/open";
		}
        String url= authUrl+"/oauth2/authorize?"+ requestParams.encodeUrl();
		//String url= "http://10.8.9.92:8011/page/noteVerify.html"+"/oauth2/authorize?"+ requestParams.encodeUrl();
		//String url= "http://10.8.9.92:8011/page/noteVerify.html";
		AuthRequestParam req = new AuthRequestParam(this.mContext);
		req.setAuthInfo(this.mAuthInfo);
		req.setAuthListener(listener);
		req.setUrl(url);
		req.setSpecifyTitle("clife开放平台");
		Bundle data = req.createRequestParamBundle();
		Intent intent = new Intent(this.mContext, HetWebViewActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtras(data);
		this.mContext.startActivity(intent);

	}
}
