package com.android.lehuitong.adapter;

import java.util.List;

import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.component.KtvLocationChooseListHolder;
import com.android.lehuitong.protocol.SHOP;
import com.funmi.lehuitong.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class KtvLocationListAdapter extends BaseAdapter {

	private List<SHOP> shopList;
	private Context mContext;

	public KtvLocationListAdapter(Context mContext) {
		this.mContext = mContext;
	}

	public void bindData(List<SHOP> shopList) {
		this.shopList = shopList;
	}

	@Override
	public int getCount() {

		return shopList.size();
	}

	@Override
	public Object getItem(int position) {

		return null;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		KtvLocationChooseListHolder holder = null;
		if (convertView == null) {
			holder = new KtvLocationChooseListHolder(mContext);
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ktv_location_choose, null);
			holder.name = (TextView) convertView.findViewById(R.id.location_name);
			holder.image = (WebImageView) convertView.findViewById(R.id.location_image);
			convertView.setTag(holder);
		} else {
			holder = (KtvLocationChooseListHolder) convertView.getTag();
		}

		SHOP shop = shopList.get(position);
		String name = shop.shop_name;
		String imageUrl = shop.shop_pic_url;

		holder.setLocationListInfo(imageUrl, name);

		return convertView;
	}

}
