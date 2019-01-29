package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.MoveDeviceToRoomAdapter;
import com.hbdiye.newlechuangsmart.adapter.RoomAdapter;
import com.hbdiye.newlechuangsmart.bean.RoomDetailDeviceBean;
import com.hbdiye.newlechuangsmart.bean.RoomDeviceBean;
import com.hbdiye.newlechuangsmart.bean.RoomListBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.IconByName;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.hbdiye.newlechuangsmart.view.LinkageAddIconPopwindow;
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

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.ROOMDETAIL;

public class RoomActivity extends AppCompatActivity {

    @BindView(R.id.iv_base_back)
    ImageView ivBaseBack;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.iv_linkage_ic)
    ImageView ivLinkageIc;
    @BindView(R.id.ll_linkage_icon)
    RelativeLayout llLinkageIcon;
    @BindView(R.id.tv_room_name)
    TextView tvRoomName;
    @BindView(R.id.iv_linkage_edit)
    ImageView ivLinkageEdit;
    @BindView(R.id.rv_room_device)
    RecyclerView rvRoomDevice;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    private RoomAdapter adapter;
    private List<RoomDetailDeviceBean.DeviceList> mList=new ArrayList<>();

    private LinkageAddIconPopwindow popwindow;
    private List<Integer> mList_icon = new ArrayList<>();
    private RoomListBean.RoomList roomInfo;
    private String roomId;
    private RoomListBean roomListBean;
    private String token;
    private String name;
    private SceneDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        GridLayoutManager manager=new GridLayoutManager(this,3);
        rvRoomDevice.setLayoutManager(manager);
        adapter=new RoomAdapter(mList);
        rvRoomDevice.setAdapter(adapter);
    }

    private void initData() {
        token = (String) SPUtils.get(this,"token","");
        roomInfo = (RoomListBean.RoomList) getIntent().getSerializableExtra("roomInfo");
        roomListBean= (RoomListBean) getIntent().getSerializableExtra("roomListBean");
        roomId=roomInfo.id;
        name = roomInfo.name;
        tvRoomName.setText(name);
        mList_icon.add(R.drawable.keting3);
        mList_icon.add(R.drawable.shufang2);
        mList_icon.add(R.drawable.shufang);
        mList_icon.add(R.drawable.weisehngjian);
        mList_icon.add(R.drawable.weishengjian2);
        mList_icon.add(R.drawable.danrenfang);
        mList_icon.add(R.drawable.ertongfang);
        mList_icon.add(R.drawable.keting);
        mList_icon.add(R.drawable.keting2);
        mList_icon.add(R.drawable.zhuwo);
        mList_icon.add(R.drawable.chufang);
        mList_icon.add(R.drawable.yangtai);
//        for (int i = 0; i < 5; i++) {
//            mList.add(i + "");
//        }
//        adapter.notifyDataSetChanged();
        roomDetail();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                alertRoomList(position);
            }
        });
    }

    private void alertRoomList(final int pos) {
        final List<RoomListBean.RoomList> roomList = roomListBean.roomList;
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).id.equals(roomId)){
                roomList.remove(i);
            }
        }

        final String deviceId = mList.get(pos).id;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.alertdialog_list, null);
        RecyclerView rv_name = view.findViewById(R.id.rv_dialog);
        MoveDeviceToRoomAdapter adapter = new MoveDeviceToRoomAdapter(roomList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_name.setLayoutManager(manager);
        rv_name.setAdapter(adapter);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                dialog.dismiss();
                String move_room_id = roomList.get(position).id;
                moveDeviceRoom(move_room_id,deviceId);
            }
        });
    }

    private void moveDeviceRoom(String move_room_id,String deviceId) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.MOVEDEVICEROOM))
                .addParams("token",token)
                .addParams("roomId",move_room_id)
                .addParams("deviceId",deviceId)
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
                                roomDetail();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 房间详情
     */
    private void roomDetail() {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(ROOMDETAIL))
                .addParams("token",token)
                .addParams("roomId",roomId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        RoomDetailDeviceBean roomDetailDeviceBean = new Gson().fromJson(response, RoomDetailDeviceBean.class);
                        List<RoomDetailDeviceBean.DeviceList> deviceList = roomDetailDeviceBean.deviceList;
                        if (deviceList!=null){
                            if (mList.size()>0){
                                mList.clear();
                            }
                            mList.addAll(deviceList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @OnClick({R.id.iv_base_back, R.id.iv_linkage_ic, R.id.iv_linkage_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_base_back:
                finish();
                break;
            case R.id.iv_linkage_ic:
                popwindow = new LinkageAddIconPopwindow(this, mList_icon, clickListener);
                popwindow.showPopupWindowBottom(llRoot);
                break;
            case R.id.iv_linkage_edit:
                dialog=new SceneDialog(this,R.style.MyDialogStyle,clicker,"修改房间名称",name);
                dialog.show();
                break;
        }
    }
    private View.OnClickListener clicker=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.app_cancle_tv:
                    dialog.dismiss();
                    break;
                case R.id.app_sure_tv:
                    String sceneName = dialog.getSceneName();
                    if (!TextUtils.isEmpty(sceneName)){
                        updateName(sceneName);
                    }
                    break;
            }
        }
    };

    private void updateName(final String sceneName) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.UPDATEROOMNAMEICON))
                .addParams("token",token)
                .addParams("roomId",roomId)
                .addParams("name",sceneName)
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
                                if (dialog!=null){
                                    dialog.dismiss();
                                }
                                SmartToast.show("修改成功");
                                tvRoomName.setText(sceneName);
                            }else {
                                String s1 = EcodeValue.resultEcode(errcode);
                                SmartToast.show(s1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private BaseQuickAdapter.OnItemClickListener clickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            List<Integer> data = adapter.getData();
            Integer integer = data.get(position);
            updateIcon(IconByName.icNameByDrawableRoom(data.get(position)),integer);
            popwindow.dismiss();
        }
    };

    private void updateIcon(String s, final int i) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.UPDATEROOMNAMEICON))
                .addParams("token",token)
                .addParams("roomId",roomId)
                .addParams("name",name)
                .addParams("icon",s)
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
                                if (popwindow!=null){
                                    popwindow.dismiss();
                                }
                                SmartToast.show("修改成功");
                                Glide.with(RoomActivity.this).load(i).into(ivLinkageIc);
                            }else {
                                String s1 = EcodeValue.resultEcode(errcode);
                                SmartToast.show(s1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
