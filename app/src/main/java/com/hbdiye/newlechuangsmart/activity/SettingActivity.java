package com.hbdiye.newlechuangsmart.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.MyApp;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.util.SPUtils;

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
    @BindView(R.id.tv_setting_version)
    TextView tvSettingVersion;

    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "设置";
    }

    @Override
    protected void initView() {
        String versionName = getVersionName(this);
        tvSettingVersion.setText("V"+versionName);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_setting;
    }

    @OnClick({R.id.ll_setting_reset_psw, R.id.ll_setting_yj, R.id.ll_setting_version, R.id.tv_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_setting_reset_psw:
                startActivity(new Intent(this, ResetPswActivity.class));
                break;
            case R.id.ll_setting_yj:
                break;
            case R.id.ll_setting_version:
                break;
            case R.id.tv_exit:
                SPUtils.clear(this);
                MyApp.finishAllActivity();
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }

    public String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
