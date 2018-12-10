package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.MyDevicesBean;
import com.hbdiye.newlechuangsmart.util.ClassyIconByProId;

import java.util.List;

public class MyDevicesAdapter extends BaseQuickAdapter<MyDevicesBean.DeviceList,BaseViewHolder>{
    private boolean isShow=true;
    public MyDevicesAdapter(@Nullable List<MyDevicesBean.DeviceList> data) {
        super(R.layout.my_device_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyDevicesBean.DeviceList item) {
        if (isShow){
            helper.setGone(R.id.ll_mydevice_item_del,false);
        }else {
            helper.setGone(R.id.ll_mydevice_item_del,true);
        }
        helper.setText(R.id.tv_scene_device_name, item.name);
        Glide.with(mContext).load(ClassyIconByProId.deviceIcon(item.productId)).into((ImageView) helper.getView(R.id.iv_device_icon));
        helper.addOnClickListener(R.id.ll_mydevice_item_del);
    }
    public void deviceStatusChange(boolean status){
        isShow=status;
        notifyDataSetChanged();
    }
}
