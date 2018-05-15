package com.het.sdk.demo.ui.activity.h5control;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.het.basic.base.RxManage;
import com.het.basic.model.DeviceBean;
import com.het.h5.sdk.base.H5CommonBaseControlActivity;
import com.het.h5.sdk.callback.IMethodCallBack;
import com.het.h5.sdk.utils.H5VersionUtil;
import com.het.log.Logc;
import com.het.open.lib.api.HetWifiDeviceControlApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.callback.IWifiDeviceData;
import com.het.sdk.demo.ui.activity.device.DeviceDetailActivity;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * wifi设备h5控制界面容器
 */

public class H5ComWifiControlActivity extends H5CommonBaseControlActivity {
    private final String TAG = H5ComWifiControlActivity.class.getSimpleName();

    public static void startH5ComWifiControlActivity(Context context, DeviceBean deviceBean) {
        Intent intent = new Intent(context, H5ComWifiControlActivity.class);
        intent.putExtra(H5VersionUtil.DEVICE_BEAN, deviceBean);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mWebView.setWebViewClient(new WebViewClient() {
            //是否在webview内加载页面
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                } else {
                    view.loadUrl(request.toString());
                }
                return true;
            }

            @Override
            public void onReceivedSslError(WebView var1, SslErrorHandler sslErrorHandler, SslError var3) {
                sslErrorHandler.proceed();//证书忽略
            }
        });
        RxManage.getInstance().register("Qr_device_url", url -> {
            this.h5BridgeManager.loadUrl((String) url);
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (deviceBean != null) {
            HetWifiDeviceControlApi.getInstance().stop(deviceBean.getDeviceId());
        }
        RxManage.getInstance().unregister("Qr_device_url");

    }

    @Override
    protected void initControlData() {
        HetWifiDeviceControlApi.getInstance().start(deviceBean.getDeviceId(), iWifiDeviceData);
    }

    private IWifiDeviceData iWifiDeviceData = new IWifiDeviceData() {
        @Override
        public void onGetConfigData(String jsonData) {
            Logc.d("onGetConfigData: ", jsonData);
            if (h5BridgeManager != null) {
                h5BridgeManager.updateConfigData(jsonData);
            }
        }

        @Override
        public void onGetRunData(String jsonData) {
            Logc.d("onGetRunData: ", jsonData);
            if (h5BridgeManager != null) {
                h5BridgeManager.updateRunData(jsonData);
            }
        }

        @Override
        public void onGetErrorData(String jsonData) {
            Logc.d("onGetErrorData: " + jsonData);
            if (h5BridgeManager != null) {
                h5BridgeManager.updateConfigData(jsonData);
            }
        }

        @Override
        public void onDeviceStatues(int onlineStatus) {
            if (h5BridgeManager != null) {
                h5BridgeManager.updateDeviceState(onlineStatus);
            }
        }

        @Override
        public void onDataError(int code, String msg) {
            Logc.d("onDataError: " + msg + " code " + code);
        }
    };

    /**
     * 控制设备
     *
     * @param data
     * @param iMethodCallBack
     */
    @Override
    protected void send(String data, IMethodCallBack iMethodCallBack) {
        HetWifiDeviceControlApi.getInstance().sendDataToDevice(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                iMethodCallBack.onSucess(code,msg);
            }

            @Override
            public void onFailed(int code, String msg) {
                iMethodCallBack.onFailed(code,msg);
            }
        }, deviceBean.getDeviceId(), data);


    }

    @Override
    public void onRightClick() {
        DeviceDetailActivity.startDeviceDetailActivity(mContext, deviceBean);
    }
}
