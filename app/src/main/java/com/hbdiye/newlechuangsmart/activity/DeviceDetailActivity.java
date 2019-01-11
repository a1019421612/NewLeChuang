package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.FragmentDetailPagerAdapter;
import com.hbdiye.newlechuangsmart.adapter.RoomDeviceDetailAdapter;
import com.hbdiye.newlechuangsmart.bean.DeviceList;
import com.hbdiye.newlechuangsmart.bean.DeviceListSceneBean;
import com.hbdiye.newlechuangsmart.util.ClassyIconByProId;
import com.hbdiye.newlechuangsmart.util.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceDetailActivity extends AppCompatActivity {

    @BindView(R.id.iv_device_detail_back)
    ImageView ivDeviceDetailBack;
    @BindView(R.id.tv_device_detail_title)
    TextView tvDeviceDetailTitle;
    @BindView(R.id.vp_device_detail)
    ViewPager vpDeviceDetail;
    @BindView(R.id.viewGroup)
    LinearLayout viewGroup;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    /**
     * 滚动图片指示视图列表
     */
    private ImageView[] mImageViews = null;
    /**
     * 图片轮播指示个图
     */
    private ImageView mImageView = null;
//    private int flag=-1;

    private String productId;
    private String all_data;
    private String deviceId;
    private List<DeviceListSceneBean.RoomList> roomList;
    private String roomId;
    private List<Fragment> mList_fragment = new ArrayList<>();
    FragmentDetailPagerAdapter fragmentDetailPagerAdapter;

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        ButterKnife.bind(this);

        productId = getIntent().getStringExtra("productId");
        all_data = getIntent().getStringExtra("all_data");
        roomId = getIntent().getStringExtra("roomId");
        deviceId = getIntent().getStringExtra("deviceId");
        handleData();
    }

    private void handleData() {
        DeviceListSceneBean deviceListSceneBean = new Gson().fromJson(all_data, DeviceListSceneBean.class);
        roomList = deviceListSceneBean.roomList;
        //重新排列房间列表，点击房间在首位
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).id.equals(roomId)) {
                DeviceListSceneBean.RoomList roomList_target = this.roomList.get(i);
                roomList.remove(i);
                roomList.add(0, roomList_target);
            }
        }
        String roomName = roomList.get(0).name;
        tvDeviceDetailTitle.setText(roomName);
        //首个房间里的所有设备，且设备为点击的设备
        List<DeviceList> deviceList = roomList.get(0).deviceList;
        for (int i = 0; i < deviceList.size(); i++) {
            String deviceid = deviceList.get(i).id;
            for (int j = 0; j < deviceList.size(); j++) {
                if (deviceList.get(j).productId.contains("PRO006")){
                    deviceList.remove(j);
                }
            }
            if (productId.equals(deviceList.get(i).productId) && deviceId.equals(deviceid)) {
                mList_fragment.add(0, ClassyIconByProId.fragmentById(deviceList.get(i).productId, deviceid));
            } else {
                mList_fragment.add(ClassyIconByProId.fragmentById(deviceList.get(i).productId, deviceid));
            }
        }
        fragmentDetailPagerAdapter = new FragmentDetailPagerAdapter(getSupportFragmentManager(), this, mList_fragment);
        vpDeviceDetail.setAdapter(fragmentDetailPagerAdapter);
        vpDeviceDetail.setCurrentItem(0);
        viewPagerIndicator();
    }

    /**
     * 添加指示图片
     */
    private void viewPagerIndicator() {
        int imageCount = mList_fragment.size();
        viewGroup.removeAllViews();
        mImageViews = new ImageView[imageCount];
        for (int i = 0; i < imageCount; i++) {
            mImageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mImageView.setLayoutParams(params);
            params.leftMargin = 10;
            mImageViews[i] = mImageView;
//            if (flag != -1) {
//                if (i == flag) {
//                    mImageViews[i].setBackgroundResource(R.mipmap.banner_dian_focus);
//                } else {
//                    mImageViews[i].setBackgroundResource(R.mipmap.banner_dian_blur);
//                }
//            } else {
            if (i == 0) {
                mImageViews[i].setBackgroundResource(R.mipmap.banner_dian_focus);
            } else {
                mImageViews[i].setBackgroundResource(R.mipmap.banner_dian_blur);
            }
//            }
            viewGroup.addView(mImageViews[i]);
        }
        vpDeviceDetail.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    @OnClick({R.id.iv_device_detail_back, R.id.tv_device_detail_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_device_detail_back:
                finish();
                break;
            case R.id.tv_device_detail_title:
                View view_room = getLayoutInflater().inflate(R.layout.room_list, null);
                popupWindow = new PopupWindow(view_room, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                RecyclerView rv_room = view_room.findViewById(R.id.rv_room_list);
                LinearLayoutManager manager = new LinearLayoutManager(this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                rv_room.setLayoutManager(manager);
                RoomDeviceDetailAdapter adapter = new RoomDeviceDetailAdapter(roomList);
                rv_room.setAdapter(adapter);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_linkage_corner_shape));
                popupWindow.showAsDropDown(tvDeviceDetailTitle);
                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        tvDeviceDetailTitle.setText(roomList.get(position).name);
                        roomId = roomList.get(position).id;
                        List<DeviceList> deviceList = roomList.get(position).deviceList;
                        vpDeviceDetail.removeAllViews();
                        mList_fragment.clear();
                        for (int i = 0; i < deviceList.size(); i++) {
                            String deviceid = deviceList.get(i).id;
                            if (!deviceList.get(i).productId.contains("PRO006")) {
                                mList_fragment.add(ClassyIconByProId.fragmentById(deviceList.get(i).productId, deviceid));
                            }
                        }
                        viewPagerIndicator();
                        fragmentDetailPagerAdapter = new FragmentDetailPagerAdapter(getSupportFragmentManager(), DeviceDetailActivity.this, mList_fragment);
                        vpDeviceDetail.setAdapter(fragmentDetailPagerAdapter);
                        vpDeviceDetail.setCurrentItem(0);
                        popupWindow.dismiss();
                    }
                });
                break;
            default:
                break;
        }
    }
}
