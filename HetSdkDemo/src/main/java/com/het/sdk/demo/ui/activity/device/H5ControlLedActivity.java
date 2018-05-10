package com.het.sdk.demo.ui.activity.device;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.het.basic.AppDelegate;
import com.het.basic.base.RxManage;
import com.het.basic.utils.ToastUtil;
import com.het.basic.utils.permissions.RxPermissions;
import com.het.open.lib.model.DeviceModel;
import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetH5Activity;
import com.het.sdk.demo.ui.activity.bind.QrScanActivity;
import com.het.sdk.demo.ui.activity.share.UserMessShareActivity;
import com.het.sdk.demo.utils.UIJsonConfig;

/**
 * Created by liuzh on 2017-10-23.
 * 设备H5控制  旧设备H5控制  wifi设备控制建议采用H5ComWifiControlActivity
 */

public class H5ControlLedActivity extends BaseHetH5Activity {

    public static void startH5ControlLedActivity(Context context, DeviceModel deviceModel) {
        Intent intent = new Intent(context, H5ControlLedActivity.class);
        intent.putExtra("DeviceModel", deviceModel);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {
        super.initData();
        RxManage.getInstance().register("Qr_device_url", url -> {
            mHtmlFiveManager.loadUrl((String) url);
        });
    }

    @Override
    protected void initTopBarView() {
        mTitleView.setTitle("设备H5控制");
        mTitleView.setUpTextOption("扫描", v -> {
            startCamera();
        });
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

    /**
     * 获取权限
     */
    private void startCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RxPermissions.getInstance(AppDelegate.getAppContext())
                    .request(Manifest.permission.CAMERA)
                    .subscribe(grant -> {
                        if (grant) {
                            jumpToTarget(QrScanActivity.class);
                        }
                    });
        } else {
            jumpToTarget(QrScanActivity.class);
        }
    }

    @Override
    protected void initH5Page() {
        super.initH5Page();
        (new DeviceH5Persenter()).loadUrlFromDeviceBean(mContext, deviceModel, mHtmlFiveManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxManage.getInstance().unregister("Qr_device_url");
    }

}
