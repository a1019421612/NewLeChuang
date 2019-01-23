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
 * 作者：kelingqiu on 18/3/14 17:17
 * 邮箱：42747487@qq.com
 */

public class DeviceActivity extends AppCompatActivity {
    private long deviceId = 0L;
    private Toast toast;
    private TextView content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_device);
        content = findViewById(R.id.content);
        toast = Toast.makeText(this,"", Toast.LENGTH_SHORT);
    }

    public void bind(View view){
        String param = HttpRequestFactory.deviceBinding(HopeLoginBusiness.getInstance().getToken(),"HOPE",
                "6299201702","A7","HOPE-A7","_test",753396045774098432L);

        HopeSDK.getInstance().httpSend("/hopeApi/device/binding", param, new HttpCallback() {
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

    public void getDevice(View view){
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

    public void unbind(View view){
        if (deviceId == 0L){
            showTips("请先确保设备列表中有设备");
            return;
        }
        String params = HttpRequestFactory.deviceUnbinding(deviceId, HopeLoginBusiness.getInstance().getToken());
        HopeSDK.getInstance().httpSend("/hopeApi/device/unbinding", params, new HttpCallback() {

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

    public void catalogList(View view){
        HopeSDK.getInstance().httpSend("/hopeApi/catalog/list", HttpRequestFactory.catalogList(753396045774098432L,1,10, HopeLoginBusiness.getInstance().getToken()), new HttpCallback() {

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

    public void catalogLoad(View view){
        HopeSDK.getInstance().httpSend("/hopeApi/catalog/load", HttpRequestFactory.catalogLoad(754057456103755776L, HopeLoginBusiness.getInstance().getToken()), new HttpCallback() {

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

    public void deviceNicker(View view){
        if (deviceId == 0L){
            showTips("请先确保设备列表中有设备");
            return;
        }
        HopeSDK.getInstance().httpSend("/hopeApi/device/nicker", HttpRequestFactory.deviceNicker(deviceId, HopeLoginBusiness.getInstance().getToken(),"我的设备"), new HttpCallback() {

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
