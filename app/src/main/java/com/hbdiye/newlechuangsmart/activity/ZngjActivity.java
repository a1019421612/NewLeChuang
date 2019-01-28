package com.hbdiye.newlechuangsmart.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.ZngjAdapter;
import com.hbdiye.newlechuangsmart.bean.ZngjBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.hbdiye.newlechuangsmart.zxing.activity.CaptureActivity;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class ZngjActivity extends BaseActivity {

    @BindView(R.id.rv_zngj)
    SwipeMenuRecyclerView rvZngj;
    //    @BindView(R.id.rv_zngj)
//    RecyclerView rvZngj;
    private String token;
    private ZngjAdapter adapter;
    private List<ZngjBean.RobotList> mList = new ArrayList<>();

    @Override
    protected void initData() {
        token = (String) SPUtils.get(this, "token", "");
        zngjList();
    }

    private void zngjList() {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.ZNGJLIST))
                .addParams("token", token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ZngjBean zngjBean = new Gson().fromJson(response, ZngjBean.class);
                        if (zngjBean.errcode.equals("0")) {
                            List<ZngjBean.RobotList> robotList = zngjBean.robotList;
                            if (mList.size()>0){
                                mList.clear();
                            }
                            mList.addAll(robotList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    protected String getTitleName() {
        return "智能管家";
    }

    @Override
    protected void initView() {
        ivBaseAdd.setVisibility(View.VISIBLE);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvZngj.setLayoutManager(manager);
        rvZngj.setSwipeMenuCreator(swipeMenuCreator);
        rvZngj.setSwipeMenuItemClickListener(mMenuItemClickListener);
        adapter = new ZngjAdapter(mList);
        rvZngj.setAdapter(adapter);
//        LinearLayoutManager manager=new LinearLayoutManager(this);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        rvZngj.setLayoutManager(manager);
//        rvZngj.setAdapter(adapter);
        ivBaseAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ZngjActivity.this, CaptureActivity.class).putExtra("camera", false).putExtra("flag", true)
                        .putExtra("flag_gj", true));
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_zngj;
    }
    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                if (menuPosition == 0) {
                    //删除
                    String serialnumber = mList.get(adapterPosition).serialnumber;
                    unbindRobot(serialnumber);
//                    mConnection.sendTextMessage("{\"pn\":\"GOPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"4\",\"sceneid\":\"" + mList_c.get(adapterPosition).id + "\"}");
                }
//                mList.remove(adapterPosition);
//                adapter.notifyDataSetChanged();"{\"pn\":\"GOPP\",\"pt\":\"T\",\"pid\":\"%@\",\"token\":\"%@\",\"oper\":\"4\",\"sceneid\":\"%@\"}"
//                mConnection.sendTextMessage("{\"pn\":\"GOPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"4\",\"sceneid\":\""+mList_c.get(adapterPosition).id+"\"}");
            }
        }
    };

    private void unbindRobot(String serialnumber) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.UNBINDROBOT))
                .addParams("token",token)
                .addParams("serialnumber",serialnumber)
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
                                SmartToast.show("解绑成功");
                                zngjList();
                            }else {
                                String s = EcodeValue.resultEcode(errcode);
                                SmartToast.show(s);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.updatebar_content_height);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 添加左侧的，如果不添加，则左侧不会出现菜单。
//            {
//                SwipeMenuItem addItem = new SwipeMenuItem(MoreSceneActivity.this)
//                        .setBackgroundColor()
//                        .setImage(R.mipmap.ic_action_add)
//                        .setWidth(width)
//                        .setHeight(height);
//                swipeLeftMenu.addMenuItem(addItem); // 添加菜单到左侧。
//
//                SwipeMenuItem closeItem = new SwipeMenuItem(MoreSceneActivity.this)
//                        .setBackground(R.drawable.selector_red)
//                        .setImage(R.mipmap.ic_action_close)
//                        .setWidth(width)
//                        .setHeight(height);
//                swipeLeftMenu.addMenuItem(closeItem); // 添加菜单到左侧。
//            }

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(ZngjActivity.this)
                        .setBackgroundColor(Color.RED)
                        .setText("解绑")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

//                SwipeMenuItem addItem = new SwipeMenuItem(MoreSceneActivity.this)
//                        .setBackgroundColor(Color.BLUE)
//                        .setText("编辑")
//                        .setTextColor(Color.WHITE)
//                        .setWidth(width)
//                        .setHeight(height);
//                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        zngjList();
    }
}
