package com.why.readydemo;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.icu.text.UnicodeSetSpanner;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by 11516 on 2018-5-24.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "SpaceItemDecoration";
    private int mTop;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top += mTop;
    }

    public SpaceItemDecoration(int top){
        mTop = top;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        Log.i(TAG, "onDrawOver: ");
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        Log.i(TAG, "onDraw: ");
    }
}
