package com.android.lehuitong.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.android.lehuitong.activity.ShopDetailActivity;
import com.android.lehuitong.component.Eatholder;
import com.android.lehuitong.protocol.SHOP;
import com.funmi.lehuitong.R;

public class EatAdapter extends BaseAdapter {
	private Context c;
	private LayoutInflater mInflater;
	private List<SHOP> data;

	public EatAdapter(Context context, List<SHOP> data) {
		this.c = context;
		this.mInflater = LayoutInflater.from(context);
		this.data = data;
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Eatholder eatholder = null;
		if (convertView == null) {
			eatholder = new Eatholder(c);
			convertView = mInflater.inflate(R.layout.item_fragment_eat_listview, null);
			eatholder.initHolder(convertView, R.id.eat_listview_RelativeLayout, R.id.eat_listview_Image_word, R.id.eat_listview_hui, R.id.eat_listview_address, R.id.eat_listview_distance,
					R.id.eat_listview_order_count, R.id.eat_listview_phone,R.id.shop_img);
			convertView.setTag(eatholder);
		} else {
			eatholder = (Eatholder) convertView.getTag();
		}

		SHOP shop = data.get(position);

		eatholder.setInfo(shop.shop_id, shop.shop_name, shop.shop_address, shop.shop_phone, shop.shop_lng, shop.shop_lat, shop.shop_order_num, shop.shop_distance,shop.shop_pic_url);
		return convertView;

	}

}
