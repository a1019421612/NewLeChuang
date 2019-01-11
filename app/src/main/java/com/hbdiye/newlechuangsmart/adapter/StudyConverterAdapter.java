package com.hbdiye.newlechuangsmart.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.SelectStudyBean;

import java.util.List;

public class StudyConverterAdapter extends BaseAdapter {


    private final List<SelectStudyBean.Data.KeyList> keyList;


    public StudyConverterAdapter(List<SelectStudyBean.Data.KeyList> keyList) {
        this.keyList = keyList;
    }

    @Override
    public int getCount() {
        return keyList.size();
    }

    @Override
    public Object getItem(int position) {
        return keyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_study_converter, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(keyList.get(position).fname);

        return convertView;
    }

    static class ViewHolder {
        TextView tvName;
    }

}
