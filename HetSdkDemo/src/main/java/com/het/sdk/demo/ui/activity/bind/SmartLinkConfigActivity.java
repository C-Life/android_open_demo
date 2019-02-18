package com.het.sdk.demo.ui.activity.bind;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.het.basic.base.RxManage;
import com.het.basic.utils.ActivityManager;
import com.het.bind.logic.HeTBindApi;
import com.het.bind.logic.api.bind.modules.ap.callback.NetWorkConnectListener;
import com.het.bind.logic.bean.SSidInfoBean;
import com.het.bind.logic.bean.device.DeviceProductBean;
import com.het.bluetoothoperate.manager.BluetoothDeviceManager;
import com.het.log.Logc;
import com.het.open.lib.api.HetCodeConstants;
import com.het.open.lib.api.HetWifiBindApi;
import com.het.open.lib.callback.HetWifiBindState;
import com.het.open.lib.callback.IWifiBind;
import com.het.open.lib.model.DeviceModel;
import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.utils.ApFitManager;
import com.het.sdk.demo.utils.Constants;
import com.het.sdk.demo.utils.UIJsonConfig;
import com.het.sdk.demo.widget.BindProgressBar;
import com.het.sdk.demo.widget.RippleBackground;
import com.het.ui.sdk.CommonDialog;

import butterknife.Bind;

import static com.het.sdk.demo.R.string.device;
import static com.het.sdk.demo.ui.activity.bind.WifiBindActivity.VALUE_KEY;


/**
 * 扫描设备界面
 */
public class SmartLinkConfigActivity extends BaseHetActivity {
    private static final String TAG = "SmartLinkConfigActivity";

    @Bind(R.id.bar_scanning)
    BindProgressBar bar_scanning;
    @Bind(R.id.ll_bind_scanning)
    LinearLayout ll_bind_scanning;

    @Bind(R.id.tv_caption)
    TextView tv_caption;
    @Bind(R.id.tv_type)
    TextView tv_type;
    @Bind(R.id.rl_ripple)
    RippleBackground rl_ripple;
    @Bind(R.id.ll_bind_success)
    LinearLayout ll_bind_success;

    @Bind(R.id.ll_bind_fail)
    LinearLayout ll_bind_fail;

    @Bind(R.id.tv_show_state)
    TextView tv_show_state;

