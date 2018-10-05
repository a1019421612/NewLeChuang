package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;

import java.util.List;

public class MessageAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public MessageAdapter(@Nullable List<String> data) {
        super(R.layout.message_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        int adapterPosition = helper.getAdapterPosition();
        if (adapterPosition%2==0){
            Glide.with(mContext).load(R.mipmap.banner_dian_focus).into((ImageView)helper.getView(R.id.iv_points));
        }else {
            Glide.with(mContext).load(R.mipmap.banner_dian_blur).into((ImageView)helper.getView(R.id.iv_points));
        }
    }
}
