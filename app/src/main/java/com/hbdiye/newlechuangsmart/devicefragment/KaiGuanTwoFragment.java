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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.tavendo.autobahn.WebSocketConnection;
import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.DEVICEDETAIL;

public class KaiGuanTwoFragment extends BaseFragment {
    @BindView(R.id.tv_kg_two)
    TextView tvKgTwo;
    @BindView(R.id.iv_kg_left)
    ImageView ivKgLeft;
    @BindView(R.id.tv_kg_left)
    TextView tvKgLeft;
    @BindView(R.id.ll_kg_left)
    LinearLayout llKgLeft;
    @BindView(R.id.iv_kg_right)
    ImageView ivKgRight;
    @BindView(R.id.tv_kg_right)
    TextView tvKgRight;
    @BindView(R.id.ll_kg_right)
    LinearLayout llKgRight;
    private String deviceid;
    private Unbinder unbinder;
    private WebSocketConnection mConnection;
    private String token;
    private int value_left;
    private int value_right;
    private HomeReceiver homeReceiver;
    private KaiGuanBean kaiGuanBean;
    private List<KaiGuanBean.Data.DevActList> devActList;

    public static KaiGuanTwoFragment newInstance(String page) {
        Bundle args = new Bundle();
        args.putString("deviceid", page);
        KaiGuanTwoFragment fragment = new KaiGuanTwoFragment();
        fragment.setArguments(args);
        Log.e("fragment", "newInstance");
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deviceid = getArguments().getString("deviceid");
        Log.e("fragment", "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kaiguan_two, container, false);
        unbinder = ButterKnife.bind(this, view);
        mConnection = SingleWebSocketConnection.getInstance();
        token = (String) SPUtils.get(getActivity(),"token","");
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
                .addParams("token",token)
                .addParams("deviceId",deviceid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        SmartToast.show("网络连接错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("sss",response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String errcode = jsonObject.getString("errcode");
                            if (errcode.equals("0")){
                                kaiGuanBean = new Gson().fromJson(response, KaiGuanBean.class);
                                String name = kaiGuanBean.data.name;
                                tvKgTwo.setText(name);

//                                KaiGuanBean.Data.DevAttList left = kaiGuanBean.data.devAttList.get(0);
//                                KaiGuanBean.Data.DevAttList right = kaiGuanBean.data.devAttList.get(1);
                                List<KaiGuanBean.Data.DevAttList> devAttList = kaiGuanBean.data.devAttList;
                                devActList = kaiGuanBean.data.devActList;
                                for (int i = 0; i < devAttList.size(); i++) {
                                    int port = devAttList.get(i).port;
                                    if (port==1){
                                        String left_name = devAttList.get(i).name;
                                        tvKgLeft.setText(left_name);
                                        value_left = devAttList.get(i).value;
                                    }else if (port==2){
                                        String right_name = devAttList.get(i).name;
                                        tvKgRight.setText(right_name);
                                        value_right=devAttList.get(i).value;
                                    }
                                }

                                setViewByAtt(value_left,ivKgLeft,tvKgLeft);
                                setViewByAtt(value_right,ivKgRight,tvKgRight);
                            }else {
                                SmartToast.show(ClassyIconByProId.mesByStatus(errcode));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    public void setViewByAtt(int value,ImageView iv,TextView tv){
        if (value==0){
            iv.setImageResource(R.drawable.mohu);
            tv.setTextColor(getResources().getColor(R.color.detail_text));
        }else {
            iv.setImageResource(R.drawable.kaiguan);
            tv.setTextColor(getResources().getColor(R.color.bar_text_sel));
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        getActivity().unregisterReceiver(homeReceiver);
    }

    @OnClick({R.id.ll_kg_left, R.id.ll_kg_right})
    public void onViewClicked(View view) {
        if (kaiGuanBean==null){
            return;
        }
        switch (view.getId()) {
            case R.id.ll_kg_left:
                if (value_left==0){
                    //左开动作
                    for (int i = 0; i < devActList.size(); i++) {
                        int port = devActList.get(i).port;
                        int comNo = devActList.get(i).comNo;
                        if (port==1){
                            if (comNo==1){
                                String dactid =devActList.get(i).id;
                                mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
                            }
                        }
                    }
//                    String dactid= kaiGuanBean.data.devActList.get(1).id;
//                    String sss="{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}";
//                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
                }else {
//                    左关动作
                    for (int i = 0; i < devActList.size(); i++) {
                        int port = devActList.get(i).port;
                        int comNo = devActList.get(i).comNo;
                        if (port==1){
                            if (comNo==0){
                                String dactid =devActList.get(i).id;
                                mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
                            }
                        }
                    }
//                    String dactid= kaiGuanBean.data.devActList.get(0).id;
//                    String sss="{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}";
//                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
                }
                break;
            case R.id.ll_kg_right:
                if (value_right==0){
                    //右开动作
                    for (int i = 0; i < devActList.size(); i++) {
                        int port = devActList.get(i).port;
                        int comNo = devActList.get(i).comNo;
                        if (port==2){
                            if (comNo==1){
                                String dactid =devActList.get(i).id;
                                mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
                            }
                        }
                    }
//                    String dactid= kaiGuanBean.data.devActList.get(3).id;
//                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
                }else {
//                    右关动作
                    for (int i = 0; i < devActList.size(); i++) {
                        int port = devActList.get(i).port;
                        int comNo = devActList.get(i).comNo;
                        if (port==2){
                            if (comNo==0){
                                String dactid =devActList.get(i).id;
                                mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
                            }
                        }
                    }
//                    String dactid= kaiGuanBean.data.devActList.get(2).id;
//                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
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
                    String ecode = jsonObject.getString("ecode");
                    if (!ecode.equals("0")){
                        String s = EcodeValue.resultEcode(ecode);
                        SmartToast.show(s);
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
                            int value = atts.get(i).value;
                            if (port==1){
                                value_left=value;
                                setViewByAtt(value_left,ivKgLeft,tvKgLeft);
                            }else if (port==2){
                                value_right=value;
                                setViewByAtt(value_right,ivKgRight,tvKgRight);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
