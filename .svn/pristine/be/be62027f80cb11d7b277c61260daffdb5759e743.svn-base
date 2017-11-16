package com.het.sdk.demo.manager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.het.recyclerview.ProgressStyle;
import com.het.recyclerview.XRecyclerView;
import com.het.sdk.demo.R;

/**
 * Created by Administrator on 2017-09-08.
 */

public class RecyclerViewManager {

    private XRecyclerView mXRecyclerView;
    private RecyclerView mRecyclerView;

    public RecyclerViewManager(){
    }

    public XRecyclerView getXLinearInstance(Context context, XRecyclerView xRecyclerView,
                                            boolean pullRefreshEnabled, boolean loadingMoreEnabled){
        if(mXRecyclerView == null){
            synchronized (RecyclerViewManager.class) {
                if(mXRecyclerView == null){
                    mXRecyclerView = xRecyclerView;
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    mXRecyclerView.setLayoutManager(layoutManager);
                    mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
                    mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
                    mXRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
                    mXRecyclerView.setPullRefreshEnabled(pullRefreshEnabled);
                    mXRecyclerView.setLoadingMoreEnabled(loadingMoreEnabled);
                }
            }
        }
        return mXRecyclerView;
    }

    public XRecyclerView getXGridInstance(Context context, XRecyclerView xRecyclerView, int spanCount,
                                          boolean pullRefreshEnabled, boolean loadingMoreEnabled){
        if(mXRecyclerView == null){
            synchronized (RecyclerViewManager.class) {
                if(mXRecyclerView == null){
                    mXRecyclerView = xRecyclerView;
                    GridLayoutManager layoutManager = new GridLayoutManager(context, spanCount);
                    layoutManager.setOrientation(GridLayoutManager.VERTICAL);
                    layoutManager.setSmoothScrollbarEnabled(true);
                    mXRecyclerView.setLayoutManager(layoutManager);
                    mXRecyclerView.setNestedScrollingEnabled(false);
                    mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
                    mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
                    mXRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
                    mXRecyclerView.setPullRefreshEnabled(pullRefreshEnabled);
                    mXRecyclerView.setLoadingMoreEnabled(loadingMoreEnabled);
                }
            }
        }
        return mXRecyclerView;
    }

    public RecyclerView getHorizontalLinear(Context context, RecyclerView recyclerView){
        if(mRecyclerView == null){
            synchronized (RecyclerViewManager.class) {
                if(mRecyclerView == null){
                    mRecyclerView = recyclerView;
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    mRecyclerView.setLayoutManager(layoutManager);
                }
            }
        }
        return mRecyclerView;
    }

    public RecyclerView getVerticalLinear(Context context, RecyclerView recyclerView){
        if(mRecyclerView == null){
            synchronized (RecyclerViewManager.class) {
                if(mRecyclerView == null){
                    mRecyclerView = recyclerView;
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    mRecyclerView.setLayoutManager(layoutManager);
                }
            }
        }
        return mRecyclerView;
    }

}
