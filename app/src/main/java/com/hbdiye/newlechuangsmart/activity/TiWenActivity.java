package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.view.CircleProgressBar;
import com.yiwent.viewlib.ShiftyTextview;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TiWenActivity extends BaseActivity {

    @BindView(R.id.progress_bar)
    CircleProgressBar progressBar;
    @BindView(R.id.stv_tzl)
    ShiftyTextview stvTzl;
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
        return "体温";
    }

    @Override
    protected void initView() {
        stvTzl.setPostfixString("℃");
        stvTzl.setDuration(3000);
        double adiposerate1 = 20;
        if (adiposerate1>42){
            adiposerate1=42;
            progressBar.setAnimProgress(100);
        }else {
            progressBar.setAnimProgress((100/42)*adiposerate1);
        }
        stvTzl.setNumberString(36 + "");
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_ti_wen;
    }

}
