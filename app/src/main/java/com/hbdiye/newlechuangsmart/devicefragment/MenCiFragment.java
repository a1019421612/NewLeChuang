package com.hbdiye.newlechuangsmart.devicefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.DeviceClassyBean;
import com.hbdiye.newlechuangsmart.fragment.BaseFragment;
import com.hbdiye.newlechuangsmart.fragment.DeviceListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MenCiFragment extends BaseFragment {

    @BindView(R.id.iv_mc)
    ImageView ivMc;
    @BindView(R.id.tv_mc)
    TextView tvMc;
    @BindView(R.id.ll_mc)
    LinearLayout llMc;
    private TextView tv_name;
    private Unbinder bind;
    private DeviceClassyBean.ProductList productList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menci, container, false);
        bind = ButterKnife.bind(this, view);
        tv_name = view.findViewById(R.id.tv_menci_name);
        return view;
    }

    //    PRO003004001
    @Override
    protected void onFragmentFirstVisible() {
        Fragment parentFragment = (DeviceListFragment) getParentFragment();
        String data = ((DeviceListFragment) parentFragment).data;
        DeviceClassyBean deviceClassyBean = new Gson().fromJson(data, DeviceClassyBean.class);
        for (int i = 0; i < deviceClassyBean.productList.size(); i++) {
            if ("PRO003004001".equals(deviceClassyBean.productList.get(i).id)) {
                productList = deviceClassyBean.productList.get(i);
                String name = productList.name;
                tv_name.setText(name);
            }
        }
        DeviceClassyBean.ProductList.DeviceList.DevAttList devAttList = productList.deviceList.get(0).devAttList.get(0);
        int value = devAttList.value;
        if (value==0){
            ivMc.setImageResource(R.drawable.mohu);
            tvMc.setText("关");
            tvMc.setTextColor(getResources().getColor(R.color.detail_text));
        }else {
            ivMc.setImageResource(R.drawable.kaiguan);
            tvMc.setText("开");
            tvMc.setTextColor(getResources().getColor(R.color.bar_text_sel));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @OnClick(R.id.ll_mc)
    public void onViewClicked() {
    }
}