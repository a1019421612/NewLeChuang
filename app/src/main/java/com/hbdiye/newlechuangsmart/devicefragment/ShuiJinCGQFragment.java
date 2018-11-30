package com.hbdiye.newlechuangsmart.devicefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.DeviceClassyBean;
import com.hbdiye.newlechuangsmart.bean.ShuiJinBean;
import com.hbdiye.newlechuangsmart.bean.WenShiDuBean;
import com.hbdiye.newlechuangsmart.fragment.BaseFragment;
import com.hbdiye.newlechuangsmart.fragment.DeviceListFragment;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.DEVICEDETAIL;

public class ShuiJinCGQFragment extends BaseFragment {
    private String deviceid;
    private String token;
    private TextView tv_sj_name,tv_sj_zc;
    private LinearLayout ll_sj_dc,ll_sj_bc,ll_sj_dl;
    public static ShuiJinCGQFragment newInstance(String page) {
        Bundle args = new Bundle();

//        args.putInt(ARGS_PAGE, page);
        args.putString("deviceid", page);
        ShuiJinCGQFragment fragment = new ShuiJinCGQFragment();
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
        View view=inflater.inflate(R.layout.fragment_shuijin,container,false);
        tv_sj_name=view.findViewById(R.id.tv_sj_name);
        tv_sj_zc=view.findViewById(R.id.tv_sj_zc);
        ll_sj_bc=view.findViewById(R.id.ll_sj_bc);
        ll_sj_dc=view.findViewById(R.id.ll_sj_dc);
        ll_sj_dl=view.findViewById(R.id.ll_sj_dl);
        return view;
    }
    @Override
    protected void onFragmentFirstVisible() {
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
                        ShuiJinBean shuiJinBean = new Gson().fromJson(response, ShuiJinBean.class);
                        if (shuiJinBean.data!=null){
                            if (shuiJinBean.errcode.equals("0")){
                                String name = shuiJinBean.data.name;
                                tv_sj_name.setText(name);
                                List<ShuiJinBean.Data.DevAttList> devAttList = shuiJinBean.data.devAttList;
                                int value = devAttList.get(0).value;
                                if ((value&1)==0){
                                    tv_sj_zc.setVisibility(View.VISIBLE);
                                }else {
                                    tv_sj_zc.setVisibility(View.GONE);
                                }
                                if ((value&512)==512){
                                    ll_sj_dc.setVisibility(View.VISIBLE);
                                }else {
                                    ll_sj_dc.setVisibility(View.GONE);
                                }
                                if ((value&4)==4){
                                    ll_sj_bc.setVisibility(View.VISIBLE);
                                }else {
                                    ll_sj_bc.setVisibility(View.GONE);
                                }
                                if ((value&10)==10){
                                    ll_sj_dl.setVisibility(View.VISIBLE);
                                }else {
                                    ll_sj_dl.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                });
    }
}
