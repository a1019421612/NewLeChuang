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
import com.lib.smartlib.HopeLoginBusiness;
import com.lib.smartlib.HopeSDK;
import com.lib.smartlib.callback.HttpCallback;


/**
 * 作者：kelingqiu on 18/3/15 10:02
 * 邮箱：42747487@qq.com
 */

public class MusicActivity extends AppCompatActivity {
    private long deviceId = 0L;
    private Toast toast;
    private TextView content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_music);
        content = findViewById(R.id.content);
        toast = Toast.makeText(this,"", Toast.LENGTH_SHORT);
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

    public void localList(View view){
        if (deviceId == 0L){
            showTips("请先添加设备，并获取设备列表");
            return;
        }
        HopeSDK.getInstance().httpSend("/hopeApi/sheet/list", HttpRequestFactory.sheetList(deviceId, HopeLoginBusiness.getInstance().getToken(),0,10), new HttpCallback() {
            @Override
            public void onSuccess(String success) {
                Log.d("HopeSDK","success:" + success);
                showContent(success);
            }

            @Override
            public void onFailure(String error) {
                Log.d("HopeSDK", "error:" + error);
                showContent(error);
            }
        });
    }

    public void loadSongs(View view){
        if (deviceId == 0L){
            showTips("请先添加设备，并获取设备列表");
            return;
        }
        HopeSDK.getInstance().httpSend("/hopeApi/music/listMusic", HttpRequestFactory.listMusic(deviceId, HopeLoginBusiness.getInstance().getToken(),0,10), new HttpCallback() {
            @Override
            public void onSuccess(String success) {
                Log.d("HopeSDK","success:" + success);
                showContent(success);
            }

            @Override
            public void onFailure(String error) {
                Log.d("HopeSDK", "error:" + error);
                showContent(error);
            }
        });
    }

    public void loadAuthor(View view){
        if (deviceId == 0L){
            showTips("请先添加设备，并获取设备列表");
            return;
        }
        HopeSDK.getInstance().httpSend("/hopeApi/music/listAuthor", HttpRequestFactory.listAuthor(deviceId, HopeLoginBusiness.getInstance().getToken(),0,10), new HttpCallback() {
            @Override
            public void onSuccess(String success) {
                Log.d("HopeSDK","success:" + success);
                showContent(success);
            }

            @Override
            public void onFailure(String error) {
                Log.d("HopeSDK", "error:" + error);
                showContent(error);
            }
        });
    }

    public void loadAlbum(View view){
        if (deviceId == 0L){
            showTips("请先添加设备，并获取设备列表");
            return;
        }
        HopeSDK.getInstance().httpSend("/hopeApi/music/listAlbum", HttpRequestFactory.listAlbum(deviceId, HopeLoginBusiness.getInstance().getToken(),0,10), new HttpCallback() {
            @Override
            public void onSuccess(String success) {
                Log.d("HopeSDK","success:" + success);
                showContent(success);
            }

            @Override
            public void onFailure(String error) {
                Log.d("HopeSDK", "error:" + error);
                showContent(error);
            }
        });
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
