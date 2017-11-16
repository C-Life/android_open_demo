package com.het.sdk.demo.manager;

import android.app.Activity;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.het.basic.utils.ScreenUtils;
import com.het.sdk.demo.utils.UIJsonConfig;

public class BuildManager {
    //版本是19之后
    public static boolean isSDKLater19() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT);
    }

    //版本是18之后
    public static boolean isSDKLater18() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2);
    }

    /**
     * @param activity   上下文
     * @param parentType 1 view的父容器是Linearlayout; 2 view的父容器是RelativeLayout
     * @param view       上下文
     */
    public static void setStatusTrans(Activity activity, int parentType, View view) {
        if (isSDKLater19()) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
            //设置沉浸模式时,页面会填充状态栏位置,设置合适的margin值使页面从状态栏下开始布局
            if (parentType == 1) {
                //LinearLayout
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                params.setMargins(0, ScreenUtils.getStatusBarHeight(activity), 0, 0);
            } else if (parentType == 2) {
                //RelativeLayout
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
                params.setMargins(0, ScreenUtils.getStatusBarHeight(activity), 0, 0);
            }
        }
    }

    public static void setStatusTrans(Activity activity, int parentType, View view, View rootView) {
        if (isSDKLater19()) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
            if (rootView != null) {
                rootView.setBackground(UIJsonConfig.getInstance(activity).setNavBackground_color());
            }
            //设置沉浸模式时,页面会填充状态栏位置,设置合适的margin值使页面从状态栏下开始布局
            if (parentType == 1) {
                //LinearLayout
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                params.setMargins(0, ScreenUtils.getStatusBarHeight(activity), 0, 0);
            } else if (parentType == 2) {
                //RelativeLayout
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
                params.setMargins(0, ScreenUtils.getStatusBarHeight(activity), 0, 0);
            }
        }
    }

    public static void setStatusTrans(Activity activity) {
        if (isSDKLater19()) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏

            ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
            View statusBarView = new View(window.getContext());
            int statusBarHeight = ScreenUtils.getStatusBarHeight(window.getContext());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
            params.gravity = Gravity.TOP;
            statusBarView.setLayoutParams(params);
            statusBarView.setBackground(UIJsonConfig.getInstance(activity).setNavBackground_color());
            decorViewGroup.addView(statusBarView);
        }
    }
}
