package com.hbdiye.newlechuangsmart.hwactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.HwBaseActivity;
import com.hbdiye.newlechuangsmart.bean.DeviceBean;
import com.hbdiye.newlechuangsmart.bean.DeviceListBean;
import com.hbdiye.newlechuangsmart.bean.EditRemoteControlBean;
import com.hbdiye.newlechuangsmart.util.SharedpreUtils;
import com.hbdiye.newlechuangsmart.util.ToastUtils;
import com.xzcysoft.wnzhikonglibrary.WNZKConfigure;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class EditRemoteControlActivity extends HwBaseActivity {

    @BindView(R.id.et_name)
    EditText etName;
    private String deviceId;
    private String brandId;
    private String deviceName;
    private String hwbDeviceId;
    private String rid;
    private EditRemoteControlBean.Data data;
    private DeviceListBean diviceListBean;
    private int deviceUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_remote_control);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        deviceId = intent.getStringExtra("deviceId");
        deviceName = intent.getStringExtra("deviceName");
        brandId = intent.getStringExtra("brandId");
        hwbDeviceId = intent.getStringExtra("hwbDeviceId");
        rid = intent.getStringExtra("rid");
        deviceUrl = intent.getIntExtra("deviceUrl",0);
        setTitle("编辑" + deviceName + "遥控器");
        etName.setText(deviceName + "遥控器");

    }

    @OnClick(R.id.bt_ok)
    public void onViewClicked() {

        if (TextUtils.isEmpty(etName.getText().toString())) {
            ToastUtils.showToast(getApplicationContext(), "请输入遥控器名称");
            return;
        }

        WNZKConfigure.findDataIncludeKeyByRid(rid, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                EditRemoteControlBean editRemoteControlBean = new Gson().fromJson(response, EditRemoteControlBean.class);
                if (getResultCode(editRemoteControlBean.errcode)) {
                    data = editRemoteControlBean.data;
                    setJson();
                    startActivity(new Intent(getApplicationContext(), ConverterListActivity.class));

//                    ArrayList<Activity> mActivityList = MyApplication.mActivityList;
//                    for (int i = 0; i <mActivityList.size(); i++) {
//                        if(mActivityList.get(i)!=null){
//                            mActivityList.get(i).finish();
//                        }
//                    }
                }

            }
        });
    }


    public void setJson() {
        String string = SharedpreUtils.getString(getApplicationContext(), "hwbDeviceId" + hwbDeviceId, "");

        if (TextUtils.isEmpty(string)) {
            diviceListBean = new DeviceListBean();
            diviceListBean.device = new ArrayList<>();
        } else {
            diviceListBean = new Gson().fromJson(string, DeviceListBean.class);
        }
        DeviceBean device = new DeviceBean();
        device.deviceId = deviceId;
        device.deviceName = etName.getText().toString();
        device.deviceUrl = deviceUrl;
        device.rid = data.rid;
        if (data == null) {
            ToastUtils.showToast(getApplicationContext(), "暂无红外数据");
            return;
        }
        device.data = data;
        diviceListBean.device.add(device);
        String json = new Gson().toJson(diviceListBean);
        SharedpreUtils.putString(getApplicationContext(), "hwbDeviceId" + hwbDeviceId, json);
    }

}
