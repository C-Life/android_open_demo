package com.het.sdk.demo.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.het.basic.utils.StringUtils;
import com.het.sdk.demo.R;


/**
 * 一个方便在多种状态切换的view
 */
public class MultipleStatusView extends RelativeLayout {
    public static final int STATUS_CONTENT = 0x00;
    public static final int STATUS_LOADING = 0x01;
    public static final int STATUS_EMPTY = 0x02;
    public static final int STATUS_ERROR = 0x03;
    public static final int STATUS_NO_NETWORK = 0x04;

    private static final int NULL_RESOURCE_ID = -1;

    private View mEmptyView;
    private View mErrorView;
    private View mLoadingView;
    private View mNoNetworkView;
    private View mContentView;
    private View mEmptyRetryView;
    private View mErrorRetryView;
    private View mNoNetworkRetryView;
    private int mEmptyViewResId;
    private int mErrorViewResId;
    private int mLoadingViewResId;
    private int mNoNetworkViewResId;
    private int mContentViewResId;
    private int mViewStatus;

    private LayoutInflater mInflater;
    private OnClickListener mOnRetryClickListener;
    private OnClickListener mOnRetryClickErrListener;
    private final ViewGroup.LayoutParams mLayoutParams = new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    public MultipleStatusView(Context context) {
        this(context, null);
    }

    public MultipleStatusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultipleStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.MultipleStatusView, defStyleAttr, 0);

        mEmptyViewResId =
                a.getResourceId(R.styleable.MultipleStatusView_emptyView, R.layout.custom_empty_view);
        mErrorViewResId =
                a.getResourceId(R.styleable.MultipleStatusView_errorView, R.layout.custom_error_view);
        mLoadingViewResId =
                a.getResourceId(R.styleable.MultipleStatusView_loadingView, R.layout.custom_loading_view);
        mNoNetworkViewResId = a.getResourceId(R.styleable.MultipleStatusView_noNetworkView,
                R.layout.custom_no_network_view);
        mContentViewResId = a.getResourceId(R.styleable.MultipleStatusView_contentView,
                NULL_RESOURCE_ID);
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mInflater = LayoutInflater.from(getContext());
        showContent();
    }

    /**
     * 获取当前状态
     */
    public int getViewStatus() {
        return mViewStatus;
    }

    /**
     * 设置重试点击事件
     *
     * @param onRetryClickListener 重试点击事件
     */
    public void setOnRetryClickListener(OnClickListener onRetryClickListener) {
        this.mOnRetryClickListener = onRetryClickListener;
    }

    /**
     * 设置重试点击事件
     *
     * @param mOnRetryClickErrListener 重试点击事件
     */
    public void setOnRetryClickErrListener(OnClickListener mOnRetryClickErrListener) {
        this.mOnRetryClickErrListener = mOnRetryClickErrListener;
    }


    /**
     * 显示空视图
     */
    public final void showEmpty() {
        mViewStatus = STATUS_EMPTY;
        if (null == mEmptyView) {
            mEmptyView = mInflater.inflate(mEmptyViewResId, null);
            mEmptyRetryView = mEmptyView.findViewById(R.id.empty_retry_view);
            if (null != mOnRetryClickListener && null != mEmptyRetryView) {
                mEmptyRetryView.setOnClickListener(mOnRetryClickListener);
            }
            addView(mEmptyView, 0, mLayoutParams);
        }
        showViewByStatus(mViewStatus);
    }

    /**
     * 显示错误视图
     */
    public final void showError() {
        mViewStatus = STATUS_ERROR;
        if (null == mErrorView) {
            mErrorView = mInflater.inflate(mErrorViewResId, null);
            mErrorRetryView = mErrorView.findViewById(R.id.error_retry_view);
            if (null != mOnRetryClickErrListener && null != mErrorRetryView) {
                mErrorRetryView.setOnClickListener(mOnRetryClickErrListener);
            }
            addView(mErrorView, 0, mLayoutParams);
        }
        showViewByStatus(mViewStatus);
    }

    public final void setErrorText(String text) {
        TextView mErrorTextView;
        if (null != mErrorView) {
            mErrorTextView = (TextView) mErrorView.findViewById(R.id.error_view_tv);
            if (mErrorTextView != null && !StringUtils.isNull(text)) {
                mErrorTextView.setText(text);
            }
        }
    }

    /**
     * 显示加载中视图
     */
    public final void showLoading() {
        mViewStatus = STATUS_LOADING;
        if (null == mLoadingView) {
            mLoadingView = mInflater.inflate(mLoadingViewResId, null);
            addView(mLoadingView, 0, mLayoutParams);
        }
        showViewByStatus(mViewStatus);
    }

    /**
     * 显示无网络视图
     */
    public final void showNoNetwork() {
        mViewStatus = STATUS_NO_NETWORK;
        if (null == mNoNetworkView) {
            mNoNetworkView = mInflater.inflate(mNoNetworkViewResId, null);
            mNoNetworkRetryView = mNoNetworkView.findViewById(R.id.no_network_retry_view);
            if (null != mOnRetryClickListener && null != mNoNetworkRetryView) {
                mNoNetworkRetryView.setOnClickListener(mOnRetryClickListener);
            }
            addView(mNoNetworkView, 0, mLayoutParams);
        }
        showViewByStatus(mViewStatus);
    }

    /**
     * 显示内容视图
     */
    public final void showContent() {
        mViewStatus = STATUS_CONTENT;
        if (null == mContentView) {
            if (mContentViewResId != NULL_RESOURCE_ID) {
                mContentView = mInflater.inflate(mContentViewResId, null);
                addView(mContentView, 0, mLayoutParams);
            } else {
                mContentView = findViewById(R.id.content_view);
            }
        }
        showViewByStatus(mViewStatus);
    }

    private void showViewByStatus(int viewStatus) {
        if (null != mLoadingView) {
            mLoadingView.setVisibility(viewStatus == STATUS_LOADING ? View.VISIBLE : View.GONE);
        }
        if (null != mEmptyView) {
            mEmptyView.setVisibility(viewStatus == STATUS_EMPTY ? View.VISIBLE : View.GONE);
        }
        if (null != mErrorView) {
            mErrorView.setVisibility(viewStatus == STATUS_ERROR ? View.VISIBLE : View.GONE);
        }
        if (null != mNoNetworkView) {
            mNoNetworkView.setVisibility(viewStatus == STATUS_NO_NETWORK ? View.VISIBLE : View.GONE);
        }
        if (null != mContentView) {
            mContentView.setVisibility(viewStatus == STATUS_CONTENT ? View.VISIBLE : View.GONE);
        }
    }
}

