package com.hbdiye.newlechuangsmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.hbdiye.newlechuangsmart.MainActivity;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.RegexUtils;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.phoneEditText)
    EditText phoneEditText;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;
    @BindView(R.id.registerTextView)
    TextView registerTextView;
    @BindView(R.id.forgetPasswordTextView)
    TextView forgetPasswordTextView;
    @BindView(R.id.login_rightimg_rel)
    RelativeLayout loginRightimgRel;
    @BindView(R.id.login_right_img)
    ImageView loginRightImg;
    private String mPhone;
    private String mPassword;
    private boolean pwdFlag = false;//密码不可见，true密码课件

    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "登录";
    }

    @Override
    protected void initView() {
        titleBarLL.setVisibility(View.GONE);
        ivBaseBack.setVisibility(View.INVISIBLE);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.tv_login, R.id.registerTextView, R.id.forgetPasswordTextView,R.id.login_rightimg_rel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                if (check()) {
//                    Toast.makeText(this, "登录", Toast.LENGTH_SHORT).show();
                    loginLC(mPhone, mPassword);
                }
                break;
            case R.id.registerTextView:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.forgetPasswordTextView:
                startActivity(new Intent(this,ForgetPasswordActivityActivity.class));
                break;
            case R.id.login_rightimg_rel:
                if (pwdFlag) {
                    loginRightImg.setImageResource(R.drawable.login_eye_grayy);
                    pwdFlag = false;
                    //否则隐藏密码
                    passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    loginRightImg.setImageResource(R.drawable.login_eye_lighten);
                    pwdFlag = true;
                    //如果选中，显示密码
                    passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
        }
    }

    private void loginLC(final String mPhone, final String mPassword) {

        OkHttpUtils.get()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.LOGIN))
                .addParams("mobilephone", mPhone)
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
                                SPUtils.put(LoginActivity.this, "mobilephone", mPhone);
                                SPUtils.put(LoginActivity.this, "password", mPassword);
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                String data = jsonObject.getString("errmsg");
                                SmartToast.show(data);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private boolean check() {

        mPhone = phoneEditText.getText().toString();
        mPassword = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(mPhone)) {
            SmartToast.show("请输入手机号");
            return false;
        } else if (!RegexUtils.isMobile(mPhone)) {
            SmartToast.show("请输入有效的手机号");
            return false;
        }
        if (TextUtils.isEmpty(mPassword)) {
            SmartToast.show("请输入密码");
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
