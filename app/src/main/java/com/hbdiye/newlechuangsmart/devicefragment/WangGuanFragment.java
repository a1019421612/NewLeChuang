package com.hbdiye.newlechuangsmart.devicefragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.SingleWebSocketConnection;
import com.hbdiye.newlechuangsmart.fragment.BaseFragment;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.ClassyIconByProId;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.tavendo.autobahn.WebSocketConnection;
import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.DEVICEDETAIL;

public class WangGuanFragment extends BaseFragment {
    @BindView(R.id.tv_gateway_name)
    TextView tvGatewayName;
    @BindView(R.id.ll_gateway_start)
    LinearLayout llGatewayStart;
    @BindView(R.id.ll_gateway_test)
    LinearLayout llGatewayTest;
    @BindView(R.id.ll_gateway_stop)
    LinearLayout llGatewayStop;
    private String deviceid;
    private String mac="";
    private String token="";
    private String name;
    private WebSocketConnection mConnection;
    private HomeReceiver homeReceiver;
    private Unbinder bind;

    public static WangGuanFragment newInstance(String page, String name,String mac) {
        Bundle args = new Bundle();

//        args.putInt(ARGS_PAGE, page);
        args.putString("deviceid", page);
        args.putString("name", name);
        args.putString("mac",mac);
        WangGuanFragment fragment = new WangGuanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deviceid = getArguments().getString("deviceid");
        name = getArguments().getString("name");
        mac=getArguments().getString("mac");
        token = (String) SPUtils.get(getActivity(), "token", "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wangguan, container, false);
        bind = ButterKnife.bind(this, view);
        tvGatewayName.setText(name);
        mConnection = SingleWebSocketConnection.getInstance();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("GOPP");
        homeReceiver = new HomeReceiver();
        getActivity().registerReceiver(homeReceiver, intentFilter);
        return view;
    }

    @Override
    protected void onFragmentFirstVisible() {
    }

    @OnClick({R.id.ll_gateway_start, R.id.ll_gateway_test, R.id.ll_gateway_stop})
    public void onViewClicked(View view) {
        if (TextUtils.isEmpty(mac)|| TextUtils.isEmpty(token)){
            return;
        }
        switch (view.getId()) {
            case R.id.ll_gateway_start:
                mConnection.sendTextMessage("{\"pn\":\"GOPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"1\",\"gmac\":\""+mac+"\",\"time\":\"120\"}");
                break;
            case R.id.ll_gateway_test:
                mConnection.sendTextMessage("{\"pn\":\"GOPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"1\",\"gmac\":\""+mac+"\",\"time\":\"255\"}");
                break;
            case R.id.ll_gateway_stop:
                mConnection.sendTextMessage("{\"pn\":\"GOPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"1\",\"gmac\":\""+mac+"\",\"time\":\"0\"}");
                break;
        }
    }

    class HomeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String message = intent.getStringExtra("message");
            if (action.equals("GOPP")) {
                try {
                    JSONObject jsonObject=new JSONObject(message);
//                    String sdid = jsonObject.getString("sdid");
//                    if (sdid.equals(deviceid)){
                        String ecode = jsonObject.getString("ecode");
//                        if (!ecode.equals("0")){
                            String s = EcodeValue.resultEcode(ecode);
                            SmartToast.show(s);
//                        }
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                parseData(message);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(homeReceiver);
        bind.unbind();
    }
}
