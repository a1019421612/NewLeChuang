package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class EditInfoActivity extends BaseActivity {

    @BindView(R.id.et_nickname)
    EditText etNickname;
    private String token;

    @Override
    protected void initData() {
        token = (String) SPUtils.get(this,"token","");
        String name = getIntent().getStringExtra("name");
        etNickname.setText(name);
        etNickname.setSelection(etNickname.getText().length());
        tvBaseEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = etNickname.getText().toString().trim();
                if (TextUtils.isEmpty(trim)){
                    SmartToast.show("不能为空");
                    return;
                }
                OkHttpUtils
                        .post()
                        .url(InterfaceManager.getInstance().getURL(InterfaceManager.UPDATENICKNAME))
                        .addParams("token",token)
                        .addParams("nickname",trim)
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
                                    String s = EcodeValue.resultEcode(errcode);
                                    SmartToast.show(s);
                                    if (errcode.equals("0")){
                                        setResult(105);
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected String getTitleName() {
        return "修改昵称";
    }

    @Override
    protected void initView() {
        tvBaseEnter.setVisibility(View.VISIBLE);
        tvBaseEnter.setText("确定");
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_edit_info;
    }

}
