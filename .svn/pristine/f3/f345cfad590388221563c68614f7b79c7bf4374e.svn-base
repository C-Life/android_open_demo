package com.het.open.lib.auth.biz;


import android.content.Context;
import android.text.TextUtils;

import com.het.open.lib.auth.callback.HetAuthListener;

import java.util.HashMap;
import java.util.Map;

/**
 * oauth回调接口管理
 * @author xuchao
 *
 */
public class HetCallbackManager {
	private static HetCallbackManager sInstance;
	private Context mContext;
	private Map<String, HetAuthListener> mHetAuthListenerMap;

	private HetCallbackManager(Context context) {
		this.mContext = context;
		this.mHetAuthListenerMap = new HashMap();

	}

	public static synchronized HetCallbackManager getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new HetCallbackManager(context);
		}
		return sInstance;
	}

	public synchronized HetAuthListener getHetAuthListener(String callbackId) {
		if (TextUtils.isEmpty(callbackId)) {
			return null;
		}
		return ((HetAuthListener) this.mHetAuthListenerMap.get(callbackId));
	}

	public synchronized void setHetAuthListener(String callbackId,
			HetAuthListener authListener) {
		if ((TextUtils.isEmpty(callbackId)) || (authListener == null)) {
			return;
		}
		this.mHetAuthListenerMap.put(callbackId, authListener);
	}

	public synchronized void removeHetAuthListener(String callbackId) {
		if (TextUtils.isEmpty(callbackId)) {
			return;
		}
		this.mHetAuthListenerMap.remove(callbackId);
	}

	public String genCallbackKey() {
		return String.valueOf(System.currentTimeMillis());
	}

}
