package com.hbdiye.newlechuangsmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hbdiye.newlechuangsmart.R;

import java.util.List;

public class LinkageConditionItemAdapter extends BaseAdapter {
    private Context context;
    private List<String> mList;
    private LinkageConditionAdapter linkageConditionAdapter;
    public LinkageConditionItemAdapter(Context context, List<String> mList, LinkageConditionAdapter linkageConditionAdapter) {
        this.context=context;
        this.mList=mList;
        this.linkageConditionAdapter=linkageConditionAdapter;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view=LayoutInflater.from(context).inflate(R.layout.condition_item,null);
        ImageView iv_del=view.findViewById(R.id.iv_condition_del);
        iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.remove(i);
                linkageConditionAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }
}
