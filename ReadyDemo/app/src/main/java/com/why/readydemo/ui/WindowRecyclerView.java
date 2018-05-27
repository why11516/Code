package com.why.readydemo.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;

import com.why.readydemo.manager.SuspensionWindowManager;


/**
 * Created by 11516 on 2018-5-21.
 */

public class WindowRecyclerView extends RecyclerView {
    private static final String TAG = "WindowRecyclerView";
    private boolean mShouldScroll;
    private int mTargetPosition;
    private int mCurrentIndex;
    private SuspensionWindowManager mWindowManager;
    public WindowRecyclerView(Context context) {
        this(context, null);
    }

    public WindowRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WindowRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //initData();
    }


    private void initData() {
        mWindowManager = SuspensionWindowManager.getSuspensionWindowManager(getContext());
    }

    @Override
    public void smoothScrollToPosition(int position) {
        super.smoothScrollToPosition(position);
        Log.i(TAG, "smoothScrollToPosition" + position);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }



}
