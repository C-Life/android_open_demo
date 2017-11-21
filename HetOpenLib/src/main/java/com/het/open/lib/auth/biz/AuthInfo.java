package com.het.open.lib.auth.biz;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.het.open.lib.utils.Utility;


/**
 * auth请求参数
 * @author xuchao
 *
 */
public class AuthInfo {
	private String mAppKey = "";   //用户key

	private String mRedirectUrl = ""; //回调地址

	private String mSecret = "";//用户密钥

	private String mPackageName = ""; //包名

	private String mKeyHash = "";   //包名md5签名

	public String getTitle() {
		return title;
	}

	private String title;   //登录页面标题

	public AuthInfo(Context context, String appKey, String redirectUrl,
					String secret) {
		this.mAppKey = appKey;
		this.mRedirectUrl = redirectUrl;
		this.mSecret= secret;
		this.mPackageName = context.getPackageName();
		this.mKeyHash = Utility.getSign(context, this.mPackageName);

	}

	public AuthInfo(Context context, String appKey, String redirectUrl,
					String secret,String title) {
		this.mAppKey = appKey;
		this.mRedirectUrl = redirectUrl;
		this.mSecret= secret;
		this.mPackageName = context.getPackageName();
		this.mKeyHash = Utility.getSign(context, this.mPackageName);
		this.title=title;

	}



	public AuthInfo(Context context, String appKey, String redirectUrl,
					String secret,int isNew) {
		this.mAppKey = appKey;
		this.mRedirectUrl = redirectUrl;
		this.mSecret= secret;
		this.mPackageName = context.getPackageName();
		this.mKeyHash = Utility.getSign(context, this.mPackageName);
		this.title=title;

	}

	public String getAppKey() {
		return this.mAppKey;
	}

	public String getRedirectUrl() {
		return this.mRedirectUrl;
	}

	public String getAppSecret() {
		return this.mSecret;
	}

	public String getPackageName() {
		return this.mPackageName;
	}

	public String getKeyHash() {
		return this.mKeyHash;
	}

	public Bundle getAuthBundle() {
		Bundle mBundle = new Bundle();
		mBundle.putString("appKey", this.mAppKey);
		mBundle.putString("redirectUri", this.mRedirectUrl);
		mBundle.putString("appSecret", this.mSecret);
		mBundle.putString("packagename", this.mPackageName);
		mBundle.putString("key_hash", this.mKeyHash);
		if (!TextUtils.isEmpty(title)){
			mBundle.putString("loginTitle", this.title);
		}
		mBundle.putString("isNew", "1");
		return mBundle;
	}

	public static AuthInfo parseBundleData(Context context, Bundle data) {
		String appKey = data.getString("appKey");
		String redirectUrl = data.getString("redirectUri");
		String appSecret= data.getString("appSecret");
		return new AuthInfo(context, appKey, redirectUrl, appSecret);
	}
}
