package com.hbdiye.newlechuangsmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.ConditionPop;
import com.hbdiye.newlechuangsmart.view.CommentView;
import com.hbdiye.newlechuangsmart.view.ConditionPopwindow;

import java.util.ArrayList;
import java.util.List;

public class LinkageConditionAdapter extends BaseAdapter {
    private Context context;
    private List<String> mList;
    public LinkageConditionAdapter(Context context,List<String> mList) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view=LayoutInflater.from(context).inflate(R.layout.linkage_condition_item,null);
        TextView tv_add_attr=view.findViewById(R.id.tv_add_attr);
        TextView tv_name=view.findViewById(R.id.tv_condition_name);
//        ListView listView=view.findViewById(R.id.mlv_condition);
        LinearLayout ll_root=view.findViewById(R.id.ll_root);
        ll_root.removeAllViews();

        for (int j = 0; j < 3; j++) {
            CommentView commentView = new CommentView(context);
            final TextView tvConditionItem = commentView.getTvConditionItem();
            tvConditionItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<ConditionPop> mList=new ArrayList<>();
                    for (int j = 0; j < 3; j++) {
                        ConditionPop conditionPop=new ConditionPop();
                        conditionPop.setName(j+"");
                        mList.add(conditionPop);
                    }
                    ConditionPopwindow popwindow=new ConditionPopwindow(context,mList);
                    popwindow.showAsDropDown(tvConditionItem);
                }
            });

            ll_root.addView(commentView);
        }
//        LinkageConditionItemAdapter adapter=new LinkageConditionItemAdapter(context,mList,this);
//        listView.setAdapter(adapter);
        tv_add_attr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmartToast.show("添加属性"+mList.get(i));
            }
        });
        tv_name.setText("客厅温度"+mList.get(i));
        return view;
    }
}
