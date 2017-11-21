package com.het.open.lib.api;

import com.het.open.lib.biz.deal.RoomDeal;
import com.het.open.lib.callback.IHetCallback;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2016, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * <p>
 * <p>描述：</p>
 * 名称: 用户房间相关api<br>
 * 作者: 80010814<br>
 * 版本: 1.0<br>
 * 日期: 2016/3/10. 16:42<br>
 */
public class HetRoomApi {
    private static HetRoomApi mInstance;

    private HetRoomApi() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HetRoomApi getInstance() {
        if (mInstance == null) {
            synchronized (HetRoomApi.class) {
                mInstance = new HetRoomApi();
            }
        }
        return mInstance;
    }

    /**
     * 获取房间列表
     *
     * @param iCallback 回调
     */
    public static void getRoomList(final IHetCallback iCallback) {
        RoomDeal.getRoomList(iCallback);

    }


    /**
     * 新增房间
     *
     * @param iCallback 回调
     * @param roomName  房间名称
     */
    public static void addRoom(final IHetCallback iCallback,String roomName) {
        RoomDeal.addRoom(iCallback,roomName);
    }

    /**
     * 更新房间信息
     *
     * @param iCallback 回调
     * @param roomName  房间名称
     * @param roomId    房间id
     */
    public static void updateRoom(final IHetCallback iCallback,String roomName,String roomId) {
        RoomDeal.updateRoom(iCallback,roomName,roomId);

    }

    /**
     * 删除房间
     *
     * @param iCallback 回调
     * @param roomId    房间id
     */
    public static void delRoom(final IHetCallback iCallback,String roomId) {
        RoomDeal.delRoom(iCallback,roomId);
    }
}
