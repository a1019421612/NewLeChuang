package com.hbdiye.newlechuangsmart.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.GateWayBean;
import com.hbdiye.newlechuangsmart.bean.GatewaySectionBean;
import com.hbdiye.newlechuangsmart.bean.SecneSectionBean;
import com.hbdiye.newlechuangsmart.util.ClassyIconByProId;

import java.util.List;

public class ZhuJiAdapter extends BaseSectionQuickAdapter<GatewaySectionBean, BaseViewHolder> {
    public ZhuJiAdapter(int layoutResId, int sectionHeadResId, List<GatewaySectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, GatewaySectionBean item) {
        helper.setText(R.id.tv_addscene_item, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, GatewaySectionBean item) {
        GateWayBean.RoomList.GatewayList content = (GateWayBean.RoomList.GatewayList) item.t;
        String productId = content.productId;
//        ll_device_kg  ll_device_warning  ll_device_jcq（温湿度） ll_device_cgq (水浸) ll_device_cl(窗帘)
            //其他 场景
            helper.setGone(R.id.ll_device_cl, false);
            helper.setGone(R.id.ll_device_kg, false);
            helper.setGone(R.id.ll_device_warning, false);
            helper.setGone(R.id.ll_device_cgq, false);
            helper.setGone(R.id.ll_device_jcq, false);
        helper.setText(R.id.tv_scene_device_name, content.name);
        Glide.with(mContext).load(ClassyIconByProId.deviceIcon(content.productId)).into((ImageView) helper.getView(R.id.iv_device_icon));

    }
}
