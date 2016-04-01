package com.android.lehuitong.adapter;

import java.util.List;

import com.android.lehuitong.component.TallyOrderHolder;
import com.android.lehuitong.protocol.GOODS_LIST;
import com.android.lehuitong.protocol.ORDER_GOODS_LIST;
import com.funmi.lehuitong.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class BuyOrderDetailAdpter extends BaseAdapter {
	private Context context;
	private List<ORDER_GOODS_LIST> orderList;
	private String mPrice;
	public BuyOrderDetailAdpter(Context context) {
		this.context = context;
	}

	public void bindData(List<ORDER_GOODS_LIST> orderList) {
		this.orderList = orderList;
	}
public void getPrice(String mPrice){
	this.mPrice = mPrice;
}
	public int getCount() {
		return orderList.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		TallyOrderHolder holder = null;
		ORDER_GOODS_LIST orders = null;
		orders = orderList.get(position);
		String name = null;
		String number = null;
		String price = null;
		String imgUrl = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_tally_order, null);
			holder = new TallyOrderHolder(context);
			holder.initView(convertView);
			convertView.setTag(holder);
		} else {
			holder = (TallyOrderHolder) convertView.getTag();
		}

		if (orders != null) {
			name = orders.name;
			number = orders.goods_number;
			price = orders.formated_shop_price;
			imgUrl = orders.img.thumb;
		}
		holder.setOrderInfo(name, number, price, imgUrl);
		return convertView;
	}

}
