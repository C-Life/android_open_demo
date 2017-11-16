package com.het.sdk.demo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mindray on 2017/9/5.
 */

public abstract class BaseCommAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mListViewDatas;

    /**
     * Constructor function
     */
    public BaseCommAdapter(Context context){
        this.mContext = context;
        if (mListViewDatas == null) {
            mListViewDatas = new ArrayList<>();
        }
    }
    /**
     * Get all data from ListView
     */
    public List<T> getAllListDatas() {
        return mListViewDatas;
    }

    /**
     * remove position data
     *
     * @param position
     */
    public T removeData(int position) {
        if (position < mListViewDatas.size()) {
            T t = mListViewDatas.remove(position);
            notifyDataSetChanged();
            return t;
        }
        return null;
    }
    /**
     * add data to list tail
     *
     * @param listDatas
     */

    public void addListDatas2Footer(List<T> listDatas) {
        if (listDatas != null) {
            mListViewDatas.addAll(listDatas);
            notifyDataSetChanged();
        }
    }

    /**
     * add data to list header
     *
     * @param listDatas
     */
    public void addListDatas2Header(List<T> listDatas) {
        if (listDatas != null) {
            mListViewDatas.addAll(0, listDatas);
            notifyDataSetChanged();
        }
    }

    /**
     * reset list data
     *
     * @param listDatas
     */
    public void setListDatas(List<T> listDatas) {
        mListViewDatas.clear();
        if (listDatas != null) {
            mListViewDatas.addAll(listDatas);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mListViewDatas == null ? 0 : mListViewDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mListViewDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder(mContext, parent, getLVItemViewLayoutID());
            convertView = viewHolder.getConvertView();
        } else viewHolder = (ViewHolder) convertView.getTag();
        T t = getItem(position);
        bindView(position, t, viewHolder);
        return convertView;
    }

    /**
     * 获取listview中的item布局
     *
     * @return
     */
    public abstract int getLVItemViewLayoutID();

    /**
     * 将业务数据绑定到具体的 tag上
     *
     * @param position
     * @param data
     * @param viewHolder
     */
    public abstract void bindView(int position, T data, ViewHolder viewHolder);
}
