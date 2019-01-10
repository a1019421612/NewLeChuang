package com.hbdiye.newlechuangsmart.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.ConverterListBean;
import com.hbdiye.newlechuangsmart.bean.DeviceBean;
import com.hbdiye.newlechuangsmart.bean.DeviceListBean;
import com.hbdiye.newlechuangsmart.hwactivity.ConverterListActivity;

import java.util.List;

public class HwDeviceListAdapter extends BaseQuickAdapter<ConverterListBean.InfraredEquipments, BaseViewHolder> {

    private final ConverterListActivity converterListActivity;
    private GvDeviceListAdapter gvDeviceListAdapter;

    public HwDeviceListAdapter(@Nullable List<ConverterListBean.InfraredEquipments> data, ConverterListActivity converterListActivity) {
        super(R.layout.item_device_list, data);
        this.converterListActivity =converterListActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ConverterListBean.InfraredEquipments item) {
        helper.setText(R.id.tv_name, item.equipmentName);
        helper.addOnClickListener(R.id.tv_bianji);
        GridView gridView = helper.getView(R.id.gridview);
        final DeviceListBean deviceListBean = item.deviceListBean;
        gvDeviceListAdapter = new GvDeviceListAdapter(deviceListBean.device);
        gridView.setAdapter(gvDeviceListAdapter);

//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (deviceListBean.device.size() == position) {
//                    Intent intent = new Intent(mContext, SelectDeviceActivity.class);
//                    intent.putExtra("hwbDeviceId", item.id + "");
//                    mContext.startActivity(intent);
//                } else {
//                    DeviceBean device = deviceListBean.device.get(position);
//                    String deviceId = device.deviceId;
//                    switch (deviceId) {
//                        case "1":
//                            startActivity(SetTopBoxActivity.class,item.id, device);
//                            break;
//                        case "2":
//                            startActivity(TVRemoteControlActivity.class,item.id, device);
//                            break;
//                        case "3":
//                            startActivity(BoxActivity.class,item.id, device);
//                            break;
//                        case "4":
//                            startActivity(DVDActivity.class,item.id, device);
//                            break;
//                        case "5":
//                            startActivity(AirConditionerActivity.class,item.id, device);
//                            break;
//                        case "6":
//                            startActivity(ProjectorActivity.class,item.id, device);
//                            break;
//                        case "7":
//                            startActivity(PublicRemoteControlActivity.class,item.id, device);
//                            break;
//                        case "8":
//                            startActivity(FanRemoteControlActivity.class, item.id,device);
//                            break;
//                        case "9":
//                            startActivity(SingleReflexActivity.class, item.id,device);
//                            break;
//                        case "10":
//                            startActivity(BulbRemoteControlActivity.class,item.id, device);
//                            break;
//                        case "11":
//                            startActivity(AirCleanerActivity.class,item.id, device);
//                            break;
//                        case "12":
//                            startActivity(HeaterActivity.class,item.id, device);
//                            break;
//                    }
//                }
//            }
//        });
    }

    private void startActivity(Class<?> clazz, int hwbId, DeviceBean data ){
        Intent intent = new Intent(mContext, clazz);
        intent.putExtra("hongWaiXian", data);
        intent.putExtra("hwbId", hwbId+"");
        converterListActivity.startActivityForResult(intent,1001);
    }
}
