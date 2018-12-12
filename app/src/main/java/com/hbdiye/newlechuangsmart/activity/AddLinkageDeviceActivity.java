package com.hbdiye.newlechuangsmart.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.AddSceneDeviceAdapter;
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
import okhttp3.Call;

public class AddLinkageDeviceActivity extends BaseActivity {
    @BindView(R.id.rv_add_scene_device)
    RecyclerView rvAddSceneDevice;

    private AddSceneDeviceAdapter adapter;
    private List<SecneSectionBean> mList = new ArrayList<>();
    private String token;
    private String linkageId;
    @Override
    protected void initData() {
        token = (String) SPUtils.get(this,"token","");
        linkageId=getIntent().getStringExtra("linkageId");
        deviceList();
    }

    @Override
    protected String getTitleName() {
        return "添加任务";
    }

    @Override
    protected void initView() {
        rvAddSceneDevice.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new AddSceneDeviceAdapter(R.layout.add_scene_device_item, R.layout.add_scene_device_header, mList);
        rvAddSceneDevice.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SecneSectionBean secneSectionBean = mList.get(position);
                boolean isHeader = secneSectionBean.isHeader;
                if (!isHeader){
                    DeviceList deviceList = secneSectionBean.t;
                    String deviceId = deviceList.id;
                    List<DeviceList.DevActList> devActList = deviceList.devActList;
                    String param = devActList.get(0).param;
                    String devActId = devActList.get(0).id;
                    OkHttpUtils
                            .post()
                            .url(InterfaceManager.getInstance().getURL(InterfaceManager.CREATETASK))
                            .addParams("token",token)
                            .addParams("linkageId",linkageId)
                            .addParams("deviceId",deviceId)
                            .addParams("delaytime","0")
                            .addParams("param",param)
                            .addParams("devActId",devActId)
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
                                            setResult(99,new Intent());
                                            finish();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
            }
        });
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
    protected int getLayoutID() {
        return R.layout.activity_add_linkage_device;
    }
}
