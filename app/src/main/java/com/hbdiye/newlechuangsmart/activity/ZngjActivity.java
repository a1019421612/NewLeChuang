package com.hbdiye.newlechuangsmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.ZngjAdapter;
import com.hbdiye.newlechuangsmart.bean.ZngjBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.hbdiye.newlechuangsmart.zxing.activity.CaptureActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class ZngjActivity extends BaseActivity {

    @BindView(R.id.rv_zngj)
    RecyclerView rvZngj;
    private String token;
    private ZngjAdapter adapter;
    private List<ZngjBean.RobotList> mList=new ArrayList<>();
    @Override
    protected void initData() {
        token = (String) SPUtils.get(this, "token", "");
        zngjList();
    }

    private void zngjList() {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.ZNGJLIST))
                .addParams("token", token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ZngjBean zngjBean = new Gson().fromJson(response, ZngjBean.class);
                        if (zngjBean.errcode.equals("0")) {
                            List<ZngjBean.RobotList> robotList = zngjBean.robotList;
                            mList.addAll(robotList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    protected String getTitleName() {
        return "智能管家";
    }

    @Override
    protected void initView() {
        ivBaseAdd.setVisibility(View.VISIBLE);
        adapter=new ZngjAdapter(mList);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvZngj.setLayoutManager(manager);
        rvZngj.setAdapter(adapter);
        ivBaseAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ZngjActivity.this,CaptureActivity.class).putExtra("camera",false).putExtra("flag",true)
                .putExtra("flag_gj",true));
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_zngj;
    }

}
