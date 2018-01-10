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
import com.het.bind.logic.bean.device.DeviceProductBean;
import com.het.open.lib.api.HetCodeConstants;
import com.het.open.lib.api.HetCommonBleBindApi;
import com.het.open.lib.callback.ICommonBleBind;
import com.het.open.lib.model.DeviceModel;
import com.het.recyclerview.XRecyclerView;
import com.het.recyclerview.recycler.BaseRecyclerViewAdapter;
import com.het.sdk.demo.R;
import com.het.sdk.demo.adapter.DeviceScanAdpter;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.manager.RecyclerViewManager;
import com.het.sdk.demo.utils.Constants;
import com.het.sdk.demo.utils.UIJsonConfig;
import com.het.sdk.demo.widget.BindProgressBar;
import com.het.sdk.demo.widget.RippleBackground;

import java.util.List;

import butterknife.Bind;

import static com.het.sdk.demo.R.string.device;
import static com.het.sdk.demo.ui.activity.bind.WifiBindActivity.VALUE_KEY;


public class BleBindActivity extends BaseHetActivity {


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


    @Bind(R.id.device_list)
    XRecyclerView device_list;

    private DeviceScanAdpter deviceScanAdpter;
    private PowerManager.WakeLock wakeLock;
    //private DeviceSubModel deviceSubModel;
    private DeviceProductBean deviceProductBean;
    private View header = null;
    DeviceProductBean deviceProductBean1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_smart_ble_bind;
        //  return 0;
    }

    @Override
    protected void initTopBarView() {
        mTitleView.setTitle("蓝牙设备绑定");
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
            deviceProductBean1 = (DeviceProductBean) bundle.getSerializable(VALUE_KEY);
        }
        System.out.println("deviceProductBean1== " + deviceProductBean1.toString());
        device_list = new RecyclerViewManager().getXLinearInstance(this, device_list, false, false);
        deviceScanAdpter = new DeviceScanAdpter(this, R.layout.bind_device_scan_item);
        device_list.setAdapter(deviceScanAdpter);


        deviceScanAdpter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object o, int i) {

                deviceProductBean = (DeviceProductBean) o;
                showBindDialog();
                HetCommonBleBindApi.getInstance().bindToServer(deviceProductBean);

            }
        });
        startScan();
    }

    /**
     * 开始蓝牙设备扫描绑定
     */
    private void startScan() {
        HetCommonBleBindApi.getInstance().startBind(this, "" + deviceProductBean1.getProductId(), new ICommonBleBind() {
            @Override
            public void onScanDevices(int code, List<DeviceProductBean> devices) {
                if (code == 0 && devices != null && devices.size() > 0) {
                    for (int i = 0; i < devices.size(); i++) {
                        devices.get(i).setProductName(deviceProductBean1.getProductName());
                    }
                    showScanSucess(devices);
                } else {
                    hideDialog();
                    showBindFail();
                }
            }

            @Override
            public void onFailed(int errId, String msg) {
                hideDialog();
                showBindFail();
            }

            @Override
            public void onSuccess(DeviceModel deviceModel) {
                hideDialog();
                showBindSuccess();
            }

            @Override
            public void onProgress(int type, int value) {
                if (HetCodeConstants.SCAN_PROGESS == type) {
                    setTextProgress(value);
                } else if (HetCodeConstants.BIND_PROGESS == type) {
                    showBindDialog();
                }
            }
        });
    }

    private void showScanSucess(List<DeviceProductBean> scanDevices) {

        disableTextProgress();
        device_list.setVisibility(View.VISIBLE);

        deviceScanAdpter.setListAll(scanDevices);

        deviceScanAdpter.notifyDataSetChanged();
    }

    private void showBindFail() {
        if (!isFinishing()) {
            disableTextProgress();
            ll_bind_fail.setVisibility(View.VISIBLE);
            rl_ripple.setVisibility(View.GONE);
            ll_bind_scanning.setVisibility(View.GONE);
            device_list.setVisibility(View.GONE);
        }

    }

    private void showBindSuccess() {
        if (!isFinishing()) {
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
                    device_list.setVisibility(View.GONE);
                    ll_bind_success.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ActivityManager.getInstance().finishActivity(WifiBindActivity.class);
                            ActivityManager.getInstance().finishActivity(DeviceSubTypeListActivity.class);
                            ActivityManager.getInstance().finishActivity(DeviceTypeListActivity.class);
                            ActivityManager.getInstance().finishActivity(BleBindActivity.this);
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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wakeLock != null) {
            wakeLock.release();
        }

        HetCommonBleBindApi.getInstance().stopBind();
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
        device_list.setVisibility(View.GONE);

        rl_ripple.setVisibility(View.VISIBLE);
        rl_ripple.startRippleAnimation();
        System.out.println("deviceProductBean" + deviceProductBean.toString());
        if (deviceProductBean != null) {
            tv_caption.setText("" + deviceProductBean.getProductName());
            //tv_type.setText("" + deviceProductBean.getProductCode());
        }

    }

}
