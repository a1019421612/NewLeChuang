package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.LinkageDetailBean;

import java.util.List;

public class LinkageConditionRangeAdapter extends BaseQuickAdapter<String,BaseViewHolder>{
    public LinkageConditionRangeAdapter(@Nullable List<String> data) {
        super(R.layout.alertdialog_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_alert_content,item);
    }
}
