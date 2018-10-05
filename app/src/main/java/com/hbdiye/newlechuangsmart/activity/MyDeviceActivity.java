package com.hbdiye.newlechuangsmart.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.DevicePagerAdapter;
import com.hbdiye.newlechuangsmart.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyDeviceActivity extends BaseActivity {
    @BindView(R.id.tablayout_device)
    TabLayout tablayoutDevice;
    @BindView(R.id.viewpager_device)
    ViewPager viewpagerDevice;
    private List<String> mList_titles = new ArrayList<>();
    public List<String> channelitem_selected = new ArrayList<>();
    DevicePagerAdapter myFragmentPagerAdapter;
    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "我的设备";
    }

    @Override
    protected void initView() {
        for (int i = 0; i < 5; i++) {
            mList_titles.add(i+"");
        }
        myFragmentPagerAdapter = new DevicePagerAdapter(getSupportFragmentManager(), this, mList_titles);
        viewpagerDevice.setAdapter(myFragmentPagerAdapter);
        tablayoutDevice.setupWithViewPager(viewpagerDevice);
        tablayoutDevice.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_my_device;
    }
}
