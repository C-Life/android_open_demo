package com.het.sdk.demo.ui.activity.tablayout;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.het.sdk.demo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mindray on 2017/9/4.
 */

public class TabMainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<String> mTitles = Arrays.asList("Fragment-->微信","Fragment-->通讯录","Fragment-->发现","Fragment-->我");

    private List<SimpleFragment> mFragments = new ArrayList<>();
    private FragmentPagerAdapter mPagerAdapter;

    private TabIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);
        initViews();
        initDatas();
        initEvent();
    }
    private void initViews() {
        mViewPager = (ViewPager)findViewById(R.id.id_viewPager);
        mIndicator = (TabIndicator)findViewById(R.id.id_bottom_indicator);
    }

    private void initDatas() {
        for(String title : mTitles) {
            mFragments.add(SimpleFragment.newInstance(title));
        }
        mPagerAdapter = new TopViewPagerAdapter(getSupportFragmentManager(),this,mFragments);
        mViewPager.setAdapter(mPagerAdapter);
        mIndicator.setViewPager(mViewPager);
    }

    private void initEvent(){

    }
}
