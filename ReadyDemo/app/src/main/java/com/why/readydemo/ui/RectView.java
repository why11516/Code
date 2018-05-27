package com.why.readydemo.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class RectView extends View {

    private static final String TAG = "RectView";
    
    public static <T extends ViewGroup> RectView addRectView(Context context,T viewGroup,int... args){
        if (args.length != 6) return null;
        int[] x = {args[0],args[2],args[4]};
        int[] y = {args[1],args[3],args[5]};
        int left=Integer.MAX_VALUE,right=0,top=Integer.MAX_VALUE,bottom=0;
        for(int i: new int[]{0,1,2}){
            left = left<x[i] ? left:x[i];
            right = right > x[i]? right : x[i];
            top = top<y[i] ? top:y[i];
            bottom = bottom > y[i]? bottom : y[i];
        }
        T.MarginLayoutParams layoutParams = new T.MarginLayoutParams(Math.abs(right - left),Math.abs(top-bottom));
        layoutParams.setMargins(left,top,right,bottom);
        Log.e("绘制",left+"  "+top+"  "+right+"   "+bottom);
        RectView rectView = new RectView(context);
        viewGroup.addView(rectView,layoutParams);
        return  rectView;
    }

    public RectView(Context context) {
        this(context, null);
    }

    public RectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        Log.i(TAG, "initData: ");
    }

    public RectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure: ");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMySize(100, widthMeasureSpec);
        int height = getMySize(100, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw: ");
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        DashPathEffect pathEffect = new DashPathEffect(new float[] { 1,2 }, 1);
        paint.setPathEffect(pathEffect);
        paint.setAntiAlias(true);
        //开始绘制
        canvas.drawRect(getLeft(),getTop(),getRight(),getBottom(),paint);
    }
}
