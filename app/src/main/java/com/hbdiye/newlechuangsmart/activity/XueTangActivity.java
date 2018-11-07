package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.view.CircleLoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XueTangActivity extends BaseActivity {

    @BindView(R.id.circleLoadingView)
    CircleLoadingView circleLoadingView;
    @BindView(R.id.tv_xuetang_result)
    TextView tvXuetangResult;
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
        return "血糖";
    }

    @Override
    protected void initView() {
        int v = (int) (3 * 10);
        circleLoadingView.setProgress(v);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_xue_tang;
    }

}
