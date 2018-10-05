package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.FamilyMemberBean;

import java.util.List;

public class FamilyMemberAdapter extends BaseQuickAdapter<FamilyMemberBean,BaseViewHolder> {
    public FamilyMemberAdapter(@Nullable List<FamilyMemberBean> data) {
        super(R.layout.family_member_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FamilyMemberBean item) {

    }
}
