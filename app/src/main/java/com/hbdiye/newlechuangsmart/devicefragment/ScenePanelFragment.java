package com.hbdiye.newlechuangsmart.devicefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.fragment.BaseFragment;

public class ScenePanelFragment extends BaseFragment {
    private String deviceid;

    public static ScenePanelFragment newInstance(String page) {
        Bundle args = new Bundle();

//        args.putInt(ARGS_PAGE, page);
        args.putString("deviceid", page);
        ScenePanelFragment fragment = new ScenePanelFragment();
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
        View view=inflater.inflate(R.layout.fragment_scenepanel,container,false);
        return view;
    }
    @Override
    protected void onFragmentFirstVisible() {

    }
}
