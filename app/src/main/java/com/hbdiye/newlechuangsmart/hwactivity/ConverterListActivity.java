package com.hbdiye.newlechuangsmart.hwactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.HwBaseActivity;
import com.hbdiye.newlechuangsmart.adapter.GvDeviceListAdapter;
import com.hbdiye.newlechuangsmart.adapter.HwDeviceListAdapter;
import com.hbdiye.newlechuangsmart.bean.ConverterListBean;
import com.hbdiye.newlechuangsmart.bean.DeviceBean;
import com.hbdiye.newlechuangsmart.bean.DeviceListBean;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.hbdiye.newlechuangsmart.util.SharedpreUtils;
import com.hbdiye.newlechuangsmart.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 万能遥控器-红外宝列表
 */
public class ConverterListActivity extends HwBaseActivity {

    @BindView(R.id.ll_bind_converter)
    LinearLayout llBindConverter;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_bianji)
    TextView tvBianji;
    @BindView(R.id.gridview)
    MyGridView gridview;
    private List<ConverterListBean.InfraredEquipments> mInfraredEquipments = new ArrayList<>();
    private HwDeviceListAdapter deviceListAdapter;
    private String hwbDeviceId;
    private String token;
    private String name;
    private GvDeviceListAdapter gvDeviceListAdapter;
    List<DeviceBean> mList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        ButterKnife.bind(this);
        token = (String) SPUtils.get(this, "token", "");
        hwbDeviceId = getIntent().getStringExtra("hwbDeviceId");
        name = getIntent().getStringExtra("name");
        initView();
        initData();
    }


    private void initView() {
        tvName.setText(name);
        setTitle("万能遥控器");
        getBackView().setVisibility(View.GONE);
        getRightView().setVisibility(View.VISIBLE);
        gvDeviceListAdapter = new GvDeviceListAdapter(mList);
        gridview.setAdapter(gvDeviceListAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList.size() == position) {
                    Intent intent = new Intent(ConverterListActivity.this, SelectDeviceActivity.class);
                    intent.putExtra("hwbDeviceId", hwbDeviceId);
                    startActivity(intent);
                }
//                else {
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
            }
        });
//        getRightView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), SelectStudyActivity.class);
//                intent.putExtra("mInfraredEquipments", (Serializable)mInfraredEquipments);
//                startActivity(intent);
//            }
//        });
//
//        deviceListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                switch (view.getId()) {
//                    case R.id.tv_bianji:
//                        Intent intent = new Intent(getApplicationContext(), EditConverterActivity.class);
//                        intent.putExtra("hwbName", mInfraredEquipments.get(position).equipmentName);
//                        intent.putExtra("hwbId", mInfraredEquipments.get(position).id);
//                        startActivityForResult(intent, 1001);
//                        break;
//                }
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1002) {
            initData();
        }
    }

    @OnClick(R.id.ll_bind_converter)
    public void onViewClicked() {
//        startActivity(new Intent(getApplicationContext(), ScanActivity.class));
    }

    private void initData() {

        String string = SharedpreUtils.getString(getApplicationContext(), "hwbDeviceId" + hwbDeviceId, "");
//        ConverterListBean.InfraredEquipments infraredEquipments1;
//        infraredEquipments1.equipmentName="红外";
        if (!TextUtils.isEmpty(string)) {
            DeviceListBean deviceListBean = new Gson().fromJson(string, DeviceListBean.class);
            List<DeviceBean> device = deviceListBean.device;
            mList.addAll(device);
        }
        gvDeviceListAdapter.notifyDataSetChanged();
//        else {
//            infraredEquipments1.deviceListBean = new DeviceListBean();
//            infraredEquipments1.deviceListBean.device = new ArrayList<>();
//        }
//        mInfraredEquipments.add(infraredEquipments1);
//        deviceListAdapter.notifyDataSetChanged();
//        WNZKConfigure.findDeviceList(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                ConverterListBean converterListBean = new Gson().fromJson(response, ConverterListBean.class);
//                mInfraredEquipments.clear();
//                if (converterListBean.errcode.equals("0")) {
//                    List<ConverterListBean.InfraredEquipments> infraredEquipments = converterListBean.infraredEquipments;
//                    //循环红外列表-将本地红外遥控添加到红外列表中
//                    for (int i = 0; i < infraredEquipments.size(); i++) {
//                        ConverterListBean.InfraredEquipments infraredEquipments1 = infraredEquipments.get(i);
//                        String string = SharedpreUtils.getString(getApplicationContext(), "hwbDeviceId" + infraredEquipments1.id, "");
//                        if (!TextUtils.isEmpty(string)) {
//                            DeviceListBean deviceListBean = new Gson().fromJson(string, DeviceListBean.class);
//                            infraredEquipments1.deviceListBean = deviceListBean;
//                        } else {
//                            infraredEquipments1.deviceListBean = new DeviceListBean();
//                            infraredEquipments1.deviceListBean.device = new ArrayList<>();
//                        }
//                    }
//                    mInfraredEquipments.addAll(infraredEquipments);
//                }
//                //如果没有红外宝,跳转到引导页
////                if (mInfraredEquipments.size() <= 0) {
////                    startActivity(new Intent(getApplicationContext(), SweepCodeActivity.class));
////                    finish();
////                }
//                deviceListAdapter.notifyDataSetChanged();
//            }
//        });
    }
}
