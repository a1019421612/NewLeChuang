package com.hbdiye.newlechuangsmart.devicefragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
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
import com.hbdiye.newlechuangsmart.bean.MenCiBean;
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

public class YanWuCGQFragment extends BaseFragment {
    private WebSocketConnection mConnection;
    private String token;
    private HomeReceiver homeReceiver;
    private int value;
    private String deviceid;
    private TextView tv_yw_name,tv_yw_zc;
    private LinearLayout ll_yw_dc,ll_yw_bc,ll_yw_dl;

    public static YanWuCGQFragment newInstance(String page) {
        Bundle args = new Bundle();

//        args.putInt(ARGS_PAGE, page);
        args.putString("deviceid", page);
        YanWuCGQFragment fragment = new YanWuCGQFragment();
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
        View view=inflater.inflate(R.layout.fragment_yanwu,container,false);
        mConnection = SingleWebSocketConnection.getInstance();
        token = (String) SPUtils.get(getActivity(),"token","");
        tv_yw_name=view.findViewById(R.id.tv_yw_name);
        tv_yw_zc=view.findViewById(R.id.tv_yw_zc);
        ll_yw_dc=view.findViewById(R.id.ll_yw_dc);
        ll_yw_bc=view.findViewById(R.id.ll_yw_bc);
        ll_yw_dl=view.findViewById(R.id.ll_yw_dl);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DCPP");
        homeReceiver = new HomeReceiver();
        getActivity().registerReceiver(homeReceiver, intentFilter);
        return view;
    }

    @Override
    protected void onFragmentFirstVisible() {
//        SmartToast.show(deviceid);
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
                            if (errcode.equals("0")) {
                                MenCiBean coBean = new Gson().fromJson(response, MenCiBean.class);
                                String name = coBean.data.name;
                                tv_yw_name.setText(name);
                                List<MenCiBean.Data.DevAttList> devAttList = coBean.data.devAttList;
                                int value = devAttList.get(0).value;
                                 if ((value&1)==0){
                                    tv_yw_zc.setVisibility(View.VISIBLE);
                                }else {
                                    tv_yw_zc.setVisibility(View.GONE);
                                }
                                if ((value&512)==512){
                                    ll_yw_dc.setVisibility(View.VISIBLE);
                                }else {
                                    ll_yw_dc.setVisibility(View.GONE);
                                }
                                if ((value&4)==4){
                                    ll_yw_bc.setVisibility(View.VISIBLE);
                                }else {
                                    ll_yw_bc.setVisibility(View.GONE);
                                }
                                if ((value&10)==10){
                                    ll_yw_dl.setVisibility(View.VISIBLE);
                                }else {
                                    ll_yw_dl.setVisibility(View.GONE);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(homeReceiver);
    }
}
