package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.ZngjBean;

import java.util.List;

public class ZngjAdapter extends BaseQuickAdapter<ZngjBean.RobotList,BaseViewHolder>{
    public ZngjAdapter( @Nullable List<ZngjBean.RobotList> data) {
        super(R.layout.zngj_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ZngjBean.RobotList item) {
        helper.setText(R.id.tv_zngj_name,item.name);
        if (item.online==0){
            helper.setText(R.id.tv_zngj_status,"离线");
        }else {
            helper.setText(R.id.tv_zngj_status,"在线");
        }
    }
}
