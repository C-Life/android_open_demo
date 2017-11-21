package com.het.open.lib.auth.biz;

import android.content.Context;

import com.het.open.lib.auth.callback.HetAuthListener;


public class HetHandler {

	private Context mAuthActivity;
	private AuthInfo mAuthInfo;
	private HetAuthListener mHetAuthListener;
	private int mSSOAuthRequestCode = 0;
	private HetAuthHandler mWebAuthHandler;

	public HetHandler(Context activity, AuthInfo mAuthInfo) {
		this.mAuthActivity = activity;
		this.mAuthInfo = mAuthInfo;
		this.mWebAuthHandler = new HetAuthHandler(activity, mAuthInfo);	
	}

	public void authorizeWeb(HetAuthListener listener) {
		authorize(32973, listener);
	}

	private void authorize(int requestCode, HetAuthListener listener) {
		this.mSSOAuthRequestCode = requestCode;
		this.mHetAuthListener = listener;
		if (listener != null) {
			this.mWebAuthHandler.anthorize(listener);
		}

	}
}
