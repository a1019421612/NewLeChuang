package com.hbdiye.newlechuangsmart.view;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.LinkageIconAdapter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 拍照的悬浮窗口
 * @author Administrator
 *
 */
public class LinkageAddIconPopwindow extends PopupWindow {
	private Context context;
	private LayoutInflater inflater;
	private View view;
	private BaseQuickAdapter.OnItemClickListener clickListener;
	private LinkageIconAdapter adapter;
	private List<Integer> mList;
   public LinkageAddIconPopwindow(Context context, List<Integer> mList,BaseQuickAdapter.OnItemClickListener clickListener){
	   this.context = context;
	   this.clickListener = clickListener;
	   this.mList=mList;
	   inflater = LayoutInflater.from(context);
	   view = inflater.inflate(R.layout.linkage_icon_popupwindows, null);
	   initView();
	   this.setWidth(LayoutParams.MATCH_PARENT);
	   this.setHeight(LayoutParams.WRAP_CONTENT);
	   this.setBackgroundDrawable(new BitmapDrawable());
	 //设置SelectPicPopupWindow弹出窗体动画效果
       this.setAnimationStyle(R.style.AnimBottom);
	   this.setFocusable(true);
	   this.setOutsideTouchable(true);
	   this.setContentView(view);
   }
   public void initView(){

	   RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
       LinearLayout ll_cancel=view.findViewById(R.id.ll_cancel);
	   RecyclerView recyclerView=view.findViewById(R.id.rv_linkage_icon);
	   GridLayoutManager manager=new GridLayoutManager(context,5);
	   recyclerView.setLayoutManager(manager);
	   adapter=new LinkageIconAdapter(mList);
	   recyclerView.setAdapter(adapter);
	   adapter.setOnItemClickListener(clickListener);
	   ll_cancel.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {
               cancelPopuWindow();
           }
       });
   }
   /**
    * 从底部展示
    * @param parent
    */
   public void showPopupWindowBottom(View parent) {
       if (!this.isShowing()) {  
       	 this.showAtLocation(
       			 parent,
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
       } else {  
           this.dismiss();  
       }  
   }
	private void cancelPopuWindow(){
       if (this.isShowing()){
           this.dismiss();
       }
    };
}
