package com.why.readydemo.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.why.readydemo.R;
import com.why.readydemo.RecyclerViewAdapter;
import com.why.readydemo.bean.ChooseImageViewItems;
import com.why.readydemo.manager.SuspensionWindowManager;
import com.why.readydemo.ui.ExpandRecyclerView;
import com.why.readydemo.ui.ItemImageView;
import com.why.readydemo.ui.LineView;
import com.why.readydemo.ui.RectView;
import com.why.readydemo.ui.SuspensionWindow;
import com.why.readydemo.ui.WaveView;
import com.why.readydemo.util.DisplayUtil;
import com.why.readydemo.util.PropertyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 11516 on 2018-5-26.
 */

public class PullFragment extends Fragment implements OnTouchEventListener, View.OnClickListener{
    private static final String TAG = "PullFragment";
    private static final int LINE_NUM = 6;
    private SuspensionWindow mSWindow;
    private SuspensionWindowManager mSWindowManager;
    private WindowManager mWindowManager;
    public Activity mActivity;

    private List<Integer> mAdapterBitmapId;
    private Map<Integer, List<Integer>> mParents;
    private List<Integer> mChilds;
    private List<LineView> mLineViews;
    private Map<Integer, List<Integer>> mScreenParents;
    private List<Integer> mScreenChilds;
    private ChooseImageViewItems mChooseImageViewItems;
    private int mCurrentIndex;

    private RelativeLayout mRootView;
    private ExpandRecyclerView mRecyclerView;
    private ExpandableListView mExpandableListView;
    private LinearLayout mChooseLayout;
    private ImageView mSelectedView;
    private ImageView mNotSelectedView;
    private ImageView mIndicateView;
    private TextView mDistanceView;
    private LinearLayout mKeyLayout;
    private ItemImageView mItemImageView;
    private LinearLayout mBackgroundLayout;
    private Button mBt_45;
    private Button mBt_55;
    private Button mBt_65;

    private RecyclerViewAdapter mViewAdapter;

