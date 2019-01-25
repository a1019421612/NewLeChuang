package com.hbdiye.newlechuangsmart.hwactivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.HwBaseActivity;
import com.hbdiye.newlechuangsmart.adapter.StudyConverterAdapter;
import com.hbdiye.newlechuangsmart.bean.SelectStudyBean;
import com.hbdiye.newlechuangsmart.util.AppUtils;
import com.hbdiye.newlechuangsmart.util.SharedpreUtils;
import com.hbdiye.newlechuangsmart.util.ToastUtils;
import com.hbdiye.newlechuangsmart.view.MyGridView;
import com.xzcysoft.wnzhikonglibrary.WNZKConfigure;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class StudyControlActivity extends HwBaseActivity {

    @BindView(R.id.gridview)
    MyGridView gridview;
    @BindView(R.id.tv_ceshi)
    TextView tvCeshi;
    private List<SelectStudyBean.Data.KeyList> keyList;
    private String mfname = "";
    private String ceshiPulse = "";
    private String fkey;
    private String rid;
    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_control);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        deviceId=getIntent().getStringExtra("deviceId");
        setTitle("学习遥控");
        SelectStudyBean selectStudyBean =
                (SelectStudyBean) getIntent().getSerializableExtra("selectStudyBean");
        keyList = selectStudyBean.data.keyList;
        StudyConverterAdapter studyConverterAdapter = new StudyConverterAdapter(keyList);
        gridview.setAdapter(studyConverterAdapter);
        gridview.setOnItemClickListener(gvListener);
    }

    AdapterView.OnItemClickListener gvListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AppUtils.vibrate(getApplicationContext(), (long) 100);
            SelectStudyBean.Data.KeyList keyList = StudyControlActivity.this.keyList.get(position);
            mfname = keyList.fname;
            fkey = keyList.fkey;
            rid = keyList.rid + "";
            study();

        }
    };

    private void study() {

        OkHttpUtils.post().url("http://39.104.119.0:8808/SmartHome/infrared/studyInfraredCode")
                .addParams("token", SharedpreUtils.getString(getApplicationContext(), "token", ""))
                .addParams("deviceId",deviceId)
                .addParams("fkey", fkey)
                .addParams("port", "1")
                .addParams("rid", rid)
                .addParams("pack", "com.hbdiye.infrared")
                .addParams("tn", "1")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                tvCeshi.setText("测试:" + mfname);
            }
        });
    }

    private void getTestKey() {
        WNZKConfigure.getTestKey(rid, fkey, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                SelectStudyBean selectStudyBean = new Gson().fromJson(response, SelectStudyBean.class);
                if (selectStudyBean.errcode.equals("0")) {
                    List<SelectStudyBean.Data.KeyList> getkeyList = selectStudyBean.data.keyList;
                    for (int i = 0; i < getkeyList.size(); i++) {
                        String fname = getkeyList.get(i).fname;
                        if (mfname.equals(fname)) {
                            ceshiPulse = getkeyList.get(i).pulse;
                            ToastUtils.showToast(getApplicationContext(),ceshiPulse);
                            setHongWai(TextUtils.isEmpty(ceshiPulse)?"":ceshiPulse);
                            break;
                        }
                    }
                }
            }
        });
    }

    @OnClick(R.id.tv_ceshi)
    public void onViewClicked() {
        AppUtils.vibrate(getApplicationContext(), (long) 100);
        getTestKey();
    }

    private void setHongWai(String pulse) {
        OkHttpUtils.post().url("http://39.104.119.0:8808/SmartHome/infrared/sentInfraredCode")
                .addParams("token", SharedpreUtils.getString(getApplicationContext(), "token", ""))
                .addParams("deviceId", deviceId)
                .addParams("pulse", pulse)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id)  {
                Log.e("成功", response);
            }
        });
    }
}
