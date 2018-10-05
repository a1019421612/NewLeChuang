package com.hbdiye.newlechuangsmart.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.view.TimeFirstPopwindow;
import com.hbdiye.newlechuangsmart.view.WeekPopwindow;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimeConditionActivity extends AppCompatActivity {

    @BindView(R.id.iv_time_back)
    ImageView ivTimeBack;
    @BindView(R.id.tv_time_ok)
    TextView tvTimeOk;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.wv_hour)
    WheelView wvHour;
    @BindView(R.id.wv_min)
    WheelView wvMin;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    private TimeFirstPopwindow popwindow;
    private WeekPopwindow weekPopwindow;
    private List<String> mList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_condition);
        ButterKnife.bind(this);

        mList.add("周一");
        mList.add("周二");
        mList.add("周三");
        mList.add("周四");
        mList.add("周五");
        mList.add("周六");
        mList.add("周日");

        wvHour.setWheelAdapter(new ArrayWheelAdapter(this));
        wvHour.setWheelData(createHours());
        wvHour.setWheelSize(5);
        wvHour.setExtraText("时", Color.parseColor("#0288ce"), 40, 70);
        wvHour.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                String hour = (String) o;
//                SmartToast.show(s);
            }
        });

        wvMin.setWheelAdapter(new ArrayWheelAdapter(this));
        wvMin.setWheelData(createMinutes());
        wvMin.setWheelSize(5);
        wvMin.setExtraText("分", Color.parseColor("#0288ce"), 40, 70);
        wvMin.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                String min = (String) o;
            }
        });
    }

    private ArrayList<String> createHours() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                list.add("0" + i);
            } else {
                list.add("" + i);
            }
        }
        return list;
    }

    private ArrayList<String> createMinutes() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                list.add("0" + i);
            } else {
                list.add("" + i);
            }
        }
        return list;
    }

    @OnClick({R.id.iv_time_back, R.id.tv_time_ok, R.id.ll_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_time_back:
                finish();
                break;
            case R.id.ll_time:
                popwindow = new TimeFirstPopwindow(this, clicker);
                popwindow.showPopupWindowBottom(llRoot);
                break;
            case R.id.tv_time_ok:
                break;
        }
    }

    public View.OnClickListener clicker = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            popwindow.changeTV(view.getId());
            switch (view.getId()) {
                case R.id.ll_time_everyday:
                    //每天
                    break;
                case R.id.ll_time_workday:
                    //工作日
                    break;
                case R.id.ll_time_oneday:
                    //仅一次
                    break;
                case R.id.ll_time_custom:

                    weekPopwindow=new WeekPopwindow(TimeConditionActivity.this,mList,rvClicker,clicker);
                    weekPopwindow.showPopupWindowBottom(llRoot);
                    //自定义
                    break;
                case R.id.tv_week_cancel:
                    //周一、二。。。
                    weekPopwindow.dismiss();
                    break;
                case R.id.tv_week_ok:
                    //周一、二。。。
                    if (popwindow!=null){
                        popwindow.dismiss();
                    }
                    weekPopwindow.dismiss();
                    break;
                default:
                    break;
            }
        }
    };
    private BaseQuickAdapter.OnItemChildClickListener rvClicker=new BaseQuickAdapter.OnItemChildClickListener() {
        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

        }
    };
}