    private boolean isSlideOpen = true;
    /**
     * 侧边栏布局
     */
    private LinearLayout mSlideLayout;
    private TextView mRectView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();

    }
    private void addWindow() {
        mWindowManager = mActivity.getWindowManager();
        mSWindowManager = SuspensionWindowManager.getSuspensionWindowManager(mActivity);
        mSWindow = mSWindowManager.getSuspensionWindow(mActivity, mWindowManager);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = initView();
        return view;
    }

    private View initView() {
        mRootView = (RelativeLayout) View.inflate(mActivity, R.layout.fragment_pull, null);

        //addBackground();

        mSlideLayout = (LinearLayout) mRootView.findViewById(R.id.slide_layout);
        mKeyLayout = (LinearLayout) mRootView.findViewById(R.id.key_49_55_65);
        mItemImageView = (ItemImageView) mRootView.findViewById(R.id.screen_itemImageView);
        mBackgroundLayout = (LinearLayout) mRootView.findViewById(R.id.background_layout);
        /**
         * 虚线框
         */
        mRectView = (TextView) mRootView.findViewById(R.id.rect_view);
        mBt_45 = (Button) mRootView.findViewById(R.id.bt_45);
        mBt_55 = (Button) mRootView.findViewById(R.id.bt_55);
        mBt_65 = (Button) mRootView.findViewById(R.id.bt_65);
        mBt_45.setOnClickListener(this);
        mBt_55.setOnClickListener(this);
        mBt_65.setOnClickListener(this);
        initChooseView();
        initRecyclerView(mRootView);
        initWaveView();
        initIndicateView();
        return mRootView;
    }

    private void addBackground() {
        TextView textView = new TextView(mActivity);
        textView.setText("sidfhsu");
        textView.setTextSize(500);
        textView.setTextColor(Color.argb(55, 255, 0, 0));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(mActivity, 100));
        mRootView.addView(textView, layoutParams);
    }

    private void initChooseView() {
        mChooseLayout = (LinearLayout) mRootView.findViewById(R.id.image_choose);
        mSelectedView = (ImageView) mRootView.findViewById(R.id.image_selected);
        mNotSelectedView = (ImageView) mRootView.findViewById(R.id.image_not_selected);
        mSelectedView.setOnClickListener(this);
        mNotSelectedView.setOnClickListener(this);
    }

    /**
     * 侧边栏的推进拉出的小按钮
     */
    private void initIndicateView() {
        mIndicateView = (ImageView) mRootView.findViewById(R.id.indicate_icon);
        mIndicateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSlideOpen){
                    Log.i(TAG, "onClick: ");
                    /**
                     * ObjectAnimator animatorYClose
                     *      = ObjectAnimator.ofFloat(mSlideLayout, "translationY", 0, mRecyclerView.getMeasuredHeight(), 0, 0);
                     *
                     * 为什么会执行两次？？？？？？？？
                     */
                    ObjectAnimator animatorYClose = ObjectAnimator.ofFloat(mSlideLayout, "translationY", 0, 0, 0, -DisplayUtil.dip2px(mActivity, 100));
                    animatorYClose.start();
                    mIndicateView.setImageResource(R.drawable.down);
                }else {
                    ObjectAnimator animatorYOpen = ObjectAnimator.ofFloat(mSlideLayout, "translationY", 0, 0, 0, 0);
                    animatorYOpen.start();
                    mIndicateView.setImageResource(R.drawable.up);
                }
                isSlideOpen = !isSlideOpen;
                Log.i(TAG, "initIndicateView: "+isSlideOpen);
            }
        });
    }


    private void initWaveView() {

    }


    /**
     * 初始化mRecyclerView
     * @param inflate
     */
    private void initRecyclerView(RelativeLayout inflate) {
        mRecyclerView = (ExpandRecyclerView) inflate.findViewById(R.id.rv_movie_list_fragment);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initRecyclerViewData();
    }

    private void initData() {
        mLineViews = new ArrayList<>();

    }

    /**
     * RecyclerView的数据准备
     */
    private void initRecyclerViewData() {
        /**
         * recyclerView
         */
        readyAdapter();
        readyData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mViewAdapter = new RecyclerViewAdapter(mActivity, mAdapterBitmapId);
        mRecyclerView.setAdapter(mViewAdapter);
        mRecyclerView.setMyItemAnimator();

        mViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i(TAG, "onItemClick: "+position);
                //mViewAdapter.notifyDataSetChanged();
                boolean openState = mViewAdapter.getOpenState();
                if (openState && position<7 && position > 0){
                    //Toast.makeText(mActivity, "点击了第"+(position-1)+"个元素", Toast.LENGTH_SHORT).show();
                    if (position == 4){
                        mBt_45.setBackgroundResource(R.drawable.key_49);
                    }
                    mBt_55.setBackgroundResource(R.drawable.key_55_selected);
                    mBt_65.setBackgroundResource(R.drawable.key_65);
                    if (position == 4){
                        mBt_45.setVisibility(View.VISIBLE);
                        ViewGroup.LayoutParams layoutParams = mKeyLayout.getLayoutParams();
                        layoutParams.height = DisplayUtil.dip2px(mActivity, 300);
                        mKeyLayout.setLayoutParams(layoutParams);
                    }else {
                        mBt_45.setVisibility(View.GONE);
                        ViewGroup.LayoutParams layoutParams = mKeyLayout.getLayoutParams();
                        layoutParams.height = DisplayUtil.dip2px(mActivity, 200);
                        mKeyLayout.setLayoutParams(layoutParams);
                    }
                    chooseTVSize(view, position);
                }
                if (mParents.get(position)!=null){
                    mViewAdapter.expandState(mParents.get(position), position+1);
                    return;
                }
            }
        });

    }

    /**
     * 点击侧边栏的时候，在屏幕中间显示一个ImageView
     *
     * @param view
     * @param position
     */
    private void chooseTVSize(View view, int position) {
        mChooseImageViewItems = ChooseImageViewItems.getInstance();
        mScreenParents = mChooseImageViewItems.getScreenParents();
        if (mScreenParents!=null){
            mCurrentIndex = position - 1;
            mScreenChilds = mScreenParents.get(position - 1);
            mItemImageView.setImageResource(mScreenChilds.get(0));
        }

        mItemImageView.setVisibility(View.VISIBLE);
        mKeyLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 添加tv图片的id
     */
    private void readyData() {
        mChilds = new ArrayList<>();
        mChilds.add(R.drawable.child_a1);
        mChilds.add(R.drawable.child_a2);
        mChilds.add(R.drawable.child_m1);
        mChilds.add(R.drawable.child_m2);
        mChilds.add(R.drawable.child_v92);
        mChilds.add(R.drawable.child_v1);

        mParents = new HashMap<>();
        mParents.put(0, mChilds);

        mParents.put(1, null);

        mParents.put(2, null);

        mParents.put(3, null);
        mParents.put(4, null);
        mParents.put(5, null);
        mParents.put(6, null);

    }

    /**
     * 给RecyclerViewAdapter所需数据做准备
     */
    private void readyAdapter() {
        mAdapterBitmapId = new ArrayList<>();
        mAdapterBitmapId.add(R.drawable.tv);
        mAdapterBitmapId.add(R.drawable.ice_box);
        mAdapterBitmapId.add(R.drawable.electric_bowl);
        mAdapterBitmapId.add(R.drawable.phone);
        mAdapterBitmapId.add(R.drawable.wash);
        mAdapterBitmapId.add(R.drawable.micro_wave_oven);
    }

    private int pointNum = 0;
    private int mPointX;
    private List<Point> lists = new ArrayList<>();
    @Override
    public void onTouchEvent(MotionEvent event) {
        //mItemImageView.onTouchEvent(event);
        int x;
        int y;
        if (pointNum>2){
            return;
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                x= (int) event.getX();
                y= (int) event.getY();
                lists.add(new Point(x, y));
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(200, 200);
                layoutParams.setMargins(x-100, y-100, 0, 0);
                mRootView.addView(getWaveView(), layoutParams);
                if (pointNum != 0){
                    mPointX += x;
                }
                pointNum++;
                break;
        }
        if (pointNum%3 == 0 && pointNum > 0){
            addMeasureDistanceLine();
            addRect();
        }
    }

    private void addRect() {
        if (lists != null && lists.size() > 0){
            int x = 0;
            int y = 0;
            for (int i=0; i<lists.size(); i++){
                x += lists.get(i).x;
                y += lists.get(i).y;
            }
            x = x/3;
            y = y/3;
//            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DisplayUtil.dip2px(mActivity, 150), DisplayUtil.dip2px(mActivity, 300));
//            layoutParams.leftMargin = x - 300;
//            layoutParams.topMargin = y - 400;
//            mRectView.setLayoutParams(layoutParams);
        }else {

        }
        mRectView.setVisibility(View.VISIBLE);
    }

    /**
     * 添加指示线段和距离
     */
    private void addMeasureDistanceLine() {
        Log.i(TAG, "addMeasureDistanceLine: mPointY =  "+mPointX);
        mBackgroundLayout.setVisibility(View.VISIBLE);
        int pointX = mPointX/4;
        mPointX = 0;
        boolean isLong = true;
        for (int i=0; i<LINE_NUM; i++){
            if (isLong){
                LineView view = new LineView(mActivity);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
                params.setMargins(PropertyUtil.getScreenWidth(mActivity)-50*i, PropertyUtil.getScreenHeight(mActivity)/2, 0, 0);
                mRootView.addView(view, params);
                mLineViews.add(view);
            }else {
                LineView view = new LineView(mActivity);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 50);
                params.setMargins(PropertyUtil.getScreenWidth(mActivity)-50*i, PropertyUtil.getScreenHeight(mActivity)/2+50, 0, 0);
                mRootView.addView(view, params);
                mLineViews.add(view);
            }
            isLong = !isLong;

        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(400, 200);
        params.setMargins(PropertyUtil.getScreenWidth(mActivity)-50*(LINE_NUM - 1), PropertyUtil.getScreenHeight(mActivity)/2+100, 0, 0);
        mDistanceView = new TextView(mActivity);
        mDistanceView.setText("4.78m");
        mDistanceView.setTextColor(Color.argb(255, 252, 133, 28));
        RotateAnimation animation = new RotateAnimation(0, -90, Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDistanceView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.setFillAfter(true);
        animation.setDuration((long) 0.1);
        mDistanceView.setAnimation(animation);
        mDistanceView.setVisibility(View.GONE);
        mRootView.addView(mDistanceView, params);
        mChooseLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 添加触摸点水波纹
     * @return
     */
    private WaveView getWaveView(){
        final WaveView waveView = new WaveView(mActivity);
        waveView.setDuration(500);
        waveView.setStyle(Paint.Style.FILL);
        waveView.setColor(Color.BLUE);
        waveView.setInterpolator(new LinearInterpolator());
        waveView.start();
        waveView.postDelayed(new Runnable() {
            @Override
            public void run() {
                waveView.stop();
            }
        }, 200);

        return waveView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_selected:
                select();
                break;
            case R.id.image_not_selected:
                notSelect();
                break;
            case R.id.bt_45:
                clickBtToChooseSize(R.id.bt_45);
                break;
            case R.id.bt_55:
                clickBtToChooseSize(R.id.bt_55);
                break;
            case R.id.bt_65:
                clickBtToChooseSize(R.id.bt_65);
                break;
        }
    }

    private void notSelect() {
        Log.i(TAG, "notSelect: ");
        pointNum = 0;
        mRectView.setVisibility(View.GONE);
        for (int i=0; i<mLineViews.size(); i++){
            mRootView.removeView(mLineViews.get(i));
        }
        if (mDistanceView!=null){
            mRootView.removeView(mDistanceView);
        }
    }

    /**
     * 按45/55/65寸
     * @param keyId
     */
    public void clickBtToChooseSize(int keyId){
        List<Integer> list = mScreenParents.get(mCurrentIndex);
        if (list == null){
            return;
        }
        switch (keyId){
            case R.id.bt_45:
//                mBt_45.setBackgroundColor(Color.argb());
                mBt_45.setBackgroundResource(R.drawable.key_49_selected);
                mBt_55.setBackgroundResource(R.drawable.key_55);
                mBt_65.setBackgroundResource(R.drawable.key_65);
                mItemImageView.setImageResource(list.get(2));
                break;
            case R.id.bt_55:
                mBt_45.setBackgroundResource(R.drawable.key_49);
                mBt_55.setBackgroundResource(R.drawable.key_55_selected);
                mBt_65.setBackgroundResource(R.drawable.key_65);
                mItemImageView.setImageResource(list.get(0));
                break;
            case R.id.bt_65:
                mBt_45.setBackgroundResource(R.drawable.key_49);
                mBt_55.setBackgroundResource(R.drawable.key_55);
                mBt_65.setBackgroundResource(R.drawable.key_65_selected);
                mItemImageView.setImageResource(list.get(1));
                break;
        }
    }

    /**
     * 测距之后选中对勾
     */
    private void select() {
        mRectView.setVisibility(View.GONE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mChooseLayout, "translationY", 0, 0, 0, -mChooseLayout.getMeasuredWidth());
        objectAnimator.setDuration(500);
        objectAnimator.start();
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mChooseLayout.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
//        TranslateAnimation animation = new TranslateAnimation(0, -mChooseLayout.getMeasuredWidth(), 0, 0);
//        animation.setDuration(500);
//        mChooseLayout.setAnimation(animation);
//        //mBackgroundLayout.setAnimation(animation);
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                mChooseLayout.setVisibility(View.GONE);
//                mRecyclerView.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
        mIndicateView.setVisibility(View.VISIBLE);

        for (int i=0; i<mLineViews.size(); i++){
            mRootView.removeView(mLineViews.get(i));
        }
        if (mDistanceView!=null){
            mRootView.removeView(mDistanceView);
        }
    }
}
