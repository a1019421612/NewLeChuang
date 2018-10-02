package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.ConditionPop;

import java.util.List;

public class ConditionPopAdapter extends BaseQuickAdapter<ConditionPop,BaseViewHolder>{
    public ConditionPopAdapter(@Nullable List<ConditionPop> data) {
        super(R.layout.condition_pop_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ConditionPop item) {
        helper.setText(R.id.tv_condition_pop,item.getName());
    }
}
