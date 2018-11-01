package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.MyFragmentPagerAdapter;
import com.hbdiye.newlechuangsmart.bean.DeviceClassyBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.ClassyIconByProId;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 设备分类详情界面
 */
public class DeviceClassyActivity extends AppCompatActivity {
    @BindView(R.id.tablayout_device)
    TabLayout tablayoutDevice;
    @BindView(R.id.viewpager_device)
    ViewPager viewpagerDevice;
    MyFragmentPagerAdapter myFragmentPagerAdapter;
    @BindView(R.id.tv_classy_title)
    TextView tvClassyTitle;
    @BindView(R.id.iv_base_back)
    ImageView ivBaseBack;
    private List<Integer> list_tabIcons = new ArrayList<>();
    private List<Integer> list_tabIconsPressed = new ArrayList<>();
    private String token;
    private String roomId;
    private String productId;
    private int flag=-1;
    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_classy);
        ButterKnife.bind(this);
        String roomName = getIntent().getStringExtra("roomName");
        roomId = getIntent().getStringExtra("roomId");
        productId = getIntent().getStringExtra("productId");
        deviceId = getIntent().getStringExtra("deviceId");
        token = (String) SPUtils.get(this,"token","");
        tvClassyTitle.setText(roomName);
        initData();
    }

    private void initData() {
        OkHttpUtils
                .get()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.DEVICELIST))
                .addParams("token",token)
                .addParams("roomId",roomId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        DeviceClassyBean deviceClassyBean = new Gson().fromJson(response, DeviceClassyBean.class);
                        List<DeviceClassyBean.DeviceList> productList = deviceClassyBean.deviceList;
                        if (productList==null){
                            return;
                        }
                        initPage(response, productList);

                    }
                });

    }

    /**
     * 根据返回数据初始化界面
     *
     * @param response
     * @param productList
     */
    private void initPage(String response, List<DeviceClassyBean.DeviceList> productList) {
        List<String> sort = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            String id1 = productList.get(i).productId;
            sort.add(id1.substring(0,6));
        }
        List<String> strings = ClassyIconByProId.removeDuplicate(sort);
//        for (int i = 0; i < strings.size(); i++) {
//            if (strings.get(i).contains(productId)){
//                list_tabIcons.add(ClassyIconByProId.iconNormal(strings.get(i)));
//                list_tabIconsPressed.add(ClassyIconByProId.iconPressed(strings.get(i)));
//            }
//        }
//        for (int i = 0; i < strings.size(); i++) {
//            if (!strings.get(i).contains(productId)){
//                list_tabIcons.add(ClassyIconByProId.iconNormal(strings.get(i)));
//                list_tabIconsPressed.add(ClassyIconByProId.iconPressed(strings.get(i)));
//            }
//        }
        for (int i = 0; i < strings.size(); i++) {
            if (productId.contains(strings.get(i))){
                flag = i;
            }
                list_tabIcons.add(ClassyIconByProId.iconNormal(strings.get(i)));
                list_tabIconsPressed.add(ClassyIconByProId.iconPressed(strings.get(i)));
        }
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), DeviceClassyActivity.this, strings, response,productId,deviceId);
        viewpagerDevice.setAdapter(myFragmentPagerAdapter);
        tablayoutDevice.setupWithViewPager(viewpagerDevice);
        tablayoutDevice.setTabMode(TabLayout.MODE_SCROLLABLE);
        if (flag!=-1){
            viewpagerDevice.setCurrentItem(flag);
        }
        for (int i = 0; i < strings.size(); i++) {
            tablayoutDevice.getTabAt(i).setCustomView(getTabView(i));
        }
        tablayoutDevice.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabSelect(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabNormal(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void changeTabNormal(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
        int position = tab.getPosition();
        changePositionNormal(img_title, position);

    }

    private void changePositionNormal(ImageView img_title, int position) {
        img_title.setImageResource(list_tabIcons.get(position));
        viewpagerDevice.setCurrentItem(position);

    }

    private void changeTabSelect(TabLayout.Tab tab) {
        int position = tab.getPosition();
        View view = tab.getCustomView();
        ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
        changePositionSelect(img_title, position);
    }

    private void changePositionSelect(ImageView img_title, int position) {

        img_title.setImageResource(list_tabIconsPressed.get(position));
        viewpagerDevice.setCurrentItem(position);
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
        img_title.setImageResource(list_tabIcons.get(position));

        if (position == flag) {
            img_title.setImageResource(list_tabIconsPressed.get(position));
        } else {
            img_title.setImageResource(list_tabIcons.get(position));
        }
        return view;
    }

    @OnClick(R.id.iv_base_back)
    public void onViewClicked() {
        finish();
    }
}
