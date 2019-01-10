package com.hbdiye.newlechuangsmart.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提示窗
 */
public class TipsDialog extends Dialog {

    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    private String yesStr;
    private String noStr;
    private String contentStr;

    private OnCancelCickListener onCancelCickListener;
    private OnConfirmClickListener onConfirmClickListener;

    public TipsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tips_dialog);
        ButterKnife.bind(this);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        initData();
    }

    public void setContent(String content) {
        this.contentStr = content;
    }

    private void initData() {
        if (contentStr != null) {
            tvContent.setText(contentStr);
        }

        if (noStr != null) {
            tvCancel.setText(noStr);
        }

        if (yesStr != null) {
            tvConfirm.setText(yesStr);
        }
    }

    @OnClick({R.id.tv_cancel, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                if (onCancelCickListener != null) {
                    onCancelCickListener.onCancelClick();
                }
                break;
            case R.id.tv_confirm:
                dismiss();
                if (onConfirmClickListener != null) {
                    onConfirmClickListener.onConfirmClick();
                }
                break;
        }
    }

    public interface OnConfirmClickListener {
        public void onConfirmClick();
    }

    public interface OnCancelCickListener {
        public void onCancelClick();
    }

    public void setOnCancelClickListener(String str, OnCancelCickListener onCancelCickListener) {
        if (str != null) {
            noStr = str;
        }
        this.onCancelCickListener = onCancelCickListener;
    }

    public void setOnConfrimlickListener(String str, OnConfirmClickListener onConfirmClickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.onConfirmClickListener = onConfirmClickListener;
    }
}
