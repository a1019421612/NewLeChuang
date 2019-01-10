package com.hbdiye.newlechuangsmart.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.SingleWebSocketConnection;
import com.hbdiye.newlechuangsmart.activity.ChoiceDeviceActivity;
import com.hbdiye.newlechuangsmart.activity.DeviceDetailActivity;
import com.hbdiye.newlechuangsmart.adapter.DeviceListAdapter;
import com.hbdiye.newlechuangsmart.bean.DeviceList;
import com.hbdiye.newlechuangsmart.bean.DeviceListSceneBean;
import com.hbdiye.newlechuangsmart.bean.SecneSectionBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.hwactivity.ConverterListActivity;
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
import butterknife.Unbinder;
import de.tavendo.autobahn.WebSocketConnection;
import okhttp3.Call;

public class DeviceFragment extends Fragment {
    @BindView(R.id.rv_device)
    RecyclerView rvDevice;
    Unbinder unbinder;
    //    @BindView(R.id.gv_common_classify)
//    MyGridView gvCommonClassify;
//    Unbinder unbinder;
//    @BindView(R.id.gv_device_classify)
//    MyGridView gvDeviceClassify;
//    @BindView(R.id.tv_device_edit)
//    TextView tvDeviceEdit;
//    private List<CommentClassyBean> mList = new ArrayList<>();
//    private List<RoomListBean.RoomList> mList_device = new ArrayList<>();
//    private Myadapter mMyadapter;
//    private MyDeviceadapter device_adapter;
//
//    private boolean flag = false;//编辑设备分类
//
//    private String[] array_productId={"PRO003","PRO002","PRO001","PRO004","PRO008","PRO007","PRO006","PRO005","PRO009"};
//    private String token;
    private DeviceListAdapter adapter;
    private String token;
    private List<SecneSectionBean> mList = new ArrayList<>();
    private WebSocketConnection mConnection;
    private HomeReceiver homeReceiver;
    private String all_data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device, container, false);
        unbinder = ButterKnife.bind(this, view);
        token = (String) SPUtils.get(getActivity(), "token", "");
        mConnection = SingleWebSocketConnection.getInstance();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DCPP");
        intentFilter.addAction("DAPP");
        homeReceiver = new HomeReceiver();
        getActivity().registerReceiver(homeReceiver, intentFilter);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvDevice.setLayoutManager(manager);
        adapter = new DeviceListAdapter(R.layout.test_device_item, R.layout.add_scene_device_header, mList);
        rvDevice.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SecneSectionBean secneSectionBean = mList.get(position);
                boolean ishead = secneSectionBean.isIshead();
                if (!ishead) {
                    DeviceList deviceList = secneSectionBean.t;
                    String productId = deviceList.productId;
                    String roomId = deviceList.roomId;
                    String deviceId=deviceList.id;
                    if (!productId.contains("PRO006")){
                        startActivity(new Intent(getActivity(), DeviceDetailActivity.class)
                                .putExtra("productId", productId)
                                .putExtra("all_data", all_data)
                                .putExtra("roomId", roomId)
                                .putExtra("deviceId",deviceId));
                    }else {
                        startActivity(new Intent(getActivity(), ConverterListActivity.class).putExtra("hwbDeviceId",deviceId).putExtra("name",deviceList.name));
                    }
                }
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                SecneSectionBean secneSectionBean = mList.get(position);
                boolean ishead = secneSectionBean.isIshead();
                DeviceList deviceList = secneSectionBean.t;
                String deviceid = deviceList.id;
                List<DeviceList.DevAttList> devAttList = deviceList.devAttList;
                List<DeviceList.DevActList> devActList = deviceList.devActList;
                if (!ishead) {
                    switch (view.getId()) {
                        case R.id.iv_device_left:
                            if (deviceList.productId.equals("PRO003004001")){
                                //智能门锁
                                int value = devAttList.get(0).value;
                                if (value==0){
                                    //开
                                    for (int i = 0; i < devActList.size(); i++) {
                                        int comNo = devActList.get(i).comNo;
                                        if (comNo==1){
                                            String dactid =devActList.get(i).id;
                                            String param = devActList.get(i).param;
                                            mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\""+param+"\"}");
                                        }
                                    }
                                }else {
                                    //关
                                    for (int i = 0; i < devActList.size(); i++) {
                                        int comNo = devActList.get(i).comNo;
                                        if (comNo==0){
                                            String dactid =devActList.get(i).id;
                                            String param = devActList.get(i).param;
                                            mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\""+param+"\"}");
                                        }
                                    }
                                }
                            }else {
                                //开关
                                for (int i = 0; i < devAttList.size(); i++) {
                                    int value = devAttList.get(i).value;
                                    int port_left = devAttList.get(i).port;
                                    if (value == 0&&port_left==1) {
                                        //左开
                                        for (int j = 0; j < devActList.size(); j++) {
                                            int port = devActList.get(j).port;
                                            int comNo = devActList.get(j).comNo;
                                            if (port == 1 && comNo == 1) {
                                                String dactid = devActList.get(j).id;
                                                mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"201\",\"sdid\":\"" + deviceid + "\",\"dactid\":\"" + dactid + "\",\"param\":\"\"}");
                                                return;
                                            }
                                        }
                                    } else if (value == 1&&port_left==1) {
                                        //左关
                                        for (int j = 0; j < devActList.size(); j++) {
                                            int port = devActList.get(j).port;
                                            int comNo = devActList.get(j).comNo;
                                            if (port == 1 && comNo == 0) {
                                                String dactid = devActList.get(j).id;
                                                mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"201\",\"sdid\":\"" + deviceid + "\",\"dactid\":\"" + dactid + "\",\"param\":\"\"}");
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case R.id.iv_device_middle:
                            for (int i = 0; i < devAttList.size(); i++) {
                                int value = devAttList.get(i).value;
                                int port_middle = devAttList.get(i).port;
                                if (value == 0&&port_middle==2) {
                                    //中开
                                    for (int j = 0; j < devActList.size(); j++) {
                                        int port = devActList.get(j).port;
                                        int comNo = devActList.get(j).comNo;
                                        if (port == 2 && comNo == 1) {
                                            String dactid = devActList.get(j).id;
                                            mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"201\",\"sdid\":\"" + deviceid + "\",\"dactid\":\"" + dactid + "\",\"param\":\"\"}");
                                            return;
                                        }
                                    }
                                } else if ((value == 1&&port_middle==2) ){
                                    //中关
                                    for (int j = 0; j < devActList.size(); j++) {
                                        int port = devActList.get(j).port;
                                        int comNo = devActList.get(j).comNo;
                                        if (port == 2 && comNo == 0) {
                                            String dactid = devActList.get(j).id;
                                            mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"201\",\"sdid\":\"" + deviceid + "\",\"dactid\":\"" + dactid + "\",\"param\":\"\"}");
                                            return;
                                        }
                                    }
                                }
                            }
                            break;
                        case R.id.iv_device_right:
                            for (int i = 0; i < devAttList.size(); i++) {
                                int value = devAttList.get(i).value;
                                int port_right = devAttList.get(i).port;
                                if (value == 0&&port_right==3) {
                                    //右开
                                    for (int j = 0; j < devActList.size(); j++) {
                                        int port = devActList.get(j).port;
                                        int comNo = devActList.get(j).comNo;
                                        if (port == 3 && comNo == 1) {
                                            String dactid = devActList.get(j).id;
                                            mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"201\",\"sdid\":\"" + deviceid + "\",\"dactid\":\"" + dactid + "\",\"param\":\"\"}");
                                            return;
                                        }
                                    }
                                } else if (value==1&&port_right==3){
                                    //右关
                                    for (int j = 0; j < devActList.size(); j++) {
                                        int port = devActList.get(j).port;
                                        int comNo = devActList.get(j).comNo;
                                        if (port == 3 && comNo == 0) {
                                            String dactid = devActList.get(j).id;
                                            mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"" + token + "\",\"token\":\"" + token + "\",\"oper\":\"201\",\"sdid\":\"" + deviceid + "\",\"dactid\":\"" + dactid + "\",\"param\":\"\"}");
                                            return;
                                        }
                                    }
                                }
                            }
                            break;
                        case R.id.iv_device_warn_s:
