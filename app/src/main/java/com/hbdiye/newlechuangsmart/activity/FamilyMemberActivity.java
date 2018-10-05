package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.FamilyMemberAdapter;
import com.hbdiye.newlechuangsmart.bean.FamilyMemberBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FamilyMemberActivity extends BaseActivity {

    @BindView(R.id.rv_family_member)
    RecyclerView rvFamilyMember;

    private List<FamilyMemberBean> mList=new ArrayList<>();
    private FamilyMemberAdapter adapter;

    @Override
    protected void initData() {
        for (int i = 0; i < 4; i++) {
            FamilyMemberBean familyMemberBean=new FamilyMemberBean();
            familyMemberBean.setName(i+"");
            mList.add(familyMemberBean);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected String getTitleName() {
        return "家庭成员";
    }

    @Override
    protected void initView() {
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFamilyMember.setLayoutManager(manager);
        adapter=new FamilyMemberAdapter(mList);
        rvFamilyMember.setAdapter(adapter);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_family_member;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
