package com.why.readydemo.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by 11516 on 2018-5-26.
 */

public class AnimatorUtil {
    private ValueAnimator createDropAnimator(final View hidden_view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = hidden_view.getLayoutParams();
                layoutParams.height = animatedValue;
                hidden_view.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
    private void animataClose(final LinearLayout hidden_view) {
        int height = hidden_view.getHeight();
        ValueAnimator animator = createDropAnimator(hidden_view, height, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                hidden_view.setVisibility(View.GONE);
            }
        });
        animator.start();
    }

    private void animataOpen(View hidden_view) {
        hidden_view.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(hidden_view, 0, hidden_view.getMeasuredHeight());
        animator.start();
    }
}
