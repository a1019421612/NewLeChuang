package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.view.LinkageAddIconPopwindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddRoomActivity extends AppCompatActivity {

    @BindView(R.id.iv_base_back)
    ImageView ivBaseBack;
    @BindView(R.id.iv_linkage_ic)
    ImageView ivLinkageIc;
    @BindView(R.id.ll_linkage_icon)
    RelativeLayout llLinkageIcon;
    @BindView(R.id.ll_root)
    RelativeLayout llRoot;
    private LinkageAddIconPopwindow popwindow;
    private List<Integer> mList_icon = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mList_icon.add(R.drawable.keting3);
        mList_icon.add(R.drawable.shufang2);
        mList_icon.add(R.drawable.shufang);
        mList_icon.add(R.drawable.weisehngjian);
        mList_icon.add(R.drawable.weishengjian2);
        mList_icon.add(R.drawable.danrenfang);
        mList_icon.add(R.drawable.ertongfang);
        mList_icon.add(R.drawable.keting);
        mList_icon.add(R.drawable.keting2);
        mList_icon.add(R.drawable.zhuwo);
        mList_icon.add(R.drawable.chufang);
        mList_icon.add(R.drawable.yangtai);
    }

    @OnClick({R.id.iv_base_back, R.id.ll_linkage_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_base_back:
                finish();
                break;
            case R.id.ll_linkage_icon:
                popwindow = new LinkageAddIconPopwindow(this, mList_icon, clickListener);
                popwindow.showPopupWindowBottom(llRoot);
                break;
        }
    }
    private BaseQuickAdapter.OnItemClickListener clickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            List<Integer> data = adapter.getData();
            Glide.with(AddRoomActivity.this).load(data.get(position)).into(ivLinkageIc);
            popwindow.dismiss();
        }
    };
}
