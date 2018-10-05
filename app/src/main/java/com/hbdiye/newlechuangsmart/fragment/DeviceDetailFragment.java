package com.hbdiye.newlechuangsmart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;

import butterknife.ButterKnife;

public class DeviceDetailFragment extends BaseFragment {
    private String murl;

    public static DeviceDetailFragment newInstance(String page) {
        Bundle args = new Bundle();
        args.putString("args", page);
        DeviceDetailFragment fragment = new DeviceDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        murl = getArguments().getString("args");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        isFirstLoad = true;
        View view=inflater.inflate(R.layout.fragment_device_detail,container,false);

//        isFirstLoad = true;//
//        isPrepared = true;//
//        lazyLoad();
        return view;
    }
}
