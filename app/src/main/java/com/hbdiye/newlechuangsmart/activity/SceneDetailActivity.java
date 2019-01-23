package com.hbdiye.newlechuangsmart.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.SingleWebSocketConnection;
import com.hbdiye.newlechuangsmart.adapter.AddSceneDeviceAdapter;
import com.hbdiye.newlechuangsmart.adapter.SceneDetailListAdapter;
import com.hbdiye.newlechuangsmart.adapter.SceneDeviceAdapter;
import com.hbdiye.newlechuangsmart.bean.DeviceList;
import com.hbdiye.newlechuangsmart.bean.SceneDetailBean;
import com.hbdiye.newlechuangsmart.bean.SecneSectionBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.IconByName;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.hbdiye.newlechuangsmart.view.AttributeDeDialog;
import com.hbdiye.newlechuangsmart.view.AttributeDialog;
import com.hbdiye.newlechuangsmart.view.LinkageAddIconPopwindow;
import com.hbdiye.newlechuangsmart.view.MyListView;
import com.hbdiye.newlechuangsmart.view.SceneDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.tavendo.autobahn.WebSocketConnection;
import okhttp3.Call;

public class SceneDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_base_back, iv_scene_ic, iv_scene_edit;
    private LinearLayout ll_scene_icon, ll_scent_root;
    private TextView tv_scene_add, tv_scene_name;
