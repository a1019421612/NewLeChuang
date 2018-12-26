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
 * 作者：kelingqiu on 18/3/19 19:27
 * 邮箱：42747487@qq.com
 */

public class PartitionActivity extends AppCompatActivity {
    private long deviceId = 0L;
    private Toast toast;
    private TextView content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_partition);
        content = findViewById(R.id.content);
        toast = Toast.makeText(this,"", Toast.LENGTH_SHORT);
        HopeSDK.getInstance().addMsgCallback(tcpCallback);
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

    public void loadDevice(View view){
        String params = HttpRequestFactory.deviceList(1,1,10, HopeLoginBusiness.getInstance().getToken());
        HopeSDK.getInstance().httpSend("/hopeApi/device/list", params, new HttpCallback() {

            @Override
            public void onSuccess(String success) {
                Log.d("HopeSDK","success:" + success);
                showContent(success);
                JsonArray ja = new JsonParser().parse(success).getAsJsonObject().getAsJsonArray("rows");
                if (ja.size() > 0){
                    deviceId = ja.get(0).getAsJsonObject().get("deviceId").getAsLong();
                }
            }

            @Override
            public void onFailure(String error) {
                Log.d("HopeSDK", "error:" + error);
                showContent(error);
            }
        });
    }

    public void getStatus(View view){
        if (deviceId == 0L){
            showTips("请先确保设备列表中有可控音乐设备");
            return;
        }
        try {
            HopeSDK.getInstance().tcpSend(0x0055, TcpRequestFactory.getPartionStatus(deviceId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOpen(View view){
        if (deviceId == 0L){
            showTips("请先确保设备列表中有可控音乐设备");
            return;
        }
        try {
            HopeSDK.getInstance().tcpSend(0x0065, TcpRequestFactory.setPartion(deviceId,1,true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setClose(View view){
        if (deviceId == 0L){
            showTips("请先确保设备列表中有可控音乐设备");
            return;
        }
        try {
            HopeSDK.getInstance().tcpSend(0x0065, TcpRequestFactory.setPartion(deviceId,1,false));
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
