package com.why.readydemo.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by 11516 on 2018-5-26.
 */

public class BitmapUtil {

    public static int getBitmapHeightFromResource(Resources res, int resId){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        return options.outHeight;
    }

    public static int getBitmapWidthFromResource(Resources res, int resId){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        return options.outWidth;
    }
}
