package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.FragmentDetailPagerAdapter;
import com.hbdiye.newlechuangsmart.bean.GateWayBean;
import com.hbdiye.newlechuangsmart.devicefragment.WangGuanFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZhuJiDetailActivity extends AppCompatActivity {

    @BindView(R.id.viewpager_detail_device)
    ViewPager viewpagerDetailDevice;
    @BindView(R.id.viewGroup)
    LinearLayout mGroup;
    @BindView(R.id.iv_zj_back)
    ImageView ivZjBack;
    @BindView(R.id.tv_zj_title)
    TextView tvZjTitle;
    private List<Fragment> mList_fragment = new ArrayList<>();
    private List<String> mList_position = new ArrayList<>();
    FragmentDetailPagerAdapter fragmentDetailPagerAdapter;


    /**
     * 滚动图片指示视图列表
     */
    private ImageView[] mImageViews = null;
    /**
     * 图片轮播指示个图
     */
    private ImageView mImageView = null;
    private String productId;
    private int flag = -1;
    private String deviceId;
    private String data = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_ji_detail);
        ButterKnife.bind(this);
        initView();
    }


    protected void initView() {
        tvZjTitle.setText("智能主机");
        data = getIntent().getStringExtra("data");
        deviceId = getIntent().getStringExtra("deviceId");
        if (TextUtils.isEmpty(data)) {
            return;
        }
        GateWayBean roomDeviceListBean = new Gson().fromJson(data, GateWayBean.class);
        List<GateWayBean.RoomList> roomList = roomDeviceListBean.roomList;
        for (int i = 0; i < roomList.size(); i++) {
            List<GateWayBean.RoomList.GatewayList> deviceList = roomList.get(i).gatewayList;
            for (int j = 0; j < deviceList.size(); j++) {
                String deviceid = deviceList.get(j).id;
                String name = deviceList.get(j).name;
                String mac = deviceList.get(j).mac;
                mList_fragment.add(WangGuanFragment.newInstance(deviceid, name, mac));
                mList_position.add(deviceList.get(j).id);
            }
        }
//        List<DeviceClassyBean.DeviceList> productList = deviceClassyBean.deviceList;
//        for (int i = 0; i < productList.size(); i++) {
//                String deviceid = productList.get(i).id;
//                mList_fragment.add(WangGuanFragment.newInstance(deviceid));
//                mList_position.add(productList.get(i).id);
//        }
        for (int i = 0; i < mList_position.size(); i++) {
            if (mList_position.get(i).equals(this.deviceId)) {
                flag = i;
            }
        }
        fragmentDetailPagerAdapter = new FragmentDetailPagerAdapter(getSupportFragmentManager(), this, mList_fragment);
        viewpagerDetailDevice.setAdapter(fragmentDetailPagerAdapter);
        if (flag != -1) {
            viewpagerDetailDevice.setCurrentItem(flag);
        }
        viewPagerIndicator();
    }

    /**
     * 添加指示图片
     */
    private void viewPagerIndicator() {
        int imageCount = mList_fragment.size();
        mGroup.removeAllViews();
        mImageViews = new ImageView[imageCount];
        for (int i = 0; i < imageCount; i++) {
            mImageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mImageView.setLayoutParams(params);
            params.leftMargin = 30;
            mImageViews[i] = mImageView;
            if (i == flag) {
                mImageViews[i].setBackgroundResource(R.mipmap.banner_dian_focus);
            } else {
                mImageViews[i].setBackgroundResource(R.mipmap.banner_dian_blur);
            }
            mGroup.addView(mImageViews[i]);
        }
        viewpagerDetailDevice.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int index) {
                index = index % mImageViews.length;
                // 设置当前显示的图片
//                mImageIndex = index;
                // 设置图片滚动指示器背
                mImageViews[index].setBackgroundResource(R.mipmap.banner_dian_focus);
                for (int i = 0; i < mImageViews.length; i++) {
                    if (index != i) {
                        mImageViews[i].setBackgroundResource(R.mipmap.banner_dian_blur);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int index) {
            }
        });
    }

    @OnClick(R.id.iv_zj_back)
    public void onViewClicked() {
        finish();
    }
}
