package com.hbdiye.newlechuangsmart.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.util.ClassyIconByProId;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 医疗注册界面
 */
public class YiLiaoActivity extends BaseActivity {

    @BindView(R.id.ll_yl_age)
    LinearLayout llYlAge;
    @BindView(R.id.ll_yl_date)
    LinearLayout llYlDate;
    @BindView(R.id.tv_yl_age)
    TextView tvYlAge;
    @BindView(R.id.tv_yl_data)
    TextView tvYlData;
    @BindView(R.id.tv_yl_register)
    TextView tvYlRegister;

    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "倍泰注册信息";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_yi_liao;
    }

    @OnClick({R.id.ll_yl_age, R.id.ll_yl_date, R.id.tv_yl_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_yl_register:
                startActivity(new Intent(this, HealthActivity.class));
                break;
            case R.id.ll_yl_age:
                OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvYlAge.setText(ClassyIconByProId.getAge().get(options1));
                    }
                })
                        .setSubmitText("确定")//确定按钮文字
                        .setCancelText("取消")//取消按钮文字
                        .setSubCalSize(20)//确定和取消文字大小
//                        .setSubmitColor(getResources().getColor(R.color.themcolor))//确定按钮文字颜色
//                        .setCancelColor(getResources().getColor(R.color.themcolor))//取消按钮文字颜色
                        .setTitleBgColor(0xFFFFFFFF)//标题背景颜色 Night mode
//                        .setBgColor(getResources().getColor(R.color.activity_bg_color))//滚轮背景颜色 Night mode
                        .setContentTextSize(20)//滚轮文字大小
//                        .setTextColorCenter(getResources().getColor(R.color.text_tv9))//设置选中项的颜色
                        .setLineSpacingMultiplier(3f)//设置两横线之间的间隔倍数
                        .build();
                pvOptions.setPicker(ClassyIconByProId.getAge());
                pvOptions.show();
//                OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
//                    @Override
//                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
//                        //返回的分别是三个级别的选中位置
////                        carTruobleValue = ContentCofig.getValueByCarTroub(ContentCofig.getCarTroub().get(options1));
////                        loancar_troub_tv.setText(ContentCofig.getCarTroub().get(options1));
//
//                    }
//                })
//                        .setSubmitText("确定")//确定按钮文字
//                        .setCancelText("取消")//取消按钮文字
//                        .setSubCalSize(20)//确定和取消文字大小
////                        .setSubmitColor(getResources().getColor(R.color.themcolor))//确定按钮文字颜色
////                        .setCancelColor(getResources().getColor(R.color.themcolor))//取消按钮文字颜色
//                        .setTitleBgColor(0xFFFFFFFF)//标题背景颜色 Night mode
////                        .setBgColor(getResources().getColor(R.color.activity_bg_color))//滚轮背景颜色 Night mode
//                        .setContentTextSize(20)//滚轮文字大小
////                        .setTextColorCenter(getResources().getColor(R.color.text_tv9))//设置选中项的颜色
//                        .setLineSpacingMultiplier(3f)//设置两横线之间的间隔倍数
//                        .build();
//                pvOptions.setPicker(ContentCofig.getCarTroub());
//                pvOptions.show();
                break;
            case R.id.ll_yl_date:
                TimePickerView pvtime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {

                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentTextSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
//                                .setTitleText("请选择时间")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
//                        .setTextColorCenter(getResources().getColor(R.color.text_tv9))//设置选中项的颜色
                        .setTitleColor(Color.BLACK)//标题文字颜色
//                        .setSubmitColor(getResources().getColor(R.color.themcolor))//确定按钮文字颜色
//                        .setCancelColor(getResources().getColor(R.color.themcolor))//取消按钮文字颜色
                        .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setLineSpacingMultiplier(3f)//设置两横线之间的间隔倍数
//                        .setBgColor(getResources().getColor(R.color.activity_bg_color))//滚轮背景颜色 Night mode
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .isDialog(false)//是否显示为对话框样式
                        .build();
                pvtime.setDate(Calendar.getInstance());
                pvtime.show();

                break;
        }
    }

}
