package com.hbdiye.newlechuangsmart;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.iflytek.cloud.SpeechUtility;
import com.lib.smartlib.HopeSDK;
import com.videogo.openapi.EZOpenSDK;
import com.xzcysoft.wnzhikonglibrary.WNZKConfigure;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MyApp extends Application{
    /**
     * 维护Activity 的list
     */
    public static List<Activity> mActivitys = Collections
            .synchronizedList(new LinkedList<Activity>());
    public static String APPKEY="378b43177968438bb78bf72e645f2ddc";
    public static String APPSECRET="1dab52a43e50c0c53c43bac177c7604d";
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        SmartToast.plainToast(this);
        //摄像头
        /** * sdk日志开关，正式发布需要去掉 */
        EZOpenSDK.showSDKLog(true);
        /** * 设置是否支持P2P取流,详见api */
        EZOpenSDK.enableP2P(false);
        /** * APP_KEY请替换成自己申请的 */
        EZOpenSDK.initLib(this, APPKEY);
        SpeechUtility.createUtility(this, "appid=5c19fe0b");
        HopeSDK.init(this,"888920151595646976","750837261197414400","B8BD721418194C09B2F193C7689453F1");
        HopeSDK.setDebug(false);
        WNZKConfigure.init("1",getApplicationContext());
    }
    /**
     * @param activity 作用说明 ：添加一个activity到管理里
     */
    public void pushActivity(Activity activity) {
        mActivitys.add(activity);
        Log.i("MYapp==","activityList:size:"+mActivitys.size());
    }

    /**
     * @param activity 作用说明 ：删除一个activity在管理里
     */
    public void popActivity(Activity activity) {
        mActivitys.remove(activity);
        Log.i("MYapp==","activityList:size:"+mActivitys.size());
    }
    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        if (mActivitys == null) {
            return;
        }
        for (Activity activity : mActivitys) {
            activity.finish();
        }
        SPUtils.clear(context);
        mActivitys.clear();
    }

    /**
     * 退出应用程序
     */
    public  static void appExit() {
        try {
            finishAllActivity();
        } catch (Exception e) {
        }
    }

    private void registerActivityListener() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    /**
                     *  监听到 Activity创建事件 将该 Activity 加入list
                     */
                    pushActivity(activity);
                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    if (null==mActivitys&&mActivitys.isEmpty()){
                        return;
                    }
                    if (mActivitys.contains(activity)){
                        /**
                         *  监听到 Activity销毁事件 将该Activity 从list中移除
                         */
                        popActivity(activity);
                    }
                }
            });
        }
    }
    //返回
    public static Context getContextObject(){
        return context;
    }
}
