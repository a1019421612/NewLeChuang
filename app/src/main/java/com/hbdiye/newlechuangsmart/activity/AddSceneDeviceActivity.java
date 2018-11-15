package com.hbdiye.newlechuangsmart.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.SingleWebSocketConnection;
import com.hbdiye.newlechuangsmart.adapter.AddSceneDeviceAdapter;
import com.hbdiye.newlechuangsmart.bean.DappBean;
import com.hbdiye.newlechuangsmart.bean.DeviceList;
import com.hbdiye.newlechuangsmart.bean.DeviceListSceneBean;
import com.hbdiye.newlechuangsmart.bean.SecneSectionBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.hbdiye.newlechuangsmart.view.AttributeDialog;
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

public class AddSceneDeviceActivity extends BaseActivity {

    @BindView(R.id.rv_add_scene_device)
    RecyclerView rvAddSceneDevice;
    private AddSceneDeviceAdapter adapter;
    private List<SecneSectionBean> mList = new ArrayList<>();
    private List<String> list=new ArrayList<>();
    private SecneSectionBean secneSectionBean;
    private AttributeDialog dialog;
    private String token;
    private WebSocketConnection mConnection;
    private HomeReceiver homeReceiver;
    private List<DeviceList.DevActList> devActList;
    private String sceneId;
    private String dactid="";

    @Override
    protected void initData() {
        token = (String) SPUtils.get(this,"token","");
        sceneId = getIntent().getStringExtra("sceneId");
        deviceList();
    }

    private void deviceList() {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.DEVICELISTSCENE))
                .addParams("token",token)
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
                        if (errcode.equals("0")){
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
        return "选择设备";
    }

    @Override
    protected void initView() {
        mConnection = SingleWebSocketConnection.getInstance();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DCPP");
        intentFilter.addAction("DAPP");
        homeReceiver = new HomeReceiver();
        registerReceiver(homeReceiver, intentFilter);
        rvAddSceneDevice.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new AddSceneDeviceAdapter(R.layout.add_scene_device_item, R.layout.add_scene_device_header, mList);
        rvAddSceneDevice.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                list.clear();
                SecneSectionBean secneSectionBean = mList.get(position);
                boolean ishead = secneSectionBean.isIshead();
                if (!ishead) {
                    devActList = secneSectionBean.t.devActList;
                    List<DeviceList.DevAttList> devAttList = secneSectionBean.t.devAttList;
//                    for (int i = 0; i < devActList.size(); i++) {
////                        list.add(i+"");
//                        DeviceList.DevActList devActList1 = devActList.get(i);
//                    }
                    dialog = new AttributeDialog(AddSceneDeviceActivity.this, R.style.MyDialogStyle, clickListener, devActList,gv_click);
                    dialog.show();
                }
            }
        });
    }
    public AdapterView.OnItemClickListener gv_click=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            dialog.changeColor(position);
            DeviceList.DevActList devActList = AddSceneDeviceActivity.this.devActList.get(position);
            dactid = devActList.id;
            String deviceid = devActList.deviceId;
//            if (position%2==0){
//                Log.e("sss","左关");
//                   String sss="{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}";
                mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+ dactid +"\",\"param\":\"\"}");
//            }else {
//                Log.e("sss","右开");
//                mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
//            }
        }
    };
    public View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_attr_cancel:
                    dialog.dismiss();
                    break;
                case R.id.tv_attr_ok:
                    if (TextUtils.isEmpty(dactid)){
                        return;
                    }
                    //"{\"pn\":\"DOPP\",\"pt\":\"T\",\"pid\":\"%@\",\"token\":\"%@\",\"oper\":\"103\",\"sceid\":\"%@\",\"dactid\":\"%@\",\"param\":\"%@\"}"
                    mConnection.sendTextMessage("{\"pn\":\"DOPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"103\",\"sceid\":\""+sceneId+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
                    dialog.dismiss();
                    break;
            }
        }
    };

    @Override
    protected int getLayoutID() {
        return R.layout.activity_add_scene_device;
    }
    class HomeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String message = intent.getStringExtra("message");
            if (action.equals("DCPP")) {
//                try {
//                    JSONObject jsonObject=new JSONObject(message);
//                    String sdid = jsonObject.getString("sdid");
//                    if (sdid.equals(deviceid)){
//                        String ecode = jsonObject.getString("ecode");
//                        if (!ecode.equals("0")){
//                            String s = EcodeValue.resultEcode(ecode);
//                            SmartToast.show(s);
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                parseData(message);
            }else if (action.equals("DAPP")){
//                try {
//                    JSONObject jsonObject=new JSONObject(message);
//                    String sdid = jsonObject.getString("sdid");
//                    if (sdid.equals(deviceid)){
//                        DappBean dappBean = new Gson().fromJson(message, DappBean.class);
//                        List<DappBean.Atts> atts = dappBean.atts;
//                        for (int i = 0; i < atts.size(); i++) {
//                            int port = atts.get(i).port;
//                            value_left = atts.get(i).value;
//                            if (port==1){
//                                setViewByAtt(value_left,iv_att,tv_att_name);
//                            }
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(homeReceiver);
    }
}
