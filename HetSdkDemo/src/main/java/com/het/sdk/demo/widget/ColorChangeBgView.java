package com.het.sdk.demo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by xuchao on 2016/5/17.
 */
public class ColorChangeBgView extends View {
    private int mRadius;
    private int mLineLength;
    private Paint mPaint;
    private Path mPath;
    private RectF mLeftCircleRectF;
    private RectF mRightCircleRectF;
    private int mBgWith;
    private boolean mIsFirstMeasure = true;
    private int mStartColor;
    private int mStopColor;
    private int mCurBgColor;
    private int mStartColorRed;
    private int mStartColorGreen;
    private int mStartColorBlue;
    private int mStopColorRed;
    private int mStopColorGreen;
    private int mStopColorBlue;
    private int mDeltaColorRed;
    private int mDeltaColorGreen;
    private int mDeltaColorBlue;

    public ColorChangeBgView(Context context) {
        super(context);
        this.init();
    }

    public ColorChangeBgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    private void init() {
        this.mPaint = new Paint(1);
        this.mPath = new Path();
        this.mLeftCircleRectF = new RectF(0.0F, 0.0F, 0.0F, 0.0F);
        this.mRightCircleRectF = new RectF(0.0F, 0.0F, 0.0F, 0.0F);
    }

    public void initColor(int startColor, int stopColor) {
        this.mStartColor = startColor;
        this.mStopColor = stopColor;
        this.mCurBgColor = this.mStartColor;
        this.mStartColorRed = this.mStartColor >> 16 & 255;
        this.mStartColorGreen = this.mStartColor >> 8 & 255;
        this.mStartColorBlue = this.mStartColor & 255;
        this.mStopColorRed = this.mStopColor >> 16 & 255;
        this.mStopColorGreen = this.mStopColor >> 8 & 255;
        this.mStopColorBlue = this.mStopColor & 255;
        this.mDeltaColorRed = this.mStopColorRed - this.mStartColorRed;
        this.mDeltaColorGreen = this.mStopColorGreen - this.mStartColorGreen;
        this.mDeltaColorBlue = this.mStopColorBlue - this.mStartColorBlue;
        this.mPaint.setColor(this.mStartColor);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(this.mIsFirstMeasure) {
            this.mIsFirstMeasure = false;
            this.mBgWith = MeasureSpec.getSize(widthMeasureSpec);
            this.mRadius = (int)((float)MeasureSpec.getSize(heightMeasureSpec) / 2.0F);
            this.mLineLength = this.mBgWith - 2 * this.mRadius;
            this.mLeftCircleRectF.set(0.0F, 0.0F, (float)(2 * this.mRadius), (float)(2 * this.mRadius));
            this.mRightCircleRectF.set((float)(this.mBgWith - 2 * this.mRadius), 0.0F, (float)this.mBgWith, (float)(2 * this.mRadius));
            this.mPath.reset();
            this.mPath.moveTo((float)this.mRadius, 0.0F);
            this.mPath.lineTo((float)(this.mRadius + this.mLineLength), 0.0F);
            this.mPath.arcTo(this.mRightCircleRectF, 270.0F, 180.0F);
            this.mPath.lineTo((float)this.mRadius, (float)(2 * this.mRadius));
            this.mPath.arcTo(this.mLeftCircleRectF, 90.0F, 180.0F);
        }

        Log.e("Measure", this.mBgWith + "");
        Log.e("mRadius", this.mRadius + "");
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(this.mPath, this.mPaint);
    }

    public void setColorFlactor(float f) {
        int curRed = (int)((float)this.mStartColorRed + (float)this.mDeltaColorRed * f);
        int curGreen = (int)((float)this.mStartColorGreen + (float)this.mDeltaColorGreen * f);
        int curBlue = (int)((float)this.mStartColorBlue + (float)this.mDeltaColorBlue * f);
        this.mCurBgColor = Color.argb(255, curRed, curGreen, curBlue);
        this.mPaint.setColor(this.mCurBgColor);
        this.invalidate();
    }
}
