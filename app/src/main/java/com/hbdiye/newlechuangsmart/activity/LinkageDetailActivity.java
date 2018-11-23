package com.hbdiye.newlechuangsmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.LinkageDetailBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

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
    private List<String> mList = new ArrayList<>();
    private String token;
    private String linkageId;

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
                        LinkageDetailBean linkageDetailBean = new Gson().fromJson(response, LinkageDetailBean.class);
                        String errcode = linkageDetailBean.errcode;
                        if (errcode.equals("0")) {
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
                                for (int i = 0; i < devAttList.size(); i++) {
                                    String id1 = devAttList.get(i).id;
                                    if (id1.equals(devAttId)) {
                                        tvConditionName.setText(devAttList.get(i).name);

                                        int value = devAttList.get(i).value;
                                        int type = devAttList.get(i).type;
                                        if (type == 2) {
                                            //监测器属性（属性的 type=2），
                                            //1 小于，2 小于等于，3 等于，4 大于等于，5 大于
                                            tvConditionValue.setText(value+"");

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
    }

    @OnClick({R.id.iv_base_back, R.id.ll_linkage_icon, R.id.tv_linkage_detail_add_attr, R.id.tv_linkage_condition_edit, R.id.tv_condition_name, R.id.tv_condition_attr, R.id.tv_condition_value})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_base_back:
                finish();
                break;
            case R.id.ll_linkage_icon:
                break;
            case R.id.tv_linkage_detail_add_attr:
                //添加条件
                startActivity(new Intent(this, EditActionActivity.class).putExtra("linkageId",linkageId));
                break;
            case R.id.tv_linkage_condition_edit:
                //条件编辑
                startActivity(new Intent(this, EditActionActivity.class));
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
        }
    }
}
