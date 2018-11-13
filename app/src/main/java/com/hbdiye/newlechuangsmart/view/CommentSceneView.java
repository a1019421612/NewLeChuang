package com.hbdiye.newlechuangsmart.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CommentSceneView extends LinearLayout {

    @BindView(R.id.tv_scene_device_name)
    TextView tvSceneDeviceName;
    @BindView(R.id.tv_scene_device_attr)
    TextView tvSceneDeviceAttr;
    @BindView(R.id.iv_scene_detail_del)
    ImageView ivSceneDetailDel;

    public CommentSceneView(Context context) {
        super(context);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.scene_device_item, this);
        ButterKnife.bind(this, view);
    }

    public CommentSceneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ItemCommentSceneView);
        if (attributes != null) {
//            String nickname = attributes.getString(R.styleable.ItemCommentView_comment_nickname);
//            if (!TextUtils.isEmpty(nickname)) {
//                tvNicknameComment.setText(nickname);
//            }

            String name = attributes.getString(R.styleable.ItemCommentSceneView_comment_scene_name);
            String attr = attributes.getString(R.styleable.ItemCommentSceneView_comment_scene_attr);
            if (!TextUtils.isEmpty(name)) {
                tvSceneDeviceName.setText(name);
            }
            if (!TextUtils.isEmpty(attr)) {
                tvSceneDeviceAttr.setText(attr);
            }
            attributes.recycle();
        }
    }

    public ImageView getImageViewDel() {
        return ivSceneDetailDel;
    }
    public TextView getTvSceneAttr(){
        return tvSceneDeviceAttr;
    }
    public void setTvSceneDeviceName(String value){
        tvSceneDeviceName.setText(value);
    }
    public void setTvSceneDeviceAttr(String value){
        tvSceneDeviceAttr.setText(value);
    }
}
