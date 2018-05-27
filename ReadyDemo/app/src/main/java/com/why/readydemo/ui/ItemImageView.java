package com.why.readydemo.ui;

import android.content.ClipData;
import android.content.Context;
import android.nfc.Tag;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.Toast;

/**
 * Created by 11516 on 2018-5-25.
 */

public class ItemImageView extends android.support.v7.widget.AppCompatImageView {
    private Scroller mScroller;
    private static final String TAG = "ItemImageView";
    private int lastX;
    private int lastY;


    public ItemImageView(Context context) {
        this(context, null);
    }

    public ItemImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

//    public static ItemImageView getInstance(Context context){
//        if (mItemImageView == null){
//            synchronized (ItemImageView.class){
//                if (mItemImageView == null){
//                    mItemImageView = new ItemImageView(context);
//                }
//            }
//        }
//        return mItemImageView;
//    }

    private void initData(Context context) {
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: ");
        //textScrollSlowly(event);
        textGetRawXY(event);
        return true;
    }

    private void textGetRawXY(MotionEvent event) {
        int startX = (int) event.getRawX();
        int startY = (int) event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = startX;
                lastY = startY;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = startX - lastX;
                int offsetY = startY - lastY;
                layout(getLeft()+offsetX, getTop()+offsetY, getRight()+offsetX, getBottom()+offsetY);
                lastX = startX;
                lastY = startY;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mLayoutParams = getLayoutParams();
    }
    private ViewGroup.LayoutParams mLayoutParams;

//    public ViewGroup.LayoutParams getItemLayoutParams(){
//        return mLayoutParams
//    }

    /**
     * 已经管用了，只是在window上面滑动，无法移动到Activity上面
     * @param event
     */
    private void textScrollSlowly(MotionEvent event) {
        int startX = (int) event.getRawX();
        int startY = (int) event.getRawY();
        Log.i(TAG, "textScrollSlowly: ");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = startX;
                lastY = startY;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = startX - lastX;
                int offsetY = startY - lastY;
                ((View)getParent()).scrollBy(-offsetX, -offsetY);
                lastX = startX;
                lastY = startY;
                break;
            case MotionEvent.ACTION_UP:
                View viewGroup = (View) getParent();
                mScroller.startScroll(viewGroup.getScrollX(), viewGroup.getScrollY(), -viewGroup.getScrollX(), -viewGroup.getScrollY());
                invalidate();
                break;
        }
    }

}
