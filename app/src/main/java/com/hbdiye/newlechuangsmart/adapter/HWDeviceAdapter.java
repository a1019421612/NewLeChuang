package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;

import java.util.List;

public class HWDeviceAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public HWDeviceAdapter(@Nullable List<String> data) {
        super(R.layout.hw_device_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Glide.with(mContext).load(R.drawable.dianshiji).into((ImageView)helper.getView(R.id.gridview_item));
        helper.setText(R.id.tv_content,"电视");
    }
}
