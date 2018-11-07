package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.view.DashboardView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TiZhongActivity extends BaseActivity {

    @BindView(R.id.dashboard_view)
    DashboardView dashboardView;
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
        dashboardView.setCreditValueWithAnim(currentKG(80));
        dashboardView.setWeightValue(String.valueOf(80));
    }

    @Override
    protected String getTitleName() {
        return "体重";
    }

    @Override
    protected void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_ti_zhong;
    }

    private int currentKG(double i) {
        return (int) (i + 350);
    }

}
