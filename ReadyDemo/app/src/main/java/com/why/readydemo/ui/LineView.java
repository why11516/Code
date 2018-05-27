package com.why.readydemo.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.LoginFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.why.readydemo.util.PropertyUtil;

/**
 * Created by 11516 on 2018-5-26.
 */

public class LineView extends View {

    private static final String TAG = "LineView";
    private Paint mPaintLine;
    private int mScreenHeight;
    private int mScreenWidth;


    public LineView(Context context) {
        this(context, null);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initData(Context context) {
        Log.i(TAG, "initData: ");
        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine.setColor(Color.argb(255, 252, 133, 28));
        mPaintLine.setStrokeWidth(20);
        mPaintLine.setAntiAlias(true);

        mScreenHeight = PropertyUtil.getScreenHeight(context);
        mScreenWidth = PropertyUtil.getScreenWidth(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: ");
        /**
         * 长刻度: 1m 2m 3m
         */
        canvas.drawLine(100, 0, 100, 400, mPaintLine);
        //canvas.drawLine(mScreenWidth-100, 0, mScreenWidth-95, 400, mPaintLine);
        /**
         * 短刻度
         */
        //canvas.drawLine(mScreenWidth-150, 100, mScreenWidth-145, 300, mPaintLine);
    }
}
