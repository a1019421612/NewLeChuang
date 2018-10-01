package com.hbdiye.newlechuangsmart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.RoomActivity;
import com.hbdiye.newlechuangsmart.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DeviceFragment extends Fragment {
    @BindView(R.id.gv_common_classify)
    MyGridView gvCommonClassify;
    Unbinder unbinder;
    @BindView(R.id.gv_device_classify)
    MyGridView gvDeviceClassify;
    @BindView(R.id.tv_device_edit)
    TextView tvDeviceEdit;
    private List<Integer> mList = new ArrayList<>();
    private
    List<Integer> mList_device = new ArrayList<>();
    private Myadapter mMyadapter;
    private MyDeviceadapter device_adapter;

    private boolean flag = false;//编辑设备分类

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device, container, false);
        unbinder = ButterKnife.bind(this, view);
        for (int i = 0; i < 8; i++) {
            mList.add(R.drawable.af);
        }
        for (int i = 0; i < 5; i++) {
            mList_device.add(R.drawable.zhuwo);
        }
        mMyadapter = new Myadapter();
        gvCommonClassify.setAdapter(mMyadapter);
        device_adapter = new MyDeviceadapter();
        gvDeviceClassify.setAdapter(device_adapter);
        handleClicker();
        return view;
    }

    private void handleClicker() {
        gvDeviceClassify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(),RoomActivity.class));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_device_edit)
    public void onViewClicked() {
        if (flag){
            tvDeviceEdit.setText("编辑");
            flag=false;
        }else {
            flag=true;
            tvDeviceEdit.setText("完成");
        }
        device_adapter.notifyDataSetChanged();
    }

    class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size() + 1 == 6 ? 6 : mList.size() + 1;
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = LayoutInflater.from(getActivity()).inflate(R.layout.device_gv_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.gridview_item);
            if (mList.size() == i) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(getActivity()).load(R.drawable.other).into(imageView);
                if (i == 7) {
                    imageView.setVisibility(View.GONE);
                }
            } else {
                Glide.with(getActivity()).load(mList.get(i)).into(imageView);
            }

            return view;
        }
    }

    class MyDeviceadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList_device.size() + 1 == 9 ? 9 : mList_device.size() + 1;
        }

        @Override
        public Object getItem(int i) {
            return mList_device.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = LayoutInflater.from(getActivity()).inflate(R.layout.device_gv_edit_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.gridview_item);
            ImageView iv_del=view.findViewById(R.id.iv_device_del);
            if (flag){
                iv_del.setVisibility(View.VISIBLE);
            }else {
                iv_del.setVisibility(View.GONE);
            }
            if (mList_device.size() == i) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(getActivity()).load(R.drawable.home_add).into(imageView);
                iv_del.setVisibility(View.GONE);
                if (i == 10) {
                    imageView.setVisibility(View.GONE);
                }
            } else {
                Glide.with(getActivity()).load(mList_device.get(i)).into(imageView);
            }

            return view;
        }
    }
}
