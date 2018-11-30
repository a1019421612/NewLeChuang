package com.hbdiye.newlechuangsmart.devicefragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.hbdiye.newlechuangsmart.bean.MenCiBean;
import com.hbdiye.newlechuangsmart.bean.MenSuoBean;
import com.hbdiye.newlechuangsmart.bean.MenSuoDappBean;
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

public class MenSuoFragment extends BaseFragment {

//    private DeviceClassyBean.ProductList productList;
    private WebSocketConnection mConnection;
    private String token;
    private HomeReceiver homeReceiver;
    private int value;
    private String deviceid;
    private TextView tv_mensuo_name,tv_kaiguan_kg;
    private LinearLayout ll_mensuo_kg;
    private ImageView iv_mensuo_kg;
    private MenSuoBean menSuoBean;

    public static MenSuoFragment newInstance(String page) {
        Bundle args = new Bundle();

//        args.putInt(ARGS_PAGE, page);
        args.putString("deviceid", page);
        MenSuoFragment fragment = new MenSuoFragment();
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
        View view = inflater.inflate(R.layout.fragment_mensuo, container, false);
        mConnection = SingleWebSocketConnection.getInstance();
        token = (String) SPUtils.get(getActivity(),"token","");
        tv_mensuo_name=view.findViewById(R.id.tv_mensuo_name);
        ll_mensuo_kg=view.findViewById(R.id.ll_mensuo_kg);
        iv_mensuo_kg=view.findViewById(R.id.iv_mensuo_kg);
        tv_kaiguan_kg=view.findViewById(R.id.tv_kaiguan_kg);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DCPP");
        intentFilter.addAction("DAPP");
        homeReceiver = new HomeReceiver();
        getActivity().registerReceiver(homeReceiver, intentFilter);

        ll_mensuo_kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<MenSuoBean.Data.DevActList> devActList = menSuoBean.data.devActList;
                if (devActList==null||devActList.size()==0){
                    return;
                }
                if (value==0){
                    //开
                    for (int i = 0; i < devActList.size(); i++) {
                        int comNo = devActList.get(i).comNo;
                        if (comNo==1){
                            String dactid =devActList.get(i).id;
                            String param = devActList.get(i).param;
                            mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\""+param+"\"}");
                        }
                    }
                }else {
                    //关
                    for (int i = 0; i < devActList.size(); i++) {
                        int comNo = devActList.get(i).comNo;
                        if (comNo==0){
                            String dactid =devActList.get(i).id;
                            String param = devActList.get(i).param;
                            mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceid+"\",\"dactid\":\""+dactid+"\",\"param\":\""+param+"\"}");
                        }
                    }
                }
            }
        });
        return view;
    }

    //    PRO003004001
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
                                menSuoBean = new Gson().fromJson(response, MenSuoBean.class);
                                String name = menSuoBean.data.name;
                                tv_mensuo_name.setText(name);
                                List<MenSuoBean.Data.DevAttList> devAttList = menSuoBean.data.devAttList;
                                value = devAttList.get(0).value;
                                setViewByAtt(value,iv_mensuo_kg,tv_kaiguan_kg);
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
//    @OnClick(R.id.ll_mc)
//    public void onViewClicked() {
//        if (value==0){
//            //开动作
//            String dactid= productList.deviceList.get(0).devActList.get(0).id;
//            mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceId+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
//        }else {
////            关动作
//            String dactid= productList.deviceList.get(0).devActList.get(1).id;
//            mConnection.sendTextMessage("{\"pn\":\"DCPP\",\"pt\":\"T\",\"pid\":\""+token+"\",\"token\":\""+token+"\",\"oper\":\"201\",\"sdid\":\""+deviceId+"\",\"dactid\":\""+dactid+"\",\"param\":\"\"}");
//        }
//    }
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
            if (action.equals("DAPP")){
                try {
                    JSONObject jsonObject=new JSONObject(message);
                    String sdid = jsonObject.getString("sdid");
                    if (sdid.equals(deviceid)){
                        MenSuoDappBean menSuoDappBean = new Gson().fromJson(message, MenSuoDappBean.class);
                        value = menSuoDappBean.atts.get(0).value;
                        setViewByAtt(value,iv_mensuo_kg,tv_kaiguan_kg);
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