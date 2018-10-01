package com.hbdiye.newlechuangsmart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.LinkageAddActivity;
import com.hbdiye.newlechuangsmart.adapter.LinkageAdapter;
import com.hbdiye.newlechuangsmart.bean.LinkageBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LinkageFragment extends Fragment {
    @BindView(R.id.rv_linkage)
    RecyclerView rvLinkage;
    Unbinder unbinder;
    @BindView(R.id.tv_linkage_add)
    TextView tvLinkageAdd;

    private List<LinkageBean> mList = new ArrayList<>();
    private LinkageAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_linkage, container, false);
        unbinder = ButterKnife.bind(this, view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvLinkage.setLayoutManager(manager);
        for (int i = 0; i < 5; i++) {
            mList.add(new LinkageBean() {{
                setName("1");
            }});
        }
        adapter = new LinkageAdapter(mList);
        rvLinkage.setAdapter(adapter);
        handlerClick();
        return view;
    }

    private void handlerClick() {
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                SmartToast.show("删除");
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_linkage_add)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(),LinkageAddActivity.class));
    }
}
