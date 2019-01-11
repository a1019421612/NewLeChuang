package com.hbdiye.newlechuangsmart.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;

public class GvBindConverterAdapter extends BaseAdapter {


    private final String[] name;
    private int selectPosition = 0;

    public GvBindConverterAdapter(String[] name) {
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
        ViewHolder holder;
        if(convertView == null){
            convertView = View.inflate(parent.getContext(), R.layout.item_bind_converter_gradview, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(name[position]);
        if(position==selectPosition){
            holder.tvName.setBackgroundColor(parent.getContext().getResources().getColor(R.color.qian_blue));
            holder.tvName.setTextColor(parent.getContext().getResources().getColor(R.color.white));
        }else {
            holder.tvName.setBackgroundColor(parent.getContext().getResources().getColor(R.color.white));
            holder.tvName.setTextColor(parent.getContext().getResources().getColor(R.color.text_color));
        }
        return convertView;
    }

    static class ViewHolder{
        TextView tvName;
    }

    public void setSelectPosition(int position){
        selectPosition = position;
    }
}
