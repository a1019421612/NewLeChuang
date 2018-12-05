package com.hbdiye.newlechuangsmart.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.YiLiaoTiZhongBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.yiwent.viewlib.ShiftyTextview;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.YILIAOINFO;

public class XueYaActivity extends BaseActivity {

    @BindView(R.id.stv_gaoya)
    ShiftyTextview stvGaoya;
    @BindView(R.id.stv_diya)
    ShiftyTextview stvDiya;
    @BindView(R.id.tv_rcjy)
    TextView tvRcjy;
    @BindView(R.id.tv_ydjy)
    TextView tvYdjy;
    @BindView(R.id.tv_ysjy)
    TextView tvYsjy;
    @BindView(R.id.tv_pulse)
    TextView tvPulse;
    @BindView(R.id.iv_xueya)
    ImageView ivXueya;
    @BindView(R.id.rv_time)
    RecyclerView rvTime;
    String phone;
    @Override
    protected void initData() {
        phone = (String) SPUtils.get(this, "mobilephone", "");
        info();
    }

    @Override
    protected String getTitleName() {
        return "血压";
    }

    @Override
    protected void initView() {
        AnimationDrawable animationDrawable = (AnimationDrawable) ivXueya.getDrawable();
        animationDrawable.start();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_xue_ya;
    }
    private void info() {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(YILIAOINFO))
                .addParams("phone",phone)
                .addParams("page","1")
                .addParams("size","6")
                .addParams("type","7")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        String replace = response.replace("\\\"", "\"").replace("\"{","{").replace("}\"","}");
                        YiLiaoTiZhongBean yiLiaoTiZhongBean = new Gson().fromJson(replace, YiLiaoTiZhongBean.class);
                        if (yiLiaoTiZhongBean.errCode.equals("0")){
//                            List<YiLiaoTiZhongBean.Data.Lists> list_data = yiLiaoTiZhongBean.data.list;
//                            if (list_data==null||list_data.size()==0){
//                                return;
//                            }
//                            double adiposerate1 = list_data.get(0).jsonStr.data.temperature;
//                            if (adiposerate1>42){
//                                adiposerate1=42;
//                                progressBar.setAnimProgress(100);
//                            }else {
//                                progressBar.setAnimProgress((100/42)*adiposerate1);
//                            }
//                            stvTzl.setPostfixString("℃");
//                            stvTzl.setDuration(3000);
//                            stvTzl.setNumberString(adiposerate1 + "");
//                            String common = list_data.get(0).jsonStr.data.suggestion.common;
//                            String sport = list_data.get(0).jsonStr.data.suggestion.sport;
//                            String foot = list_data.get(0).jsonStr.data.suggestion.foot;
//                            tvRcjy.setText(common);
//                            tvYdjy.setText(sport);
//                            tvYsjy.setText(foot);
//                            mList.addAll(list_data);
//                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
