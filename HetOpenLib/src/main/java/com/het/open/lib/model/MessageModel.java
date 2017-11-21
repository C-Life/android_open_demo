package com.het.open.lib.model;

/**
 * 消息model
 * Created by xuchao on 2016/6/1.
 */
public class MessageModel {
    private int messageId;  //消息标识
    private String title;  //标题
    private String description;  //描述
    private String businessParam;  //业务参数的值(系统推送消息对应消息详情URL(businessParam为空时不要跳转)；添加好友消息对应用户Id，控制设备消息对应设备ID，查看帖子评论对应帖子详情URL。）
    private int sender;//发送者ID
    private String icon;  //图标URL
    private int messageType;//消息类型：0-系统消息；1-添加好友；2-邀请控制设备；3-查看帖子评论；其他后续补充
    private int createTime;//时间戳
    private int status;//消息状态(0-删除；1-未处理；2-已处理)

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBusinessParam() {
        return businessParam;
    }

    public void setBusinessParam(String businessParam) {
        this.businessParam = businessParam;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "messageId=" + messageId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", businessParam='" + businessParam + '\'' +
                ", sender=" + sender +
                ", icon='" + icon + '\'' +
                ", messageType=" + messageType +
                ", createTime=" + createTime +
                ", status=" + status +
                '}';
    }
}
