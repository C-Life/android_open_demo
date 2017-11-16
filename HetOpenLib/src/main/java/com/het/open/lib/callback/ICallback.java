package com.het.open.lib.callback;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称: 接口回调<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public interface ICallback<T> {
    /**
     *  call when volley access success and the response code is SUCCESS
     */
    void onSuccess(T t, int id);

    /**
     *  call when volley access success and the response code is not SUCCESS
     */
    void onFailure(int code, String msg, int id);
}