//                            SmartToast.show("报警_声");
                            for (int i = 0; i < devActList.size(); i++) {
                                String param = devActList.get(i).param;
                                if ("10000A0000".equals(param)){
                                    String dactid = devActList.get(i).id;
                                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\""+param+"\"}");
                                }
                            }
                            break;
                        case R.id.iv_device_warn_g:
//                            SmartToast.show("报警_光");
                            for (int i = 0; i < devActList.size(); i++) {
                                String param = devActList.get(i).param;
                                if ("14000A0000".equals(param)){
                                    String dactid = devActList.get(i).id;
                                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\""+param+"\"}");
                                }
                            }
                            break;
                        case R.id.iv_device_warn_stop:
//                            SmartToast.show("报警_停止");
                            for (int i = 0; i < devActList.size(); i++) {
                                String param = devActList.get(i).param;
                                if ("0000000000".equals(param)){
                                    String dactid = devActList.get(i).id;
                                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\""+param+"\"}");
                                }
                            }
                            break;
                        case R.id.iv_device_cl_close:
//                            SmartToast.show("窗帘_关");
                            for (int i = 0; i < devActList.size(); i++) {
                                int comNo = devActList.get(i).comNo;
                                if (comNo==0){
                                    String dactid =devActList.get(i).id;
                                    String param = devActList.get(i).param;
                                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\""+param+"\"}");
                                }
                            }
                            break;
                        case R.id.iv_device_cl_open:
