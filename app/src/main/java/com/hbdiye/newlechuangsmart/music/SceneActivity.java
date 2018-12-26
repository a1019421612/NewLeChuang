package com.hbdiye.newlechuangsmart.music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.music.bean.SystemAlarmList;
import com.hbdiye.newlechuangsmart.music.factory.HttpRequestFactory;
import com.hbdiye.newlechuangsmart.music.factory.TcpRequestFactory;
import com.lib.smartlib.HopeLoginBusiness;
import com.lib.smartlib.HopeSDK;
import com.lib.smartlib.callback.HttpCallback;
import com.lib.smartlib.callback.MsgCallback;
import com.lib.smartlib.tcp.utils.ByteUtils;


/**
 * 作者：kelingqiu on 18/3/19 16:19
 * 邮箱：42747487@qq.com
 */

public class SceneActivity extends AppCompatActivity {
    private long sceneId = 0L;
    private Toast toast;
    private TextView content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_scene);
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

    public void searchScene(View view){
        String params = HttpRequestFactory.sceneSearch(HopeLoginBusiness.getInstance().getToken(),1,10);
        HopeSDK.getInstance().httpSend("/hopeApi/scene/search", params, new HttpCallback() {
            @Override
            public void onSuccess(String success) {
                showContent(success);
                Log.d("HopeSDK", "success:" + success);
                JsonArray ja = new JsonParser().parse(success).getAsJsonObject().getAsJsonArray("rows");
                for (int i = 0; i < ja.size(); i++) {
                    sceneId = ja.get(0).getAsJsonObject().get("refrenceId").getAsLong();
                    break;
                }
            }

            @Override
            public void onFailure(String error) {
                Log.d("HopeSDK", "error:" + error);
                showContent(error);
            }
        });
    }

    public void sceneDetail(View view){
        if (sceneId == 0L){
            showTips("请先确保情景列表中有情景");
            return;
        }
        String params = HttpRequestFactory.sceneLoad(HopeLoginBusiness.getInstance().getToken(),sceneId);
        HopeSDK.getInstance().httpSend("/hopeApi/scene/load", params, new HttpCallback() {
            @Override
            public void onSuccess(String success) {
                showContent(success);
                Log.d("HopeSDK", "success:" + success);
            }

            @Override
            public void onFailure(String error) {
                Log.d("HopeSDK", "error:" + error);
                showContent(error);
            }
        });
    }

    public void createScene(View view){
        try{
            JsonArray jsonArray = new JsonArray();
            JsonObject config = new JsonObject();
            config.addProperty("devId", ByteUtils.encodePrimary(779872055301537792L));
            config.addProperty("sceId",-1);
            config.addProperty("sceType",0);
            config.addProperty("sceBell",0);
            config.addProperty("sceMus", SystemAlarmList.getInstance().getSystemAlarms().get(0));
            config.addProperty("sceRate",1);
            config.addProperty("sceDate","");
            config.addProperty("sceValue","2");
            config.addProperty("sceTime","2000");
            config.addProperty("operate",1);
            jsonArray.add(config);
            HopeSDK.getInstance().tcpSend(0x0085, TcpRequestFactory.createScene("我的情景",1,jsonArray));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delScene(View view){
        try{
            HopeSDK.getInstance().tcpSend(0x0180, TcpRequestFactory.delScene(String.valueOf(sceneId)));
        }catch (Exception e){
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
