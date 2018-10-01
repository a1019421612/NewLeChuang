package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.AddConditionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LinkageAddConditionActivity extends BaseActivity {

    @BindView(R.id.lv_add_condition)
    ListView lvAddCondition;

    private List<String> mList=new ArrayList<>();
    private AddConditionAdapter adapter;

    @Override
    protected void initData() {
        for (int i = 0; i < 3; i++) {
            mList.add(i+"");
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected String getTitleName() {
        return "添加条件";
    }

    @Override
    protected void initView() {
        adapter=new AddConditionAdapter(this,mList);
        lvAddCondition.setAdapter(adapter);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_linkage_add_condition;
    }
}
