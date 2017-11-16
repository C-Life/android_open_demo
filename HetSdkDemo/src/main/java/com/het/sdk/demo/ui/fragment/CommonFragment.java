package com.het.sdk.demo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.het.open.lib.api.HetHttpApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.sdk.demo.R;

/**
 * 通用fragment
 * Created by xuchao on 2016/6/30.
 */

public class CommonFragment extends BaseFragment {


    private View rootView;
    private Button btnGet;
    private Button btnPost;
    private Button btnScan;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_common, container,
                    false);
            initView(rootView);
        }

        return rootView;
    }

    private void initView(View rootView) {

        btnGet=(Button)rootView.findViewById(R.id.btn_http_get);
        btnGet.setOnClickListener(onClickListener);
        btnPost=(Button)rootView.findViewById(R.id.btn_http_post);
        btnPost.setOnClickListener(onClickListener);
        btnScan=(Button)rootView.findViewById(R.id.btn_scan);
        btnScan.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_http_get:
                    httpGet();
                    break;
                case R.id.btn_http_post:
                    httpPost();
                    break;
                case R.id.btn_scan:
                    break;

            }
        }


    };


    private void httpGet(){
        String url="v1/device/getBind";
        HetHttpApi.getInstance().hetGet(url, new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
               if (code==0){
                   showToast(msg);
               }
            }

            @Override
            public void onFailed(int code, String msg) {
                showToast(code+msg);
            }
        });
    }

    private void httpPost(){

//        TreeMap<String, String> params = new TreeMap<String, String>();
//        params.put(ComParamContant.Device.DEVICE_ID, "3BAAC9113A7ACBD9782463EBC52559CC");
//        String path = DeviceShareUrls.Share.getDeviceAuthUser;;
//        HetHttpApi.getInstance().hetPost(path, params,new IHetCallback() {
//            @Override
//            public void onSuccess(int code, String msg) {
//                if (code==0){
//                    showToast(msg);
//                }
//            }
//
//            @Override
//            public void onFailed(int code, String msg) {
//                showToast(code+msg);
//            }
//        });

    }
}
