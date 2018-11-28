package com.hbdiye.newlechuangsmart.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.WriterException;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.util.DensityUtils;
import com.hbdiye.newlechuangsmart.zxing.encoding.EncodingHandler;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyErCodeActivity extends BaseActivity {

    @BindView(R.id.iv_myercode)
    ImageView ivMyercode;

    @Override
    protected void initData() {
//        Bitmap qrCode= EncodingHandler.createQRCode("123", DensityUtils.dp2px(this, 300),DensityUtils.dp2px(this, 300),null);
        try {
            Bitmap qrCode1 = EncodingHandler.createQRCode("123", DensityUtils.dp2px(this, 800));
            ivMyercode.setImageBitmap(qrCode1);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getTitleName() {
        return "我的二维码";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_my_er_code;
    }

}
