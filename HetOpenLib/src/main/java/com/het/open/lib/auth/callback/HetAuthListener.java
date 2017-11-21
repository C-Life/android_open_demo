package com.het.open.lib.auth.callback;


import com.het.open.lib.utils.HetException;

public  interface HetAuthListener {
	void onComplete(String msg);

	void onHetException(HetException paramHetException);

	void onCancel();
}
