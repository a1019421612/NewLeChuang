package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.RoomDetailDeviceBean;
import com.hbdiye.newlechuangsmart.bean.RoomDeviceBean;

import java.util.List;

public class RoomAdapter extends BaseQuickAdapter<RoomDetailDeviceBean.DeviceList,BaseViewHolder>{
    public RoomAdapter(@Nullable List<RoomDetailDeviceBean.DeviceList> data) {
        super(R.layout.room_device_gv_edit_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RoomDetailDeviceBean.DeviceList item) {
        Glide.with(mContext).load(R.drawable.liangjian).into((ImageView)helper.getView(R.id.gridview_item));
        helper.setGone(R.id.iv_device_del,false);
        helper.setText(R.id.tv_name,item.name);
    }
}
