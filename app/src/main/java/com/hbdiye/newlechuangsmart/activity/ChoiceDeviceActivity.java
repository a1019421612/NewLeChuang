package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.AddConditionAdapter;
import com.hbdiye.newlechuangsmart.adapter.ChoiceDeviceAdapter;
import com.hbdiye.newlechuangsmart.bean.RoomDeviceListBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class ChoiceDeviceActivity extends BaseActivity {

    @BindView(R.id.lv_choice_device)
    ListView lvChoiceDevice;
    private List<RoomDeviceListBean.RoomList> mList=new ArrayList<>();
    private ChoiceDeviceAdapter adapter;
    private String title="";

    @Override
    protected void initData() {
//        adapter.notifyDataSetChanged();
        title = getIntent().getStringExtra("title");
        tvBaseTitle.setText(title);
        roomAndDevice();
    }

    private void roomAndDevice() {
        OkHttpUtils.get()
                .url("http://192.168.0.115:8888/DYPServer/device/findDeviceListByProductId?token=1234567812345678123456&productId=PRO002003001")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        RoomDeviceListBean roomDeviceListBean = new Gson().fromJson(response, RoomDeviceListBean.class);
                        List<RoomDeviceListBean.RoomList> roomList = roomDeviceListBean.roomList;
                        mList.addAll(roomList);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected String getTitleName() {
        return title;
    }

    @Override
    protected void initView() {
        adapter=new ChoiceDeviceAdapter(this,mList);
        lvChoiceDevice.setAdapter(adapter);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_choice_device;
    }

}
