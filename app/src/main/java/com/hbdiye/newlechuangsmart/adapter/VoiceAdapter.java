package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.VoiceListBean;

import java.util.List;

public class VoiceAdapter extends BaseQuickAdapter<VoiceListBean,BaseViewHolder> {
    public VoiceAdapter(@Nullable List<VoiceListBean> data) {
        super(R.layout.item_voice, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VoiceListBean item) {
        if (item.isleft){
            helper.setGone(R.id.ll_voice_right,false);
            helper.setGone(R.id.ll_voice_left,true);
            helper.setText(R.id.tv_voice_left,item.msg);
        }else {
            helper.setGone(R.id.ll_voice_right,true);
            helper.setGone(R.id.ll_voice_left,false);
            helper.setText(R.id.tv_voice_right,item.msg);
        }
    }
}
