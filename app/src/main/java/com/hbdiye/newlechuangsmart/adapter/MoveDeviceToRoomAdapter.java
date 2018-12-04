package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.RoomListBean;

import java.util.List;

public class MoveDeviceToRoomAdapter extends BaseQuickAdapter<RoomListBean.RoomList,BaseViewHolder>{
    public MoveDeviceToRoomAdapter( @Nullable List<RoomListBean.RoomList> data) {
        super(R.layout.alertdialog_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RoomListBean.RoomList item) {
        helper.setText(R.id.tv_alert_content,item.name);
    }
}
