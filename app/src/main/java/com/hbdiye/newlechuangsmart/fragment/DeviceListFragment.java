package com.hbdiye.newlechuangsmart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.FragmentDetailPagerAdapter;
import com.hbdiye.newlechuangsmart.bean.DeviceClassyBean;
import com.hbdiye.newlechuangsmart.devicefragment.ChuangLianFragment;
import com.hbdiye.newlechuangsmart.devicefragment.HongWaiFragment;
import com.hbdiye.newlechuangsmart.devicefragment.KaiGuanThreeFragment;
import com.hbdiye.newlechuangsmart.devicefragment.YanWuCGQFragment;
import com.hbdiye.newlechuangsmart.util.ClassyIconByProId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DeviceListFragment extends BaseFragment {
    @BindView(R.id.viewpager_detail_device)
    ViewPager viewpagerDetailDevice;
    @BindView(R.id.viewGroup)
    LinearLayout mGroup;
    public String data;
    private String proId;
    private Unbinder unbinder;
    private List<Fragment> mList_fragment = new ArrayList<>();
    private List<String> mList_position=new ArrayList<>();
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
    private int flag=-1;
    private String deviceId;

    public static DeviceListFragment newInstance(String data,String id,String productId,String deviceId) {
        Bundle args = new Bundle();

        args.putString("args_page", data);
        args.putString("proId",id);
        args.putString("productId",productId);
        args.putString("deviceId",deviceId);
        DeviceListFragment fragment = new DeviceListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = getArguments().getString("args_page");
        proId= getArguments().getString("proId");
        productId = getArguments().getString("productId");
        deviceId = getArguments().getString("deviceId");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    @Override
    protected void onFragmentFirstVisible() {
        DeviceClassyBean deviceClassyBean = new Gson().fromJson(data, DeviceClassyBean.class);
        List<DeviceClassyBean.DeviceList> productList = deviceClassyBean.deviceList;
        for (int i = 0; i < productList.size(); i++) {
            if ((productList.get(i).productId).contains(proId)){
                String deviceid = productList.get(i).id;
                mList_fragment.add(ClassyIconByProId.fragmentById(productList.get(i).productId,deviceid));
                mList_position.add(productList.get(i).id);
            }
        }
        for (int i = 0; i < mList_position.size(); i++) {
            if (mList_position.get(i).equals(deviceId)){
                flag=i;
            }
        }
        fragmentDetailPagerAdapter = new FragmentDetailPagerAdapter(getChildFragmentManager(), getActivity(), mList_fragment);
        viewpagerDetailDevice.setAdapter(fragmentDetailPagerAdapter);
        if (flag!=-1){
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
            mImageView = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mImageView.setLayoutParams(params);
            params.leftMargin=30;
            mImageViews[i] = mImageView;
            if (flag!=-1){
                if (i == flag) {
                    mImageViews[i].setBackgroundResource(R.mipmap.banner_dian_focus);
                } else {
                    mImageViews[i].setBackgroundResource(R.mipmap.banner_dian_blur);
                }
            }else {
                if (i == 0) {
                    mImageViews[i].setBackgroundResource(R.mipmap.banner_dian_focus);
                } else {
                    mImageViews[i].setBackgroundResource(R.mipmap.banner_dian_blur);
                }
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
//    @Override
//    protected void lazyLoad() {
//        super.lazyLoad();
//        if (isPrepared && isVisible && isFirstLoad) {
//            SmartToast.show(proId);
//        }
//
//    }

}
