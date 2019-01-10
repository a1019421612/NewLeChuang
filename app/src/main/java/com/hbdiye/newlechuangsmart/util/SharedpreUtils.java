package com.hbdiye.newlechuangsmart.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * sharedpreference工具类
 * 
 * @author Administrator
 *
 */
public class SharedpreUtils {
	public static SharedPreferences sp;

	public static SharedPreferences getSharedPre(Context ctx) {
		if (sp == null) {
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sp;
	}

	public static void putBoolean(Context ctx, String key, boolean value) {
		SharedPreferences sp = getSharedPre(ctx);
		sp.edit().putBoolean(key, value).commit();
	}

	public static boolean getBoolean(Context ctx, String key, boolean defValue) {
		SharedPreferences sp = getSharedPre(ctx);
		return sp.getBoolean(key, defValue);
	}

	public static void putString(Context ctx, String key, String value) {
		SharedPreferences sp = getSharedPre(ctx);
		sp.edit().putString(key, value).commit();
	}

	public static String getString(Context ctx, String key, String defValue) {
		SharedPreferences sp = getSharedPre(ctx);
		return sp.getString(key, defValue);
	}

	public static void putInt(Context ctx, String key, int value) {
		SharedPreferences sp = getSharedPre(ctx);
		sp.edit().putInt(key, value).commit();
	}

	public static int getInt(Context ctx, String key, int defValue) {
		SharedPreferences sp = getSharedPre(ctx);
		return sp.getInt(key, defValue);
	}

	public static void removeString(Context ctx, String key) {
		getSharedPre(ctx).edit().remove(key).commit();
	}
	
	public static void remove(Context ctx){
		getSharedPre(ctx).edit().clear().commit();
	}
}
