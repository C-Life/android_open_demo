package com.het.sdk.demo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.het.basic.base.util.TUtil;
import com.het.basic.utils.StringUtils;
import com.het.basic.utils.ToastUtil;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-08-23.
 * BaseHetFragment Fragment的基类
 * <p>
 * getLayoutId 绑定视图   initView  初始化页面
 * BaseHetPresenter数据接口  数据层----操作数据  子类继承,具体实现
 * BaseHetView     UI接口    视图层----刷新界面  子类继承,刷新界面
 */

public abstract class BaseHetFragment<P extends BaseHetPresenter> extends Fragment implements BaseHetView {

    public View rootView;
    protected Context mContext;
    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        mContext = getActivity();
        rootView = inflater.inflate(layoutId, container, false);
        ButterKnife.bind(this, rootView);//绑定framgent
        rootView.setClickable(true);
        mPresenter = TUtil.getT(this, 0);
        if (mPresenter != null) {
            mPresenter.setVM(getActivity(), this);
            mPresenter.setVM(this);
        }
        initView(savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);//解绑
    }

    public abstract int getLayoutId();

    public abstract void initView(Bundle savedInstanceState);

    public void showToast(String msg) {
        ToastUtil.showToast(mContext, !StringUtils.isNull(msg) ? msg : "提示语不能为空");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        super.onHiddenChanged(hidden);

    }
}
