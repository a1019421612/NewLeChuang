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
import com.hbdiye.newlechuangsmart.bean.DeviceClassyBean;
import com.hbdiye.newlechuangsmart.fragment.BaseFragment;
import com.hbdiye.newlechuangsmart.fragment.DeviceListFragment;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.tavendo.autobahn.WebSocketConnection;

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
    private DeviceClassyBean.ProductList productList;
    private Unbinder unbinder;

    private WebSocketConnection mConnection;
    private String token;
    private String deviceId;
    private int value_left;
    private int value_middle;
    private int value_right;

    private HomeReceiver homeReceiver;

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
        homeReceiver = new HomeReceiver();
        getActivity().registerReceiver(homeReceiver, intentFilter);
        return view;
    }

    @Override
    protected void onFragmentFirstVisible() {
        Fragment parentFragment = (DeviceListFragment) getParentFragment();
        String data = ((DeviceListFragment) parentFragment).data;
        DeviceClassyBean deviceClassyBean = new Gson().fromJson(data, DeviceClassyBean.class);
        for (int i = 0; i < deviceClassyBean.productList.size(); i++) {
            if ("PRO002003001".equals(deviceClassyBean.productList.get(i).id)) {
                productList = deviceClassyBean.productList.get(i);
                String name = productList.name;
                tv_name.setText(name);
            }
        }
        List<DeviceClassyBean.ProductList.DeviceList.DevAttList> devAttList = productList.deviceList.get(0).devAttList;
        deviceId = devAttList.get(0).deviceId;
        value_left = devAttList.get(0).value;
        setViewByAtt(value_left,ivKgLeft,tvKgLeft);
        value_middle = devAttList.get(1).value;
        setViewByAtt(value_middle,ivKgMiddle,tvKgMiddle);
        value_right = devAttList.get(2).value;
        setViewByAtt(value_right,ivKgRight,tvKgRight);

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
        switch (view.getId()) {
            case R.id.ll_kg_left:
                if (value_left==0){
                    //左开动作
                   String dactid= productList.deviceList.get(0).devActList.get(0).id;
                   String sss="{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceId+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}";
                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceId+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
                }else {
//                    左关动作
                    String dactid= productList.deviceList.get(0).devActList.get(1).id;
                    String sss="{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceId+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}";
                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceId+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
                }
                break;
            case R.id.ll_kg_middle:
                if (value_middle==0){
                    //中开动作
                    String dactid= productList.deviceList.get(0).devActList.get(2).id;
                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceId+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
                }else {
//                    中关动作
                    String dactid= productList.deviceList.get(0).devActList.get(3).id;
                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceId+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
                }
                break;
            case R.id.ll_kg_right:
                if (value_right==0){
                    //右开动作
                    String dactid= productList.deviceList.get(0).devActList.get(4).id;
                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceId+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
                }else {
//                    右关动作
                    String dactid= productList.deviceList.get(0).devActList.get(5).id;
                    mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceId+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
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
                    String s = EcodeValue.resultEcode(ecode);
                    SmartToast.show(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                parseData(message);
            }
        }
    }
}
