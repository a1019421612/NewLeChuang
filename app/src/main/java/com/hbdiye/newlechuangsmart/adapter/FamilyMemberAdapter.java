package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.FamilyMemberBean;

import java.util.List;

public class FamilyMemberAdapter extends BaseQuickAdapter<FamilyMemberBean.UserList,BaseViewHolder> {
    boolean isAdmin;
    public FamilyMemberAdapter(@Nullable List<FamilyMemberBean.UserList> data,boolean isAdmin) {
        super(R.layout.family_member_item, data);
        this.isAdmin=isAdmin;
    }

    @Override
    protected void convert(BaseViewHolder helper, FamilyMemberBean.UserList item) {
        int position = helper.getAdapterPosition();

        if (isAdmin){
            helper.setGone(R.id.iv_member_icon,true);
        }else {
            helper.setGone(R.id.iv_member_icon,false);
        }
        if (position==0){
            helper.setGone(R.id.iv_member_icon,true);
            Glide.with(mContext).load(R.drawable.huzhu).into((ImageView)helper.getView(R.id.iv_member_icon));
        }
        helper.setText(R.id.tv_member_name,item.name);
        helper.setText(R.id.tv_member_phone,item.phone);
        helper.addOnClickListener(R.id.iv_member_icon);
    }
}
