package com.hbdiye.newlechuangsmart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.FragmentDetailPagerAdapter;
import com.hbdiye.newlechuangsmart.devicefragment.ChuangLianFragment;
import com.hbdiye.newlechuangsmart.devicefragment.HongWaiFragment;
import com.hbdiye.newlechuangsmart.devicefragment.YanWuCGQFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyDeviceListFragment extends BaseFragment {
//    @BindView(R.id.viewpager_detail_device)
//    ViewPager viewpagerDetailDevice;
////    @BindView(R.id.tablayout_device_detail)
////    TabLayout tablayoutDeviceDetail;
//    @BindView(R.id.viewGroup)
//    LinearLayout mGroup;
    private String murl;
//    private Unbinder unbinder;
//    private List<String> mList_titles = new ArrayList<>();
//    public List<String> channelitem_selected = new ArrayList<>();
//    private List<Fragment> mList_fragment = new ArrayList<>();
//    FragmentDetailPagerAdapter fragmentDetailPagerAdapter;


//    /**
//     * 滚动图片指示视图列表
//     */
//    private ImageView[] mImageViews = null;
//    /**
//     * 图片轮播指示个图
//     */
//    private ImageView mImageView = null;

    public static MyDeviceListFragment newInstance(String page) {
        Bundle args = new Bundle();

//        args.putInt(ARGS_PAGE, page);
        args.putString("args_page", page);
        MyDeviceListFragment fragment = new MyDeviceListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        murl = getArguments().getString("args_page");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 如果这里有数据累加的Bug 请在initViews方法里初始化您的数据 比如 list.clear();
//        isFirstLoad = true;
        View view = inflater.inflate(R.layout.fragment_mydevice_list, container, false);
//        unbinder = ButterKnife.bind(this, view);
        TextView tv=view.findViewById(R.id.tv_content);
        tv.setText(murl);
//        isFirstLoad = true;//
//        isPrepared = true;//
//        if (murl.equals("0")) {
//            mList_fragment.add(new HongWaiFragment());
//            mList_titles.add("1");
//        } else if (murl.equals("1")) {
//            mList_fragment.add(new YanWuCGQFragment());
//            mList_fragment.add(new ChuangLianFragment());
//            mList_titles.add("1");
//            mList_titles.add("2");
//        } else {
//            mList_fragment.add(new ChuangLianFragment());
//            mList_fragment.add(new YanWuCGQFragment());
//            mList_fragment.add(new HongWaiFragment());
//            mList_titles.add("1");
//            mList_titles.add("2");
//            mList_titles.add("3");
//        }
//        fragmentDetailPagerAdapter = new FragmentDetailPagerAdapter(getChildFragmentManager(), getActivity(), mList_titles, mList_fragment);
//        viewpagerDetailDevice.setAdapter(fragmentDetailPagerAdapter);
//        tablayoutDeviceDetail.setupWithViewPager(viewpagerDetailDevice);
//        tablayoutDeviceDetail.setTabMode(TabLayout.MODE_SCROLLABLE);

//        int imageCount = mList_fragment.size();
//        mGroup.removeAllViews();
//        mImageViews = new ImageView[imageCount];
//        for (int i = 0; i < imageCount; i++) {
//            mImageView = new ImageView(getActivity());
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            mImageView.setLayoutParams(params);
//            params.leftMargin=30;
//            mImageViews[i] = mImageView;
//            if (i == 0) {
//                mImageViews[i].setBackgroundResource(R.mipmap.banner_dian_focus);
//            } else {
//                mImageViews[i].setBackgroundResource(R.mipmap.banner_dian_blur);
//            }
//            mGroup.addView(mImageViews[i]);
//        }
//        viewpagerDetailDevice.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int index) {
//                index = index % mImageViews.length;
//                // 设置当前显示的图片
////                mImageIndex = index;
//                // 设置图片滚动指示器背
//                mImageViews[index].setBackgroundResource(R.mipmap.banner_dian_focus);
//                for (int i = 0; i < mImageViews.length; i++) {
//                    if (index != i) {
//                        mImageViews[i].setBackgroundResource(R.mipmap.banner_dian_blur);
//                    }
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int index) {
//            }
//        });
//        lazyLoad();
        return view;
    }

//    @Override
//    protected void lazyLoad() {
//        super.lazyLoad();
//        if (isPrepared && isVisible && isFirstLoad) {
//
////            SmartToast.show(murl);
//        }
//
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }
}
