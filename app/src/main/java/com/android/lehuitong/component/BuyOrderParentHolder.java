package com.android.lehuitong.component;

import android.widget.TextView;

public class BuyOrderParentHolder {
	
	public TextView orderSn;
	public TextView orderMoney;
	
	public void setOrderParentInfo(String order_sn,String order_amount){
		orderSn.setText(order_sn);
		orderMoney.setText(order_amount);
		
	}
	
}
