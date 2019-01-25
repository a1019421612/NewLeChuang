package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.DeviceAttNameAdapter;
import com.hbdiye.newlechuangsmart.bean.KaiGuanBean;
import com.hbdiye.newlechuangsmart.bean.MyDevicesBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.hbdiye.newlechuangsmart.view.SceneDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.DEVICEDETAIL;
import static com.hbdiye.newlechuangsmart.global.InterfaceManager.UPDATEDEVICEATTNAME;
import static com.hbdiye.newlechuangsmart.global.InterfaceManager.UPDATEDEVICENAME;

public class EditDeviceNameActivity extends BaseActivity {

    @BindView(R.id.tv_et_device_name)
    TextView tvEtDeviceName;
    @BindView(R.id.rv_et_attr_name)
    RecyclerView rvEtAttrName;
    @BindView(R.id.ll_et_device)
    LinearLayout llEtDevice;
    private MyDevicesBean.DeviceList data = null;
    private String token;
    private String deviceid;
    private KaiGuanBean kaiGuanBean;
    List<KaiGuanBean.Data.DevAttList> mList = new ArrayList<>();
    private DeviceAttNameAdapter adapter;
    private SceneDialog sceneDialog,attDialog;
    private int flag=-1;

    @Override
    protected void initData() {
        token = (String) SPUtils.get(this, "token", "");
        data = (MyDevicesBean.DeviceList) getIntent().getSerializableExtra("data");
        if (data == null) {
            return;
        }
        if (data.productId.contains("PRO00200")) {
            llEtDevice.setVisibility(View.VISIBLE);
        } else {
            llEtDevice.setVisibility(View.GONE);
        }
        String name = data.name;
        deviceid = data.id;
        tvEtDeviceName.setText(name);
        deviceDetail();
    }

    private void deviceDetail() {
        OkHttpUtils.post()
                .url(InterfaceManager.getInstance().getURL(DEVICEDETAIL))
                .addParams("token", token)
                .addParams("deviceId", deviceid)
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
                            if (errcode.equals("0")) {
                                kaiGuanBean = new Gson().fromJson(response, KaiGuanBean.class);
                                List<KaiGuanBean.Data.DevAttList> devAttList = kaiGuanBean.data.devAttList;
                                if (mList.size()>0){
                                    mList.clear();
                                }
                                mList.addAll(devAttList);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected String getTitleName() {
        return "修改名称";
    }

    @Override
    protected void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvEtAttrName.setLayoutManager(manager);
        adapter = new DeviceAttNameAdapter(mList);
        rvEtAttrName.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                SmartToast.show(mList.get(position).name);
//                updateAttName();
                flag=position;
                attDialog = new SceneDialog(EditDeviceNameActivity.this, R.style.MyDialogStyle, att_clicker, "设备名称");
                attDialog.show();
            }
        });
    }

    private void updateAttName(String linkageName) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(UPDATEDEVICEATTNAME))
                .addParams("token",token)
                .addParams("devAttId",mList.get(flag).id)
                .addParams("name",linkageName)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String errcode = jsonObject.getString("errcode");
                            if (errcode.equals("0")){
                                if (attDialog!=null){
                                    attDialog.dismiss();
                                }
                                deviceDetail();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_edit_device_name;
    }

    @OnClick(R.id.tv_et_device_name)
    public void onViewClicked() {
        sceneDialog = new SceneDialog(this, R.style.MyDialogStyle, edit_name_clicker, "设备名称",data.name);
        sceneDialog.show();
    }
    private View.OnClickListener edit_name_clicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.app_cancle_tv:
                    sceneDialog.dismiss();
                    break;
                case R.id.app_sure_tv:
                    String linkageName = sceneDialog.getSceneName();
                    if (!TextUtils.isEmpty(linkageName)) {
                        updateDeviceName(linkageName);
                    }
                    break;
            }
        }
    };
    private View.OnClickListener att_clicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.app_cancle_tv:
                    attDialog.dismiss();
                    break;
                case R.id.app_sure_tv:
                    String linkageName = attDialog.getSceneName();
                    if (!TextUtils.isEmpty(linkageName)) {
                        updateAttName(linkageName);
                    }
                    break;
            }
        }
    };
    private void updateDeviceName(final String linkageName) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(UPDATEDEVICENAME))
                .addParams("token", token)
                .addParams("deviceId", deviceid)
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
                                if (sceneDialog != null) {
                                    sceneDialog.dismiss();
                                }
                                tvEtDeviceName.setText(linkageName);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
