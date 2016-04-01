package com.android.lehuitong.component;

import android.content.Context;
import android.widget.TextView;

import com.BeeFramework.view.WebImageView;
import com.funmi.lehuitong.R;

public class BuyOrderChildHolder {

	public TextView order_name;//订单名字
	public TextView buy_count;//购买数量
	public WebImageView goodsImg;
	private Context context;
	public BuyOrderChildHolder(Context context){
		this.context = context;
	}
	public void setChildInfo(String name,String goods_number,String imgUrl){
		this.order_name.setText(name);
		this.buy_count.setText(goods_number);
		this.goodsImg.setImageWithURL(context, imgUrl,R.drawable.default_image);
	}
	
	
	
}
