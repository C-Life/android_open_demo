package com.het.open.lib.auth.biz;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

public abstract class BrowserRequestParamBase {
	public static final int EXEC_REQUEST_ACTION_OK = 1;
	public static final int EXEC_REQUEST_ACTION_ERROR = 2;
	public static final int EXEC_REQUEST_ACTION_CANCEL = 3;
	public static final String EXTRA_KEY_LAUNCHER = "key_launcher";
	protected static final String EXTRA_KEY_URL = "key_url";
	protected static final String EXTRA_KEY_SPECIFY_TITLE = "key_specify_title";
	protected Context mContext;
	protected String mUrl;
	protected BrowserLauncher mLaucher;
	protected String mSpecifyTitle;

	public BrowserRequestParamBase(Context context) {
		this.mContext = context.getApplicationContext();
	}

	public void setupRequestParam(Bundle data) {
		this.mUrl = data.getString("key_url");
		this.mLaucher = ((BrowserLauncher) data.getSerializable("key_launcher"));
		this.mSpecifyTitle = data.getString("key_specify_title");
		onSetupRequestParam(data);
	}

	protected abstract void onSetupRequestParam(Bundle paramBundle);

	public Bundle createRequestParamBundle() {
		Bundle data = new Bundle();
		if (!(TextUtils.isEmpty(this.mUrl))) {
			data.putString("key_url", this.mUrl);
		}
		if (this.mLaucher != null) {
			data.putSerializable("key_launcher", this.mLaucher);
		}
		if (!(TextUtils.isEmpty(this.mSpecifyTitle))) {
			data.putString("key_specify_title", this.mSpecifyTitle);
		}

		onCreateRequestParamBundle(data);

		return data;
	}

	protected abstract void onCreateRequestParamBundle(Bundle paramBundle);

	public abstract void execRequest(Activity paramActivity, int paramInt);

	public void setUrl(String url) {
		this.mUrl = url;
	}

	public String getUrl() {
		return this.mUrl;
	}

	public void setLauncher(BrowserLauncher launcher) {
		this.mLaucher = launcher;
	}

	public BrowserLauncher getLauncher() {
		return this.mLaucher;
	}

	public void setSpecifyTitle(String title) {
		this.mSpecifyTitle = title;
	}

	public String getSpecifyTitle() {
		return this.mSpecifyTitle;
	}

}
