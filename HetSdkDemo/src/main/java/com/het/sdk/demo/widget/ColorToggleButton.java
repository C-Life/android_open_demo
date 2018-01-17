package com.het.sdk.demo.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.het.sdk.demo.R;

/**
 * Created by xuchao on 2016/5/17.
 */
public class ColorToggleButton extends RelativeLayout {
    public static final int OPEN = 1;
    public static final int CLOSE = -1;
    public static final int RUN = 0;
    private static final ClipData EMPTY_CLIP_DATA = ClipData.newPlainText("Toggle", "Toggle");
    private static final String TOGGLE_BUTTON = "COLORBUTTON";
    private static final long DURATION = 300L;
    private ColorChangeBgView mBgView;
    private AnimBall mAnimBall;
    private ColorToggleButton.OnCheckedChangeListener mOnCheckedChangeListener;
    private int mState = -1;
    private int mMeasureWidth;
    private int mMeasureHeight;
    private int mBallWidth;
    private int mBallColor;
    private int mBgSelColor;
    private int mBgUnSelColor;
    private int mBallStartLeft;
    private int mBallStopLeft;
    private int mBallAnimStartLeft;
    private int mBallAnimStopLeft;
    private int mBallScrollMaxX;
    private float mPaddingFactor = 0.1F;
    private int mFirstScreenX;
    private int[] mScreenLocation = new int[2];

