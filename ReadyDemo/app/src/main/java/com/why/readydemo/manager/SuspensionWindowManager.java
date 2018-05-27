package com.why.readydemo.manager;

import android.content.Context;
import android.view.WindowManager;

import com.why.readydemo.ui.SuspensionWindow;

/**
 * Created by 11516 on 2018-5-21.
 */

public class SuspensionWindowManager {

    private static SuspensionWindowManager mSuspensionWindowManager;
    private SuspensionWindow mSuspensionWindow;

    private SuspensionWindowManager(){

    }

    public SuspensionWindow getSuspensionWindow(Context context, WindowManager windowManager){
        mSuspensionWindow = SuspensionWindow.getSuspensionWindowIntance(context, windowManager);
        return mSuspensionWindow;
    }

    public static SuspensionWindowManager getSuspensionWindowManager(Context context){
        if (mSuspensionWindowManager == null){
            synchronized (SuspensionWindowManager.class){
                if (mSuspensionWindowManager == null){
                    mSuspensionWindowManager = new SuspensionWindowManager();
                }
            }
        }
        return mSuspensionWindowManager;
    }

    public void removeSuspensionWindow(Context context) throws Exception {
        if (mSuspensionWindow == null){
            //mSuspensionWindow = getSuspensionWindow(context);
            throw new Exception("没有Window，不能删除");
        }
        mSuspensionWindow.removeSuspensionWindow(context);
    }

}
