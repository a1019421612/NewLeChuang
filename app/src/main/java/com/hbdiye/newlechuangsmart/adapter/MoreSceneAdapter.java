package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.HomeSceneBean;
import com.hbdiye.newlechuangsmart.util.IconByName;

import java.util.List;

public class MoreSceneAdapter extends BaseItemDraggableAdapter<HomeSceneBean.SceneList,BaseViewHolder> {
    public MoreSceneAdapter( @Nullable List<HomeSceneBean.SceneList> data) {
        super(R.layout.more_scene_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeSceneBean.SceneList item) {
        Glide.with(mContext).load(IconByName.drawableByName(item.icon)).into((ImageView)helper.getView(R.id.iv_more_scene));
        helper.setText(R.id.tv_more_scene,item.name);
    }
}
