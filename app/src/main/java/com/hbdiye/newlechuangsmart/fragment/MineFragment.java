package com.hbdiye.newlechuangsmart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.FamilyMemberActivity;
import com.hbdiye.newlechuangsmart.activity.MyDeviceActivity;
import com.hbdiye.newlechuangsmart.activity.PersonInfoActivity;
import com.hbdiye.newlechuangsmart.activity.SettingActivity;
import com.hbdiye.newlechuangsmart.view.GetPhotoPopwindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class MineFragment extends Fragment {
    @BindView(R.id.iv_mine_er_code)
    ImageView ivMineErCode;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.tv_mine_name)
    TextView tvMineName;
    @BindView(R.id.tv_mine_family_name)
    TextView tvMineFamilyName;
    @BindView(R.id.ll_mine_sys)
    LinearLayout llMineSys;
    @BindView(R.id.ll_mine_family_member)
    LinearLayout llMineFamilyMember;
    @BindView(R.id.ll_mine_about_us)
    LinearLayout llMineAboutUs;
    @BindView(R.id.ll_mine_setting)
    LinearLayout llMineSetting;
    @BindView(R.id.ll_parent)
    LinearLayout llParent;
    @BindView(R.id.ll_mine_devices)
    LinearLayout llMineDevices;
    @BindView(R.id.rl_mine_info)
    RelativeLayout rlMineInfo;
    private Unbinder bind;

    private GetPhotoPopwindow getPhotoPopwindow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        bind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @OnClick({R.id.iv_mine_er_code, R.id.profile_image, R.id.ll_mine_sys, R.id.ll_mine_family_member, R.id.ll_mine_about_us, R.id.ll_mine_setting, R.id.ll_mine_devices, R.id.rl_mine_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_mine_er_code:
                //二维码图片
                break;
            case R.id.profile_image:
                //头像
                getPhotoPopwindow = new GetPhotoPopwindow(getActivity(), imageClick);
                getPhotoPopwindow.showPopupWindowBottom(llParent);
                break;
            case R.id.rl_mine_info:
                startActivity(new Intent(getActivity(),PersonInfoActivity.class));
                break;
            case R.id.ll_mine_sys:
                break;
            case R.id.ll_mine_family_member:
                //家庭成员
                startActivity(new Intent(getActivity(),FamilyMemberActivity.class));
                break;
            case R.id.ll_mine_about_us:
                break;
            case R.id.ll_mine_devices:
                //我的设备
                startActivity(new Intent(getActivity(),MyDeviceActivity.class));
                break;
            case R.id.ll_mine_setting:
                //设置
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            default:
                break;
        }
    }

    public View.OnClickListener imageClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.civ_head1:
                    Glide.with(getActivity()).load(R.drawable.ic_head_01).into(profileImage);
                    getPhotoPopwindow.dismiss();
                    break;
                case R.id.civ_head2:
                    Glide.with(getActivity()).load(R.drawable.ic_head_02).into(profileImage);
                    getPhotoPopwindow.dismiss();
                    break;
                case R.id.civ_head3:
                    Glide.with(getActivity()).load(R.drawable.ic_head_03).into(profileImage);
                    getPhotoPopwindow.dismiss();
                    break;
                case R.id.civ_head4:
                    Glide.with(getActivity()).load(R.drawable.ic_head_04).into(profileImage);
                    getPhotoPopwindow.dismiss();
                    break;
                case R.id.civ_head5:
                    Glide.with(getActivity()).load(R.drawable.ic_head_05).into(profileImage);
                    getPhotoPopwindow.dismiss();
                    break;
                case R.id.civ_head6:
                    Glide.with(getActivity()).load(R.drawable.ic_head_06).into(profileImage);
                    getPhotoPopwindow.dismiss();
                    break;
                case R.id.civ_head7:
                    Glide.with(getActivity()).load(R.drawable.ic_head_07).into(profileImage);
                    getPhotoPopwindow.dismiss();
                    break;
                case R.id.civ_head8:
                    Glide.with(getActivity()).load(R.drawable.ic_head_08).into(profileImage);
                    getPhotoPopwindow.dismiss();
                    break;
                default:
                    break;
            }
        }
    };
}
