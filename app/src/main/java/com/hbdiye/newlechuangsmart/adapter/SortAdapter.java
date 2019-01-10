package com.hbdiye.newlechuangsmart.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.SortModel;
import com.hbdiye.newlechuangsmart.hwactivity.AddRemoteControlActivity;
import com.hbdiye.newlechuangsmart.hwactivity.ChooseBrandActivity;

import java.util.List;


public class SortAdapter extends RecyclerView.Adapter<SortAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<SortModel> mData;
    private ChooseBrandActivity chooseBrandActivity;

    public SortAdapter(ChooseBrandActivity chooseBrandActivity, List<SortModel> data) {
        mInflater = LayoutInflater.from(chooseBrandActivity);
        mData = data;
        this.chooseBrandActivity = chooseBrandActivity;
    }

    @Override
    public SortAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_name, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tvName = (TextView) view.findViewById(R.id.tvName);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SortAdapter.ViewHolder holder, final int position) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });

        }

        holder.tvName.setText(this.mData.get(position).cname);

        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chooseBrandActivity, AddRemoteControlActivity.class);
                intent.putExtra("hwbDeviceId",chooseBrandActivity.getIntent().getStringExtra("hwbDeviceId"));
                intent.putExtra( "deviceName",chooseBrandActivity.getIntent().getStringExtra("deviceName"));
                intent.putExtra( "deviceId",chooseBrandActivity.getIntent().getStringExtra("deviceId"));
                intent.putExtra( "deviceUrl",chooseBrandActivity.getIntent().getIntExtra("deviceUrl",0));
                intent.putExtra("brandId",mData.get(position).brandId+"");
                intent.putExtra("spId",chooseBrandActivity.getIntent().getStringExtra("spId"));
                chooseBrandActivity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    //**********************itemClick************************
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    //**************************************************************

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 提供给Activity刷新数据
     * @param list
     */
    public void updateList(List<SortModel> list){
        this.mData = list;
        notifyDataSetChanged();
    }

    public Object getItem(int position) {
        return mData.get(position);
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return mData.get(position).inital.charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mData.get(i).inital;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

}
