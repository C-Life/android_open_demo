package com.het.open.lib.biz.constans;

import android.text.TextUtils;

import com.het.basic.AppDelegate;
import com.het.basic.constact.AppGlobalHost;
import com.het.basic.data.http.retrofit2.RetrofitManager;

/**
 * Created by UUXIA on 2015/7/29.
 */
public class GlobalAddr {


    //生产环境，正式环境，开放平台
    public final static String PRODUCE_HOST = "https://open.api.clife.cn";
    //预发布环境，开放平台
    public final static String PREVIEW_HOST = "https://test.open.api.clife.cn";

    //clife预发布地址
    public final static String CLIFE_PREVIEW_HOST = "https://test.api.clife.cn";

    //clife正式地址
    public final static String CLIFE_PRODUCE_HOST = "https://api.clife.cn";

    //外网，可以访问内部服务器
    public final static String INHOST = "https://dp.clife.net";
    //内网
    public final static String LOCAl_HOST = "https://200.200.200.50";

    public static String HOST;

    //修改密码 内网
    public final static String TEST_MODIFY_PWD_HOST = "https://200.200.200.50";
    //修改密码 预发布
    public final static String PREVIEW_MODIFY_PWD_HOST = "https://test.cms.clife.cn";
    //修改密码 正式环境
    public final static String RELEASW_MODIFY_PWD_HOST = "https://cms.clife.cn";

    /**
     * 返回clife域名
     *
     * @param url
     * @return
     */
    public static String getClifeUrl(String url) {
        if (GlobalAddr.HOST.equals(PREVIEW_HOST)) {
            return CLIFE_PREVIEW_HOST;
        } else if (GlobalAddr.HOST.equals(PRODUCE_HOST)) {
            return CLIFE_PRODUCE_HOST;
        } else {
            return LOCAl_HOST;
        }


    }

    /**
     * 设置为clife域名
     *
     * @return
     */
    public static void setClifeUrl() {
        if (!TextUtils.isEmpty(GlobalAddr.HOST)) {
            if (GlobalAddr.HOST.equals(PREVIEW_HOST)) {

                GlobalAddr.HOST = CLIFE_PREVIEW_HOST;
                AppDelegate.setHost(GlobalAddr.HOST);
                if(RetrofitManager.getBuilder() != null) {
                    RetrofitManager.getBuilder().baseUrl(AppGlobalHost.getHost()).build();
                }
            } else if (GlobalAddr.HOST.equals(PRODUCE_HOST)) {

                GlobalAddr.HOST = CLIFE_PRODUCE_HOST;
                AppDelegate.setHost(GlobalAddr.HOST);
                if(RetrofitManager.getBuilder() != null) {
                    RetrofitManager.getBuilder().baseUrl(AppGlobalHost.getHost()).build();
                }
            }

        }
    }

    /**
     * 设置为clife域名
     *
     * @return
     */
    public static void setOpenUrl() {
        if (!TextUtils.isEmpty(GlobalAddr.HOST)) {
            if (GlobalAddr.HOST.equals(CLIFE_PREVIEW_HOST)) {
                GlobalAddr.HOST = PREVIEW_HOST;
                AppDelegate.setHost(GlobalAddr.HOST);
                if(RetrofitManager.getBuilder() != null) {
                    RetrofitManager.getBuilder().baseUrl(AppGlobalHost.getHost()).build();
                }
            } else if (GlobalAddr.HOST.equals(CLIFE_PRODUCE_HOST)) {
                GlobalAddr.HOST = PRODUCE_HOST;
                AppDelegate.setHost(GlobalAddr.HOST);
                if(RetrofitManager.getBuilder() != null) {
                    RetrofitManager.getBuilder().baseUrl(AppGlobalHost.getHost()).build();
                }

            }

        }
    }

    /**
     * 返回开放平台域名
     *
     * @param url
     * @return
     */
    public static String getOpenUrl(String url) {
        if (GlobalAddr.HOST.equals(CLIFE_PREVIEW_HOST)) {
            return PREVIEW_HOST;
        } else if (GlobalAddr.HOST.equals(CLIFE_PRODUCE_HOST)) {
            return PRODUCE_HOST;
        } else {
            return LOCAl_HOST;
        }


    }

    /**
     * 返回开放平台域名
     *
     * @return
     */
    public static boolean isOpen() {
        if (GlobalAddr.HOST.contains("open")) {
            return true;
        } else {
            return false;
        }
    }


}
