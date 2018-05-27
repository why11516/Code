package com.why.readydemo.bean;

import com.why.readydemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 11516 on 2018-5-27.
 */

public class ChooseImageViewItems {
    private static final int TYPE_COUNT = 6;
    private static ChooseImageViewItems mChooseImageViewItems;
    private Map<Integer, List<Integer>> mScreenParents;
    private List<Integer> mScreenChilds;

    public Map<Integer, List<Integer>> getScreenParents() {
        return mScreenParents;
    }

    private List<Integer> getmScreenChilds() {
        return mScreenChilds;
    }

    private ChooseImageViewItems(){
        initData();
    }

    private void initData() {
        mScreenChilds = new ArrayList<>();
        mScreenParents = new HashMap<>();

        /**
         * V1
         */
        List<Integer> list1 = new ArrayList<>();
        list1.add(R.drawable.screen_v1_55);
        list1.add(R.drawable.screen_v1_65);

        /**
         * V92
         */
        List<Integer> list2 = new ArrayList<>();
        list2.add(R.drawable.screen_v92_55);
        list2.add(R.drawable.screen_v92_65);
        /**
         * M2
         */
        List<Integer> list3 = new ArrayList<>();
        list3.add(R.drawable.screen_m2_55);
        list3.add(R.drawable.screen_m2_65);

        /**
         * M1
         */
        List<Integer> list4 = new ArrayList<>();
        list4.add(R.drawable.screen_m1_55);
        list4.add(R.drawable.screen_m1_65);
        list4.add(R.drawable.screen_49m1);

        /**
         * A2
         */
        List<Integer> list5 = new ArrayList<>();
        list5.add(R.drawable.screen_a2_55);
        list5.add(R.drawable.screen_a2_65);

        /**
         * A1
         */
        List<Integer> list6 = new ArrayList<>();
        list6.add(R.drawable.screen_a1_55);
        list6.add(R.drawable.screen_a1_65);

        mScreenParents.put(0, list1);
        mScreenParents.put(1, list2);
        mScreenParents.put(2, list3);
        mScreenParents.put(3, list4);
        mScreenParents.put(4, list5);
        mScreenParents.put(5, list6);
    }

    public static ChooseImageViewItems getInstance(){
        if (mChooseImageViewItems == null){
            synchronized (ChooseImageViewItems.class){
                if (mChooseImageViewItems == null){
                    mChooseImageViewItems = new ChooseImageViewItems();
                }
            }
        }
        return mChooseImageViewItems;
    }

}
