package com.het.sdk.demo.ui.activity.message;

import com.google.gson.reflect.TypeToken;
import com.het.basic.base.RxManage;
import com.het.basic.utils.GsonUtil;
import com.het.log.Logc;
import com.het.open.lib.api.HetMessageApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.message.DeviceDetailBean;
import com.het.open.lib.model.message.MessageListByPageBean;
import com.het.sdk.demo.base.BaseHetPresenter;
import com.het.sdk.demo.event.HetMessageEvent;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017-10-11.
 */

public class MsgListPresenter extends BaseHetPresenter<MesListView> {

    public final static int MSG_TYPE_LIST_REFRESH_SUCCESS = 1001;
    public final static int MSG_TYPE_LIST_REFRESH_FAIL = 1002;
    public final static int MSG_TYPE_LIST_LOAD_SUCCESS = 1003;
    public final static int MSG_TYPE_LIST_LOAD_FAIL = 1004;
    public final static int MSG_TYPE_LIST_DELETE_SUCCESS = 1005;
    public final static int MSG_TYPE_LIST_DELETE_FAIL = 1006;
    public final static int MSG_TYPE_LIST_UPDATE_SUCCESS = 1009;
    public final static int MSG_TYPE_LIST_UPDATE_FAIL = 1010;

    /**
     * 刷新消息列表
     *
     * @param messageId   消息ID
     * @param messageType 消息类型
     * @param pageRows    每页的数据大小
     */
    public void refreshList(String messageId, String messageType, String pageRows) {
        HetMessageApi.getInstance().refreshList(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (code != 0) {
                    Logc.e("refreshList e =" + msg);
                    mHetView.Failed(MSG_TYPE_LIST_REFRESH_FAIL, msg);
                    return;
                }
                Type type = new TypeToken<MessageListByPageBean>() {
                }.getType();
                MessageListByPageBean listByPage = GsonUtil.getInstance().getGson().fromJson(msg, type);
                mHetView.success(MSG_TYPE_LIST_REFRESH_SUCCESS, listByPage, 0);
            }

            @Override
            public void onFailed(int code, String msg) {
                mHetView.Failed(MSG_TYPE_LIST_REFRESH_FAIL, msg);
            }
        }, messageId, messageType, pageRows);
    }


    /**
     * 加载消息列表
     *
     * @param messageId   消息ID
     * @param messageType 消息类型
     * @param pageRows    每页的数据大小
     */
    public void loadList(String messageId, String messageType, String pageRows) {
        HetMessageApi.getInstance().loadList(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (code != 0) {
                    Logc.e("loadList e =" + msg);
                    mHetView.Failed(MSG_TYPE_LIST_LOAD_FAIL, msg);
                    return;
                }
                Type type = new TypeToken<MessageListByPageBean>() {
                }.getType();
                MessageListByPageBean listByPage = GsonUtil.getInstance().getGson().fromJson(msg, type);
                mHetView.success(MSG_TYPE_LIST_LOAD_SUCCESS, listByPage, 0);
            }

            @Override
            public void onFailed(int code, String msg) {
                mHetView.Failed(MSG_TYPE_LIST_LOAD_FAIL, msg);
            }
        }, messageId, messageType, pageRows);
    }


    /**
     * 删除消息
     *
     * @param messageId 消息ID
     * @param postion   位置标识
     */
    public void deleteMessage(String messageId, int postion) {
        HetMessageApi.getInstance().deleteMessage(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (code != 0) {
                    mHetView.Failed(MSG_TYPE_LIST_DELETE_FAIL, msg);
                    return;
                }
                mHetView.success(MSG_TYPE_LIST_DELETE_SUCCESS, null, postion);
            }

            @Override
            public void onFailed(int code, String msg) {
                mHetView.Failed(MSG_TYPE_LIST_LOAD_FAIL, msg);
            }
        }, messageId);
    }

    /**
     * 标记已读
     *
     * @param messageId 消息ID
     */
    public void readMessage(String messageId) {
        HetMessageApi.getInstance().readMessage(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (code != 0) {
                    return;
                }
            }

            @Override
            public void onFailed(int code, String msg) {
            }
        }, messageId);
    }

    /**
     * 获取设备详情
     *
     * @param deviceId 设备标识
     */
    public void getBydeviceId(String deviceId) {
        HetMessageApi.getInstance().getBydeviceId(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (code != 0) {
                    RxManage.getInstance().post(HetMessageEvent.HET_EVENT_MSG_GET_DEVICE_DETAIL, msg);
                    return;
                }
                Type type = new TypeToken<DeviceDetailBean>() {
                }.getType();
                DeviceDetailBean deviceDetailBean = GsonUtil.getInstance().getGson().fromJson(msg, type);
                RxManage.getInstance().post(HetMessageEvent.HET_EVENT_MSG_GET_DEVICE_DETAIL, deviceDetailBean);
            }

            @Override
            public void onFailed(int code, String msg) {
                Logc.e("getBydeviceId e =" + msg);
                RxManage.getInstance().post(HetMessageEvent.HET_EVENT_MSG_GET_DEVICE_DETAIL, msg);
            }
        }, deviceId);
    }



}
