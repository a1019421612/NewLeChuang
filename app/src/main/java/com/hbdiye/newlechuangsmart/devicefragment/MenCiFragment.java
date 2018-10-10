package com.hbdiye.newlechuangsmart.devicefragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.tavendo.autobahn.WebSocketConnection;

public class MenCiFragment extends BaseFragment {

    @BindView(R.id.iv_mc)
    ImageView ivMc;
    @BindView(R.id.tv_mc)
    TextView tvMc;
    @BindView(R.id.ll_mc)
    LinearLayout llMc;
    private TextView tv_name;
    private Unbinder bind;
    private DeviceClassyBean.ProductList productList;
    private WebSocketConnection mConnection;
    private String token;
    private HomeReceiver homeReceiver;
    private int value;
    private String deviceId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menci, container, false);
        bind = ButterKnife.bind(this, view);
        tv_name = view.findViewById(R.id.tv_menci_name);
        mConnection = SingleWebSocketConnection.getInstance();
        token = (String) SPUtils.get(getActivity(),"token","");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DCPP");
        homeReceiver = new HomeReceiver();
        getActivity().registerReceiver(homeReceiver, intentFilter);
        return view;
    }

    //    PRO003004001
    @Override
    protected void onFragmentFirstVisible() {
        Fragment parentFragment = (DeviceListFragment) getParentFragment();
        String data = ((DeviceListFragment) parentFragment).data;
        DeviceClassyBean deviceClassyBean = new Gson().fromJson(data, DeviceClassyBean.class);
        for (int i = 0; i < deviceClassyBean.productList.size(); i++) {
            if ("PRO003004001".equals(deviceClassyBean.productList.get(i).id)) {
                productList = deviceClassyBean.productList.get(i);
                String name = productList.name;
                tv_name.setText(name);
            }
        }
        DeviceClassyBean.ProductList.DeviceList.DevAttList devAttList = productList.deviceList.get(0).devAttList.get(0);
        deviceId = devAttList.deviceId;
        value = devAttList.value;
        if (value==0){
            ivMc.setImageResource(R.drawable.mohu);
            tvMc.setText("关");
            tvMc.setTextColor(getResources().getColor(R.color.detail_text));
        }else {
            ivMc.setImageResource(R.drawable.kaiguan);
            tvMc.setText("开");
            tvMc.setTextColor(getResources().getColor(R.color.bar_text_sel));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @OnClick(R.id.ll_mc)
    public void onViewClicked() {
        if (value==0){
            //开动作
            String dactid= productList.deviceList.get(0).devActList.get(0).id;
            mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceId+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
        }else {
//            关动作
            String dactid= productList.deviceList.get(0).devActList.get(1).id;
            mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceId+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
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