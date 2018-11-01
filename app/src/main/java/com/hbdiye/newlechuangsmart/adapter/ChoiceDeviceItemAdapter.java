package com.hbdiye.newlechuangsmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.RoomDeviceListBean;

import java.util.List;

public class ChoiceDeviceItemAdapter extends BaseAdapter {
    private Context context;
    private List<RoomDeviceListBean.RoomList.DeviceList> mList;
    private int icon;
    public ChoiceDeviceItemAdapter(Context context, List<RoomDeviceListBean.RoomList.DeviceList> mList,int icon) {
        this.context=context;
        this.mList=mList;
        this.icon=icon;
    }

    @Override
    public int getCount() {
        return mList.size();
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
        view=LayoutInflater.from(context).inflate(R.layout.device_choice_item,null);
        TextView tv_content=view.findViewById(R.id.tv_content);
        tv_content.setText(mList.get(i).name);
        ImageView iv_icon=view.findViewById(R.id.gridview_item);
        Glide.with(context).load(icon).into(iv_icon);
        return view;
    }
}
