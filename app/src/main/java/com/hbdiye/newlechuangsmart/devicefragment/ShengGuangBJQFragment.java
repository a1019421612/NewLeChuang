package com.hbdiye.newlechuangsmart.devicefragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.SingleWebSocketConnection;
import com.hbdiye.newlechuangsmart.bean.ChuanglianBean;
import com.hbdiye.newlechuangsmart.bean.ShengGuangBjqBean;
import com.hbdiye.newlechuangsmart.fragment.BaseFragment;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.tavendo.autobahn.WebSocketConnection;
import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.DEVICEDETAIL;

public class ShengGuangBJQFragment extends BaseFragment implements View.OnClickListener {
    private String deviceid;
    private String token;
    private WebSocketConnection mConnection;
    private HomeReceiver homeReceiver;
    private TextView tv_shengguang_name;
    private LinearLayout ll_sg_sy, ll_sg_sg, ll_sg_stop;
    private ShengGuangBjqBean shengGuangBjqBean;

    public static ShengGuangBJQFragment newInstance(String page) {
        Bundle args = new Bundle();

//        args.putInt(ARGS_PAGE, page);
        args.putString("deviceid", page);
        ShengGuangBJQFragment fragment = new ShengGuangBJQFragment();
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
        View view = inflater.inflate(R.layout.fragment_shengguang, container, false);
        tv_shengguang_name = view.findViewById(R.id.tv_shengguang_name);
        ll_sg_sg = view.findViewById(R.id.ll_shengguang_guang);
        ll_sg_sy = view.findViewById(R.id.ll_shengguang_sheng);
        ll_sg_stop = view.findViewById(R.id.ll_shengguang_stop);
        ll_sg_stop.setOnClickListener(this);
        ll_sg_sg.setOnClickListener(this);
        ll_sg_sy.setOnClickListener(this);
        mConnection = SingleWebSocketConnection.getInstance();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DCPP");
        intentFilter.addAction("DAPP");
        homeReceiver = new HomeReceiver();
        getActivity().registerReceiver(homeReceiver, intentFilter);
        return view;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
//        SmartToast.show(deviceid);
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
//                        chuanglianBean = new Gson().fromJson(response, ChuanglianBean.class);
//                        if (chuanglianBean.errcode.equals("0")) {
//                            String name = chuanglianBean.data.name;
//                            tv_chuanglian_name.setText(name);
//                        }
                        shengGuangBjqBean = new Gson().fromJson(response, ShengGuangBjqBean.class);
                        if (shengGuangBjqBean.errcode.equals("0")) {
                            String name = shengGuangBjqBean.data.name;
                            tv_shengguang_name.setText(name);
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_shengguang_guang:
                sendInfo("14000A0000");
                break;
            case R.id.ll_shengguang_sheng:
                sendInfo("10000A0000");
                break;
            case R.id.ll_shengguang_stop:
                sendInfo("0000000000");
                break;
        }
    }

    private void sendInfo(String vaule) {
        List<ShengGuangBjqBean.Data.DevActList> devActList = shengGuangBjqBean.data.devActList;
        for (int i = 0; i < devActList.size(); i++) {
            String param = devActList.get(i).param;
            if (vaule.equals(param)){
                String dactid = devActList.get(i).id;
                mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\""+param+"\"}");
            }
        }
    }

    class HomeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String message = intent.getStringExtra("message");
            if (action.equals("DCPP")) {
                try {
                    JSONObject jsonObject = new JSONObject(message);
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
            } else if (action.equals("DAPP")) {
                try {
                    JSONObject jsonObject = new JSONObject(message);
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
