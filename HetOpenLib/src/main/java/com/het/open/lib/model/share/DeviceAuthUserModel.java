package com.het.open.lib.model.share;

/**
 * 设备授权的用户model
 * Created by xuchao on 2016/6/1.
 */
public class DeviceAuthUserModel {

    private String userId;   //用户id
    private String userName; //用户名称
    private String avatar;  //头像
    private String authTime; //授权时间

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAuthTime() {
        return authTime;
    }

    public void setAuthTime(String authTime) {
        this.authTime = authTime;
    }

    @Override
    public String toString() {
        return "DeviceAuthUserModel{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", authTime='" + authTime + '\'' +
                '}';
    }
}
