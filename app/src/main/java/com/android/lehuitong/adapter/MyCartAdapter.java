package com.android.lehuitong.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.BeeFramework.view.WebImageView;
import com.alipay.sdk.app.t;
import com.android.lehuitong.component.MyCartHolderpter;
import com.android.lehuitong.protocol.GOODS_LIST;
import com.android.lehuitong.protocol.ProtocolConst;
import com.funmi.lehuitong.R;

import android.R.integer;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

public class MyCartAdapter extends BaseAdapter {
	String addmuber;
	String count;
	Boolean pressBoolean = true;
	public static int setNumberCount = 0;
	private Map<Integer, Integer> addNumberMap = new HashMap<Integer, Integer>();
	private Map<Integer, String> price = new HashMap<Integer, String>();
	public Map<Integer, Boolean> delete = new HashMap<Integer, Boolean>();
	private Map<Integer, Boolean> selectStatus = new HashMap<Integer, Boolean>();
	private Context context;
	boolean b, select, finish;
	private List<GOODS_LIST> list;
	private Handler handler;
	private String totalPrice;
	private int selectNum = 0;
	private String mprice;

	public MyCartAdapter(Context context, boolean b, boolean select, boolean finish, Handler handler) {
		this.context = context;
		this.b = b;
		this.select = select;
		this.finish = finish;
		this.handler = handler;

	}

	public String getTotalprice() {
		return totalPrice;
	}

	public void bindData(List<GOODS_LIST> list) {
		this.list = list;
		for (int i = 0; i < list.size(); i++) {
			selectStatus.put(i, select);
		}

	}

	public void getdata(String count) {
		this.count = count;
	}

	public boolean isB() {
		return b;
	}

