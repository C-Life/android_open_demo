package com.het.sdk.demo.base;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.het.basic.base.RxManage;
import com.het.basic.utils.GsonUtil;
import com.het.basic.utils.NetworkUtil;
import com.het.basic.utils.permissions.RxPermissions;
import com.het.h5.sdk.callback.IAppJavaScriptsInterface;
import com.het.h5.sdk.callback.IH5CallBack;
import com.het.h5.sdk.callback.IMethodCallBack;
import com.het.h5.sdk.manager.HetH5SdkManager;
import com.het.open.lib.api.HetDeviceControlDelegate;
import com.het.open.lib.callback.ICtrlCallback;
import com.het.open.lib.manager.base.BaseHtmlFiveFactory;
import com.het.open.lib.manager.h5.AppJavaBridgeManager;
import com.het.open.lib.model.DeviceModel;
import com.het.sdk.demo.R;
import com.het.sdk.demo.ble.BLEBean;
import com.het.sdk.demo.ble.BLEManager;
import com.het.sdk.demo.event.DeviceStatusEvent;
import com.het.sdk.demo.impl.OnUpdateInViewImpl;
import com.het.sdk.demo.manager.BuildManager;
import com.het.sdk.demo.model.PushToH5Model;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.Bind;

/**
 * 设备H5控制  旧设备H5控制
 */
public abstract class BaseHetH5Activity extends BaseHetActivity implements IAppJavaScriptsInterface {

    @Bind(R.id.rl_h5_container)
    RelativeLayout rlH5Container;

    WebView mWebView;
    protected BaseHtmlFiveFactory mHtmlFiveManager;
    protected HetDeviceControlDelegate deviceControlDelegate;
    protected DeviceModel deviceModel;
    private PushToH5Model pushToH5Model = new PushToH5Model();

