package com.hbdiye.newlechuangsmart.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemSettingView extends RelativeLayout {

    @BindView(R.id.iv_start_pic)
    ImageView ivStartPic;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_kaiguan)
    ImageView ivKaiguan;
    private final TypedArray attributes;

    public ItemSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(getContext(), R.layout.itemsettingview, this);//先有孩子，再去找爹，喜当爹
        ButterKnife.bind(this, view);


        attributes = context.obtainStyledAttributes(attrs, R.styleable.ItemSettingView);

        if (attributes == null) {
            return;
        }
        initView();
    }

    private void initView() {
        //处理icon图片
        int leftDrawable = attributes.getResourceId(R.styleable.ItemSettingView_left_drawable, -1);
        if (leftDrawable != -1) {
            ivStartPic.setBackgroundResource(leftDrawable);
        } else {
            ivStartPic.setVisibility(GONE);
        }
        //处理title文字
        String titleText = attributes.getString(R.styleable.ItemSettingView_title_text);
        if (!TextUtils.isEmpty(titleText)) {
            tvName.setText(titleText);
        }

        //如果不是图片标题 则获取文字标题
        String decText = attributes.getString(R.styleable.ItemSettingView_dec_text);
        if (!TextUtils.isEmpty(decText)) {
            tvContent.setText(decText);
        } else {
            tvContent.setVisibility(GONE);
        }
        attributes.recycle();
    }

}

