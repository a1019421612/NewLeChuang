package com.hbdiye.newlechuangsmart.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.FamilyMemberAdapter;
import com.hbdiye.newlechuangsmart.bean.FamilyMemberBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class FamilyMemberActivity extends BaseActivity {

    @BindView(R.id.rv_family_member)
    RecyclerView rvFamilyMember;
    private List<FamilyMemberBean.UserList> mList=new ArrayList<>();
    private FamilyMemberAdapter adapter;
    private String token;
    private boolean isAdmin;

    @Override
    protected void initData() {
        token = (String) SPUtils.get(this,"token","");
        memberList();
    }

    @Override
    protected String getTitleName() {
        return "家庭成员";
    }

    @Override
    protected void initView() {
        isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFamilyMember.setLayoutManager(manager);
        adapter=new FamilyMemberAdapter(mList,isAdmin);
        rvFamilyMember.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.iv_member_icon:
                        if (position!=0){
                            final FamilyMemberBean.UserList userList = mList.get(position);
                            final String phone = userList.phone;
                            AlertDialog.Builder builder=new AlertDialog.Builder(FamilyMemberActivity.this);
                            builder.setMessage("确定踢出该成员？");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    delMember(phone);
                                }
                            });
                            builder.setNegativeButton("取消",null);
                            builder.show();
                        }
                        break;
                }
            }
        });
    }
    private void memberList() {
        OkHttpUtils.post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.MEMBERLIST))
                .addParams("token",token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        FamilyMemberBean familyMemberBean = new Gson().fromJson(response, FamilyMemberBean.class);
                        if (familyMemberBean.errcode.equals("0")){
                            List<FamilyMemberBean.UserList> userList = familyMemberBean.userList;
                            if (mList==null){
                                return;
                            }
                            if (mList.size()>0){
                                mList.clear();
                            }
                            mList.addAll(userList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    private void delMember(String phone) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.KICKEDOUTUSERBYPHONE))
                .addParams("token",token)
                .addParams("phone",phone)
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
                            if (errcode.equals("0")){
                                SmartToast.show("操作成功");
                                memberList();
                            }else {
                                String s = EcodeValue.resultEcode(errcode);
                                SmartToast.show(s);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
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
