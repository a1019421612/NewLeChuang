package com.hbdiye.newlechuangsmart.devicefragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.DeviceModelActivity;
import com.hbdiye.newlechuangsmart.adapter.HWDeviceAdapter;
import com.hbdiye.newlechuangsmart.fragment.BaseFragment;
import com.hbdiye.newlechuangsmart.view.HwIconPopwindow;

import java.util.ArrayList;
import java.util.List;

public class HongWaiFragment extends BaseFragment {
    private LinearLayout ll_root;
    private RecyclerView recyclerView;
    private HWDeviceAdapter adapter;
    private ImageView iv_hw_add;
    private List<String> mList=new ArrayList<>();

    private List<Integer> mList_icon=new ArrayList<>();
    private List<String> mList_name=new ArrayList<>();
    private HwIconPopwindow popwindow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_hongwai,container,false);
        recyclerView=view.findViewById(R.id.rv_hw_device);
        iv_hw_add=view.findViewById(R.id.iv_hw_add);
        ll_root=view.findViewById(R.id.ll_root);
        for (int i = 0; i < 10; i++) {
            mList.add(i+"");
        }
        mList_icon.add(R.drawable.jidinghe);
        mList_name.add("机顶盒");
        mList_icon.add(R.drawable.dianshiji);
        mList_name.add("电视机");
        mList_icon.add(R.drawable.kongtiao);
        mList_name.add("空调");
        mList_icon.add(R.drawable.hezi);
        mList_name.add("盒子");
        mList_icon.add(R.drawable.dvd);
        mList_name.add("DVD");
        mList_icon.add(R.drawable.touyingyi);
        mList_name.add("投影仪");
        mList_icon.add(R.drawable.fengshan);
        mList_name.add("风扇");
        mList_icon.add(R.drawable.gongfang);
        mList_name.add("功放");
        mList_icon.add(R.drawable.danfanxiangji);
        mList_name.add("单反相机");

        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        adapter=new HWDeviceAdapter(mList);
        recyclerView.setAdapter(adapter);

        iv_hw_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popwindow=new HwIconPopwindow(getActivity(),mList_icon,mList_name,clicker);
                popwindow.showPopupWindowBottom(ll_root);

            }
        });
        return view;
    }
    private BaseQuickAdapter.OnItemClickListener clicker=new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            startActivity(new Intent(getActivity(),DeviceModelActivity.class));
            popwindow.dismiss();
        }
    };
}
