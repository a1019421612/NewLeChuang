package com.hbdiye.newlechuangsmart.hwactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.HwBaseActivity;
import com.hbdiye.newlechuangsmart.adapter.RemoteConTrolAdapter;
import com.hbdiye.newlechuangsmart.bean.DeviceBean;
import com.hbdiye.newlechuangsmart.bean.EditRemoteControlBean;
import com.hbdiye.newlechuangsmart.util.AppUtils;
import com.hbdiye.newlechuangsmart.util.SharedpreUtils;
import com.hbdiye.newlechuangsmart.view.MyGridView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class AirConditionerActivity extends HwBaseActivity {

    @BindView(R.id.gridview)
    MyGridView gridview;
    @BindView(R.id.tv_moshi)
    TextView tvMoshi;
    @BindView(R.id.tv_fengsu)
    TextView tvFengsu;
    @BindView(R.id.tv_shuimian)
    TextView tvShuimian;
    @BindView(R.id.tv_fengxiang)
    TextView tvFengxiang;
    @BindView(R.id.tv_saofeng)
    TextView tvSaofeng;
    @BindView(R.id.tv_temperature_num)
    TextView tvTemperatureNum;
    @BindView(R.id.iv_guan)
    ImageView ivGuan;
    @BindView(R.id.ll_kai)
    LinearLayout llKai;
    @BindView(R.id.tv_dingshi)
    TextView tvDingshi;
    @BindView(R.id.ll_2)
    LinearLayout ll2;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    @BindView(R.id.iv_temperature_add)
    ImageView ivTemperatureAdd;
    @BindView(R.id.iv_temperature_reduce)
    ImageView ivTemperatureReduce;
    @BindView(R.id.iv_kaiguan)
    ImageView ivKaiguan;
    private int icon[] = {R.mipmap.dingshi, R.mipmap.fengsu, R.mipmap.shuimian,
            R.mipmap.saofeng, R.mipmap.fengxiang, R.mipmap.moshi};
    //图标下的文字
    private String name[] = {"定时", "风速", "睡眠", "扫风", "风向", "模式"};
    private EditRemoteControlBean.Data hongWaiXian;
    private String hwbId;
    private List<EditRemoteControlBean.Data.KeyList> keyList;

    private int mMoshi = 1;
    private int mFengSu = 1;
    private int mFengXiang = 1;
    private int mSaoFeng = 1;
    private boolean mIsOpen = false;
    private boolean mIsShuiMian = false;
    private boolean mIsDingShi = false;

    private String M = "MO";
    private String T = "26";
    private String S = "S0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_conditioner);
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
        setTitle("空调遥控");
        RemoteConTrolAdapter remoteConTrolAdapter = new RemoteConTrolAdapter(icon, name);
        gridview.setAdapter(remoteConTrolAdapter);

        getRightView().setVisibility(View.VISIBLE);
        getRightView().setOnClickListener(listener);
        gridview.setOnItemClickListener(gvListener);

        ivTemperatureAdd.setEnabled(false);
        ivTemperatureReduce.setEnabled(false);
        gridview.setEnabled(false);
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

    AdapterView.OnItemClickListener gvListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (mIsShuiMian && position == 1) {
                return;
            }
            if (mSaoFeng == 2 && position == 4) {
                return;
            }
            AppUtils.vibrate(getApplicationContext(), (long) 200);
            switch (position) {
//               {"定时", "风速", "睡眠", "扫风", "风向", "模式"};
                case 0:
//                    dingshi(!mIsDingShi);
                    break;
                case 1:
                    fengsu();
                    break;
                case 2:
//                    setIsShuiMian(!mIsShuiMian);
                    break;
                case 3:
//                    saofeng();
                    break;
                case 4:
//                    fengxiang();
                    break;
                case 5:
                    moshi();
                    break;

            }
        }
    };


    @OnClick({R.id.iv_temperature_add, R.id.iv_temperature_reduce, R.id.iv_kaiguan})
    public void onViewClicked(View view) {
        AppUtils.vibrate(getApplicationContext(), (long) 200);
        switch (view.getId()) {
            case R.id.iv_temperature_add:
                int temNum = Integer.parseInt(tvTemperatureNum.getText().toString());
                if (temNum >= 30) {
                    return;
                }
                temNum++;
                tvTemperatureNum.setText(temNum + "");
                T = "T"+temNum;
                setFaSong();
                break;
            case R.id.iv_temperature_reduce:
                int temNum2 = Integer.parseInt(tvTemperatureNum.getText().toString());
                if (temNum2 <= 16) {
                    return;
                }
                temNum2--;
                tvTemperatureNum.setText(temNum2 + "");
                T = "T"+temNum2;
                setFaSong();
                break;
            case R.id.iv_kaiguan:
                setIsOpen(!mIsOpen);
                break;
        }
    }

    private void dingshi(boolean isDingshi) {
        if (isDingshi) {
            mIsDingShi = true;
            tvDingshi.setVisibility(View.VISIBLE);
        } else {
            mIsDingShi = false;
            tvDingshi.setVisibility(View.INVISIBLE);
        }
    }


    private void setIsShuiMian(Boolean isShuiMian) {
        if (isShuiMian) {
            mIsShuiMian = true;
            tvShuimian.setVisibility(View.VISIBLE);
            tvFengsu.setText("风速:低");
            mFengSu = 2;
        } else {
            mIsShuiMian = false;
            tvShuimian.setVisibility(View.INVISIBLE);
        }
    }

    private void setIsOpen(Boolean isOpen) {
        if (isOpen) {
            mIsOpen = true;
            ivGuan.setVisibility(View.INVISIBLE);
            llKai.setVisibility(View.VISIBLE);
            ll1.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.VISIBLE);
            ivTemperatureAdd.setEnabled(true);
            ivTemperatureReduce.setEnabled(true);
            gridview.setEnabled(true);
            setHongWai(getPulse("power_on"));

        } else {
            mIsOpen = false;
            ivGuan.setVisibility(View.VISIBLE);
            llKai.setVisibility(View.INVISIBLE);
            ll1.setVisibility(View.INVISIBLE);
            ll2.setVisibility(View.INVISIBLE);
            ivTemperatureAdd.setEnabled(false);
            ivTemperatureReduce.setEnabled(false);
            gridview.setEnabled(false);
            setHongWai(getPulse("power_off"));
        }
    }

    private void saofeng() {
        mSaoFeng++;
        if (mSaoFeng == 3) {
            mSaoFeng = 1;
        }

        switch (mSaoFeng) {
            case 1:
                tvSaofeng.setText("自动风向");
                tvFengxiang.setVisibility(View.INVISIBLE);
                break;
            case 2:
                tvSaofeng.setText("手动风向");
                tvFengxiang.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void fengxiang() {
        mFengXiang++;
        if (mFengXiang == 4) {
            mFengXiang = 1;
        }

        switch (mFengXiang) {
            case 1:
                tvFengxiang.setText("风向:下");
                break;
            case 2:
                tvFengxiang.setText("风向:中");
                break;
            case 3:
                tvFengxiang.setText("风向:上");
                break;

        }
    }

    private void fengsu() {
        mFengSu++;
        if (mFengSu == 5) {
            mFengSu = 1;
        }

        switch (mFengSu) {
            case 1:
                S = "S0";
                tvFengsu.setText("风速:自动");
                break;
            case 2:
                S = "S1";
                tvFengsu.setText("风速:低");
                break;
            case 3:
                S = "S2";
                tvFengsu.setText("风速:中");
                break;
            case 4:
                S = "S3";
                tvFengsu.setText("风速:高");
                break;
        }

        setFaSong();
    }

    private void moshi() {

        mMoshi++;
        if (mMoshi == 6) {
            mMoshi = 1;
        }

        switch (mMoshi) {
            case 1:
                M = "M0";
                tvMoshi.setText("制冷");
                break;
            case 2:
                M = "M1";
                tvMoshi.setText("制热");
                break;
            case 3:
                M = "M2";
                tvMoshi.setText("自动");
                break;
            case 4:
                M = "M3";
                tvMoshi.setText("通风");
                break;
            case 5:
                M = "M4";
                tvMoshi.setText("除湿");
                break;
        }
        setFaSong();
    }

    private void setFaSong() {
        String pulse = M + "_" + T + "_" + S;
        setHongWai(getPulse(pulse));
    }

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

}
