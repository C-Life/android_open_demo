package com.het.sdk.demo.ui.activity.bind;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.het.basic.base.RxManage;
import com.het.basic.utils.ActivityManager;
import com.het.bind.logic.bean.SSidInfoBean;
import com.het.bind.logic.bean.device.DeviceProductBean;
import com.het.open.lib.api.HetWifiBindApi;
import com.het.open.lib.callback.HetWifiBindState;
import com.het.open.lib.callback.IWifiBind;
import com.het.open.lib.biz.constans.HetDeviceConstans;
import com.het.open.lib.model.DeviceModel;
import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.utils.Constants;
import com.het.sdk.demo.utils.UIJsonConfig;
import com.het.sdk.demo.widget.BindProgressBar;
import com.het.sdk.demo.widget.RippleBackground;

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
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sSidInfoBean = (SSidInfoBean) bundle.getSerializable("SSidInfoBean");
            currentDevice = (DeviceProductBean) bundle.getSerializable(VALUE_KEY);
        }

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
                if(HetDeviceConstans.SCAN_PROGESS == type) {
                    setTextProgress(value);
                }else if(HetDeviceConstans.BIND_PROGESS == type){
                    showBindDialog();
                }
            }
        });
    }

    private void showBindFail() {
        disableTextProgress();
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

}
