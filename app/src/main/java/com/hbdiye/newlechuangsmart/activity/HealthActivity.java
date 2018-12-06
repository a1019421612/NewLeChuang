package com.hbdiye.newlechuangsmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.YiLiaoTiZhongBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.YILIAOINFO;

/**
 * 医疗列表界面
 */
public class HealthActivity extends BaseActivity {

    @BindView(R.id.ll_health_tizhong)
    LinearLayout llHealthTizhong;
    @BindView(R.id.ll_health_tizhilv)
    LinearLayout llHealthTizhilv;
    @BindView(R.id.ll_health_xuetang)
    LinearLayout llHealthXuetang;
    @BindView(R.id.ll_health_tiwen)
    LinearLayout llHealthTiwen;
    @BindView(R.id.ll_health_xueya)
    LinearLayout llHealthXueya;
    String phone;
    @BindView(R.id.tv_health_gaoya)
    TextView tvHealthGaoya;
    @BindView(R.id.tv_health_diya)
    TextView tvHealthDiya;

    @Override
    protected void initData() {
        phone = (String) SPUtils.get(this, "mobilephone", "");
        info();
    }

    @Override
    protected String getTitleName() {
        return "健康医疗";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_health;
    }

    @OnClick({R.id.ll_health_tizhong, R.id.ll_health_tizhilv, R.id.ll_health_xuetang, R.id.ll_health_tiwen, R.id.ll_health_xueya})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_health_tizhong:
                startActivity(new Intent(this, TiZhongActivity.class));
                break;
            case R.id.ll_health_tizhilv:
                startActivity(new Intent(this, TiZhiLvActivity.class));
                break;
            case R.id.ll_health_xuetang:
                startActivity(new Intent(this, XueTangActivity.class));
                break;
            case R.id.ll_health_tiwen:
                startActivity(new Intent(this, TiWenActivity.class));
                break;
            case R.id.ll_health_xueya:
                startActivity(new Intent(this, XueYaActivity.class));
                break;
        }
    }

    private void info() {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(YILIAOINFO))
                .addParams("phone", phone)
                .addParams("page", "1")
                .addParams("size", "6")
                .addParams("type", "7")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        String replace = response.replace("\\\"", "\"").replace("\"{", "{").replace("}\"", "}");
                        YiLiaoTiZhongBean yiLiaoTiZhongBean = new Gson().fromJson(replace, YiLiaoTiZhongBean.class);
                        if (yiLiaoTiZhongBean.errCode.equals("0")) {

                            List<YiLiaoTiZhongBean.Data.Lists> list_data = yiLiaoTiZhongBean.data.list;
                            if (list_data == null || list_data.size() == 0) {
                                return;
                            }

                            int diastolic = list_data.get(0).jsonStr.data.diastolic;
                            tvHealthDiya.setText(diastolic+"");
                            int systolic = list_data.get(0).jsonStr.data.systolic;
                            tvHealthGaoya.setText(systolic+"");

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
