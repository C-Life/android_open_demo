package com.het.sdk.demo;

import android.app.ActivityManager;
import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.broadcom.cooee.CooeeModuleImpl;
import com.espressif.iot.esptouch.EsptouchModuleV4Impl;
import com.ezconnect.marvell.MarvellV1WiFiImpl;
import com.ezconnect.marvell.MarvellV2WiFiImpl;
import com.het.RealtekModuleImpl;
import com.het.ap.HeTApModuleImpl;
import com.het.basic.utils.GsonUtil;
import com.het.basic.utils.ToastUtil;
import com.het.bind.logic.api.bind.ModuleManager;
import com.het.open.lib.api.HetCodeConstants;
import com.het.open.lib.api.HetSdk;
import com.het.open.lib.biz.thirdlogin.HetSdkThirdDelegate;
import com.het.open.lib.biz.thirdlogin.HetSdkThirdDelegateBuilder;
import com.het.open.lib.model.ConfigModel;
import com.het.sdk.demo.utils.UIJsonConfig;
import com.het.smartlink.HeTSmartlinkImpl;
import com.het.xlw.XlwModuleImpl;
import com.mediatek.elian.ElianModuleImpl;
import com.sctech.cfe.SctechModuleImpl;

import java.util.List;

public class AppApplication extends MultiDexApplication {

    private HetSdkThirdDelegate mLoginDelegate;
    private String mSinaRedirectURL = "http://www.clife.cn";
    private static AppApplication instance;
    private UIJsonConfig uiJsonConfig;

    public static AppApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        //获得进程列表
        List<ActivityManager.RunningAppProcessInfo> processList = activityManager.getRunningAppProcesses();
        //获得当前pid
        int pid = android.os.Process.myPid();
        //判断当前pid属于哪个进程
        for (ActivityManager.RunningAppProcessInfo process : processList) {
            if (pid == process.pid) {
                if (process.processName.equals(getPackageName())) {
                    initHetSdk();
                    registerModule();
                }
            }
        }
    }

    /**
     * 初始化和而泰sdk  配置登录界面样式
     */
    private void initHetSdk() {
        initH5LoginStyle();
        String AppID = UIJsonConfig.getInstance(this).getAppId();
        String AppSecret = UIJsonConfig.getInstance(this).getAppSecret();
        if (TextUtils.isEmpty(AppID) || TextUtils.isEmpty(AppSecret)) {
            ToastUtil.showToast(this, this.getString(R.string.appID_Toast));
        }
        // 启动SDK
        configApplication(AppID, AppSecret);
    }

    /**
     * 配置登录界面样式
     */
    private void initH5LoginStyle() {
        uiJsonConfig = new UIJsonConfig.ConfigUiBuilder(this, "30765", "5f699a78c319444cb8a291296049572c")// appId 、app_secret
                .setNavBackgroundColor("FF3285FF")//标题栏颜色
                .setNavTitleColor("FFFFFFFF")//标题栏文字颜色
                .setLogoshow(true)//登录页面是否显示LOGO
                .setLoginBtnBackgroundColor("FFFFFFFF")//登录页面登录按钮的背景色
                .setLoginBtnBorderColor("FF5BA532")//登录页面登录按钮的边框色
                .setLoginBtnFontColor("FF000000")//登录页面登录按钮的文字颜色
                .setWeiboLogin(true)//是否支持微博登录
                .setWeixinLogin(true)//是否支持微信登录
                .setQQLogin(true)//是否支持QQ登录
                .setTencentAppId("1106346235")//腾讯开放平台 appId
                .setWechatAppId("wx49a17f4b9643f2e0")//微信开放平台 appId
                .setWechatAppSecret("121b5279ba5f3b009f968c987ec2ac1c")//微信开放平台 app_secret
                .setSinaAppId("1919114603")//新浪开放平台 appId
                .setSinaAppSecret("981961acd338382be8e7ef5f04e5b17f")//新浪开放平台 app_secret
                .setLoginType("1")//登录模板选择 1-模板一   2-模板二  3-模板三
                .build();
    }

    /**
     * 配置SKD
     *
     * @param appId
     * @param appSecret
     */
    private void configApplication(String appId, String appSecret) {
        ConfigModel configModel = new ConfigModel();
        configModel.setLog(true); //是否开启log信息
        configModel.setHost(HetCodeConstants.TYPE_PRODUCE_HOST); //TYPE_PRODUCE_HOST HetCodeConstants.TYPE_LOCAL_HOST
        configModel.setH5UIconfig(GsonUtil.getInstance().toJson(uiJsonConfig));
        //配置第三方登录
        mLoginDelegate = new HetSdkThirdDelegateBuilder(this)
                .registerQQ(UIJsonConfig.getInstance(this).getTencentAppId())
                .registerWeixin(UIJsonConfig.getInstance(this).getWechatAppId(), UIJsonConfig.getInstance(this).getWechatAppSecret())
                .registerSinaWeibo(UIJsonConfig.getInstance(this).getSinaAppId(), UIJsonConfig.getInstance(this).getSinaAppSecret(), this.mSinaRedirectURL)
                .create();
        HetSdk.getInstance().init(this, appId, appSecret, configModel);
    }

    /**
     * 模组注册
     */
    private void registerModule() {
        try {
            ModuleManager.getInstance().registerModule(HeTApModuleImpl.class, getApplicationContext());//和而泰AP绑定
            ModuleManager.getInstance().registerModule(HeTSmartlinkImpl.class, getApplicationContext());//和而泰smartlink绑定
            ModuleManager.getInstance().registerModule(RealtekModuleImpl.class, getApplicationContext());//科中龙(realtekmodule)
            ModuleManager.getInstance().registerModule(XlwModuleImpl.class, getApplicationContext());//新力维_NL6621底层库
            ModuleManager.getInstance().registerModule(SctechModuleImpl.class, getApplicationContext());//双驰达(sctechmodule)
            ModuleManager.getInstance().registerModule(ElianModuleImpl.class, getApplicationContext());//信驰达_MTK7681底层库
            ModuleManager.getInstance().registerModule(MarvellV1WiFiImpl.class, getApplicationContext());//Marvell(marvellmodule)
            ModuleManager.getInstance().registerModule(MarvellV2WiFiImpl.class, getApplicationContext());//Marvell(marvellmodule)
            ModuleManager.getInstance().registerModule(EsptouchModuleV4Impl.class, getApplicationContext());//乐鑫信息科技(esptouchmodule)
            ModuleManager.getInstance().registerModule(CooeeModuleImpl.class, getApplicationContext());//博通(cooeemodule)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        HetSdk.getInstance().destroy();
        if (mLoginDelegate != null) {
            mLoginDelegate.releaseResource();
        }
    }


}
