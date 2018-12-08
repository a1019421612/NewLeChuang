package com.hbdiye.newlechuangsmart.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.SingleWebSocketConnection;
import com.hbdiye.newlechuangsmart.adapter.DeviceListAdapter;
import com.hbdiye.newlechuangsmart.bean.DeviceList;
import com.hbdiye.newlechuangsmart.bean.DeviceListSceneBean;
import com.hbdiye.newlechuangsmart.bean.SecneSectionBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.SPUtils;
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

public class ChoiceDeviceActivity extends BaseActivity {

    @BindView(R.id.rv_choice_device)
    RecyclerView rvChoiceDevice;
    //    @BindView(R.id.lv_choice_device)
    //    private int icon;
    //    private ChoiceDeviceAdapter adapter;
    //    private List<RoomDeviceListBean.RoomList> mList=new ArrayList<>();
    //    ListView lvChoiceDevice;
    //    private String token;
    private String productId;
    private String token;
    private String title = "";
    private DeviceListAdapter adapter;
    private List<SecneSectionBean> mList = new ArrayList<>();
    private WebSocketConnection mConnection;
    private HomeReceiver homeReceiver;
    private String all_data;

    @Override
    protected void initData() {
//        adapter.notifyDataSetChanged();
        token = (String) SPUtils.get(this, "token", "");
        title = getIntent().getStringExtra("title");

        tvBaseTitle.setText(title);
        roomAndDevice();
        deviceList();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SecneSectionBean secneSectionBean = mList.get(position);
                boolean ishead = secneSectionBean.isIshead();
                DeviceList deviceList = secneSectionBean.t;
                if (!ishead) {
                    String productId = deviceList.productId;
                    String roomId = deviceList.roomId;
//                    SmartToast.show();
                    startActivity(new Intent(ChoiceDeviceActivity.this, DeviceDetailActivity.class)
                            .putExtra("productId", productId)
                            .putExtra("all_data", all_data)
                            .putExtra("roomId", roomId));
                }
            }
        });
    }

    private void deviceList() {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.ALLDEVICELIST))
                .addParams("token", token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {


                        DeviceListSceneBean deviceListSceneBean = new Gson().fromJson(response, DeviceListSceneBean.class);
                        String errcode = deviceListSceneBean.errcode;
                        List<DeviceListSceneBean.RoomList> roomList = deviceListSceneBean.roomList;
                        if (errcode.equals("0")) {
                            all_data = response;

                        }
//                            if (mList.size()>0){
//                                mList.clear();
//                            }
//                            for (int i = 0; i < roomList.size(); i++) {
//                                DeviceListSceneBean.RoomList roomList1 = roomList.get(i);
//                                SecneSectionBean secneSectionBean = new SecneSectionBean(true, roomList1.name);
//                                secneSectionBean.setIshead(true);
//                                secneSectionBean.setTitle(roomList1.name);
//                                mList.add(secneSectionBean);
//                                for (int j = 0; j < roomList1.deviceList.size(); j++) {
//                                    DeviceList deviceList = roomList1.deviceList.get(j);
//                                    mList.add(new SecneSectionBean(deviceList));
//                                }
//                            }
//                            adapter.notifyDataSetChanged();
//                        }
                    }
                });
    }

    private void roomAndDevice() {
        OkHttpUtils.get()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.FINDDEVICELISTBYPRODUCTID))
                .addParams("token", token)
                .addParams("productId", productId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        RoomDeviceListBean roomDeviceListBean = new Gson().fromJson(response, RoomDeviceListBean.class);
//                        if (roomDeviceListBean.errcode == 0) {
//                            List<RoomDeviceListBean.RoomList> roomList = roomDeviceListBean.roomList;
//                            if (roomList == null) {
//                                return;
//                            }
//                            mList.addAll(roomList);
//                            adapter.notifyDataSetChanged();
//                        }
                        DeviceListSceneBean deviceListSceneBean = new Gson().fromJson(response, DeviceListSceneBean.class);
                        String errcode = deviceListSceneBean.errcode;
                        List<DeviceListSceneBean.RoomList> roomList = deviceListSceneBean.roomList;
                        if (errcode.equals("0")) {
                            if (mList.size() > 0) {
                                mList.clear();
                            }
                            for (int i = 0; i < roomList.size(); i++) {
                                DeviceListSceneBean.RoomList roomList1 = roomList.get(i);
                                SecneSectionBean secneSectionBean = new SecneSectionBean(true, roomList1.name);
                                secneSectionBean.setIshead(true);
                                secneSectionBean.setTitle(roomList1.name);
                                mList.add(secneSectionBean);
                                for (int j = 0; j < roomList1.deviceList.size(); j++) {
                                    DeviceList deviceList = roomList1.deviceList.get(j);
                                    mList.add(new SecneSectionBean(deviceList));
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    protected String getTitleName() {
        return title;
    }

    @Override
    protected void initView() {
        productId = getIntent().getStringExtra("productId");
        mConnection = SingleWebSocketConnection.getInstance();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DCPP");
        intentFilter.addAction("DAPP");
        homeReceiver = new HomeReceiver();
        registerReceiver(homeReceiver, intentFilter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvChoiceDevice.setLayoutManager(manager);
        adapter = new DeviceListAdapter(R.layout.test_device_item, R.layout.add_scene_device_header, mList);
        rvChoiceDevice.setAdapter(adapter);
        handlerClicker();
    }

    private void handlerClicker() {
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                SecneSectionBean secneSectionBean = mList.get(position);
                boolean ishead = secneSectionBean.isIshead();
                DeviceList deviceList = secneSectionBean.t;
                String deviceid = deviceList.id;
                List<DeviceList.DevAttList> devAttList = deviceList.devAttList;
                List<DeviceList.DevActList> devActList = deviceList.devActList;
                if (!ishead) {
                    switch (view.getId()) {
                        case R.id.iv_device_left:
                            if (deviceList.productId.equals("PRO003004001")) {
                                //智能门锁
                                int value = devAttList.get(0).value;
                                if (value == 0) {
                                    //开
                                    for (int i = 0; i < devActList.size(); i++) {
                                        int comNo = devActList.get(i).comNo;
                                        if (comNo == 1) {
                                            String dactid = devActList.get(i).id;
                                            String param = devActList.get(i).param;
                                            mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"201\",\"sdid\":\"" + deviceid + "\",\"dactid\":\"" + dactid + "\",\"param\":\"" + param + "\"}");
                                        }
                                    }
                                } else {
                                    //关
                                    for (int i = 0; i < devActList.size(); i++) {
                                        int comNo = devActList.get(i).comNo;
                                        if (comNo == 0) {
                                            String dactid = devActList.get(i).id;
                                            String param = devActList.get(i).param;
                                            mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"201\",\"sdid\":\"" + deviceid + "\",\"dactid\":\"" + dactid + "\",\"param\":\"" + param + "\"}");
                                        }
                                    }
                                }
                            } else {
                                //开关
                                for (int i = 0; i < devAttList.size(); i++) {
                                    int value = devAttList.get(i).value;
                                    int port_left = devAttList.get(i).port;
                                    if (value == 0 && port_left == 1) {
                                        //左开
                                        for (int j = 0; j < devActList.size(); j++) {
                                            int port = devActList.get(j).port;
                                            int comNo = devActList.get(j).comNo;
                                            if (port == 1 && comNo == 1) {
                                                String dactid = devActList.get(j).id;
                                                mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"201\",\"sdid\":\"" + deviceid + "\",\"dactid\":\"" + dactid + "\",\"param\":\"\"}");
                                                return;
                                            }
                                        }
                                    } else if (value == 1 && port_left == 1) {
                                        //左关
                                        for (int j = 0; j < devActList.size(); j++) {
                                            int port = devActList.get(j).port;
                                            int comNo = devActList.get(j).comNo;
                                            if (port == 1 && comNo == 0) {
                                                String dactid = devActList.get(j).id;
                                                mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"201\",\"sdid\":\"" + deviceid + "\",\"dactid\":\"" + dactid + "\",\"param\":\"\"}");
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case R.id.iv_device_middle:
                            for (int i = 0; i < devAttList.size(); i++) {
                                int value = devAttList.get(i).value;
                                int port_middle = devAttList.get(i).port;
                                if (value == 0 && port_middle == 2) {
                                    //中开
                                    for (int j = 0; j < devActList.size(); j++) {
                                        int port = devActList.get(j).port;
                                        int comNo = devActList.get(j).comNo;
                                        if (port == 2 && comNo == 1) {
                                            String dactid = devActList.get(j).id;
                                            mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"201\",\"sdid\":\"" + deviceid + "\",\"dactid\":\"" + dactid + "\",\"param\":\"\"}");
                                            return;
                                        }
                                    }
                                } else if ((value == 1 && port_middle == 2)) {
                                    //中关
                                    for (int j = 0; j < devActList.size(); j++) {
                                        int port = devActList.get(j).port;
                                        int comNo = devActList.get(j).comNo;
                                        if (port == 2 && comNo == 0) {
                                            String dactid = devActList.get(j).id;
                                            mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"201\",\"sdid\":\"" + deviceid + "\",\"dactid\":\"" + dactid + "\",\"param\":\"\"}");
                                            return;
                                        }
                                    }
                                }
                            }
                            break;
                        case R.id.iv_device_right:
                            for (int i = 0; i < devAttList.size(); i++) {
                                int value = devAttList.get(i).value;
                                int port_right = devAttList.get(i).port;
                                if (value == 0 && port_right == 3) {
                                    //右开
                                    for (int j = 0; j < devActList.size(); j++) {
                                        int port = devActList.get(j).port;
                                        int comNo = devActList.get(j).comNo;
                                        if (port == 3 && comNo == 1) {
                                            String dactid = devActList.get(j).id;
                                            mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"201\",\"sdid\":\"" + deviceid + "\",\"dactid\":\"" + dactid + "\",\"param\":\"\"}");
                                            return;
                                        }
                                    }
                                } else if (value == 1 && port_right == 3) {
                                    //右关
                                    for (int j = 0; j < devActList.size(); j++) {
                                        int port = devActList.get(j).port;
                                        int comNo = devActList.get(j).comNo;
                                        if (port == 3 && comNo == 0) {
                                            String dactid = devActList.get(j).id;
                                            mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"201\",\"sdid\":\"" + deviceid + "\",\"dactid\":\"" + dactid + "\",\"param\":\"\"}");
                                            return;
                                        }
                                    }
                                }
                            }
                            break;
                        case R.id.iv_device_warn_s:
//                            SmartToast.show("报警_声");
                            for (int i = 0; i < devActList.size(); i++) {
                                String param = devActList.get(i).param;
                                if ("10000A0000".equals(param)) {
                                    String dactid = devActList.get(i).id;
                                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"201\",\"sdid\":\"" + deviceid + "\",\"dactid\":\"" + dactid + "\",\"param\":\"" + param + "\"}");
                                }
                            }
                            break;
                        case R.id.iv_device_warn_g:
//                            SmartToast.show("报警_光");
                            for (int i = 0; i < devActList.size(); i++) {
                                String param = devActList.get(i).param;
                                if ("14000A0000".equals(param)) {
                                    String dactid = devActList.get(i).id;
                                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"201\",\"sdid\":\"" + deviceid + "\",\"dactid\":\"" + dactid + "\",\"param\":\"" + param + "\"}");
                                }
                            }
                            break;
                        case R.id.iv_device_warn_stop:
//                            SmartToast.show("报警_停止");
                            for (int i = 0; i < devActList.size(); i++) {
                                String param = devActList.get(i).param;
                                if ("0000000000".equals(param)) {
                                    String dactid = devActList.get(i).id;
                                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"201\",\"sdid\":\"" + deviceid + "\",\"dactid\":\"" + dactid + "\",\"param\":\"" + param + "\"}");
                                }
                            }
                            break;
                        case R.id.iv_device_cl_close:
//                            SmartToast.show("窗帘_关");
                            for (int i = 0; i < devActList.size(); i++) {
                                int comNo = devActList.get(i).comNo;
                                if (comNo == 0) {
                                    String dactid = devActList.get(i).id;
                                    String param = devActList.get(i).param;
                                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"201\",\"sdid\":\"" + deviceid + "\",\"dactid\":\"" + dactid + "\",\"param\":\"" + param + "\"}");
                                }
                            }
                            break;
                        case R.id.iv_device_cl_open:
//                            SmartToast.show("窗帘_开");
                            for (int i = 0; i < devActList.size(); i++) {
                                int comNo = devActList.get(i).comNo;
                                if (comNo == 1) {
                                    String dactid = devActList.get(i).id;
                                    String param = devActList.get(i).param;
                                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"201\",\"sdid\":\"" + deviceid + "\",\"dactid\":\"" + dactid + "\",\"param\":\"" + param + "\"}");
                                }
                            }
                            break;
                        case R.id.iv_device_cl_stop:
