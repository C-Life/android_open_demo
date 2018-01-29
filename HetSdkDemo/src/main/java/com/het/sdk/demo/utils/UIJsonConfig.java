package com.het.sdk.demo.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.het.sdk.demo.R;

import java.io.Serializable;

/**
 * Created by liuzh on 2017-08-24.
 * app登录界面和SDK参数的配置类
 */

public class UIJsonConfig implements Serializable {

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
    }

    private String appId;
    private String appSecret;
    private String navBackgroundColor;
    private String navTitleColor;
    private boolean logoshow;
    private String loginBtnBackgroundColor;
    private String loginBtnBorderColor;
    private String loginBtnFontColor;
    private boolean weiboLogin;
    private boolean weixinLogin;
    private boolean qqLogin;
    private String tencentAppId;
    private String wechatAppId;
    private String wechatAppSecret;
    private String sinaAppId;
    private String sinaAppSecret;
    private String loginType;

    public String getNavBackgroundColor() {
        return navBackgroundColor;
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getLoginBtnBackgroundColor() {
        return loginBtnBackgroundColor;
    }

    public void setLoginBtnBackgroundColor(String loginBtnBackgroundColor) {
        this.loginBtnBackgroundColor = loginBtnBackgroundColor;
    }

    public String getLoginBtnBorderColor() {
        return loginBtnBorderColor;
    }

    public void setLoginBtnBorderColor(String loginBtnBorderColor) {
        this.loginBtnBorderColor = loginBtnBorderColor;
    }

    public String getLoginBtnFontColor() {
        return loginBtnFontColor;
    }

    public void setLoginBtnFontColor(String loginBtnFontColor) {
        this.loginBtnFontColor = loginBtnFontColor;
    }

    public boolean isLogoshow() {
        return logoshow;
    }

    public void setLogoshow(boolean logoshow) {
        this.logoshow = logoshow;
    }

    public boolean isQQLogin() {
        return qqLogin;
    }

    public void setQQLogin(boolean qqLogin) {
        this.qqLogin = qqLogin;
    }

    public boolean isWeiboLogin() {
        return weiboLogin;
    }

    public void setWeiboLogin(boolean weiboLogin) {
        this.weiboLogin = weiboLogin;
    }

    public boolean isWeixinLogin() {
        return weixinLogin;
    }

    public void setWeixinLogin(boolean weixinLogin) {
        this.weixinLogin = weixinLogin;
    }

    public String getSinaAppId() {
        return sinaAppId;
    }

    public void setSinaAppId(String sinaAppId) {
        this.sinaAppId = sinaAppId;
    }

    public String getSinaAppSecret() {
        return sinaAppSecret;
    }

    public void setSinaAppSecret(String sinaAppSecret) {
        this.sinaAppSecret = sinaAppSecret;
    }

    public String getWechatAppId() {
        return wechatAppId;
    }

    public void setWechatAppId(String wechatAppId) {
        this.wechatAppId = wechatAppId;
    }

    public String getTencentAppId() {
        return tencentAppId;
    }

    public void setTencentAppId(String tencentAppId) {
        this.tencentAppId = tencentAppId;
    }

    public String getWechatAppSecret() {
        return wechatAppSecret;
    }

    public void setWechatAppSecret(String wechatAppSecret) {
        this.wechatAppSecret = wechatAppSecret;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }

    public Drawable setNavBackground_color() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        String ButtonColor_FromMap = navBackgroundColor;
        if (!TextUtils.isEmpty(ButtonColor_FromMap)) {
            drawable.setColor(Color.parseColor("#" + ButtonColor_FromMap));
        } else {
            drawable.setColor(ContextCompat.getColor(mContext, R.color.tab_text_color_press));
        }
        return drawable;
    }


    public void setNavBackgroundColor(String navBackgroundColor) {
        this.navBackgroundColor = navBackgroundColor;
    }

    public int setNavBackground_color_string() {
        if (!TextUtils.isEmpty(navBackgroundColor)) {
            return Color.parseColor("#" + navBackgroundColor);
        } else {
            return ContextCompat.getColor(mContext, R.color.tab_text_color_press);
        }
    }


    public void setNavTitleColor(String navTitleColor) {
        this.navTitleColor = navTitleColor;
    }

    /**
     * 设置导航栏文字颜色
     *
     * @return
     */
    public int setNavigationBarTextColor() {
        int navigationBarTextColor = ContextCompat.getColor(mContext, R.color.black);
        String NavigationBarTextColor_FromMap = navTitleColor;
        if (!TextUtils.isEmpty(NavigationBarTextColor_FromMap)) {
            navigationBarTextColor = Color.parseColor("#" + NavigationBarTextColor_FromMap);
        }
        return navigationBarTextColor;
    }


    //配置文件
    public static final class ConfigUiBuilder {
        private String appId;
        private String appSecret;
        private String navBackgroundColor;
        private String navTitleColor;
        private boolean logoshow;
        private String loginBtnBackgroundColor;
        private String loginBtnBorderColor;
        private String loginBtnFontColor;
        private boolean weiboLogin;
        private boolean weixinLogin;
        private boolean qqLogin;
        private String tencentAppId;
        private String wechatAppId;
        private String wechatAppSecret;
        private String sinaAppId;
        private String sinaAppSecret;
        private String loginType;
        private Context context;

        public ConfigUiBuilder(Context context) {
            this.context = context;
        }

        public ConfigUiBuilder(Context context, String appId, String appSecret) {
            this.context = context;
            this.appId = appId;
            this.appSecret = appSecret;
        }

        public ConfigUiBuilder setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public ConfigUiBuilder setAppSecret(String appSecret) {
            this.appSecret = appSecret;
            return this;
        }

        public ConfigUiBuilder setNavBackgroundColor(String navBackgroundColor) {
            this.navBackgroundColor = navBackgroundColor;
            return this;
        }

        public ConfigUiBuilder setNavTitleColor(String navTitleColor) {
            this.navTitleColor = navTitleColor;
            return this;
        }

        public ConfigUiBuilder setLoginBtnBackgroundColor(String loginBtnBackgroundColor) {
            this.loginBtnBackgroundColor = loginBtnBackgroundColor;
            return this;
        }

        public ConfigUiBuilder setLoginBtnBorderColor(String loginBtnBorderColor) {
            this.loginBtnBorderColor = loginBtnBorderColor;
            return this;
        }

        public ConfigUiBuilder setLoginBtnFontColor(String loginBtnFontColor) {
            this.loginBtnFontColor = loginBtnFontColor;
            return this;
        }

        public ConfigUiBuilder setLoginType(String loginType) {
            this.loginType = loginType;
            return this;
        }

        public ConfigUiBuilder setLogoshow(boolean logoshow) {
            this.logoshow = logoshow;
            return this;
        }

        public ConfigUiBuilder setQQLogin(boolean qqLogin) {
            this.qqLogin = qqLogin;
            return this;
        }

        public ConfigUiBuilder setSinaAppId(String sinaAppId) {
            this.sinaAppId = sinaAppId;
            return this;
        }

        public ConfigUiBuilder setSinaAppSecret(String sinaAppSecret) {
            this.sinaAppSecret = sinaAppSecret;
            return this;
        }

        public ConfigUiBuilder setTencentAppId(String tencentAppId) {
            this.tencentAppId = tencentAppId;
            return this;
        }

        public ConfigUiBuilder setWechatAppId(String wechatAppId) {
            this.wechatAppId = wechatAppId;
            return this;
        }

        public ConfigUiBuilder setWechatAppSecret(String wechatAppSecret) {
            this.wechatAppSecret = wechatAppSecret;
            return this;
        }

        public ConfigUiBuilder setWeiboLogin(boolean weiboLogin) {
            this.weiboLogin = weiboLogin;
            return this;
        }

        public ConfigUiBuilder setWeixinLogin(boolean weixinLogin) {
            this.weixinLogin = weixinLogin;
            return this;
        }

        public UIJsonConfig build() {
            UIJsonConfig uiJsonConfig = UIJsonConfig.getInstance(context);
            uiJsonConfig.setAppId(appId);
            uiJsonConfig.setAppSecret(appSecret);
            uiJsonConfig.setNavBackgroundColor(navBackgroundColor);
            uiJsonConfig.setNavTitleColor(navTitleColor);
            uiJsonConfig.setLogoshow(logoshow);
            uiJsonConfig.setLoginBtnBackgroundColor(loginBtnBackgroundColor);
            uiJsonConfig.setLoginBtnBorderColor(loginBtnBorderColor);
            uiJsonConfig.setLoginBtnFontColor(loginBtnFontColor);
            uiJsonConfig.setWeiboLogin(weiboLogin);
            uiJsonConfig.setWeixinLogin(weixinLogin);
            uiJsonConfig.setQQLogin(qqLogin);
            uiJsonConfig.setTencentAppId(tencentAppId);
            uiJsonConfig.setWechatAppId(wechatAppId);
            uiJsonConfig.setWechatAppSecret(wechatAppSecret);
            uiJsonConfig.setSinaAppId(sinaAppId);
            uiJsonConfig.setSinaAppSecret(sinaAppSecret);
            uiJsonConfig.setLoginType(loginType);
            return uiJsonConfig;
        }
    }

}
