package com.het.sdk.demo.ui.activity.device;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.het.basic.utils.GsonUtil;
import com.het.basic.utils.ToastUtil;
import com.het.log.Logc;
import com.het.open.lib.api.HetDeviceWifiControlApi;
import com.het.open.lib.api.HetWifiDeviceControlApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.callback.IWifiDeviceData;
import com.het.open.lib.model.DeviceModel;
import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.model.led.LedConfigModel;
import com.het.sdk.demo.model.led.LedRunDataModel;
import com.het.sdk.demo.ui.activity.share.UserMessShareActivity;
import com.het.sdk.demo.utils.UIJsonConfig;

import butterknife.Bind;
import butterknife.OnClick;


public class ControlLedActivity extends BaseHetActivity {


    @Bind(R.id.lightIv)
    ImageView lightIv;
    @Bind(R.id.closeBtn)
    Button closeBtn;
    @Bind(R.id.readIv)
    ImageView readIv;
    @Bind(R.id.readTv)
    TextView readTv;

    @Bind(R.id.restIv)
    ImageView restIv;
    @Bind(R.id.restTv)
    TextView restTv;

    @Bind(R.id.restRe)
    RelativeLayout restRe;
    @Bind(R.id.yedengRe)
    RelativeLayout yedengRe;
    @Bind(R.id.readRe)
    RelativeLayout readRe;


    @Bind(R.id.yedengIv)
    ImageView yedengIv;
    @Bind(R.id.yedengTv)
    TextView yedengTv;

    @Bind(R.id.lightSB)
    SeekBar lightSB;
    @Bind(R.id.one)
    ImageView one;
    @Bind(R.id.two)
    ImageView two;
    @Bind(R.id.colorSB)
    SeekBar colorSB;

    private DeviceModel deviceModel;

    private LedConfigModel ledConfigModel = new LedConfigModel();
    private LedRunDataModel ledRunDataModel = new LedRunDataModel();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_led_layout;
    }


    @Override
    protected void initData() {

        if (deviceModel != null) {

           // HetWifiDeviceControlApi.getInstance().init(mContext, iWifiDeviceData);

            HetWifiDeviceControlApi.getInstance().start(deviceModel.getDeviceId(),iWifiDeviceData);
        }
    }

    private IWifiDeviceData iWifiDeviceData = new IWifiDeviceData() {
        @Override
        public void onGetConfigData(String jsonData) {
            Logc.d("onGetConfigData: " ,jsonData);
            LedConfigModel configModel = GsonUtil.getInstance().toObject(jsonData, LedConfigModel.class);
            if (ledConfigModel != null) {
                ledConfigModel = configModel;
            }
        }

        @Override
        public void onGetRunData(String jsonData) {
            Logc.d("onGetRunData: " ,jsonData);
            LedRunDataModel runDataModel = GsonUtil.getInstance().toObject(jsonData, LedRunDataModel.class);
            if (runDataModel != null) {
                ledRunDataModel = runDataModel;
            }
        }

        @Override
        public void onGetErrorData(String jsonData) {
            Logc.d("onGetErrorData: " + jsonData);
        }

        @Override
        public void onDeviceStatues(int onlineStatus) {

        }

        @Override
        public void onDataError(int code, String msg) {
            Logc.d("onDataError: " + msg + " code " + code);
        }
    };

    @Override
    protected void initView(Bundle savedInstanceState) {
        deviceModel = (DeviceModel) getIntent().getSerializableExtra("DeviceModel");
    }

    @Override
    protected void initTopBarView() {
        mTitleView.setTitle("设备控制");
        mTitleView.setBackground(UIJsonConfig.getInstance(mContext).setNavBackground_color());
        mTitleView.setUpImgOption(R.mipmap.add, v -> {
            if (deviceModel.getShare() == 2) {
                Bundle bundle = new Bundle();
                bundle.putString("DeviceModel", deviceModel.getDeviceId());
                jumpToTarget(UserMessShareActivity.class, bundle);
            } else {
                ToastUtil.showToast(mContext, "该设备是分享的设备,不能分享给其他人");
            }
        });
    }

    @OnClick({R.id.readRe, R.id.restRe, R.id.yedengRe, R.id.closeBtn})
    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.readRe:
                break;
            case R.id.restRe:
                break;
            case R.id.yedengRe:
                break;
            case R.id.closeBtn:
                submit();
                break;
        }
    }

    private void submit() {

        if (ledConfigModel.getSwitchStatus() == "90") {
            ledConfigModel.setSwitchStatus("165");
            ledConfigModel.setSceneMode("4");
            ledConfigModel.setColorTemp("10");
            ledConfigModel.setLightness("2");
        } else {
            ledConfigModel.setSwitchStatus("90");
            ledConfigModel.setSceneMode("4");

            ledConfigModel.setColorTemp("40");
            ledConfigModel.setLightness("3");
        }
        ledConfigModel.setUpdateFlag("1");

        //HetWifiDeviceControApi.getInstance().

        HetDeviceWifiControlApi.getInstance().setDataToDevice(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {


            }

            @Override
            public void onFailed(int code, String msg) {

            }
        }, deviceModel.getDeviceId(), GsonUtil.getInstance().toJson(ledConfigModel));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HetWifiDeviceControlApi.getInstance().stop(deviceModel.getDeviceId());
    }
}
