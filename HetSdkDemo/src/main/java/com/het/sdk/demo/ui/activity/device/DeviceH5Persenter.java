package com.het.sdk.demo.ui.activity.device;

import android.content.Context;
import android.net.Uri;

import com.het.basic.base.RxManage;
import com.het.basic.model.DeviceBean;
import com.het.basic.utils.ToastUtil;
import com.het.h5.sdk.event.HetH5PlugEvent;
import com.het.open.lib.api.HetH5Api;
import com.het.open.lib.manager.base.BaseHtmlFiveFactory;
import com.het.open.lib.model.DeviceModel;
import com.het.sdk.demo.base.BaseHetPresenter;
import com.het.sdk.demo.base.BaseHetView;

import java.io.File;

/**
 * Created by Administrator on 2017-10-23.
 */

public class DeviceH5Persenter extends BaseHetPresenter<BaseHetView> {
    private static String url = "file:///android_asset/household/virtualScene1/page/index.html";

    private static void loadVirtualUrlFromDeviceBean(BaseHtmlFiveFactory htmlFiveManager) {
        htmlFiveManager.loadUrl(url);
    }

    /**
     * 真实设备h5页面的url
     *
     * @param context 上下文
     * @param model   设备对象
     */
    public void loadUrlFromDeviceBean(Context context, DeviceModel model, BaseHtmlFiveFactory htmlFiveManager) {
        unRegisterUrl(model);
        RxManage.getInstance().register(HetH5PlugEvent.HET_EVENT_H5_PLUG_GET_LOCAL_URL_SUCCESS + model.getProductId(), o -> {
            if (htmlFiveManager != null) {
                String localPath = (String) o;
                String path = Uri.fromFile(new File(localPath)).toString();
                path += "/index.html";
                htmlFiveManager.loadUrl(path);
            }
        });

        RxManage.getInstance().register(HetH5PlugEvent.HET_EVENT_H5_PLUG_GET_LOCAL_URL_FAILED + model.getProductId(), o -> {
            ToastUtil.showToast(context, "h5页面下载失败");
        });
        DeviceBean deviceBean = new DeviceBean();
        deviceBean.setDeviceId(model.getDeviceId());
        deviceBean.setDeviceTypeId(model.getDeviceTypeId());
        deviceBean.setDeviceSubtypeId(model.getDeviceSubtypeId());
        deviceBean.setDeviceName(model.getDeviceName());
        deviceBean.setProductId(model.getProductId());
        HetH5Api.getInstance().getH5ControlPlug(context, deviceBean);
        // HetH5SdkManager.getInstance().getH5Plug(context, deviceBean);
    }

    public static void unRegisterUrl(DeviceModel model) {
        RxManage.getInstance().unregister(HetH5PlugEvent.HET_EVENT_H5_PLUG_GET_LOCAL_URL_SUCCESS + model.getProductId());
    }
}
