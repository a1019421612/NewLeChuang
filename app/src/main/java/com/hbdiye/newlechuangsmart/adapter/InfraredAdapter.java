package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.InfraredBean;

import java.util.List;

public class InfraredAdapter extends BaseQuickAdapter<InfraredBean.Remote_list,BaseViewHolder>{

    private boolean isShow=true;
    public InfraredAdapter(@Nullable List<InfraredBean.Remote_list> data) {
        super(R.layout.infrared_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InfraredBean.Remote_list item) {
        if (isShow){
            helper.setGone(R.id.ll_scene_item_del,false);
            helper.setGone(R.id.ll_scene_item_edt,false);
        }else {
            helper.setGone(R.id.ll_scene_item_del,true);
            helper.setGone(R.id.ll_scene_item_edt,true);
        }
//        Glide.with(mContext).load(ContentConfig.sceneIcon(item.icon)).into((ImageView) helper.getView(R.id.iv_scene_icon));
        helper.setText(R.id.tv_scene_name,item.name);
        helper.addOnClickListener(R.id.ll_scene_item_del);
        helper.addOnClickListener(R.id.ll_scene_item_edt);
    }
    public void sceneStatusChange(boolean status){
        isShow=status;
        notifyDataSetChanged();
    }
}
