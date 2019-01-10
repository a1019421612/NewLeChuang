package com.hbdiye.newlechuangsmart.hwactivity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.HwBaseActivity;
import com.hbdiye.newlechuangsmart.adapter.SortAdapter;
import com.hbdiye.newlechuangsmart.bean.ChooseBrandBean;
import com.hbdiye.newlechuangsmart.bean.SortModel;
import com.hbdiye.newlechuangsmart.util.PinyinComparator;
import com.hbdiye.newlechuangsmart.util.PinyinUtils;
import com.hbdiye.newlechuangsmart.util.TitleItemDecoration;
import com.hbdiye.newlechuangsmart.view.WaveSideBar;
import com.xzcysoft.wnzhikonglibrary.WNZKConfigure;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class ChooseBrandActivity extends HwBaseActivity {

    @BindView(R.id.et_search_name)
    EditText etSearchName;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.sideBar)
    WaveSideBar sideBar;
    private PinyinComparator mComparator;
    private List<SortModel> mDateList = new ArrayList<>();
    private LinearLayoutManager manager;
    private SortAdapter mAdapter;
    private TitleItemDecoration mDecoration;
    private String deviceId;
    private List<SortModel> brandList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_brand);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setTitle("选择品牌");
        mComparator = new PinyinComparator();
        //设置右侧SideBar触摸监听
        sideBar.setOnTouchLetterChangeListener(new WaveSideBar.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(letter.charAt(0));
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }
        });

        //RecyclerView设置manager
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        mAdapter = new SortAdapter(this, mDateList);
        recyclerView.setAdapter(mAdapter);
        mDecoration = new TitleItemDecoration(this, mDateList);
        //如果add两个，那么按照先后顺序，依次渲染。
        recyclerView.addItemDecoration(mDecoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        //根据输入框输入值的改变来过滤搜索
        etSearchName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initData() {

        String spId = getIntent().getStringExtra("spId");
        if(TextUtils.isEmpty(spId)) {
            deviceId = getIntent().getStringExtra("deviceId");
            getDevice(deviceId);
        }else {
            getIPTVList(spId);
        }
    }

    private void getIPTVList(String spId) {
        WNZKConfigure.findBrandListByIPTV(spId, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                ChooseBrandBean chooseBrandBean = new Gson().fromJson(response, ChooseBrandBean.class);
                if(getResultCode(chooseBrandBean.errcode)) {
                    mDateList.clear();
                    brandList = chooseBrandBean.brandList;
                    mDateList.addAll(brandList);
                    // 根据a-z进行排序源数据
                    Collections.sort(mDateList, mComparator);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void getDevice(String deviceId) {
        WNZKConfigure.findBrandListByType(deviceId, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("成功", e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                ChooseBrandBean chooseBrandBean = new Gson().fromJson(response, ChooseBrandBean.class);
                if(getResultCode(chooseBrandBean.errcode)) {
                    mDateList.clear();
                    brandList = chooseBrandBean.brandList;
                    mDateList.addAll(brandList);
                    // 根据a-z进行排序源数据
                    Collections.sort(mDateList, mComparator);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    /**
     * 根据输入框中的值来过滤数据并更新RecyclerView
     *
     * @param filterName
     */
    private void filterData(String filterName) {
        List<SortModel> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterName)) {
            filterDateList.addAll(brandList);
        } else {
            filterDateList.clear();
            for (SortModel sortModel : mDateList) {

                String nameQuan = sortModel.cname.trim().replaceAll(" ","");
                String namePinYin = filterName.trim();
//                String nameQuan = sortModel.pinyin.toLowerCase().trim().replaceAll(" ","");
//                String namePinYin = PinyinUtils.getPingYin(filterStr).toLowerCase().trim();

                if(nameQuan.equals(namePinYin)){
                    filterDateList.clear();
                    filterDateList.add(sortModel);
                    break;
                }
                String name = sortModel.inital;
                String filterStr = PinyinUtils.getFirstSpell(filterName);

                if (name.indexOf(filterStr) != -1
                        || PinyinUtils.getFirstSpell(name).startsWith(filterStr)
                        || PinyinUtils.getFirstSpell(name).toLowerCase().startsWith(filterStr)
                        || PinyinUtils.getFirstSpell(name).toUpperCase().startsWith(filterStr)) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, mComparator);
        mDateList.clear();
        mDateList.addAll(filterDateList);
        mAdapter.notifyDataSetChanged();
    }
}
