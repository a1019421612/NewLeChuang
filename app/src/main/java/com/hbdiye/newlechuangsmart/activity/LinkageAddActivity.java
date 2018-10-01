package com.hbdiye.newlechuangsmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.LinkageConditionAdapter;
import com.hbdiye.newlechuangsmart.view.LinkageAddIconPopwindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LinkageAddActivity extends AppCompatActivity {

    @BindView(R.id.iv_base_back)
    ImageView ivBaseBack;
    @BindView(R.id.tv_linkage_add)
    TextView tvLinkageAdd;
    @BindView(R.id.ll_linkage_icon)
    LinearLayout llLinkageIcon;
    @BindView(R.id.iv_linkage_edit)
    ImageView ivLinkageEdit;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.iv_linkage_ic)
    ImageView ivLinkageIc;
    @BindView(R.id.lv_add_condition)
    ListView lvAddCondition;
    private LinkageAddIconPopwindow popwindow;

    private List<Integer> mList_icon = new ArrayList<>();
    private List<String> mList = new ArrayList<>();
    private LinkageConditionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkage_add);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        adapter = new LinkageConditionAdapter(this, mList);
        lvAddCondition.setAdapter(adapter);
    }

    private void initData() {
        mList_icon.add(R.drawable.huijia);
        mList_icon.add(R.drawable.lijia);
        mList_icon.add(R.drawable.yeqi);
        mList_icon.add(R.drawable.xican);
        mList_icon.add(R.drawable.xiuxi);
        mList_icon.add(R.drawable.xiawucha);
        mList_icon.add(R.drawable.zuofan);
        mList_icon.add(R.drawable.xizao);
        mList_icon.add(R.drawable.shuijiao);
        mList_icon.add(R.drawable.kandianshi);
        mList_icon.add(R.drawable.diannao);
        mList_icon.add(R.drawable.yinyue);
        mList_icon.add(R.drawable.wucan);
        mList_icon.add(R.drawable.youxi);
        mList_icon.add(R.drawable.huike);
        mList_icon.add(R.drawable.xiyifu);
        mList_icon.add(R.drawable.kaihui);
        mList_icon.add(R.drawable.huazhuang);
        mList_icon.add(R.drawable.dushu);
        mList_icon.add(R.drawable.qichuang);
        for (int i = 0; i < 5; i++) {
            mList.add(i + "");
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.tv_linkage_add, R.id.ll_linkage_icon, R.id.iv_linkage_edit, R.id.iv_base_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_linkage_add:
                startActivity(new Intent(this, LinkageAddConditionActivity.class));
                break;
            case R.id.ll_linkage_icon:
                popwindow = new LinkageAddIconPopwindow(this, mList_icon, clickListener);
                popwindow.showPopupWindowBottom(llRoot);
                break;
            case R.id.iv_linkage_edit:
                break;
            case R.id.iv_base_back:
                finish();
                break;
        }
    }

    private BaseQuickAdapter.OnItemClickListener clickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            List<Integer> data = adapter.getData();
            Glide.with(LinkageAddActivity.this).load(data.get(position)).into(ivLinkageIc);
            popwindow.dismiss();
        }
    };
}
