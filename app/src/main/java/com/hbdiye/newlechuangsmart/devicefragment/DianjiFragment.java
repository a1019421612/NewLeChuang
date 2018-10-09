package com.hbdiye.newlechuangsmart.devicefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.DeviceClassyBean;
import com.hbdiye.newlechuangsmart.fragment.BaseFragment;
import com.hbdiye.newlechuangsmart.fragment.DeviceListFragment;
import com.zhouyou.view.seekbar.SignSeekBar;

import java.util.Locale;

public class DianjiFragment extends BaseFragment {
    private SignSeekBar signSeekBar;
    private TextView tv_name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dianji,container,false);
        tv_name = view.findViewById(R.id.tv_dj_name);
        signSeekBar = (SignSeekBar) view.findViewById(R.id.seek_bar);
//        signSeekBar.getConfigBuilder()
//                .min(0)
//                .max(100)
//                .progress(2)
//                .trackColor(ContextCompat.getColor(getActivity(), R.color.color_gray))
//                .secondTrackColor(ContextCompat.getColor(getActivity(), R.color.color_blue))
//                .thumbColor(ContextCompat.getColor(getActivity(), R.color.color_blue))
//                .sectionTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary))
//                .sectionTextSize(16)
//                .thumbTextColor(ContextCompat.getColor(getActivity(), R.color.color_red))
//                .thumbTextSize(18)
//                .signColor(ContextCompat.getColor(getActivity(), R.color.color_green))
//                .signTextSize(18)
//                .sectionTextPosition(SignSeekBar.TextPosition.BELOW_SECTION_MARK)
//                .build();
        signSeekBar.setOnProgressChangedListener(new SignSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(SignSeekBar signSeekBar, int progress, float progressFloat, boolean fromUser) {
                //fromUser 表示是否是用户触发 是否是用户touch事件产生
                String s = String.format(Locale.CHINA, "onChanged int:%d, float:%.1f", progress, progressFloat);
//                progressText.setText(s);
            }

            @Override
            public void getProgressOnActionUp(SignSeekBar signSeekBar, int progress, float progressFloat) {
                String s = String.format(Locale.CHINA, "onActionUp int:%d, float:%.1f", progress, progressFloat);
//                progressText.setText(s);
            }

            @Override
            public void getProgressOnFinally(SignSeekBar signSeekBar, int progress, float progressFloat, boolean fromUser) {
                String s = String.format(Locale.CHINA, "onFinally int:%d, float:%.1f", progress, progressFloat);
                Log.e("sss", progress + "℃");
            }
        });
        return view;
    }
    @Override
    protected void onFragmentFirstVisible() {
        Fragment parentFragment = (DeviceListFragment) getParentFragment();
        String data = ((DeviceListFragment) parentFragment).data;
        DeviceClassyBean deviceClassyBean = new Gson().fromJson(data, DeviceClassyBean.class);
        for (int i = 0; i < deviceClassyBean.productList.size(); i++) {
            if ("PRO005002001".equals(deviceClassyBean.productList.get(i).id)) {
                DeviceClassyBean.ProductList productList = deviceClassyBean.productList.get(i);
                String name = productList.name;
                tv_name.setText(name);
            }
        }
    }
}
