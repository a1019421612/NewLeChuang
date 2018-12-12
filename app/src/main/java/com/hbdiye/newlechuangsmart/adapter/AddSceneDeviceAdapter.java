package com.hbdiye.newlechuangsmart.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.DeviceList;
import com.hbdiye.newlechuangsmart.bean.SecneSectionBean;
import com.hbdiye.newlechuangsmart.util.ClassyIconByProId;

import java.util.List;

public class AddSceneDeviceAdapter extends BaseSectionQuickAdapter<SecneSectionBean,BaseViewHolder>{
    public AddSceneDeviceAdapter(int layoutResId, int sectionHeadResId, List<SecneSectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SecneSectionBean item) {
        helper.setText(R.id.tv_addscene_item,item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, SecneSectionBean item) {
        DeviceList content=(DeviceList) item.t;
        String productId = content.productId;
        Glide.with(mContext).load(ClassyIconByProId.deviceIcon(productId)).into((ImageView)helper.getView(R.id.iv_scene_device));
        helper.setText(R.id.tv_scene_device_name,content.name);
    }
}
