package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.view.CircleProgressBar;
import com.yiwent.viewlib.ShiftyTextview;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TiZhiLvActivity extends BaseActivity {

    @BindView(R.id.progress_bar)
    CircleProgressBar progressBar;
    @BindView(R.id.stv_tzl)
    ShiftyTextview stvTzl;
    @BindView(R.id.progress_bar1)
    CircleProgressBar progressBar1;
    @BindView(R.id.stv_sfl)
    ShiftyTextview stvSfl;
    @BindView(R.id.progress_bar2)
    CircleProgressBar progressBar2;
    @BindView(R.id.stv_jrl)
    ShiftyTextview stvJrl;
    @BindView(R.id.progress_bar3)
    CircleProgressBar progressBar3;
    @BindView(R.id.stv_nzzfdj)
    ShiftyTextview stvNzzfdj;
    @BindView(R.id.tv_rcjy)
    TextView tvRcjy;
    @BindView(R.id.tv_ydjy)
    TextView tvYdjy;
    @BindView(R.id.tv_ysjy)
    TextView tvYsjy;
    @BindView(R.id.rv_time)
    RecyclerView rvTime;

    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "体脂率";
    }

    @Override
    protected void initView() {
        progressBar.setAnimProgress(10);
        progressBar1.setAnimProgress(20);
        progressBar2.setAnimProgress(60);
        progressBar3.setAnimProgress(80);

        stvTzl.setPostfixString("%");
        stvTzl.setDuration(3000);
        stvTzl.setNumberString(10 + "");

        stvSfl.setPostfixString("%");
        stvSfl.setDuration(3000);
        stvSfl.setNumberString(20 + "");

        stvJrl.setPostfixString("%");
        stvJrl.setDuration(3000);
        stvJrl.setNumberString(60 + "");

        stvNzzfdj.setDuration(3000);
        stvNzzfdj.setNumberString(80 + "");
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_ti_zhi_lv;
    }

}
