package com.hbdiye.newlechuangsmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.RoomListBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.IconByName;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.hbdiye.newlechuangsmart.view.DelDialog;
import com.hbdiye.newlechuangsmart.view.MyGridView;
import com.hbdiye.newlechuangsmart.view.SceneDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.DELROOM;
import static com.hbdiye.newlechuangsmart.global.InterfaceManager.ROOMLIST;

public class RoomListActivity extends BaseActivity {
    @BindView(R.id.gv_room_list)
    MyGridView gvRoomList;

    private String token;
    private List<RoomListBean.RoomList> mList_device = new ArrayList<>();
    private MyDeviceadapter device_adapter;
    private boolean flag = false;//编辑设备分类
    private DelDialog delDialog;
    private SceneDialog sceneDialog;
    private RoomListBean roomListBean;
    private int del_pos = -1;

    @Override
    protected void initData() {
        token = (String) SPUtils.get(this, "token", "");
        roomList();
    }

    @Override
    protected String getTitleName() {
        return "我的房间";
    }

    @Override
    protected void initView() {
        device_adapter = new MyDeviceadapter();
        gvRoomList.setAdapter(device_adapter);
        tvBaseEnter.setVisibility(View.VISIBLE);
        tvBaseEnter.setText("编辑");
        tvBaseEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    tvBaseEnter.setText("编辑");
                    flag = false;
                } else {
                    flag = true;
                    tvBaseEnter.setText("完成");
                }
                device_adapter.notifyDataSetChanged();
            }
        });
        gvRoomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == mList_device.size()) {
                    //添加
                    sceneDialog = new SceneDialog(RoomListActivity.this, R.style.MyDialogStyle, clicker, "创建新房间");
                    sceneDialog.show();
                } else {
                    startActivity(new Intent(RoomListActivity.this, RoomActivity.class)
                            .putExtra("roomInfo", mList_device.get(i))
                            .putExtra("roomListBean", roomListBean));
                }
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_room_list;
    }

    class MyDeviceadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList_device.size() + 1;
        }

        @Override
        public Object getItem(int i) {
            return mList_device.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(RoomListActivity.this).inflate(R.layout.device_gv_edit_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.gridview_item);
            ImageView iv_del = view.findViewById(R.id.iv_device_del);
            TextView tv_name = view.findViewById(R.id.tv_name);
            if (flag) {
                iv_del.setVisibility(View.VISIBLE);
            } else {
                iv_del.setVisibility(View.GONE);
            }
            if (mList_device.size() == i) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(RoomListActivity.this).load(R.drawable.home_add).into(imageView);
                tv_name.setText("添加");
                iv_del.setVisibility(View.GONE);
//                if (i == 10) {
//                    imageView.setVisibility(View.GONE);
//                }
            } else {
                tv_name.setText(mList_device.get(i).name);
                Glide.with(RoomListActivity.this).load(IconByName.drawableByNameRoom(mList_device.get(i).icon)).into(imageView);
            }
            iv_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    del_pos = i;
                    delDialog = new DelDialog(RoomListActivity.this, R.style.MyDialogStyle, clicker, "是否删除房间？");
                    delDialog.show();
                }
            });
            return view;
        }
    }

    public View.OnClickListener clicker = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_del_ok:
                    if (del_pos != -1) {
                        String roomId = mList_device.get(del_pos).id;
                        del_pos=-1;
                        delRoom(roomId);
                    }
                    delDialog.dismiss();
                    break;
                case R.id.app_cancle_tv:
                    sceneDialog.dismiss();
                    break;
                case R.id.app_sure_tv:
                    String roomName = sceneDialog.getSceneName();
                    if (!TextUtils.isEmpty(roomName)) {
                        createRoom(roomName);
                    }
                    break;
            }
        }
    };

    private void delRoom(String roomId) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(DELROOM))
                .addParams("token", token)
                .addParams("roomId", roomId)
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
                            if (errcode.equals("0")){
                                roomList();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 创建房间
     *
     * @param roomName
     */
    private void createRoom(String roomName) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.CREATEROOM))
                .addParams("token", token)
                .addParams("name", roomName)
                .addParams("icon", "danrenfang3")
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
                                if (sceneDialog != null) {
                                    sceneDialog.dismiss();
                                    roomList();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void roomList() {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(ROOMLIST))
                .addParams("token", token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        roomListBean = new Gson().fromJson(response, RoomListBean.class);
                        List<RoomListBean.RoomList> roomList = roomListBean.roomList;
                        if (mList_device.size() > 0) {
                            mList_device.clear();
                        }
                        mList_device.addAll(roomList);
                        device_adapter.notifyDataSetChanged();
                    }
                });
    }
}
