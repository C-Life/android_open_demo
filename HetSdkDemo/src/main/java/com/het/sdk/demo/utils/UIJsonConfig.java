package com.het.sdk.demo.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.het.basic.utils.GsonUtil;
import com.het.basic.utils.StringUtils;
import com.het.sdk.demo.R;
import com.het.sdk.demo.model.UIconfigModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by liuzh on 2017-08-24.
 * app登录界面和SDK参数的配置类
 */

public class UIJsonConfig {

    // 配置文件名称
    public static final String fileName = "h5UIConfig.json";
    public static HashMap<String, Object> appInfoMap;
    private static Context mContext;
    private static UIJsonConfig mInstance;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static UIJsonConfig getInstance(Context context) {
        mContext = context;
        if (mInstance == null) {
            synchronized (UIJsonConfig.class) {
                if (mInstance == null)
                    mInstance = new UIJsonConfig(context.getApplicationContext());
            }
        }
        return mInstance;
    }

    private UIJsonConfig(Context context) {
        if (mInstance == null) {
            init(context);
        }
    }

    private void init(Context context) {
        getJson(fileName, context);
    }

    public String getJsonString(String fileName, Context context) {
        String json = getJson(fileName, context);
        if (StringUtils.isNull(json)) return null;
        Type type = new TypeToken<UIconfigModel>() {
        }.getType();
        UIconfigModel uIconfigModel = new Gson().fromJson(json, type);
        if (uIconfigModel == null) return null;
        return GsonUtil.getInstance().getGson().toJson(uIconfigModel, type);
    }

    public UIconfigModel getJsonObj(String fileName, Context context) {
        String json = getJson(fileName, context);
        if (StringUtils.isNull(json)) return null;
        Type type = new TypeToken<UIconfigModel>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    private String getJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            configMap(stringBuilder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @SuppressWarnings("rawtypes")
    private void configMap(StringBuilder stringBuilder) {
        appInfoMap = new HashMap<>();
        try {
            JSONObject root = new JSONObject(stringBuilder.toString());
            Iterator actions = root.keys();
            while (actions.hasNext()) {
                String param = actions.next().toString();
                Object value = root.get(param);
                appInfoMap.put(param, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /*
     *===========================================
     * 定义app-Key 用来获取配置数据
     *===========================================
     */
    /**
     * appId
     */
    private static final String App_ID_Key = "app_id";
    /**
     * App_Secret
     */
    private static final String App_Secret_Key = "app_secret";
    /**
     * 登录界面 顶部导航背景颜色
     */
    private static final String NavBackground_color = "navBackgroundColor";
    /**
     * 登录界面 顶部标题字体颜色
     */
    private static final String NavTitle_color = "navTitleColor";
    /**
     * QQ登录 腾讯开放平台申请的ID
     */
    private static final String Tencent_app_id = "tencent_app_id";

    /**
     * 微信登录 微信开放平台申请的ID
     */
    private static final String Wechat_app_id = "wechat_app_id";

    /**
     * 微信登录 微信开放平台申请的secret"
     */
    private static final String Wechat_app_secret = "wechat_app_secret";

    /**
     * 新浪登录 新浪开放平台申请的secret"
     */
    private static final String Sina_app_id = "sina_app_id";

    /**
     * 新浪登录 新浪开放平台申请的secret"
     */
    private static final String Sina_app_secret = "sina_app_secret";





    /*
     *===========================================
     * 获取app的配置参数,用来配置app参数
     *===========================================
     */

    /** **/
    public String getAppId() {
        return appInfoMap.get(App_ID_Key).toString();
    }

    public String getSecret() {
        return appInfoMap.get(App_Secret_Key).toString();
    }

    /**
     * 获取TencentAppID
     */
    public static String getTencentAppID() {
        return appInfoMap.get(Tencent_app_id).toString();
    }

    /**
     * 获取WechatAppID
     */
    public static String getWechatAppID() {
        return appInfoMap.get(Wechat_app_id).toString();
    }

    /**
     * 获取WeChatAppSecret
     */
    public static String getWechatAppSecret() {
        return appInfoMap.get(Wechat_app_secret).toString();
    }


    /**
     * 获取SinaAppID
     */
    public static String getSinaAppID() {
        return appInfoMap.get(Sina_app_id).toString();
    }

    /**
     * 获取SinaAppSecret
     */
    public static String getSinaAppSecret() {
        return appInfoMap.get(Sina_app_secret).toString();
    }

    public Drawable setNavBackground_color() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
//        drawable.setCornerRadius(100);

        String ButtonColor_FromMap = appInfoMap.get(NavBackground_color).toString();
        if (!TextUtils.isEmpty(ButtonColor_FromMap)) {
            drawable.setColor(Color.parseColor("#" + ButtonColor_FromMap));
        } else {
            drawable.setColor(ContextCompat.getColor(mContext,R.color.tab_text_color_press));
        }
        return drawable;
    }

    public int setNavBackground_color_string() {
        String ButtonColor_FromMap = appInfoMap.get(NavBackground_color).toString();
        if (!TextUtils.isEmpty(ButtonColor_FromMap)) {
            return Color.parseColor("#" + ButtonColor_FromMap);
        } else {
            return ContextCompat.getColor(mContext,R.color.tab_text_color_press);
        }
    }

    /**
     * 设置导航栏文字颜色
     *
     * @return
     */
    public int setNavigationBarTextColor() {
        int navigationBarTextColor = ContextCompat.getColor(mContext,R.color.black);
        String NavigationBarTextColor_FromMap = appInfoMap.get(NavTitle_color).toString();
        if (!TextUtils.isEmpty(NavigationBarTextColor_FromMap)) {
            navigationBarTextColor = Color.parseColor("#" + NavigationBarTextColor_FromMap);
        }
        return navigationBarTextColor;
    }

}
