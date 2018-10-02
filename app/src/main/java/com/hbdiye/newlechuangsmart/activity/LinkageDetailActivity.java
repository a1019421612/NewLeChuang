package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkage_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            mList.add(i + "");
        }
        adapter.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        adapter = new LinkageConditionAdapter(this, mList);
        lvAddCondition.setAdapter(adapter);
        mAdapter = new LinkageConditionAdapter(this, mList);
        lvAddResult.setAdapter(mAdapter);
    }
}
