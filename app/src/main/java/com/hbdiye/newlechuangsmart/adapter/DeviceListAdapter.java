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

public class DeviceListAdapter extends BaseSectionQuickAdapter<SecneSectionBean, BaseViewHolder> {
    public DeviceListAdapter(int layoutResId, int sectionHeadResId, List<SecneSectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SecneSectionBean item) {
        helper.setText(R.id.tv_addscene_item, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, SecneSectionBean item) {
        DeviceList content = (DeviceList) item.t;
        String productId = content.productId;
//        ll_device_kg  ll_device_warning  ll_device_jcq（温湿度） ll_device_cgq (水浸) ll_device_cl(窗帘)
        if (productId.contains("PRO002")) {
            //开关类

            if (productId.contains("PRO002030")){
                helper.setGone(R.id.ll_device_kg, false);
                helper.setGone(R.id.ll_device_warning, false);
                helper.setGone(R.id.ll_device_jcq, false);
                helper.setGone(R.id.ll_device_cgq, false);
                helper.setGone(R.id.ll_device_cl, false);
            }else {
                helper.setGone(R.id.ll_device_kg, true);
                helper.setGone(R.id.ll_device_warning, false);
                helper.setGone(R.id.ll_device_jcq, false);
                helper.setGone(R.id.ll_device_cgq, false);
                helper.setGone(R.id.ll_device_cl, false);
                List<DeviceList.DevAttList> devAttList = content.devAttList;
                if (devAttList.size() == 1) {
                    //一路开关
                    helper.setGone(R.id.iv_device_left, true);
                    helper.setGone(R.id.iv_device_middle, false);
                    helper.setGone(R.id.iv_device_right, false);
                    int value = devAttList.get(0).value;
                    if (value == 0) {
                        Glide.with(mContext).load(R.drawable.guan).into((ImageView) helper.getView(R.id.iv_device_left));
                    } else {
                        Glide.with(mContext).load(R.drawable.kai).into((ImageView) helper.getView(R.id.iv_device_left));
                    }
                } else if (devAttList.size() == 2) {
                    //二路开关
                    helper.setGone(R.id.iv_device_left, true);
                    helper.setGone(R.id.iv_device_middle, true);
                    helper.setGone(R.id.iv_device_right, false);
                    for (int i = 0; i < devAttList.size(); i++) {
                        int port = devAttList.get(i).port;
                        if (port == 1) {
                            int value = devAttList.get(i).value;
                            if (value == 0) {
                                Glide.with(mContext).load(R.drawable.guan).into((ImageView) helper.getView(R.id.iv_device_left));
                            } else {
                                Glide.with(mContext).load(R.drawable.kai).into((ImageView) helper.getView(R.id.iv_device_left));
                            }
                        } else if (port == 2) {
                            int value = devAttList.get(i).value;
                            if (value == 0) {
                                Glide.with(mContext).load(R.drawable.guan).into((ImageView) helper.getView(R.id.iv_device_middle));
                            } else {
                                Glide.with(mContext).load(R.drawable.kai).into((ImageView) helper.getView(R.id.iv_device_middle));
                            }
                        }
                    }
                } else if (devAttList.size() == 3) {
                    //三路开关
                    helper.setGone(R.id.iv_device_left, true);
                    helper.setGone(R.id.iv_device_middle, true);
                    helper.setGone(R.id.iv_device_right, true);
                    for (int i = 0; i < devAttList.size(); i++) {
                        int port = devAttList.get(i).port;
                        if (port == 1) {
                            int value = devAttList.get(i).value;
                            if (value == 0) {
                                Glide.with(mContext).load(R.drawable.guan).into((ImageView) helper.getView(R.id.iv_device_left));
                            } else {
                                Glide.with(mContext).load(R.drawable.kai).into((ImageView) helper.getView(R.id.iv_device_left));
                            }
                        } else if (port == 2) {
                            int value = devAttList.get(i).value;
                            if (value == 0) {
                                Glide.with(mContext).load(R.drawable.guan).into((ImageView) helper.getView(R.id.iv_device_middle));
                            } else {
                                Glide.with(mContext).load(R.drawable.kai).into((ImageView) helper.getView(R.id.iv_device_middle));
                            }
                        } else if (port == 3) {
                            int value = devAttList.get(i).value;
                            if (value == 0) {
                                Glide.with(mContext).load(R.drawable.guan).into((ImageView) helper.getView(R.id.iv_device_right));
                            } else {
                                Glide.with(mContext).load(R.drawable.kai).into((ImageView) helper.getView(R.id.iv_device_right));
                            }
                        }
                    }
                }
            }


        } else if (productId.contains("PRO003")) {
//            安防设备 水浸传感器等
            if (productId.equals("PRO003002001")) {
//                报警器
                helper.setGone(R.id.ll_device_warning, true);
                helper.setGone(R.id.ll_device_cgq, false);
                helper.setGone(R.id.ll_device_kg, false);
                helper.setGone(R.id.ll_device_jcq, false);
                helper.setGone(R.id.ll_device_cl, false);
            } else if (productId.equals("PRO003004001")) {
                helper.setGone(R.id.ll_device_kg, true);
                helper.setGone(R.id.ll_device_jcq, false);
                helper.setGone(R.id.ll_device_warning, false);
                helper.setGone(R.id.ll_device_cgq, false);
                helper.setGone(R.id.ll_device_cl, false);

                helper.setGone(R.id.iv_device_left, true);
                helper.setGone(R.id.iv_device_middle, false);
                helper.setGone(R.id.iv_device_right, false);
                List<DeviceList.DevAttList> devAttList = content.devAttList;
                int value = devAttList.get(0).value;
                if (value==0){
                    Glide.with(mContext).load(R.drawable.guan).into((ImageView) helper.getView(R.id.iv_device_left));
                }else {
                    Glide.with(mContext).load(R.drawable.kai).into((ImageView) helper.getView(R.id.iv_device_left));
                }

            } else {
                //一氧化碳 水浸
                int value = content.devAttList.get(0).value;
                switch (value) {
                    case 0:
                        helper.setText(R.id.tv_device_statu, "正常");
                        break;
                    case 1:
                        helper.setText(R.id.tv_device_statu, "有感应");
                        break;
                    case 4:
                        helper.setText(R.id.tv_device_statu, "被拆除");
                        break;
                    case 8:
                        helper.setText(R.id.tv_device_statu, "电池欠压");
                        break;
                    case 512:
                        helper.setText(R.id.tv_device_statu, "电池故障");
                        break;
                }
                helper.setGone(R.id.ll_device_cgq, true);
                helper.setGone(R.id.ll_device_warning, false);
                helper.setGone(R.id.ll_device_kg, false);
                helper.setGone(R.id.ll_device_jcq, false);
                helper.setGone(R.id.ll_device_cl, false);

            }

//            helper.setGone(R.id.ll_device_kg,false);
//            helper.setGone(R.id.ll_device_jcq,false);
//            helper.setGone(R.id.ll_device_cl,false);

        } else if (productId.contains("PRO004")) {
//            环境监测 温湿度
            helper.setGone(R.id.ll_device_jcq, true);
            helper.setGone(R.id.ll_device_kg, false);
            helper.setGone(R.id.ll_device_warning, false);
            helper.setGone(R.id.ll_device_cgq, false);
            helper.setGone(R.id.ll_device_cl, false);
            List<DeviceList.DevAttList> devAttList = content.devAttList;
            for (int i = 0; i < devAttList.size(); i++) {
                int cluNo = devAttList.get(i).cluNo;
                int value = devAttList.get(i).value;
                if (cluNo == 1026) {
                    //温度
                    helper.setText(R.id.tv_device_wd, value / 100 + "℃");
                } else if (cluNo == 1029) {
                    //湿度
                    helper.setText(R.id.tv_device_sd, value / 100 + "%");
                }
            }
        } else if (productId.contains("PRO005")) {
//            窗帘设备
            helper.setGone(R.id.ll_device_cl, true);
            helper.setGone(R.id.ll_device_kg, false);
            helper.setGone(R.id.ll_device_warning, false);
            helper.setGone(R.id.ll_device_cgq, false);
            helper.setGone(R.id.ll_device_jcq, false);
        } else if (productId.contains("PRO006")) {
            //其他 场景
            helper.setGone(R.id.ll_device_cl, false);
            helper.setGone(R.id.ll_device_kg, false);
            helper.setGone(R.id.ll_device_warning, false);
            helper.setGone(R.id.ll_device_cgq, false);
            helper.setGone(R.id.ll_device_jcq, false);
        }else if (productId.contains("PRO009")) {
            //其他 场景
            helper.setGone(R.id.ll_device_cl, false);
            helper.setGone(R.id.ll_device_kg, false);
            helper.setGone(R.id.ll_device_warning, false);
            helper.setGone(R.id.ll_device_cgq, false);
            helper.setGone(R.id.ll_device_jcq, false);
        }else if (productId.contains("PRO001")){
            //红外发射器
            helper.setGone(R.id.ll_device_cl, false);
            helper.setGone(R.id.ll_device_kg, false);
            helper.setGone(R.id.ll_device_warning, false);
            helper.setGone(R.id.ll_device_cgq, false);
            helper.setGone(R.id.ll_device_jcq, false);
        }else{
            helper.setGone(R.id.ll_device_cl, false);
            helper.setGone(R.id.ll_device_kg, false);
            helper.setGone(R.id.ll_device_warning, false);
            helper.setGone(R.id.ll_device_cgq, false);
            helper.setGone(R.id.ll_device_jcq, false);
        }
        helper.setText(R.id.tv_scene_device_name, content.name);
        Glide.with(mContext).load(ClassyIconByProId.deviceIcon(content.productId)).into((ImageView) helper.getView(R.id.iv_device_icon));

        helper.addOnClickListener(R.id.iv_device_left);
        helper.addOnClickListener(R.id.iv_device_middle);
        helper.addOnClickListener(R.id.iv_device_right);

        helper.addOnClickListener(R.id.iv_device_warn_s);
        helper.addOnClickListener(R.id.iv_device_warn_g);
        helper.addOnClickListener(R.id.iv_device_warn_stop);

        helper.addOnClickListener(R.id.iv_device_cl_close);
        helper.addOnClickListener(R.id.iv_device_cl_open);
        helper.addOnClickListener(R.id.iv_device_cl_stop);
    }
}
