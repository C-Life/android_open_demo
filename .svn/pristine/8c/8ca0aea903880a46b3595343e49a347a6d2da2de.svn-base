package com.het.sdk.demo.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.het.basic.base.HetBaseActivity;
import com.het.basic.base.util.TUtil;
import com.het.basic.utils.StringUtils;
import com.het.basic.utils.ToastUtil;
import com.het.sdk.demo.R;
import com.het.sdk.demo.manager.BuildManager;
import com.het.sdk.demo.widget.CommonLoadingDialog;
import com.het.ui.sdk.CommonTopBar;

import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017-08-23.
 * BaseHetActivity Avtivity的基类
 * <p>
 * getLayoutId 绑定视图   initView  初始化页面
 * initTopBarView 初始化标题栏
 * BaseHetPresenter数据接口  数据层----操作数据   子类继承,具体实现
 * BaseHetView     UI接口    视图层----刷新界面   子类继承,刷新界面
 */


public abstract class BaseHetActivity<P extends BaseHetPresenter> extends HetBaseActivity implements BaseHetView {

    private CommonLoadingDialog mDialog;
    protected View mainView;
    protected Context mContext;
    protected P mPresenter;
    protected FrameLayout mBaseContent;
    /**
     * 标题栏
     */
    protected CommonTopBar mTitleView;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        super.onCreate(savedInstanceState);
        mContext = this;
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.activity_base, null);
        mBaseContent = (FrameLayout) view.findViewById(R.id.base_content_view);
        mainView = LayoutInflater.from(this).inflate(layoutId, null);
        mBaseContent.addView(mainView);
        setContentView(view);
        ButterKnife.bind(this);
        mPresenter = TUtil.getT(this, 0);
        if (mPresenter != null) {
            mPresenter.setVM(this);
            mPresenter.setVM(this, this);
        }
        mTitleView = (CommonTopBar) view.findViewById(R.id.topbar);
        initDefTopBarView();
        initTopBarView();
        BuildManager.setStatusTrans(this, 2, mTitleView, view);
        initView(savedInstanceState);
        initData();
    }

    /**
     * 默认的title 栏
     */
    private void initDefTopBarView() {
        mTitleView.setTitle(getString(R.string.app_name));
        mTitleView.setUpNavigateMode();
        mTitleView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.tab_text_color_press));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initTopBarView();

    public CommonTopBar getTitleView() {
        return mTitleView;
    }

    public void showToast(String msg) {
        ToastUtil.showToast(mContext, !StringUtils.isNull(msg) ? msg : "提示语不能为空");
    }

    public void showDialog(String text) {
        if (mDialog == null) {
            mDialog = new CommonLoadingDialog(this);
            mDialog.setText(text);
            mDialog.show();
        }
    }


    public void hideDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

}