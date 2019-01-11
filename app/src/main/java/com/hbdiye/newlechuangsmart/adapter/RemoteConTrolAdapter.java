package com.hbdiye.newlechuangsmart.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;
public class RemoteConTrolAdapter extends BaseAdapter {

    private final int[] icon;
    private final String[] name;

    public RemoteConTrolAdapter(int[] icon, String[] name) {
        this.icon = icon;
        this.name = name;
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        return name[position];
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

        holder.pic.setImageResource(icon[position]);
        holder.name.setText(name[position]);

        return convertView;
    }

    static class ViewHolder {
        ImageView pic;
        TextView name;
    }
}