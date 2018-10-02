package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.RoomDeviceBean;

import java.util.List;

public class RoomAdapter extends BaseQuickAdapter<RoomDeviceBean,BaseViewHolder>{
    public RoomAdapter(@Nullable List<RoomDeviceBean> data) {
        super(R.layout.device_gv_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RoomDeviceBean item) {

    }
}
