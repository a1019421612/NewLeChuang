package com.hbdiye.newlechuangsmart.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.DeviceList;
import com.hbdiye.newlechuangsmart.bean.MonitorBean;
import com.hbdiye.newlechuangsmart.bean.MonitorSectionBean;
import com.hbdiye.newlechuangsmart.bean.SecneSectionBean;

import java.text.DecimalFormat;
import java.util.List;

public class MonitorActionAdapter extends BaseSectionQuickAdapter<MonitorSectionBean,BaseViewHolder>{
    public MonitorActionAdapter(int layoutResId, int sectionHeadResId, List<MonitorSectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, MonitorSectionBean item) {
        helper.setText(R.id.tv_addscene_item,item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, MonitorSectionBean item) {
        MonitorBean.RoomList.DevAttList content=(MonitorBean.RoomList.DevAttList) item.t;
//        List<DeviceList.DevAttList> devAttList = content.devAttList;
//        for (int i = 0; i < devAttList.size(); i++) {
//            devAttList.get(i).value
//        }
        DecimalFormat df=new DecimalFormat("0.0");
        String format = df.format((float) content.value / 100);
        helper.setText(R.id.tv_scene_action,format);
        helper.setText(R.id.tv_scene_device_name,content.name);
        helper.setText(R.id.tv_scene_device_attr_name,content.deviceName);
    }
}
