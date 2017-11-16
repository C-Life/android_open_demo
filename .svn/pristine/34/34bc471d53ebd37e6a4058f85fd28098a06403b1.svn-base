package com.het.sdk.demo.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.het.sdk.demo.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-09-07.
 */

public class BindProgressBar extends RelativeLayout {

    private Context mContext;
    private View root;
    private ImageView iv_bai, iv_shi, iv_ge;
    private static Animation rotationOuter;
    private Drawable[] digital = new Drawable[10];

    public BindProgressBar(Context context) {
        this(context, null);
    }

    public BindProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BindProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        ArrayList<Drawable> drawables = new ArrayList();
        Drawable drawable = mContext.getResources().getDrawable(R.mipmap.bind_number_0);
        drawables.add(drawable);
        drawable = mContext.getResources().getDrawable(R.mipmap.bind_number_1);
        drawables.add(drawable);
        drawable = mContext.getResources().getDrawable(R.mipmap.bind_number_2);
        drawables.add(drawable);
        drawable = mContext.getResources().getDrawable(R.mipmap.bind_number_3);
        drawables.add(drawable);
        drawable = mContext.getResources().getDrawable(R.mipmap.bind_number_4);
        drawables.add(drawable);
        drawable = mContext.getResources().getDrawable(R.mipmap.bind_number_5);
        drawables.add(drawable);
        drawable = mContext.getResources().getDrawable(R.mipmap.bind_number_6);
        drawables.add(drawable);
        drawable = mContext.getResources().getDrawable(R.mipmap.bind_number_7);
        drawables.add(drawable);
        drawable = mContext.getResources().getDrawable(R.mipmap.bind_number_8);
        drawables.add(drawable);
        drawable = mContext.getResources().getDrawable(R.mipmap.bind_number_9);
        drawables.add(drawable);
        try {
            drawables.toArray(digital);
        } catch (Exception e) {
            e.printStackTrace();
        }
        root = LayoutInflater.from(getContext()).inflate(R.layout.bind_scan_progress_layout, (ViewGroup) getParent());
        ImageView iv_scan_outer = (ImageView) root.findViewById(R.id.iv_scan_outer);
        iv_bai = (ImageView) root.findViewById(R.id.iv_bai);
        iv_shi = (ImageView) root.findViewById(R.id.iv_shi);
        iv_ge = (ImageView) root.findViewById(R.id.iv_ge);
        if (rotationOuter == null) {
            rotationOuter = AnimationUtils.loadAnimation(mContext, R.anim.scan_outer_progress);
            rotationOuter.setInterpolator(new LinearInterpolator());
        }
        iv_scan_outer.startAnimation(rotationOuter);
        addView(root);
    }
    public void setProgress(int progress) {
        if (progress < 0 || progress > 100)
            return;
        if (progress < 10) {
            iv_bai.setVisibility(GONE);
            iv_shi.setVisibility(GONE);
            iv_ge.setImageDrawable(digital[progress]);
        } else if (progress < 100) {
            iv_bai.setVisibility(GONE);
            iv_shi.setVisibility(VISIBLE);
            iv_shi.setImageDrawable(digital[progress / 10]);
            iv_ge.setImageDrawable(digital[progress % 10]);
        } else {
            iv_bai.setVisibility(VISIBLE);
            iv_bai.setImageDrawable(digital[1]);
            iv_shi.setImageDrawable(digital[0]);
            iv_ge.setImageDrawable(digital[0]);
        }
    }
}