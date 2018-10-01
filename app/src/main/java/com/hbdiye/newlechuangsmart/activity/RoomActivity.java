package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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

public class RoomActivity extends AppCompatActivity {

    @BindView(R.id.iv_base_back)
    ImageView ivBaseBack;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.iv_linkage_ic)
    ImageView ivLinkageIc;
    @BindView(R.id.ll_linkage_icon)
    RelativeLayout llLinkageIcon;
    @BindView(R.id.tv_room_name)
    TextView tvRoomName;
    @BindView(R.id.iv_linkage_edit)
    ImageView ivLinkageEdit;
    @BindView(R.id.rv_room_device)
    RecyclerView rvRoomDevice;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    private LinkageAddIconPopwindow popwindow;
    private List<Integer> mList_icon = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
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
//        for (int i = 0; i < 5; i++) {
//            mList.add(i + "");
//        }
//        adapter.notifyDataSetChanged();
    }
    @OnClick({R.id.iv_base_back, R.id.iv_linkage_ic, R.id.iv_linkage_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_base_back:
                finish();
                break;
            case R.id.iv_linkage_ic:
                popwindow = new LinkageAddIconPopwindow(this, mList_icon, clickListener);
                popwindow.showPopupWindowBottom(llRoot);
                break;
            case R.id.iv_linkage_edit:

                break;
        }
    }
    private BaseQuickAdapter.OnItemClickListener clickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            List<Integer> data = adapter.getData();
            Glide.with(RoomActivity.this).load(data.get(position)).into(ivLinkageIc);
            popwindow.dismiss();
        }
    };
}
