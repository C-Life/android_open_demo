package com.het.sdk.demo.adapter;

import android.content.Context;

import com.het.bind.logic.bean.device.DeviceProductBean;
import com.het.recyclerview.recycler.HelperRecyclerViewAdapter;
import com.het.recyclerview.recycler.HelperRecyclerViewHolder;
import com.het.sdk.demo.R;

/**
 * Created by Administrator on 2017-09-07.
 */

public class DeviceScanAdpter extends HelperRecyclerViewAdapter<DeviceProductBean> {


    public DeviceScanAdpter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, DeviceProductBean item) {
        viewHolder.setText(R.id.bind_bindding_text_name, item.getProductName());
        viewHolder.setText(R.id.bind_bindding_text_mode, "mac:" + item.getDeviceMacAddr());
        viewHolder.setImageUrl(R.id.bind_bindding_img_icon, item.getProductIcon());

    }


}