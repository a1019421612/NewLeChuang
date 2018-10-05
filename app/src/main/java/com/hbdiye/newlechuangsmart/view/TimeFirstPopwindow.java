package com.hbdiye.newlechuangsmart.view;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.LinkageIconAdapter;

import java.util.List;


/**
 * 时间条件选择  每天。工作日。自定义。仅一次
 *
 * @author Administrator
 */
public class TimeFirstPopwindow extends PopupWindow {
    private Context context;
    private LayoutInflater inflater;
    private View view;
    private View.OnClickListener clickListener;
    private LinkageIconAdapter adapter;
    LinearLayout ll_time_everyday, ll_time_workday, ll_time_oneday, ll_time_custom;
    TextView tv_time_everyday, tv_time_workday, tv_time_oneday, tv_time_custom;
    ImageView iv_time_everyday, iv_time_workday, iv_time_oneday;

    public TimeFirstPopwindow(Context context, View.OnClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.time_first_popupwindows, null);
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

    public void initView() {

        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        LinearLayout ll_cancel = view.findViewById(R.id.ll_cancel);

        ll_time_everyday = view.findViewById(R.id.ll_time_everyday);
        ll_time_workday = view.findViewById(R.id.ll_time_workday);
        ll_time_oneday = view.findViewById(R.id.ll_time_oneday);
        ll_time_custom = view.findViewById(R.id.ll_time_custom);

        tv_time_everyday = view.findViewById(R.id.tv_time_everyday);
        tv_time_workday = view.findViewById(R.id.tv_time_workday);
        tv_time_oneday = view.findViewById(R.id.tv_time_oneday);
        tv_time_custom = view.findViewById(R.id.tv_time_custom);

        iv_time_everyday = view.findViewById(R.id.iv_time_everyday);
        iv_time_workday = view.findViewById(R.id.iv_time_workday);
        iv_time_oneday = view.findViewById(R.id.iv_time_oneday);

        ll_time_custom.setOnClickListener(clickListener);
        ll_time_everyday.setOnClickListener(clickListener);
        ll_time_oneday.setOnClickListener(clickListener);
        ll_time_workday.setOnClickListener(clickListener);

        ll_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelPopuWindow();
            }
        });
    }

    public void changeTV(int viewId){
        tv_time_everyday.setTextColor(Color.parseColor("#999999"));
        tv_time_workday.setTextColor(Color.parseColor("#999999"));
        tv_time_oneday.setTextColor(Color.parseColor("#999999"));
        tv_time_custom.setTextColor(Color.parseColor("#999999"));

        iv_time_everyday.setVisibility(View.GONE);
        iv_time_workday.setVisibility(View.GONE);
        iv_time_oneday.setVisibility(View.GONE);

        if (viewId==R.id.ll_time_everyday){
            tv_time_everyday.setTextColor(Color.parseColor("#0686DD"));
            iv_time_everyday.setVisibility(View.VISIBLE);
        }else if (viewId==R.id.ll_time_workday){
            tv_time_workday.setTextColor(Color.parseColor("#0686DD"));
            iv_time_workday.setVisibility(View.VISIBLE);
        } else if (viewId==R.id.ll_time_oneday) {
            tv_time_oneday.setTextColor(Color.parseColor("#0686DD"));
            iv_time_oneday.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 从底部展示
     *
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

    private void cancelPopuWindow() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }

    ;
}
