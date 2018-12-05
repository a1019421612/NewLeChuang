package com.hbdiye.newlechuangsmart.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.MyWebSocketHandler;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.SingleWebSocketConnection;
import com.hbdiye.newlechuangsmart.SingleWebSocketHandler;
import com.hbdiye.newlechuangsmart.SocketSendMessage;
import com.hbdiye.newlechuangsmart.activity.LoginActivity;
import com.hbdiye.newlechuangsmart.activity.MessageActivity;
import com.hbdiye.newlechuangsmart.activity.MonitorListActivity;
import com.hbdiye.newlechuangsmart.activity.MoreSceneActivity;
import com.hbdiye.newlechuangsmart.bean.HomeSceneBean;
import com.hbdiye.newlechuangsmart.bean.UserFamilyInfoBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.IconByName;
import com.hbdiye.newlechuangsmart.util.PicUtils;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.hbdiye.newlechuangsmart.util.StringUtil;
import com.hbdiye.newlechuangsmart.view.CustomViewPager;
import com.hbdiye.newlechuangsmart.view.MyGridView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.tavendo.autobahn.WebSocketConnection;
import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.MyApp.finishAllActivity;

public class HomeFragment extends Fragment {
    @BindView(R.id.gv_fragment_home)
    GridView gvFragmentHome;
    @BindView(R.id.viewpager)
    CustomViewPager viewpager;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.gv_cgq_value)
    MyGridView gvCgqValue;
    private HomeReceiver homeReceiver;
    private Unbinder bind;
    private WebSocketConnection mConnection;
    private String mobilephone;
    private String password;
    private String url;
    public MyWebSocketHandler instance;

    //    private List<Integer> mList = new ArrayList<>();
//    private List<String> mList_t = new ArrayList<>();
    private List<HomeSceneBean.SceneList> list = new ArrayList<>();
    private Myadapter mMyadapter;
    private ArrayList<String> imageUrl = new ArrayList<>();
    private String token;

    private boolean scene_flag = true;//场景是否可用点击状态
    private List<HomeSceneBean.DevAttList> mList=new ArrayList<>();
    private MyCGQadapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        bind = ButterKnife.bind(this, view);
        token = (String) SPUtils.get(getActivity(), "token", "");
