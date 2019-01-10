package com.hbdiye.newlechuangsmart.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

	private static Toast toast;

	/**
	 * 显示单例吐司
	 * 
	 * @param context
	 * @param letter
	 */
	public static void showToast(Context context, String letter) {
		if (toast == null) {
			toast = Toast.makeText(context, "", 0);
		}

		toast.setText(letter);
		toast.show();
	}
	
	public static void showToastLong(Context context, String letter) {
		if (toast == null) {
			toast = Toast.makeText(context, "", 1);
		}

		toast.setText(letter);
		toast.show();
	}
}
