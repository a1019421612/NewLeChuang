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

/**
 * 插座界面
 */
public class ChaZuoFragment extends BaseFragment {

    @BindView(R.id.iv_cz)
    ImageView ivCz;
    @BindView(R.id.tv_cz)
    TextView tvCz;
    @BindView(R.id.ll_cz)
    LinearLayout llCz;
    private TextView tv_name;
    private DeviceClassyBean.DeviceList productList;
    private Unbinder bind;
    private WebSocketConnection mConnection;
    private String token;
    private HomeReceiver homeReceiver;
    private int value;
    private String deviceid;

    public static ChaZuoFragment newInstance(String page) {
        Bundle args = new Bundle();

//        args.putInt(ARGS_PAGE, page);
        args.putString("deviceid", page);
        ChaZuoFragment fragment = new ChaZuoFragment();
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
        View view = inflater.inflate(R.layout.fragment_chazuo, container, false);
        bind = ButterKnife.bind(this, view);
        tv_name = view.findViewById(R.id.tv_chazuo_name);
        mConnection = SingleWebSocketConnection.getInstance();
        token = (String) SPUtils.get(getActivity(),"token","");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DCPP");
//        homeReceiver = new HomeReceiver();
//        getActivity().registerReceiver(homeReceiver, intentFilter);
        return view;
    }

    @Override
    protected void onFragmentFirstVisible() {
//        SmartToast.show(deviceid);
//        Fragment parentFragment = (DeviceListFragment) getParentFragment();
//        String data = ((DeviceListFragment) parentFragment).data;
//        DeviceClassyBean deviceClassyBean = new Gson().fromJson(data, DeviceClassyBean.class);
//        for (int i = 0; i < deviceClassyBean.deviceList.size(); i++) {
//            if ("PRO006001001".equals(deviceClassyBean.deviceList.get(i).id)) {
//                productList = deviceClassyBean.deviceList.get(i);
//                String name = productList.name;
//                tv_name.setText(name);
//            }
//        }
//        DeviceClassyBean.DeviceList.DevAttList devAttList = productList.deviceList.get(0).devAttList.get(0);
//        deviceId = devAttList.deviceId;
//        value = devAttList.value;
//        if (value ==0){
//            ivCz.setImageResource(R.drawable.mohu);
//            tvCz.setText("关");
//            tvCz.setTextColor(getResources().getColor(R.color.detail_text));
//        }else {
//            ivCz.setImageResource(R.drawable.kaiguan);
//            tvCz.setText("开");
//            tvCz.setTextColor(getResources().getColor(R.color.bar_text_sel));
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @OnClick(R.id.ll_cz)
    public void onViewClicked() {
//        if (value==0){
//            //开动作
//            String dactid= productList.deviceList.get(0).devActList.get(0).id;
//            mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceId+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
//        }else {
////            关动作
//            String dactid= productList.deviceList.get(0).devActList.get(1).id;
//            mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceId+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
//        }
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
            }
        }
    }
}
