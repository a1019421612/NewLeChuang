package com.hbdiye.newlechuangsmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.UserFamilyInfoBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.hbdiye.newlechuangsmart.view.SceneDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

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
    private String phone;
    private String admin_phone;
    private SceneDialog dialog;
    private String family_name;

    @Override
    protected void initData() {
        token = (String) SPUtils.get(this, "token", "");
        userFamilyInfoBean = (UserFamilyInfoBean) getIntent().getSerializableExtra("info");
        family_name = userFamilyInfoBean.family.name;
        String name = userFamilyInfoBean.user.name;
        phone = userFamilyInfoBean.user.phone;
        admin_phone = userFamilyInfoBean.family.phone;
        tvInfoUserName.setText(name);
        tvInfoFamilyName.setText(family_name);
        tvInfoUserPhone.setText(this.phone);

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
                startActivityForResult(new Intent(this, EditInfoActivity.class).putExtra("name",tvInfoUserName.getText().toString()), 105);
                break;
            case R.id.tv_info_family_name:
                if (phone.equals(admin_phone)){
                    //管理员
                    dialog=new SceneDialog(PersonInfoActivity.this,R.style.MyDialogStyle,clicker,"修改家庭名称",family_name);
                    dialog.show();
                }else {
                    //普通用户
                    SmartToast.show("不是家庭创建者，不能修改家庭名称");
                }
                break;
            case R.id.ll_info_family_code:
                startActivity(new Intent(this,MyErCodeActivity.class).putExtra("familyId",userFamilyInfoBean.user.familyId));
                break;
        }
    }
    private View.OnClickListener clicker=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.app_cancle_tv:
                    dialog.dismiss();
                    break;
                case R.id.app_sure_tv:
                    String sceneName = dialog.getSceneName();
                    if (!TextUtils.isEmpty(sceneName)){
                        updateFamilyName(sceneName);
                    }
                    break;
            }
        }
    };

    private void updateFamilyName(final String sceneName) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.UPDATEFAMILYNAME))
                .addParams("token",token)
                .addParams("name",sceneName)
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
                                if (dialog!=null){
                                    dialog.dismiss();
                                }
                                SmartToast.show("修改成功");
                                tvInfoFamilyName.setText(sceneName);
                            }else {
                                String s = EcodeValue.resultEcode(errcode);
                                SmartToast.show(s);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
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
