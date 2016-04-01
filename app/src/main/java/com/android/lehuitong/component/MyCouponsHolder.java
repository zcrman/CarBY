package com.android.lehuitong.component;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 我的红包列表
 * 
 * @author shenlw
 *
 */
public class MyCouponsHolder {

	public TextView packet_name;
	public TextView packet_price;
	public TextView packet_date;
	public TextView shop_name_packet;
	public TextView enter_shop;
	public RelativeLayout shop_layout;
	private Context context;
	public ImageView coupons_chose;
	public LinearLayout coupon_item;
	public MyCouponsHolder(Context context) {
		this.context = context;
	}

	/**
	 * @param name
	 * @param brief
	 * @param date
	 * @param effective_date
	 */
	public void SetMyCouponsInfo(String name, String packet_price, String packet_date,String shop_name_packet) {
		this.packet_name.setText(name);
		this.packet_price.setText(packet_price);
		try{
		this.packet_date.setText(packet_date);
		}catch(Exception e){
			e.printStackTrace();
		}
		this.shop_name_packet.setText(shop_name_packet);
	}

}
