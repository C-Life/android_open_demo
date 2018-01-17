package com.het.sdk.demo;

import android.app.ActivityManager;
import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.broadcom.cooee.CooeeModuleImpl;
import com.espressif.iot.esptouch.EsptouchModuleImpl;
import com.ezconnect.marvell.MarvellV1WiFiImpl;
import com.ezconnect.marvell.MarvellV2WiFiImpl;
import com.het.RealtekModuleImpl;
import com.het.ap.HeTApModuleImpl;
import com.het.basic.utils.ToastUtil;
import com.het.bind.logic.api.bind.ModuleManager;
import com.het.bluetoothbase.ViseBluetooth;
import com.het.open.lib.api.HetCodeConstants;
import com.het.open.lib.api.HetSdk;
import com.het.open.lib.biz.thirdlogin.HetSdkThirdDelegate;
import com.het.open.lib.biz.thirdlogin.HetSdkThirdDelegateBuilder;
import com.het.open.lib.model.ConfigModel;
import com.het.sdk.demo.utils.Constants;
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
     * 初始化和而泰sdk
     * 通过h5UIConfig.json 获取的配置来配置sdk的参数
     * 通过h5UIConfig.json 没有配置的在Constants 类设置默认的
     */
    private void initHetSdk() {
        String AppID = UIJsonConfig.getInstance(this).getAppId();
        String AppSecret = UIJsonConfig.getInstance(this).getSecret();
        if (TextUtils.isEmpty(AppID) || AppID.contains("your_app_id") || TextUtils.isEmpty(AppSecret)
                || AppSecret.contains("your_app_secret")) {
            ToastUtil.showToast(this, this.getString(R.string.appID_Toast));
            configApplication(Constants.APP_ID, Constants.APP_SECRET);
        } else {
            // 启动SDK
            configApplication(AppID, AppSecret);
        }
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
       // configModel.setSupportDevicePush(false);
        configModel.setHost(HetCodeConstants.TYPE_PRODUCE_HOST); //TYPE_PRODUCE_HOST HetCodeConstants.TYPE_LOCAL_HOST

        configModel.setH5UIconfig(UIJsonConfig.getInstance(this).getJsonString(UIJsonConfig.fileName, this));
        //配置第三方登录
        mLoginDelegate = new HetSdkThirdDelegateBuilder(this)
                .registerQQ(UIJsonConfig.getTencentAppID())
                .registerWeixin(UIJsonConfig.getWechatAppID(), UIJsonConfig.getWechatAppSecret())
                .registerSinaWeibo(UIJsonConfig.getSinaAppID(), UIJsonConfig.getSinaAppSecret(), this.mSinaRedirectURL)
                .create();
        HetSdk.getInstance().init(this, appId, appSecret, configModel);

        ViseBluetooth.getInstance().init(this);
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
            ModuleManager.getInstance().registerModule(EsptouchModuleImpl.class, getApplicationContext());//乐鑫信息科技(esptouchmodule)
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