//                            SmartToast.show("窗帘_开");
                            for (int i = 0; i < devActList.size(); i++) {
                                int comNo = devActList.get(i).comNo;
                                if (comNo==1){
                                    String dactid =devActList.get(i).id;
                                    String param = devActList.get(i).param;
                                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\""+param+"\"}");
                                }
                            }
                            break;
                        case R.id.iv_device_cl_stop:
//                            SmartToast.show("窗帘_停止");
                            for (int i = 0; i < devActList.size(); i++) {
                                int comNo = devActList.get(i).comNo;
                                if (comNo==2){
                                    String dactid =devActList.get(i).id;
                                    String param = devActList.get(i).param;
                                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\""+param+"\"}");
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        deviceList();
//        unbinder = ButterKnife.bind(this, view);
//        token = (String) SPUtils.get(getActivity(),"token","");
//        mList.add(new CommentClassyBean(){{setIcon(R.drawable.af);setTitle("安防设备");}});
//        mList.add(new CommentClassyBean(){{setIcon(R.drawable.dengpao);setTitle("照明开关");}});
//        mList.add(new CommentClassyBean(){{setIcon(R.drawable.znzj);setTitle("智能主机");}});
//        mList.add(new CommentClassyBean(){{setIcon(R.drawable.hjjc);setTitle("环境监测");}});
//        mList.add(new CommentClassyBean(){{setIcon(R.drawable.ylyl);setTitle("医疗养老");}});
//        mList.add(new CommentClassyBean(){{setIcon(R.drawable.zngj);setTitle("智能管家");}});
//        mList.add(new CommentClassyBean(){{setIcon(R.drawable.jd);setTitle("家电控制");}});
//        mList.add(new CommentClassyBean(){{setIcon(R.drawable.cl);setTitle("窗帘开关");}});

//        roomList();
//        mMyadapter = new Myadapter();
//        gvCommonClassify.setAdapter(mMyadapter);
//        device_adapter = new MyDeviceadapter();
//        gvDeviceClassify.setAdapter(device_adapter);
//        handleClicker();
        
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        getActivity().unregisterReceiver(homeReceiver);
    }
    private void deviceList() {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.ALLDEVICELIST))
                .addParams("token", token)
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
                        if (errcode.equals("0")) {
                            all_data=response;
                            if (mList.size()>0){
                                mList.clear();
                            }
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
    class HomeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String message = intent.getStringExtra("message");
            if (action.equals("DCPP")) {
                try {
                    JSONObject jsonObject = new JSONObject(message);
                    String ecode = jsonObject.getString("ecode");
                    if (!ecode.equals("0")) {
                        String s = EcodeValue.resultEcode(ecode);
                        SmartToast.show(s);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                parseData(message);
            } else if (action.equals("DAPP")) {
                try {
                    JSONObject jsonObject = new JSONObject(message);
                    String sdid = jsonObject.getString("sdid");
                    deviceList();
//                    if (sdid.equals(deviceid)){
//                        DappBean dappBean = new Gson().fromJson(message, DappBean.class);
//                        List<DappBean.Atts> atts = dappBean.atts;
//                        for (int i = 0; i < atts.size(); i++) {
//                            int port = atts.get(i).port;
//                            int value = atts.get(i).value;
//                            if (port==1){
//                                value_left=value;
//                                setViewByAtt(value_left,ivKgLeft,tvKgLeft);
//                            }else if (port==2){
//                                value_middle=value;
//                                setViewByAtt(value_middle,ivKgMiddle,tvKgMiddle);
//                            }else if (port==3){
//                                value_right=value;
//                                setViewByAtt(value_right,ivKgRight,tvKgRight);
//                            }
//                        }
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
//    private void roomList() {
//        OkHttpUtils
//                .post()
//                .url(InterfaceManager.getInstance().getURL(ROOMLIST))
//                .addParams("token",token)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        RoomListBean roomListBean = new Gson().fromJson(response, RoomListBean.class);
//                        List<RoomListBean.RoomList> roomList = roomListBean.roomList;
//                        if (mList_device.size()>0){
//                            mList_device.clear();
//                        }
//                        mList_device.addAll(roomList);
//                        device_adapter.notifyDataSetChanged();
//                    }
//                });
//    }
//
//    private void handleClicker() {
//        gvDeviceClassify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i == mList_device.size()) {
//                    startActivity(new Intent(getActivity(), AddRoomActivity.class));
//                } else {
//                    startActivity(new Intent(getActivity(), RoomActivity.class));
//                }
//            }
//        });
//        gvCommonClassify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i==8){
//                    startActivity(new Intent(getActivity(), CameraListActivity.class));
//                }else if (i==6){
//                    //跳转遥控中心
//                    startActivity(new Intent(getActivity(),YaoKongCenterActivity.class).putExtra("productId",array_productId[i]));
//                }else if (i==4){
//                    //医疗
//                    isRegister();
//                }else if (i==2){
//                    String title = mList.get(i).getTitle();
//                    int icon = mList.get(i).getIcon();
//                    startActivity(new Intent(getActivity(), ZhiNengzjActivity.class)
//                            .putExtra("title",title)
//                            .putExtra("icon",icon)
//                            .putExtra("productId",array_productId[i]));
//                }
//                else {
//                    String title = mList.get(i).getTitle();
//                    int icon = mList.get(i).getIcon();
//                    startActivity(new Intent(getActivity(),ChoiceDeviceActivity.class)
//                            .putExtra("title",title)
//                            .putExtra("icon",icon)
//                            .putExtra("productId",array_productId[i]));
//                }
//            }
//        });
//
//    }
//
//    private void isRegister() {
////        SPUtils.put(LoginActivity.this, "mobilephone", mPhone);
//        String phone= (String) SPUtils.get(getActivity(),"mobilephone","");
//        OkHttpUtils
//                .post()
//                .url(InterfaceManager.getInstance().getURL(InterfaceManager.YILIAOISREGISTER))
//                .addParams("phone",phone)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        try {
//                            JSONObject jsonObject=new JSONObject(response);
//                            String errcode = jsonObject.getString("errcode");
//                            if (errcode.equals("0")){
//                                startActivity(new Intent(getActivity(), HealthActivity.class));
////                                startActivity(new Intent(getActivity(),YiLiaoActivity.class));
//                            }else {
//                                startActivity(new Intent(getActivity(),YiLiaoActivity.class));
////                                startActivity(new Intent(getActivity(), HealthActivity.class));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
//
//    @OnClick(R.id.tv_device_edit)
//    public void onViewClicked() {
//        if (flag) {
//            tvDeviceEdit.setText("编辑");
//            flag = false;
//        } else {
//            flag = true;
//            tvDeviceEdit.setText("完成");
//        }
//        device_adapter.notifyDataSetChanged();
//    }
//
//    class Myadapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return mList.size() + 1 == 6 ? 6 : mList.size() + 1;
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return mList.get(i);
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return i;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//
//            view = LayoutInflater.from(getActivity()).inflate(R.layout.device_gv_item, null);
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
//
//            return view;
//        }
//    }
//
//    private DelDialog delDialog;
//
//    class MyDeviceadapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return mList_device.size() + 1;
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return mList_device.get(i);
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return i;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//
//            view = LayoutInflater.from(getActivity()).inflate(R.layout.device_gv_edit_item, null);
//            ImageView imageView = (ImageView) view.findViewById(R.id.gridview_item);
//            ImageView iv_del = view.findViewById(R.id.iv_device_del);
//            TextView tv_name = view.findViewById(R.id.tv_name);
//            if (flag) {
//                iv_del.setVisibility(View.VISIBLE);
//            } else {
//                iv_del.setVisibility(View.GONE);
//            }
//            if (mList_device.size() == i) {
//                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                Glide.with(getActivity()).load(R.drawable.home_add).into(imageView);
//                tv_name.setText("其他");
//                iv_del.setVisibility(View.GONE);
////                if (i == 10) {
////                    imageView.setVisibility(View.GONE);
////                }
//            } else {
//                tv_name.setText(mList_device.get(i).name);
//                Glide.with(getActivity()).load(IconByName.drawableByNameRoom(mList_device.get(i).icon)).into(imageView);
//            }
//            iv_del.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    delDialog = new DelDialog(getActivity(), R.style.MyDialogStyle, clicker, "是否删除房间？");
//                    delDialog.show();
//                }
//            });
//            return view;
//        }
//    }
//
//    public View.OnClickListener clicker = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.tv_del_ok:
//                    SmartToast.show("删除");
//                    delDialog.dismiss();
//                    break;
//            }
//        }
//    };
}
