package com.hbdiye.newlechuangsmart.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.DeviceList;

import java.util.List;

public class AttrDialogAdapter extends BaseAdapter {
    private List<DeviceList.DevActList> mList;
    private Context context;
    private int location = -1;

    public AttrDialogAdapter(Context content, List<DeviceList.DevActList> mList) {
        this.mList = mList;
        this.context = content;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.attr_dialog_item, null);
        TextView tv_attr = convertView.findViewById(R.id.tv_attr_name);
        tv_attr.setText(mList.get(position).name);

        if (position == location) {
            tv_attr.setTextColor(context.getResources().getColor(R.color.white));
            tv_attr.setBackgroundResource(R.drawable.attr_b);
        }
        return convertView;
    }

    public void setSeclection(int position) {
        location = position;
    }
}
