package com.hbdiye.newlechuangsmart.devicefragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.SingleWebSocketConnection;
import com.hbdiye.newlechuangsmart.bean.ChuanglianBean;
import com.hbdiye.newlechuangsmart.bean.DeviceClassyBean;
import com.hbdiye.newlechuangsmart.bean.MenCiBean;
import com.hbdiye.newlechuangsmart.fragment.BaseFragment;
import com.hbdiye.newlechuangsmart.fragment.DeviceListFragment;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.zhouyou.view.seekbar.SignSeekBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import de.tavendo.autobahn.WebSocketConnection;
import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.DEVICEDETAIL;

public class DianjiFragment extends BaseFragment implements View.OnClickListener {
    private SignSeekBar signSeekBar;
    private TextView tv_dj_name;
    private ImageView iv_dj_open, iv_dj_stop, iv_dj_close;
//    private DeviceClassyBean.ProductList productList;
    private WebSocketConnection mConnection;
    private String token;
    private HomeReceiver homeReceiver;
//    private HomeReceiver homeReceiver;
    private int value;
    private String deviceid;
    private ChuanglianBean chuanglianBean;

    public static DianjiFragment newInstance(String page) {
        Bundle args = new Bundle();

//        args.putInt(ARGS_PAGE, page);
        args.putString("deviceid", page);
        DianjiFragment fragment = new DianjiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deviceid = getArguments().getString("deviceid");
        token = (String) SPUtils.get(getActivity(), "token", "");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dianji,container,false);
        tv_dj_name = view.findViewById(R.id.tv_dj_name);
        iv_dj_close = view.findViewById(R.id.iv_dj_close);
        iv_dj_stop = view.findViewById(R.id.iv_dj_stop);
        iv_dj_open = view.findViewById(R.id.iv_dj_open);

        iv_dj_close.setOnClickListener(this);
        iv_dj_stop.setOnClickListener(this);
        iv_dj_open.setOnClickListener(this);
        mConnection = SingleWebSocketConnection.getInstance();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DCPP");
        intentFilter.addAction("DAPP");
        homeReceiver = new HomeReceiver();
        getActivity().registerReceiver(homeReceiver, intentFilter);
//        tv_name = view.findViewById(R.id.tv_dj_name);
//        signSeekBar = (SignSeekBar) view.findViewById(R.id.seek_bar);
//        signSeekBar.setOnProgressChangedListener(new SignSeekBar.OnProgressChangedListener() {
//            @Override
//            public void onProgressChanged(SignSeekBar signSeekBar, int progress, float progressFloat, boolean fromUser) {
//                //fromUser 表示是否是用户触发 是否是用户touch事件产生
//                String s = String.format(Locale.CHINA, "onChanged int:%d, float:%.1f", progress, progressFloat);
////                progressText.setText(s);
//            }
//
//            @Override
//            public void getProgressOnActionUp(SignSeekBar signSeekBar, int progress, float progressFloat) {
//                String s = String.format(Locale.CHINA, "onActionUp int:%d, float:%.1f", progress, progressFloat);
////                progressText.setText(s);
//            }
//
//            @Override
//            public void getProgressOnFinally(SignSeekBar signSeekBar, int progress, float progressFloat, boolean fromUser) {
//                String s = String.format(Locale.CHINA, "onFinally int:%d, float:%.1f", progress, progressFloat);
//                Log.e("sss", progress + "℃");
//            }
//        });
//
//        mConnection = SingleWebSocketConnection.getInstance();
//        token = (String) SPUtils.get(getActivity(),"token","");
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("DCPP");
//        homeReceiver = new HomeReceiver();
//        getActivity().registerReceiver(homeReceiver, intentFilter);
        return view;
    }
    @Override
    protected void onFragmentFirstVisible() {
        OkHttpUtils.post()
                .url(InterfaceManager.getInstance().getURL(DEVICEDETAIL))
                .addParams("token", token)
                .addParams("deviceId", deviceid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        chuanglianBean = new Gson().fromJson(response, ChuanglianBean.class);
                        if (chuanglianBean.errcode.equals("0")) {
                            String name = chuanglianBean.data.name;
                            tv_dj_name.setText(name);
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if (chuanglianBean == null) {
            return;
        }
        List<ChuanglianBean.Data.DevActList> devActList = chuanglianBean.data.devActList;
        switch (v.getId()) {
            case R.id.iv_dj_close://关
                for (int i = 0; i < devActList.size(); i++) {
                    int comNo = devActList.get(i).comNo;
                    if (comNo==0){
                        String dactid =devActList.get(i).id;
                        String param = devActList.get(i).param;
                        mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\""+param+"\"}");
                    }
                }
                break;
            case R.id.iv_dj_stop://停
                for (int i = 0; i < devActList.size(); i++) {
                    int comNo = devActList.get(i).comNo;
                    if (comNo==2){
                        String dactid =devActList.get(i).id;
                        String param = devActList.get(i).param;
                        mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\""+param+"\"}");
                    }
                }
                break;
            case R.id.iv_dj_open://开  "{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"%@\",\"token\":\"%@\",\"oper\":\"201\",\"sdid\":\"%@\",\"dactid\":\"%@\",\"param\":\"%@\"}"
                for (int i = 0; i < devActList.size(); i++) {
                    int comNo = devActList.get(i).comNo;
                    if (comNo==1){
                        String dactid =devActList.get(i).id;
                        String param = devActList.get(i).param;
                        mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\""+param+"\"}");
                    }
                }
                break;
        }
    }

    //    class HomeReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            String message = intent.getStringExtra("message");
//            if (action.equals("DCPP")) {
//                try {
//                    JSONObject jsonObject=new JSONObject(message);
//                    String ecode = jsonObject.getString("ecode");
//                    String s = EcodeValue.resultEcode(ecode);
//                    SmartToast.show(s);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
////                parseData(message);
//            }
//        }
//    }
class HomeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String message = intent.getStringExtra("message");
        if (action.equals("DCPP")) {
            try {
                JSONObject jsonObject=new JSONObject(message);
                String sdid = jsonObject.getString("sdid");
//                    if (sdid.equals(deviceid)){
//                        String ecode = jsonObject.getString("ecode");
//                        if (!ecode.equals("0")){
//                            String s = EcodeValue.resultEcode(ecode);
//                            SmartToast.show(s);
//                        }
//                    }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//                parseData(message);
        }else if (action.equals("DAPP")){
            try {
                JSONObject jsonObject=new JSONObject(message);
                String sdid = jsonObject.getString("sdid");
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
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(homeReceiver);
    }
}
