package com.hbdiye.newlechuangsmart.activity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.SingleWebSocketConnection;
import com.hbdiye.newlechuangsmart.adapter.DragAdapter;
import com.hbdiye.newlechuangsmart.adapter.MoreSceneAdapter;
import com.hbdiye.newlechuangsmart.bean.HomeSceneBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.hbdiye.newlechuangsmart.view.SceneDialog;
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

import de.tavendo.autobahn.WebSocketConnection;
import okhttp3.Call;


public class MoreSceneActivity extends AppCompatActivity {
    //    /** 用户栏目的GRIDVIEW */
//    private DragGrid userGridView;
//    /** 其它栏目的GRIDVIEW */
//    private OtherGridView otherGridView;
//    /** 用户栏目对应的适配器，可以拖动 */
//    DragAdapter userAdapter;
//    /** 其它栏目对应的适配器 */
//    OtherAdapter otherAdapter;
//    /** 其它栏目列表 */
//    ArrayList<ChannelItem> otherChannelList = new ArrayList<ChannelItem>();
//    /** 用户栏目列表 */
//    ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();
//    /** 是否在移动，由于这边是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。 */
//    boolean isMove = false;
//    private TextView tv_save;
    private SwipeMenuRecyclerView rv_more_scene;
    private ImageView iv_back, iv_more_add;
    private MoreSceneAdapter adapter;
    private List<HomeSceneBean.SceneList> mList = new ArrayList<>();
    private List<HomeSceneBean.SceneList> mList_c = new ArrayList<>();
    private String token;

    private int start_pos = -1;
    private int end_pos = -1;
    private WebSocketConnection mConnection;
    private HomeReceiver homeReceiver;
    private SceneDialog sceneDialog;
    private boolean scene_flag=true;//场景是否可用点击状态
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_scene);
        token = (String) SPUtils.get(this, "token", "");
        mConnection = SingleWebSocketConnection.getInstance();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("GOPP");
        homeReceiver = new HomeReceiver();
        registerReceiver(homeReceiver, intentFilter);
        initView();
        initData();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_base_back);
        iv_more_add = findViewById(R.id.iv_more_add);
        rv_more_scene = findViewById(R.id.rv_more_scene);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_more_scene.setLayoutManager(manager);
        rv_more_scene.setSwipeMenuCreator(swipeMenuCreator);
        rv_more_scene.setSwipeMenuItemClickListener(mMenuItemClickListener);
        adapter = new MoreSceneAdapter(mList);
        rv_more_scene.setAdapter(adapter);
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(rv_more_scene);

