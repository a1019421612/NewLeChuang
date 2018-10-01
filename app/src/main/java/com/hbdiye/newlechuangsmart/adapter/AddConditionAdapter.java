package com.hbdiye.newlechuangsmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.view.MyGridView;

import java.util.List;

public class AddConditionAdapter extends BaseAdapter {
    private Context context;
    private List<String> mList;
    public AddConditionAdapter(Context context, List<String> mList) {
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
        view=LayoutInflater.from(context).inflate(R.layout.add_condition_item,null);
        MyGridView myGridView=view.findViewById(R.id.mlv_condition);
        AddConditionItemAdapter adapter=new AddConditionItemAdapter(context,mList);
        myGridView.setAdapter(adapter);
        return view;
    }
}
