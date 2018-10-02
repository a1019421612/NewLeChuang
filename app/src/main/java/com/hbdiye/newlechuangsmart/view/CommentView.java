package com.hbdiye.newlechuangsmart.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CommentView extends LinearLayout {
    @BindView(R.id.tv_condition_item)
    TextView tvConditionItem;
    @BindView(R.id.tv_condition_attr)
    TextView tvConditionAttr;
    @BindView(R.id.tv_condition_value)
    TextView tvConditionValue;
    @BindView(R.id.iv_condition_del)
    ImageView ivConditionDel;

    public CommentView(Context context) {
        super(context);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.condition_item, this);
        ButterKnife.bind(this, view);
    }

    public CommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ItemCommentView);
        if (attributes != null) {
//            String nickname = attributes.getString(R.styleable.ItemCommentView_comment_nickname);
//            if (!TextUtils.isEmpty(nickname)) {
//                tvNicknameComment.setText(nickname);
//            }

            String name = attributes.getString(R.styleable.ItemCommentView_comment_name);
            String attr = attributes.getString(R.styleable.ItemCommentView_comment_attr);
            String value = attributes.getString(R.styleable.ItemCommentView_comment_value);

            attributes.recycle();
        }
    }
    public TextView getTvConditionItem(){
        return tvConditionItem;
    }
}
