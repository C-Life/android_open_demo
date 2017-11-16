package com.het.sdk.demo.ui.activity.tablayout;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by mindray on 2017/9/4.
 */

public class TopViewPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private List<SimpleFragment> mFragments;

    public TopViewPagerAdapter(FragmentManager fm,Context context,List<SimpleFragment> fragments) {
        super(fm);
        mContext = context;
        mFragments = fragments;
    }
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
