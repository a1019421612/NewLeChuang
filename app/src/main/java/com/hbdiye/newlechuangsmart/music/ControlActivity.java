package com.hbdiye.newlechuangsmart.music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.music.factory.HttpRequestFactory;
import com.hbdiye.newlechuangsmart.music.factory.TcpRequestFactory;
import com.lib.smartlib.HopeLoginBusiness;
import com.lib.smartlib.HopeSDK;
import com.lib.smartlib.callback.HttpCallback;
import com.lib.smartlib.callback.MsgCallback;


/**
 * 作者：kelingqiu on 18/3/19 13:52
 * 邮箱：42747487@qq.com
 */

public class ControlActivity extends AppCompatActivity {
    private long deviceId = 0L;
    private Toast toast;
    private TextView content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_control);
        content = findViewById(R.id.content);
        HopeSDK.getInstance().addMsgCallback(tcpCallback);
        toast = Toast.makeText(this,"", Toast.LENGTH_SHORT);
    }

    private MsgCallback tcpCallback = new MsgCallback() {
        @Override
        public void onMsgReceive(Long deviceId, int messageId, String data) {
            String result = "deviceId:" + deviceId + " messageId:" + String.format("%04x", messageId) + " data:" + data;
            Log.d("control", result);
            showContent(result);

        }
    };

    @Override
    protected void onDestroy() {
        HopeSDK.getInstance().removeMsgCallback(tcpCallback);
        super.onDestroy();
    }

    public void getDevice(View view) {
        String params = HttpRequestFactory.deviceList(1, 1, 10, HopeLoginBusiness.getInstance().getToken());
        HopeSDK.getInstance().httpSend("/hopeApi/device/list", params, new HttpCallback() {

            @Override
            public void onSuccess(String success) {
                showContent(success);
                Log.d("HopeSDK", "success:" + success);
                JsonArray ja = new JsonParser().parse(success).getAsJsonObject().getAsJsonArray("rows");
                for (int i = 0; i < ja.size(); i++) {
                    if (ja.get(0).getAsJsonObject().get("onlineStatus").getAsBoolean()){
                        deviceId = ja.get(0).getAsJsonObject().get("deviceId").getAsLong();
                        break;
                    }
                }
            }

            @Override
            public void onFailure(String error) {
                Log.d("HopeSDK", "error:" + error);
                showContent(error);
            }
        });
    }

    public void play(View view) {
        if (deviceId == 0L){
            showTips("请先确保设备列表中有可控音乐设备");
            return;
        }
        try {
            HopeSDK.getInstance().tcpSend(0x0005, TcpRequestFactory.playRequest(deviceId));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void pause(View view) {
        if (deviceId == 0L){
            showTips("请先确保设备列表中有可控音乐设备");
            return;
        }
        try {
            HopeSDK.getInstance().tcpSend(0x0005, TcpRequestFactory.pauseRequest(deviceId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void next(View view) {
        if (deviceId == 0L){
            showTips("请先确保设备列表中有可控音乐设备");
            return;
        }
        try {
            HopeSDK.getInstance().tcpSend(0x0005, TcpRequestFactory.nextRequest(deviceId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void last(View view) {
        if (deviceId == 0L){
            showTips("请先确保设备列表中有可控音乐设备");
            return;
        }
        try {
            HopeSDK.getInstance().tcpSend(0x0005, TcpRequestFactory.prevRequest(deviceId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playWithId(View view) {
        if (deviceId == 0L){
            showTips("请先确保设备列表中有可控音乐设备");
            return;
        }
        try {
            HopeSDK.getInstance().tcpSend(0x0005, TcpRequestFactory.playWithIdRequest(deviceId,0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showTips(String tips){
        toast.setText(tips);
        toast.show();
    }

    private void showContent(final String text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                content.setText(text);
            }
        });
    }
}
