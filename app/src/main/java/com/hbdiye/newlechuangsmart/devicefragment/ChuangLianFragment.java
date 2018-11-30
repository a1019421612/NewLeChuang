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
import android.widget.ImageView;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.SingleWebSocketConnection;
import com.hbdiye.newlechuangsmart.bean.ChuanglianBean;
import com.hbdiye.newlechuangsmart.bean.DappBean;
import com.hbdiye.newlechuangsmart.fragment.BaseFragment;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.tavendo.autobahn.WebSocketConnection;
import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.DEVICEDETAIL;

public class ChuangLianFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_chuanglian_name;
    private ImageView iv_chuanglian_open, iv_chuanglian_stop, iv_chuanglian_close;

    private String token;
    private String deviceid;
    private ChuanglianBean chuanglianBean;

    private WebSocketConnection mConnection;
    private HomeReceiver homeReceiver;

    public static ChuangLianFragment newInstance(String page) {
        Bundle args = new Bundle();

//        args.putInt(ARGS_PAGE, page);
        args.putString("deviceid", page);
        ChuangLianFragment fragment = new ChuangLianFragment();
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
        View view = inflater.inflate(R.layout.fragment_chuanglian, container, false);
        tv_chuanglian_name = view.findViewById(R.id.tv_chuanglian_name);
        iv_chuanglian_close = view.findViewById(R.id.iv_chuanglian_close);
        iv_chuanglian_stop = view.findViewById(R.id.iv_chuanglian_stop);
        iv_chuanglian_open = view.findViewById(R.id.iv_chuanglian_open);

        iv_chuanglian_close.setOnClickListener(this);
        iv_chuanglian_stop.setOnClickListener(this);
        iv_chuanglian_open.setOnClickListener(this);
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
                            tv_chuanglian_name.setText(name);
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
            case R.id.iv_chuanglian_close://关
                for (int i = 0; i < devActList.size(); i++) {
                    int comNo = devActList.get(i).comNo;
                    if (comNo==0){
                        String dactid =devActList.get(i).id;
                        String param = devActList.get(i).param;
                        mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\""+param+"\"}");
                    }
                }
                break;
            case R.id.iv_chuanglian_stop://停
                for (int i = 0; i < devActList.size(); i++) {
                    int comNo = devActList.get(i).comNo;
                    if (comNo==2){
                        String dactid =devActList.get(i).id;
                        String param = devActList.get(i).param;
                        mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\""+param+"\"}");
                    }
                }
                break;
            case R.id.iv_chuanglian_open://开  "{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\"%@\",\"token\":\"%@\",\"oper\":\"201\",\"sdid\":\"%@\",\"dactid\":\"%@\",\"param\":\"%@\"}"
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
