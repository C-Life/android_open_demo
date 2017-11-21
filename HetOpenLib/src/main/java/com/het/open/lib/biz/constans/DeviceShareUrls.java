package com.het.open.lib.biz.constans;


/**
 * Created by weatherfish on 2015/3/10.
 */
public final class DeviceShareUrls {
    private static final String SHARE = "/v1/device/auth/";
    public static class Share {
        public final static String Invite =SHARE + "invite";
        public final static String Agree =SHARE + "agree";
        public final static String Del =SHARE + "del";
        public final static String getDeviceAuthUser =SHARE + "getDeviceAuthUser";
        public final static String getDeviceNotAuthUser =SHARE + "getDeviceNotAuthUser";
        public final static String getAuthFriendDevice =SHARE + "getAuthFriendDevice";
        public final static String getNotAuthFriendDevice =SHARE + "getNotAuthFriendDevice";
        public final static String getAuthDevice =SHARE + "getAuthDevice";
    }

}
