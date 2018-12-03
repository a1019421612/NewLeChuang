package com.hbdiye.newlechuangsmart.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.LinkageAddActivity;
import com.hbdiye.newlechuangsmart.activity.LinkageDetailActivity;
import com.hbdiye.newlechuangsmart.activity.MoreSceneActivity;
import com.hbdiye.newlechuangsmart.adapter.LinkageAdapter;
import com.hbdiye.newlechuangsmart.bean.LinkageListBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.hbdiye.newlechuangsmart.view.SceneDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

public class LinkageFragment extends Fragment {
    @BindView(R.id.rv_linkage)
    RecyclerView rvLinkage;
    Unbinder unbinder;
    @BindView(R.id.tv_linkage_add)
    TextView tvLinkageAdd;

    private List<LinkageListBean.LinkageList> mList = new ArrayList<>();
    private LinkageAdapter adapter;
    private String token;
    private SceneDialog sceneDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_linkage, container, false);
        unbinder = ButterKnife.bind(this, view);
        token = (String) SPUtils.get(getActivity(), "token", "");
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvLinkage.setLayoutManager(manager);
        adapter = new LinkageAdapter(mList);
        rvLinkage.setAdapter(adapter);
        linkageList();
        handlerClick();
        return view;
    }

    private void linkageList() {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.LINKAGELIST))
                .addParams("token", token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                    @Override
                    public void onResponse(String response, int id) {
                        LinkageListBean linkageListBean = new Gson().fromJson(response, LinkageListBean.class);
                        if (linkageListBean.errcode.equals("0")) {
                            List<LinkageListBean.LinkageList> linkageList = linkageListBean.linkageList;
                            if (linkageList != null) {
                                if (mList.size() > 0) {
                                    mList.clear();
                                }
                                mList.addAll(linkageList);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void handlerClick() {
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setMessage("确定删除该场景？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delLinkage(mList.get(position).id);
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();

                return false;
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String linkageId = mList.get(position).id;
                switch (view.getId()) {
                    case R.id.iv_linkage_kg:
                        int active = mList.get(position).active;
                        if (active == 0) {
                            updateLinkageActive(linkageId, "1");
                        } else {
                            updateLinkageActive(linkageId, "0");
                        }
                        break;
                    case R.id.ll_linkage:
                        startActivity(new Intent(getActivity(), LinkageDetailActivity.class).putExtra("linkageId",linkageId));
                        break;
                }
            }
        });
    }

    private void delLinkage(String id) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.DELLINKAGE))
                .addParams("token",token)
                .addParams("linkageId",id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String errcode = jsonObject.getString("errcode");
                            String s = EcodeValue.resultEcode(errcode);
                            SmartToast.show(s);
                            if (errcode.equals("0")){
                                linkageList();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 联动开关
     */
    private void updateLinkageActive(String linkageId, String active) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.UPDATELINKAGEACTIVE))
                .addParams("token", token)
                .addParams("linkageId", linkageId)
                .addParams("active", active)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String errcode = jsonObject.getString("errcode");
                            String s = EcodeValue.resultEcode(errcode);
                            if (errcode.equals("0")) {
                                linkageList();
                            }
                            SmartToast.show(s);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
//        startActivity(new Intent(getActivity(), LinkageAddActivity.class));
        sceneDialog=new SceneDialog(getActivity(),R.style.MyDialogStyle,clicker,"创建联动");
        sceneDialog.show();
    }
    private View.OnClickListener clicker=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.app_cancle_tv:
                    sceneDialog.dismiss();
                    break;
                case R.id.app_sure_tv:
                    String linkageName = sceneDialog.getSceneName();
                    if (!TextUtils.isEmpty(linkageName)){
                        createLinkage(linkageName);
                    }
                    break;
            }
        }
    };

    /**
     * 创建联动
     * @param linkageName
     */
    private void createLinkage(String linkageName) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.CREATELINKAGE))
                .addParams("token",token)
                .addParams("name",linkageName)
                .addParams("icon","shouye")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String errcode = jsonObject.getString("errcode");
                            String s = EcodeValue.resultEcode(errcode);
                            SmartToast.show(s);
                            if (errcode.equals("0")){
                                if (sceneDialog!=null){
                                    sceneDialog.dismiss();
                                    linkageList();
                                }
                                startActivity(new Intent(getActivity(), LinkageDetailActivity.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        linkageList();
    }
}
