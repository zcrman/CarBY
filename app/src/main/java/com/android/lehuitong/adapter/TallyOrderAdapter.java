package com.android.lehuitong.adapter;

import java.util.List;

import com.android.lehuitong.component.TallyOrderHolder;
import com.android.lehuitong.protocol.GOODORDER;
import com.android.lehuitong.protocol.GOODS_LIST;
import com.android.lehuitong.protocol.ORDER_GOODS_LIST;
import com.funmi.lehuitong.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TallyOrderAdapter extends BaseAdapter {

	private Context context;
	private List<GOODS_LIST> orderList;
	private List<ORDER_GOODS_LIST> list;
	private String mPrice;
	public TallyOrderAdapter(Context context) {
		this.context = context;
	}

	public void bindData(List<GOODS_LIST> orderList) {
		this.orderList = orderList;
	}

	public void bindDatas(List<ORDER_GOODS_LIST> list) {
		this.list = list;
	}
	public void getPrice(String mPrice){
		this.mPrice = mPrice;
	}
	public int getCount() {
		int i = 0;
		if (list!=null&&list.size() > 0) {
			i = list.size();
		} else if (orderList!=null&&orderList.size() > 0) {
			i = orderList.size();
		}
		return i;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		TallyOrderHolder holder = null;
		GOODS_LIST order = null;
		ORDER_GOODS_LIST orders=null;
		if (orderList!=null&&orderList.size()>0) {
			 order = orderList.get(position);
		}else if (list!=null&&list.size()>0) {
			 orders= list.get(position);
		}
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
		if (order!=null) {
			
			name = order.goods_name;
			number = order.goods_number;
			price = order.formated_goods_price;
			imgUrl = order.img.thumb;
		}else if (orders!=null) {
			name = orders.name;
			number = orders.goods_number;
			price = orders.formated_shop_price;
			imgUrl = orders.img.thumb;
		}
		 holder.setOrderInfo(name, number, price, imgUrl);
		return convertView;
	}

}
