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
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.FamilyMemberActivity;
import com.hbdiye.newlechuangsmart.activity.MyDeviceActivity;
import com.hbdiye.newlechuangsmart.activity.MyErCodeActivity;
import com.hbdiye.newlechuangsmart.activity.PersonInfoActivity;
import com.hbdiye.newlechuangsmart.activity.RoomListActivity;
import com.hbdiye.newlechuangsmart.activity.SettingActivity;
import com.hbdiye.newlechuangsmart.bean.UserFamilyInfoBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.music.MainActivity;
import com.hbdiye.newlechuangsmart.util.EcodeValue;
import com.hbdiye.newlechuangsmart.util.IconByName;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.hbdiye.newlechuangsmart.view.GetPhotoPopwindow;
import com.hbdiye.newlechuangsmart.zxing.activity.CaptureActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

public class MineFragment extends Fragment {
    @BindView(R.id.iv_mine_er_code)
    ImageView ivMineErCode;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.tv_mine_name)
    TextView tvMineName;
    @BindView(R.id.tv_mine_family_phone)
    TextView tvMineFamilyPhone;
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
    @BindView(R.id.ll_mine_music)
    LinearLayout rlMineMusic;
    @BindView(R.id.ll_mine_rooms)
    LinearLayout llMineRooms;
    private Unbinder bind;

    private GetPhotoPopwindow getPhotoPopwindow;
    private String token;
    private UserFamilyInfoBean userFamilyInfoBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        bind = ButterKnife.bind(this, view);
        token = (String) SPUtils.get(getActivity(), "token", "");
        personInfo();
        return view;
    }

    private void personInfo() {
        OkHttpUtils.post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.USERANDFAMILYINFO))
                .addParams("token", token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        userFamilyInfoBean = new Gson().fromJson(response, UserFamilyInfoBean.class);
                        String errcode = userFamilyInfoBean.errcode;
                        if (errcode.equals("0")) {
                            String user_name = userFamilyInfoBean.user.name;
                            String family_name = userFamilyInfoBean.family.name;
                            String phone = userFamilyInfoBean.family.phone;
                            tvMineName.setText(user_name);
                            SPUtils.put(getActivity(), "nickName", user_name);
                            tvMineFamilyPhone.setText(phone);
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @OnClick({R.id.iv_mine_er_code, R.id.profile_image, R.id.ll_mine_sys, R.id.ll_mine_family_member, R.id.ll_mine_about_us,
            R.id.ll_mine_setting, R.id.ll_mine_devices, R.id.rl_mine_info, R.id.ll_mine_rooms, R.id.ll_mine_music})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_mine_er_code:
                //二维码图片
                startActivity(new Intent(getActivity(), MyErCodeActivity.class).putExtra("familyId", userFamilyInfoBean.user.familyId));
                break;
            case R.id.profile_image:
                //头像
                getPhotoPopwindow = new GetPhotoPopwindow(getActivity(), imageClick);
                getPhotoPopwindow.showPopupWindowBottom(llParent);
                break;
            case R.id.rl_mine_info:
                if (userFamilyInfoBean != null) {
                    startActivity(new Intent(getActivity(), PersonInfoActivity.class).putExtra("info", userFamilyInfoBean));
                }
                break;
            case R.id.ll_mine_sys:
                //flag为true时跳转扫描界面为加入家庭功能为false时为加入网关
                startActivity(new Intent(getActivity(), CaptureActivity.class).putExtra("camera", false).putExtra("flag", true));
                break;
            case R.id.ll_mine_family_member:
                //家庭成员
                startActivity(new Intent(getActivity(), FamilyMemberActivity.class));
                break;
            case R.id.ll_mine_about_us:
//                startActivity(new Intent(getActivity(), aboutu.class));
                break;
            case R.id.ll_mine_devices:
                //我的设备
                startActivity(new Intent(getActivity(), MyDeviceActivity.class));
                break;
            case R.id.ll_mine_rooms:
                //我的房间
                startActivity(new Intent(getActivity(), RoomListActivity.class));
                break;
            case R.id.ll_mine_setting:
                //设置
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.ll_mine_music:
                //背景音乐
                startActivity(new Intent(getActivity(), MainActivity.class));
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
                    updatePhoto("ic_head_01");
                    break;
                case R.id.civ_head2:
                    updatePhoto("ic_head_02");
                    break;
                case R.id.civ_head3:
                    updatePhoto("civ_head3");
                    break;
                case R.id.civ_head4:
                    updatePhoto("civ_head4");
                    break;
                case R.id.civ_head5:
                    updatePhoto("civ_head5");
                    break;
                case R.id.civ_head6:
                    updatePhoto("civ_head6");
                    break;
                case R.id.civ_head7:
                    updatePhoto("civ_head7");
                    break;
                case R.id.civ_head8:
                    updatePhoto("civ_head8");
                    break;
                default:
                    break;
            }
        }
    };

    private void updatePhoto(final String icon) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.UPDATEICON))
                .addParams("token", token)
                .addParams("icon", icon)
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
                            if (errcode.equals("0")) {
                                if (getPhotoPopwindow != null) {
                                    getPhotoPopwindow.dismiss();
                                }
                                Glide.with(getActivity()).load(IconByName.photoByName(icon)).into(profileImage);
                            } else {
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
    public void onResume() {
        super.onResume();
        personInfo();
    }
}
