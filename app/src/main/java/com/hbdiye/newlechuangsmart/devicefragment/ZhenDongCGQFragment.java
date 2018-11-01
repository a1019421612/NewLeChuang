package com.hbdiye.newlechuangsmart.devicefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.fragment.BaseFragment;

public class ZhenDongCGQFragment extends BaseFragment {
    private String deviceid;

    public static ZhenDongCGQFragment newInstance(String page) {
        Bundle args = new Bundle();

//        args.putInt(ARGS_PAGE, page);
        args.putString("deviceid", page);
        ZhenDongCGQFragment fragment = new ZhenDongCGQFragment();
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
        View view=inflater.inflate(R.layout.fragment_zhendong,container,false);
        return view;
    }
    @Override
    protected void onFragmentFirstVisible() {
//        SmartToast.show(deviceid);
//        Fragment parentFragment = (DeviceListFragment)getParentFragment();
//        String data = ((DeviceListFragment) parentFragment).data;
//        DeviceClassyBean deviceClassyBean = new Gson().fromJson(data, DeviceClassyBean.class);
//        for (int i = 0; i < deviceClassyBean.productList.size(); i++) {
//            if ("PRO003006004".equals(deviceClassyBean.productList.get(i).id)){
//                DeviceClassyBean.ProductList productList = deviceClassyBean.productList.get(i);
//                String name = productList.name;
//                tv_sj.setText(name);
//            }
//        }
//        SmartToast.show("水浸传感器获取父控件数据"+data);
    }
}
