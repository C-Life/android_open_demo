package com.het.sdk.demo.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.het.open.lib.model.DeviceTypeModel;
import com.het.recyclerview.recycler.HelperRecyclerViewAdapter;
import com.het.recyclerview.recycler.HelperRecyclerViewHolder;
import com.het.sdk.demo.R;

/**
 * 设备大类列表适配器
 */
public class AdapterDeviceTypeList extends HelperRecyclerViewAdapter<DeviceTypeModel> {

    public AdapterDeviceTypeList(Context context, int... layoutIds) {
        super(context, layoutIds);
    }


    @Override
    protected void HelperBindData(HelperRecyclerViewHolder holder, int i, DeviceTypeModel deviceTypeModel) {
        TextView tvwMac = holder.getView(R.id.tvw_mac);
        TextView tvwName = holder.getView(R.id.tvw_device_name);
        SimpleDraweeView faceIcon = holder.getView(R.id.iv_product_face);

        String imageUrl = deviceTypeModel.getDeviceTypeIcon();
        if (!TextUtils.isEmpty(imageUrl)) {
            Uri uri = Uri.parse(imageUrl);
            faceIcon.setImageURI(uri);
        } else {
            faceIcon.setImageDrawable(
                    ResourcesCompat.getDrawable(mContext.getResources(), R.mipmap.defsb, null));
        }
        String name = deviceTypeModel.getDeviceTypeName();
        if (!TextUtils.isEmpty(name)) {
            tvwName.setText(name);
        } else {
            tvwName.setText("");
        }
        tvwMac.setVisibility(View.GONE);
    }


}
