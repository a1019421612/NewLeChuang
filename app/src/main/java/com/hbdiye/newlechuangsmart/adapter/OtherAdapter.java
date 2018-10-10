package com.hbdiye.newlechuangsmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.ChannelItem;

import java.util.List;

public class OtherAdapter extends BaseAdapter {
	private Context context;
	public List<ChannelItem> channelList;
	private TextView item_text;
	/** 是否可见 */
	boolean isVisible = true;
	/** 要删除的position */
	public int remove_position = -1;
	/**
	 * 删除图标是否可见
	 */
	boolean show=false;
	private RelativeLayout ri_delete;
	private ImageView imageview;

	public OtherAdapter(Context context, List<ChannelItem> channelList) {
		this.context = context;
		this.channelList = channelList;
	}

	@Override
	public int getCount() {
		return channelList == null ? 0 : channelList.size();
	}

	@Override
	public ChannelItem getItem(int position) {
		if (channelList != null && channelList.size() != 0) {
			return channelList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.subscribe_category_item, null);
		ri_delete = view.findViewById(R.id.ri_delete);
		imageview = view.findViewById(R.id.imageview);
		item_text = (TextView) view.findViewById(R.id.text_item);
		ChannelItem channel = getItem(position);
		item_text.setText(channel.getName());
		if (!isVisible && (position == -1 + channelList.size())){
			item_text.setText("");
			imageview.setVisibility(View.INVISIBLE);
			item_text.setVisibility(View.GONE);//在动画没有结束时，设置不可见
		}
		if(remove_position == position){
			item_text.setText("");
			item_text.setVisibility(View.GONE);
			imageview.setVisibility(View.INVISIBLE);
		}
		if (show){
			ri_delete.setVisibility(View.VISIBLE);
		}else {
			ri_delete.setVisibility(View.INVISIBLE);
		}
		ri_delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				channelList.remove(position);
				notifyDataSetChanged();
			}
		});
		return view;
	}
	
	/** 获取频道列表 */
	public List<ChannelItem> getChannnelLst() {
		return channelList;
	}
	
	/** 添加频道列表 */
	public void addItem(ChannelItem channel) {
		channelList.add(channel);
		notifyDataSetChanged();
	}
	public void  showDelIcon(boolean show){
		this.show=show;
		notifyDataSetChanged();
	}
	/** 设置删除的position */
	public void setRemove(int position) {
		remove_position = position;
		notifyDataSetChanged();
	}

	/** 删除频道列表 */
	public void remove() {
		channelList.remove(remove_position);
		remove_position = -1;
		notifyDataSetChanged();
	}
	/** 设置频道列表 */
	public void setListDate(List<ChannelItem> list) {
		channelList = list;
	}

	/** 获取是否可见 */
	public boolean isVisible() {
		return isVisible;
	}
	
	/** 设置是否可见 */
	public void setVisible(boolean visible) {
		isVisible = visible;
	}
}