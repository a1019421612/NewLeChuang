package com.hbdiye.newlechuangsmart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.AddRoomActivity;
import com.hbdiye.newlechuangsmart.activity.ChoiceDeviceActivity;
import com.hbdiye.newlechuangsmart.activity.DeviceClassyActivity;
import com.hbdiye.newlechuangsmart.activity.RoomActivity;
import com.hbdiye.newlechuangsmart.activity.YaoKongCenterActivity;
import com.hbdiye.newlechuangsmart.activity.YiLiaoActivity;
import com.hbdiye.newlechuangsmart.activity.ZhiNengzjActivity;
import com.hbdiye.newlechuangsmart.bean.CommentClassyBean;
import com.hbdiye.newlechuangsmart.view.DelDialog;
import com.hbdiye.newlechuangsmart.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DeviceFragment extends Fragment {
    @BindView(R.id.gv_common_classify)
    MyGridView gvCommonClassify;
    Unbinder unbinder;
    @BindView(R.id.gv_device_classify)
    MyGridView gvDeviceClassify;
    @BindView(R.id.tv_device_edit)
    TextView tvDeviceEdit;
    private List<CommentClassyBean> mList = new ArrayList<>();
    private
    List<Integer> mList_device = new ArrayList<>();
    private Myadapter mMyadapter;
    private MyDeviceadapter device_adapter;

    private boolean flag = false;//编辑设备分类

    private String[] array_productId={"PRO003","PRO002","PRO001","PRO004","PRO008","PRO007","PRO006","PRO005","PRO009"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device, container, false);
        unbinder = ButterKnife.bind(this, view);
        mList.add(new CommentClassyBean(){{setIcon(R.drawable.af);setTitle("安防设备");}});
        mList.add(new CommentClassyBean(){{setIcon(R.drawable.dengpao);setTitle("照明开关");}});
        mList.add(new CommentClassyBean(){{setIcon(R.drawable.znzj);setTitle("智能主机");}});
        mList.add(new CommentClassyBean(){{setIcon(R.drawable.hjjc);setTitle("环境监测");}});
        mList.add(new CommentClassyBean(){{setIcon(R.drawable.ylyl);setTitle("医疗养老");}});
        mList.add(new CommentClassyBean(){{setIcon(R.drawable.zngj);setTitle("智能管家");}});
        mList.add(new CommentClassyBean(){{setIcon(R.drawable.jd);setTitle("家电控制");}});
        mList.add(new CommentClassyBean(){{setIcon(R.drawable.cl);setTitle("窗帘开关");}});

        for (int i = 0; i < 5; i++) {
            mList_device.add(R.drawable.zhuwo);
        }
        mMyadapter = new Myadapter();
        gvCommonClassify.setAdapter(mMyadapter);
        device_adapter = new MyDeviceadapter();
        gvDeviceClassify.setAdapter(device_adapter);
        handleClicker();
        return view;
    }

    private void handleClicker() {
        gvDeviceClassify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == mList_device.size()) {
                    startActivity(new Intent(getActivity(), AddRoomActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), RoomActivity.class));
                }
            }
        });
        gvCommonClassify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==8){

                }else if (i==6){
                    //跳转遥控中心
                    startActivity(new Intent(getActivity(),YaoKongCenterActivity.class).putExtra("productId",array_productId[i]));
                }else if (i==4){
                    //医疗
                    startActivity(new Intent(getActivity(), YiLiaoActivity.class));
                }else if (i==2){
                    String title = mList.get(i).getTitle();
                    int icon = mList.get(i).getIcon();
                    startActivity(new Intent(getActivity(), ZhiNengzjActivity.class)
                            .putExtra("title",title)
                            .putExtra("icon",icon)
                            .putExtra("productId",array_productId[i]));
                }
                else {
                    String title = mList.get(i).getTitle();
                    int icon = mList.get(i).getIcon();
                    startActivity(new Intent(getActivity(),ChoiceDeviceActivity.class)
                            .putExtra("title",title)
                            .putExtra("icon",icon)
                            .putExtra("productId",array_productId[i]));
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_device_edit)
    public void onViewClicked() {
        if (flag) {
            tvDeviceEdit.setText("编辑");
            flag = false;
        } else {
            flag = true;
            tvDeviceEdit.setText("完成");
        }
        device_adapter.notifyDataSetChanged();
    }

    class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size() + 1 == 6 ? 6 : mList.size() + 1;
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = LayoutInflater.from(getActivity()).inflate(R.layout.device_gv_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.gridview_item);
            TextView tv_title=view.findViewById(R.id.tv_content);
            if (mList.size() == i) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(getActivity()).load(R.drawable.other).into(imageView);
                if (i == 7) {
                    imageView.setVisibility(View.GONE);
                }
            } else {
                Glide.with(getActivity()).load(mList.get(i).getIcon()).into(imageView);
                tv_title.setText(mList.get(i).getTitle());
            }

            return view;
        }
    }

    private DelDialog delDialog;

    class MyDeviceadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList_device.size() + 1 == 9 ? 9 : mList_device.size() + 1;
        }

        @Override
        public Object getItem(int i) {
            return mList_device.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = LayoutInflater.from(getActivity()).inflate(R.layout.device_gv_edit_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.gridview_item);
            ImageView iv_del = view.findViewById(R.id.iv_device_del);
            TextView tv_name = view.findViewById(R.id.tv_name);
            if (flag) {
                iv_del.setVisibility(View.VISIBLE);
            } else {
                iv_del.setVisibility(View.GONE);
            }
            tv_name.setText("房间" + i);
            if (mList_device.size() == i) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(getActivity()).load(R.drawable.home_add).into(imageView);
                tv_name.setText("其他");
                iv_del.setVisibility(View.GONE);
                if (i == 10) {
                    imageView.setVisibility(View.GONE);
                }
            } else {
                Glide.with(getActivity()).load(mList_device.get(i)).into(imageView);
            }
            iv_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delDialog = new DelDialog(getActivity(), R.style.MyDialogStyle, clicker, "是否删除房间？");
                    delDialog.show();
                }
            });
            return view;
        }
    }

    public View.OnClickListener clicker = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_del_ok:
                    SmartToast.show("删除");
                    delDialog.dismiss();
                    break;
            }
        }
    };
}
