package com.het.sdk.demo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.het.sdk.demo.R;
import com.het.sdk.demo.ui.activity.bind.DeviceTypeListActivity;

/**
 * 授权fragment
 * Created by xuchao on 2016/6/30.
 */

public class BindFragment extends BaseFragment {

    private View rootView;
    private Button btnBindDevice;
    private Button btnOkBind;
    private Button btnThirdBle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_bind, container,
                    false);
            initView(rootView);
        }
        return rootView;
    }

    private void initView(View rootView) {
        btnBindDevice=(Button)rootView.findViewById(R.id.btn_device_bind);
        btnBindDevice.setOnClickListener(onClickListener);
        btnOkBind =(Button)rootView.findViewById(R.id.btn_ok_bind);
        btnOkBind.setOnClickListener(onClickListener);
        btnThirdBle=(Button)rootView.findViewById(R.id.btn_third_ble_bind);
        btnThirdBle.setOnClickListener(onClickListener);

    }


    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_device_bind:
                    Intent intent = new Intent(getActivity(), DeviceTypeListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_ok_bind:

                    break;
                case R.id.btn_third_ble_bind:

                    break;

            }
        }


    };
}
