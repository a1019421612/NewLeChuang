package com.hbdiye.newlechuangsmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.ConditionPop;
import com.hbdiye.newlechuangsmart.bean.SceneDetailBean;
import com.hbdiye.newlechuangsmart.view.CommentSceneView;
import com.hbdiye.newlechuangsmart.view.CommentView;
import com.hbdiye.newlechuangsmart.view.ConditionPopwindow;

import java.util.ArrayList;
import java.util.List;

public class SceneDetailListAdapter extends BaseAdapter {
    private Context context;
    private List<SceneDetailBean.DeviceList> mList;
    public SceneDetailListAdapter(Context context, List<SceneDetailBean.DeviceList> mList) {
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
        view=LayoutInflater.from(context).inflate(R.layout.scene_device_header,null);
        TextView tv_name=view.findViewById(R.id.tv_scene_device_item);
        TextView tv_edit=view.findViewById(R.id.tv_scene_device_edit);
//        ListView listView=view.findViewById(R.id.mlv_condition);
        LinearLayout ll_root=view.findViewById(R.id.ll_root);
        ll_root.removeAllViews();

        for (int j = 0; j < mList.get(i).devAttList.size(); j++) {
            CommentSceneView commentView = new CommentSceneView(context);
            List<SceneDetailBean.DeviceList.DevAttList> devAttList = mList.get(i).devAttList;
            String name = devAttList.get(i).name;
            int index = devAttList.get(i).index;
            commentView.setTvSceneDeviceName(name);
            if (index==0){
                commentView.setTvSceneDeviceAttr("关");
            }else if (index==1){
                commentView.setTvSceneDeviceAttr("开");
            }
//            final TextView tvConditionItem = commentView.getTvConditionItem();
//            tvConditionItem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    List<ConditionPop> mList=new ArrayList<>();
//                    for (int j = 0; j < 3; j++) {
//                        ConditionPop conditionPop=new ConditionPop();
//                        conditionPop.setName(j+"");
//                        mList.add(conditionPop);
//                    }
//                    ConditionPopwindow popwindow=new ConditionPopwindow(context,mList);
//                    popwindow.showAsDropDown(tvConditionItem);
//                }
//            });

            ll_root.addView(commentView);
        }
//        LinkageConditionItemAdapter adapter=new LinkageConditionItemAdapter(context,mList,this);
//        listView.setAdapter(adapter);
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmartToast.show("编辑属性");
            }
        });
        tv_name.setText(mList.get(i).name);
        return view;
    }
}
