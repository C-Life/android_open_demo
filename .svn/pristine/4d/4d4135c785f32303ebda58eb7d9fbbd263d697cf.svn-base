package com.het.open.lib.biz.deal;

import com.het.basic.utils.GsonUtil;
import com.het.log.Logc;
import com.het.open.lib.biz.api.MessageApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.message.DeviceDetailBean;
import com.het.open.lib.model.message.MessageListByPageBean;

/**
 * 消息api
 * Created by xuchao on 2016/6/1.
 */
public class MessageDeal {

    /**
     * 刷新列表
     *
     * @param callback    回调
     * @param messageId   消息ID
     * @param messageType 消息类型
     * @param pageRows    每页数据大小
     */
    public static void refreshList(IHetCallback callback, String messageId, String messageType, String pageRows) {
        MessageApi.getInstance().getListByPage(messageId, messageType, pageRows).subscribe(s -> {
            if (s.getCode() != 0) {
                Logc.e("refreshList e =" + s.getMsg());
                callback.onFailed(s.getCode(), s.getMsg());
                return;
            }
            MessageListByPageBean listByPage = s.getData();
            String json = GsonUtil.getInstance().getGson().toJsonTree(listByPage).toString();
            callback.onSuccess(s.getCode(), json);
        }, e -> {
            Logc.e("refreshList e =" + e.getMessage());
            callback.onFailed(-1, e.getMessage());
        });
    }

    /**
     * 加载更多
     *
     * @param callback    回调
     * @param messageId   消息ID
     * @param messageType 消息类型
     * @param pageRows    每页数据大小
     */
    public static void loadList(IHetCallback callback, String messageId, String messageType, String pageRows) {
        MessageApi.getInstance().getListByPage(messageId, messageType, pageRows).subscribe(s -> {
            if (s.getCode() != 0) {
                Logc.e("loadList e =" + s.getMsg());
                callback.onFailed(s.getCode(), s.getMsg());
                return;
            }
            MessageListByPageBean listByPage = s.getData();
            String json = GsonUtil.getInstance().getGson().toJsonTree(listByPage).toString();
            callback.onSuccess(s.getCode(), json);
        }, e -> {
            Logc.e("loadList e =" + e.getMessage());
            callback.onFailed(-1, e.getMessage());
        });
    }

    /**
     * 删除消息
     *
     * @param callback  回调
     * @param messageId 消息ID
     */
    public static void deleteMessage(IHetCallback callback, String messageId) {
        MessageApi.getInstance().delete(messageId).subscribe(s -> {
            if (s.getCode() != 0) {
                callback.onFailed(s.getCode(), s.getMsg());
                return;
            }
            callback.onSuccess(s.getCode(), null);
        }, e -> {
            Logc.e("deleteMessage e =" + e.getMessage());
            callback.onFailed(-1, e.getMessage());
        });
    }

    /**
     * 消息标记为已读
     *
     * @param callback  回调
     * @param messageId 消息ID
     */
    public static void readMessage(IHetCallback callback, String messageId) {
        MessageApi.getInstance().msgReaded(messageId).subscribe(s -> {
            if (s.getCode() != 0) {
                callback.onFailed(s.getCode(), s.getMsg());
                return;
            }
            callback.onSuccess(s.getCode(), null);
        }, e -> {
            Logc.e("readMessage e =" + e.getMessage());
            callback.onFailed(-1, e.getMessage());
        });
    }

    /**
     * 获取设备详情
     *
     * @param callback 回调
     * @param deviceId 设备ID
     */
    public static void getBydeviceId(IHetCallback callback, String deviceId) {
        MessageApi.getInstance().getBydeviceId(deviceId).subscribe(s -> {
            if (s.getCode() != 0) {
                callback.onFailed(s.getCode(), s.getMsg());
                return;
            }
            DeviceDetailBean bean = s.getData();
            String json = GsonUtil.getInstance().getGson().toJsonTree(bean).toString();
            callback.onSuccess(s.getCode(), json);
        }, e -> {
            Logc.e("getBydeviceId e =" + e.getMessage());
            callback.onFailed(-1, e.getMessage());
        });
    }


    /**
     * 消息标记为已读
     *
     * @param callback  回调
     * @param messageId 消息ID
     */
    public static void updateMsg(IHetCallback callback, String messageId) {
        MessageApi.getInstance().updateMsg(messageId).subscribe(s -> {
            if (s.getCode() != 0) {
                callback.onFailed(s.getCode(), s.getMsg());
                return;
            }
            callback.onSuccess(s.getCode(), null);
        }, e -> {
            Logc.e("readMessage e =" + e.getMessage());
            callback.onFailed(-1, e.getMessage());
        });
    }
}
