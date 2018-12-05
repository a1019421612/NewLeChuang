package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class TiWenActivity extends BaseActivity {

    @BindView(R.id.progress_bar)
    CircleProgressBar progressBar;
    @BindView(R.id.stv_tzl)
    ShiftyTextview stvTzl;
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
        return "体温";
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
                double adiposerate1 = lists.jsonStr.data.temperature;
                if (adiposerate1>42){
                    adiposerate1=42;
                    progressBar.setAnimProgress(100);
                }else {
                    progressBar.setAnimProgress((100/42)*adiposerate1);
                }
                stvTzl.setPostfixString("℃");
                stvTzl.setDuration(3000);
                stvTzl.setNumberString(adiposerate1 + "");
                String common = lists.jsonStr.data.suggestion.common;
                String sport = lists.jsonStr.data.suggestion.sport;
                String foot = lists.jsonStr.data.suggestion.foot;
                tvRcjy.setText(common);
                tvYdjy.setText(sport);
                tvYsjy.setText(foot);
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_ti_wen;
    }
    private void info() {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(YILIAOINFO))
                .addParams("phone",phone)
                .addParams("page","1")
                .addParams("size","6")
                .addParams("type","3")
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
                            double adiposerate1 = list_data.get(0).jsonStr.data.temperature;
                            if (adiposerate1>42){
                                adiposerate1=42;
                                progressBar.setAnimProgress(100);
                            }else {
                                progressBar.setAnimProgress((100/42)*adiposerate1);
                            }
                            stvTzl.setPostfixString("℃");
                            stvTzl.setDuration(3000);
                            stvTzl.setNumberString(adiposerate1 + "");
                            String common = list_data.get(0).jsonStr.data.suggestion.common;
                            String sport = list_data.get(0).jsonStr.data.suggestion.sport;
                            String foot = list_data.get(0).jsonStr.data.suggestion.foot;
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
