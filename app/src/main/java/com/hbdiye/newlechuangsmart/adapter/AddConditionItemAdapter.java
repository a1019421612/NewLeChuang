package com.hbdiye.newlechuangsmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hbdiye.newlechuangsmart.R;

import java.util.List;

public class AddConditionItemAdapter extends BaseAdapter {
    private Context context;
    private List<String> mList;
    public AddConditionItemAdapter(Context context, List<String> mList) {
        this.context=context;
        this.mList=mList;
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
        view=LayoutInflater.from(context).inflate(R.layout.device_gv_item,null);
        ImageView iv_icon=view.findViewById(R.id.gridview_item);
        Glide.with(context).load(R.drawable.af).into(iv_icon);
        return view;
    }
}
