package com.het.open.lib.auth.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;


public class LoadingBar extends android.support.v7.widget.AppCompatTextView {
	private static final int MAX_PROGRESS = 100;
	private int mProgress;
	private int mProgressColor;
	private Paint mPaint;
	private Handler mHander;
	private Runnable mRunnable = new Runnable() {
		public void run() {
			LoadingBar.this.mProgress += 1;
			LoadingBar.this.drawProgress(LoadingBar.this.mProgress);
		}
	};

	public LoadingBar(Context context) {
		super(context);
		init(context);
	}

	public LoadingBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public LoadingBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		this.mHander = new Handler();
		this.mPaint = new Paint();
		initSkin();
	}

	public void initSkin() {
		this.mProgressColor = -11693826;
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.mPaint.setColor(this.mProgressColor);
		Rect r = getRect();
		canvas.drawRect(r, this.mPaint);
	}

	private Rect getRect() {
		int left = getLeft();
		int top = getTop();
		int right = getLeft() + (getRight() - getLeft()) * this.mProgress / 100;
		int bottom = getBottom();
		return new Rect(0, 0, right - left, bottom - top);
	}

	public void drawProgress(int progress) {
		if (progress < 7) {
			this.mHander.postDelayed(this.mRunnable, 70L);
		} else {
			this.mHander.removeCallbacks(this.mRunnable);
			this.mProgress = progress;
		}
		invalidate();
	}
}
