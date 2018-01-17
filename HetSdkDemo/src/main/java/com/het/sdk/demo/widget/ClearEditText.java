package com.het.sdk.demo.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.het.sdk.demo.R;


/**
 * <p>描述：   带删除按钮的输入框
 * 作者：      houtrry <br>
 * 日期：      2017/1/17 19:11<br>
 * 版本：      $Rev$<br>
 */
public class ClearEditText extends AppCompatEditText implements View.OnFocusChangeListener,
        TextWatcher {

    private Drawable mClearDrawable;

    private boolean mHasFoucs;

    private boolean mShowClearIcon = true;

//    private int mPaddingRight;


    private boolean ismShowClearIcon() {
        return mShowClearIcon;
    }

    public void setmShowClearIcon(boolean mShowClearIcon) {
        this.mShowClearIcon = mShowClearIcon;
    }

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.common_selector_editext_delete, null);
        }
        if (mClearDrawable != null) {
            mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(),
                    mClearDrawable.getIntrinsicHeight());
        }
        int mPaddingRight = (int) (20 * getResources().getDisplayMetrics().density);
        setPadding(getPaddingLeft(), getPaddingTop(), mPaddingRight, getPaddingBottom());
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.mHasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    private void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        if (mHasFoucs) {
            if (!ismShowClearIcon()) {
                setClearIconVisible(false);
            } else {
                setClearIconVisible(s.length() > 0);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    private static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

}

