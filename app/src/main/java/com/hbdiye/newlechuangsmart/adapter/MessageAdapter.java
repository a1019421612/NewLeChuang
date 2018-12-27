package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.MessageBean;

import java.util.List;

public class MessageAdapter extends BaseQuickAdapter<MessageBean.MessageList, BaseViewHolder> {
    public MessageAdapter(@Nullable List<MessageBean.MessageList> data) {
        super(R.layout.message_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean.MessageList item) {
        int adapterPosition = helper.getAdapterPosition();
        String subject = item.subject;
        helper.setText(R.id.tv_msg_title, subject);
        String created = item.created;
        helper.setText(R.id.tv_msg_time, created);
        String content = item.content;
        helper.setText(R.id.tv_msg_content, content);
        Glide.with(mContext).load(R.drawable.msg_putong).into((ImageView) helper.getView(R.id.iv_points));
    }
}