//        initWebSocket();
        mConnection = SingleWebSocketConnection.getInstance();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("GOPP");
        homeReceiver = new HomeReceiver();
        getActivity().registerReceiver(homeReceiver, intentFilter);
        initData();
        personInfo();
        imageUrl.add("http://www.wuyueapp.com/wuyueTest//api/img/show?id=5b694a0b00be4526acf029da");
        imageUrl.add("http://www.wuyueapp.com/wuyueTest/api/img/show?id=5b6949ff00be4526acf029d8");
        imageUrl.add("http://www.wuyueapp.com/wuyueTest/api/img/show?id=5b69499a00be4526acf029d4");

        mMyadapter = new Myadapter();
        gvFragmentHome.setAdapter(mMyadapter);

        mAdapter=new MyCGQadapter();
        gvCgqValue.setAdapter(mAdapter);
        gvCgqValue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), MonitorListActivity.class).putExtra("cur_pos",position+""));
            }
        });
        viewpager.setImageResources(imageUrl, mAdCycleViewListener);
        gvFragmentHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == list.size()) {
                    startActivity(new Intent(getActivity(), MoreSceneActivity.class));
                } else {
                    if (scene_flag) {//可点击状态
                        scene_flag = false;
                        mConnection.sendTextMessage("{\"pn\":\"GOPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"3\",\"sceneid\":\"" + list.get(position).id + "\"}");
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (!scene_flag) {//视为发送请求10秒以后没有接受到返回信息
                                    SmartToast.show("响应失效");
                                    scene_flag = true;
                                }
                            }
                        }, 10000);
                    } else {//不可点击状态
                        SmartToast.show("请稍候");
                    }
                }
            }
        });
        return view;
    }

    private void initData() {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.GETINDEXDATA))
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
                                List<HomeSceneBean.DevAttList> devAttList = homeSceneBean.devAttList;
                                List<HomeSceneBean.SceneList> sceneList = homeSceneBean.sceneList;
                                if (sceneList != null && sceneList.size() > 0) {
                                    if (list.size() > 0) {
                                        list.clear();
                                    }
                                    list.addAll(sceneList);
                                    mMyadapter.notifyDataSetChanged();
                                }
                                if (devAttList!=null&&devAttList.size()>0){
                                    if (mList.size()>0){
                                        mList.clear();
                                    }
                                    mList.addAll(devAttList);
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initWebSocket() {
        mobilephone = (String) SPUtils.get(getActivity(), "mobilephone", "");
        password = (String) SPUtils.get(getActivity(), "password", "");
        url = (String) SPUtils.get(getActivity(), "url", "");
        mConnection = SingleWebSocketConnection.getInstance();
        instance = SingleWebSocketHandler.getInstance(mConnection, "{\"pn\":\"UITP\"}");
        try {
            mConnection.connect(url, instance);
        } catch (Exception e) {
            Log.e("sss", "异常：" + e.toString());
            e.printStackTrace();
        }
        instance.SetSocketsendMessage(new SocketSendMessage() {
            @Override
            public void websocketSendMessage(String message) {
                if (message.contains("\"pn\":\"PRTP\"")) {
                    Log.e("EEE", message);
                    mConnection.disconnect();
                    finishAllActivity();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }

//                =========================================================
                if (message.contains("\"pn\":\"DCPP\"")) {
                    websocketSendBroadcase(message, "DCPP");
                }
                if (message.contains("\"pn\":\"DAPP\"")) {
                    websocketSendBroadcase(message, "DAPP");
                }
                if (message.contains("\"pn\":\"GOPP\"")) {//删除场景
                    websocketSendBroadcase(message, "GOPP");
                }
                if (message.contains("\"pn\":\"DOPP\"")) {//场景添加设备
                    websocketSendBroadcase(message, "DOPP");
                }
//                ========================================================
                if (message.contains("\"pn\":\"SLTP\"")) {
                    websocketSendBroadcase(message, "SLTP");
                }
                if (message.contains("\"pn\":\"UITP\"")) {

                    websocketSendBroadcase(message, "UITP");
                }
                if (message.contains("\"pn\":\"NSSTP\"")) {
                    //开启场景
                    websocketSendBroadcase(message, "NSSTP");
                }
                if (message.contains("\"pn\":\"SUTP\"")) {
                    //修改场景名称
                    websocketSendBroadcase(message, "SUTP");
                }
                if (message.contains("\"pn\":\"NSDTP\"")) {
                    //删除场景
                    websocketSendBroadcase(message, "NSDTP");
                }
                if (message.contains("\"pn\":\"NSATP\"")) {
                    //添加场景
                    websocketSendBroadcase(message, "NSATP");
                }
                if (message.contains("\"pn\":\"LLTP\"")) {
                    //联动
                    websocketSendBroadcase(message, "LLTP");
                }
                if (message.contains("\"pn\":\"LUTP\"")) {
//                修改联动名称
                    websocketSendBroadcase(message, "LUTP");
                }
                if (message.contains("\"pn\":\"LDTP\"")) {
                    //删除联动
                    websocketSendBroadcase(message, "LDTP");
                }
                if (message.contains("\"pn\":\"LATP\"")) {
                    //LATPSPUtils.put(this,"isTrigger",true);
                    boolean isTrigger = (boolean) SPUtils.get(getActivity(), "isTrigger", false);
                    if (!isTrigger) {
                        websocketSendBroadcase(message, "LATP");
                    } else {
                        websocketSendBroadcase(message, "LATP_T");
                    }
                }
//========================scenesettingActivity======================================
                if (message.contains("\"pn\":\"STLTP\"")) {
                    //STLTP  scenesettingActivity
                    websocketSendBroadcase(message, "STLTP");
                }
                if (message.contains("\"pn\":\"IRLTP\"")) {
                    //IRLTP
                    websocketSendBroadcase(message, "IRLTP");
                }
                if (message.contains("\"pn\":\"SUTP\"")) {
                    //修改场景名称
                    websocketSendBroadcase(message, "SUTP");
                }
                if (message.contains("\"pn\":\"NSTATP\"")) {
                    //添加设备
                    websocketSendBroadcase(message, "NSTATP");
                }
                if (message.contains("\"pn\":\"NSTUTP\"")) {
                    //设置延时
                    websocketSendBroadcase(message, "NSTUTP");
                }
                if (message.contains("\"pn\":\"NSTDTP\"")) {
                    //删除设备
                    websocketSendBroadcase(message, "NSTDTP");
                }
                if (message.contains("\"pn\":\"SDRTP\"")) {
                    //情景面板设备
                    websocketSendBroadcase(message, "SDRTP");
                }
//                ============================FangjianActivity========================
                if (message.contains("\"pn\":\"GSTP\"")) {
//                    GSTP设备 调试 停止入网
                    websocketSendBroadcase(message, "GSTP");
                }
//========================LinkageSettingActivity=====================
                if (message.contains("\"pn\":\"LCTP\"")) {
                    //
                    websocketSendBroadcase(message, "LCTP");
                }
                if (message.contains("\"pn\":\"LDLTP\"")) {
                    //
                    websocketSendBroadcase(message, "LDLTP");
                }
                if (message.contains("\"pn\":\"IRLTP\"")) {
                    //设备列表
                    websocketSendBroadcase(message, "IRLTP");
                }
                if (message.contains("\"pn\":\"LUTP\"")) {
                    //修改联动名称
                    websocketSendBroadcase(message, "LUTP");
                }
                if (message.contains("\"pn\":\"LTDTP\"")) {
                    // 删除联动设备 LTDTP
                    websocketSendBroadcase(message, "LTDTP");
                }
                if (message.contains("\"pn\":\"LTUTP\"")) {
                    //  延时时间修改 LTUTP
                    websocketSendBroadcase(message, "LTUTP");
                }
                if (message.contains("\"pn\":\"LTATP\"")) {
                    //   联动添加设备 LTATP
                    websocketSendBroadcase(message, "LTATP");
                }
// =========================devicetriggeredactivity============
                if (message.contains("\"pn\":\"LDLTP\"")) {
                    //  设备列表
                    websocketSendBroadcase(message, "LDLTP");
                }
                if (message.contains("\"pn\":\"LUTP\"")) {
                    //  修改触发设备
                    websocketSendBroadcase(message, "LUTP");
                }
                if (message.contains("\"pn\":\"LCTP\"")) {
                    //
                    websocketSendBroadcase(message, "LCTP");
                }
//=========================FamilyNameActivity==================
                if (message.contains("\"pn\":\"UITP\"")) {
                    //
                    websocketSendBroadcase(message, "UITP");
                }
                if (message.contains("\"pn\":\"UUITP\"")) {
                    //
                    websocketSendBroadcase(message, "UUITP");
                }
                if (message.contains("\"pn\":\"UJFTP\"")) {
                    //扫描加入家庭
                    websocketSendBroadcase(message, "UJFTP");
                }
//======================editpswactivity=================
                if (message.contains("\"pn\":\"UUITP\"")) {
                    //修改密码
                    websocketSendBroadcase(message, "UUITP");
                }
//======================FamilyManagerActivity===================
                if (message.contains("\"pn\":\"RGLTP\"")) {
                    //房间管理
//                    boolean isRoom= (boolean) SPUtils.get(getActivity(),"RoomRgltp",false);
//                    if (isRoom){
//                        websocketSendBroadcase(message,"RGLTP_R");
//                    }else {
                    websocketSendBroadcase(message, "RGLTP");
//                    }
//                    SPUtils.remove(getActivity(),"RoomRgltp");
                }
                if (message.contains("\"pn\":\"DDUTP\"")) {
//                    DDUTP 将设备放置到对应房间
                    websocketSendBroadcase(message, "DDUTP");
                }
                if (message.contains("\"pn\":\"RUTP\"")) {
                    //修改房间名
                    websocketSendBroadcase(message, "RUTP");
                }
                if (message.contains("\"pn\":\"RATP\"")) {
                    //添加新房间 RATP
                    websocketSendBroadcase(message, "RATP");
                }
                if (message.contains("\"pn\":\"RDTP\"")) {
                    //删除房间
                    websocketSendBroadcase(message, "RDTP");
                }
                if (message.contains("\"pn\":\"RDTP\"")) {
                    //删除房间中的设备
                    websocketSendBroadcase(message, "DDUTP");
                }
//=========================RoomActivity========================
                if (message.contains("\"pn\":\"SDOSTP\"")) {
                    //子设备在线
                    websocketSendBroadcase(message, "SDOSTP");
                }
                if (message.contains("\"pn\":\"DUTP\"")) {
                    //修改设备名称
                    websocketSendBroadcase(message, "DUTP");
                }
                if (message.contains("\"pn\":\"DGLTP\"")) {
                    //列表
                    websocketSendBroadcase(message, "DGLTP");
                }
                if (message.contains("\"pn\":\"SDBTP\"")) {
                    //扫描加入家庭
                    websocketSendBroadcase(message, "SDBTP");
                }
                if (message.contains("\"pn\":\"ATP\"")) {
                    websocketSendBroadcase(message, "ATP");
                }
//==========================YaoKongListActivity=========================
                if (message.contains("\"pn\":\"IRUTP\"")) {
                    //                修改设备名称 IRUTP
                    websocketSendBroadcase(message, "IRUTP");
                }
                if (message.contains("\"pn\":\"IRDTP\"")) {
                    //删除 IRDTP
                    websocketSendBroadcase(message, "IRDTP");
                }
//===========================PicKTYaoKongActivity=====================
                if (message.contains("\"pn\":\"IRTP\"")) {
                    websocketSendBroadcase(message, "IRTP");
                }
                if (message.contains("\"pn\":\"IRATP\"")) {
                    websocketSendBroadcase(message, "IRATP");
                }
//                ==============医疗BTLTP
                if (message.contains("\"pn\":\"BTLTP\"")) {
                    websocketSendBroadcase(message, "BTLTP");
                }
            }
        });
    }

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
                    if (ecode.equals("0")) {
                        scene_flag = true;
                        SmartToast.show("开启成功");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                parseData(message);
            }
        }
    }
    private void personInfo() {
        OkHttpUtils.post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.USERANDFAMILYINFO))
                .addParams("token", token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        UserFamilyInfoBean userFamilyInfoBean = new Gson().fromJson(response, UserFamilyInfoBean.class);
                        String errcode = userFamilyInfoBean.errcode;
                        if (errcode.equals("0")) {
                            String user_name = userFamilyInfoBean.user.name;
                            SPUtils.put(getActivity(),"nickName",user_name);
                        }
                    }
                });
    }
    private void websocketSendBroadcase(String message, String param) {
        Intent intent = new Intent();
        intent.setAction(param);
        intent.putExtra("message", message);
        try {
            getActivity().sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CustomViewPager.ImageCycleViewListener mAdCycleViewListener = new CustomViewPager.ImageCycleViewListener() {
        @Override
        public void onImageClick(int position, View imageView) {
            // TODO 单击图片处理事件
            int curPos = viewpager.getCurPos();
            String url = imageUrl.get(curPos);
//            Toast.makeText(getActivity(), url, Toast.LENGTH_SHORT).show();
            if (StringUtil.isBlank(url)) {
                return;
            }
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
//            PicUtils.showImgRoundedNoDiskCache(getActivity(), imageView, R.drawable.denglutupian);
            PicUtils.showImgRoundedNoDiskCacheNoUrl(getActivity(), imageView);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
        getActivity().unregisterReceiver(homeReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //这是一条错误指令目的是为了立刻断开连接 如果用mConnection.disconnect()会等很长时间才会断开连接
//        mConnection.sendTextMessage("{\"pn\":\"DLLL\", \"classify\":\"protype\", \"id\":\"PROTYPE07\"}");
//        mConnection.disconnect();
    }

    @OnClick(R.id.iv_message)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), MessageActivity.class));
    }
    class MyCGQadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = LayoutInflater.from(getActivity()).inflate(R.layout.home_value_item, null);
            TextView tv_home_value=view.findViewById(R.id.tv_home_value);
            TextView tv_home_name=view.findViewById(R.id.tv_home_name);
            TextView tv_home_empty=view.findViewById(R.id.tv_home_empty);
            if (mList.size()-1>=i){
                tv_home_empty.setVisibility(View.GONE);
                tv_home_name.setVisibility(View.VISIBLE);
                tv_home_value.setVisibility(View.VISIBLE);
                tv_home_name.setText(mList.get(i).name);
                DecimalFormat df=new DecimalFormat("0.0");
                tv_home_value.setText(df.format((float)mList.get(i).value/100));
            }else {
                tv_home_value.setVisibility(View.GONE);
                tv_home_name.setVisibility(View.GONE);
                tv_home_empty.setVisibility(View.VISIBLE);
            }
