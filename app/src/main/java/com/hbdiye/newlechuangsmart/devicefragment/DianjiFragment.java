package com.hbdiye.newlechuangsmart.devicefragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.zhouyou.view.seekbar.SignSeekBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import de.tavendo.autobahn.WebSocketConnection;

public class DianjiFragment extends BaseFragment {
    private SignSeekBar signSeekBar;
    private TextView tv_name;
//    private DeviceClassyBean.ProductList productList;
    private WebSocketConnection mConnection;
    private String token;
//    private HomeReceiver homeReceiver;
    private int value;
    private String deviceid;

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
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dianji,container,false);
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
//        SmartToast.show(deviceid+"");
//        Fragment parentFragment = (DeviceListFragment) getParentFragment();
//        String data = ((DeviceListFragment) parentFragment).data;
//        DeviceClassyBean deviceClassyBean = new Gson().fromJson(data, DeviceClassyBean.class);
//        for (int i = 0; i < deviceClassyBean.productList.size(); i++) {
//            if ("PRO005002001".equals(deviceClassyBean.productList.get(i).id)) {
//                productList = deviceClassyBean.productList.get(i);
//                String name = productList.name;
//                tv_name.setText(name);
//            }
//        }
//        List<DeviceClassyBean.ProductList.DeviceList.DevAttList> devAttList = productList.deviceList.get(0).devAttList;
//        deviceId = devAttList.get(0).deviceId;

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
}
