package com.android.lehuitong.component;

import android.widget.TextView;

import com.BeeFramework.view.WebImageView;
import com.funmi.lehuitong.R;

public class BuyOrederHolder {
	
	public WebImageView oreder_image;//购买订单头像
	public TextView order_number;//订单号
	public TextView order_price;//订单价格
	public TextView order_name;//订单名字
	public TextView buy_count;//购买数量
	public TextView order_cancel;//取消订单
	public TextView order_pay;//支付订单
	public TextView order_state;//订单状态
	public TextView buy_agin;//订单状态
	public TextView order_delete;//订单状态
	public void SetMyCouponsInfo(String order_nuber, String order_price, String order_name, String buy_count,String order_cancel,String order_pay,String order_state){
		this.oreder_image.setImageResource(R.id.oreder_image);
		this.order_number.setText(order_nuber);
		this.order_price.setText(order_price);
		this.order_name.setText(order_name);
		this.buy_count.setText(buy_count);
		this.order_cancel.setText(order_cancel);
		this.order_pay.setText(order_pay);
		this.order_state.setText(order_state);
	}		
}
