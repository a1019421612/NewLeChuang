package com.hbdiye.newlechuangsmart.devicelist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.RootActivity;
import com.videogo.widget.TitleBar;


//import com.videogo.main.CustomApplication;

/**
 * 一键配置准备界面
 * 
 * @author chengjuntao
 * @data 2014-4-9
 */
public class AutoWifiResetActivity extends RootActivity implements OnClickListener {

    private View btnNext;
    private TextView topTip;
    private ImageView iv_back;
    private TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        ((CustomApplication) getApplication()).addSingleActivity(AutoWifiResetActivity.class.getName(), this);
        // 页面统计
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_wifi_reset);
        initTitleBar();
        initUI();
    }

    /**
     * 初始化标题栏
     */
    private void initTitleBar() {
        iv_back = findViewById(R.id.iv_device_back);
        tv_title = findViewById(R.id.tv_device_title);
        tv_title.setText(R.string.device_reset_title);
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 这里对方法做描述
     * 
     * @see
     * @since V1.0
     */
    private void initUI() {
        topTip = (TextView) findViewById(R.id.topTip);
        btnNext = findViewById(R.id.btnNext);
        topTip.setText(R.string.device_reset_tip);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btnNext:
                intent = new Intent(this, AutoWifiNetConfigActivity.class);
                intent.putExtras(getIntent());
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
