package com.hbdiye.newlechuangsmart.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;


public class DelDialog extends Dialog {
    private Context context;
    private TextView tv_del_ok,tv_del_content,tv_del_cancel;
    private View.OnClickListener clicerm;
    private String title;
    public DelDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public DelDialog(Context context, int theme, View.OnClickListener clicerm, String title) {
        super(context, theme);
        this.context = context;
        this.clicerm=clicerm;
        this.title=title;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deldialog);
        this.setCanceledOnTouchOutside(true);
        initViews();
    }
    public void initViews(){
        tv_del_ok=this.findViewById(R.id.tv_del_ok);
        tv_del_content=(TextView)this.findViewById(R.id.tv_del_content);
        tv_del_cancel=this.findViewById(R.id.tv_del_cancel);
        tv_del_content.setText(title);
        tv_del_ok.setOnClickListener(clicerm);
        tv_del_cancel.setOnClickListener(clicerm);
    }
    public String getSceneName(){
        return tv_del_content.getText().toString().trim();
    }
}
