package com.hbdiye.newlechuangsmart.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 医疗注册界面
 */
public class YiLiaoActivity extends BaseActivity {


    @BindView(R.id.ll_yl_date)
    LinearLayout llYlDate;

    @BindView(R.id.tv_yl_data)
    TextView tvYlData;
    @BindView(R.id.tv_yl_register)
    TextView tvYlRegister;
    @BindView(R.id.et_yl_age)
    EditText etYlAge;
    @BindView(R.id.et_yl_cm)
    EditText etYlCm;
    @BindView(R.id.et_yl_kg)
    EditText etYlKg;
    @BindView(R.id.rg_yl)
    RadioGroup rgYl;
    private String phone;
    private String password;
    private int sex = -1;
    private String nickName;

    @Override
    protected void initData() {
        phone = (String) SPUtils.get(this, "mobilephone", "");
        password = (String) SPUtils.get(this, "password", "");
        nickName = (String) SPUtils.get(this,"nickName","");

    }

    @Override
    protected String getTitleName() {
        return "倍泰注册信息";
    }

    @Override
    protected void initView() {
        rgYl.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_nan:
                        sex = 0;
                        break;
                    case R.id.rb_nv:
                        sex = 1;
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_yi_liao;
    }

    @OnClick({R.id.ll_yl_date, R.id.tv_yl_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_yl_register:
                String age = etYlAge.getText().toString().trim();
                String height = etYlCm.getText().toString().trim();
                String kg = etYlKg.getText().toString().trim();
                String data = tvYlData.getText().toString();
                if (TextUtils.isEmpty(age)) {
                    SmartToast.show("请输入年龄");
                    return;
                }
                if (sex == -1) {
                    SmartToast.show("请选择性别");
                    return;
                }
                if (TextUtils.isEmpty(height)) {
                    SmartToast.show("请输入身高");
                    return;
                }
                if (TextUtils.isEmpty(kg)) {
                    SmartToast.show("请输入体重");
                    return;
                }
                if (TextUtils.isEmpty(data)){
                    SmartToast.show("请选择出生日期");
                    return;
                }
                ylRegister(sex,height,kg,data);
                break;
            case R.id.ll_yl_date:
                TimePickerView pvtime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        tvYlData.setText(format.format(date));
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

    private void ylRegister(int sex, String height, String kg, String data) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.YILIAOREGISTER))
                .addParams("phone",phone)
                .addParams("pwd",password)
                .addParams("sex",sex+"")
                .addParams("height",height)
                .addParams("weight",kg)
                .addParams("birthday",data)
                .addParams("nickname",nickName)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String errcode = jsonObject.getString("errcode");
                            if (errcode.equals("0")){
                                SmartToast.show("注册成功");
                                startActivity(new Intent(YiLiaoActivity.this,HealthActivity.class));
                            }else if (errcode.equals("6001")){
                                SmartToast.show("该账号已注册");
                            }
                        } catch (JSONException e) {
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
