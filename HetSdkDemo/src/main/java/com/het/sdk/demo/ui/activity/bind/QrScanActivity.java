package com.het.sdk.demo.ui.activity.bind;

import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.het.basic.base.RxManage;
import com.het.basic.utils.ToastUtil;
import com.het.bind.logic.api.bind.bean.ModuleType;
import com.het.bind.logic.bean.device.DeviceProductBean;
import com.het.open.lib.api.HetDeviceShareApi;
import com.het.open.lib.api.HetQrCodeApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.event.HetShareEvent;
import com.het.sdk.demo.utils.UIJsonConfig;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

import static com.het.sdk.demo.ui.activity.bind.WifiBindActivity.VALUE_KEY;


public class QrScanActivity extends BaseHetActivity implements QRCodeView.Delegate {


    private QRCodeView mQRCodeView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_qr_scan;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        mQRCodeView = (ZXingView) findViewById(R.id.zbarview);
        mQRCodeView.setDelegate(this);
    }

    @Override
    protected void initTopBarView() {
        mTitleView.setTitle(R.string.btn_scan);
        mTitleView.setBackground(UIJsonConfig.getInstance(mContext).setNavBackground_color());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.startSpotAndShowRect();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        this.mQRCodeView.stopSpotAndHiddenRect();
        this.mQRCodeView.stopCamera();
        if (result.length() == 32) {
            parseQrCodeVer2(result);
        } else {
            parseQrCodeVersion(result);
        }
    }

    /**
     * @param code 设备分享码
     */
    private void parseQrCodeVer2(String code) {
        HetDeviceShareApi.getInstance().authShareDevice(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                ToastUtil.showToast(mContext, "设备分享成功");
                RxManage.getInstance().post(HetShareEvent.HET_EVENT_MAIN_SHARE_SUCCEE,null);
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtil.showToast(mContext, msg);
                finish();
            }
        }, code, "5");

    }


    //二维码新规则
    private void parseQrCodeVersion(String url) {
        String param = Uri.parse(url).getQueryParameter("param");
        if (TextUtils.isEmpty(param)) {
            tips(getResources().getString(R.string.qr_code_error));
        } else {
            HetQrCodeApi.getInstance().dealQrCode(new IHetCallback() {
                @Override
                public void onSuccess(int code, String msg) {

                    DeviceProductBean deviceProductBean = new Gson().fromJson(msg, DeviceProductBean.class);
                    Bundle bund = new Bundle();
                    bund.putSerializable(VALUE_KEY, deviceProductBean);

                    if (deviceProductBean.getModuleType() == ModuleType.WIFI) {
                        QrScanActivity.this.jumpToTarget(WifiBindActivity.class, bund);
                    } else if (deviceProductBean.getModuleType() == ModuleType.BLE) {
                        QrScanActivity.this.jumpToTarget(BleBindActivity.class, bund);
                    }
                }

                @Override
                public void onFailed(int code, String msg) {
                    tips(msg);
                    finish();
                }
            }, url);
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(this, "打开相机出错", Toast.LENGTH_SHORT).show();
        finish();
    }
}