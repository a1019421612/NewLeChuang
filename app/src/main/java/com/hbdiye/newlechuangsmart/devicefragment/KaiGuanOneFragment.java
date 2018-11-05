package com.hbdiye.newlechuangsmart.devicefragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.SingleWebSocketConnection;
import com.hbdiye.newlechuangsmart.bean.DappBean;
import com.hbdiye.newlechuangsmart.bean.KaiGuanBean;
import com.hbdiye.newlechuangsmart.fragment.BaseFragment;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.ClassyIconByProId;
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

public class KaiGuanOneFragment extends BaseFragment {

    private String deviceid;
    private String token;
    private TextView tv_name,tv_att_name;
    private ImageView iv_att;
    private LinearLayout ll_kaiguan_one;
    private WebSocketConnection mConnection;
    private HomeReceiver homeReceiver;
    private int value_left;
    private KaiGuanBean kaiGuanBean;

    public static KaiGuanOneFragment newInstance(String page) {
        Bundle args = new Bundle();
        args.putString("deviceid", page);
        KaiGuanOneFragment fragment = new KaiGuanOneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deviceid = getArguments().getString("deviceid");
        token = (String) SPUtils.get(getActivity(),"token","");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_kaiguan_one,container,false);
        tv_name=view.findViewById(R.id.tv_kaiguan_name);
        tv_att_name=view.findViewById(R.id.tv_kaiguan_one);
        iv_att=view.findViewById(R.id.iv_kaiguan_one);
        ll_kaiguan_one=view.findViewById(R.id.ll_kaiguan_one);
        mConnection = SingleWebSocketConnection.getInstance();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DCPP");
        intentFilter.addAction("DAPP");
        homeReceiver = new HomeReceiver();
        getActivity().registerReceiver(homeReceiver, intentFilter);
        ll_kaiguan_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kaiGuanBean==null){
                    return;
                }
                if (value_left==0){
//                    //左开动作
                    String dactid = kaiGuanBean.data.devActList.get(0).id;
//                   String sss="{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}";
                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
                }else {
//                    左关动作
                    String dactid = kaiGuanBean.data.devActList.get(1).id;
//                    String sss="{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}";
                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
                }
            }
        });
        return view;
    }

    @Override
    protected void onFragmentFirstVisible() {
        Log.e("WW","WW");
        OkHttpUtils.post()
                .url(InterfaceManager.getInstance().getURL(DEVICEDETAIL))
                .addParams("token",token)
                .addParams("deviceId",deviceid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        Log.e("sss",e.toString());
                        SmartToast.show("网络连接错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("sss",response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String errcode = jsonObject.getString("errcode");
                            if (errcode.equals("0")){
                                kaiGuanBean = new Gson().fromJson(response, KaiGuanBean.class);
                                String name = kaiGuanBean.data.name;
                                tv_name.setText(name);

                                KaiGuanBean.Data.DevAttList devAttList = kaiGuanBean.data.devAttList.get(0);
                                value_left = devAttList.value;
                                String left_name = devAttList.name;
                                tv_att_name.setText(left_name);
                                setViewByAtt(value_left,iv_att,tv_att_name);
                            }else {
                                SmartToast.show(ClassyIconByProId.mesByStatus(errcode));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
    public void setViewByAtt(int value, ImageView iv, TextView tv){
        if (value==0){
            iv.setImageResource(R.drawable.mohu);
            tv.setTextColor(getResources().getColor(R.color.detail_text));
        }else {
            iv.setImageResource(R.drawable.kaiguan);
            tv.setTextColor(getResources().getColor(R.color.bar_text_sel));
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
                    if (sdid.equals(deviceid)){
                        String ecode = jsonObject.getString("ecode");
                        if (!ecode.equals("0")){
                            String s = EcodeValue.resultEcode(ecode);
                            SmartToast.show(s);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                parseData(message);
            }else if (action.equals("DAPP")){
                try {
                    JSONObject jsonObject=new JSONObject(message);
                    String sdid = jsonObject.getString("sdid");
                    if (sdid.equals(deviceid)){
                        DappBean dappBean = new Gson().fromJson(message, DappBean.class);
                        List<DappBean.Atts> atts = dappBean.atts;
                        for (int i = 0; i < atts.size(); i++) {
                            int port = atts.get(i).port;
                            value_left = atts.get(i).value;
                            if (port==1){
                                setViewByAtt(value_left,iv_att,tv_att_name);
                            }
                        }
                    }
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
