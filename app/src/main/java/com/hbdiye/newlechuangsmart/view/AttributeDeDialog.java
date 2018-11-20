package com.hbdiye.newlechuangsmart.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.AttrDialogAdapter;
import com.hbdiye.newlechuangsmart.adapter.AttrDialogDeAdapter;
import com.hbdiye.newlechuangsmart.bean.DeviceList;
import com.hbdiye.newlechuangsmart.bean.SceneDetailBean;

import java.util.List;

public class AttributeDeDialog extends Dialog {
    private TextView tv_attr_cancel, tv_attr_ok;
    private MyGridView gv_attr;
    private Context context;
    private View.OnClickListener clicerm;
    private AdapterView.OnItemClickListener gv_listener;
    private List<SceneDetailBean.SceneTaskList.DevActList> mList;
    private AttrDialogDeAdapter adapter;

    public AttributeDeDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public AttributeDeDialog(Context context, int theme, View.OnClickListener clicerm, List<SceneDetailBean.SceneTaskList.DevActList> mList, AdapterView.OnItemClickListener gv_listener) {
        super(context, theme);
        this.context = context;
        this.clicerm = clicerm;
        this.mList = mList;
        this.gv_listener = gv_listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attrbute_dialog);
        initViews();
        setCanceledOnTouchOutside(true);
    }

    private void initViews() {
        tv_attr_cancel = this.findViewById(R.id.tv_attr_cancel);
        tv_attr_ok = this.findViewById(R.id.tv_attr_ok);
        gv_attr = this.findViewById(R.id.gv_attr);
        tv_attr_ok.setOnClickListener(clicerm);
        tv_attr_cancel.setOnClickListener(clicerm);

        adapter = new AttrDialogDeAdapter(context, mList);
        gv_attr.setAdapter(adapter);
        gv_attr.setOnItemClickListener(gv_listener);
//        gv_attr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                adapter.setSeclection(position);
//                adapter.notifyDataSetChanged();
//            }
//        });
    }

    public void changeColor(int position) {
        adapter.setSeclection(position);
        adapter.notifyDataSetChanged();
    }
}
