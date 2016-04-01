package com.android.lehuitong.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.activity.HiActivity;
import com.android.lehuitong.activity.MyCouponsActivity;
import com.android.lehuitong.activity.ShopDetailActivity;
import com.android.lehuitong.component.MyCouponsHolder;
import com.android.lehuitong.protocol.BONUS;
import com.android.lehuitong.protocol.KTV_ORDER;
import com.funmi.lehuitong.R;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyCouponsAdapter extends BaseAdapter {

	private Context context;
	private List<BONUS> orderList;
	//selectStatus 选中状态
	Boolean select,selectStatus=true;
	//存要使用的红包
	private Map<Integer, Boolean> isSelect = new HashMap<Integer, Boolean>();
	public MyCouponsAdapter(Context context,Boolean select) {
		this.context = context;
		this.select=select;
	}

	public void bindData(List<BONUS> orderList) {
		this.orderList = orderList;
	}
	

	public Map<Integer, Boolean> getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(Map<Integer, Boolean> isSelect) {
		this.isSelect = isSelect;
	}

	@Override
	public int getCount() {
		return orderList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final MyCouponsHolder holder ;
		final BONUS list = orderList.get(position);
		String name = "";
		String price = "";
		String data = "";
		String shop_name = "";
		if (convertView == null) {
			holder = new MyCouponsHolder(context);
			final LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_my_coupons, null);
			holder.packet_name = (TextView) convertView
					.findViewById(R.id.packet_name);
			holder.packet_date = (TextView) convertView
					.findViewById(R.id.packet_date);
			holder.packet_price = (TextView) convertView
					.findViewById(R.id.packet_price);
			holder.enter_shop = (TextView) convertView
					.findViewById(R.id.enter_shop);
			holder.shop_name_packet = (TextView) convertView
					.findViewById(R.id.shop_name_packet);
			holder.shop_layout = (RelativeLayout) convertView
					.findViewById(R.id.shop_layout);
			holder.coupons_chose=(ImageView) convertView.findViewById(R.id.coupons_chose);
			holder.coupon_item=(LinearLayout) convertView.findViewById(R.id.coupon_item);
			convertView.setTag(holder);
		} else {
			holder = (MyCouponsHolder) convertView.getTag();
		}
		holder.enter_shop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(list.seller.can_order.equals("0")){
			Intent intent=new Intent(MyCouponsActivity.activity, ShopDetailActivity.class);
			intent.putExtra("shop_id", list.seller_id);
			MyCouponsActivity.activity.startActivity(intent);
				}else{
					Intent intent=new Intent(MyCouponsActivity.activity, HiActivity.class);
					String sellerId=list.seller_id;
					intent.putExtra("shop_id", sellerId);
					intent.putExtra("type", "bonus");
					MyCouponsActivity.activity.startActivity(intent);
				}
			}
		});
		holder.coupon_item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (isSelect.isEmpty()) {
					isSelect.put(position, true);
					notifyDataSetChanged();
				}
				else if (!isSelect.isEmpty()) {
					isSelect.clear();
					isSelect.put(position, true);
					notifyDataSetChanged();
				}
 		
			}
		});
		if (!isSelect.isEmpty()&&isSelect.get(position)!=null&&isSelect.get(position)==true) {
			holder.coupons_chose.setImageResource(R.drawable.my_shopping_cart_choice_icon);
		}else {
			holder.coupons_chose.setImageResource(R.drawable.my_shopping_cart_not_selected_icon);
		}
		if (select==true) {
			holder.coupons_chose.setVisibility(View.VISIBLE);
		}
		name = list.type_name;
		price = list.type_money;
		if (list.seller != null) {
			shop_name = list.seller.shop_name;
			holder.shop_layout.setVisibility(View.VISIBLE);
		} else {
			holder.shop_layout.setVisibility(View.GONE);
		}
		String starTime = list.use_start_date;
		String endTime = list.use_end_date;
		
		data = gettime(starTime) + "至" + gettime(endTime);
		holder.SetMyCouponsInfo(name, price, data, shop_name);
		
		return convertView;
	}

	/** 日期格式转换 */
	@SuppressWarnings("unused")
	public String gettime(String time) {

		String[] date = time.split("/");
		String year = null;
		String month = null;
		String mdate = null;
		for (int i = 0; i < date.length; i++) {
			year = date[0];
			month = date[1];
			mdate = date[2];
		}

		String month2 = month.substring(1, 2);
		String date2 = mdate.substring(0, 2);
		// pTime = year + "-" + month + "-" + date2;
		String times = month2 + "月" + date2 + "日";
		return times;

	}
	

}
