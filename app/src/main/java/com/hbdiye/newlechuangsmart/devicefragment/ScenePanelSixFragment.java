package com.hbdiye.newlechuangsmart.devicefragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.SceneDetailActivity;
import com.hbdiye.newlechuangsmart.bean.KaiGuanBean;
import com.hbdiye.newlechuangsmart.bean.MenCiBean;
import com.hbdiye.newlechuangsmart.bean.SceneDetailBean;
import com.hbdiye.newlechuangsmart.fragment.BaseFragment;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.DEVICEDETAIL;

public class ScenePanelSixFragment extends BaseFragment implements View.OnClickListener {
    private String deviceid;
    private String token;
    private TextView tv_scenepanel_name;
    private LinearLayout ll_scene_1, ll_scene_2, ll_scene_3, ll_scene_4, ll_scene_5, ll_scene_6;

    public static ScenePanelSixFragment newInstance(String page) {
        Bundle args = new Bundle();

//        args.putInt(ARGS_PAGE, page);
        args.putString("deviceid", page);
        ScenePanelSixFragment fragment = new ScenePanelSixFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deviceid = getArguments().getString("deviceid");
        token = (String) SPUtils.get(getActivity(), "token", "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scenepanel_six, container, false);
        tv_scenepanel_name = view.findViewById(R.id.tv_scenepanel_name);
        ll_scene_1 = view.findViewById(R.id.ll_scene_1);
        ll_scene_2 = view.findViewById(R.id.ll_scene_2);
        ll_scene_3 = view.findViewById(R.id.ll_scene_3);
        ll_scene_4 = view.findViewById(R.id.ll_scene_4);
        ll_scene_5 = view.findViewById(R.id.ll_scene_5);
        ll_scene_6 = view.findViewById(R.id.ll_scene_6);

        ll_scene_1.setOnClickListener(this);
        ll_scene_2.setOnClickListener(this);
        ll_scene_3.setOnClickListener(this);
        ll_scene_4.setOnClickListener(this);
        ll_scene_5.setOnClickListener(this);
        ll_scene_6.setOnClickListener(this);

        return view;
    }

    @Override
    protected void onFragmentFirstVisible() {
        OkHttpUtils.post()
                .url(InterfaceManager.getInstance().getURL(DEVICEDETAIL))
                .addParams("token", token)
                .addParams("deviceId", deviceid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("sss", e.toString());
                        SmartToast.show("网络连接错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("sss", response);
                        KaiGuanBean kaiGuanBean = new Gson().fromJson(response, KaiGuanBean.class);
                        if (kaiGuanBean.errcode.equals("0")) {
                            String name = kaiGuanBean.data.name;
                            tv_scenepanel_name.setText(name);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_scene_1:
                scenePanelDetail("1");
                break;
            case R.id.ll_scene_2:
                scenePanelDetail("2");
                break;
            case R.id.ll_scene_3:
                scenePanelDetail("3");
                break;
            case R.id.ll_scene_4:
                scenePanelDetail("4");
                break;
            case R.id.ll_scene_5:
                scenePanelDetail("5");
                break;
            case R.id.ll_scene_6:
                scenePanelDetail("6");
                break;
        }
    }

    private void scenePanelDetail(String index) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(InterfaceManager.DEVICESCENEDETAILS))
                .addParams("token",token)
                .addParams("groupNo",index)
                .addParams("sceneNo",index)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        SceneDetailBean sceneDetailBean = new Gson().fromJson(response, SceneDetailBean.class);
                        if (sceneDetailBean.errcode.equals("0")){
                            String sceneId = sceneDetailBean.scene.id;
                            startActivity(new Intent(getActivity(), SceneDetailActivity.class).putExtra("sceneId",sceneId));
                        }
                    }
                });
    }

}
