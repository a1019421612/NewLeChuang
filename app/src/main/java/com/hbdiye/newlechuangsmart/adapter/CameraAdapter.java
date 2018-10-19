package com.hbdiye.newlechuangsmart.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.videogo.openapi.bean.EZDeviceInfo;

import java.util.List;

public class CameraAdapter extends BaseQuickAdapter<EZDeviceInfo,BaseViewHolder> {
    public CameraAdapter(@Nullable List<EZDeviceInfo> data) {
        super(R.layout.device_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EZDeviceInfo item) {
        if (item!=null){
            if (item.getStatus()==2){
//                helper.setGone(R.id.item_offline,true);
//                helper.setGone(R.id.offline_bg,true);
                helper.setText(R.id.tv_online_status,"不在线");
                helper.setTextColor(R.id.tv_online_status, Color.parseColor("#df2d10"));
            }else {
//                helper.setGone(R.id.item_offline,false);
//                helper.setGone(R.id.offline_bg,false);
                helper.setText(R.id.tv_online_status,"在线");
                helper.setTextColor(R.id.tv_online_status, Color.parseColor("#39a4cb"));
            }
            helper.setText(R.id.camera_name_tv,item.getDeviceName());
            helper.setVisible(R.id.item_icon,true);
            String imageUrl = item.getDeviceCover();
            if(!TextUtils.isEmpty(imageUrl)) {
                Glide.with(mContext).load(imageUrl).into((ImageView)helper.getView(R.id.item_icon));
            }
        }
//        helper.addOnClickListener(R.id.tab_remoteplayback_btn);
//        helper.addOnClickListener(R.id.tab_alarmlist_btn);
        helper.addOnClickListener(R.id.tab_setdevice_btn);
    }
}
