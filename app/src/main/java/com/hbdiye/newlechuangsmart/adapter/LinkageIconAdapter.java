package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;

import java.util.List;

public class LinkageIconAdapter extends BaseQuickAdapter<Integer,BaseViewHolder> {
    public LinkageIconAdapter( @Nullable List<Integer> data) {
        super(R.layout.icon_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {

        Glide.with(mContext).load(item).into((ImageView)helper.getView(R.id.iv_icon));
    }
}
