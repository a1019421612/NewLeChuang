package com.hbdiye.newlechuangsmart.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.DeviceList;
import com.hbdiye.newlechuangsmart.bean.SecneSectionBean;

import java.util.List;

public class SceneActionAdapter extends BaseSectionQuickAdapter<SecneSectionBean,BaseViewHolder>{
    public SceneActionAdapter(int layoutResId, int sectionHeadResId, List<SecneSectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SecneSectionBean item) {
        helper.setText(R.id.tv_addscene_item,item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, SecneSectionBean item) {
        DeviceList content=(DeviceList) item.t;
//        List<DeviceList.DevAttList> devAttList = content.devAttList;
//        for (int i = 0; i < devAttList.size(); i++) {
//            devAttList.get(i).value
//        }
        helper.setText(R.id.tv_scene_action,content.devAttList.get(0).value+"");
        helper.setText(R.id.tv_scene_device_name,content.name);
    }
}
