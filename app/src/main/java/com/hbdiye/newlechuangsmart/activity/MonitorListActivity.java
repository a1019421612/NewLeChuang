package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.MonitorActionAdapter;
import com.hbdiye.newlechuangsmart.adapter.SceneActionAdapter;
import com.hbdiye.newlechuangsmart.bean.DeviceList;
import com.hbdiye.newlechuangsmart.bean.MonitorBean;
import com.hbdiye.newlechuangsmart.bean.MonitorSectionBean;
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
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.MONITORLIST;
import static com.hbdiye.newlechuangsmart.global.InterfaceManager.UPDATEINDEXMONITOR;

public class MonitorListActivity extends BaseActivity {

    @BindView(R.id.rv_monitor)
    RecyclerView rvMonitor;
    private MonitorActionAdapter adapter;
    private List<MonitorSectionBean> mList = new ArrayList<>();
    private String token;
    private String cur_pos;
    @Override
    protected void initData() {
        token = (String) SPUtils.get(this,"token","");
        cur_pos=getIntent().getStringExtra("cur_pos");
        monitorList();
    }

    private void monitorList() {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(MONITORLIST))
                .addParams("token",token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MonitorBean monitorBean = new Gson().fromJson(response, MonitorBean.class);
                        String errcode = monitorBean.errcode;
                        if (errcode.equals("0")){
                            List<MonitorBean.RoomList> roomList = monitorBean.roomList;
                            for (int i = 0; i < roomList.size(); i++) {
                                MonitorBean.RoomList roomList1 = roomList.get(i);
                                String name = roomList1.name;
                                MonitorSectionBean monitorSectionBean=new MonitorSectionBean(true,name);
                                monitorSectionBean.setIshead(true);
                                monitorSectionBean.setTitle(name);
                                mList.add(monitorSectionBean);
                                for (int j = 0; j < roomList1.devAttList.size(); j++) {
                                    if (!roomList1.devAttList.get(j).name.equals("电压")&&!roomList1.devAttList.get(j).name.equals("电量")){
                                        MonitorBean.RoomList.DevAttList devAttList = roomList1.devAttList.get(j);
                                        mList.add(new MonitorSectionBean(devAttList));
                                    }
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    protected String getTitleName() {
        return "更换显示";
    }

    @Override
    protected void initView() {
        rvMonitor.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new MonitorActionAdapter(R.layout.action_monitor_device_item, R.layout.add_scene_device_header, mList);
        rvMonitor.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MonitorSectionBean monitorSectionBean = mList.get(position);
                boolean ishead = monitorSectionBean.isIshead();
                if (!ishead){
                    String devAttId = monitorSectionBean.t.id;
                    updateIndex(devAttId);
                }
            }
        });
    }

    private void updateIndex(String devAttId) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(UPDATEINDEXMONITOR))
                .addParams("token",token)
                .addParams("position",cur_pos)
                .addParams("devAttId",devAttId)
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
        return R.layout.activity_monitor_list;
    }
}
