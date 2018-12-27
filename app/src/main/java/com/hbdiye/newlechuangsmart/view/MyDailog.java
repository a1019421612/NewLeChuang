package com.hbdiye.newlechuangsmart.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;


/**
 * Created by Administrator on 2017/9/26.
 */

public class MyDailog {
//    Context context;
//    View.OnClickListener onClickListener;
//     Dialog noticeDialog;
//    public MyDailog(Context context,View.OnClickListener onClickListener){
//        this.onClickListener=onClickListener;
//        this.context=context;
//    }
//    public void Create(String title,String body,String left,String right){
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.dialog_version, null);
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        noticeDialog = builder.create();
//        noticeDialog.show();
//        noticeDialog.setCancelable(false);
//        noticeDialog.getWindow().setContentView(view);
//        TextView tv_title= (TextView) view.findViewById(R.id.tv_title);
//        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
//        TextView tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
//        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
//        tv_title.setText(title);//头部
//        tv_confirm.setText(left);//确定
//        tv_cancle.setText(right);
//        tv_content.setText(body);
//        tv_confirm.setOnClickListener(onClickListener);
//        tv_cancle.setOnClickListener(onClickListener);
//    }
//   public void dissmis(){
//       noticeDialog.dismiss();
//   }
Context context;
    View.OnClickListener onClickListener;
    Dialog noticeDialog;

    public MyDailog(Context context, View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.context = context;
    }

    public void Create(String title, String body, String left, String right) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_version, null);//dialog_version
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.dialog);
        noticeDialog = builder.create();
        noticeDialog.show();
        noticeDialog.setCancelable(false);
        noticeDialog.getWindow().setContentView(view);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        TextView tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_title.setText(title);//头部
        tv_cancle.setText(right);//取消按钮
        tv_content.setText(body);
        tv_confirm.setText(left);//确定按钮
        tv_confirm.setOnClickListener(onClickListener);
        tv_cancle.setOnClickListener(onClickListener);
    }

    public void dissmis() {
        noticeDialog.dismiss();
    }
}
