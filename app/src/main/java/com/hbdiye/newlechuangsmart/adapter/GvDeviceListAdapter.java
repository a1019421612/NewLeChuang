package com.hbdiye.newlechuangsmart.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.DeviceBean;

import java.util.List;

public class GvDeviceListAdapter extends BaseAdapter {


    private final List<DeviceBean> deviceList;

    public GvDeviceListAdapter(List<DeviceBean> device) {
        this.deviceList = device;
    }

    @Override
    public int getCount() {
        return deviceList.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return deviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_remote_control, null);
            holder = new ViewHolder();
            holder.pic = convertView.findViewById(R.id.iv_pic);
            holder.name = convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(position==deviceList.size()){
            holder.pic.setImageResource(R.mipmap.tianjia);
            holder.name.setText("添加");
        }else {
            DeviceBean device = deviceList.get(position);
        holder.pic.setImageResource(deviceList.get(position).deviceUrl);
            holder.name.setText(device.deviceName);
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView pic;
        TextView name;
    }
}