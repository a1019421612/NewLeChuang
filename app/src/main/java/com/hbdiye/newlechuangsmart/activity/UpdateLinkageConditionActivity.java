package com.hbdiye.newlechuangsmart.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.SceneActionAdapter;
import com.hbdiye.newlechuangsmart.bean.DeviceList;
import com.hbdiye.newlechuangsmart.bean.DeviceListSceneBean;
import com.hbdiye.newlechuangsmart.bean.SecneSectionBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.UPDATELINKAGECONDITION;

/**
 * 添加条件
 */
public class UpdateLinkageConditionActivity extends BaseActivity {

    @BindView(R.id.rv_scene_action_device)
    RecyclerView rvSceneActionDevice;
    private String token;
    private List<SecneSectionBean> mList = new ArrayList<>();
    private SceneActionAdapter adapter;
    private String linkageId;
    private String condition_id;

    @Override
    protected void initData() {
        token = (String) SPUtils.get(this,"token","");
        linkageId = getIntent().getStringExtra("linkageId");
        condition_id=getIntent().getStringExtra("condition_id");
        includeAttribute();
    }

    private void includeAttribute() {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.SCENEEDITACTION))
                .addParams("token",token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        DeviceListSceneBean deviceListSceneBean = new Gson().fromJson(response, DeviceListSceneBean.class);
                        String errcode = deviceListSceneBean.errcode;
                        List<DeviceListSceneBean.RoomList> roomList = deviceListSceneBean.roomList;
                        if (errcode.equals("0")){
                            for (int i = 0; i < roomList.size(); i++) {
                                DeviceListSceneBean.RoomList roomList1 = roomList.get(i);
                                SecneSectionBean secneSectionBean = new SecneSectionBean(true, roomList1.name);
                                secneSectionBean.setIshead(true);
                                secneSectionBean.setTitle(roomList1.name);
                                mList.add(secneSectionBean);
                                for (int j = 0; j < roomList1.deviceList.size(); j++) {
                                    DeviceList deviceList = roomList1.deviceList.get(j);
                                    mList.add(new SecneSectionBean(deviceList));
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    protected String getTitleName() {
        return "选择条件";
    }

    @Override
    protected void initView() {
        rvSceneActionDevice.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new SceneActionAdapter(R.layout.action_scene_device_item, R.layout.add_scene_device_header, mList);
        rvSceneActionDevice.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SecneSectionBean secneSectionBean = mList.get(position);
                boolean ishead = secneSectionBean.isIshead();
                if (!ishead){
                    String name = secneSectionBean.t.name;
                    DeviceList dev_list = secneSectionBean.t;
                    String device_id = dev_list.id;
                    DeviceList.DevAttList devAttList = dev_list.devAttList.get(0);
                    int type = devAttList.type;
                    String devAttId = devAttList.id;
                    if (type==2){
                        //检测器（温湿度）
//                        createJianCeQi(device_id,devAttId);
                        updateCondition(device_id,devAttId);
                    }else {
//                        createJianCeQi(device_id,devAttId);
                        updateCondition(device_id,devAttId);
                    }

                }
            }
        });
    }

    /**
     * 添加检测器
     * @return
     */
    private void createJianCeQi(String deviceId,String devAttId){
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.CREATECONDITION))
                .addParams("token",token)
                .addParams("linkageId",linkageId)
                .addParams("deviceId",deviceId)
                .addParams("devAttId",devAttId)
                .addParams("condition","3")
                .addParams("value","1")
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
                            String s = EcodeValue.resultEcode(errcode);
                            SmartToast.show(s);
                            if (errcode.equals("0")){
                                setResult(101,new Intent());
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 更新条件
     * @return
     */
    private void updateCondition(String deviceId,String devAttId){
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(UPDATELINKAGECONDITION))
                .addParams("token",token)
                .addParams("linkageId",linkageId)
                .addParams("deviceId",deviceId)
                .addParams("devAttId",devAttId)
                .addParams("condition","3")
                .addParams("value","1")
                .addParams("id",condition_id)
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
                            String s = EcodeValue.resultEcode(errcode);
                            SmartToast.show(s);
                            if (errcode.equals("0")){
                                setResult(101,new Intent());
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_edit_action;
    }
}
