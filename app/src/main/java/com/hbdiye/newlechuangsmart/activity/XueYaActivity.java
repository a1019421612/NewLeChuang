package com.hbdiye.newlechuangsmart.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;
import com.yiwent.viewlib.ShiftyTextview;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XueYaActivity extends BaseActivity {

    @BindView(R.id.stv_gaoya)
    ShiftyTextview stvGaoya;
    @BindView(R.id.stv_diya)
    ShiftyTextview stvDiya;
    @BindView(R.id.tv_rcjy)
    TextView tvRcjy;
    @BindView(R.id.tv_ydjy)
    TextView tvYdjy;
    @BindView(R.id.tv_ysjy)
    TextView tvYsjy;
    @BindView(R.id.tv_pulse)
    TextView tvPulse;
    @BindView(R.id.iv_xueya)
    ImageView ivXueya;
    @BindView(R.id.rv_time)
    RecyclerView rvTime;

    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "血压";
    }

    @Override
    protected void initView() {
        AnimationDrawable animationDrawable = (AnimationDrawable) ivXueya.getDrawable();
        animationDrawable.start();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_xue_ya;
    }

}
