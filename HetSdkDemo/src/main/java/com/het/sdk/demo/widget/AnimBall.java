package com.het.sdk.demo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xuchao on 2016/5/17.
 */
public class AnimBall extends View {
    private static final float HEIGHT_WIDTH_RADIO = 1.4F;
    private Paint mPaint;
    private RectF mDrawRectF;
    private int mWidth;
    private int mRadius;
    private int mDeltaOfHeightWidth;

    public AnimBall(Context context) {
        super(context);
        this.init();
    }

    public AnimBall(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    private void init() {
        this.mPaint = new Paint(1);
        this.mPaint.setColor(-1);
        this.mDrawRectF = new RectF(0.0F, 0.0F, 0.0F, 0.0F);
    }

    public void setColor(int color) {
        this.mPaint.setColor(color);
    }

    public void setBallRadius(int radius) {
        this.mRadius = radius;
        this.mDeltaOfHeightWidth = (int)((float)radius * 0.39999998F) * 2;
        this.mDrawRectF.set(0.0F, 0.0F, (float)(2 * this.mRadius), (float)(2 * this.mRadius));
        this.mWidth = 2 * this.mRadius;
    }

    public void setAnimFactor(float factorOfScrollX) {
        this.caculateWidth(this.getActualFactor(factorOfScrollX));
        this.refreshRectFOfDraw();
    }

    public void setBallColor(int color) {
        this.mPaint.setColor(color);
    }

    private void refreshRectFOfDraw() {
        this.mDrawRectF.set(0.0F, 0.0F, (float)this.mWidth, (float)(2 * this.mRadius));
    }

    private void caculateWidth(float curF) {
        this.mWidth = (int)((float)(2 * this.mRadius) + (float)this.mDeltaOfHeightWidth * curF);
    }

    private float getActualFactor(float f) {
        return f < 0.5F?2.0F * f:1.0F - 2.0F * (f - 0.5F);
    }

    public int getActualHeight() {
        return 2 * this.mRadius;
    }

    public int getActualWidth() {
        return this.mWidth;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(this.mDrawRectF, this.mPaint);
    }
}
