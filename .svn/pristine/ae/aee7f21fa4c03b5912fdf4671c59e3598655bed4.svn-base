package com.het.sdk.demo.ui.fragment;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.het.basic.utils.GsonUtil;
import com.het.open.lib.api.HetDeviceListApi;
import com.het.open.lib.api.HetSdk;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.DeviceModel;
import com.het.sdk.demo.R;
import com.het.sdk.demo.adapter.AdapterDeviceList;
import com.het.sdk.demo.utils.HandlerUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 授权fragment
 * Created by xuchao on 2016/6/30.
 */

public class DeviceFragment extends BaseFragment {


    private View rootView;
    private TextView tvwTip;
    private List<DeviceModel> deviceModels = new ArrayList<>();
    private final int INIT_DATA = 0x01;
    private final int UPDATE_ADAPTER = 0x02;
    private AdapterDeviceList mAdapter;
    private ListView mListView;
    private boolean firstFlag=false;


    private HandlerUtil.MessageListener mMessageListener = new HandlerUtil.MessageListener() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case INIT_DATA:
                    initData();
                    break;
                case UPDATE_ADAPTER:
                    if (mListView!=null){
                        mListView.setVisibility(View.VISIBLE);
                    }
                    if (tvwTip!=null){
                        tvwTip.setVisibility(View.GONE);
                    }
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }

        }
    };

    private HandlerUtil.StaticHandler mStableHandler = new HandlerUtil.StaticHandler(
            mMessageListener);


    private void initData() {
        if (!HetSdk.getInstance().isAuthLogin()) {
            showTipText("授权登录后查看绑定设备信息");
            return;
        }
        HetDeviceListApi.getInstance().getBindList(new IHetCallback() {

            @Override
            public void onSuccess(int code,String s) {
                if (code==0){
                    if (!TextUtils.isEmpty(s)){
                        Type type = new TypeToken<List<DeviceModel>>() {
                        }.getType();
                        List<DeviceModel> models = GsonUtil.getInstance().getGson().fromJson(s, type);
                        if (models != null && models.size() > 0) {
                            deviceModels.clear();
                            deviceModels.addAll(models);
                            sendMsg(UPDATE_ADAPTER);
                        } else {
                            showTipText("未绑定任何设备");
                        }
                    }else {
                        showTipText("未绑定任何设备");
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(msg);
            }
        });
    }

    private void showTipText(String text){
        if (mListView!=null){
            mListView.setVisibility(View.GONE);
        }
        tvwTip.setText(text);
        tvwTip.setVisibility(View.VISIBLE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_device, container,
                    false);
            initView(rootView);
        }

        return rootView;
    }

    private void initView(View rootView) {
        mListView = (ListView) rootView.findViewById(R.id.device_list);
        if (!firstFlag){
            sendMsg(INIT_DATA);
            firstFlag=true;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        sendMsg(INIT_DATA);
    }



    private void sendMsg(int value) {
        if (mStableHandler != null) {
            mStableHandler.obtainMessage(value).sendToTarget();
        }

    }
}
