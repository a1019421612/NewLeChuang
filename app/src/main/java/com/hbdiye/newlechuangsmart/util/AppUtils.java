package com.hbdiye.newlechuangsmart.util;

import android.content.Context;
import android.os.Vibrator;


public class AppUtils {
    /**
     * 设置震动
     *
     * @param context
     * @param time    毫秒
     */
    public static void vibrate(Context context, Long time) {
        Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        vibrator.vibrate(100);
    }
}
