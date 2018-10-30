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
import com.hbdiye.newlechuangsmart.bean.RoomDeviceListBean;
import com.hbdiye.newlechuangsmart.view.MyGridView;

import java.util.List;

public class ChoiceDeviceAdapter extends BaseAdapter {
    private Context context;
    private List<RoomDeviceListBean.RoomList> mList;

    public ChoiceDeviceAdapter(Context context, List<RoomDeviceListBean.RoomList> mList) {
        this.context = context;
        this.mList = mList;
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
        final List<RoomDeviceListBean.RoomList.DeviceList> deviceList = mList.get(i).deviceList;
        ChoiceDeviceItemAdapter adapter = new ChoiceDeviceItemAdapter(context, deviceList);
        myGridView.setAdapter(adapter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                context.startActivity(new Intent(context, DeviceClassyActivity.class)
                        .putExtra("roomName", name)
                        .putExtra("roomId", deviceList.get(i).roomId));
            }
        });
        return view;
    }
}
