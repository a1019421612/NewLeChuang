package com.hbdiye.newlechuangsmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.hbdiye.newlechuangsmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 医疗列表界面
 */
public class HealthActivity extends BaseActivity {

    @BindView(R.id.ll_health_tizhong)
    LinearLayout llHealthTizhong;
    @BindView(R.id.ll_health_tizhilv)
    LinearLayout llHealthTizhilv;
    @BindView(R.id.ll_health_xuetang)
    LinearLayout llHealthXuetang;
    @BindView(R.id.ll_health_tiwen)
    LinearLayout llHealthTiwen;
    @BindView(R.id.ll_health_xueya)
    LinearLayout llHealthXueya;

    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "健康医疗";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_health;
    }

    @OnClick({R.id.ll_health_tizhong, R.id.ll_health_tizhilv, R.id.ll_health_xuetang, R.id.ll_health_tiwen, R.id.ll_health_xueya})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_health_tizhong:
                startActivity(new Intent(this,TiZhongActivity.class));
                break;
            case R.id.ll_health_tizhilv:
                startActivity(new Intent(this,TiZhiLvActivity.class));
                break;
            case R.id.ll_health_xuetang:
                startActivity(new Intent(this,XueTangActivity.class));
                break;
            case R.id.ll_health_tiwen:
                startActivity(new Intent(this,TiWenActivity.class));
                break;
            case R.id.ll_health_xueya:
                startActivity(new Intent(this,XueYaActivity.class));
                break;
        }
    }
}