// 开启拖拽
        adapter.enableDragItem(itemTouchHelper);
        adapter.setOnItemDragListener(onItemDragListener);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_more_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MoreSceneActivity.this, SceneDetailActivity.class) .putExtra("sceneId", ""));
                sceneDialog=new SceneDialog(MoreSceneActivity.this,R.style.MyDialogStyle,clicker,"创建场景");
                sceneDialog.show();
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (scene_flag){//可点击状态
                    scene_flag=false;
                    mConnection.sendTextMessage("{\"pn\":\"GOPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"3\",\"sceneid\":\"" + mList_c.get(position).id + "\"}");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!scene_flag){//视为发送请求10秒以后没有接受到返回信息
                                SmartToast.show("响应失效");
                                scene_flag=true;
                            }
                        }
                    },10000);
                }else {//不可点击状态
                    SmartToast.show("请稍候");
                }

            }
        });

    }
    private View.OnClickListener clicker=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.app_cancle_tv:
                    sceneDialog.dismiss();
                    break;
                case R.id.app_sure_tv:
                    String sceneName = sceneDialog.getSceneName();
                    if (!TextUtils.isEmpty(sceneName)){
                        createScene(sceneName);
                    }
                    break;
            }
        }
    };
    /**
     * 创建场景
     */
    private void createScene(String name) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.CREATESCENE))
                .addParams("token",token)
                .addParams("icon","shouye")
                .addParams("name",name)
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
                            String msg = EcodeValue.resultEcode(errcode);
                            SmartToast.show(msg);
                            if (errcode.equals("0")){
                                if (sceneDialog!=null){
                                    sceneDialog.dismiss();
                                }
                                sceneList();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initData() {
        sceneList();
    }

    private void sceneList() {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.SCENELIST))
                .addParams("token", token)
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
                                HomeSceneBean homeSceneBean = new Gson().fromJson(response, HomeSceneBean.class);
                                List<HomeSceneBean.SceneList> sceneList = homeSceneBean.sceneList;
                                if (sceneList != null && sceneList.size() > 0) {
                                    if (mList.size() > 0) {
                                        mList.clear();
                                    }
                                    if (mList_c.size() > 0) {
                                        mList_c.clear();
                                    }
                                    mList_c.addAll(sceneList);
                                    mList.addAll(sceneList);
                                    adapter.notifyDataSetChanged();
                                }
                            } else {
                                String msg = EcodeValue.resultEcode(errcode);
                                SmartToast.show(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    OnItemDragListener onItemDragListener = new OnItemDragListener() {
        @Override
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#e8e8e8"));
            start_pos = pos;
        }

        @Override
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

        }

        @Override
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            if (start_pos == end_pos) {
                return;
            }
            end_pos = pos;
            updateIndex(mList_c.get(start_pos).id + "", mList_c.get(start_pos).index + "", mList_c.get(end_pos).index + "");
        }
    };

    /**
     * 排序
     */
    private void updateIndex(String sceneId, String currIndex, String newIndex) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.UPDATESCENEINDEX))
                .addParams("token", token)
//                .addParams("currIndex", currIndex)
                .addParams("newIndex", newIndex)
                .addParams("sceneId", sceneId)
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
                            if (errcode.equals("0")) {
                                sceneList();
                            } else {
                                String msg = EcodeValue.resultEcode(errcode);
                                SmartToast.show(msg);
                                mList.clear();
                                mList.addAll(mList_c);
                                adapter.notifyDataSetChanged();
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
                SwipeMenuItem deleteItem = new SwipeMenuItem(MoreSceneActivity.this)
                        .setBackgroundColor(Color.RED)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

                SwipeMenuItem addItem = new SwipeMenuItem(MoreSceneActivity.this)
                        .setBackgroundColor(Color.BLUE)
                        .setText("编辑")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
            }
        }
    };
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
                    mConnection.sendTextMessage("{\"pn\":\"GOPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"4\",\"sceneid\":\"" + mList_c.get(adapterPosition).id + "\"}");
                } else {
                    //编辑
                    String sceneId = mList_c.get(adapterPosition).id;
                    startActivity(new Intent(MoreSceneActivity.this, SceneDetailActivity.class)
                            .putExtra("sceneId", sceneId));
                }
//                mList.remove(adapterPosition);
//                adapter.notifyDataSetChanged();"{\"pn\":\"GOPP\",\"pt\":\"T\",\"pid\":\"%@\",\"token\":\"%@\",\"oper\":\"4\",\"sceneid\":\"%@\"}"
//                mConnection.sendTextMessage("{\"pn\":\"GOPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"4\",\"sceneid\":\""+mList_c.get(adapterPosition).id+"\"}");
            }
        }
    };

    class HomeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String message = intent.getStringExtra("message");
            if (action.equals("GOPP")) {
                try {
                    JSONObject jsonObject = new JSONObject(message);
                    String ecode = jsonObject.getString("ecode");
                    String s = EcodeValue.resultEcode(ecode);
                    if (ecode.equals("0")&&message.contains("\"oper\":\"4\"")) {
                        SmartToast.show("删除成功");
                        sceneList();
                    }else if (ecode.equals("0")&&message.contains("\"oper\":\"3\"")){
                        scene_flag=true;
                        SmartToast.show("开启成功");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                parseData(message);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sceneList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(homeReceiver);
    }
}
