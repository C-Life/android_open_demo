package com.het.sdk.demo.ui.activity.device;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.het.basic.AppDelegate;
import com.het.basic.base.RxManage;
import com.het.basic.model.DeviceBean;
import com.het.basic.utils.ToastUtil;
import com.het.basic.utils.permissions.RxPermissions;
import com.het.h5.sdk.utils.H5VersionUtil;
import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.ui.activity.bind.QrScanActivity;
import com.het.sdk.demo.ui.activity.share.UserMessShareActivity;
import com.het.sdk.demo.utils.UIJsonConfig;
import com.het.sdk.demo.widget.ItemView;

import butterknife.Bind;
import butterknife.OnClick;

import static com.het.sdk.demo.R.id.tv_device_name;

public class DeviceDetailActivity extends BaseHetActivity {

    @Bind(R.id.device_icon)
    SimpleDraweeView deviceIcon;
    @Bind(tv_device_name)
    TextView tvDeviceName;
    @Bind(R.id.tv_device_type)
    TextView tvDeviceType;
    @Bind(R.id.tv_device_mac)
    TextView tvDeviceMac;

    @Bind(R.id.itemview_qr)
    ItemView itemviewQr;
    @Bind(R.id.itemview_permission)
    ItemView itemviewPermission;

    private DeviceBean deviceModel;

    public static void startDeviceDetailActivity(Context context, DeviceBean deviceBean) {
        Intent intent = new Intent(context, DeviceDetailActivity.class);
        intent.putExtra(H5VersionUtil.DEVICE_BEAN, deviceBean);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_detail;
    }

    @Override
    protected void initData() {
        deviceModel = (DeviceBean) getIntent().getSerializableExtra(H5VersionUtil.DEVICE_BEAN);
        if (deviceModel != null) {
            if (!TextUtils.isEmpty(deviceModel.getProductIcon())) {
                deviceIcon.setImageURI(Uri.parse(deviceModel.getProductIcon()));
            }
            tvDeviceName.setText(deviceModel.getDeviceName());
            tvDeviceType.setText(getString(R.string.detail_type) + deviceModel.getProductCode());
            tvDeviceMac.setText(getString(R.string.detail_mac) + deviceModel.getMacAddress());
        } else {
            ToastUtil.showToast(mContext, getString(R.string.detail_error));
        }
        RxManage.getInstance().register("Qr_device_url", url -> {
            finish();
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    protected void initTopBarView() {
        mTitleView.setTitle("设备详情");
        mTitleView.setBackground(UIJsonConfig.getInstance(mContext).setNavBackground_color());
    }

    @OnClick({R.id.itemview_qr, R.id.itemview_permission})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.itemview_qr://二维码调试数据
                startCamera();
                break;
            case R.id.itemview_permission://设备分享
                if (deviceModel.getShare() == 2) {
                    Bundle bundle = new Bundle();
                    bundle.putString("DeviceModel", deviceModel.getDeviceId());
                    jumpToTarget(UserMessShareActivity.class, bundle);
                } else {
                    ToastUtil.showToast(mContext, "该设备是分享的设备,不能分享给其他人");
                }
                break;
        }
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

}
