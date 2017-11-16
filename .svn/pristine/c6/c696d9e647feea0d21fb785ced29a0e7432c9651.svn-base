package com.het.sdk.demo.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mindray on 2017/9/5.
 */

public class ViewHolder {
    private SparseArray<View> mItemViews = new SparseArray<>();
    private View mConvertView;

    public ViewHolder(Context context, ViewGroup parent, int layoutId) {
        this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.mConvertView.setTag(this);
        this.mItemViews = new SparseArray<>();
    }

    /**
     * 获取listView中item对应的view
     *
     * @return
     */
    public View getConvertView() {
        return this.mConvertView;
    }

    /**
     * 查找View
     *
     * @param viewPosition
     * @return
     */
    public View getView(int viewPosition) {
        View view = mItemViews.get(viewPosition);
        if (view == null) {
            view = mConvertView.findViewById(viewPosition);
            mItemViews.put(viewPosition, view);
        }
        return view;
    }


}
