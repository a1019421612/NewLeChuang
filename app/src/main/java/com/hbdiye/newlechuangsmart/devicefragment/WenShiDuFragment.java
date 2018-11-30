package com.hbdiye.newlechuangsmart.devicefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.ChuanglianBean;
import com.hbdiye.newlechuangsmart.bean.WenShiDuBean;
import com.hbdiye.newlechuangsmart.fragment.BaseFragment;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.DEVICEDETAIL;

public class WenShiDuFragment extends BaseFragment {
    private String deviceid;
    private String token;
    private TextView tv_wenshidu_name,tv_wenshidu_wen,tv_wenshidu_shi;
    public static WenShiDuFragment newInstance(String page) {
        Bundle args = new Bundle();

//        args.putInt(ARGS_PAGE, page);
        args.putString("deviceid", page);
        WenShiDuFragment fragment = new WenShiDuFragment();
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
        View view = inflater.inflate(R.layout.fragment_wenshidu, container, false);
        tv_wenshidu_name=view.findViewById(R.id.tv_wenshidu_name);
        tv_wenshidu_shi=view.findViewById(R.id.tv_wenshidu_shi);
        tv_wenshidu_wen=view.findViewById(R.id.tv_wenshidu_wen);
        return view;
    }

    @Override
    protected void onFragmentFirstVisible() {
//        SmartToast.show(deviceid);
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
                        WenShiDuBean wenShiDuBean = new Gson().fromJson(response, WenShiDuBean.class);
                        if (wenShiDuBean.data!=null){
                            if (wenShiDuBean.errcode.equals("0")){
                                List<WenShiDuBean.Data.DevAttList> devAttList = wenShiDuBean.data.devAttList;
                                tv_wenshidu_name.setText(wenShiDuBean.data.name);
                                for (int i = 0; i < devAttList.size(); i++) {
                                    int cluNo = devAttList.get(i).cluNo;
                                    if (cluNo==1026){
                                        //温度
                                        int value = devAttList.get(i).value;
                                        tv_wenshidu_wen.setText((value/100)+"℃");
                                    }else if (cluNo==1029){
                                        //湿度
                                        int value = devAttList.get(i).value;
                                        tv_wenshidu_shi.setText((value/100)+"%");
                                    }
                                }
                            }
                        }
                    }
                });
    }
}
