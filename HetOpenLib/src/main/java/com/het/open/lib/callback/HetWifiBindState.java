package com.het.open.lib.callback;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  wifi设备绑定状态<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public enum HetWifiBindState {
	NONE(0),START_SCAN(1), COMMIT_DEVICE_MESS(2),SERVER_MESS(3), START_BIND(4), BIND_SUCCESS(
			5), ERROR(6);

	private int nCode;

	// 构造函数，枚举类型只能为私有

	HetWifiBindState(int _nCode) {

		this.nCode = _nCode;

	}

	@Override
	public String toString() {

		return String.valueOf(this.nCode);

	}
}