//                            SmartToast.show("窗帘_停止");
                            for (int i = 0; i < devActList.size(); i++) {
                                int comNo = devActList.get(i).comNo;
                                if (comNo == 2) {
                                    String dactid = devActList.get(i).id;
                                    String param = devActList.get(i).param;
                                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"201\",\"sdid\":\"" + deviceid + "\",\"dactid\":\"" + dactid + "\",\"param\":\"" + param + "\"}");
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_choice_device;
    }

    class HomeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String message = intent.getStringExtra("message");
            if (action.equals("DCPP")) {
                try {
                    JSONObject jsonObject = new JSONObject(message);
                    String ecode = jsonObject.getString("ecode");
                    if (!ecode.equals("0")) {
                        String s = EcodeValue.resultEcode(ecode);
                        SmartToast.show(s);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                parseData(message);
            } else if (action.equals("DAPP")) {
                try {
                    JSONObject jsonObject = new JSONObject(message);
                    String sdid = jsonObject.getString("sdid");
                    roomAndDevice();
//                    if (sdid.equals(deviceid)){
//                        DappBean dappBean = new Gson().fromJson(message, DappBean.class);
//                        List<DappBean.Atts> atts = dappBean.atts;
//                        for (int i = 0; i < atts.size(); i++) {
//                            int port = atts.get(i).port;
//                            int value = atts.get(i).value;
//                            if (port==1){
//                                value_left=value;
//                                setViewByAtt(value_left,ivKgLeft,tvKgLeft);
//                            }else if (port==2){
//                                value_middle=value;
//                                setViewByAtt(value_middle,ivKgMiddle,tvKgMiddle);
//                            }else if (port==3){
//                                value_right=value;
//                                setViewByAtt(value_right,ivKgRight,tvKgRight);
//                            }
//                        }
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(homeReceiver);
    }
}
