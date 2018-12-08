package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.DeviceListSceneBean;

import java.util.List;

public class RoomDeviceDetailAdapter extends BaseQuickAdapter<DeviceListSceneBean.RoomList,BaseViewHolder> {
    public RoomDeviceDetailAdapter( @Nullable List<DeviceListSceneBean.RoomList> data) {
        super(R.layout.room_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceListSceneBean.RoomList item) {
        helper.setText(R.id.tv_room_name,item.name);
    }
}
