package com.het.open.lib.biz.device;

import com.het.open.lib.model.SendPacketData;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * 名称: ContextStratrgy <br>
 * 版本: 1.0<br>
 * 日期: 2016/8/8 14:14<br>
 * 描述: 发送策略  扩展策略之外的功能
 **/

public class ContextStratrgy {
    IStrategy strategy;

    public ContextStratrgy(IStrategy strategy) {
        this.strategy = strategy;
    }

    public void addFilter(SendPacketData packetData) {
        this.strategy.addFilter(packetData);
    }
}
