package com.het.sdk.demo.ui.fragment.device;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.het.basic.base.RxManage;
import com.het.basic.model.DeviceBean;
import com.het.basic.utils.GsonUtil;
import com.het.h5.sdk.bean.H5PackParamBean;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.DeviceModel;
import com.het.recyclerview.ProgressStyle;
import com.het.recyclerview.XRecyclerView;
import com.het.recyclerview.swipemenu.SwipeMenuRecyclerView;
import com.het.sdk.demo.R;
import com.het.sdk.demo.adapter.AdapterDeviceList;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.base.BaseHetFragment;
import com.het.sdk.demo.event.DeviceStatusEvent;
import com.het.sdk.demo.event.HetShareEvent;
import com.het.sdk.demo.ui.activity.MainActivity;
import com.het.sdk.demo.ui.activity.device.ControlLedActivity;
import com.het.sdk.demo.ui.activity.h5control.H5ComBle3AControlActivity;
import com.het.sdk.demo.ui.activity.h5control.H5ComNbControlActivity;
import com.het.sdk.demo.ui.activity.h5control.H5ComWifiControlActivity;
import com.het.sdk.demo.ui.activity.sidebarlayout.SidebarMainActivity;
import com.het.sdk.demo.utils.Constants;
import com.het.sdk.demo.utils.MacIMEIBindHelper;
import com.het.sdk.demo.widget.MultipleStatusView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017-09-06.
 */

public class DeviceListFragment extends BaseHetFragment<LoginPresenter> implements DeviceListHetView, XRecyclerView.LoadingListener {
    @Bind(R.id.device_list)
    SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    @Bind(R.id.main_multiplestatusview)
    MultipleStatusView mMultipleStatusView;


    public static DeviceListFragment newInstance() {
        DeviceListFragment f = new DeviceListFragment();
        return f;
    }

