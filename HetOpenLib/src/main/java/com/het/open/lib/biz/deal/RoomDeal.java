package com.het.open.lib.biz.deal;

import com.het.open.lib.callback.IHetCallback;

import java.util.TreeMap;

/**
 * Auth逻辑
 * Created by xuchao on 2016/3/14.
 */
public class RoomDeal {

    /**
     * 获取房间列表
     * @param callback 回调
     */
    public static void getRoomList(IHetCallback callback) {

        String path = "/v1/user/room/list";;
        HttpDeal.hetClifeGet(path,callback);


    }

    /**
     * 新增房间
     * @param callback 回调
     */
    public static void addRoom(IHetCallback callback,String roomName) {

       TreeMap<String, String> params = new TreeMap<String, String>();
       params.put("roomName",roomName);
        String path = "/v1/user/room/add";;
        HttpDeal.hetClifePost(path,params,callback);


    }

    /**
     * 更新房间
     * @param callback 回调
     */
    public static void updateRoom(IHetCallback callback,String roomName,String roomId) {

        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("roomName",roomName);
        params.put("roomId",roomId);
        String path = "/v1/user/room/update";;
        HttpDeal.hetClifePost(path,params,callback);


    }

    /**
     * 删除房间
     * @param callback 回调
     */
    public static void delRoom(IHetCallback callback,String roomId) {

        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("roomId",roomId);
        String path =  "/v1/user/room/delete";;
        HttpDeal.hetClifePost(path,params,callback);


    }

}
