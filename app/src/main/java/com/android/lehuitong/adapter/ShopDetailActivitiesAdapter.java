package com.android.lehuitong.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.lehuitong.component.ShopDetailActivitiesHolder;
import com.android.lehuitong.component.ShopDetailCousponHolder;
import com.android.lehuitong.model.HomeTitleTextUtil;
import com.android.lehuitong.protocol.GOODS;
import com.android.lehuitong.protocol.SHOP;
import com.funmi.lehuitong.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ShopDetailActivitiesAdapter extends BaseAdapter {

	private Context context;
	private List<GOODS> goodsList = new ArrayList<GOODS>();
	private String onlyActivity;
	public ShopDetailActivitiesAdapter(Context context) {
		this.context = context;
	}

	public void bindData(List<GOODS> goodsList,String onlyActivity) {
		
		 if(onlyActivity.equals("ktv")){
			for (int i = 0; i < goodsList.size(); i++) {
				if (goodsList.get(i).cat_id.equals("997")) {
					this.goodsList.add(goodsList.get(i));
				}
			}
		}else{
			this.goodsList = goodsList;	
		}
	}

	@Override
	public int getCount() {
		return goodsList.size();
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
		GOODS goods = goodsList.get(position);
		String name = goods.goods_name;
		String price = goods.shop_price;
		String imgUrl = goods.goods_thumb;
		String brief = goods.goods_brief;
		String startTime = goods.promote_start_date;
		String endTime = goods.promote_end_date;
		String sales = goods.sales_volume;
		String id = goods.goods_id;

		ShopDetailCousponHolder cousponHolder;
		ShopDetailActivitiesHolder activitiesHolder;
		// cat_id =996是优惠券，997是优惠活动
		

		if (goods.cat_id.equals("997")) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_shop_detail_activities, null);
			activitiesHolder = new ShopDetailActivitiesHolder(context);
			activitiesHolder.initView(convertView, R.id.img, R.id.name, R.id.price, R.id.time, R.id.sales,R.id.activitydetail);
			activitiesHolder.setActivityInfo(imgUrl, name, brief, null, startTime, endTime,id);
		} else {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_shop_detail_coupons, null);
			cousponHolder = new ShopDetailCousponHolder(context);
			cousponHolder.initView(convertView, R.id.img, R.id.name, R.id.price, R.id.brief, R.id.sales,R.id.coupondetail);
			cousponHolder.setCousponInfo(imgUrl, name, price, brief, sales,id);
		}
		
		return convertView;
	}

}
