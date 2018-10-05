package com.hbdiye.newlechuangsmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.hbdiye.newlechuangsmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 遥控中心
 */
public class YaoKongCenterActivity extends BaseActivity {

    @BindView(R.id.ll_center)
    LinearLayout llCenter;

    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "遥控中心";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_yao_kong_center;
    }


    @OnClick(R.id.ll_center)
    public void onViewClicked() {
        startActivity(new Intent(this,InfraredActivity.class));
    }
}
