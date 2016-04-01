package com.android.lehuitong.component;

import android.R.string;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiConfiguration.Protocol;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.activity.StartActivity;
import com.BeeFramework.fragment.BaseFragment;
import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.activity.MyEatInlehuiActivity;
import com.android.lehuitong.activity.ShopDetailActivity;
import com.android.lehuitong.protocol.ProtocolConst;
import com.funmi.lehuitong.R;

public class Eatholder {
	private Context mContext;
	public RelativeLayout eat_RelativeLayout;
	private TextView eat_Image_word;
	private TextView eat_hui;
	private TextView eat_address;
	private TextView eat_distance;
	private TextView eat_order_count;
	private TextView eat_listview_phone;
	private WebImageView shopImg;
	
	
	public Eatholder(Context context) {
		mContext=context;
	}
	
	public void initHolder(View view,int eat_RelativeLayout,int eat_Image_word,int eat_hui,int eat_address,
			int eat_distance,int eat_order_count,int eat_listview_phone,int shop_img){
		this.eat_RelativeLayout=(RelativeLayout)view.findViewById(eat_RelativeLayout);
		
		this.eat_Image_word=(TextView)view.findViewById(eat_Image_word);
		this.eat_hui=(TextView)view.findViewById(eat_hui);
		this.eat_address=(TextView)view.findViewById(eat_address);
		this.eat_distance=(TextView)view.findViewById(eat_distance);
		this.eat_order_count=(TextView)view.findViewById(eat_order_count);
		this.eat_listview_phone=(TextView)view.findViewById(eat_listview_phone);
		this.shopImg = (WebImageView) view.findViewById(shop_img);
		
	}
	
	public void setInfo(final String shop_id,String shop_Name,String shop_Address,
			final String shop_phone,String shop_lng,String shop_lat, String shop_Order_Num,String shop_distance,String imgUrl){
		eat_Image_word.setText(shop_Name);
		eat_address.setText(shop_Address);
		eat_order_count.setText(shop_Order_Num);
		eat_distance.setText(shop_distance+"km");
		shopImg.setImageWithURL(mContext,imgUrl, R.drawable.default_image);
		
		
		//jump to ShopDetailActivity
		this.eat_RelativeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,ShopDetailActivity.class);
				intent.putExtra("shop_id", shop_id);
				mContext.startActivity(intent);
			}
		});
		
		//call phone
		this.eat_listview_phone.setOnClickListener(new OnClickListener() {
			
			private Intent intent;

			@Override
			public void onClick(View v) {
				String uriString=shop_phone;
					intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+uriString));
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					mContext.startActivity(intent);
			}
		});
	}

}
