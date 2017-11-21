package com.het.open.lib.callback;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p/>
 * <p>描述：发控制成功失败</p>
 * 名称: ICtrlCallback <br>
 * 作者: uuxia-mac<br>
 * 版本: 1.0<br>
 * 日期: 16/7/24 21:02<br>
 **/
public interface ICtrlCallback {
    void onSucess();

    void onFailed(Throwable throwable);

    void onProtocolError(Throwable throwable);
}
