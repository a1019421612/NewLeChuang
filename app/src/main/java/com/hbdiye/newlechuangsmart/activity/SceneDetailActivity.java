package com.hbdiye.newlechuangsmart.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;

public class SceneDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_base_back, iv_scene_ic;
    private LinearLayout ll_scene_icon;
    private TextView tv_scene_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_detail);
        initView();
    }

    private void initView() {
        iv_base_back = findViewById(R.id.iv_base_back);
        iv_scene_ic = findViewById(R.id.iv_scene_ic);
        ll_scene_icon = findViewById(R.id.ll_scene_icon);
        tv_scene_add = findViewById(R.id.tv_scene_add);

        iv_base_back.setOnClickListener(this);
        ll_scene_icon.setOnClickListener(this);
        tv_scene_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_base_back:
                finish();
                break;
            case R.id.ll_scene_icon:
                //选择图标
                break;
            case R.id.tv_scene_add:
                //添加设备
                startActivity(new Intent(this,AddSceneDeviceActivity.class));
                break;
        }
    }
}
