package com.hbdiye.newlechuangsmart.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.LinkageConditionAdapter;
import com.hbdiye.newlechuangsmart.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LinkageDetailActivity extends AppCompatActivity {

    @BindView(R.id.lv_add_condition)
    MyListView lvAddCondition;
    @BindView(R.id.lv_add_result)
    MyListView lvAddResult;
    private List<String> mList = new ArrayList<>();
    private LinkageConditionAdapter adapter,mAdapter;
    private ProgressDialog progressDialog;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            initData();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkage_detail);
        ButterKnife.bind(this);
        initView();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("加载中...");
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = mHandler.obtainMessage();
                mHandler.sendMessageDelayed(message,2000);
            }
        }).start();

    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            mList.add(i + "");
        }
        adapter.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }

    private void initView() {
        adapter = new LinkageConditionAdapter(this, mList);
        lvAddCondition.setAdapter(adapter);
        mAdapter = new LinkageConditionAdapter(this, mList);
        lvAddResult.setAdapter(mAdapter);
    }
}
