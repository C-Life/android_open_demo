package com.het.open.lib.auth;

/**
 * Created by xuchao on 2016/3/11.
 */
public class HetAuthMess {

    private static HetAuthMess mInstance;
    private String appId;   //用户id
    private String appKey;  //用户key

//    public String getRedirectUrl() {
//        return redirectUrl;
//    }
//
//    public void setRedirectUrl(String redirectUrl) {
//        this.redirectUrl = redirectUrl;
//    }
//
//    private String redirectUrl;//

    private HetAuthMess(){
    }

    public static HetAuthMess getInstance(){
        if (mInstance==null){
            synchronized (HetAuthMess.class){
                mInstance=new HetAuthMess();
            }
        }
        return  mInstance;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    @Override
    public String toString() {
        return "HetAuthMess{" +
                "appId='" + appId + '\'' +
                ", appKey='" + appKey + '\'' +
                '}';
    }
}
