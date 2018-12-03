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

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.AlertdialogListAttAdapter;
import com.hbdiye.newlechuangsmart.adapter.AlertdialogListNameAdapter;
import com.hbdiye.newlechuangsmart.adapter.LinkageConditionNameAdapter;
import com.hbdiye.newlechuangsmart.adapter.LinkageConditionRangeAdapter;
import com.hbdiye.newlechuangsmart.adapter.LinkageDetailListAdapter;
import com.hbdiye.newlechuangsmart.bean.LinkageDetailBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.IconByName;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.hbdiye.newlechuangsmart.view.LinkageAddIconPopwindow;
import com.hbdiye.newlechuangsmart.view.MyListView;
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
import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.UPDATELINKAGECONDITION;
import static com.hbdiye.newlechuangsmart.global.InterfaceManager.UPDATELINKAGEINFO;
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
    @BindView(R.id.iv_linkage_edit)
    ImageView ivLinkageEdit;
    private List<String> mList = new ArrayList<>();
    private String token;
    private String linkageId;

    private LinkageDetailListAdapter mAdapter;
    private List<LinkageDetailBean.LinkageTaskLists> list = new ArrayList<>();
    private List<LinkageDetailBean.LinkageTaskLists> linkageTaskList;
    private LinkageDetailBean linkageDetailBean;

    private int current_value;//条件当前值
    private int current_condition;//当前条件的范围
    private String current_devattId;//当前devattid
    private String current_Id;//当前id
    private int type;//当前type
    private SceneDialog sceneDialog;
    private LinkageAddIconPopwindow popwindow;
    private List<Integer> mList_icon = new ArrayList<>();

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
        initData();
        linkageDetail();
    }

    private void initData() {
        mList_icon.add(R.drawable.liandong_icon1);
        mList_icon.add(R.drawable.liandong_icon2);
        mList_icon.add(R.drawable.liandong_icon3);
        mList_icon.add(R.drawable.liandong_icon4);
        mList_icon.add(R.drawable.liandong_icon5);
        mList_icon.add(R.drawable.liandong_icon6);
        mList_icon.add(R.drawable.liandong_icon7);
        mList_icon.add(R.drawable.liandong_icon8);
        mList_icon.add(R.drawable.liandong_icon9);
        mList_icon.add(R.drawable.liandong_icon10);
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
                            String icon = linkageDetailBean.linkage.icon;
                            Glide.with(LinkageDetailActivity.this).load(IconByName.drawableByNameLinkage(icon)).into(ivLinkageIc);
                            tvLinkageName.setText(name);
                            List<LinkageDetailBean.LinkageConditionLists> linkageConditionList = linkageDetailBean.linkageConditionList;
                            if (linkageConditionList != null && linkageConditionList.size() > 0) {
                                llLinkageDetailAddAttr.setVisibility(View.GONE);
                                LinkageDetailBean.LinkageConditionLists linkageConditionLists = linkageConditionList.get(0);
                                String name1 = linkageConditionLists.name;

                                tvLinkageConditionName.setText(name1);
                                List<LinkageDetailBean.LinkageConditionLists.LinkageConditionList> linkageConditionList1 = linkageConditionLists.linkageConditionList;
                                LinkageDetailBean.LinkageConditionLists.LinkageConditionList linkageConditionList2 = linkageConditionList1.get(0);
                                current_devattId = linkageConditionList2.devAttId;
                                List<LinkageDetailBean.LinkageConditionLists.DevAttList> devAttList = linkageConditionLists.devAttList;
                                current_condition = linkageConditionList2.condition;
                                current_value = linkageConditionList2.value;
                                current_Id = linkageConditionList2.id;
                                for (int i = 0; i < devAttList.size(); i++) {
                                    String id1 = devAttList.get(i).id;
                                    if (id1.equals(current_devattId)) {
                                        tvConditionName.setText(devAttList.get(i).name);

//                                        current_value = devAttList.get(i).value;
                                        type = devAttList.get(i).type;
                                        if (type == 2) {
                                            switch (current_condition) {
                                                case 1:
                                                    tvConditionAttr.setText("小于");
                                                    break;
                                                case 2:
                                                    tvConditionAttr.setText("小于");
                                                    break;
                                                case 3:
                                                    tvConditionAttr.setText("等于");
                                                    break;
                                                case 4:
                                                    tvConditionAttr.setText("大于");
                                                    break;
                                                case 5:
                                                    tvConditionAttr.setText("大于");
                                                    break;
                                            }
                                            //监测器属性（属性的 type=2），
                                            //1 小于，2 小于等于，3 等于，4 大于等于，5 大于
                                            tvConditionValue.setVisibility(View.VISIBLE);
                                            tvConditionValue.setText(current_value + "");

                                        } else if (type == 3) {
                                            switch (current_value) {
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
                AlertDialog.Builder builder = new AlertDialog.Builder(LinkageDetailActivity.this);
                View view = View.inflate(LinkageDetailActivity.this, R.layout.alertdialog_list, null);
                RecyclerView rv_name = view.findViewById(R.id.rv_dialog);
                AlertdialogListNameAdapter adapter = new AlertdialogListNameAdapter(devAttList);
                LinearLayoutManager manager = new LinearLayoutManager(LinkageDetailActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                rv_name.setLayoutManager(manager);
                rv_name.setAdapter(adapter);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
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
                        String real_devactid = "";
                        for (int i = 0; i < devActList.size(); i++) {

                            if (devActId.equals(devActList.get(i).id)) {
                                int comNo = devActList.get(i).comNo;
                                for (int j = 0; j < devActList.size(); j++) {
                                    if (port == devActList.get(j).port && comNo == devActList.get(j).comNo) {
                                        real_devactid = devActList.get(j).id;
                                    }
                                }
                            }
                        }
                        String task_id = linkageTaskList.id;
                        OkHttpUtils
                                .post()
                                .url(InterfaceManager.getInstance().getURL(UPDATELINKAGETASK))
                                .addParams("token", token)
                                .addParams("linkageId", linkageId)
                                .addParams("delayTime", "0")
                                .addParams("deviceId", deviceId)
                                .addParams("devActId", real_devactid)
                                .addParams("param", param)
                                .addParams("id", task_id)
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
                                            SmartToast.show(s);
                                            if (errcode.equals("0")) {
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
            public void OnAttrAttListener(final int pos, final int p, final int port) {
                final LinkageDetailBean.LinkageTaskLists linkageTaskLists = linkageTaskList.get(pos);
                final List<LinkageDetailBean.LinkageTaskLists.DevActList> devActList = linkageTaskLists.devActList;
                final List<LinkageDetailBean.LinkageTaskLists.DevAttList> devAttList = linkageTaskLists.devAttList;
                final List<LinkageDetailBean.LinkageTaskLists.DevActList> devActList_copy = new ArrayList<>();
                for (int i = 0; i < devActList.size(); i++) {
                    if (port == devActList.get(i).port) {
                        devActList_copy.add(devActList.get(i));
                    }
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(LinkageDetailActivity.this);
                View view = View.inflate(LinkageDetailActivity.this, R.layout.alertdialog_list, null);
                RecyclerView rv_name = view.findViewById(R.id.rv_dialog);
                AlertdialogListAttAdapter adapter = new AlertdialogListAttAdapter(devActList_copy);
                LinearLayoutManager manager = new LinearLayoutManager(LinkageDetailActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                rv_name.setLayoutManager(manager);
                rv_name.setAdapter(adapter);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.show();

                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        dialog.dismiss();
                        LinkageDetailBean.LinkageTaskLists.LinkageTaskList linkageTaskList = linkageTaskLists.linkageTaskList.get(p);
                        String param = devActList_copy.get(position).param;
                        String real_devactid = devActList_copy.get(position).id;
                        String task_id = linkageTaskList.id;
                        String deviceId = "";
                        int port1 = linkageTaskList.port;
                        for (int i = 0; i < devAttList.size(); i++) {
                            if (port1 == devAttList.get(i).port) {
                                deviceId = devAttList.get(i).id;
                            }
                        }

                        OkHttpUtils
                                .post()
                                .url(InterfaceManager.getInstance().getURL(UPDATELINKAGETASK))
                                .addParams("token", token)
                                .addParams("linkageId", linkageId)
                                .addParams("delayTime", "0")
                                .addParams("deviceId", deviceId)
                                .addParams("devActId", real_devactid)
                                .addParams("param", param)
                                .addParams("id", task_id)
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
                                            SmartToast.show(s);
                                            if (errcode.equals("0")) {
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
                        .addParams("token", token)
                        .addParams("taskId", stid)
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
                                    SmartToast.show(s);
                                    if (errcode.equals("0")) {
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
                if (linkageTaskList.size() >= devAttList.size()) {
                    SmartToast.show("不能添加了");
                    return;
                }
                List<Boolean> flag = new ArrayList<>();
                for (int i = 0; i < devAttList.size(); i++) {
                    flag.add(false);
                }
                for (int i = 0; i < devAttList.size(); i++) {
                    int devatt_port = devAttList.get(i).port;
                    for (int j = 0; j < linkageTaskList.size(); j++) {
                        int port = linkageTaskList.get(j).port;
                        if (devatt_port == port) {
                            flag.set(i, true);
                        }
                    }
                }
                for (int i = 0; i < flag.size(); i++) {
                    if (!flag.get(i)) {
                        String name1 = devAttList.get(i).name;
                        int port = devAttList.get(i).port;
                        for (int j = 0; j < devActList.size(); j++) {
                            int port1 = devActList.get(j).port;
                            if (port == port1) {
                                String param = devActList.get(j).param;
                                String devActId = devActList.get(j).id;
                                OkHttpUtils
                                        .post()
                                        .url(InterfaceManager.getInstance().getURL(InterfaceManager.CREATETASK))
                                        .addParams("token", token)
                                        .addParams("linkageId", linkageId)
                                        .addParams("deviceId", deviceId)
                                        .addParams("delaytime", "0")
                                        .addParams("param", param)
                                        .addParams("devActId", devActId)
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
                                                    SmartToast.show(s);
                                                    if (errcode.equals("0")) {
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

    @OnClick({R.id.iv_base_back, R.id.ll_linkage_icon, R.id.tv_linkage_detail_add_attr, R.id.tv_linkage_condition_edit,
            R.id.tv_condition_name, R.id.tv_condition_attr, R.id.tv_condition_value, R.id.tv_linkage_add_task, R.id.iv_linkage_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_base_back:
                finish();
                break;
            case R.id.ll_linkage_icon:
                //修改联动图标
                popwindow = new LinkageAddIconPopwindow(this, mList_icon, clickListener);
                popwindow.showPopupWindowBottom(llLinkageIcon);
                break;
            case R.id.iv_linkage_edit:
                //修改联动名称
                sceneDialog = new SceneDialog(this, R.style.MyDialogStyle, edit_name_clicker, "联动名称");
                sceneDialog.show();
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
                        .putExtra("condition_id", condition_id), 101);
                break;
            case R.id.tv_condition_name:
                //条件属性名称
                LinkageDetailBean.LinkageConditionLists linkageConditionLists = linkageDetailBean.linkageConditionList.get(0);
                final List<LinkageDetailBean.LinkageConditionLists.DevAttList> devAttList = linkageDetailBean.linkageConditionList.get(0).devAttList;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View view_attr = View.inflate(LinkageDetailActivity.this, R.layout.alertdialog_list, null);
                RecyclerView rv_name = view_attr.findViewById(R.id.rv_dialog);
                LinkageConditionNameAdapter adapter = new LinkageConditionNameAdapter(devAttList);
                LinearLayoutManager manager = new LinearLayoutManager(LinkageDetailActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                rv_name.setLayoutManager(manager);
                rv_name.setAdapter(adapter);
                builder.setView(view_attr);
                final AlertDialog dialog = builder.create();
                dialog.show();
                final String conditionId = linkageDetailBean.linkageConditionList.get(0).linkageConditionList.get(0).id;
                final int value = linkageConditionLists.linkageConditionList.get(0).value;


                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        dialog.dismiss();
                        String devAttId = devAttList.get(position).id;
                        OkHttpUtils
                                .post()
                                .url(InterfaceManager.getInstance().getURL(UPDATELINKAGECONDITION))
                                .addParams("token", token)
                                .addParams("linkageId", linkageId)
                                .addParams("devAttId", devAttId)
                                .addParams("condition", current_condition + "")
                                .addParams("value", current_value + "")
                                .addParams("id", current_Id)
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
                                            SmartToast.show(s);
                                            if (errcode.equals("0")) {
                                                linkageDetail();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    }
                });
                break;
            case R.id.tv_condition_attr:
                //条件属性属性
                if (type == 2) {
                    typeEqualTwo();
                } else if (type == 3) {
                    typeEqualThree();
                }
                break;
            case R.id.tv_condition_value:
                //条件属性值
                sceneDialog = new SceneDialog(this, R.style.MyDialogStyle, clicker, "输入值");
                sceneDialog.show();
                sceneDialog.setEditInput();
                break;
            case R.id.tv_linkage_add_task:
                startActivity(new Intent(this, AddLinkageDeviceActivity.class).putExtra("linkageId", linkageId));
                break;
        }
    }

    private BaseQuickAdapter.OnItemClickListener clickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            List<Integer> data = adapter.getData();
            String name = IconByName.nameByDrawableLinkage(data.get(position));
            updateLinkageIcon(name);
            popwindow.dismiss();
        }
    };

    /**
     * 修改联动图标
     *
     * @param name
     */
    private void updateLinkageIcon(String name) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(UPDATELINKAGEINFO))
                .addParams("token", token)
                .addParams("linkageId", linkageId)
                .addParams("icon", name)
                .addParams("name", "")
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
                            String msg = EcodeValue.resultEcode(errcode);
                            SmartToast.show(msg);
                            if (errcode.equals("0")) {
                                linkageDetail();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    /**
     * 修改联动名称
     *
     * @param name
     */
    private void updateLinkageName(String name) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(UPDATELINKAGEINFO))
                .addParams("token", token)
                .addParams("linkageId", linkageId)
                .addParams("icon", "")
                .addParams("name", name)
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
                            String msg = EcodeValue.resultEcode(errcode);
                            SmartToast.show(msg);
                            if (errcode.equals("0")) {
                                if (sceneDialog!=null){
                                    sceneDialog.dismiss();
                                }
                                linkageDetail();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    private void typeEqualTwo() {
        final List<String> mList = new ArrayList<>();
        mList.add("2");
        mList.add("3");
        mList.add("4");
        List<String> mList_name = new ArrayList<>();
        mList_name.add("小于");
        mList_name.add("等于");
        mList_name.add("大于");
        AlertDialog.Builder builder_attr = new AlertDialog.Builder(this);
        View view_attr1 = View.inflate(LinkageDetailActivity.this, R.layout.alertdialog_list, null);
        RecyclerView rv_name1 = view_attr1.findViewById(R.id.rv_dialog);
        LinkageConditionRangeAdapter madapter = new LinkageConditionRangeAdapter(mList_name);
        LinearLayoutManager manager1 = new LinearLayoutManager(LinkageDetailActivity.this);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        rv_name1.setLayoutManager(manager1);
        rv_name1.setAdapter(madapter);
        builder_attr.setView(view_attr1);
        final AlertDialog dialog_attr = builder_attr.create();
        dialog_attr.show();
        madapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String condition = mList.get(position);
                dialog_attr.dismiss();
                OkHttpUtils
                        .post()
                        .url(InterfaceManager.getInstance().getURL(UPDATELINKAGECONDITION))
                        .addParams("token", token)
                        .addParams("linkageId", linkageId)
                        .addParams("devAttId", current_devattId)
                        .addParams("condition", condition)
                        .addParams("value", current_value + "")
                        .addParams("id", current_Id)
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
                                    SmartToast.show(s);
                                    if (errcode.equals("0")) {
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

    private void typeEqualThree() {
        final List<String> mList = new ArrayList<>();
        mList.add("1");
        mList.add("4");
        mList.add("8");
        mList.add("512");
        List<String> mList_name = new ArrayList<>();
        mList_name.add("有感应");
        mList_name.add("被拆除");
        mList_name.add("电池欠压");
        mList_name.add("电池故障");
        AlertDialog.Builder builder_attr = new AlertDialog.Builder(this);
        View view_attr1 = View.inflate(LinkageDetailActivity.this, R.layout.alertdialog_list, null);
        RecyclerView rv_name1 = view_attr1.findViewById(R.id.rv_dialog);
        LinkageConditionRangeAdapter madapter = new LinkageConditionRangeAdapter(mList_name);
        LinearLayoutManager manager1 = new LinearLayoutManager(LinkageDetailActivity.this);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        rv_name1.setLayoutManager(manager1);
        rv_name1.setAdapter(madapter);
        builder_attr.setView(view_attr1);
        final AlertDialog dialog_attr = builder_attr.create();
        dialog_attr.show();
        madapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String value = mList.get(position);
                dialog_attr.dismiss();
                OkHttpUtils
                        .post()
                        .url(InterfaceManager.getInstance().getURL(UPDATELINKAGECONDITION))
                        .addParams("token", token)
                        .addParams("linkageId", linkageId)
                        .addParams("devAttId", current_devattId)
                        .addParams("condition", "3")
                        .addParams("value", value)
                        .addParams("id", current_Id)
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
                                    SmartToast.show(s);
                                    if (errcode.equals("0")) {
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

    private View.OnClickListener edit_name_clicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.app_cancle_tv:
                    sceneDialog.dismiss();
                    break;
                case R.id.app_sure_tv:
                    String linkageName = sceneDialog.getSceneName();
                    if (!TextUtils.isEmpty(linkageName)) {
                        updateLinkageName(linkageName);
                    }
                    break;
            }
        }
    };
    private View.OnClickListener clicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.app_cancle_tv:
                    sceneDialog.dismiss();
                    break;
                case R.id.app_sure_tv:
                    String linkageName = sceneDialog.getSceneName();
                    if (!TextUtils.isEmpty(linkageName)) {
                        OkHttpUtils
                                .post()
                                .url(InterfaceManager.getInstance().getURL(UPDATELINKAGECONDITION))
                                .addParams("token", token)
                                .addParams("linkageId", linkageId)
                                .addParams("devAttId", current_devattId)
                                .addParams("condition", current_condition + "")
                                .addParams("value", linkageName)
                                .addParams("id", current_Id)
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
                                            SmartToast.show(s);
                                            if (errcode.equals("0")) {
                                                if (sceneDialog != null) {
                                                    sceneDialog.dismiss();
                                                }
                                                linkageDetail();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    }
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {

            if (requestCode == 101) {
                linkageDetail();
            }
        }
    }
}
