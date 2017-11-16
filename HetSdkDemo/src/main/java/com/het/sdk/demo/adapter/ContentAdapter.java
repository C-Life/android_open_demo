package com.het.sdk.demo.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.het.sdk.demo.R;
import com.het.sdk.demo.model.SidebarItemModel;

/**
 * Created by mindray on 2017/9/5.
 */

public class ContentAdapter extends BaseCommAdapter<SidebarItemModel>{
    /**
     * Constructor function
     *
     * @param context
     */
    public ContentAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLVItemViewLayoutID() {
        return R.layout.listview_item_layout;
    }

    @Override
    public void bindView(int position, SidebarItemModel data, ViewHolder viewHolder) {

        ImageView iv_item = (ImageView)viewHolder.getView(R.id.iv_item);
        iv_item.setBackgroundResource(data.getImageView());

        TextView tvName = (TextView) viewHolder.getView(R.id.tv_name);
        tvName.setText(data.getText());
    }

    /**
     * Constructor function
     *
     * @param context
     */

}