//    private RecyclerView rv_scene_detail;
//    private SceneDeviceAdapter adapter;
    private List<SecneSectionBean> mList = new ArrayList<>();
    private String sceneId = "";
    private String token;

    private ListView mlv_scene;
    private SceneDetailListAdapter mAdapter;
    private List<SceneDetailBean.SceneTaskList> list = new ArrayList<>();
    private LinkageAddIconPopwindow popwindow;
    private List<Integer> mList_icon = new ArrayList<>();
    private SceneDialog sceneDialog;
    private AttributeDeDialog dialog;
    private List<SceneDetailBean.SceneTaskList.DevActList> devActList;
    private WebSocketConnection mConnection;
    private HomeReceiver homeReceiver;
    private String dactid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_detail);
        sceneId = getIntent().getStringExtra("sceneId");
        token = (String) SPUtils.get(this, "token", "");
        initView();
        initData();
    }

    private void initData() {
        mList_icon.add(R.drawable.huijia);
        mList_icon.add(R.drawable.lijia);
        mList_icon.add(R.drawable.yeqi);
        mList_icon.add(R.drawable.xican);
        mList_icon.add(R.drawable.xiuxi);
        mList_icon.add(R.drawable.xiawucha);
        mList_icon.add(R.drawable.zuofan);
        mList_icon.add(R.drawable.xizao);
        mList_icon.add(R.drawable.shuijiao);
        mList_icon.add(R.drawable.kandianshi);
        mList_icon.add(R.drawable.diannao);
        mList_icon.add(R.drawable.yinyue);
        mList_icon.add(R.drawable.wucan);
        mList_icon.add(R.drawable.youxi);
        mList_icon.add(R.drawable.huike);
        mList_icon.add(R.drawable.xiyifu);
        mList_icon.add(R.drawable.kaihui);
        mList_icon.add(R.drawable.huazhuang);
        mList_icon.add(R.drawable.dushu);
        mList_icon.add(R.drawable.qichuang);
        if (sceneId.equals("")){
            return;
        }
        sceneDetail();
    }

    private void initView() {
        mConnection = SingleWebSocketConnection.getInstance();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DCPP");
        intentFilter.addAction("DAPP");
        intentFilter.addAction("DOPP");
        homeReceiver = new HomeReceiver();
        registerReceiver(homeReceiver, intentFilter);
        iv_base_back = findViewById(R.id.iv_base_back);
        iv_scene_ic = findViewById(R.id.iv_scene_ic);
        tv_scene_name = findViewById(R.id.tv_scene_detail_name);
        ll_scene_icon = findViewById(R.id.ll_scene_icon);
        tv_scene_add = findViewById(R.id.tv_scene_add);
//        rv_scene_detail = findViewById(R.id.rv_scene_detail);
        ll_scent_root = findViewById(R.id.ll_scene_root);
        iv_scene_edit = findViewById(R.id.iv_scene_edit);

        mlv_scene = findViewById(R.id.mlv_scene);
        mAdapter = new SceneDetailListAdapter(this, list);
        mlv_scene.setAdapter(mAdapter);

        iv_base_back.setOnClickListener(this);
        ll_scene_icon.setOnClickListener(this);
        tv_scene_add.setOnClickListener(this);
        iv_scene_edit.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        rv_scene_detail.setLayoutManager(manager);
//        adapter = new SceneDeviceAdapter(R.layout.scene_device_item, R.layout.scene_device_header, mList);
//        rv_scene_detail.setAdapter(adapter);
//        for (int i = 0; i < 3; i++) {
//
//            SecneSectionBean secneSectionBean = new SecneSectionBean(true, "厨房开关" + i);
//            secneSectionBean.setIshead(true);
//            secneSectionBean.setTitle("厨房开关" + i);
//            mList.add(secneSectionBean);
//            for (int j = 0; j < i + 1; j++) {
//                Content content = new Content();
//                content.setName("左开" + i);
//                mList.add(new SecneSectionBean(content));
//            }
//        }
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(new SceneDetailListAdapter.GridOnItemClickListener() {
            @Override
            public void OnGridItemClickListener(int pos) {
//                SmartToast.show(pos+"编辑动作");
                devActList = list.get(pos).devActList;
                dialog = new AttributeDeDialog(SceneDetailActivity.this, R.style.MyDialogStyle, clickListener_dialog, devActList,gv_click);
                dialog.show();
            }
        });
        mAdapter.setImageViewDelListener(new SceneDetailListAdapter.ImageViewDelListener() {
            @Override
            public void OnImageViewDelListener(String stid) {
//                "{\"pn\":\"DOPP\",\"pt\":\"T\",\"pid\":\"%@\",\"token\":\"%@\",\"oper\":\"104\",\"stid\":\"%@\"}"
                mConnection.sendTextMessage("{\"pn\":\"DOPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"104\",\"stid\":\""+stid+"\"}");
            }
        });
//        adapter.notifyDataSetChanged();
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                SecneSectionBean secneSectionBean = mList.get(position);
//                if (!secneSectionBean.isHeader) {
//                    SmartToast.show(secneSectionBean.t.getName());
//                }
//            }
//        });
//        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                SecneSectionBean secneSectionBean = mList.get(position);
//                switch (view.getId()) {
//                    case R.id.iv_scene_detail_del:
//                        SmartToast.show("del" + position);
//                        break;
//                }
//            }
//        });
    }
    public AdapterView.OnItemClickListener gv_click=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            dialog.changeColor(position);
            SceneDetailBean.SceneTaskList.DevActList devActList1 = devActList.get(position);
            dactid = devActList1.id;
//            String deviceid = devActList.dev;
            String deviceid = devActList1.deviceId;
            mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+ dactid +"\",\"param\":\"\"}");
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_base_back:
                finish();
                break;
            case R.id.ll_scene_icon:
                //选择图标
                popwindow = new LinkageAddIconPopwindow(this, mList_icon, clickListener);
                popwindow.showPopupWindowBottom(ll_scent_root);
                break;
            case R.id.tv_scene_add:
                //添加设备
                startActivityForResult(new Intent(this, AddSceneDeviceActivity.class).putExtra("sceneId",sceneId),100);
                break;
            case R.id.iv_scene_edit:
                //修改名称
                sceneDialog=new SceneDialog(this,R.style.MyDialogStyle,mClicker,"修改场景名称");
                sceneDialog.show();
                break;
        }
    }
    private View.OnClickListener mClicker=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.app_cancle_tv:
                    sceneDialog.dismiss();
                    break;
                case R.id.app_sure_tv:
                    String sceneName = sceneDialog.getSceneName();
                    if (!TextUtils.isEmpty(sceneName)){
                        updateSceneName(sceneName);
                    }
                    break;
            }
        }
    };
    public View.OnClickListener clickListener_dialog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_attr_cancel:
                    dialog.dismiss();
                    break;
                case R.id.tv_attr_ok:
                    if (TextUtils.isEmpty(dactid)){
                        return;
                    }
