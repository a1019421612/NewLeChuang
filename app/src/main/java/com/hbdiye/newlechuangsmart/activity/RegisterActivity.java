package com.hbdiye.newlechuangsmart.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.RegexUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.phoneEditText)
    EditText phoneEditText;
    @BindView(R.id.codeEditText)
    EditText codeEditText;
    @BindView(R.id.sendCodeButton)
    Button sendCodeButton;
    @BindView(R.id.saveButton)
    TextView saveButton;
    @BindView(R.id.iv_register_back)
    ImageView ivRegisterBack;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;
    @BindView(R.id.nickNameEditText)
    EditText nickNameEditText;
    @BindView(R.id.login_right_img)
    ImageView loginRightImg;
    @BindView(R.id.login_rightimg_rel)
    RelativeLayout loginRightimgRel;

    private String mPhone;
    private String mCode;
    private String mPassword;
    private String mData;
    private TimeCount timeCount;
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
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_register;
    }


    @OnClick({R.id.sendCodeButton, R.id.saveButton, R.id.iv_register_back,R.id.login_rightimg_rel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sendCodeButton:
                if (checkPhone()) {
//                    Toast.makeText(this, "验证码", Toast.LENGTH_SHORT).show();
                    timeCount = new TimeCount(60000, 1000);
                    timeCount.start();
                    getVailCode(mPhone);
                }
                break;
            case R.id.saveButton:
                if (check()) {
                    registerUser(mPhone, mPassword, mCode);
                }
                break;
            case R.id.iv_register_back:
                finish();
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

    private void registerUser(String mPhone, String mPassword, String mCode) {
        OkHttpUtils.post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.REGISTER))
                .addParams("phone", mPhone)
                .addParams("password", mPassword)
                .addParams("code", mCode)
                .addParams("name", mPhone)
                .addParams("icon","ic_head_02")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        SmartToast.show("注册失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("sss", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("errcode");
                            if (result.equals("0")) {
                                SmartToast.show("注册成功");
                                finish();
                            } else {
                                String data = EcodeValue.resultEcode(result);
                                SmartToast.show(data);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void getVailCode(String mPhone) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.GETVAILCODE))
                .addParams("phone", mPhone)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        SmartToast.show("网络连接错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", response);
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    class TimeCount extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            try {
                sendCodeButton.setEnabled(false);
                sendCodeButton.setBackgroundColor(Color.TRANSPARENT);
                sendCodeButton.setText(millisUntilFinished / 1000 + "秒");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFinish() {
            try {
                sendCodeButton.setEnabled(true);
                sendCodeButton.setText("");
                sendCodeButton.setBackgroundResource(R.drawable.ic_code);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private boolean check() {

        if (!checkPhone()) return false;

        mCode = codeEditText.getText().toString();
        if (TextUtils.isEmpty(mCode)) {
            SmartToast.show("请输入验证码");
            return false;
        }
        mPassword = passwordEditText.getText().toString();
        if (TextUtils.isEmpty(mPassword)) {
            SmartToast.show("请输入密码");
            return false;
        }
        return true;
    }

    private boolean checkPhone() {

        mPhone = phoneEditText.getText().toString();
        if (TextUtils.isEmpty(mPhone)) {
            SmartToast.show("请输入手机号");
            return false;
        } else if (!RegexUtils.isMobile(mPhone)) {
            SmartToast.show("请输入有效的手机号");
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeCount != null) {
            timeCount.cancel();
        }
    }
}
