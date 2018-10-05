package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;

import java.util.List;

public class WeekPopAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public WeekPopAdapter(@Nullable List<String> data) {
        super(R.layout.week_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_week,item);
    }
}
