package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.LinkageListBean;

import java.util.List;

public class LinkageAdapter extends BaseQuickAdapter<LinkageListBean.LinkageList,BaseViewHolder> {
    public LinkageAdapter(@Nullable List<LinkageListBean.LinkageList> data) {
        super(R.layout.linkage_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LinkageListBean.LinkageList item) {
        helper.setText(R.id.tv_linkage_name,item.name);
        if (item.active==0){
            Glide.with(mContext).load(R.drawable.guan).into((ImageView) helper.getView(R.id.iv_linkage_kg));
        }else {
            Glide.with(mContext).load(R.drawable.kai).into((ImageView) helper.getView(R.id.iv_linkage_kg));
        }
        helper.addOnClickListener(R.id.iv_linkage_kg);
        helper.addOnClickListener(R.id.ll_linkage);
    }
}
