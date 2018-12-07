package com.hbdiye.newlechuangsmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.ChoiceDeviceAdapter;
import com.hbdiye.newlechuangsmart.adapter.DeviceListAdapter;
import com.hbdiye.newlechuangsmart.adapter.ZhuJiAdapter;
import com.hbdiye.newlechuangsmart.bean.DeviceList;
import com.hbdiye.newlechuangsmart.bean.DeviceListSceneBean;
import com.hbdiye.newlechuangsmart.bean.GateWayBean;
import com.hbdiye.newlechuangsmart.bean.GatewaySectionBean;
import com.hbdiye.newlechuangsmart.bean.RoomDeviceListBean;
import com.hbdiye.newlechuangsmart.bean.SecneSectionBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class ZhiNengzjActivity extends BaseActivity {

    @BindView(R.id.rv_gateway_device)
    RecyclerView recyclerView;
    private String token;
    private List<GatewaySectionBean> mList=new ArrayList<>();
    private ZhuJiAdapter adapter;
    private String productId;
    private int icon;
    private String data="";
    private List<GateWayBean.RoomList> roomList;

    @Override
    protected void initData() {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.GATEWAYLIST))
                .addParams("token", token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        data = response;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String errcode = jsonObject.getString("errcode");
                            if (errcode.equals("0")) {
                                GateWayBean roomDeviceListBean = new Gson().fromJson(response, GateWayBean.class);
                                roomList = roomDeviceListBean.roomList;
                                if (roomList ==null){
                                    return;
                                }
                                if (mList.size() > 0) {
                                    mList.clear();
                                }
                                for (int i = 0; i < roomList.size(); i++) {
                                    GateWayBean.RoomList roomList1 = roomList.get(i);
                                    GatewaySectionBean secneSectionBean= new GatewaySectionBean(true, roomList1.name);
                                    secneSectionBean.setIshead(true);
                                    secneSectionBean.setTitle(roomList1.name);
                                    mList.add(secneSectionBean);
                                    for (int j = 0; j < roomList1.gatewayList.size(); j++) {
                                        GateWayBean.RoomList.GatewayList deviceList = roomList1.gatewayList.get(j);
                                        mList.add(new GatewaySectionBean(deviceList));
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected String getTitleName() {
        return "智能主机";
    }

    @Override
    protected void initView() {
        token = (String) SPUtils.get(this, "token", "");
        productId = getIntent().getStringExtra("productId");
        icon = getIntent().getIntExtra("icon",-1);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new ZhuJiAdapter(R.layout.test_device_item, R.layout.add_scene_device_header, mList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GatewaySectionBean secneSectionBean = mList.get(position);
                boolean ishead = secneSectionBean.isIshead();
                GateWayBean.RoomList.GatewayList gatewayList = secneSectionBean.t;
                if (!ishead){
                    startActivity(new Intent(ZhiNengzjActivity.this, ZhuJiDetailActivity.class)
                            .putExtra("data", data)
                            .putExtra("productId", gatewayList.productId)
                            .putExtra("deviceId",gatewayList.id));
                }
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_zhi_nengzj;
    }
}
