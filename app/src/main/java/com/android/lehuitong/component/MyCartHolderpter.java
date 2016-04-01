package com.android.lehuitong.component;


import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.protocol.ProtocolConst;
import com.funmi.lehuitong.R;

import android.content.Context;
import android.net.wifi.WifiConfiguration.Protocol;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author Administrator
 *
 */
public class MyCartHolderpter {
	public TextView trade_name;
	public TextView goods_cart;
	public TextView price;
	public ImageView shop_cart_subtract;
	public ImageView shop_cart_add;
	public ImageView have_choose;
	public TextView shop_cart;
	public RelativeLayout edittext_relativelayout;
	public LinearLayout edittext_linearlayout;
	public WebImageView goodsImg;
	public LinearLayout mycar_choice;
	private Context mContext;
	public MyCartHolderpter(Context context){
		mContext = context;
	}
	public void SetMyCouponsInfo(String name, String mgoods_number, String price,String imgUrl){
		this.trade_name.setText(name);
		this.goods_cart.setText(mgoods_number);
		this.price.setText(price);
		this.goodsImg.setImageWithURL(mContext,imgUrl,R.drawable.default_image);
		
			
	}
	

}
