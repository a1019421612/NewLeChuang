package com.hbdiye.newlechuangsmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.AlertdialogListAttAdapter;
import com.hbdiye.newlechuangsmart.adapter.AlertdialogListNameAdapter;
import com.hbdiye.newlechuangsmart.adapter.LinkageDetailListAdapter;
import com.hbdiye.newlechuangsmart.bean.LinkageDetailBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.hbdiye.newlechuangsmart.view.MyListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.UPDATELINKAGETASK;

public class LinkageDetailActivity extends AppCompatActivity {

    @BindView(R.id.iv_base_back)
    ImageView ivBaseBack;
    @BindView(R.id.iv_linkage_ic)
    ImageView ivLinkageIc;
    @BindView(R.id.ll_linkage_icon)
    LinearLayout llLinkageIcon;
    @BindView(R.id.tv_linkage_detail_add_attr)
    TextView tvLinkageDetailAddAttr;
    @BindView(R.id.ll_linkage_detail_add_attr)
    LinearLayout llLinkageDetailAddAttr;
    @BindView(R.id.tv_linkage_name)
    TextView tvLinkageName;
    @BindView(R.id.tv_linkage_condition_name)
    TextView tvLinkageConditionName;
    @BindView(R.id.tv_linkage_condition_edit)
    TextView tvLinkageConditionEdit;
    @BindView(R.id.tv_condition_name)
    TextView tvConditionName;
    @BindView(R.id.tv_condition_attr)
    TextView tvConditionAttr;
    @BindView(R.id.tv_condition_value)
    TextView tvConditionValue;
    @BindView(R.id.mlv_linkage_result)
    MyListView mlvLinkageResult;
    @BindView(R.id.tv_linkage_add_task)
    TextView tvLinkageAddTask;
    private List<String> mList = new ArrayList<>();
    private String token;
    private String linkageId;

    private LinkageDetailListAdapter mAdapter;
    private List<LinkageDetailBean.LinkageTaskLists> list = new ArrayList<>();
    private List<LinkageDetailBean.LinkageTaskLists> linkageTaskList;
    private LinkageDetailBean linkageDetailBean;

