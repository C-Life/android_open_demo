package com.het.sdk.demo.ui.activity.bind;

import com.het.open.lib.api.HetDeviceListApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.base.BaseHetPresenter;

/**
 * Created by Administrator on 2017-09-06.
 */

public class DeviceTypePersenter extends BaseHetPresenter<DeviceTypeHetView> {


    public void getDeviceTypeList() {
        HetDeviceListApi.getInstance().getTypeList(new IHetCallback() {

            @Override
            public void onSuccess(int code, String s) {
                if (code == 0) {
                    mHetView.setTypeListView(s);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ((BaseHetActivity) activity).showToast(msg);
            }
        });
    }

    public void getDeviceSubTypeList(String deviceType) {
        HetDeviceListApi.getInstance().getSubTypeListProduct(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (code == 0) {
                    mHetView.setTypeListView(msg);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ((BaseHetActivity) activity).showToast(msg);
            }
        }, deviceType);
    }
}
