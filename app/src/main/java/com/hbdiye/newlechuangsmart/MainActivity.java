package com.hbdiye.newlechuangsmart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.fragment.DeviceFragment;
import com.hbdiye.newlechuangsmart.fragment.HomeFragment;
import com.hbdiye.newlechuangsmart.fragment.LinkageFragment;
import com.hbdiye.newlechuangsmart.fragment.MineFragment;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mImageChats, mImageContact, mImageFind, mImageMe;
    private TextView mTextChats, mTextContact, mTextFind, mTextMe;
    private FrameLayout frameLayout;
    private FragmentManager fragmentManager;

    private HomeFragment homeFragment;
    private DeviceFragment deviceFragment;
    private LinkageFragment linkageFragment;
    private MineFragment mineFragment;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initStatusBar();
        initView();

        intent = new Intent(MainActivity.this, MyService.class);
        startService(intent);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(intent);
//        }else {
//            startService(intent);
//        }

//        initData();
        String[] perm={Permission.CAMERA,Permission.WRITE_EXTERNAL_STORAGE,Permission.ACCESS_COARSE_LOCATION};
        AndPermission.with(this)
                .runtime()
                .permission(perm)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                    }
                })
                .start();
        fragmentManager = getSupportFragmentManager();
        changeTextViewColor();
        changeSelectedTabState(0);
        showFragment(0);
    }
    private void initView() {
        frameLayout = findViewById(R.id.fl_container);
        RelativeLayout chatRLayout = (RelativeLayout) findViewById(R.id.seal_chat);
        RelativeLayout contactRLayout = (RelativeLayout) findViewById(R.id.seal_contact_list);
        RelativeLayout foundRLayout = (RelativeLayout) findViewById(R.id.seal_find);
        RelativeLayout mineRLayout = (RelativeLayout) findViewById(R.id.seal_me);
        mImageChats = (ImageView) findViewById(R.id.tab_img_chats);
        mImageContact = (ImageView) findViewById(R.id.tab_img_contact);
        mImageFind = (ImageView) findViewById(R.id.tab_img_find);
        mImageMe = (ImageView) findViewById(R.id.tab_img_me);
        mTextChats = (TextView) findViewById(R.id.tab_text_chats);
        mTextContact = (TextView) findViewById(R.id.tab_text_contact);
        mTextFind = (TextView) findViewById(R.id.tab_text_find);
        mTextMe = (TextView) findViewById(R.id.tab_text_me);

        chatRLayout.setOnClickListener(this);
        contactRLayout.setOnClickListener(this);
        foundRLayout.setOnClickListener(this);
        mineRLayout.setOnClickListener(this);
    }
    public void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            // 获取状态栏高度
            int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            View rectView = new View(this);
            // 绘制一个和状态栏一样高的矩形，并添加到视图中
            LinearLayout.LayoutParams params
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            rectView.setLayoutParams(params);
            //设置状态栏颜色（该颜色根据你的App主题自行更改）
            rectView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            // 添加矩形View到布局中
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            decorView.addView(rectView);
            ViewGroup rootView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    private void showFragment(int i) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hide(transaction);
        switch (i) {
            case 0:
                if (homeFragment!=null){
                    transaction.show(homeFragment);
                }else {
                    homeFragment=new HomeFragment();
                    transaction.add(R.id.fl_container,homeFragment);
                }
                break;
            case 1:
                if (deviceFragment!=null){
                    transaction.show(deviceFragment);
                }else {
                    deviceFragment=new DeviceFragment();
                    transaction.add(R.id.fl_container,deviceFragment);
                }
                break;
            case 2:
                if (linkageFragment!=null){
                    transaction.show(linkageFragment);
                }else {
                    linkageFragment=new LinkageFragment();
                    transaction.add(R.id.fl_container,linkageFragment);
                }
                break;
            case 3:
                if (mineFragment!=null){
                    transaction.show(mineFragment);
                }else {
                    mineFragment=new MineFragment();
                    transaction.add(R.id.fl_container,mineFragment);
                }
                break;
        }
        transaction.commit();
    }
    private void hide(FragmentTransaction transaction) {

        if (homeFragment!=null){
            transaction.hide(homeFragment);
        }
        if (deviceFragment!=null){
            transaction.hide(deviceFragment);
        }
        if (linkageFragment!=null){
            transaction.hide(linkageFragment);
        }
        if (mineFragment!=null){
            transaction.hide(mineFragment);
        }
    }
    private void changeTextViewColor() {
        mImageChats.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_chat));
        mImageContact.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_contacts));
        mImageFind.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_found));
        mImageMe.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_me));
        mTextChats.setTextColor(Color.parseColor("#abadbb"));
        mTextContact.setTextColor(Color.parseColor("#abadbb"));
        mTextFind.setTextColor(Color.parseColor("#abadbb"));
        mTextMe.setTextColor(Color.parseColor("#abadbb"));
    }
    private void changeSelectedTabState(int position) {
        switch (position) {
            case 0:
                mTextChats.setTextColor(Color.parseColor("#0686DD"));
                mImageChats.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_chat_hover));
                break;
            case 1:
                mTextContact.setTextColor(Color.parseColor("#0686DD"));
                mImageContact.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_contacts_hover));
                break;
            case 2:
                mTextFind.setTextColor(Color.parseColor("#0686DD"));
                mImageFind.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_found_hover));
                break;
            case 3:
                mTextMe.setTextColor(Color.parseColor("#0686DD"));
                mImageMe.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_me_hover));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        changeTextViewColor();
        switch (v.getId()) {
            case R.id.seal_chat:
                changeSelectedTabState(0);
                showFragment(0);
                break;
            case R.id.seal_contact_list:
                changeSelectedTabState(1);
                showFragment(1);
                break;
            case R.id.seal_find:
                changeSelectedTabState(2);
                showFragment(2);
                break;
            case R.id.seal_me:
                changeSelectedTabState(3);
                showFragment(3);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
    }
}
