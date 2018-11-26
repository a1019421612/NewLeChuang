package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.ChoiceDeviceAdapter;
import com.hbdiye.newlechuangsmart.adapter.ZhuJiAdapter;
import com.hbdiye.newlechuangsmart.bean.GateWayBean;
import com.hbdiye.newlechuangsmart.bean.RoomDeviceListBean;
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

    @BindView(R.id.lv_zj_device)
    ListView lvZjDevice;
    private String token;
    private List<GateWayBean.RoomList> mList=new ArrayList<>();
    private ZhuJiAdapter adapter;
    private String productId;
    private int icon;
    private String data="";

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
                                List<GateWayBean.RoomList> roomList = roomDeviceListBean.roomList;
                                if (roomList==null){
                                    return;
                                }
                                mList.addAll(roomList);
                                adapter=new ZhuJiAdapter(ZhiNengzjActivity.this,mList,productId,icon,data);
                                lvZjDevice.setAdapter(adapter);
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

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_zhi_nengzj;
    }
}
