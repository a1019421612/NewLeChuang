package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.YiLiaoTiZhongAdapter;
import com.hbdiye.newlechuangsmart.bean.YiLiaoTiZhongBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.hbdiye.newlechuangsmart.view.CircleProgressBar;
import com.yiwent.viewlib.ShiftyTextview;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.YILIAOINFO;

public class TiZhiLvActivity extends BaseActivity {

    @BindView(R.id.progress_bar)
    CircleProgressBar progressBar;
    @BindView(R.id.stv_tzl)
    ShiftyTextview stvTzl;
    @BindView(R.id.progress_bar1)
    CircleProgressBar progressBar1;
    @BindView(R.id.stv_sfl)
    ShiftyTextview stvSfl;
    @BindView(R.id.progress_bar2)
    CircleProgressBar progressBar2;
    @BindView(R.id.stv_jrl)
    ShiftyTextview stvJrl;
    @BindView(R.id.progress_bar3)
    CircleProgressBar progressBar3;
    @BindView(R.id.stv_nzzfdj)
    ShiftyTextview stvNzzfdj;
    @BindView(R.id.tv_rcjy)
    TextView tvRcjy;
    @BindView(R.id.tv_ydjy)
    TextView tvYdjy;
    @BindView(R.id.tv_ysjy)
    TextView tvYsjy;
    @BindView(R.id.rv_time)
    RecyclerView rvTime;
    String phone;
    private List<YiLiaoTiZhongBean.Data.Lists> mList=new ArrayList<>();
    private YiLiaoTiZhongAdapter adapter;
    private int flag=0;
    @Override
    protected void initData() {
        phone = (String) SPUtils.get(this, "mobilephone", "");
        info();
    }

    @Override
    protected String getTitleName() {
        return "体脂率";
    }

    @Override
    protected void initView() {
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTime.setLayoutManager(manager);
        adapter=new YiLiaoTiZhongAdapter(mList);
        rvTime.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (flag==position){
                    return;
                }
                flag=position;
                YiLiaoTiZhongBean.Data.Lists lists = mList.get(position);
                double adiposerate1 = lists.jsonStr.data.adiposerate;
                double muscle = lists.jsonStr.data.muscle;
                double moisture = lists.jsonStr.data.moisture;
                int visceralfat = lists.jsonStr.data.visceralfat;
                String common = lists.jsonStr.data.suggestion.common;
                String sport = lists.jsonStr.data.suggestion.sport;
                String foot = lists.jsonStr.data.suggestion.foot;
                progressBar.setAnimProgress(adiposerate1);
                progressBar1.setAnimProgress(moisture);
                progressBar2.setAnimProgress(muscle);
                progressBar3.setAnimProgress(visceralfat);

                stvTzl.setPostfixString("%");
                stvTzl.setDuration(3000);
                stvTzl.setNumberString(adiposerate1 + "");

                stvSfl.setPostfixString("%");
                stvSfl.setDuration(3000);
                stvSfl.setNumberString(moisture + "");

                stvJrl.setPostfixString("%");
                stvJrl.setDuration(3000);
                stvJrl.setNumberString(muscle + "");

                stvNzzfdj.setDuration(3000);
                stvNzzfdj.setNumberString(visceralfat + "");

                tvRcjy.setText(common);
                tvYdjy.setText(sport);
                tvYsjy.setText(foot);
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_ti_zhi_lv;
    }
    private void info() {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(YILIAOINFO))
                .addParams("phone",phone)
                .addParams("page","1")
                .addParams("size","6")
                .addParams("type","8")
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
                            List<YiLiaoTiZhongBean.Data.Lists> list_data = yiLiaoTiZhongBean.data.list;
                            if (list_data==null||list_data.size()==0){
                                return;
                            }
                            String datetime = list_data.get(0).datetime;
                            YiLiaoTiZhongBean.Data.Lists.JsonStr jsonStr = list_data.get(0).jsonStr;
                            double adiposerate1 = list_data.get(0).jsonStr.data.adiposerate;
                            double muscle = list_data.get(0).jsonStr.data.muscle;
                            double moisture = list_data.get(0).jsonStr.data.moisture;
                            int visceralfat = list_data.get(0).jsonStr.data.visceralfat;
                            String common = list_data.get(0).jsonStr.data.suggestion.common;
                            String sport = list_data.get(0).jsonStr.data.suggestion.sport;
                            String foot = list_data.get(0).jsonStr.data.suggestion.foot;
                            Log.e("TTT", adiposerate1 + "===" + muscle + "===" + moisture + "===" + visceralfat);
                            progressBar.setAnimProgress(adiposerate1);
                            progressBar1.setAnimProgress(moisture);
                            progressBar2.setAnimProgress(muscle);
                            progressBar3.setAnimProgress(visceralfat);

                            stvTzl.setPostfixString("%");
                            stvTzl.setDuration(3000);
                            stvTzl.setNumberString(adiposerate1 + "");

                            stvSfl.setPostfixString("%");
                            stvSfl.setDuration(3000);
                            stvSfl.setNumberString(moisture + "");

                            stvJrl.setPostfixString("%");
                            stvJrl.setDuration(3000);
                            stvJrl.setNumberString(muscle + "");

                            stvNzzfdj.setDuration(3000);
                            stvNzzfdj.setNumberString(visceralfat + "");

                            tvRcjy.setText(common);
                            tvYdjy.setText(sport);
                            tvYsjy.setText(foot);
                            mList.addAll(list_data);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
