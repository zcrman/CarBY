package com.android.lehuitong.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.android.lehuitong.activity.MyPurchaseDetailActivity;
import com.android.lehuitong.component.Purchaseholder;
import com.android.lehuitong.model.GoodsListModel;
import com.android.lehuitong.protocol.GOODS;
import com.android.lehuitong.protocol.PHOTO;
import com.android.lehuitong.protocol.SHOP;
import com.android.lehuitong.protocol.SIMPLEGOODS;
import com.external.activeandroid.util.Log;
import com.funmi.lehuitong.R;

public class PurchaseAdapter extends BaseAdapter {
	private Context c;
	private LayoutInflater mInflater;
	private List<SIMPLEGOODS> data;
	private List<GOODS> list;
	private GoodsListModel model;
	private SIMPLEGOODS goods;

	public PurchaseAdapter() {
	}

	public PurchaseAdapter(Context context, List<SIMPLEGOODS> data) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		Purchaseholder shopholder;
		if (convertView == null) {
			shopholder = new Purchaseholder(c);
			convertView = mInflater.inflate(R.layout.item_fragmeng_shop_list,
					null);
			shopholder.initHolder(convertView,
					R.id.item_fragment_shop_list_lin,
					R.id.item_fragment_shop_list_imageview1,
					R.id.item_fragment_shop_list_textview1,
					R.id.haitaopin_image1,
					R.id.item_fragment_shop_list_textview3,
					R.id.item_fragment_shop_list_textview5,
					R.id.item_fragment_shop_list_lay,
					R.id.item_fragment_shop_list_textview6,
					R.id.item_fragment_shop_list_textview7,
					R.id.item_fragment_shop_list_textview9,
					R.id.item_fragment_shop_list_textview10,
					R.id.item_fragment_shop_list_textview5);
			shopholder.item_fragment_shop_list_lin = (LinearLayout) convertView
					.findViewById(R.id.item_fragment_shop_list_lin);
//			shopholder.item_fragment_shop_list_lin
//					.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							Intent intent = new Intent(c,
//									MyPurchaseDetailActivity.class);
//							intent.putExtra("position", position);// 测试新增
//							c.startActivity(intent);
//
//						}
//					});
			convertView.setTag(shopholder);
		} else {
			shopholder = (Purchaseholder) convertView.getTag();
		}

		goods = data.get(position);
		PHOTO img = goods.img;
		try {
			shopholder.setInfo(goods.id, goods.shop_price, goods.name,
					goods.goods_id, goods.img, goods.num, goods.promote_start_date,
					goods.promote_end_date, goods.sales_volume,goods.promote_price,goods.is_haitao,goods.is_promote);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return convertView;
	}

	public static int dayForWeek(String pTime) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(pTime));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

}
