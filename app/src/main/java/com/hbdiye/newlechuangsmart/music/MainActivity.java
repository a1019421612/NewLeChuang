package com.hbdiye.newlechuangsmart.music;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.BaseActivity;
import com.hbdiye.newlechuangsmart.music.factory.TcpRequestFactory;
import com.lib.smartlib.HopeLoginBusiness;
import com.lib.smartlib.HopeSDK;

public class MainActivity extends BaseActivity{

    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "音乐";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_music_main;
    }

    public void login(View view) {
        startActivity(new Intent(this,LoginActivity.class));
    }
        public void device(View View) {
        if(checkLogin()){
            startActivity(new Intent(this, DeviceActivity.class));
        }
    }

    public void user(View View){
        if (checkLogin()){
            startActivity(new Intent(this, UserActivity.class));
        }
    }

    public void music(View View){
//        val jo = com.google.gson.JsonObject()
//        jo.addProperty("receive", "13780096118")
//        HopeSDK.getInstance().tcpSend(0x0210, jo.toString())
        if (checkLogin()){
            startActivity(new Intent(this, MusicActivity.class));
        }
    }

    public void control(View View){
//        val jo = com.google.gson.JsonObject()
//        jo.addProperty("receive", "13780096118")
//        HopeSDK.getInstance().tcpSend(0x0210, jo.toString())
        if (checkLogin()){
            startActivity(new Intent(this, ControlActivity.class));
        }
    }

    public void playlist(View View){
        if (checkLogin()){
            startActivity(new Intent(this, PlaylistActivity.class));
        }
    }

    public void scene(View View){
        if (checkLogin()){
            startActivity(new Intent(this, SceneActivity.class));
        }
    }

    public void channel(View View){
        if (checkLogin()){
            startActivity(new Intent(this, PartitionActivity.class));
        }
    }

    private  boolean checkLogin(){
        if(!HopeLoginBusiness.getInstance().isLogin()){
            Toast.makeText(this,"请先登录",Toast.LENGTH_SHORT).show();
        }
        return HopeLoginBusiness.getInstance().isLogin();
    }
}
