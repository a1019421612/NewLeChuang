package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.RoomDeviceBean;

import java.util.List;

public class RoomAdapter extends BaseQuickAdapter<RoomDeviceBean,BaseViewHolder>{
    public RoomAdapter(@Nullable List<RoomDeviceBean> data) {
        super(R.layout.room_device_gv_edit_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RoomDeviceBean item) {
        Glide.with(mContext).load(R.drawable.liangjian).into((ImageView)helper.getView(R.id.gridview_item));
        helper.setText(R.id.tv_name,"两键开关");
    }
}
