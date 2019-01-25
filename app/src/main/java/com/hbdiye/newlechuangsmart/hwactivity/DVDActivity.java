package com.hbdiye.newlechuangsmart.hwactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.HwBaseActivity;
import com.hbdiye.newlechuangsmart.bean.DeviceBean;
import com.hbdiye.newlechuangsmart.bean.EditRemoteControlBean;
import com.hbdiye.newlechuangsmart.util.AppUtils;
import com.hbdiye.newlechuangsmart.util.SharedpreUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class DVDActivity extends HwBaseActivity {

    private List<EditRemoteControlBean.Data.KeyList> keyList;
    private EditRemoteControlBean.Data hongWaiXian;
    private String hwbId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvd);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        DeviceBean deviceBean = (DeviceBean) getIntent().getSerializableExtra("hongWaiXian");
        if (deviceBean == null) {
            return;
        }
        hongWaiXian = deviceBean.data;
        hwbId = getIntent().getStringExtra("hwbId");
        keyList = hongWaiXian.keyList;
    }

    private void initView() {
        setTitle("DVD遥控");
        getRightView().setVisibility(View.VISIBLE);
        getRightView().setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_right:
                    Intent intent = new Intent(getApplicationContext(), SetUpActivity.class);
                    intent.putExtra("rid", hongWaiXian.rid);
                    intent.putExtra("hwbId", hwbId);
                    startActivityForResult(intent, 1001);
                    break;
            }
        }
    };

    public String getPulse(String name) {
        String pulse = "";
        if (keyList == null || keyList.size() == 0) {
            return pulse;
        }
        for (int i = 0; i < keyList.size(); i++) {
            if (name.equals(keyList.get(i).fkey)) {
                pulse = keyList.get(i).pulse;
                break;
            }
        }
        return pulse;
    }

    private void setHongWai(String pulse) {
        OkHttpUtils.post().url("http://39.104.119.0:8808/SmartHome/infrared/sentInfraredCode")
                .addParams("token", SharedpreUtils.getString(getApplicationContext(), "token", ""))
                .addParams("deviceId", hwbId)
                .addParams("pulse", pulse)
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1002) {
            setResult(1002);
            finish();
        }
    }

    @OnClick({R.id.tv_tanchu, R.id.iv_xiangshang, R.id.iv_xiangxia, R.id.iv_xiangzuo, R.id.iv_xiangyou,
            R.id.tv_ok, R.id.iv_voice_add, R.id.iv_voice_reduce, R.id.iv_tingzhi, R.id.iv_bofang,
            R.id.iv_zanting, R.id.iv_shangyishou, R.id.iv_kuaitui, R.id.iv_kuaijin, R.id.iv_xiayishou, R.id.iv_kaiguan})
    public void onViewClicked(View view) {
        AppUtils.vibrate(getApplicationContext(), (long) 200);
        switch (view.getId()) {
            case R.id.tv_tanchu:
                setHongWai(getPulse("eject"));
                break;
            case R.id.iv_xiangshang:
                setHongWai(getPulse("navigate_up"));
                break;
            case R.id.iv_xiangxia:
                setHongWai(getPulse("navigate_down"));
                break;
            case R.id.iv_xiangzuo:
                setHongWai(getPulse("navigate_left"));
                break;
            case R.id.iv_xiangyou:
                setHongWai(getPulse("navigate_right"));
                break;
            case R.id.tv_ok:
                setHongWai(getPulse("ok"));
                break;
            case R.id.iv_voice_add:
                setHongWai(getPulse("volume_up"));
                break;
            case R.id.iv_voice_reduce:
                setHongWai(getPulse("volume_down"));
                break;
            case R.id.iv_tingzhi:
                setHongWai(getPulse("stop"));
                break;
            case R.id.iv_bofang:
                setHongWai(getPulse("play"));
                break;
            case R.id.iv_zanting:
                setHongWai(getPulse("pause"));
                break;
            case R.id.iv_shangyishou:
                setHongWai(getPulse("previous"));
                break;
            case R.id.iv_kuaitui:
                setHongWai(getPulse("rewind"));
                break;
            case R.id.iv_kuaijin:
                setHongWai(getPulse("fast_forward"));
                break;
            case R.id.iv_xiayishou:
                setHongWai(getPulse("next"));
                break;
            case R.id.iv_kaiguan:
                setHongWai(getPulse("power"));
                break;
        }
    }
}
