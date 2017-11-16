package com.het.open.lib.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.Locale;
import java.util.UUID;

public class Utility {
    private static final String DEFAULT_CHARSET = "UTF-8";

    public static Bundle parseUrl(String url) {
        try {
            URL u = new URL(url);
            Bundle b = decodeUrl(u.getQuery());
            b.putAll(decodeUrl(u.getRef()));
            return b;
        } catch (MalformedURLException e) {
        }
        return new Bundle();
    }

    public static Bundle parseUri(String uri) {
        try {
            URI u = new URI(uri);
            Bundle b = decodeUrl(u.getQuery());
            return b;
        } catch (Exception e) {
        }
        return new Bundle();
    }

    public static Bundle decodeUrl(String s) {
        Bundle params = new Bundle();
        if (s != null) {
            String[] array = s.split("&");
            for (String parameter : array) {
                String[] v = parameter.split("=");
                try {
                    params.putString(URLDecoder.decode(v[0], DEFAULT_CHARSET),
                            URLDecoder.decode(v[1], DEFAULT_CHARSET));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return params;
    }

    public static boolean isChineseLocale(Context context) {
        try {
            Locale locale = context.getResources().getConfiguration().locale;
            if ((Locale.CHINA.equals(locale))
                    || (Locale.CHINESE.equals(locale))
                    || (Locale.SIMPLIFIED_CHINESE.equals(locale))
                    || (Locale.TAIWAN.equals(locale)))
                return true;
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    public static String generateGUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取包名的签名
     *
     * @param context
     * @param pkgName
     * @return
     */
    public static String getSign(Context context, String pkgName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName,
                    PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
            return null;
        }
        for (int j = 0; j < packageInfo.signatures.length; ++j) {
            byte[] str = packageInfo.signatures[j].toByteArray();
            if (str != null) {

                return hexdigest(str);
            }
        }
        return null;
    }

    public static String hexdigest(String string) {
        String s = null;
        try {
            s = hexdigest(string.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static String hexdigest(byte[] bytes) {
        String s = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            byte[] tmp = md.digest();
            char[] str = new char[32];
            int k = 0;
            for (int i = 0; i < 16; ++i) {
                byte byte0 = tmp[i];
                str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
                str[(k++)] = hexDigits[(byte0 & 0xF)];
            }
            s = new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

        private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static String safeString(String orignal) {
        return ((TextUtils.isEmpty(orignal)) ? "" : orignal);
    }

    public static String getAid(Context context, String appKey) {
        // AidTask task = AidTask.getInstance(context);
        // String cacheAid = task.loadAidFromCache();
        // if (!(TextUtils.isEmpty(cacheAid))) {
        // return cacheAid;
        // }
        // task.aidTaskInit(appKey);
        return "";
    }

    public static String generateUA(Context ctx) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(Build.MANUFACTURER).append("-").append(Build.MODEL);
        buffer.append("_");
        buffer.append(Build.VERSION.RELEASE);
        buffer.append("_");
        buffer.append("hetsdk");
        buffer.append("_");
        buffer.append("0030105000");
        buffer.append("_android");
        return buffer.toString();
    }
}
