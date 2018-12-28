package com.hbdiye.newlechuangsmart.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.SingleWebSocketConnection;
import com.hbdiye.newlechuangsmart.adapter.MyDevicesAdapter;
import com.hbdiye.newlechuangsmart.bean.MyDevicesBean;
import com.hbdiye.newlechuangsmart.fragment.DeviceFragment;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.hbdiye.newlechuangsmart.view.DelDialog;
import com.hbdiye.newlechuangsmart.view.SceneDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.tavendo.autobahn.WebSocketConnection;
import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.MYDEVICES;
import static com.hbdiye.newlechuangsmart.global.InterfaceManager.UPDATEDEVICENAME;

public class MyDeviceActivity extends BaseActivity {
    @BindView(R.id.rv_my_devices)
    RecyclerView rvMyDevices;

    private String token;
    private List<MyDevicesBean.DeviceList> mList = new ArrayList<>();
    private MyDevicesAdapter adapter;

    private boolean editStatus = false;//编辑状态标志，默认false

    private WebSocketConnection mConnection;
    private HomeReceiver homeReceiver;
    private DelDialog delDialog;
    private SceneDialog sceneDialog;
    private String editName_device_id;

    @Override
    protected void initData() {
        deviceList();
    }

    @Override
    protected String getTitleName() {
        return "我的设备";
    }

    @Override
    protected void initView() {
        mConnection = SingleWebSocketConnection.getInstance();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DOPP");
        homeReceiver = new HomeReceiver();
        registerReceiver(homeReceiver, intentFilter);

        tvBaseEnter.setVisibility(View.VISIBLE);
        tvBaseEnter.setText("编辑");
        tvBaseEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editStatus) {
                    tvBaseEnter.setText("编辑");
                    adapter.deviceStatusChange(editStatus);
                    editStatus = false;
                } else {
                    tvBaseEnter.setText("完成");
                    adapter.deviceStatusChange(editStatus);
                    editStatus = true;
                }
            }
        });

        token = (String) SPUtils.get(this, "token", "");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMyDevices.setLayoutManager(manager);
        adapter = new MyDevicesAdapter(mList);
        rvMyDevices.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                switch (view.getId()) {
                    case R.id.ll_mydevice_item_del:
                        delDialog = new DelDialog(MyDeviceActivity.this, R.style.MyDialogStyle, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String id = mList.get(position).id;
//                        "{\"pn\":\"DOPP\",\"pt\":\"T\",\"pid\":\"%@\",\"token\":\"%@\",\"oper\":\"102\",\"sdid\":\"%@\"}"
                                mConnection.sendTextMessage("{\"pn\":\"DOPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"102\",\"sdid\":\"" + id + "\"}");
                                delDialog.dismiss();
                            }
                        }, "是否删除设备？");
                        delDialog.show();
                        break;
                    case R.id.tv_device_ed_name:
                        editName_device_id = mList.get(position).id;
                        sceneDialog = new SceneDialog(MyDeviceActivity.this, R.style.MyDialogStyle, edit_name_clicker, "设备名称");
                        sceneDialog.show();
                        break;
                }
            }
        });
    }
    private View.OnClickListener edit_name_clicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.app_cancle_tv:
                    sceneDialog.dismiss();
                    break;
                case R.id.app_sure_tv:
                    String linkageName = sceneDialog.getSceneName();
                    if (!TextUtils.isEmpty(linkageName)) {
                        updateDeviceName(linkageName);
                    }
                    break;
            }
        }
    };

    private void updateDeviceName(String linkageName) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(UPDATEDEVICENAME))
                .addParams("token",token)
                .addParams("deviceId",editName_device_id)
                .addParams("name",linkageName)
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
                                if (sceneDialog!=null){
                                    sceneDialog.dismiss();
                                }
                                deviceList();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_my_device;
    }

    private void deviceList() {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(MYDEVICES))
                .addParams("token", token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MyDevicesBean myDevicesBean = new Gson().fromJson(response, MyDevicesBean.class);
                        if (myDevicesBean.errcode.equals("0")) {
                            List<MyDevicesBean.DeviceList> deviceList = myDevicesBean.deviceList;
                            if (mList.size() > 0) {
                                mList.clear();
                            }
                            mList.addAll(deviceList);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    class HomeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String message = intent.getStringExtra("message");
            if (action.equals("DOPP")) {
                try {
                    JSONObject jsonObject = new JSONObject(message);
                    String ecode = jsonObject.getString("ecode");
                    if (!ecode.equals("0")) {
                        String s = EcodeValue.resultEcode(ecode);
                        SmartToast.show(s);
                    } else {
                        deviceList();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                parseData(message);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(homeReceiver);
    }
}
