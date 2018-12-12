package com.hbdiye.newlechuangsmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.EditActionActivity;
import com.hbdiye.newlechuangsmart.bean.ConditionPop;
import com.hbdiye.newlechuangsmart.bean.SceneDetailBean;
import com.hbdiye.newlechuangsmart.util.ClassyIconByProId;
import com.hbdiye.newlechuangsmart.view.CommentSceneView;
import com.hbdiye.newlechuangsmart.view.CommentView;
import com.hbdiye.newlechuangsmart.view.ConditionPopwindow;

import java.util.ArrayList;
import java.util.List;

public class SceneDetailListAdapter extends BaseAdapter {
    private Context context;
    private List<SceneDetailBean.SceneTaskList> mList;
    private String name;

    public SceneDetailListAdapter(Context context, List<SceneDetailBean.SceneTaskList> mList) {
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
        ImageView imageView=view.findViewById(R.id.iv_scene_detail_icon);
//        ListView listView=view.findViewById(R.id.mlv_condition);
        LinearLayout ll_root=view.findViewById(R.id.ll_root);
        ll_root.removeAllViews();
        List<SceneDetailBean.SceneTaskList.SceneTaskLists> sceneTaskList = mList.get(i).sceneTaskList;
        for (int j = 0; j < sceneTaskList.size(); j++) {
            CommentSceneView commentView = new CommentSceneView(context);
            List<SceneDetailBean.SceneTaskList.DevAttList> devAttList = mList.get(i).devAttList;
            int port = sceneTaskList.get(j).port;
            String devActId = sceneTaskList.get(j).devActId;
            for (int k = 0; k < devAttList.size(); k++) {
                if (port==devAttList.get(k).port){
                    name = devAttList.get(k).name;
                    commentView.setTvSceneDeviceName(name);
                }
            }
            List<SceneDetailBean.SceneTaskList.DevActList> devActList = mList.get(i).devActList;
            for (int k = 0; k < devActList.size(); k++) {
                String id = devActList.get(k).id;
                if (devActId.equals(id)){
                    commentView.setTvSceneDeviceAttr(devActList.get(k).name);
                }
            }
            final String stid = sceneTaskList.get(j).id;
            ImageView iv_del = commentView.getImageViewDel();
            iv_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mImageViewDelListener!=null){
                        mImageViewDelListener.OnImageViewDelListener(stid);
                    }
                }
            });
            ll_root.addView(commentView);
        }
//        for (int j = 0; j < mList.get(i).devAttList.size(); j++) {
//            CommentSceneView commentView = new CommentSceneView(context);
//             List<SceneDetailBean.SceneTaskList.DevAttList> devAttList = mList.get(i).devAttList;
//             SceneDetailBean.SceneTaskList.DevAttList devAttList1 = devAttList.get(j);
//
//            final String stid = "";
//
//            String name = devAttList.get(j).name;
//            int value = devAttList.get(j).value;
//            commentView.setTvSceneDeviceName(name);
//            if (value==0){
//                commentView.setTvSceneDeviceAttr("关");
//            }else if (value==1){
//                commentView.setTvSceneDeviceAttr("开");
//            }
//            ImageView iv_del = commentView.getImageViewDel();
//            iv_del.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mImageViewDelListener!=null){
//                        mImageViewDelListener.OnImageViewDelListener(stid);
//                    }
//                }
//            });
//            ll_root.addView(commentView);
//        }
//        LinkageConditionItemAdapter adapter=new LinkageConditionItemAdapter(context,mList,this);
//        listView.setAdapter(adapter);
        if (mListener!=null){
            tv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.OnGridItemClickListener(i);
                }
            });
        }
        tv_name.setText(mList.get(i).name);
        Glide.with(context).load(ClassyIconByProId.deviceIcon(mList.get(i).productId)).into(imageView);
        return view;
    }

    public interface ImageViewDelListener{
        void OnImageViewDelListener(String stid);
    }
    private ImageViewDelListener mImageViewDelListener;
    public void setImageViewDelListener(ImageViewDelListener mImageViewDelListener){
        this.mImageViewDelListener=mImageViewDelListener;
    }
    public interface GridOnItemClickListener{
        void OnGridItemClickListener(int pos);
    }
    private GridOnItemClickListener mListener;
    public void setOnItemClickListener(GridOnItemClickListener mListener){
        this.mListener=mListener;
    }
}
