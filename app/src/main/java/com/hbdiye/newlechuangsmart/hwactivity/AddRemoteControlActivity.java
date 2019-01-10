package com.hbdiye.newlechuangsmart.hwactivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.HwBaseActivity;
import com.hbdiye.newlechuangsmart.bean.AddRemoteControlBean;
import com.hbdiye.newlechuangsmart.util.AppUtils;
import com.hbdiye.newlechuangsmart.util.SharedpreUtils;
import com.hbdiye.newlechuangsmart.util.ToastUtils;
import com.xzcysoft.wnzhikonglibrary.WNZKConfigure;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class AddRemoteControlActivity extends HwBaseActivity {

    @BindView(R.id.tv_introduce)
    TextView tvIntroduce;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.bt_preservation)
    Button btPreservation;
    private List<AddRemoteControlBean.RidsList> mRidsList = new ArrayList<>();
    private int count = 1;
    private String deviceId;
    private String brandId;
    private String deviceName;
    private String hwbDeviceId;
    private int deviceUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remote_control);
        ButterKnife.bind(this);
        initView();
        String spId = getIntent().getStringExtra("spId");
        brandId = getIntent().getStringExtra("brandId");

        //判断是否机顶盒
        if (!TextUtils.isEmpty(spId)) {
            //判断是否是有线机顶盒
            if (TextUtils.isEmpty(brandId)) {
                getNoIPTvJiDingHe(spId);
            } else {
                getIpTvJiDingHe(spId, brandId);
            }
        } else {
            initData();
        }
    }

    private void getIpTvJiDingHe(String spId, String brandId) {
        WNZKConfigure.findRidsListBySpAndBrandAndIPTV(spId, brandId, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                AddRemoteControlBean addRemoteControlBean = new Gson().fromJson(response, AddRemoteControlBean.class);
                if (getResultCode(addRemoteControlBean.errcode)) {
                    List<AddRemoteControlBean.RidsList> ridsList = addRemoteControlBean.ridsList;
                    mRidsList.clear();
                    mRidsList.addAll(ridsList);
                    count = 1;
                    tvNumber.setText("(" + count + "/" + mRidsList.size() + ")");
                }
            }
        });
    }

    private void getNoIPTvJiDingHe(String spId) {
        WNZKConfigure.findRidsListBySpAndNotIPTV(spId, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                AddRemoteControlBean addRemoteControlBean = new Gson().fromJson(response, AddRemoteControlBean.class);
                if (getResultCode(addRemoteControlBean.errcode)) {
                    List<AddRemoteControlBean.RidsList> ridsList = addRemoteControlBean.ridsList;
                    mRidsList.clear();
                    mRidsList.addAll(ridsList);
                    count = 1;
                    tvNumber.setText("(" + count + "/" + mRidsList.size() + ")");
                }
            }
        });
    }

    private void initData() {
        WNZKConfigure.findRidsListByTypeAndBrand(deviceId, brandId, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("成功", e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                AddRemoteControlBean addRemoteControlBean = new Gson().fromJson(response, AddRemoteControlBean.class);
                if (getResultCode(addRemoteControlBean.errcode)) {
                    List<AddRemoteControlBean.RidsList> ridsList = addRemoteControlBean.ridsList;
                    mRidsList.clear();
                    mRidsList.addAll(ridsList);
                    count = 1;
                    tvNumber.setText("(" + count + "/" + mRidsList.size() + ")");
                }
            }
        });

    }

    private void initView() {
        Intent intent = getIntent();
        deviceId = intent.getStringExtra("deviceId");
        deviceName = intent.getStringExtra("deviceName");
        hwbDeviceId = intent.getStringExtra("hwbDeviceId");
        deviceUrl = intent.getIntExtra("deviceUrl", 0);
        setTitle("添加" + deviceName + "遥控器");

        String format = getResources().getString(R.string.add_remote_control_introduce);
        String result = String.format(format, deviceName);
        tvIntroduce.setText(result);
    }

    @OnClick({R.id.iv_open, R.id.ll_up, R.id.ll_down, R.id.bt_preservation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_open:
                if (mRidsList.size() == 0) {
                    ToastUtils.showToast(getApplicationContext(), "暂无红外数据");
                    return;
                }
                setKaiGuanHongWai();
                break;
            case R.id.ll_up:
                if (count <= 1) {
                    count = 1;
                } else {
                    count--;
                }
                tvNumber.setText("(" + count + "/" + mRidsList.size() + ")");
                break;
            case R.id.ll_down:
                if (count >= mRidsList.size()) {
                    count = mRidsList.size();
                } else {
                    count++;
                }
                tvNumber.setText("(" + count + "/" + mRidsList.size() + ")");
                break;
            case R.id.bt_preservation:
                if (mRidsList.size() == 0) {
                    ToastUtils.showToast(getApplicationContext(), "暂无红外数据");
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), EditRemoteControlActivity.class);
                intent.putExtra("deviceId", deviceId);
                intent.putExtra("deviceName", deviceName);
                intent.putExtra("brandId", brandId);
                intent.putExtra("hwbDeviceId", hwbDeviceId);
                intent.putExtra("deviceUrl", deviceUrl);
                intent.putExtra("rid", mRidsList.get(count - 1).rid + "");
                startActivity(intent);
                break;
        }
    }

    private void setKaiGuanHongWai() {
        AppUtils.vibrate(getApplicationContext(), (long) 100);
        OkHttpUtils.post().url("http://39.104.119.0:8808/SmartHome/infrared/sentInfraredCode")
                .addParams("token", SharedpreUtils.getString(getApplicationContext(), "token", ""))
                .addParams("deviceId", "HW2")
                .addParams("pulse", mRidsList.get(count - 1).testKey.pulse)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("成功", response);
            }
        });
    }
}
