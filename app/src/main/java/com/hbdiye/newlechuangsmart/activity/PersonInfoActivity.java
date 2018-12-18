package com.hbdiye.newlechuangsmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.UserFamilyInfoBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class PersonInfoActivity extends BaseActivity {

    @BindView(R.id.tv_info_user_name)
    TextView tvInfoUserName;
    @BindView(R.id.tv_info_user_phone)
    TextView tvInfoUserPhone;
    @BindView(R.id.tv_info_family_name)
    TextView tvInfoFamilyName;
    @BindView(R.id.ll_info_family_code)
    LinearLayout llInfoFamilyCode;
    private UserFamilyInfoBean userFamilyInfoBean;
    private String token;

    @Override
    protected void initData() {
        token = (String) SPUtils.get(this, "token", "");
        userFamilyInfoBean = (UserFamilyInfoBean) getIntent().getSerializableExtra("info");
        String family_name = userFamilyInfoBean.family.name;
        String name = userFamilyInfoBean.user.name;
        String phone = userFamilyInfoBean.user.phone;
        tvInfoUserName.setText(name);
        tvInfoFamilyName.setText(family_name);
        tvInfoUserPhone.setText(phone);

    }

    @Override
    protected String getTitleName() {
        return "个人信息";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_person_info;
    }

    @OnClick({R.id.tv_info_user_name, R.id.tv_info_family_name, R.id.ll_info_family_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_info_user_name:
                startActivityForResult(new Intent(this, EditInfoActivity.class), 105);
                break;
            case R.id.tv_info_family_name:
                break;
            case R.id.ll_info_family_code:
                startActivity(new Intent(this,MyErCodeActivity.class).putExtra("familyId",userFamilyInfoBean.user.familyId));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 105) {
            personInfo();
        }
    }

    private void personInfo() {
        OkHttpUtils.post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.USERANDFAMILYINFO))
                .addParams("token", token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        userFamilyInfoBean = new Gson().fromJson(response, UserFamilyInfoBean.class);
                        String errcode = userFamilyInfoBean.errcode;
                        if (errcode.equals("0")) {
                            String user_name = userFamilyInfoBean.user.name;
                            String family_name = userFamilyInfoBean.family.name;
                            tvInfoUserName.setText(user_name);
                            tvInfoFamilyName.setText(family_name);
                            SPUtils.put(PersonInfoActivity.this, "nickName", user_name);
                        }
                    }
                });
    }

}
