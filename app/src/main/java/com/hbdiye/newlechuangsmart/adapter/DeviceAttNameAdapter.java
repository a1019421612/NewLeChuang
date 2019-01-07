package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.KaiGuanBean;

import java.util.List;

public class DeviceAttNameAdapter extends BaseQuickAdapter<KaiGuanBean.Data.DevAttList,BaseViewHolder>{
    public DeviceAttNameAdapter(@Nullable List<KaiGuanBean.Data.DevAttList> data) {
        super(R.layout.device_att_name_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KaiGuanBean.Data.DevAttList item) {
        helper.setText(R.id.tv_device_att_name,item.name);
        helper.addOnClickListener(R.id.tv_device_att_name);
    }
}
