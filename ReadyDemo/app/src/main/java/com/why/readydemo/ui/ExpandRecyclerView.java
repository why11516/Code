package com.why.readydemo.ui;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.LoginFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.why.readydemo.RecyclerViewAdapter;

/**
 * Created by 11516 on 2018-5-26.
 */

public class ExpandRecyclerView extends RecyclerView {
    private static final String TAG = "ExpandRecyclerView";
    private volatile int mAnimatorNum;
    public ExpandRecyclerView(Context context) {
        this(context, null);
    }

    public ExpandRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initData();
    }

    private void initData() {

    }

    public void addItemImageView(ImageView imageView, int position){

    }

    public void setMyItemAnimator(){
        final RecyclerViewAdapter viewAdapter = (RecyclerViewAdapter) getAdapter();
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator(){
            @Override
            public boolean animateDisappearance(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @Nullable ItemHolderInfo postLayoutInfo) {
                Log.i(TAG, "animateDisappearance: ");
                return super.animateDisappearance(viewHolder, preLayoutInfo, postLayoutInfo);
            }

            @Override
            public boolean animateAppearance(@NonNull ViewHolder viewHolder, @Nullable ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
                Log.i(TAG, "animateAppearance: ");
                return super.animateAppearance(viewHolder, preLayoutInfo, postLayoutInfo);
            }


            @Override
            public void endAnimation(ViewHolder item) {
                super.endAnimation(item);
                Log.i(TAG, "endAnimation: ");
            }
        };
        defaultItemAnimator.setAddDuration(1000);
        defaultItemAnimator.setRemoveDuration(1000);
        setItemAnimator(defaultItemAnimator);
    }
    
}
