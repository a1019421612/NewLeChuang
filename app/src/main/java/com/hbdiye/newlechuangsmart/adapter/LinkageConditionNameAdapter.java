package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.LinkageDetailBean;

import java.util.List;

public class LinkageConditionNameAdapter extends BaseQuickAdapter<LinkageDetailBean.LinkageConditionLists.DevAttList,BaseViewHolder>{
    public LinkageConditionNameAdapter(@Nullable List<LinkageDetailBean.LinkageConditionLists.DevAttList> data) {
        super(R.layout.alertdialog_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LinkageDetailBean.LinkageConditionLists.DevAttList item) {
        helper.setText(R.id.tv_alert_content,item.name);
    }
}
