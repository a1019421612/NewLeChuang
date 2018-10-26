package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;

import java.util.List;

public class MoreSceneAdapter extends BaseItemDraggableAdapter<String,BaseViewHolder> {
    public MoreSceneAdapter( @Nullable List<String> data) {
        super(R.layout.more_scene_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_more_scene,"第"+item+"项");
    }
}
