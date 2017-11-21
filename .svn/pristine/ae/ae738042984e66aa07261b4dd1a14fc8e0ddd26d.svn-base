package com.het.sdk.demo.ui.activity.bind;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.het.basic.AppDelegate;
import com.het.basic.utils.GsonUtil;
import com.het.basic.utils.permissions.RxPermissions;
import com.het.open.lib.model.DeviceTypeModel;
import com.het.recyclerview.XRecyclerView;
import com.het.sdk.demo.R;
import com.het.sdk.demo.adapter.AdapterDeviceTypeList;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.manager.RecyclerViewManager;
import com.het.sdk.demo.utils.Constants;
import com.het.sdk.demo.utils.UIJsonConfig;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 设备大类列表
 */
public class DeviceTypeListActivity extends BaseHetActivity<DeviceTypePersenter> implements DeviceTypeHetView {
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private List<DeviceTypeModel> deviceModels = new ArrayList<>();
    private AdapterDeviceTypeList mAdapter;
    @Bind(R.id.device_list)
    XRecyclerView mRecyclerView;
    @Bind(R.id.iv_qr_code)
    ImageView ivQrCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_list;
    }

    @Override
    protected void initData() {
        mAdapter.setOnItemClickListener((view, o, i) -> {
            DeviceTypeModel deviceModel = deviceModels.get(i);
            Intent intent = new Intent(DeviceTypeListActivity.this, DeviceSubTypeListActivity.class);
            intent.putExtra(Constants.DEVICE_TYPE_MODEL, deviceModel);
            startActivity(intent);
        });
        mPresenter.getDeviceTypeList();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRecyclerView = new RecyclerViewManager().getXLinearInstance(this, mRecyclerView, false, false);
        mAdapter = new AdapterDeviceTypeList(mContext, R.layout.adapter_choose_bind_device_item1);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initTopBarView() {
        mTitleView.setTitle(getString(R.string.bind_type_top_name));
        mTitleView.setBackground(UIJsonConfig.getInstance(mContext).setNavBackground_color());
        mTitleView.setUpImgOption(R.mipmap.bind_right_help,v -> {
            DeviceTypeListActivity.this.jumpToTarget(DeviceReadmeActivity.class);
        });
    }

    @OnClick({R.id.iv_qr_code})
    public void onclick() {
        startCamera();

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
                            startQrActivity();
                        }
                    });
        }else {
            startQrActivity();
        }
    }

    private void startQrActivity(){
        Intent intent = new Intent();
        intent.setClass(DeviceTypeListActivity.this, QrScanActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();

//                    //显示扫描到的内容
//                    mTextView.setText(bundle.getString("result"));
//                    //显示
//                    mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
                }
                break;
        }
    }

    @Override
    public void setTypeListView(String list) {
        Type type = new TypeToken<List<DeviceTypeModel>>() {
        }.getType();
        List<DeviceTypeModel> models = GsonUtil.getInstance().getGson().fromJson(list, type);
        if (models != null && models.size() > 0) {
            deviceModels.clear();
            deviceModels.addAll(models);
            mAdapter.setListAll(deviceModels);
        } else {
            showToast("没有设备");
        }
    }
}
