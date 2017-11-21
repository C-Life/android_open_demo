package com.het.sdk.demo.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.het.open.lib.model.share.DeviceAuthUserModel;
import com.het.recyclerview.recycler.HelperRecyclerViewAdapter;
import com.het.recyclerview.recycler.HelperRecyclerViewHolder;
import com.het.sdk.demo.R;

/**
 * Created by Administrator on 2017-10-10.
 */

public class ShareUserDevicesAdapter extends HelperRecyclerViewAdapter<DeviceAuthUserModel> {
    public ShareUserDevicesAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder holder, int i, DeviceAuthUserModel deviceAuthUserModel) {
        String imageUrl = deviceAuthUserModel.getAvatar();
        SimpleDraweeView faceIcon = holder.getView(R.id.iv_product_face);
        if (!TextUtils.isEmpty(imageUrl)) {
            Uri uri = Uri.parse(imageUrl);
            faceIcon.setImageURI(uri);
        } else {
            faceIcon.setImageDrawable(
                    ResourcesCompat.getDrawable(mContext.getResources(), R.mipmap.defsb, null));
        }

        holder.getView(R.id.btRemove).setOnClickListener(v -> {
            if (mDelBtnClickListener != null) {
                mDelBtnClickListener.onDelBtnCilck(v, i);
            }
        });

        String name = deviceAuthUserModel.getUserName();
        holder.setText(R.id.tvw_user_name, TextUtils.isEmpty(name) ? "" : name);
    }

    private AdapterDeviceList.ISwipeMenuClickListener mDelBtnClickListener;

    public void setISwipeMenuClickListener(AdapterDeviceList.ISwipeMenuClickListener mDelBtnClickListener) {
        this.mDelBtnClickListener = mDelBtnClickListener;
    }

    public interface ISwipeMenuClickListener {
        void onDelBtnCilck(View var1, int var2);
    }
}
