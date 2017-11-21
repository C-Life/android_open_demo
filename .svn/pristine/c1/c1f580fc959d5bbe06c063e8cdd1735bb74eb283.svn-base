package com.het.sdk.demo.ui.activity.tablayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by mindray on 2017/9/4.
 */

public class TabIconView extends ImageView{

    public static final int START_POSITION = 0;
    public static final int ALPHA_MAX = 255;
    //画笔
    private Paint mPaint;
    //选中时的图标
    private Bitmap mIconSelected;
    //未选中时的图标
    private Bitmap mIconNormal;
    //选中时的矩形(限制绘制范围)
    private Rect mRectSelected;
    //未选中时的矩形(限制绘制范围)
    private Rect mRectNormal;
    //当前的alpha值
    private int mAlphaCurrent = 0;

    public TabIconView(Context context) {
        super(context);
    }

    public TabIconView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabIconView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /**
     * 初始化
     *
     * @param normal   正常图标的id
     * @param selected 选中的图标的id
     */
    public final void init(int normal,int selected) throws Exception{
        mIconNormal = createBitmap(normal);
        mIconSelected = createBitmap(selected);
        //创建不了图片
        if(mIconNormal == null || mIconSelected == null) {
            throw new Exception("icon id can not create1 bitmap");
        }
        //根据创建的位图创建对应的矩形
        mRectNormal = new Rect(START_POSITION,START_POSITION,mIconNormal.getWidth(),mIconNormal.getHeight());
        mRectSelected = new Rect(START_POSITION,START_POSITION,mIconSelected.getWidth(),mIconSelected.getHeight());
        //画笔只要实例化就行，没有什么要求
        mPaint = new Paint(1);
    }
    /**
     * 根据资源id创建的位图
     *
     * @param resId 资源id
     * @return 创建的位图
     */
    private Bitmap createBitmap(int resId) {
        return BitmapFactory.decodeResource(getResources(),resId);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //画笔为空，直接返回
        if(mPaint == null){
            return;
        }
        //设置当前选中图标的alpha值(逐渐减少)
        mPaint.setAlpha(ALPHA_MAX - mAlphaCurrent);
        canvas.drawBitmap(mIconNormal,null,mRectNormal,mPaint);
        //设置目标图标的alpha值(逐渐减增大)
        mPaint.setAlpha(mAlphaCurrent);
        canvas.drawBitmap(mIconSelected,null,mRectSelected,mPaint);
    }
    /**
     * 改变alpha值
     * @param alpha
     */
    public final void changeSelectedAlpha(int alpha) {
        mAlphaCurrent = alpha;
        invalidate();
    }

    /**
     * ViewPager切换时用到
     * @param offset 偏移量
     */
    public final void transformPage(float offset) {
        changeSelectedAlpha((int)(ALPHA_MAX * (1 - offset)));
    }

}