	public void setB(boolean b) {
		this.b = b;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public Map<Integer, Integer> getAddNumberMap() {
		return addNumberMap;
	}

	public void setAddNumberMap(Map<Integer, Integer> addNumberMap) {
		this.addNumberMap = addNumberMap;
	}

	public Map<Integer, Boolean> getDelete() {
		return delete;
	}

	public void setDelete(Map<Integer, Boolean> delete) {
		this.delete = delete;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Map<Integer, String> getPrice() {
		return price;
	}

	public void setPrice(Map<Integer, String> price) {
		this.price = price;
	}

	@Override
	public int getCount() {
		return list.size();
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
		final MyCartHolderpter holder;
		final GOODS_LIST good_list = list.get(position);
		if (convertView == null) {
			holder = new MyCartHolderpter(context);
			convertView = LayoutInflater.from(context).inflate(R.layout.item_my_cart, null);
			holder.mycar_choice=(LinearLayout) convertView.findViewById(R.id.mycar_chioce);
			holder.trade_name = (TextView) convertView.findViewById(R.id.trade_name);
			holder.goods_cart = (TextView) convertView.findViewById(R.id.goods_cart);
			holder.price = (TextView) convertView.findViewById(R.id.price);
			holder.shop_cart_subtract = (ImageView) convertView.findViewById(R.id.shop_cart_subtract);
			holder.shop_cart_add = (ImageView) convertView.findViewById(R.id.shop_cart_add);
			holder.have_choose = (ImageView) convertView.findViewById(R.id.have_choose);
			holder.shop_cart = (TextView) convertView.findViewById(R.id.shop_cart);
			holder.edittext_relativelayout = (RelativeLayout) convertView.findViewById(R.id.edittext_relativelayout);
			holder.edittext_linearlayout = (LinearLayout) convertView.findViewById(R.id.edittext_linearlayout);
			holder.goodsImg = (WebImageView) convertView.findViewById(R.id.cart_image);
			convertView.setTag(holder);
		} else {
			holder = (MyCartHolderpter) convertView.getTag();
		}
		if (b == true) {
			holder.edittext_relativelayout.setVisibility(View.GONE);
			holder.edittext_linearlayout.setVisibility(View.VISIBLE);
			if (setNumberCount < list.size()*2) {
				holder.shop_cart.setText(good_list.goods_number);
				setNumberCount++;
			}
		}
		if (selectStatus.get(position)) {
			holder.have_choose.setImageResource(R.drawable.my_shopping_cart_choice_icon);
			if (price.get(position) != null) {
				delete.remove(position);
				price.remove(position);
			}
			delete.put(position, true);
//			if(list.get(position).is_promote.equals("1")){
//				price.put(position, list.get(position).promote_price);	
//			}else{
				price.put(position, list.get(position).subtotal);	
//			}
		} else {
			holder.have_choose.setImageResource(R.drawable.my_hopping_cart_not_selected_icon);
			if (price.get(position) != null) {
				delete.remove(position);
				price.remove(position);
			}
			Message message = Message.obtain();
			message.obj = "false";
			handler.sendMessage(message);
		}

		selectNum = 0;
		for (int i = 0; i < list.size(); i++) {
			if (selectStatus.get(i)) {
				selectNum++;
			}else {
				break;
			}
		}
		if (selectNum==list.size()) {
			Message message = Message.obtain();
			message.obj = "true";
			handler.sendMessage(message);
		}
		String str_temp = "price";
		Message message = Message.obtain();
		message.obj = str_temp;
		handler.sendMessage(message);

		holder.shop_cart_subtract.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String Str = holder.shop_cart.getText().toString();
				int date = Integer.parseInt(Str);
				int subtractdata = date;
				if (subtractdata > 1) {
					subtractdata--;
					addmuber = String.valueOf(subtractdata);
					holder.shop_cart.setText(addmuber);
					if (subtractdata != date) {
						addNumberMap.put(position, subtractdata);
					} else {
						addNumberMap.put(position, date);
					}
					notifyDataSetChanged();
				}

			}
		});
		holder.shop_cart_add.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				String Str = holder.shop_cart.getText().toString();
				int date = Integer.parseInt(Str);
				int adddata = date;
				adddata++;
				if (adddata != date) {
					addNumberMap.put(position, adddata);
				} else {
					addNumberMap.put(position, date);
				}
				addmuber = String.valueOf(adddata);
				holder.shop_cart.setText(addmuber);
				notifyDataSetChanged();
			}

		});
		holder.mycar_choice.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (selectStatus.get(position) == true) {
					holder.have_choose.setImageResource(R.drawable.my_shopping_cart_not_selected_icon);
					selectStatus.remove(position);
					selectStatus.put(position, false);
					notifyDataSetChanged();
					delete.put(position, false);
					delete.remove(position);
					price.remove(position);
					String str_temp = "price";
					Message message = Message.obtain();
					message.obj = str_temp;
					handler.sendMessage(message);
				} else if (selectStatus.get(position) == false) {
					selectStatus.remove(position);
					selectStatus.put(position, true);
					holder.have_choose.setImageResource(R.drawable.my_shopping_cart_choice_icon);
//					if(list.get(position).is_promote.equals("1")){	
//						price.put(position, list.get(position).promote_price);
//					}else{
						price.put(position, list.get(position).subtotal);
//					}
					notifyDataSetChanged();
					delete.put(position, true);
					String str_temp = "price";
					Message message = Message.obtain();
					message.obj = str_temp;
					handler.sendMessage(message);
				}
			}
		});
		String mname = good_list.goods_name;
		String mgoods_number;
		if (finish == true) {
			mgoods_number = count;
			notifyDataSetChanged();
		} else {
			mgoods_number = good_list.goods_number;
		}
//		if(good_list.is_promote != null && good_list.is_promote.equals("1")){
//			mprice = good_list.promote_price;
//		}else{
		mprice = good_list.subtotal;
//		}
		String imgUrl = good_list.img.thumb;
		holder.SetMyCouponsInfo(mname, mgoods_number, mprice, imgUrl);
		return convertView;
	}

}
