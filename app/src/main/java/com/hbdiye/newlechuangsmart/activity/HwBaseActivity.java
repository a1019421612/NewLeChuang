package com.hbdiye.newlechuangsmart.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.util.ToastUtils;

public class HwBaseActivity extends AppCompatActivity {
    ImageView ivBack;
    TextView tvTitle;
    ImageView ivRight;
    FrameLayout flContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_hw_base);
        initView1();
    }

    private void initView1() {
        flContent = findViewById(R.id.fl_content);
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        ivRight = findViewById(R.id.iv_right);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setContentView(int layoutResID) {
        getLayoutInflater().inflate(layoutResID, flContent, true);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public ImageView getRightView() {
        return ivRight;
    }

    public ImageView getBackView() {
        return ivBack;
    }



    /**
     * 获取点击事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                HideSoftInput(view.getWindowToken());
                view.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判定是否需要隐藏
     */
    public boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 隐藏软键盘
     */
    private void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public Boolean getResultCode(String errorCode) {
        Boolean isOk = true;
//        0 操作成功，500 用户未登录或登录失效，801 参数错误，888 服务端接口错误
//        if (TextUtils.isEmpty(errorCode)) {
//            ToastUtils.showToast(getApplicationContext(), "返回值为空");
//            isOk = false;
//            return isOk;
//        }
        switch (Integer.parseInt(errorCode)) {
            case 0:
                isOk = true;
                break;
            case 500:
                isOk = false;
                ToastUtils.showToast(getApplicationContext(), "用户未登录或登录失效");
                break;
            case 801:
                isOk = false;
                ToastUtils.showToast(getApplicationContext(), "参数错误");
                break;
            case 888:
                isOk = false;
                ToastUtils.showToast(getApplicationContext(), "服务端接口错误");
                break;
        }
        return isOk;
    }
}