//                    //"{\"pn\":\"DOPP\",\"pt\":\"T\",\"pid\":\"%@\",\"token\":\"%@\",\"oper\":\"103\",\"sceid\":\"%@\",\"dactid\":\"%@\",\"param\":\"%@\"}"
                    mConnection.sendTextMessage("{\"pn\":\"DOPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"103\",\"sceid\":\""+sceneId+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
                    dialog.dismiss();
                    break;
            }
        }
    };
    private BaseQuickAdapter.OnItemClickListener clickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            List<Integer> data = adapter.getData();
            String name = IconByName.nameByDrawable(data.get(position));
            updateSceneIcon(name);
            popwindow.dismiss();
        }
    };

    /**
     * 获取详情
     */
    private void sceneDetail() {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.SCENEDETAIL))
                .addParams("token", token)
                .addParams("sceneId", sceneId)
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
                                SceneDetailBean sceneDetailBean = new Gson().fromJson(response, SceneDetailBean.class);
                                List<SceneDetailBean.SceneTaskList> deviceList = sceneDetailBean.sceneTaskList;
                                tv_scene_name.setText(sceneDetailBean.scene.name);
                                Glide.with(SceneDetailActivity.this).load(IconByName.drawableByName(sceneDetailBean.scene.icon)).into(iv_scene_ic);
                                if (deviceList != null) {
                                    if (list.size()>0){
                                        list.clear();
                                    }
                                    list.addAll(deviceList);
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    /**
     * 修改图标
     * @param icon
     */
    private void updateSceneIcon(String icon) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.UPDATESCENE))
                .addParams("token", token)
                .addParams("sceneId", sceneId)
                .addParams("icon", icon)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        SmartToast.show("网络连接错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String errcode = jsonObject.getString("errcode");
                            String msg = EcodeValue.resultEcode(errcode);
                            SmartToast.show(msg);
                            if (errcode.equals("0")){
                                sceneDetail();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 修改名称
     * @param sceneName
     */
    private void updateSceneName(String sceneName) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.UPDATESCENE))
                .addParams("token", token)
                .addParams("sceneId", sceneId)
                .addParams("name", sceneName)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        SmartToast.show("网络连接错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String errcode = jsonObject.getString("errcode");
                            String msg = EcodeValue.resultEcode(errcode);
                            SmartToast.show(msg);
                            if (errcode.equals("0")){
                                if (sceneDialog!=null){
                                    sceneDialog.dismiss();
                                }
                                sceneDetail();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    class HomeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String message = intent.getStringExtra("message");
            if (action.equals("DOPP")) {
                try {
                    JSONObject jsonObject=new JSONObject(message);
                    String ecode = jsonObject.getString("ecode");
                    if (ecode.equals("0")){
                        String s = EcodeValue.resultEcode(ecode);
                        SmartToast.show(s);
                        sceneDetail();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else if (action.equals("DAPP")){
//                try {
//                    JSONObject jsonObject=new JSONObject(message);
//                    String sdid = jsonObject.getString("sdid");
//                    if (sdid.equals(deviceid)){
//                        DappBean dappBean = new Gson().fromJson(message, DappBean.class);
//                        List<DappBean.Atts> atts = dappBean.atts;
//                        for (int i = 0; i < atts.size(); i++) {
//                            int port = atts.get(i).port;
//                            value_left = atts.get(i).value;
//                            if (port==1){
//                                setViewByAtt(value_left,iv_att,tv_att_name);
//                            }
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(homeReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data!=null){

            if (requestCode==100){
                sceneDetail();
            }
        }
    }
}