    private BLEManager mBleManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_het_h5;
    }

    @Override
    protected void initData() {
        deviceModel = (DeviceModel) getIntent().getSerializableExtra("DeviceModel");
        mHtmlFiveManager = AppJavaBridgeManager.getAppJavaBridgeManager().getBridgerById(deviceModel.getProductId(), mContext).build(this, mWebView, this);

        initH5Page();
        initDelegate();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mWebView = new WebView(getApplicationContext());
        mWebView.setFocusable(true);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rlH5Container.addView(mWebView, layoutParams);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                hideDialog();
            }

            @Override
            public void onReceivedSslError(WebView var1, SslErrorHandler sslErrorHandler, SslError var3) {
                sslErrorHandler.proceed();//证书忽略
            }
        });
        IX5WebViewExtension x5WebViewExtension = mWebView.getX5WebViewExtension();
        if (x5WebViewExtension != null) {
            x5WebViewExtension.setScrollBarFadingEnabled(false);//隐藏X5快速滑动时的滚动条
        }
    }

    /**
     * 初始化H5页面
     */
    protected void initH5Page() {
    }

    /**
     * 初始化代理
     */
    protected void initDelegate() {
        if (deviceModel.getModuleType() != 2) {
            //非蓝牙设备
            deviceControlDelegate = new HetDeviceControlDelegate();
            deviceControlDelegate.onCreate(mContext, deviceModel);
            OnUpdateInViewImpl onUpdateInView = new OnUpdateInViewImpl(mHtmlFiveManager);
            deviceControlDelegate.setOnUpdateInView(onUpdateInView);
        }
    }

    /**
     * 初始化状态
     */
    private void initJson() {
        RxManage.getInstance().register(DeviceStatusEvent.DEVICE_STATUS, o -> {
            //设备离线在线事件监听
            pushToH5Model.setOnline((int) o);
            pushToH5Model.setNetworkavailable(NetworkUtil.isNetworkAvailable(this) ? 1 : 2);
            if (mHtmlFiveManager != null) {
                mHtmlFiveManager.updateRunData(GsonUtil.getInstance().toJson(pushToH5Model));
            }
        });
        checkOnline();
    }

    /**
     * 检查离线在线
     */
    private void checkOnline() {
        int status = deviceModel.getOnlineStatus() == 2 ? 2 : 1;//1设备在线  2设备离线
        //设备状态
        pushToH5Model.setOnline(status);
        //网络状态
        pushToH5Model.setNetworkavailable(NetworkUtil.isNetworkAvailable(this) ? 1 : 2);//1 网络可用   2网络不可用

        //设备信息
        pushToH5Model.setDeviceName(deviceModel.getDeviceName());
        pushToH5Model.setDeviceId(deviceModel.getDeviceId());
        pushToH5Model.setProductId(deviceModel.getProductId());
        if (mHtmlFiveManager != null) {
            //自定义内容
            mHtmlFiveManager.updateRunData(GsonUtil.getInstance().toJson(pushToH5Model));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (deviceModel.getModuleType() != 2) {
            if (deviceControlDelegate != null) {
                deviceControlDelegate.onResume();
            }
        } else {
            initBleManager().refreshAty(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mBleManager != null)
            mBleManager.stopScan();
        if (deviceModel.getModuleType() != 2) {
            if (deviceControlDelegate != null) {
                deviceControlDelegate.onPause();
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mWebView != null) {
            HetH5SdkManager.getInstance().clearCache(this);
            mWebView.removeJavascriptInterface("bindJavaScript");
            mWebView.destroy();
            mWebView = null;
        }
        if (deviceControlDelegate != null) {
            deviceControlDelegate.onDestroy();
        } else {
            mBleManager.disconnect();
            mBleManager.stopConnDevice();
            mBleManager.clear();
        }

        RxManage.getInstance().unregister(DeviceStatusEvent.DEVICE_STATUS);
        super.onDestroy();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 页面跳转
     */
    protected void toBrowser(String s) {
        Uri uri = Uri.parse(s);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    /**
     * 拨打电话
     *
     * @param phone 电话号码
     */
    protected void toServiceCall(String phone) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    @Override
    public void send(String s, IMethodCallBack iMethodCallBack) {
        if (deviceModel.getModuleType() == 2) {
            mBleManager.setConfigData(s, iMethodCallBack);
            return;
        }
        if (deviceControlDelegate == null) {
            return;
        }
        deviceControlDelegate.send(s, new ICtrlCallback() {
            @Override
            public void onSucess() {
                iMethodCallBack.onSucess(0, 0);
            }

            @Override
            public void onFailed(Throwable throwable) {
                iMethodCallBack.onFailed(-1, -1);
            }

            @Override
            public void onProtocolError(Throwable throwable) {
                iMethodCallBack.onFailed(-1, -1);
            }
        });
    }

    @Override
    public String getModeJson() {
        return null;
    }

    @Override
    public void onWebViewCreate() {
        //开始监听上报的数据
        initJson();
        if (deviceControlDelegate != null) {
            deviceControlDelegate.onStart();
        }

        if (deviceModel.getModuleType() == 2) {
            if (mHtmlFiveManager != null) {
                BLEBean bleBean = new BLEBean();
                mHtmlFiveManager.updateConfigData(GsonUtil.getInstance().toJson(bleBean));
            }
            getPermission();
        }
    }

    protected BLEManager initBleManager() {
        if (mBleManager == null) {
            mBleManager = BLEManager.getInstance();
            mBleManager.init(this);
        }
        return mBleManager;
    }

    /**
     * 获取权限
     */
    private void getPermission() {
        initBleManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RxPermissions.getInstance(this)
                    .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                    .subscribe(grant -> {
                        if (!grant) {
                            BaseHetH5Activity.this.finish();
                        } else {
                            startBle();
                        }
                    });
        } else {
            startBle();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (BuildManager.isSDKLater18()) {
                BLEManager.getInstance(this).connDeviceByMac(deviceModel.getMacAddress(), mHtmlFiveManager);
            }
        }
    }

    private void startBle() {
        if (mBleManager.isBlueToothEnable(this)) {
            mBleManager.connDeviceByMac(deviceModel.getMacAddress(), mHtmlFiveManager);
        } else {
            mBleManager.enableBlueTooth(this, 1);
        }
    }

    @Override
    public void tips(String msg) {
        super.tips(msg);
    }


    @Override
    public void setTitle(String s) {

    }

    @Override
    public void onLoadH5Failed(int i, String s) {

    }

    @Override
    public void h5SendDataToNative(int i, String s, String s1, IH5CallBack ih5CallBack) {

    }

    @Override
    public void h5GetDataFromNative(int i, String s, IH5CallBack ih5CallBack) {

    }
}
