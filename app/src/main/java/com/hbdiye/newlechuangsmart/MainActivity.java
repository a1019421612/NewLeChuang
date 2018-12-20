package com.hbdiye.newlechuangsmart;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.hbdiye.newlechuangsmart.activity.VoiceActivity;
import com.hbdiye.newlechuangsmart.fragment.DeviceFragment;
import com.hbdiye.newlechuangsmart.fragment.HomeFragment;
import com.hbdiye.newlechuangsmart.fragment.LinkageFragment;
import com.hbdiye.newlechuangsmart.fragment.MineFragment;
import com.hbdiye.newlechuangsmart.util.JsonParser;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private ImageView mImageChats, mImageContact, mImageFind, mImageMe, iv_voice;
    private TextView mTextChats, mTextContact, mTextFind, mTextMe;
    private FrameLayout frameLayout;
    private FragmentManager fragmentManager;

    private HomeFragment homeFragment;
    private DeviceFragment deviceFragment;
    private LinkageFragment linkageFragment;
    private MineFragment mineFragment;
    private Intent intent;


    // 语音听写对象
    private SpeechRecognizer mIat;
    int ret = 0; // 函数调用返回值
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    private long cur_time;
    private MediaRecorder recorder;
    protected Drawable[] micImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initStatusBar();
        initImageVoice();
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(MainActivity.this, mInitListener);
        initView();
        intent = new Intent(MainActivity.this, MyService.class);
        startService(intent);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(intent);
//        }else {
//            startService(intent);
//        }

//        initData();
        String[] perm = {Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.ACCESS_COARSE_LOCATION, Permission.RECORD_AUDIO};
        AndPermission.with(this)
                .runtime()
                .permission(perm)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                    }
                })
                .start();
        fragmentManager = getSupportFragmentManager();
        changeTextViewColor();
        changeSelectedTabState(0);
        showFragment(0);
    }

    private void initImageVoice() {
        micImages = new Drawable[]{getResources().getDrawable(R.drawable.ease_record_animate_01),
                getResources().getDrawable(R.drawable.ease_record_animate_02),
                getResources().getDrawable(R.drawable.ease_record_animate_03),
                getResources().getDrawable(R.drawable.ease_record_animate_04),
                getResources().getDrawable(R.drawable.ease_record_animate_05),
                getResources().getDrawable(R.drawable.ease_record_animate_06),
                getResources().getDrawable(R.drawable.ease_record_animate_07),
                getResources().getDrawable(R.drawable.ease_record_animate_08),
                getResources().getDrawable(R.drawable.ease_record_animate_09),
                getResources().getDrawable(R.drawable.ease_record_animate_10),
                getResources().getDrawable(R.drawable.ease_record_animate_11),
                getResources().getDrawable(R.drawable.ease_record_animate_12),
                getResources().getDrawable(R.drawable.ease_record_animate_13),
                getResources().getDrawable(R.drawable.ease_record_animate_14),
                getResources().getDrawable(R.drawable.ease_record_animate_14),
                getResources().getDrawable(R.drawable.ease_record_animate_14),
                getResources().getDrawable(R.drawable.ease_record_animate_14)};
    }

    private boolean flag_voice = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 100) {
                if (flag_voice) {
                    // 设置参数
                    if (null == mIat) {
                        // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
                        SmartToast.show("语音未开启");
                    } else {
                        FlowerCollector.onEvent(MainActivity.this, "iat_recognize");
                        setParam();
                        ret = mIat.startListening(mRecognizerListener);

                        if (ret != ErrorCode.SUCCESS) {
                            SmartToast.show("识别失败");
                        } else {
                            SmartToast.show("开始语音");
//                            startVoice();
                        }
                    }
                }
            } else {
                iv_voice.setImageDrawable(micImages[msg.what]);
            }
        }
    };

    private void initView() {
        frameLayout = findViewById(R.id.fl_container);
        RelativeLayout chatRLayout = (RelativeLayout) findViewById(R.id.seal_chat);
        RelativeLayout contactRLayout = (RelativeLayout) findViewById(R.id.seal_contact_list);
        RelativeLayout foundRLayout = (RelativeLayout) findViewById(R.id.seal_find);
        RelativeLayout mineRLayout = (RelativeLayout) findViewById(R.id.seal_me);
        RelativeLayout voiceRLayout = findViewById(R.id.seal_voice);
        mImageChats = (ImageView) findViewById(R.id.tab_img_chats);
        mImageContact = (ImageView) findViewById(R.id.tab_img_contact);
        mImageFind = (ImageView) findViewById(R.id.tab_img_find);
        mImageMe = (ImageView) findViewById(R.id.tab_img_me);
        mTextChats = (TextView) findViewById(R.id.tab_text_chats);
        mTextContact = (TextView) findViewById(R.id.tab_text_contact);
        mTextFind = (TextView) findViewById(R.id.tab_text_find);
        mTextMe = (TextView) findViewById(R.id.tab_text_me);

        iv_voice = findViewById(R.id.iv_voice);

        chatRLayout.setOnClickListener(this);
        contactRLayout.setOnClickListener(this);
        foundRLayout.setOnClickListener(this);
        mineRLayout.setOnClickListener(this);
        voiceRLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        flag_voice = true;
                        cur_time = System.currentTimeMillis();
                        mHandler.sendEmptyMessageDelayed(100, 1000);
                        break;
                    case MotionEvent.ACTION_UP:
                        long up_time = System.currentTimeMillis();
                        if (up_time - cur_time > 1000) {
                            mIat.stopListening();
                            isRecording=false;
                            iv_voice.setVisibility(View.GONE);
                        } else {
                            flag_voice = false;
                           startActivity(new Intent(MainActivity.this, VoiceActivity.class));
                        }
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
//            SmartToast.show("开始说话");
            iv_voice.setVisibility(View.VISIBLE);
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            mIat.stopListening();
            isRecording=false;
            iv_voice.setVisibility(View.GONE);
            if (error.getErrorCode()==10118){
                SmartToast.show("您没有说话");
            }else {
                SmartToast.show(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
//            SmartToast.show("结束说话");
            mIat.stopListening();
            isRecording=false;
            iv_voice.setVisibility(View.GONE);
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, results.getResultString());
            printResult(results);
        }

        @Override
        public void onVolumeChanged(final int volume, byte[] data) {
//            showTip("当前正在说话，音量大小：" + volume);
            isRecording = true;
            Log.d(TAG, "返回音频数据：" + data.length+"当前正在说话，音量大小：" + volume);
            if (isRecording) {
                android.os.Message msg = new android.os.Message();
                msg.what = volume/2;
                mHandler.sendMessage(msg);
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };
    private boolean isRecording = false;

    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(text)) {
            SmartToast.show(text);
        }
    }

    private void printTransResult(RecognizerResult results) {
        String trans = JsonParser.parseTransResult(results.getResultString(), "dst");
        String oris = JsonParser.parseTransResult(results.getResultString(), "src");

        if (TextUtils.isEmpty(trans) || TextUtils.isEmpty(oris)) {
            SmartToast.show("解析结果失败，请确认是否已开通翻译功能。");
        } else {
            SmartToast.show("原始语言:\n" + oris + "\n目标语言:\n" + trans);
        }

    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                SmartToast.show("初始化失败，错误码：" + code);
            }
        }
    };

    /**
     * 参数设置
     *
     * @return
     */
    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

