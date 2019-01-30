package com.hbdiye.newlechuangsmart.hwactivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.HwBaseActivity;
import com.hbdiye.newlechuangsmart.adapter.GvBindConverterAdapter;
import com.hbdiye.newlechuangsmart.bean.BaseBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.hbdiye.newlechuangsmart.util.SharedpreUtils;
import com.hbdiye.newlechuangsmart.util.ToastUtils;
import com.hbdiye.newlechuangsmart.view.MyGridView;
import com.hbdiye.newlechuangsmart.view.TipsDialog;
import com.xzcysoft.wnzhikonglibrary.WNZKConfigure;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.UPDATEDEVICENAME;

public class EditConverterActivity extends HwBaseActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.gridview)
    MyGridView gridview;
    private String[] name = {"客厅", "主卧", "次卧", "书房", "厨房", "卫生间", "阳台", "办公室", "会议室"};
    private int selectPositon = 0;
    private GvBindConverterAdapter gvBindConverterAdapter;
    private String hwbId;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_converter);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        String hwbName = getIntent().getStringExtra("hwbName");
        hwbId = getIntent().getStringExtra("hwbId");
        token = (String) SPUtils.get(this,"token","");
        etName.setText(hwbName);
    }

    private void initView() {
        setTitle("编辑红外宝");
        gvBindConverterAdapter = new GvBindConverterAdapter(name);
        gridview.setAdapter(gvBindConverterAdapter);
        gridview.setOnItemClickListener(listener);
    }

    public AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            gvBindConverterAdapter.setSelectPosition(position);
            gvBindConverterAdapter.notifyDataSetChanged();
            selectPositon = position;
            etName.setText(name[position] + "红外宝");
        }
    };

    @OnClick({R.id.bt_update, R.id.bt_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_update:
                updateHwb(etName.getText().toString());
                break;
            case R.id.bt_delete:
                showDeleteDialog();
                break;
        }
    }

    private void showDeleteDialog() {
        TipsDialog tipsDialog = new TipsDialog(EditConverterActivity.this);
        tipsDialog.setContent("是否删除红外宝?删除后,红外宝下的设备也将删除");
        tipsDialog.setOnCancelClickListener("取消", null);
        tipsDialog.setOnConfrimlickListener("确定", new TipsDialog.OnConfirmClickListener() {
            @Override
            public void onConfirmClick() {
                deleteHwb();
            }
        });
        tipsDialog.show();
    }

    private void updateHwb(final String linkageName) {
            OkHttpUtils
                    .post()
                    .url(InterfaceManager.getInstance().getURL(UPDATEDEVICENAME))
                    .addParams("token", token)
                    .addParams("deviceId", hwbId)
                    .addParams("name", linkageName)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String errcode = jsonObject.getString("errcode");
                                String s = EcodeValue.resultEcode(errcode);
                                SmartToast.show(s);
                                if (errcode.equals("0")) {
                                    setResult(1002,new Intent(EditConverterActivity.this,ConverterListActivity.class).putExtra("hwName",linkageName));
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

//        }
//        String s = etName.getText().toString();
//        if (TextUtils.isEmpty(s)) {
//            ToastUtils.showToast(getApplicationContext(), "请输入红外宝名称");
//            return;
//        }
//        WNZKConfigure.updateDeviceName(hwbId + "", s, new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
//                if (baseBean.errcode == 0) {
//                    setResult(1002);
//                    finish();
//                }
//            }
//        });
    }

    private void deleteHwb() {
        WNZKConfigure.unbindDevice(hwbId + "", new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                BaseBean baseBean = new Gson().fromJson(response, BaseBean.class);
                if (baseBean.errcode == 0) {
                    SharedpreUtils.removeString(getApplicationContext(), "hwbDeviceId" + hwbId);
                    setResult(1002);
                    finish();
                }
            }
        });
    }
}
