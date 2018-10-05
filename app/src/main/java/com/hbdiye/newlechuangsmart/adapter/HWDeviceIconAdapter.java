package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;

import java.util.List;

public class HWDeviceIconAdapter extends BaseQuickAdapter<Integer,BaseViewHolder> {
    private List<String> list;
    public HWDeviceIconAdapter(@Nullable List<Integer> data,List<String> list) {
        super(R.layout.device_gv_item, data);
        this.list=list;
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        int adapterPosition = helper.getAdapterPosition();
        Glide.with(mContext).load(item).into((ImageView)helper.getView(R.id.gridview_item));
        helper.setText(R.id.tv_content,list.get(adapterPosition));
    }
}
