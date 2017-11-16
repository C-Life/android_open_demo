package com.het.open.lib.api;

import com.het.open.lib.biz.deal.MessageDeal;
import com.het.open.lib.callback.IHetCallback;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称:  消息模块<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetMessageApi {
    private static HetMessageApi mInstance;

    private HetMessageApi() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetMessageApi getInstance() {
        if (mInstance == null) {
            synchronized (HetMessageApi.class) {
                mInstance = new HetMessageApi();
            }
        }
        return mInstance;
    }

    /**
     * 刷新列表
     *
     * @param callback    回调
     * @param messageId   消息ID
     * @param messageType 消息类型
     * @param pageRows    每页数据大小
     */
    public void refreshList(IHetCallback callback, String messageId, String messageType, String pageRows) {
        MessageDeal.refreshList(callback, messageId, messageType, pageRows);
    }

    /**
     * 加载更多
     *
     * @param callback    回调
     * @param messageId   消息ID
     * @param messageType 消息类型
     * @param pageRows    每页数据大小
     */
    public void loadList(IHetCallback callback, String messageId, String messageType, String pageRows) {
        MessageDeal.loadList(callback, messageId, messageType, pageRows);
    }

    /**
     * 删除消息
     *
     * @param callback  回调
     * @param messageId 消息ID
     */
    public void deleteMessage(IHetCallback callback, String messageId) {
        MessageDeal.deleteMessage(callback, messageId);
    }


    /**
     * 消息标记为已读
     *
     * @param callback  回调
     * @param messageId 消息ID
     */
    public void readMessage(IHetCallback callback, String messageId) {
        MessageDeal.readMessage(callback, messageId);
    }

    /**
     * 获取设备详情
     *
     * @param callback 回调
     * @param deviceId 设备ID
     */
    public void getBydeviceId(IHetCallback callback, String deviceId) {
        MessageDeal.getBydeviceId(callback, deviceId);
    }


    /**
     * 消息标记为已读
     *
     * @param callback  回调
     * @param messageId 消息ID
     */
    public void updateMsg(IHetCallback callback, String messageId) {
        MessageDeal.updateMsg(callback, messageId);
    }

}
