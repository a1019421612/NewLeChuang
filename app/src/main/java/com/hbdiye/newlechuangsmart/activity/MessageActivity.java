package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageActivity extends BaseActivity {

    @BindView(R.id.rv_message)
    RecyclerView rvMessage;

    private MessageAdapter adapter;
    private List<String> mList=new ArrayList<>();

    @Override
    protected void initData() {
        for (int i = 0; i < 10; i++) {
            mList.add(i+"");
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected String getTitleName() {
        return "消息";
    }

    @Override
    protected void initView() {
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMessage.setLayoutManager(manager);
        adapter=new MessageAdapter(mList);
        rvMessage.setAdapter(adapter);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