    //    private LinkageConditionAdapter adapter,mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkage_detail);
        ButterKnife.bind(this);
        token = (String) SPUtils.get(this, "token", "");
        linkageId = getIntent().getStringExtra("linkageId");
        if (TextUtils.isEmpty(linkageId)) {
            return;
        }
        initView();
        linkageDetail();
    }

    private void linkageDetail() {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.LINKAGEDETAIL))
                .addParams("token", token)
                .addParams("linkageId", linkageId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        linkageDetailBean = new Gson().fromJson(response, LinkageDetailBean.class);
                        String errcode = linkageDetailBean.errcode;
                        if (errcode.equals("0")) {

                            linkageTaskList = linkageDetailBean.linkageTaskList;
                            if (linkageTaskList != null) {
                                if (list.size() > 0) {
                                    list.clear();
                                }
                                list.addAll(linkageTaskList);
                                mAdapter.notifyDataSetChanged();
                            }

                            String name = linkageDetailBean.linkage.name;
                            tvLinkageName.setText(name);
                            List<LinkageDetailBean.LinkageConditionLists> linkageConditionList = linkageDetailBean.linkageConditionList;
                            if (linkageConditionList != null && linkageConditionList.size() > 0) {
                                llLinkageDetailAddAttr.setVisibility(View.GONE);
                                LinkageDetailBean.LinkageConditionLists linkageConditionLists = linkageConditionList.get(0);
                                String name1 = linkageConditionLists.name;
                                tvLinkageConditionName.setText(name1);
                                List<LinkageDetailBean.LinkageConditionLists.LinkageConditionList> linkageConditionList1 = linkageConditionLists.linkageConditionList;
                                LinkageDetailBean.LinkageConditionLists.LinkageConditionList linkageConditionList2 = linkageConditionList1.get(0);
                                String devAttId = linkageConditionList2.devAttId;
                                List<LinkageDetailBean.LinkageConditionLists.DevAttList> devAttList = linkageConditionLists.devAttList;
                                int value1 = linkageConditionList2.value;

                                for (int i = 0; i < devAttList.size(); i++) {
                                    String id1 = devAttList.get(i).id;
                                    if (id1.equals(devAttId)) {
                                        tvConditionName.setText(devAttList.get(i).name);

                                        int value = devAttList.get(i).value;
                                        int type = devAttList.get(i).type;
                                        if (type == 2) {
                                            switch (value1) {
                                                case 1:
                                                    tvConditionAttr.setText("小于");
                                                    break;
                                                case 2:
                                                    tvConditionAttr.setText("小于等于");
                                                    break;
                                                case 3:
                                                    tvConditionAttr.setText("等于");
                                                    break;
                                                case 4:
                                                    tvConditionAttr.setText("大于等于");
                                                    break;
                                                case 5:
                                                    tvConditionAttr.setText("大于");
                                                    break;
                                            }
                                            //监测器属性（属性的 type=2），
                                            //1 小于，2 小于等于，3 等于，4 大于等于，5 大于
                                            tvConditionValue.setText(value + "");

                                        } else {
                                            switch (value1) {
                                                case 1:
                                                    tvConditionAttr.setText("有感应");
                                                    break;
                                                case 4:
                                                    tvConditionAttr.setText("被拆除");
                                                    break;
                                                case 8:
                                                    tvConditionAttr.setText("电池欠压");
                                                    break;
                                                case 512:
                                                    tvConditionAttr.setText("电池故障");
                                                    break;
                                            }
                                            tvConditionValue.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            } else {
                                llLinkageDetailAddAttr.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

    private void initView() {
        mAdapter = new LinkageDetailListAdapter(this, list);
        mlvLinkageResult.setAdapter(mAdapter);
        //动作名称
        mAdapter.setOnAttrNameListener(new LinkageDetailListAdapter.AttrNameListener() {
            @Override
            public void OnAttrNameListener(final int pos, final int p, final String param) {
                final LinkageDetailBean.LinkageTaskLists linkageTaskLists = linkageTaskList.get(pos);
                final String deviceId = linkageTaskLists.id;
                final List<LinkageDetailBean.LinkageTaskLists.DevAttList> devAttList = linkageTaskLists.devAttList;
                final List<LinkageDetailBean.LinkageTaskLists.DevActList> devActList = linkageTaskLists.devActList;
                AlertDialog.Builder builder=new AlertDialog.Builder(LinkageDetailActivity.this);
                View view=View.inflate(LinkageDetailActivity.this,R.layout.alertdialog_list,null);
                RecyclerView rv_name=view.findViewById(R.id.rv_dialog);
                AlertdialogListNameAdapter adapter=new AlertdialogListNameAdapter(devAttList);
                LinearLayoutManager manager=new LinearLayoutManager(LinkageDetailActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                rv_name.setLayoutManager(manager);
                rv_name.setAdapter(adapter);
                builder.setView(view);
                final AlertDialog dialog=builder.create();
                dialog.show();
                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        dialog.dismiss();
                        LinkageDetailBean.LinkageTaskLists.DevAttList devAttList1 = devAttList.get(position);
                        int port = devAttList1.port;
                        String deviceId = devAttList1.id;
                        LinkageDetailBean.LinkageTaskLists.LinkageTaskList linkageTaskList = linkageTaskLists.linkageTaskList.get(p);
                        String devActId = linkageTaskList.devActId;
                        String real_devactid="";
                        for (int i = 0; i < devActList.size(); i++) {

                            if (devActId.equals(devActList.get(i).id)){
                                int comNo = devActList.get(i).comNo;
                                for (int j = 0; j < devActList.size(); j++) {
                                    if (port==devActList.get(j).port&&comNo==devActList.get(j).comNo){
                                        real_devactid=devActList.get(j).id;
                                    }
                                }
                            }
                        }
                        String task_id = linkageTaskList.id;
                        OkHttpUtils
                                .post()
                                .url(InterfaceManager.getInstance().getURL(UPDATELINKAGETASK))
                                .addParams("token",token)
                                .addParams("linkageId",linkageId)
                                .addParams("delayTime","0")
                                .addParams("deviceId",deviceId)
                                .addParams("devActId",real_devactid)
                                .addParams("param",param)
                                .addParams("id",task_id)
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
                                                linkageDetail();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    }
                });
            }
        });
        //动作属性
        mAdapter.setOnAttrAttListener(new LinkageDetailListAdapter.AttrAttListener() {
            @Override
            public void OnAttrAttListener(final int pos, final int p,final int port) {
                final LinkageDetailBean.LinkageTaskLists linkageTaskLists = linkageTaskList.get(pos);
                final List<LinkageDetailBean.LinkageTaskLists.DevActList> devActList = linkageTaskLists.devActList;
                final List<LinkageDetailBean.LinkageTaskLists.DevAttList> devAttList = linkageTaskLists.devAttList;
                final List<LinkageDetailBean.LinkageTaskLists.DevActList> devActList_copy=new ArrayList<>();
                for (int i = 0; i < devActList.size(); i++) {
                    if (port==devActList.get(i).port){
                        devActList_copy.add(devActList.get(i));
                    }
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(LinkageDetailActivity.this);
                View view=View.inflate(LinkageDetailActivity.this,R.layout.alertdialog_list,null);
                RecyclerView rv_name=view.findViewById(R.id.rv_dialog);
                AlertdialogListAttAdapter adapter=new AlertdialogListAttAdapter(devActList_copy);
                LinearLayoutManager manager=new LinearLayoutManager(LinkageDetailActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                rv_name.setLayoutManager(manager);
                rv_name.setAdapter(adapter);
                builder.setView(view);
                final AlertDialog dialog=builder.create();
                dialog.show();

                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        dialog.dismiss();
                        LinkageDetailBean.LinkageTaskLists.LinkageTaskList linkageTaskList = linkageTaskLists.linkageTaskList.get(p);
                        String param= devActList_copy.get(position).param;
                        String real_devactid = devActList_copy.get(position).id;
                        String task_id = linkageTaskList.id;
                        String deviceId="";
                        int port1 = linkageTaskList.port;
                        for (int i = 0; i < devAttList.size(); i++) {
                            if (port1==devAttList.get(i).port){
                                deviceId = devAttList.get(i).id;
                            }
                        }

                        OkHttpUtils
                                .post()
                                .url(InterfaceManager.getInstance().getURL(UPDATELINKAGETASK))
                                .addParams("token",token)
                                .addParams("linkageId",linkageId)
                                .addParams("delayTime","0")
                                .addParams("deviceId",deviceId)
                                .addParams("devActId",real_devactid)
                                .addParams("param",param)
                                .addParams("id",task_id)
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
                                                linkageDetail();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    }
                });
            }
        });
        //删除动作
        mAdapter.setImageViewDelListener(new LinkageDetailListAdapter.ImageViewDelListener() {
            @Override
            public void OnImageViewDelListener(String stid) {
                OkHttpUtils
                        .post()
                        .url(InterfaceManager.getInstance().getURL(InterfaceManager.DELLINKAGEACTION))
                        .addParams("token",token)
                        .addParams("taskId",stid)
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
                                        linkageDetail();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
        //添加动作
        mAdapter.setOnItemClickListener(new LinkageDetailListAdapter.GridOnItemClickListener() {
            @Override
            public void OnGridItemClickListener(int pos) {
                LinkageDetailBean.LinkageTaskLists linkageTaskLists = linkageTaskList.get(pos);
                String deviceId = linkageTaskLists.id;
                List<LinkageDetailBean.LinkageTaskLists.LinkageTaskList> linkageTaskList = linkageTaskLists.linkageTaskList;
                List<LinkageDetailBean.LinkageTaskLists.DevAttList> devAttList = linkageTaskLists.devAttList;
                List<LinkageDetailBean.LinkageTaskLists.DevActList> devActList = linkageTaskLists.devActList;
                if (linkageTaskList.size()>=devAttList.size()){
                    SmartToast.show("不能添加了");
                    return;
                }
                List<Boolean> flag=new ArrayList<>();
                for (int i = 0; i < devAttList.size(); i++) {
                    flag.add(false);
                }
                for (int i = 0; i < devAttList.size(); i++) {
                    int devatt_port = devAttList.get(i).port;
                    for (int j = 0; j < linkageTaskList.size(); j++) {
                        int port = linkageTaskList.get(j).port;
                        if (devatt_port==port){
                            flag.set(i,true);
                        }
                    }
                }
                for (int i = 0; i < flag.size(); i++) {
                    if (!flag.get(i)){
                        String name1 = devAttList.get(i).name;
                        int port = devAttList.get(i).port;
                        for (int j = 0; j < devActList.size(); j++) {
                            int port1 = devActList.get(j).port;
                            if (port==port1){
                                String param = devActList.get(j).param;
                                String devActId = devActList.get(j).id;
                                OkHttpUtils
                                        .post()
                                        .url(InterfaceManager.getInstance().getURL(InterfaceManager.CREATETASK))
                                        .addParams("token",token)
                                        .addParams("linkageId",linkageId)
                                        .addParams("deviceId",deviceId)
                                        .addParams("delaytime","0")
                                        .addParams("param",param)
                                        .addParams("devActId",devActId)
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
                                                        linkageDetail();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
            }
        });
    }

    @OnClick({R.id.iv_base_back, R.id.ll_linkage_icon, R.id.tv_linkage_detail_add_attr, R.id.tv_linkage_condition_edit, R.id.tv_condition_name, R.id.tv_condition_attr, R.id.tv_condition_value, R.id.tv_linkage_add_task})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_base_back:
                finish();
                break;
            case R.id.ll_linkage_icon:
                break;
            case R.id.tv_linkage_detail_add_attr:
                //添加条件
                startActivityForResult(new Intent(this, EditActionActivity.class).putExtra("linkageId", linkageId), 101);
                break;
            case R.id.tv_linkage_condition_edit:
                //条件编辑
                String condition_id = linkageDetailBean.linkageConditionList.get(0).linkageConditionList.get(0).id;
                startActivityForResult(new Intent(this, UpdateLinkageConditionActivity.class)
                        .putExtra("linkageId", linkageId)
                        .putExtra("condition_id",condition_id), 101);
                break;
            case R.id.tv_condition_name:
                //条件属性名称
                break;
            case R.id.tv_condition_attr:
                //条件属性属性
                break;
            case R.id.tv_condition_value:
                //条件属性值
                break;
            case R.id.tv_linkage_add_task:
                startActivity(new Intent(this,AddLinkageDeviceActivity.class).putExtra("linkageId",linkageId));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {

            if (requestCode == 101) {
                linkageDetail();
            }
        }
    }
}
