package com.hbdiye.newlechuangsmart.hwactivity;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.activity.HwBaseActivity;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.DeviceListBean;
import com.hbdiye.newlechuangsmart.util.SharedpreUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetUpActivity extends HwBaseActivity {

    private int rid;
    private String hwbId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("设置");
        rid= getIntent().getIntExtra("rid",0);
        hwbId = getIntent().getStringExtra("hwbId");
    }

    @OnClick({R.id.sv_edit_control, R.id.sv_delete_control})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sv_edit_control:

                break;
            case R.id.sv_delete_control:
                deleteControl();
                break;
        }
    }

    private void deleteControl() {
        String string = SharedpreUtils.getString(getApplicationContext(), "hwbDeviceId" + hwbId, "");
        DeviceListBean deviceListBean = new Gson().fromJson(string, DeviceListBean.class);
        for (int i = 0; i < deviceListBean.device.size(); i++) {
            if(deviceListBean.device.get(i).rid==rid){
                deviceListBean.device.remove(i);
                break;
            }
        }
        String json = new Gson().toJson(deviceListBean);
        SharedpreUtils.putString(getApplicationContext(), "hwbDeviceId" + hwbId, json);
        setResult(1002);
        finish();
    }
}
