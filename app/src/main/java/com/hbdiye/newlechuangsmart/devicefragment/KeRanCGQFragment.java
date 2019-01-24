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
import com.hbdiye.newlechuangsmart.bean.MenCiBean;
import com.hbdiye.newlechuangsmart.bean.ShuiJinBean;
import com.hbdiye.newlechuangsmart.fragment.BaseFragment;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.DEVICEDETAIL;

public class KeRanCGQFragment extends BaseFragment {
    private String deviceid;
    private String token;
    private HomeReceiver homeReceiver;
    private TextView tv_kr_name,tv_kr_zc,tv_kr_cancel;
    private LinearLayout ll_kr_dc,ll_kr_bc,ll_kr_dl;
    private List<MenCiBean.Data.DevAttList> devAttList;

    public static KeRanCGQFragment newInstance(String page) {
        Bundle args = new Bundle();

//        args.putInt(ARGS_PAGE, page);
        args.putString("deviceid", page);
        KeRanCGQFragment fragment = new KeRanCGQFragment();
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
        View view=inflater.inflate(R.layout.fragment_keran,container,false);
        tv_kr_name=view.findViewById(R.id.tv_kr_name);
        tv_kr_zc=view.findViewById(R.id.tv_kr_zc);
        ll_kr_dc=view.findViewById(R.id.ll_kr_dc);
        ll_kr_bc=view.findViewById(R.id.ll_kr_bc);
        ll_kr_dl=view.findViewById(R.id.ll_kr_dl);
        tv_kr_cancel=view.findViewById(R.id.tv_kr_cancel);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DCPP");
        homeReceiver = new HomeReceiver();
        getActivity().registerReceiver(homeReceiver, intentFilter);
        tv_kr_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = devAttList.get(0).id;
                OkHttpUtils
                        .post()
                        .url(InterfaceManager.getInstance().getURL(InterfaceManager.CANCELWARNING))
                        .addParams("token",token)
                        .addParams("devAttId",id)
                        .addParams("value","0")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    String errcode = jsonObject.getString("errcode");
                                    if (errcode.equals("0")){
                                        String s = EcodeValue.resultEcode(errcode);
                                        SmartToast.show(s);
                                        deviceStatus();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
        return view;
    }
    @Override
    protected void onFragmentFirstVisible() {
        deviceStatus();
    }

    private void deviceStatus() {
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
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String errcode = jsonObject.getString("errcode");
                            if (errcode.equals("0")) {
                                MenCiBean coBean = new Gson().fromJson(response, MenCiBean.class);
                                String name = coBean.data.name;
                                tv_kr_name.setText(name);
                                devAttList = coBean.data.devAttList;
                                int value = devAttList.get(0).value;
                                if (value==1){
                                    tv_kr_zc.setVisibility(View.VISIBLE);
                                    tv_kr_zc.setText("一氧化碳有感应");
                                    tv_kr_zc.setTextColor(Color.RED);
                                }else if (value==0){
                                    tv_kr_zc.setVisibility(View.VISIBLE);
                                }else if (value==521){
                                    ll_kr_dc.setVisibility(View.VISIBLE);
                                }else if (value==4){
                                    ll_kr_bc.setVisibility(View.VISIBLE);
                                }else if (value==10){
                                    ll_kr_dl.setVisibility(View.VISIBLE);
                                }
//                                if ((value&1)==1){
//                                    tv_kr_zc.setVisibility(View.VISIBLE);
//                                    tv_kr_zc.setText("一氧化碳有感应");
//                                    tv_kr_zc.setTextColor(Color.RED);
//                                }else if ((value&1)==0){
//                                    tv_kr_zc.setVisibility(View.VISIBLE);
//                                }else {
//                                    tv_kr_zc.setVisibility(View.GONE);
//                                }
//                                if ((value&512)==512){
//                                    ll_kr_dc.setVisibility(View.VISIBLE);
//                                }else {
//                                    ll_kr_dc.setVisibility(View.GONE);
//                                }
//                                if ((value&4)==4){
//                                    ll_kr_bc.setVisibility(View.VISIBLE);
//                                }else {
//                                    ll_kr_bc.setVisibility(View.GONE);
//                                }
//                                if ((value&10)==10){
//                                    ll_kr_dl.setVisibility(View.VISIBLE);
//                                }else {
//                                    ll_kr_dl.setVisibility(View.GONE);
//                                }
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
