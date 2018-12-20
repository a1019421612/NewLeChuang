package com.hbdiye.newlechuangsmart.activity;

import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.hbdiye.newlechuangsmart.MainActivity;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.util.JsonParser;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "语音助手";
    }

    @Override
    protected void initView() {
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
                        mHandler.sendEmptyMessageDelayed(100, 1000);
                        break;
                    case MotionEvent.ACTION_UP:
                        long up_time = System.currentTimeMillis();
                        if (up_time - cur_time > 1000) {
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
                getResources().getDrawable(R.drawable.ease_record_animate_14),};
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
    private void startVoice() {
        iv_voice.setVisibility(View.VISIBLE);
        try {
            if (recorder != null) {
                recorder.release();
                recorder = null;
            }
            String dir = Environment.getExternalStorageDirectory() + "/recorder_audios";
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdir();
            }

            recorder = new MediaRecorder();
            String fileName = "aaa.amr";
            File files = new File(dir, fileName);
            recorder.setOutputFile(files.getAbsolutePath());
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//            recorder.setAudioChannels(1); // MONO
//            recorder.setAudioSamplingRate(8000); // 8000Hz
//            recorder.setAudioEncodingBitRate(64); // seems if chang

//            voiceFileName = getVoiceFileName(EMClient.getInstance().getCurrentUser());
//            voiceFilePath = PathUtil.getInstance().getVoicePath() + "/" + voiceFileName;

            recorder.prepare();
            recorder.start();
            isRecording = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isRecording) {
                        android.os.Message msg = new android.os.Message();
                        msg.what = recorder.getMaxAmplitude() * 13 / 0x7FFF;
                        mHandler.sendMessage(msg);
                        SystemClock.sleep(100);
                    }
                } catch (Exception e) {
                    // from the crash report website, found one NPE crash from
                    // one android 4.0.4 htc phone
                    // maybe handler is null for some reason
//                    EMLog.e("voice", e.toString());
                }
            }
        }).start();
    }
    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
//            SmartToast.show("开始说话");
            startVoice();
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
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
            SmartToast.show("翻译" + text);
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
        if (null != mIat) {
            // 退出时释放连接
            mIat.cancel();
            mIat.destroy();
        }
    }
}
