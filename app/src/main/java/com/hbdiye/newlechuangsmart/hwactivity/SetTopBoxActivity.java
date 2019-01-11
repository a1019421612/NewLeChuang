package com.hbdiye.newlechuangsmart.hwactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.hbdiye.newlechuangsmart.activity.HwBaseActivity;
import com.hbdiye.newlechuangsmart.adapter.RemoteConTrolAdapter;
import com.hbdiye.newlechuangsmart.bean.DeviceBean;
import com.hbdiye.newlechuangsmart.bean.EditRemoteControlBean;
import com.hbdiye.newlechuangsmart.util.AppUtils;
import com.hbdiye.newlechuangsmart.util.SharedpreUtils;
import com.hbdiye.newlechuangsmart.view.MyGridView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.hbdiye.newlechuangsmart.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;


public class SetTopBoxActivity extends HwBaseActivity {
    //
//
    @BindView(R.id.gridview)
    MyGridView gridview;

    private int icon[] = {R.mipmap.zhuye, R.mipmap.caidan, R.mipmap.shuzijian,
            R.mipmap.zidonghuantai, R.mipmap.pindaoliebiao, R.mipmap.jingyin};
    //图标下的文字
    private String name[] = {"主页", "菜单", "数字键", "自动换台", "频道列表", "静音"};
    private List<EditRemoteControlBean.Data.KeyList> keyList;
    private EditRemoteControlBean.Data hongWaiXian;
    private String hwbId;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_top_box);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    //
    private void initView() {
        setTitle("机顶盒");
        RemoteConTrolAdapter remoteConTrolAdapter = new RemoteConTrolAdapter(icon, name);
        gridview.setAdapter(remoteConTrolAdapter);
        getRightView().setVisibility(View.VISIBLE);
        getRightView().setOnClickListener(listener);
        gridview.setOnItemClickListener(gvListener);
    }

    //
    private void initData() {
        DeviceBean deviceBean = (DeviceBean) getIntent().getSerializableExtra("hongWaiXian");
        if (deviceBean == null) {
            return;
        }
        hongWaiXian = deviceBean.data;
        hwbId = getIntent().getStringExtra("hwbId");
        keyList = hongWaiXian.keyList;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_right:
                    Intent intent = new Intent(getApplicationContext(), SetUpActivity.class);
                    intent.putExtra("rid", hongWaiXian.rid);
                    intent.putExtra("hwbId", hwbId);
                    startActivityForResult(intent, 1001);
                    break;
            }
        }
    };
    AdapterView.OnItemClickListener gvListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AppUtils.vibrate(getApplicationContext(), (long) 200);
            switch (position) {
//              {"主页", "菜单", "数字键", "自动换台", "频道列表", "静音"};
                case 0:
                    setHongWai(getPulse("homepage"));
                    break;
                case 1:
                    setHongWai(getPulse("menu"));
                    break;
                case 2:
//                    setHongWai(getPulse("channel_up"));
                    break;
                case 3:
//                    setHongWai(getPulse("channel_up"));
                    break;
                case 4:
//                    setHongWai(getPulse("channel_up"));
                    break;
                case 5:
                    setHongWai(getPulse("mute"));
                    break;

            }
        }
    };

    @OnClick({R.id.iv_program_add, R.id.iv_program_reduce, R.id.iv_xiangshang, R.id.iv_xiangxia,
            R.id.iv_xiangzuo, R.id.iv_xiangyou, R.id.tv_ok, R.id.iv_voice_add, R.id.iv_voice_reduce,
            R.id.iv_kaiguan_jidinghe, R.id.iv_kaiguan_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_program_add:
                setHongWai(getPulse("channel_up"));
                break;
            case R.id.iv_program_reduce:
                setHongWai(getPulse("channel_down"));
                break;
            case R.id.iv_xiangshang:
                setHongWai(getPulse("navigate_up"));
                break;
            case R.id.iv_xiangxia:
                setHongWai(getPulse("navigate_down"));
                break;
            case R.id.iv_xiangzuo:
                setHongWai(getPulse("navigate_left"));
                break;
            case R.id.iv_xiangyou:
                setHongWai(getPulse("navigate_right"));
                break;
            case R.id.tv_ok:
                setHongWai(getPulse("ok"));
                break;
            case R.id.iv_voice_add:
                setHongWai(getPulse("volume_up"));
                break;
            case R.id.iv_voice_reduce:
                setHongWai(getPulse("volume_down"));
                break;
            case R.id.iv_kaiguan_jidinghe:
                setHongWai(getPulse("power"));
                break;
            case R.id.iv_kaiguan_tv:
                setHongWai(getPulse("power"));
                break;
        }
    }

    public String getPulse(String name) {
        String pulse = "";
        if (keyList == null || keyList.size() == 0) {
            return pulse;
        }
        for (int i = 0; i < keyList.size(); i++) {
            if (name.equals(keyList.get(i).fkey)) {
                pulse = keyList.get(i).pulse;
                break;
            }
        }
        return pulse;
    }

    //
    private void setHongWai(String pulse) {
        OkHttpUtils.post().url("http://39.104.119.0:8808/SmartHome/infrared/sentInfraredCode")
                .addParams("token", SharedpreUtils.getString(getApplicationContext(), "token", ""))
                .addParams("deviceId", "HW2")
                .addParams("pulse", pulse)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("成功", response);
            }
        });
    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1002) {
            setResult(1002);
            finish();
        }
    }
//
}
