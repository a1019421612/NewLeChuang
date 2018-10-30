package com.hbdiye.newlechuangsmart.devicefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.fragment.BaseFragment;

public class KaiGuanOneFragment extends BaseFragment {

    private String deviceid;

    public static KaiGuanOneFragment newInstance(String page) {
        Bundle args = new Bundle();

//        args.putInt(ARGS_PAGE, page);
        args.putString("deviceid", page);
        KaiGuanOneFragment fragment = new KaiGuanOneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deviceid = getArguments().getString("deviceid");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_kaiguan_one,container,false);
        return view;
    }

    @Override
    protected void onFragmentFirstVisible() {
        SmartToast.show(deviceid);
    }
}
