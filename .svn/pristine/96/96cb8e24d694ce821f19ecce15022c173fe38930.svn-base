/*
 * -----------------------------------------------------------------
 * Copyright (C) 2012-2013, by Het, ShenZhen, All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: NetworkUtils.java
 * Author: clark
 * Version: 1.0
 * Create: 2013-5-25
 *
 * Changes (from 2013-5-25)
 * -----------------------------------------------------------------
 * 2013-5-25 : 创建 NetworkUtils.java (clark);
 * -----------------------------------------------------------------
 */

package com.het.open.lib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;


/**
 * 判断网络连接
 */
public final class NetworkUtils {

//    /*
//     * 获取用户ID地址
//     */
//    public static String getLocalIpAddress() {
//        try {
//            String ipv4;
//            for (NetworkInterface ni : Collections.list(NetworkInterface
//                    .getNetworkInterfaces())) {
//                for (InetAddress address : Collections.list(ni
//                        .getInetAddresses())) {
//                    if (!address.isLoopbackAddress()
//                            && InetAddressUtils.isIPv4Address(ipv4 = address
//                            .getHostAddress())) {
//                        return ipv4;
//                    }
//                }
//            }
//        } catch (SocketException ex) {
//        }
//        return null;
//    }


    /****************************** 常量 ******************************/

    /**
     * 未知网络类型.
     */
    public static final String NETWORK_TYPE_UNKNOW = "未知网络";

    /**
     * 其他网络类型.
     */
    public static final String NETWORK_TYPE_OTHER = "其他网络";

    /**
     * Wi-Fi网络类型.
     */
    public static final String NETWORK_TYPE_WIFI = "Wi-Fi网络";

    /**
     * 电信2G网络.
     */
    public static final String NETWORK_TYPE_2G_TELECOM = "电信2G网络";

    /**
     * 移动2G网络.
     */
    public static final String NETWORK_TYPE_2G_MOBILE = "移动2G网络";

    /**
     * 联通2G网络.
     */
    public static final String NETWORK_TYPE_2G_UNICOM = "联通2G网络";

    /**
     * 电信3G网络.
     */
    public static final String NETWORK_TYPE_3G_TELECOM = "电信3G网络";

    /**
     * 联通3G网络
     */
    public static final String NETWORK_TYPE_3G_UNICOM = "联通3G网络";

    /**
     * 4G网络
     */
    public static final String NETWORK_TYPE_4G = "4G网络";

    /**
     * 私有的构造方法
     */
    private NetworkUtils() {
    }

    /**
     * 从上下文信息中获取ConnectivityManager.
     *
     * @param context 上下文信�?
     * @return 上下文信息中获取的ConnectivityManager
     */
    private static ConnectivityManager getConnectivityManager(
            final Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager;
    }

    /**
     * 从上下文信息中获取指定的NetworkInfo.
     *
     * @param context      上下文信�?
     * @param iNetworkType 网络类型
     * @return 上下文信息中获取到的NetworkInfo
     */
    private static NetworkInfo getNetworkInfo(final Context context,
                                              final int iNetworkType) {
        ConnectivityManager connectivityManager = NetworkUtils
                .getConnectivityManager(context);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getNetworkInfo(iNetworkType);
        }
        return networkInfo;
    }

    /**
     * 从上下文信息中获取活动的NetworkInfo.
     *
     * @param context
     * @return 上下文信息中获取到活动的NetworkInfo
     */
    private static NetworkInfo getActiveNetworkInfo(final Context context) {
        ConnectivityManager connectivityManager = NetworkUtils
                .getConnectivityManager(context);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo;
    }

    /**
     * 判断网络(移动网络或wifi)是否可用.
     *
     * @param context 上下文信使
     * @return ture表示网络可用，否则不可用
     */
    public static boolean isNetworkAvailable(final Context context) {
        if (context == null) {
            return false;
        }
        NetworkInfo networkInfo = NetworkUtils.getActiveNetworkInfo(context);
        if (networkInfo != null) {
            return networkInfo.isConnectedOrConnecting();
        }
        return false;
    }

    public static boolean isWiFiActive(Context inContext) {
        Context context = inContext.getApplicationContext();
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }

    /**
     * 判断移动网络是否可用.
     *
     * @param context
     * @return true表示移动网络可用，否则不可用
     */
    public static boolean isMobileAvailable(final Context context) {
        if (context == null) {
            return false;
        }
        NetworkInfo networkInfo = NetworkUtils.getNetworkInfo(context,
                ConnectivityManager.TYPE_MOBILE);
        if (networkInfo != null) {
            return networkInfo.isConnectedOrConnecting();
        }
        return false;
    }

    /**
     * 判断Wifi是否可用.
     *
     * @param context
     * @return true表示Wifi可用，否则不可用
     */
    public static boolean isWifiAvailable(final Context context) {
        if (context == null) {
            return false;
        }
        NetworkInfo networkInfo = NetworkUtils.getNetworkInfo(context,
                ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null) {
            return networkInfo.isConnectedOrConnecting();
        }
        return false;
    }

    /**
     * 获取当前网络类型.(wifi/移动/联通/电信/2G/3G/4G)
     *
     * @param context
     * @return 当前网络类型
     */
    public static String getNetworkType(final Context context) {
        if (context == null) {
            return NetworkUtils.NETWORK_TYPE_UNKNOW;
        }
        String strNetWorkType = NetworkUtils.NETWORK_TYPE_UNKNOW;
        ConnectivityManager connectivityManager = NetworkUtils
                .getConnectivityManager(context);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                switch (networkInfo.getType()) {
                    case ConnectivityManager.TYPE_WIFI:
                        strNetWorkType = NetworkUtils.NETWORK_TYPE_WIFI;
                        break;
                    case ConnectivityManager.TYPE_MOBILE:
                        switch (networkInfo.getSubtype()) {
                            case TelephonyManager.NETWORK_TYPE_GPRS:
                                strNetWorkType = NetworkUtils.NETWORK_TYPE_2G_UNICOM;
                                break;
                            case TelephonyManager.NETWORK_TYPE_EDGE:
                                strNetWorkType = NetworkUtils.NETWORK_TYPE_2G_MOBILE;
                                break;
                            case TelephonyManager.NETWORK_TYPE_CDMA:
                                strNetWorkType = NetworkUtils.NETWORK_TYPE_2G_TELECOM;
                                break;
                            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                                strNetWorkType = NetworkUtils.NETWORK_TYPE_3G_TELECOM;
                                break;
                            case TelephonyManager.NETWORK_TYPE_HSDPA:
                            case TelephonyManager.NETWORK_TYPE_UMTS:
                                strNetWorkType = NetworkUtils.NETWORK_TYPE_3G_UNICOM;
                                break;

                            //4G网络
                            case TelephonyManager.NETWORK_TYPE_LTE:
                                strNetWorkType = NETWORK_TYPE_4G;

                                break;
                            default:
                                strNetWorkType = NetworkUtils.NETWORK_TYPE_OTHER;
                                break;
                        }
                        break;
                    default:
                        strNetWorkType = NetworkUtils.NETWORK_TYPE_OTHER;
                        break;
                }
            }
        }
        return strNetWorkType;
    }
}
