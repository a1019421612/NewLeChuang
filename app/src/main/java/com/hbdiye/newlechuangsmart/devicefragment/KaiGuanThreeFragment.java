package com.hbdiye.newlechuangsmart.devicefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.DeviceClassyBean;
import com.hbdiye.newlechuangsmart.fragment.BaseFragment;
import com.hbdiye.newlechuangsmart.fragment.DeviceListFragment;

public class KaiGuanThreeFragment extends BaseFragment {
    private String data;
    private String proId;
    private TextView tv_name;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_kaiguan_three,container,false);
        tv_name=view.findViewById(R.id.tv_kg_three);
        return view;
    }
    @Override
    protected void onFragmentFirstVisible() {
        Fragment parentFragment = (DeviceListFragment)getParentFragment();
        String data = ((DeviceListFragment) parentFragment).data;
        DeviceClassyBean deviceClassyBean = new Gson().fromJson(data, DeviceClassyBean.class);
        for (int i = 0; i < deviceClassyBean.productList.size(); i++) {
            if ("PRO002003001".equals(deviceClassyBean.productList.get(i).id)){
                DeviceClassyBean.ProductList productList = deviceClassyBean.productList.get(i);
                String name = productList.name;
                tv_name.setText(name);
            }
        }
    }
}
