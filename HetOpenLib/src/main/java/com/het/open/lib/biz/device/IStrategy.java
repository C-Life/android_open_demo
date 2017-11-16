package com.het.open.lib.biz.device;

import com.het.open.lib.model.SendPacketData;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * 名称: IStrategy <br>
 * 版本: 1.0<br>
 * 日期: 2016/8/8 14:14<br>
 * 描述: 发送策略 接口
 **/

public interface IStrategy {
    void addFilter(SendPacketData packetData);
}