    public ColorToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorToggleButton);

        this.mBallColor = typedArray.getColor(R.styleable.ColorToggleButton_toggole_ball_color, -1);
        this.mBgSelColor = typedArray.getColor(R.styleable.ColorToggleButton_toggole_bg_color_sel, Color.parseColor("#FF2ED785"));
        this.mBgUnSelColor = typedArray.getColor(R.styleable.ColorToggleButton_toggole_bg_color_unsel, -7829368);
        typedArray.recycle();
        this.mBgView = new ColorChangeBgView(context);
        this.mBgView.initColor(this.mBgUnSelColor, this.mBgSelColor);
        this.mBgView.setLayoutParams(new LayoutParams(-1, -1));
        this.addView(this.mBgView);
        this.mAnimBall = new AnimBall(context);
        this.mAnimBall.setLayoutParams(new LayoutParams(-2, -2));
        this.mAnimBall.setColor(this.mBallColor);
        this.addView(this.mAnimBall);
        this.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(ColorToggleButton.this.mState != 0) {
                    if(ColorToggleButton.this.mState == 1) {
                        ColorToggleButton.this.close();
                    } else {
                        ColorToggleButton.this.open();
                    }

                }
            }
        });
        this.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                v.startDrag(ColorToggleButton.EMPTY_CLIP_DATA, new DragShadowBuilder(), "COLORBUTTON", 0);
                return false;
            }
        });
    }

    public void initState(int state) {
        this.mState = state;
        this.mBgView.setColorFlactor(this.mState == 1?1.0F:0.0F);
        this.requestLayout();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMs = MeasureSpec.makeMeasureSpec(this.getLayoutParams().width, 1073741824);
        int heightMs = MeasureSpec.makeMeasureSpec(this.getLayoutParams().height, 1073741824);

        for(int i = 0; i < this.getChildCount(); ++i) {
            View child = this.getChildAt(i);
            child.measure(widthMs, heightMs);
        }

        this.setMeasuredDimension(widthMs, heightMs);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.mMeasureWidth = this.getWidth();
        this.mMeasureHeight = this.getHeight();
        this.mBgView.layout(0, 0, this.mMeasureWidth, this.mMeasureHeight);
        this.mAnimBall.setBallRadius((int)((float)this.mMeasureHeight * (1.0F - 2.0F * this.mPaddingFactor) / 2.0F));
        this.mBallWidth = this.mAnimBall.getActualWidth();
        int padding = (this.mMeasureHeight - this.mAnimBall.getActualHeight()) / 2;
        this.mBallStartLeft = padding;
        this.mBallStopLeft = this.mMeasureWidth - padding - this.mBallWidth;
        this.mBallScrollMaxX = this.mMeasureWidth - 2 * padding - this.mBallWidth;
        if(this.mState == 1) {
            this.mAnimBall.layout(this.mBallStopLeft, padding, this.mBallStopLeft + this.mBallWidth, padding + this.mAnimBall.getActualHeight());
        } else if(this.mState == -1) {
            this.mAnimBall.layout(this.mBallStartLeft, padding, this.mBallStartLeft + this.mBallWidth, padding + this.mAnimBall.getActualHeight());
        }

        Log.e("mMeasureWidth ", this.mMeasureWidth + "");
        Log.e("mMeasureHeight ", this.mMeasureHeight + "");
        Log.e("mBallWidth", this.mBallWidth + "");
        Log.e(" mAnimBall", this.mAnimBall + "");
    }

    public void setOnCheckedChangeListener(ColorToggleButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    private void slideButton() {
        final int lastState = this.mState;
        Log.e("tag", "lastState:" + this.mState);
        ValueAnimator openAnimator = ValueAnimator.ofInt(new int[]{this.mBallAnimStartLeft, this.mBallAnimStopLeft});
        openAnimator.setDuration(300L);
        openAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int left = ((Integer)valueAnimator.getAnimatedValue()).intValue();
                ColorToggleButton.this.moveBall(left);
            }
        });
        openAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        openAnimator.addListener(new Animator.AnimatorListener() {
            public void onAnimationStart(Animator animator) {
                ColorToggleButton.this.mState = 0;
            }

            public void onAnimationEnd(Animator animator) {
                int state = ColorToggleButton.this.mBallAnimStopLeft == ColorToggleButton.this.mBallStartLeft?-1:1;
                ColorToggleButton.this.mState = state;
                if(lastState != ColorToggleButton.this.mState) {
                    if(ColorToggleButton.this.mOnCheckedChangeListener != null) {
                        ColorToggleButton.this.mOnCheckedChangeListener.onCheckedChange(ColorToggleButton.this, ColorToggleButton.this.mState == 1);
                    }

                    Log.e("tag", "state:" + state);
                }

            }

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }
        });
        openAnimator.start();
    }

    private void moveBall(int curLeft) {
        if(curLeft <= this.mBallStopLeft && curLeft >= this.mBallStartLeft) {
            float f = (float)(curLeft - this.mBallStartLeft) * 1.0F / (float)this.mBallScrollMaxX;
            this.mAnimBall.setAnimFactor(f);
            this.mAnimBall.layout(curLeft, this.mAnimBall.getTop(), curLeft + this.mAnimBall.getActualWidth(), this.mAnimBall.getBottom());
            this.mBgView.setColorFlactor(f);
        }
    }

    public boolean onDragEvent(DragEvent event) {
        switch(event.getAction()) {
            case 1:
                if(!"COLORBUTTON".equals(event.getLocalState())) {
                    return false;
                }

                this.mFirstScreenX = (int)event.getX();
                this.getLocationOnScreen(this.mScreenLocation);
                break;
            case 2:
                int curScreenX = (int)((float)this.mScreenLocation[0] + event.getX());
                int deltaX = curScreenX - this.mFirstScreenX;
                this.moveBall(this.mAnimBall.getLeft() + deltaX);
                this.mFirstScreenX = curScreenX;
            case 3:
            default:
                break;
            case 4:
                this.autoOpenOrClose();
        }

        return true;
    }

    private void autoOpenOrClose() {
        if(this.isNeedToClose()) {
            this.close();
        } else {
            this.open();
        }

    }

    private boolean isNeedToClose() {
        return (float)(this.mAnimBall.getLeft() - this.mBallStartLeft) < (float)this.mBallScrollMaxX * 1.0F / 2.0F;
    }

    public void open() {
        this.mBallAnimStartLeft = this.mAnimBall.getLeft();
        this.mBallAnimStopLeft = this.mBallStopLeft;
        this.slideButton();
    }

    public void close() {
        this.mBallAnimStartLeft = this.mAnimBall.getLeft();
        this.mBallAnimStopLeft = this.mBallStartLeft;
        this.slideButton();
    }

    public boolean isOpen() {
        return this.mState == 1;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChange(ColorToggleButton var1, boolean var2);
    }
}

