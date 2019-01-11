package com.hbdiye.newlechuangsmart.hwactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.HwBaseActivity;
import com.hbdiye.newlechuangsmart.adapter.GvBindConverterAdapter;
import com.hbdiye.newlechuangsmart.bean.ConverterListBean;
import com.hbdiye.newlechuangsmart.bean.SelectStudyBean;
import com.hbdiye.newlechuangsmart.util.ToastUtils;
import com.hbdiye.newlechuangsmart.view.MyGridView;
import com.xzcysoft.wnzhikonglibrary.WNZKConfigure;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class SelectStudyActivity extends HwBaseActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.gridview)
    MyGridView gridview;
    @BindView(R.id.tv_hongwaibao)
    TextView tvHongwaibao;
    @BindView(R.id.cb_youxian)
    CheckBox cbYouxian;
    @BindView(R.id.cb_wangluo)
    CheckBox cbWangluo;
    @BindView(R.id.ll_penpai)
    LinearLayout llPenpai;
    @BindView(R.id.ll_select_tv)
    LinearLayout llSelectTv;
    @BindView(R.id.bt_add)
    Button btAdd;
    @BindView(R.id.tv_yunyingshang)
    TextView tvYunyingshang;
    @BindView(R.id.ll_yunyingshang)
    LinearLayout llYunyingshang;
    private String name[] = {"机顶盒", "电视机", "网络盒子", "DVD", "空调", "投影仪",
            "功放", "风扇", "单反相机", "灯泡", "空气净化器", "热水器"};
    private int selectPositon = -1;
    private GvBindConverterAdapter gvBindConverterAdapter;
    private String hwbId;
    private OptionsPickerView<ConverterListBean.InfraredEquipments> hwbSelect;
    private String hwname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_study);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        setTitle("学习");
        gvBindConverterAdapter = new GvBindConverterAdapter(name);
        gvBindConverterAdapter.setSelectPosition(selectPositon);
        gridview.setAdapter(gvBindConverterAdapter);
        gridview.setOnItemClickListener(listener);
        hwname = getIntent().getStringExtra("hwname");
        hwbId=getIntent().getStringExtra("hwid");
        tvHongwaibao.setText(hwname);
//        initHWBList();
    }

    public AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            gvBindConverterAdapter.setSelectPosition(position);
            gvBindConverterAdapter.notifyDataSetChanged();
            selectPositon = position;
            if (position == 0) {
                llYunyingshang.setVisibility(View.VISIBLE);
                llSelectTv.setVisibility(View.VISIBLE);
                cbWangluo.setChecked(true);
            } else {
                llYunyingshang.setVisibility(View.GONE);
                llSelectTv.setVisibility(View.GONE);
                llPenpai.setVisibility(View.VISIBLE);
            }
        }
    };

//    private void initHWBList() {
//        //设置选中项文字颜色
//        hwbSelect = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                ConverterListBean.InfraredEquipments infraredEquipments = mInfraredEquipments.get(options1);
//                tvHongwaibao.setText(infraredEquipments.equipmentName);
//                hwbId = infraredEquipments.id + "";
//            }
//        })
//
//                .setTitleText("红外宝选择")
//                .setDividerColor(Color.BLACK)
//                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
//                .setContentTextSize(16)
//                .build();
//        hwbSelect.setPicker(mInfraredEquipments);
//    }


    @OnClick(R.id.bt_add)
    public void onViewClicked() {

    }

    private void addNoSetTopBoxControl(String name) {
        WNZKConfigure.addRemote(selectPositon + 1 + "", hwbId, name, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                SelectStudyBean selectStudyBean = new Gson().fromJson(response, SelectStudyBean.class);
                if (selectStudyBean.errcode.equals("0")) {
                    Intent intent = new Intent(getApplicationContext(), StudyControlActivity.class);
                    intent.putExtra("selectStudyBean", selectStudyBean);
                    startActivity(intent);
                }
            }
        });
    }


    @OnClick({R.id.tv_hongwaibao, R.id.cb_youxian, R.id.cb_wangluo, R.id.tv_yunyingshang, R.id.bt_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_hongwaibao:
//                hwbSelect.show();
                break;
            case R.id.cb_youxian:
                cbYouxian.setChecked(true);
                cbWangluo.setChecked(false);
                llPenpai.setVisibility(View.GONE);
                break;
            case R.id.cb_wangluo:
                cbWangluo.setChecked(true);
                cbYouxian.setChecked(false);
                llPenpai.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_yunyingshang:

                break;
            case R.id.bt_add:
                String name = etName.getText().toString();
                if (selectPositon == -1) {
                    ToastUtils.showToast(getApplicationContext(), "请选择要控制的设备");
                    return;
                }

                if (selectPositon == 0) {
                    //当选择机顶盒的时候需要判断
                    if (TextUtils.isEmpty(tvYunyingshang.getText().toString())) {
                        ToastUtils.showToast(getApplicationContext(), "请输运营商名称");
                        return;
                    }
                    //机顶盒选择有限电视时候不需要输入品牌名称,网络需要输入品牌名称
                    if (cbWangluo.isChecked()) {
                        if (TextUtils.isEmpty(name)) {
                            ToastUtils.showToast(getApplicationContext(), "请输入品牌名称");
                            return;
                        }
                    }
                } else {
                    if (TextUtils.isEmpty(name)) {
                        ToastUtils.showToast(getApplicationContext(), "请输入品牌名称");
                        return;
                    }
                }
                //selectPositon +1 就是品牌id
                if (selectPositon + 1 == 1) {
                    //学习机顶盒遥控器
                    addSetTopBoxControl(name);
                    return;
                }
                addNoSetTopBoxControl(name);

                break;
        }
    }

    private void addSetTopBoxControl(String name) {
        WNZKConfigure.addRemoteBySTB(selectPositon + 1 + "", hwbId,
                cbWangluo.isChecked() ? "1" : "0", "", name, new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        SelectStudyBean selectStudyBean = new Gson().fromJson(response, SelectStudyBean.class);
                        if (getResultCode(selectStudyBean.errcode)) {
                            Intent intent = new Intent(getApplicationContext(), StudyControlActivity.class);
                            intent.putExtra("selectStudyBean", selectStudyBean);
                            startActivity(intent);
                        }
                    }
                });
    }
}
