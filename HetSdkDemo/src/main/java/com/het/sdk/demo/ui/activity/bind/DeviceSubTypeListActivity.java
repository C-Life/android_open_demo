package com.het.sdk.demo.ui.activity.bind;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.reflect.TypeToken;
import com.het.basic.utils.GsonUtil;
import com.het.bind.logic.api.bind.bean.ModuleType;
import com.het.bind.logic.bean.device.DeviceProductBean;
import com.het.open.lib.model.DeviceSubModel;
import com.het.open.lib.model.DeviceTypeModel;
import com.het.recyclerview.XRecyclerView;
import com.het.sdk.demo.R;
import com.het.sdk.demo.adapter.AdapterDeviceSubtypeList;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.manager.RecyclerViewManager;
import com.het.sdk.demo.utils.Constants;
import com.het.sdk.demo.utils.UIJsonConfig;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.het.sdk.demo.ui.activity.bind.WifiBindActivity.GUIDEURL_VALUE_KEY;
import static com.het.sdk.demo.ui.activity.bind.WifiBindActivity.VALUE_KEY;

/**
 * 设备小类列表
 */
public class DeviceSubTypeListActivity extends BaseHetActivity<DeviceTypePersenter> implements DeviceTypeHetView {

    private List<DeviceSubModel> deviceModels = new ArrayList<>();
    private AdapterDeviceSubtypeList mAdapter;
    private DeviceTypeModel deviceTypeModel;
    @Bind(R.id.device_list)
    XRecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sub_device_list;
    }

    @Override
    protected void initData() {
        deviceTypeModel = (DeviceTypeModel) getIntent().getSerializableExtra(Constants.DEVICE_TYPE_MODEL);
        mAdapter.setOnItemClickListener((view, o, i) -> {

            DeviceSubModel deviceSubModel = deviceModels.get(i);
            DeviceProductBean deviceProductBean = new DeviceProductBean();

            deviceProductBean.setDeviceTypeId(deviceSubModel.getDeviceTypeId());
            deviceProductBean.setDeviceSubtypeId(deviceSubModel.getDeviceSubtypeId());
            deviceProductBean.setProductId(deviceSubModel.getProductId());
            deviceProductBean.setModuleType(ModuleType.values()[deviceSubModel.getModuleType()]);
            deviceProductBean.setModuleId(deviceSubModel.getModuleId());
            deviceProductBean.setProductName(deviceSubModel.getProductName());
            deviceProductBean.setRadioCastName(deviceSubModel.getRadiocastName());
            deviceProductBean.setBindType(deviceSubModel.getModuleType());

            int type = deviceSubModel.getModuleType();
            switch (type) {
                case 1:
                case 9:
                    Intent intent = new Intent(DeviceSubTypeListActivity.this, WifiBindActivity.class);
                    intent.putExtra(VALUE_KEY, deviceProductBean);
                    intent.putExtra(GUIDEURL_VALUE_KEY, deviceSubModel.getGuideUrl());
                    startActivity(intent);
                    finish();
                    break;
                case 2:
                    Intent intentBle = new Intent(DeviceSubTypeListActivity.this, BleBindActivity.class);
                    intentBle.putExtra(VALUE_KEY, deviceProductBean);
                    startActivity(intentBle);
                    finish();
                    break;
                case 3:
                    break;
            }
        });

        String deviceType = deviceTypeModel.getDeviceTypeId();
        mPresenter.getDeviceSubTypeList(deviceType);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRecyclerView = new RecyclerViewManager().getXLinearInstance(this, mRecyclerView, false, false);
        mAdapter = new AdapterDeviceSubtypeList(mContext, R.layout.adapter_choose_bind_device_item1);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initTopBarView() {
        mTitleView.setTitle(getString(R.string.bind_type_top_name));
        mTitleView.setBackground(UIJsonConfig.getInstance(mContext).setNavBackground_color());
    }


    @Override
    public void setTypeListView(String list) {
        Type type = new TypeToken<List<DeviceSubModel>>() {
        }.getType();
        List<DeviceSubModel> models = GsonUtil.getInstance().getGson().fromJson(list, type);
        if (models != null && models.size() > 0) {
            deviceModels.clear();
            deviceModels.addAll(models);
            mAdapter.setListAll(deviceModels);
        } else {
            showToast("没有设备");
        }
    }
}
