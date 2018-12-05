package com.hbdiye.newlechuangsmart.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.YiLiaoTiZhongBean;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YiLiaoTiZhongAdapter extends BaseQuickAdapter<YiLiaoTiZhongBean.Data.Lists,BaseViewHolder>{
    public YiLiaoTiZhongAdapter( @Nullable List<YiLiaoTiZhongBean.Data.Lists> data) {
        super(R.layout.yiliao_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, YiLiaoTiZhongBean.Data.Lists item) {
        String datetime = item.datetime;
        Pattern p = Pattern.compile("\\s");
        Matcher m = p.matcher(datetime);
        datetime = m.replaceAll("\n");
        helper.setText(R.id.tv_date,datetime);
    }
}
