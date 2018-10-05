package com.hbdiye.newlechuangsmart.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.hbdiye.newlechuangsmart.fragment.DeviceDetailFragment;
import com.hbdiye.newlechuangsmart.fragment.DeviceListFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/2/23.
 */

public class FragmentDetailPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private List<Fragment> mList_fragment;
    public FragmentDetailPagerAdapter(FragmentManager fm, Context context, List<Fragment> mList_fragment) {
        super(fm);
        this.context=context;
        this.mList_fragment=mList_fragment;
    }

    @Override
    public Fragment getItem(int position) {
        return mList_fragment.get(position);
    }

    @Override
    public int getCount() {
        return mList_fragment.size();
    }
//    @Override
//    public CharSequence getPageTitle(int position) {
//        Log.e("ssss",mList_titles.get(position));
//        return mList_titles.get(position);
//    }
}
