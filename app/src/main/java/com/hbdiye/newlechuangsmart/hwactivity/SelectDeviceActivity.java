package com.hbdiye.newlechuangsmart.hwactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.HwBaseActivity;
import com.hbdiye.newlechuangsmart.adapter.SelectDeviceAdapter;
import com.hbdiye.newlechuangsmart.view.MyGridView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectDeviceActivity extends HwBaseActivity {

    @BindView(R.id.gridview)
    MyGridView gridview;
    private int icon[] = {R.mipmap.dianshiji, R.mipmap.kongtiao, R.mipmap.hezi,
            R.mipmap.jidinghe, R.mipmap.fengshan, R.mipmap.dengpao, R.mipmap.kongqijinghuaqi,
            R.mipmap.reshuiqi, R.mipmap.danfanxiangji, R.mipmap.dvd, R.mipmap.touyingyi,
            R.mipmap.gongfang};
    //图标下的文字
    private String name[] = {"电视机", "空调", "网络盒子", "机顶盒", "风扇", "灯泡",
            "空气净化器", "热水器", "单反相机", "DVD", "投影仪", "功放"};
    private String hwbDeviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_device);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("选择设备");
        hwbDeviceId = getIntent().getStringExtra("hwbDeviceId");
        SelectDeviceAdapter selectDeviceAdapter = new SelectDeviceAdapter(icon, name);
        gridview.setAdapter(selectDeviceAdapter);
        gridview.setOnItemClickListener(listener);
    }

    public AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getApplicationContext(), ChooseBrandActivity.class);
            intent.putExtra("hwbDeviceId", hwbDeviceId);
            intent.putExtra("deviceUrl", icon[position]);
            switch (position) {
                case 0:
                    //电视机
                    intent.putExtra("deviceId", 2 + "");
                    intent.putExtra("deviceName", "电视机");
                    break;
                case 1:
                    //空调
                    intent.putExtra("deviceId", 5 + "");
                    intent.putExtra("deviceName", "空调");
                    break;
                case 2:
                    //网络盒子
                    intent.putExtra("deviceId", 3 + "");
                    intent.putExtra("deviceName", "网络盒子");
                    break;
                case 3:
                    //机顶盒
                    intent.putExtra("deviceId", 1 + "");
                    intent.putExtra("deviceName", "机顶盒");
                    intent.setClass(getApplicationContext(),SelectSetTopBoxActivity.class);
                break;
                case 4:
                    //风扇
                    intent.putExtra("deviceId", 8 + "");
                    intent.putExtra("deviceName", "风扇");
                    break;
                case 5:
                    //灯泡
                    intent.putExtra("deviceId", 10 + "");
                    intent.putExtra("deviceName", "灯泡");
                    break;
                case 6:
                    //空气净化器
                    intent.putExtra("deviceId", 11 + "");
                    intent.putExtra("deviceName", "空气净化器");
                    break;
                case 7:
                    //热水器
                    intent.putExtra("deviceId", 12 + "");
                    intent.putExtra("deviceName", "热水器");
                    break;
                case 8:
                    //单反相机
                    intent.putExtra("deviceId", 9 + "");
                    intent.putExtra("deviceName", "单反相机");
                    break;
                case 9:
                    //DVD
                    intent.putExtra("deviceId", 4 + "");
                    intent.putExtra("deviceName", "DVD");
                    break;
                case 10:
                    //投影仪
                    intent.putExtra("deviceId", 6 + "");
                    intent.putExtra("deviceName", "投影仪");
                    break;
                case 11:
                    //功放
                    intent.putExtra("deviceId", 7 + "");
                    intent.putExtra("deviceName", "功放");
                    break;
            }
            startActivity(intent);
        }
    };

}
