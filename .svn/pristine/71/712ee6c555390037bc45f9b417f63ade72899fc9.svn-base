package com.het.sdk.demo.base;

import android.app.Activity;

/**
 * BasePresenter
 * 数据层的处理。
 * 关联到生命周期方法里面去
 * 抽象子类 自定义接口函数并继承BaseHetPresenter  具体的实体类实现抽象方法来处理数据层
 * mBaseHetView  方便页面实现刷新
 */
public abstract class BaseHetPresenter<T> {
    protected T mHetView;
    protected BaseHetView mBaseHetView;
    protected Activity activity;


    public void setVM(Activity activity, T v) {
        this.mHetView = v;
        this.activity = activity;
    }

    public void setVM(BaseHetView v) {
        this.mBaseHetView = v;
    }
}