    private List<DeviceModel> deviceModels = new ArrayList<>();
    private AdapterDeviceList mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.frgment_device_list;
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(1);
        this.mSwipeMenuRecyclerView.setLayoutManager(layoutManager);
        this.mSwipeMenuRecyclerView.setPullRefreshEnabled(true);
        this.mSwipeMenuRecyclerView.setLoadingListener(this);
        this.mSwipeMenuRecyclerView.setLoadingMoreEnabled(false);
        this.mSwipeMenuRecyclerView.setSwipeDirection(1);
        this.mSwipeMenuRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
        this.mSwipeMenuRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        this.mSwipeMenuRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);

        mAdapter = new AdapterDeviceList(mContext, R.layout.adapter_device_list_item);
        mSwipeMenuRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((view, o, i) -> {
            DeviceModel deviceModel = (DeviceModel) o;
            Bundle bundle = new Bundle();
            bundle.putSerializable("DeviceModel", (DeviceModel) o);
            DeviceBean deviceBean = new DeviceBean();
            deviceBean.setDeviceId(deviceModel.getDeviceId());
            deviceBean.setProductIcon(deviceModel.getProductIcon());
            deviceBean.setProductCode(deviceModel.getProductCode());
            deviceBean.setDeviceName(deviceModel.getDeviceName());
            deviceBean.setMacAddress(deviceModel.getMacAddress());
            deviceBean.setProductId(deviceModel.getProductId());
            deviceBean.setUserKey(deviceModel.getUserKey());
            deviceBean.setShare(deviceModel.getShare());

            if (((DeviceModel) o).getModuleType() == 2) {//蓝牙控制
                H5PackParamBean h5PackParamBean=new H5PackParamBean();
                h5PackParamBean.setDeviceBean(deviceBean);
                H5ComBle3AControlActivity.startH5Ble3AControlActivity(mContext,h5PackParamBean);
            } else if (deviceModel.getDeviceTypeId() == 14 && deviceModel.getDeviceSubtypeId() == 3) {//WIFI -- 舒眠灯原生控制
                //原生 控制
                ((BaseHetActivity) getActivity()).jumpToTarget(ControlLedActivity.class, bundle);
            } else if (deviceModel.getModuleType() == 4 && deviceModel.getModuleId() == 82) {//NB
                H5PackParamBean h5PackParamBean=new H5PackParamBean();
                h5PackParamBean.setDeviceBean(deviceBean);
                H5ComNbControlActivity.startH5ComNbControlActivity(getActivity(), h5PackParamBean);
            } else {//WIFI -- H5控制
                H5PackParamBean h5PackParamBean=new H5PackParamBean();
                h5PackParamBean.setDeviceBean(deviceBean);
                H5ComWifiControlActivity.startH5ComWifiControlActivity(getActivity(), h5PackParamBean);
            }

        });

        mAdapter.setISwipeMenuClickListener((var1, var2) -> {
            mPresenter.delDevice(deviceModels.get(var2), new IHetCallback() {
                @Override
                public void onSuccess(int code, String msg) {
                    //解绑成功
                    deviceModels.remove(var2);
                    mAdapter.setListAll(deviceModels);
                    if (deviceModels.size() == 0) {
                        showTipText("赶紧去添加设备吧!");
                    }
                }

                @Override
                public void onFailed(int code, String msg) {
                    //解绑失败
                }
            });
        });
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initRecyclerView();
        mMultipleStatusView.setOnRetryClickListener(onRetryClickListener);
        mMultipleStatusView.setOnRetryClickErrListener(onRetryClickErrListener);
        initRx();
        mPresenter.getDevicelist();

        //设备扫描gprs设备  绑定成功
        MacIMEIBindHelper.getInstance().setOnBindListener(deviceBean -> mPresenter.getDevicelist());
    }

    public void getDeviveList() {
        mPresenter.getDevicelist();
    }

    private void initRx() {
        //设备绑定成功  刷新列表
        RxManage.getInstance().register(Constants.DEVICE_BIND, o -> {
            mPresenter.getDevicelist();
        });

        //设备分享成功  刷新列表
        RxManage.getInstance().register(HetShareEvent.HET_EVENT_MAIN_SHARE_SUCCEE, o -> {
            mPresenter.getDevicelist();
            ((SidebarMainActivity) getActivity()).startMainActivity();
        });

        RxManage.getInstance().register(DeviceStatusEvent.DEVICE_NOT_EXIST, o -> {
            //设备不存在
            startMainActivity();
            mPresenter.getDevicelist();
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }


    private final View.OnClickListener onRetryClickListener = v -> {
        mPresenter.getDevicelist();
    };

    private final View.OnClickListener onRetryClickErrListener = v -> {
        mPresenter.getDevicelist();
    };

    @Override
    public void getDeviceList(String list) {
        mSwipeMenuRecyclerView.refreshComplete();
        Type type = new TypeToken<List<DeviceModel>>() {
        }.getType();
        List<DeviceModel> models = GsonUtil.getInstance().toObject(list, type);
        if (models != null && models.size() > 0) {
            deviceModels.clear();
            deviceModels.addAll(models);
            mAdapter.setListAll(deviceModels);
            if (mMultipleStatusView != null) {
                mMultipleStatusView.showContent();
            }
        } else {
            showTipText("赶紧去添加设备吧!");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterRx();
    }

    private void unregisterRx() {
        RxManage.getInstance().unregister(Constants.DEVICE_BIND);
        RxManage.getInstance().unregister(HetShareEvent.HET_EVENT_MAIN_SHARE_SUCCEE);
        RxManage.getInstance().unregister(DeviceStatusEvent.DEVICE_NOT_EXIST);
    }

    @Override
    public void showTipText(String text) {
        mSwipeMenuRecyclerView.refreshComplete();
        mMultipleStatusView.showError();
        mMultipleStatusView.setErrorText(text);
    }


    @Override
    public void onRefresh() {
        mPresenter.getDevicelist();
    }

    @Override
    public void onLoadMore() {

    }
}
