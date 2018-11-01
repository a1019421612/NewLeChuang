package com.hbdiye.newlechuangsmart.devicefragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.hbdiye.newlechuangsmart.bean.DeviceClassyBean;
import com.hbdiye.newlechuangsmart.bean.KaiGuanBean;
import com.hbdiye.newlechuangsmart.fragment.BaseFragment;
import com.hbdiye.newlechuangsmart.fragment.DeviceListFragment;
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

public class KaiGuanThreeFragment extends BaseFragment {
    @BindView(R.id.iv_kg_left)
    ImageView ivKgLeft;
    @BindView(R.id.tv_kg_left)
    TextView tvKgLeft;
    @BindView(R.id.ll_kg_left)
    LinearLayout llKgLeft;
    @BindView(R.id.iv_kg_middle)
    ImageView ivKgMiddle;
    @BindView(R.id.tv_kg_middle)
    TextView tvKgMiddle;
    @BindView(R.id.ll_kg_middle)
    LinearLayout llKgMiddle;
    @BindView(R.id.iv_kg_right)
    ImageView ivKgRight;
    @BindView(R.id.tv_kg_right)
    TextView tvKgRight;
    @BindView(R.id.ll_kg_right)
    LinearLayout llKgRight;
    private String data;
    private String proId;
    private TextView tv_name;
    private DeviceClassyBean.DeviceList productList;
    private Unbinder unbinder;

    private WebSocketConnection mConnection;
    private String token;
    private int value_left;
    private int value_middle;
    private int value_right;

    private HomeReceiver homeReceiver;
    private String deviceid;
    private KaiGuanBean kaiGuanBean;

    public static KaiGuanThreeFragment newInstance(String page) {
        Bundle args = new Bundle();

//        args.putInt(ARGS_PAGE, page);
        args.putString("deviceid", page);
        KaiGuanThreeFragment fragment = new KaiGuanThreeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deviceid = getArguments().getString("deviceid");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kaiguan_three, container, false);
        unbinder = ButterKnife.bind(this, view);
        tv_name = view.findViewById(R.id.tv_kg_three);
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
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String errcode = jsonObject.getString("errcode");
                            if (errcode.equals("0")){
                                kaiGuanBean = new Gson().fromJson(response, KaiGuanBean.class);
                                String name = kaiGuanBean.data.name;
                                tv_name.setText(name);

                                KaiGuanBean.Data.DevAttList left = kaiGuanBean.data.devAttList.get(0);
                                KaiGuanBean.Data.DevAttList middle = kaiGuanBean.data.devAttList.get(1);
                                KaiGuanBean.Data.DevAttList right = kaiGuanBean.data.devAttList.get(2);
                                String left_name = left.name;
                                tvKgLeft.setText(left_name);
                                String middle_name = middle.name;
                                tvKgMiddle.setText(middle_name);
                                String right_name = right.name;
                                tvKgRight.setText(right_name);
                                value_left = left.value;
                                setViewByAtt(value_left,ivKgLeft,tvKgLeft);
                                value_middle=middle.value;
                                setViewByAtt(value_middle,ivKgMiddle,tvKgMiddle);
                                value_right=right.value;
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

    @OnClick({R.id.ll_kg_left, R.id.ll_kg_middle, R.id.ll_kg_right})
    public void onViewClicked(View view) {
        if (kaiGuanBean==null){
            return;
        }
        switch (view.getId()) {
            case R.id.ll_kg_left:
                if (value_left==0){
                    //左开动作
                   String dactid= kaiGuanBean.data.devActList.get(0).id;
                   String sss="{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}";
                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
                }else {
//                    左关动作
                    String dactid= kaiGuanBean.data.devActList.get(1).id;
                    String sss="{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}";
                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
                }
                break;
            case R.id.ll_kg_middle:
                if (value_middle==0){
                    //中开动作
                    String dactid= kaiGuanBean.data.devActList.get(2).id;
                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
                }else {
//                    中关动作
                    String dactid= kaiGuanBean.data.devActList.get(3).id;
                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
                }
                break;
            case R.id.ll_kg_right:
                if (value_right==0){
                    //右开动作
                    String dactid= kaiGuanBean.data.devActList.get(4).id;
                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
                }else {
//                    右关动作
                    String dactid= kaiGuanBean.data.devActList.get(5).id;
                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
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
                                value_middle=value;
                                setViewByAtt(value_middle,ivKgMiddle,tvKgMiddle);
                            }else if (port==3){
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
