package com.hbdiye.newlechuangsmart.devicefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.fragment.BaseFragment;
import com.zhouyou.view.seekbar.SignSeekBar;

import java.util.Locale;

public class TiaoSeDengFragment extends BaseFragment {
    private SignSeekBar signSeekBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_tiaosedeng,container,false);
        signSeekBar = (SignSeekBar) view.findViewById(R.id.seek_bar);
//        signSeekBar.getConfigBuilder()
//                .min(0)
//                .max(100)
//                .progress(2)
//                .trackColor(ContextCompat.getColor(getActivity(), R.color.color_gray))
//                .secondTrackColor(ContextCompat.getColor(getActivity(), R.color.color_blue))
//                .thumbColor(ContextCompat.getColor(getActivity(), R.color.color_blue))
//                .sectionTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary))
//                .sectionTextSize(16)
//                .thumbTextColor(ContextCompat.getColor(getActivity(), R.color.color_red))
//                .thumbTextSize(18)
//                .signColor(ContextCompat.getColor(getActivity(), R.color.color_green))
//                .signTextSize(18)
//                .sectionTextPosition(SignSeekBar.TextPosition.BELOW_SECTION_MARK)
//                .build();
        signSeekBar.setOnProgressChangedListener(new SignSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(SignSeekBar signSeekBar, int progress, float progressFloat, boolean fromUser) {
                //fromUser 表示是否是用户触发 是否是用户touch事件产生
                String s = String.format(Locale.CHINA, "onChanged int:%d, float:%.1f", progress, progressFloat);
//                progressText.setText(s);
            }

            @Override
            public void getProgressOnActionUp(SignSeekBar signSeekBar, int progress, float progressFloat) {
                String s = String.format(Locale.CHINA, "onActionUp int:%d, float:%.1f", progress, progressFloat);
//                progressText.setText(s);
            }

            @Override
            public void getProgressOnFinally(SignSeekBar signSeekBar, int progress, float progressFloat, boolean fromUser) {
                String s = String.format(Locale.CHINA, "onFinally int:%d, float:%.1f", progress, progressFloat);
                Log.e("sss", progress + "℃");
            }
        });
        return view;
    }
}
