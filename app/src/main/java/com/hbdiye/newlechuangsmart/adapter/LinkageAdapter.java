package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.LinkageBean;

import java.util.List;

public class LinkageAdapter extends BaseQuickAdapter<LinkageBean,BaseViewHolder> {
    public LinkageAdapter(@Nullable List<LinkageBean> data) {
        super(R.layout.linkage_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LinkageBean item) {

    }
}
