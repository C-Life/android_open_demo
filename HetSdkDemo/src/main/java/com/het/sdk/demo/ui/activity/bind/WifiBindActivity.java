package com.het.sdk.demo.ui.activity.bind;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.het.basic.utils.SharePreferencesUtil;
import com.het.basic.utils.StringUtils;
import com.het.bind.logic.HeTBindApi;
import com.het.bind.logic.bean.SSidInfoBean;
import com.het.bind.logic.bean.device.DeviceProductBean;
import com.het.bind.logic.utils.Utils;
import com.het.log.Logc;
import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.utils.UIJsonConfig;
import com.het.sdk.demo.widget.HidePwdEditText;
import com.het.ui.sdk.BaseAbstractDialog;
import com.het.ui.sdk.CommonDialog;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 设置wifi信息页面
 */
public class WifiBindActivity extends BaseHetActivity {


    @Bind(R.id.bind_next)
    Button bind_next;
    @Bind(R.id.bind_webview)
    WebView webview;
    @Bind(R.id.ssid)
    TextView tv_ssid;
    @Bind(R.id.pass)
    HidePwdEditText et_pass;
    @Bind(R.id.remeber)
    CheckBox savepas;
    @Bind(R.id.bind_wifi_main)
    View bind_wifi_main;

    private WebSettings webSetting;
    public final static String VALUE_KEY = "DeviceProductBean";
    public final static String GUIDEURL_VALUE_KEY = "guide_url_html";
    protected DeviceProductBean deviceProductBean = null;
    private CommonDialog commonDialog;
    private String gudie_url;
    private int SETTING_GPS = 3;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_choose_wifi;
    }

    @Override
    protected void initData() {

        deviceProductBean = (DeviceProductBean) getIntent().getSerializableExtra(VALUE_KEY);
        gudie_url = getIntent().getStringExtra(GUIDEURL_VALUE_KEY);
        if (!StringUtils.isNull(gudie_url)) {
            webview.loadUrl(gudie_url);
        } else {
            HeTBindApi.getInstance().getDataApi().getGuideHtmlFiveUrl(s -> {
                if (TextUtils.isEmpty(s))
                    return;
                webview.loadUrl(s);
            }, throwable -> throwable.printStackTrace(), deviceProductBean);

        }



        savepas.setChecked(SharePreferencesUtil.getBoolean(mContext, "savePwd"));

        savepas.setOnCheckedChangeListener((buttonView, isChecked) -> SharePreferencesUtil.putBoolean(mContext, "savePwd", isChecked));
        if (!isLocationOpen()) {
            tips(getResources().getString(R.string.bind_wifi_turn_on_gps));
            gotoGpsSetting();
        }else {
            getSsId();
        }
    }

    private void getSsId(){
        HeTBindApi.getInstance().getWiFiInputApi().setOnWiFiStatusListener(this, sSidInfoBean -> {
            if (sSidInfoBean == null)
                return;
            tv_ssid.setText(sSidInfoBean.getSsid() == null ? "" : sSidInfoBean.getSsid());
            et_pass.setText(sSidInfoBean.getPass() == null ? "" : sSidInfoBean.getPass());
            if (!SharePreferencesUtil.getBoolean(mContext, "savePwd")) {
                et_pass.setText("");
            }

        }, throwable -> {
            CharSequence text = tv_ssid.getText();
            if (!TextUtils.isEmpty(text)) {
                tv_ssid.setText("");
                et_pass.setText("");
                checkWiFiStatus();
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initWebView();
        bind_next.setEnabled(true);
        try {
            et_pass.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (Utils.isChineseChar(s.toString())) {
                        tips(getResources().getString(R.string.bind_wifi_pass_no_chinese));
                        bind_next.setEnabled(false);
                    } else {
                        bind_next.setEnabled(true);
                    }
                    if (s != null && (s.length() >= 8 || s.length() == 0)) {
                        bind_next.setEnabled(true);
                    } else {
                        bind_next.setEnabled(false);
                    }
                    if (!Utils.isWifiConnected(WifiBindActivity.this)) {
                        bind_next.setEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            et_pass.setFilters(new InputFilter[]{
                    (source, start, end, dest, dstart, dend) -> {
                        Logc.d(source + "-start-" + start + "-end-" + end + "-dstart-" + dstart + "-dend-" + dend);
                        if (Utils.isChineseChar(source.toString())) {
                            tips(getResources().getString(R.string.bind_wifi_pass_no_chinese));
                            return "";
                        } else
                            return source;
                    }
            });
        } catch (Exception e) {
            e.printStackTrace();
            tips(e.getMessage());
        }

    }


    @Override
    protected void initTopBarView() {
        mTitleView.setTitle(getString(R.string.bind_type_top_name));
        mTitleView.setUpNavigateMode();
        mTitleView.setBackground(UIJsonConfig.getInstance(mContext).setNavBackground_color());
    }

    @OnClick({R.id.bind_next})
    public void onBind(View view) {
        switch (view.getId()) {
            case R.id.bind_next:
                startBind();
                break;
        }
    }

    private void initWebView() {
        webview.setHorizontalScrollBarEnabled(false);
        webSetting = webview.getSettings();
        webSetting.setDefaultTextEncodingName("GBK");
        // User settings
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        webSetting.setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        webSetting.setSupportZoom(false);//是否可以缩放，默认true
        webSetting.setBuiltInZoomControls(false);//是否显示缩放按钮，默认false
        webSetting.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        webSetting.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        webSetting.setAppCacheEnabled(true);//是否使用缓存
        webSetting.setDomStorageEnabled(true);//DOM Storage
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) //5.0以上不能加载http与https混合内容
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
    }

    private void checkWiFiStatus() {
        if (!Utils.isWifiConnected(this)) {
            bind_next.setEnabled(false);
            showTitleMsgTwoButtonAlertDialog(getResources().getString(R.string.bind_wifi_tips),
                    getResources().getString(R.string.bind_wifi_check_wifi),
                    getResources().getString(R.string.bind_wifi_oh_yes),
                    new BaseAbstractDialog.CommonDialogCallBack() {
                        @Override
                        public void onCancelClick() {

                        }

                        @Override
                        public void onConfirmClick(String... strings) {
                            gotoWiFiSetting();
                        }
                    });
        } else {
            if (et_pass != null) {
                String pass = et_pass.getText().toString();
                if (TextUtils.isEmpty(pass)) {
                    bind_next.setEnabled(false);
                }
            }
        }
    }

    private void showTitleMsgTwoButtonAlertDialog(String title, String msg, String yesText, CommonDialog.CommonDialogCallBack callback) {
        if (commonDialog != null) {
            commonDialog.dismiss();
        } else {
            commonDialog = new CommonDialog(this);
        }
        commonDialog.setDialogType(CommonDialog.DialogType.TitleWithMes);
        commonDialog.setTitle(title);
        commonDialog.setMessage(msg);
        commonDialog.setConfirmText(yesText);
        commonDialog.setTitleGravity(Gravity.CENTER);
        commonDialog.setMessageGravity(Gravity.CENTER);
        commonDialog.setCanceledOnTouchOutside(false);
        commonDialog.setCancleText(getString(R.string.bind_cancel));
        commonDialog.setCommonDialogCallBack(callback);
        commonDialog.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webview != null) {
            webview.destroy();
            webview = null;
        }
        if (commonDialog != null && commonDialog.isShowing()) {
            commonDialog.dismiss();
            commonDialog = null;
        }
        HeTBindApi.getInstance().getWiFiInputApi().onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (SETTING_GPS == requestCode) {
            if (isLocationOpen()){
                getSsId();
                Logc.i("============ 打开定位成功");
            }
            else
                tips(getResources().getString(R.string.bind_open_loc_faild));
        }
    }

    private void startBind() {
        //金立手机 GIONEE GN9012在扫ap的时候必须开启GPS
        if (!isLocationOpen()) {
            tips(getResources().getString(R.string.bind_wifi_turn_on_gps));
            gotoGpsSetting();
        } else {
            gotoScanActivity();
        }
    }

    /**
     * 判断位置信息是否开启
     *
     * @return
     */
    public boolean isLocationOpen() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //gps定位
        boolean isGpsProvider = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //网络定位
        boolean isNetWorkProvider = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return isGpsProvider || isNetWorkProvider;
    }

    /**
     * 挑战系统设置定位
     */
    public void gotoGpsSetting() {
        // 转到手机设置界面，用户设置GPS
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, SETTING_GPS); // 设置完成后返回到原来的界面
    }

    private void gotoScanActivity() {
        String pass = et_pass.getText().toString();
        if (Utils.isChineseChar(pass)) {
            tips(getResources().getString(R.string.bind_wifi_pass_no_chinese));
            return;
        }
        if (StringUtils.isNull(pass)) {
            tips(getResources().getString(R.string.bind_wifi_pass_no_null));
            return;
        }
        boolean isSave = savepas.isChecked();
        SSidInfoBean sSidInfoBean = HeTBindApi.getInstance().getWiFiInputApi().saveWiFiPassword(pass, isSave);//mPresenter.saveWiFiPassword(pass, isSave);
        Bundle bundle = new Bundle();
        bundle.putSerializable("SSidInfoBean", sSidInfoBean);
        bundle.putSerializable(VALUE_KEY, deviceProductBean);
        Intent intent = new Intent(this, SmartLinkConfigActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);

    }


    private void gotoWiFiSetting() {
        if (!isLocationOpen()) {
            tips(getResources().getString(R.string.bind_wifi_turn_on_gps));
            gotoGpsSetting();
        } else {
            Intent intent = new Intent();
            if(android.os.Build.VERSION.SDK_INT >= 11){
                intent .setClassName("com.android.settings", "com.android.settings.Settings$WifiSettingsActivity");
            }else{
                intent .setClassName("com.android.settings" ,"com.android.settings.wifi.WifiSettings");
            }
            startActivity( intent);
        }
    }

    public void onWiFiSetting(View view) {
        gotoWiFiSetting();
    }
}
