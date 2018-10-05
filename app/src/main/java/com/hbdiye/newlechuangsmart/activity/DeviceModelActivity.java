package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.hbdiye.newlechuangsmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 设备型号
 */
public class DeviceModelActivity extends BaseActivity {

    @BindView(R.id.rv_device_model)
    RecyclerView rvDeviceModel;

    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "型号";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_device_model;
    }
}
