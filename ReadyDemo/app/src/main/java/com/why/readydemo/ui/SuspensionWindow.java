package com.why.readydemo.ui;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.why.readydemo.R;
import com.why.readydemo.RecyclerViewAdapter;


/**
 * Created by 11516 on 2018-5-21.
 */

public class SuspensionWindow {

    private static final String TAG = "SuspensionWindow";
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private ViewGroup mViewParent;
    private WindowRecyclerView mRecyclerView;
    private Context mContext;

    private static SuspensionWindow intance;
    private SuspensionWindow(Context context , WindowManager windowManager){
        mContext = context;
        mWindowManager = windowManager;
        initData();
        initView();
    }

    private void initView() {
        mViewParent = (ViewGroup) View.inflate(mContext, R.layout.window_recyclerview, null);
        mRecyclerView = (WindowRecyclerView) mViewParent.findViewById(R.id.rv_movie_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(32));
        RecyclerViewAdapter viewAdapter = new RecyclerViewAdapter(mContext, null);
        mRecyclerView.setAdapter(viewAdapter);
        //RecyclerViewOnKeyListener keyListener = new RecyclerViewOnKeyListener(this);
        //mRecyclerView.setOnKeyListener(keyListener);

//        mRecyclerView.setFocusable(true);
//        mRecyclerView.setFocusableInTouchMode(true);
        mWindowManager.addView(mViewParent, mLayoutParams);
    }

    private void initData() {
        //mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//        mWindowManager = mContext.getWindowManager();
        mLayoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSPARENT);
        mLayoutParams.gravity = Gravity.BOTTOM;
//        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;

        //// TODO: 2018-5-18 mMovieLists的数据还没有取到
    }

    public static SuspensionWindow getSuspensionWindowIntance(Context context, WindowManager windowManager){
        if (intance == null){
            synchronized (SuspensionWindow.class){
                if (intance == null){
                    intance = new SuspensionWindow(context, windowManager);
                }
            }
        }
//        intance = new SuspensionWindow(context);
        return intance;
    }

    public void removeSuspensionWindow(Context context){
        if (mWindowManager != null){
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        mWindowManager.removeView(mViewParent);
        intance = null;
    }

}
