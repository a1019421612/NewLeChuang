package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.hbdiye.newlechuangsmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddSceneDeviceActivity extends BaseActivity {

    @BindView(R.id.rv_add_scene_device)
    RecyclerView rvAddSceneDevice;

    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "选择设备";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_add_scene_device;
    }
}
