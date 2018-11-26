package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

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
import butterknife.OnClick;
import okhttp3.Call;

public class GateWaySeriesNumActivity extends BaseActivity {

    @BindView(R.id.seriesNumberEt)
    EditText seriesNumberEt;
    @BindView(R.id.tv_gateway_add)
    TextView tvGatewayAdd;
    private String token;
    private String seriesNum;

    @Override
    protected void initData() {
        token = (String) SPUtils.get(this,"token","");
    }

    @Override
    protected String getTitleName() {
        return "添加网关";
    }

    @Override
    protected void initView() {
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_gate_way_series_num;
    }

    @OnClick(R.id.tv_gateway_add)
    public void onViewClicked() {
        seriesNum = seriesNumberEt.getText().toString().trim();
        if (TextUtils.isEmpty(seriesNum)){
            SmartToast.show("序列号不能为空");
            return;
        }
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.BINDGATEWAY))
                .addParams("token",token)
                .addParams("serialnumber",seriesNum)
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

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
