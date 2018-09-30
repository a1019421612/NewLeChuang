package com.hbdiye.newlechuangsmart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.hbdiye.newlechuangsmart.MyWebSocketHandler;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.SingleWebSocketConnection;
import com.hbdiye.newlechuangsmart.SingleWebSocketHandler;
import com.hbdiye.newlechuangsmart.SocketSendMessage;
import com.hbdiye.newlechuangsmart.activity.LoginActivity;
import com.hbdiye.newlechuangsmart.util.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;

import static com.hbdiye.newlechuangsmart.MyApp.finishAllActivity;

public class HomeFragment extends Fragment {
    @BindView(R.id.tv_home)
    TextView tvHome;

    private Unbinder bind;
    private WebSocketConnection mConnection;
    private String mobilephone;
    private String password;
    public MyWebSocketHandler instance;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        bind = ButterKnife.bind(this, view);
        initWebSocket();
        return view;
    }

    private void initWebSocket() {
        mobilephone = (String) SPUtils.get(getActivity(), "mobilephone", "");
        password = (String) SPUtils.get(getActivity(), "password", "");
        mConnection = SingleWebSocketConnection.getInstance();
        instance = SingleWebSocketHandler.getInstance(mConnection, "{\"pn\":\"UITP\"}");
        try {
            mConnection.connect("ws://39.104.119.0:18888/mobilephone=" + mobilephone + "&password=" + password, instance);
        } catch (WebSocketException e) {
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @OnClick(R.id.tv_home)
    public void onViewClicked() {
        SmartToast.show("home");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //这是一条错误指令目的是为了立刻断开连接 如果用mConnection.disconnect()会等很长时间才会断开连接
//        mConnection.sendTextMessage("{\"pn\":\"DLLL\", \"classify\":\"protype\", \"id\":\"PROTYPE07\"}");
        mConnection.disconnect();
    }
}
