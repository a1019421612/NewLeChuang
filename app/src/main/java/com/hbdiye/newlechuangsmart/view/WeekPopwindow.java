package com.hbdiye.newlechuangsmart.view;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.ConditionPopAdapter;
import com.hbdiye.newlechuangsmart.adapter.WeekPopAdapter;
import com.hbdiye.newlechuangsmart.bean.ConditionPop;

import java.util.List;


/**
 * 拍照的悬浮窗口
 * @author Administrator
 *
 */
public class WeekPopwindow extends PopupWindow {
	private Context context;
	private LayoutInflater inflater;
	private View view;
	private BaseQuickAdapter.OnItemChildClickListener clickListener;
	private WeekPopAdapter adapter;
	private List<String> mList;
	private View.OnClickListener clicker;
   public WeekPopwindow(Context context,List<String> mList,BaseQuickAdapter.OnItemChildClickListener clickListener,View.OnClickListener clicker){
	   this.context = context;
	   this.mList=mList;
	   this.clickListener=clickListener;
	   this.clicker=clicker;
	   inflater = LayoutInflater.from(context);
	   view = inflater.inflate(R.layout.week_popupwindows, null);
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
       RecyclerView rv=view.findViewById(R.id.rv_week);
       LinearLayoutManager manager=new LinearLayoutManager(context);
       manager.setOrientation(LinearLayoutManager.VERTICAL);
       rv.setLayoutManager(manager);
       adapter=new WeekPopAdapter(mList);
       rv.setAdapter(adapter);
//	   RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
       LinearLayout ll_cancel=view.findViewById(R.id.ll_cancel);
       TextView tv_week_cancel=view.findViewById(R.id.tv_week_cancel);
       TextView tv_week_ok=view.findViewById(R.id.tv_week_ok);
//	   CircleImageView civ_head1=view.findViewById(R.id.civ_head1);
//	   CircleImageView civ_head2=view.findViewById(R.id.civ_head2);
//	   CircleImageView civ_head3=view.findViewById(R.id.civ_head3);
//	   CircleImageView civ_head4=view.findViewById(R.id.civ_head4);
//	   CircleImageView civ_head5=view.findViewById(R.id.civ_head5);
//	   CircleImageView civ_head6=view.findViewById(R.id.civ_head6);
//	   CircleImageView civ_head7=view.findViewById(R.id.civ_head7);
//	   CircleImageView civ_head8=view.findViewById(R.id.civ_head8);
//	   civ_head1.setOnClickListener(clickListener);
//	   civ_head2.setOnClickListener(clickListener);
//	   civ_head3.setOnClickListener(clickListener);
//	   civ_head4.setOnClickListener(clickListener);
//	   civ_head5.setOnClickListener(clickListener);
//	   civ_head6.setOnClickListener(clickListener);
//	   civ_head7.setOnClickListener(clickListener);
//	   civ_head8.setOnClickListener(clickListener);
	   ll_cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               cancelPopuWindow();
           }
       });
       adapter.setOnItemChildClickListener(clickListener);
       tv_week_cancel.setOnClickListener(clicker);
       tv_week_ok.setOnClickListener(clicker);
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
