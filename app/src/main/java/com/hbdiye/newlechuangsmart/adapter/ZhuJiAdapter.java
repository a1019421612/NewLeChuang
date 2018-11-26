package com.hbdiye.newlechuangsmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.DeviceClassyActivity;
import com.hbdiye.newlechuangsmart.activity.ZhuJiDetailActivity;
import com.hbdiye.newlechuangsmart.bean.GateWayBean;
import com.hbdiye.newlechuangsmart.bean.RoomDeviceListBean;
import com.hbdiye.newlechuangsmart.view.MyGridView;

import java.util.List;

public class ZhuJiAdapter extends BaseAdapter {
    private Context context;
    private List<GateWayBean.RoomList> mList;
    private String productId;
    private int icon;
    private String data;

    public ZhuJiAdapter(Context context, List<GateWayBean.RoomList> mList, String productId,int icon,String data) {
        this.context = context;
        this.mList = mList;
        this.productId = productId;
        this.icon=icon;
        this.data=data;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.choice_device_item, null);
        MyGridView myGridView = view.findViewById(R.id.mlv_condition);
        TextView tv_name = view.findViewById(R.id.tv_condition_name);
        final String name = mList.get(i).name;
        tv_name.setText(name);
        final List<GateWayBean.RoomList.GatewayList> deviceList = mList.get(i).gatewayList;
        ChoiceZhujiItemAdapter adapter = new ChoiceZhujiItemAdapter(context, deviceList,icon);
        myGridView.setAdapter(adapter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                context.startActivity(new Intent(context, ZhuJiDetailActivity.class)
                        .putExtra("data", data)
                        .putExtra("roomId", deviceList.get(i).roomId)
                        .putExtra("productId", deviceList.get(i).productId)
                        .putExtra("deviceId",deviceList.get(i).id));
            }
        });
        return view;
    }
}
