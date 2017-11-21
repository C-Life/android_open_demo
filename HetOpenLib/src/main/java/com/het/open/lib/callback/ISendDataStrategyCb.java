package com.het.open.lib.callback;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称: ISendDataStrategyCb <br>
 * 作者: uuxia<br>
 * 版本: 1.0<br>
 * 日期: 2016/8/8 14:22<br>
 **/
public interface ISendDataStrategyCb<T> {
    void filterData(T packetData);
}
