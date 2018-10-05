package com.hbdiye.newlechuangsmart.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.hbdiye.newlechuangsmart.fragment.DeviceListFragment;
import com.hbdiye.newlechuangsmart.fragment.MyDeviceListFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/2/23.
 */

public class DevicePagerAdapter extends FragmentStatePagerAdapter {
    private List<String> mList_titles;
    private Context context;
    public DevicePagerAdapter(FragmentManager fm, Context context, List<String> mList_titles) {
        super(fm);
        this.context=context;
        this.mList_titles=mList_titles;

    }

    @Override
    public Fragment getItem(int position) {
        return MyDeviceListFragment.newInstance(mList_titles.get(position));
    }

    @Override
    public int getCount() {
        return mList_titles.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        Log.e("ssss",mList_titles.get(position));
        return "设备"+mList_titles.get(position);
    }
}
