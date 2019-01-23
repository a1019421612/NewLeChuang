package com.hbdiye.newlechuangsmart.music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.BaseActivity;
import com.lib.smartlib.HopeLoginBusiness;
import com.lib.smartlib.callback.ILoginCallback;


/**
 * 作者：kelingqiu on 18/3/14 16:45
 * 邮箱：42747487@qq.com
 */

public class LoginActivity extends BaseActivity {
    EditText userIdEdit,userAccountEdit;
    TextView loginStatus;
    Button login,logout;
    private Toast toast;

    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "登录";
    }

    @Override
    protected void initView() {
        toast = Toast.makeText(this,"", Toast.LENGTH_SHORT);
        userIdEdit = findViewById(R.id.userId);
        userAccountEdit = findViewById(R.id.userAccount);
        loginStatus = findViewById(R.id.login_status);
        login = findViewById(R.id.login);
        logout = findViewById(R.id.logout);
        userIdEdit.setText("123456");
        userAccountEdit.setText("15556928882");
        setLoginStatus(HopeLoginBusiness.getInstance().isLogin());
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_music_login;
    }

    public void login(View view){
        String userId = userIdEdit.getText().toString();
        String userAccount = userAccountEdit.getText().toString();
        if (userId.isEmpty() || userAccount.isEmpty()){
            showTips("唯一编码或者用户名不能为空");
            return;
        }
        HopeLoginBusiness.getInstance().openLogin(userId, userAccount, new ILoginCallback() {
            @Override
            public void onSuccess(String success) {
                Log.d("HopeSDK","success:" + success);
                setLoginStatus(true);
            }

            @Override
            public void onFailure(String error) {
                Log.d("HopeSDK", "error:" + error);
            }
        });

    }

    public void logout(View view){
        HopeLoginBusiness.getInstance().logout(new ILoginCallback() {
            @Override
            public void onSuccess(String success) {
                Log.d("HopeSDK","success:" + success);
                setLoginStatus(false);
            }

            @Override
            public void onFailure(String error) {
                Log.d("HopeSDK", "error:" + error);
                setLoginStatus(false);
            }
        });
    }

    private void setLoginStatus(final boolean isLogin){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isLogin){
                    loginStatus.setText("已登录");
                    userIdEdit.setVisibility(View.GONE);
                    userAccountEdit.setVisibility(View.GONE);
                    login.setVisibility(View.GONE);
                    logout.setVisibility(View.VISIBLE);
                }else{
                    loginStatus.setText("未登录");
                    userIdEdit.setVisibility(View.VISIBLE);
                    userAccountEdit.setVisibility(View.VISIBLE);
                    login.setVisibility(View.VISIBLE);
                    logout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void showTips(String tips){
        toast.setText(tips);
        toast.show();
    }
}
