package com.hbdiye.newlechuangsmart.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.MessageAdapter;
import com.hbdiye.newlechuangsmart.bean.MessageBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.util.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.MESSAGELIST;

public class MessageActivity extends BaseActivity {

    @BindView(R.id.rv_message)
    RecyclerView rvMessage;

    private MessageAdapter adapter;
    private List<MessageBean.MessageList> mList=new ArrayList<>();
    private String token;
    private int page=1;
    private int size=20;

    @Override
    protected void initData() {
        token = (String) SPUtils.get(this,"token","");
        messageList(page);
    }

    private  void messageList(int page) {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(MESSAGELIST))
                .addParams("token",token)
                .addParams("page",page+"")
                .addParams("pageSize",size+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MessageBean messageBean = new Gson().fromJson(response, MessageBean.class);
                        if (messageBean.errcode.equals("0")){
                            List<MessageBean.MessageList> messageList = messageBean.messageList;
                            if (messageList.size()>0){
                                mList.addAll(messageList);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    @Override
    protected String getTitleName() {
        return "消息";
    }

    @Override
    protected void initView() {
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMessage.setLayoutManager(manager);
        adapter=new MessageAdapter(mList);
        rvMessage.setAdapter(adapter);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
