package com.hbdiye.newlechuangsmart.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.hbdiye.newlechuangsmart.MainActivity;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class WelcomeActivity extends AppCompatActivity {
    private String userName;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    String mobilephone = (String) SPUtils.get(WelcomeActivity.this, "mobilephone", "");
                    String password = (String) SPUtils.get(WelcomeActivity.this, "password", "");
                    loginLC(mobilephone,password);
                    break;
                case 200:
                    startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        userName= (String) SPUtils.get(this,"mobilephone","");
        if (TextUtils.isEmpty(userName)){
            handler.sendEmptyMessageDelayed(200,3000);
        }else {
            handler.sendEmptyMessageDelayed(100,3000);
        }
    }
    private void loginLC(final String mPhone, final String mPassword) {

        OkHttpUtils.get()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.LOGIN))
                .addParams("phone", mPhone)
                .addParams("password", mPassword)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        SmartToast.show("网络连接错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int result = jsonObject.getInt("errcode");
                            if (result == 0) {
//                                JSONObject jsonObject1=jsonObject.getJSONObject("data");
//                                String mobilephone = jsonObject1.getString("mobilephone");
//                                String password = jsonObject1.getString("password");
                                String token = jsonObject.getString("token");
                                String url = jsonObject.getString("url");
                                SPUtils.put(WelcomeActivity.this, "mobilephone", mPhone);
                                SPUtils.put(WelcomeActivity.this, "password", mPassword);
                                SPUtils.put(WelcomeActivity.this,"token",token);
                                SPUtils.put(WelcomeActivity.this,"url",url+"/token="+token);
                                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                                finish();
                            } else {
                                String s = EcodeValue.resultEcode(result+"");
                                SmartToast.show(s);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
