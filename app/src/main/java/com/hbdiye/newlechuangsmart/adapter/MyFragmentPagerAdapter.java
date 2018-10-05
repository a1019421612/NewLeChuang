package com.hbdiye.newlechuangsmart.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.hbdiye.newlechuangsmart.fragment.DeviceFragment;
import com.hbdiye.newlechuangsmart.fragment.DeviceListFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/2/23.
 */

public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> mList_id;
    private Context context;
    private String response;
    public MyFragmentPagerAdapter(FragmentManager fm, Context context, List<String> mList_id,String response) {
        super(fm);
        this.context=context;
        this.mList_id=mList_id;
        this.response=response;

    }

    @Override
    public Fragment getItem(int position) {
        return DeviceListFragment.newInstance(response,mList_id.get(position));
    }

    @Override
    public int getCount() {
        return mList_id.size();
    }
//    @Override
//    public CharSequence getPageTitle(int position) {
////        Log.e("ssss",mList_id.get(position));
//        return ""+mList_id.get(position);
//    }
}
