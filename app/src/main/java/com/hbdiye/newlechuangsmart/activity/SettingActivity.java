package com.hbdiye.newlechuangsmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.ll_setting_reset_psw)
    LinearLayout llSettingResetPsw;
    @BindView(R.id.ll_setting_yj)
    LinearLayout llSettingYj;
    @BindView(R.id.ll_setting_version)
    LinearLayout llSettingVersion;
    @BindView(R.id.tv_exit)
    TextView tvExit;

    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "设置";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_setting;
    }

    @OnClick({R.id.ll_setting_reset_psw, R.id.ll_setting_yj, R.id.ll_setting_version, R.id.tv_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_setting_reset_psw:
                startActivity(new Intent(this,ResetPswActivity.class));
                break;
            case R.id.ll_setting_yj:
                break;
            case R.id.ll_setting_version:
                break;
            case R.id.tv_exit:
                break;
        }
    }
}
