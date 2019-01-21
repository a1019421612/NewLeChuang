package com.hbdiye.newlechuangsmart.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.hbdiye.newlechuangsmart.MainActivity;
import com.hbdiye.newlechuangsmart.MyWebSocketHandler;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.SingleWebSocketConnection;
import com.hbdiye.newlechuangsmart.adapter.VoiceAdapter;
import com.hbdiye.newlechuangsmart.bean.VoiceListBean;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.JsonParser;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.sunflower.FlowerCollector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.tavendo.autobahn.WebSocketConnection;

public class VoiceActivity extends BaseActivity {

    private static final String TAG = "VoiceActivity";
    @BindView(R.id.rv_voice)
    RecyclerView rvVoice;
    @BindView(R.id.iv_voice_record)
    ImageView iv_voice_record;
    @BindView(R.id.iv_voice)
    ImageView iv_voice;
    private boolean flag_voice = false;
    private long cur_time;
    private MediaRecorder recorder;
    protected Drawable[] micImages;
    private boolean isRecording = false;
    // 语音听写对象
    private SpeechRecognizer mIat;
    int ret = 0; // 函数调用返回值
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;

    private List<VoiceListBean> list=new ArrayList<>();
    private VoiceAdapter adapter;


    private WebSocketConnection mConnection;
    public MyWebSocketHandler instance;
    private HomeReceiver homeReceiver;
    private String token;

    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "语音助手";
    }

    @Override
    protected void initView() {
        token = (String) SPUtils.get(this, "token", "");
//        initWebSocket();
        mConnection = SingleWebSocketConnection.getInstance();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("GOPP");
        intentFilter.addAction("CSPP");
        homeReceiver = new HomeReceiver();
        registerReceiver(homeReceiver, intentFilter);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvVoice.setLayoutManager(manager);
        adapter=new VoiceAdapter(list);
        rvVoice.setAdapter(adapter);
        initImageVoice();
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(this, mInitListener);
        onViewClicked();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_voice;
    }
    class HomeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String message = intent.getStringExtra("message");
            if (action.equals("CSPP")){
                try {
                    JSONObject jsonObject=new JSONObject(message);
                    String ecode = jsonObject.getString("ecode");
                    if (ecode.equals("0")){
                        String msg = jsonObject.getString("msg");
                        VoiceListBean left=new VoiceListBean();
                        left.isleft=true;
                        left.msg=msg;
                        list.add(left);
                        adapter.notifyDataSetChanged();
                    }else {
                        String s = EcodeValue.resultEcode(ecode);
                        SmartToast.show(s);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
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

    public void onViewClicked() {
        iv_voice_record.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        flag_voice = true;
                        cur_time = System.currentTimeMillis();
                        mHandler.sendEmptyMessageDelayed(100, 100);
                        break;
                    case MotionEvent.ACTION_UP:
                        long up_time = System.currentTimeMillis();
                        if (up_time - cur_time > 100) {
                            mIat.stopListening();
                            isRecording = false;
                            iv_voice.setVisibility(View.GONE);
                        }
                        break;
                }
                return true;
            }
        });
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
                getResources().getDrawable(R.drawable.ease_record_animate_15),
                getResources().getDrawable(R.drawable.ease_record_animate_16),
                getResources().getDrawable(R.drawable.ease_record_animate_17),
                getResources().getDrawable(R.drawable.ease_record_animate_18),
                getResources().getDrawable(R.drawable.ease_record_animate_19),
                getResources().getDrawable(R.drawable.ease_record_animate_20),
                getResources().getDrawable(R.drawable.ease_record_animate_21),
                getResources().getDrawable(R.drawable.ease_record_animate_22),
                getResources().getDrawable(R.drawable.ease_record_animate_23),
                getResources().getDrawable(R.drawable.ease_record_animate_24),
                getResources().getDrawable(R.drawable.ease_record_animate_25),
                getResources().getDrawable(R.drawable.ease_record_animate_26),
                getResources().getDrawable(R.drawable.ease_record_animate_27),
                getResources().getDrawable(R.drawable.ease_record_animate_28),
                getResources().getDrawable(R.drawable.ease_record_animate_29),
                getResources().getDrawable(R.drawable.ease_record_animate_29),
                getResources().getDrawable(R.drawable.ease_record_animate_29),
                getResources().getDrawable(R.drawable.ease_record_animate_29),

        };
    }

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
                        FlowerCollector.onEvent(VoiceActivity.this, "iat_recognize");
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
            if (error.getErrorCode() == 10118) {
                SmartToast.show("您没有说话");
            } else {
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
        public void onVolumeChanged(int volume, byte[] data) {
//            showTip("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据：" + data.length);
            isRecording = true;
            if (isRecording) {
                android.os.Message msg = new android.os.Message();
                msg.what = volume;
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
            mConnection.sendTextMessage("{\"pn\":\"CSPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"905\",\"msg\":\""+text+"\"}");
            VoiceListBean voiceListBean=new VoiceListBean();
            voiceListBean.isleft=false;
            voiceListBean.msg=text;
            list.add(voiceListBean);
            adapter.notifyDataSetChanged();
//            SmartToast.show("翻译" + text);
        }
    }

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
        mIat.setParameter(SpeechConstant.VAD_BOS, "40000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, "10000");

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "0");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(homeReceiver);
        if (null != mIat) {
            // 退出时释放连接
            mIat.cancel();
            mIat.destroy();
        }
    }
}
