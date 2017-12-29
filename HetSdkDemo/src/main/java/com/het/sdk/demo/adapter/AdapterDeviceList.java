package com.het.sdk.demo.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.het.open.lib.model.DeviceModel;
import com.het.recyclerview.recycler.HelperRecyclerViewAdapter;
import com.het.recyclerview.recycler.HelperRecyclerViewHolder;
import com.het.sdk.demo.R;

/**
 * 设备列表适配器
 */
public class AdapterDeviceList extends HelperRecyclerViewAdapter<DeviceModel> {

    public AdapterDeviceList(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder holder, int i, DeviceModel deviceModel) {
        String imageUrl = deviceModel.getProductIcon();
        SimpleDraweeView faceIcon = holder.getView(R.id.iv_product_face);
        if (!TextUtils.isEmpty(imageUrl)) {
            Uri uri = Uri.parse(imageUrl);
            faceIcon.setImageURI(uri);
        } else {
            faceIcon.setImageDrawable(
                    ResourcesCompat.getDrawable(mContext.getResources(), R.mipmap.defsb, null));
        }
        String name = deviceModel.getDeviceName();
        holder.setText(R.id.tvw_device_name, TextUtils.isEmpty(name) ? "" : name);
        String model = deviceModel.getModuleName();
        holder.setText(R.id.tvw_model, TextUtils.isEmpty(model) ? "" : model);

        int online = deviceModel.getOnlineStatus();
        if (online == 1) {
            holder.setText(R.id.device_statue, mContext.getString(R.string.device_statue_online));
            ((TextView) holder.getView(R.id.device_statue)).setTextColor(ContextCompat.getColor(mContext,R.color.color_68));
        } else {
            holder.setText(R.id.device_statue, mContext.getString(R.string.device_statue_offline));
            ((TextView) holder.getView(R.id.device_statue)).setTextColor(ContextCompat.getColor(mContext,R.color.color_c8));
        }

        holder.setOnClickListener(R.id.btRemove, v -> {
            if (mDelBtnClickListener != null)
                AdapterDeviceList.this.mDelBtnClickListener.onDelBtnCilck(v, i);
        });

    }


    private ISwipeMenuClickListener mDelBtnClickListener;

    public void setISwipeMenuClickListener(ISwipeMenuClickListener mDelBtnClickListener) {
        this.mDelBtnClickListener = mDelBtnClickListener;
    }

    public interface ISwipeMenuClickListener {
        void onDelBtnCilck(View var1, int var2);
    }
}
