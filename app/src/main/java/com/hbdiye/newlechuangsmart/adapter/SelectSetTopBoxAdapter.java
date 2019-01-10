package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.LocationBean;

import java.util.List;

public class SelectSetTopBoxAdapter extends BaseQuickAdapter<LocationBean.SpList, BaseViewHolder> {


    public SelectSetTopBoxAdapter(@Nullable List<LocationBean.SpList> data) {
        super(R.layout.item_select_set_top_box, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final LocationBean.SpList item) {
        helper.setText(R.id.tv_name, item.spName);

    }


}