    private View header = null;
    private SSidInfoBean sSidInfoBean = null;
    private PowerManager.WakeLock wakeLock;
    protected DeviceProductBean currentDevice = null;
    private CommonDialog commonDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_smart_bind;
    }

    @Override
    protected void initTopBarView() {
        mTitleView.setTitle(getString(R.string.bind_type_top_name));
        mTitleView.setUpNavigateMode();
        mTitleView.setBackground(UIJsonConfig.getInstance(mContext).setNavBackground_color());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        header = findViewById(R.id.ll_bind_scanning);
        if (header != null) {
            bar_scanning = (BindProgressBar) header.findViewById(R.id.bar_scanning);
        }
        setTextProgress(0);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "TAG");
        wakeLock.acquire();

        inableTextProgress();

        registerNetworkListener();
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sSidInfoBean = (SSidInfoBean) bundle.getSerializable("SSidInfoBean");
            currentDevice = (DeviceProductBean) bundle.getSerializable(VALUE_KEY);
        }
        BluetoothDeviceManager.getInstance().init(mContext);
        HetWifiBindApi.getInstance().startBind(this, sSidInfoBean.getSsid(), sSidInfoBean.getPass(), "" + currentDevice.getProductId(), new IWifiBind() {
            @Override
            public void onStatus(HetWifiBindState hetWifiBindState, String msg) {

            }

            @Override
            public void onFailed(int errId, String msg) {
                showBindFail();
            }

            @Override
            public void onSuccess(DeviceModel deviceModel) {
                showBindSuccess();
            }

            @Override
            public void onProgress(int type, int value) {
                if (HetCodeConstants.SCAN_PROGESS == type) {
                    setTextProgress(value);
                    //AP绑定 针对几款不能自动连接WIFI的兼容 50s发现不了设备热点，需要手动去wifi设置界面
                    if (currentDevice.getBindType() == 9 && value == 50 && ApFitManager.getInstance().isFit()) {
                        handleConnectWifi(null);
                    }
                } else if (HetCodeConstants.BIND_PROGESS == type) {
                    showBindDialog();
                }
            }
        });

    }


    private void registerNetworkListener() {
        if (!ApFitManager.getInstance().isFit())
            return;
        int timeout = 0;
        int connApCount = 0;
        HeTBindApi.getInstance().getBindApi().setOnNetworkListener(new NetWorkConnectListener(timeout, connApCount) {
            @Override
            public void onNetworkConnTimeout(Context context) {
            }

            @Override
            public void onNetworkConnected(Context context) {
            }

            @Override
            public void onDeviceConfigComplete(Context context) {
//                tips(getResources().getString(R.string.bind_please_jump_mainapp));
            }

            @Override
            public boolean onConnDeviceApTimeout(Context context, final String ssid) {
                //返回false，则程序后台依然连接ap。返回true，则后台程序不做ap连接，只做ap连接状态查询
                handleConnectWifi(ssid);
                return true;
            }
        });
    }

    private void handleConnectWifi(String ssid) {
        runOnUiThread(() -> {
            String msg = TextUtils.isEmpty(ssid) ? getResources().getString(R.string.bind_please_conn_device) : getResources().getString(R.string.bind_please_conn_ap) + ssid;

            DeviceProductBean dev = HeTBindApi.getInstance().getBindApi().getTargetDevice();
            if (dev != null && !TextUtils.isEmpty(dev.getApPassword())) {
                msg += ",";
                msg += getResources().getString(R.string.bind_please_conn_ap_pass) + dev.getApPassword();
            }
            Logc.i("==onConnDeviceApTimeout:" + msg);
            CommonDialog.CommonDialogCallBack onBindFailedCallback = showTitleMsgOneButtonAlertDialog(
                    getResources().getString(R.string.bind_reminder),
                    msg,
                    getResources().getString(R.string.bind_togo_conn_ap), new CommonDialog.CommonDialogCallBack() {

                        @Override
                        public void onCancelClick() {
                        }

                        @Override
                        public void onConfirmClick(String... strings) {
                            ApFitManager.getInstance().gotoWiFiSetting(SmartLinkConfigActivity.this);
                        }
                    });
        });
    }

    private CommonDialog.CommonDialogCallBack showTitleMsgOneButtonAlertDialog(String title, String msg, String yesText, CommonDialog.CommonDialogCallBack callback) {
        commonDialog = new CommonDialog(this);
        commonDialog.setDialogType(CommonDialog.DialogType.TitleWithMes);
        commonDialog.setTitle(title);
        commonDialog.setMessage(msg);
        commonDialog.setMessageGravity(Gravity.CENTER);
        commonDialog.setConfirmText(yesText);
        commonDialog.setCanceledOnTouchOutside(false);
        commonDialog.setCancleVisiable(View.GONE);
        commonDialog.setCommonDialogCallBack(callback);
        commonDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    ActivityManager.getInstance().finishActivity(WifiBindActivity.class);
                    closeActivity();
                    return true;
                } else {
                    return false;
                }
            }
        });
        commonDialog.show();
        return callback;
    }


    private void unregisterNetworkListener() {
        if (!ApFitManager.getInstance().isFit())
            return;
        HeTBindApi.getInstance().getBindApi().setOnNetworkListener(null);
    }

    private void showBindFail() {
        disableTextProgress();
        if (commonDialog != null && commonDialog.isShowing()){
            commonDialog.dismiss();
            commonDialog = null;
        }
        ll_bind_fail.setVisibility(View.VISIBLE);
        rl_ripple.setVisibility(View.GONE);
        ll_bind_scanning.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wakeLock != null) {
            wakeLock.release();
        }
        if (commonDialog != null && commonDialog.isShowing()){
            commonDialog.dismiss();
            commonDialog = null;
        }
        HetWifiBindApi.getInstance().stopBind();
    }

    private void showBindSuccess() {
        disableTextProgress();
        RxManage.getInstance().post(Constants.DEVICE_BIND, device);
        AlphaAnimation aa = new AlphaAnimation(1f, 0.1f);
        aa.setDuration(1000);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                rl_ripple.setVisibility(View.GONE);
                ll_bind_fail.setVisibility(View.GONE);
                ll_bind_success.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ActivityManager.getInstance().finishActivity(WifiBindActivity.class);
                        ActivityManager.getInstance().finishActivity(DeviceSubTypeListActivity.class);
                        ActivityManager.getInstance().finishActivity(DeviceTypeListActivity.class);
                        ActivityManager.getInstance().finishActivity(SmartLinkConfigActivity.this);
                    }
                }, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rl_ripple.stopRippleAnimation();
        rl_ripple.startAnimation(aa);

        if (commonDialog != null && commonDialog.isShowing()){
            commonDialog.dismiss();
            commonDialog = null;
        }
    }


    /**
     * 设置扫描进度
     **/
    private void setTextProgress(int progress) {
        if (bar_scanning != null && bar_scanning.getVisibility() != View.GONE) {
            bar_scanning.setProgress(progress);
        }
    }

    /**
     * 隐藏扫描进度
     **/
    private void disableTextProgress() {
        if (header.getVisibility() != View.GONE) {
            header.setVisibility(View.GONE);
        }
    }

    /**
     * 显示扫描进度
     **/

    private void inableTextProgress() {
        if (rl_ripple.getVisibility() != View.GONE) {
            rl_ripple.stopRippleAnimation();
            rl_ripple.setVisibility(View.GONE);
        }
        if (ll_bind_scanning.getVisibility() != View.VISIBLE) {
            ll_bind_scanning.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示绑定loading
     **/
    private void showBindDialog() {

        ll_bind_scanning.setVisibility(View.GONE);
        ll_bind_fail.setVisibility(View.GONE);

        rl_ripple.setVisibility(View.VISIBLE);
        rl_ripple.startRippleAnimation();

        tv_caption.setText(currentDevice.getProductName());
        tv_type.setText(currentDevice.getProductCode());

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        unregisterNetworkListener();
        closeActivity();
    }


}
