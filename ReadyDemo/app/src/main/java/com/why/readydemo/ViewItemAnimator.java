package com.why.readydemo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by 11516 on 2018-5-24.
 */

public class ViewItemAnimator extends RecyclerView.ItemAnimator {
    private static final String TAG = "ViewItemAnimator";
    @Override
    public boolean animateDisappearance(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @Nullable ItemHolderInfo postLayoutInfo) {
        Log.i(TAG, "animateDisappearance: ");
        return false;
    }

    @Override
    public boolean animateAppearance(@NonNull RecyclerView.ViewHolder viewHolder, @Nullable ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
        Log.i(TAG, "animateAppearance: ");
        return false;
    }

    @Override
    public boolean animatePersistence(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
        Log.i(TAG, "animatePersistence: ");
        return false;
    }

    @Override
    public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder, @NonNull RecyclerView.ViewHolder newHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
        Log.i(TAG, "animateChange: ");
        return false;
    }

    @Override
    public void runPendingAnimations() {
        Log.i(TAG, "runPendingAnimations: ");
    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder item) {
        Log.i(TAG, "endAnimation: ");
    }

    @Override
    public void endAnimations() {
        Log.i(TAG, "endAnimations: ");
    }

    @Override
    public boolean isRunning() {
        Log.i(TAG, "isRunning: ");
        return false;
    }
}
