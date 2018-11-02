package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.AddSceneDeviceAdapter;
import com.hbdiye.newlechuangsmart.bean.Content;
import com.hbdiye.newlechuangsmart.bean.SecneSectionBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddSceneDeviceActivity extends BaseActivity {

    @BindView(R.id.rv_add_scene_device)
    RecyclerView rvAddSceneDevice;
    private AddSceneDeviceAdapter adapter;
    private List<SecneSectionBean> mList = new ArrayList<>();

    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "选择设备";
    }

    @Override
    protected void initView() {
        rvAddSceneDevice.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new AddSceneDeviceAdapter(R.layout.add_scene_device_item, R.layout.add_scene_device_header, mList);
        rvAddSceneDevice.setAdapter(adapter);
        for (int i = 0; i < 3; i++) {
            SecneSectionBean secneSectionBean = new SecneSectionBean(true, "厨房" + i);
            secneSectionBean.setIshead(true);
            secneSectionBean.setTitle("卧室" + i);
            mList.add(secneSectionBean);
            for (int j = 0; j < i+1; j++) {
                Content content=new Content();
                content.setName("设备"+i);
                mList.add(new SecneSectionBean(content));
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_add_scene_device;
    }
}