//            if (mList==null||mList.size()==0){
//                tv_home_value.setVisibility(View.GONE);
//                tv_home_name.setVisibility(View.GONE);
//                tv_home_empty.setVisibility(View.VISIBLE);
//            }else {
//
//            }
//            ImageView imageView = (ImageView) view.findViewById(R.id.gridview_item);
//            TextView tv_title=view.findViewById(R.id.tv_content);
//            if (mList.size() == i) {
//                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                Glide.with(getActivity()).load(R.drawable.other).into(imageView);
//                if (i == 7) {
//                    imageView.setVisibility(View.GONE);
//                }
//            } else {
//                Glide.with(getActivity()).load(mList.get(i).getIcon()).into(imageView);
//                tv_title.setText(mList.get(i).getTitle());
//            }

            return view;
        }
    }
    class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size() + 1 == 8 ? 8 : list.size() + 1;
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = LayoutInflater.from(getActivity()).inflate(R.layout.add_scene, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.gridview_item);
            TextView textView = (TextView) view.findViewById(R.id.tv_content_home);
            if (list.size() == i) {
                Glide.with(getActivity()).load(R.drawable.home_add).into(imageView);
                textView.setText("更多");
                if (i == 9) {
                    imageView.setVisibility(View.GONE);
                }
            } else {
                Glide.with(getActivity()).load(IconByName.drawableByName(list.get(i).icon)).into(imageView);
                textView.setText(list.get(i).name);
            }
            return view;
        }
    }

}
