package com.hbdiye.newlechuangsmart.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.DeviceList;
import com.hbdiye.newlechuangsmart.bean.SecneSectionBean;

import java.util.List;

public class SceneDeviceAdapter extends BaseSectionQuickAdapter<SecneSectionBean,BaseViewHolder>{
    public SceneDeviceAdapter(int layoutResId, int sectionHeadResId, List<SecneSectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SecneSectionBean item) {
        helper.setText(R.id.tv_scene_device_item,item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, SecneSectionBean item) {
        DeviceList content=(DeviceList) item.t;
        helper.setText(R.id.tv_scene_device_name,content.name);
        helper.addOnClickListener(R.id.iv_scene_detail_del);
    }
}