//        Log.i(TAG, "translate enable");
//        mIat.setParameter(SpeechConstant.ASR_SCH, "1");
//        mIat.setParameter(SpeechConstant.ADD_CAP, "translate");
//        mIat.setParameter(SpeechConstant.TRS_SRC, "its");

        // 设置语言
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        // 设置语言区域
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
        //此处用于设置dialog中不显示错误码信息
        //mIat.setParameter("view_tips_plain","false");

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "10000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, "10000");

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "0");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
    }

    public void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            // 获取状态栏高度
            int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            View rectView = new View(this);
            // 绘制一个和状态栏一样高的矩形，并添加到视图中
            LinearLayout.LayoutParams params
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            rectView.setLayoutParams(params);
            //设置状态栏颜色（该颜色根据你的App主题自行更改）
            rectView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            // 添加矩形View到布局中
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            decorView.addView(rectView);
            ViewGroup rootView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    private void showFragment(int i) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hide(transaction);
        switch (i) {
            case 0:
                if (homeFragment != null) {
                    transaction.show(homeFragment);
                } else {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fl_container, homeFragment);
                }
                break;
            case 1:
                if (deviceFragment != null) {
                    transaction.show(deviceFragment);
                } else {
                    deviceFragment = new DeviceFragment();
                    transaction.add(R.id.fl_container, deviceFragment);
                }
                break;
            case 2:
                if (linkageFragment != null) {
                    transaction.show(linkageFragment);
                } else {
                    linkageFragment = new LinkageFragment();
                    transaction.add(R.id.fl_container, linkageFragment);
                }
                break;
            case 3:
                if (mineFragment != null) {
                    transaction.show(mineFragment);
                } else {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.fl_container, mineFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hide(FragmentTransaction transaction) {

        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (deviceFragment != null) {
            transaction.hide(deviceFragment);
        }
        if (linkageFragment != null) {
            transaction.hide(linkageFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }

    private void changeTextViewColor() {
        mImageChats.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_chat));
        mImageContact.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_contacts));
        mImageFind.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_found));
        mImageMe.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_me));
        mTextChats.setTextColor(Color.parseColor("#abadbb"));
        mTextContact.setTextColor(Color.parseColor("#abadbb"));
        mTextFind.setTextColor(Color.parseColor("#abadbb"));
        mTextMe.setTextColor(Color.parseColor("#abadbb"));
    }

    private void changeSelectedTabState(int position) {
        switch (position) {
            case 0:
                mTextChats.setTextColor(Color.parseColor("#0686DD"));
                mImageChats.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_chat_hover));
                break;
            case 1:
                mTextContact.setTextColor(Color.parseColor("#0686DD"));
                mImageContact.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_contacts_hover));
                break;
            case 2:
                mTextFind.setTextColor(Color.parseColor("#0686DD"));
                mImageFind.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_found_hover));
                break;
            case 3:
                mTextMe.setTextColor(Color.parseColor("#0686DD"));
                mImageMe.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_me_hover));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        changeTextViewColor();
        switch (v.getId()) {
            case R.id.seal_chat:
                changeSelectedTabState(0);
                showFragment(0);
                break;
            case R.id.seal_contact_list:
                changeSelectedTabState(1);
                showFragment(1);
                break;
            case R.id.seal_find:
                changeSelectedTabState(2);
                showFragment(2);
                break;
            case R.id.seal_me:
                changeSelectedTabState(3);
                showFragment(3);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
        if (null != mIat) {
            // 退出时释放连接
            mIat.cancel();
            mIat.destroy();
        }
    }
}
