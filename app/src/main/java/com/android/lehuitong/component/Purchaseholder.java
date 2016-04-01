package com.android.lehuitong.component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.activity.MyPurchaseDetailActivity;
import com.android.lehuitong.adapter.PurchaseAdapter;
import com.android.lehuitong.protocol.PHOTO;
import com.funmi.lehuitong.R;

public class Purchaseholder {
	private Context mContext;
	public LinearLayout item_fragment_shop_list_lin;
	private WebImageView item_fragment_shop_list_imageview1;//
	private TextView shop_name;
	private ImageView shop_haitaoImage;
	private TextView shop_price;
	private TextView shop_num;
	private LinearLayout item_fragment_shop_list_lay;
	private TextView item_fragment_shop_list_textview5;
	private TextView item_fragment_shop_list_textview6;
	private TextView item_fragment_shop_list_textview7;
	private TextView item_fragment_shop_list_textview9;
	private TextView item_fragment_shop_list_textview10;
	private Date promoteTime;
	private SimpleDateFormat format;
	private String isPromotePrice;
	public Purchaseholder(Context context){
		mContext=context;
	}
	public void initHolder(View view,int item_fragment_shop_list_lin,
			int item_fragment_shop_list_imageview1,int shop_name,
			int shop_haitaoImage,
			int shop_price,
			int shop_num,int item_fragment_shop_list_lay,
			int item_fragment_shop_list_textview6,int item_fragment_shop_list_textview7,
			int item_fragment_shop_list_textview9,int item_fragment_shop_list_textview10,int item_fragment_shop_list_textview5){
		this.item_fragment_shop_list_lin=(LinearLayout)view.findViewById(item_fragment_shop_list_lin);
		this.shop_haitaoImage=(ImageView) view.findViewById(R.id.haitaopin_image1);
		this.item_fragment_shop_list_imageview1=(WebImageView)view.findViewById
				(item_fragment_shop_list_imageview1);
		this.shop_name=(TextView)view.findViewById
				(shop_name);
		this.shop_price=(TextView)view.findViewById
				(shop_price);
		this.shop_num=(TextView)view.findViewById
				(shop_num);
		this.item_fragment_shop_list_lay=(LinearLayout)view.findViewById
				(item_fragment_shop_list_lay);
		this.item_fragment_shop_list_textview6=(TextView)view.findViewById
				(item_fragment_shop_list_textview6);
		
		this.item_fragment_shop_list_textview7=(TextView)view.findViewById
				(item_fragment_shop_list_textview7);
		this.item_fragment_shop_list_textview9=(TextView)view.findViewById
				(item_fragment_shop_list_textview9);
		this.item_fragment_shop_list_textview10=(TextView)view.findViewById
				(item_fragment_shop_list_textview10);

		this.item_fragment_shop_list_textview5=(TextView)view.findViewById
				(item_fragment_shop_list_textview5);
	}

	@SuppressLint("SimpleDateFormat")
	public void setInfo (int id, String shop_price2,String name, final int goods_id, 
			PHOTO img, String num,String startTime,String endTime,String sales_volume,
			String promotePrice,String is_haitao,String is_promote) throws Exception{
		shop_name.setText(name);
		shop_num.setText(num);
		if(is_promote.equals("1")){
			format=new SimpleDateFormat("yyyy/MM/dd");
			item_fragment_shop_list_textview6.setText(startTime.substring(5,10));
			item_fragment_shop_list_textview9.setText(endTime.substring(5, 10));
			PurchaseAdapter adapter=new PurchaseAdapter();
			try {
				Log.i("startTime", startTime.substring(0,10));
				Log.i("endTime", endTime.substring(0,10));
				int StartTime=0;
				int EndTime=0;
				StartTime = adapter.dayForWeek(startTime.substring(0, 10));
				EndTime= adapter.dayForWeek(endTime.substring(0, 10));
				getDayOfWeek(StartTime);
				getEndOfWeek(EndTime);
				String endOfPromote=endTime.substring(0,10);
				Log.i("endOfPromote", endOfPromote);
				promoteTime=format.parse(endOfPromote);
				long choiceTime = promoteTime.getTime();
				long current=System.currentTimeMillis();
				int i=(int) ((new Long(choiceTime).longValue()-new Long(current).longValue())/(1000L*60*60*24));
				if(i>=0){
					isPromotePrice="1";
				item_fragment_shop_list_lay.setVisibility(View.VISIBLE);
					shop_price.setText(promotePrice);
				}else if(i<0){
					isPromotePrice="0";
					item_fragment_shop_list_lay.setVisibility(View.GONE);
					shop_price.setText(shop_price2);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			shop_price.setText(shop_price2);
		}
		item_fragment_shop_list_textview5.setText(sales_volume);
		
		
		if(is_haitao.equals("0")){
			shop_haitaoImage.setVisibility(View.GONE);
			
			
		}
		
			
		item_fragment_shop_list_imageview1.setImageWithURL(mContext, img.thumb, R.drawable.default_image);
//		ImageLoaderUtils.displayNetworkImage(mContext, img.thumb, ImageLoaderUtils.IMAGE_PATH, item_fragment_shop_list_imageview1);

		this.item_fragment_shop_list_lin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent=new Intent(mContext,MyPurchaseDetailActivity.class);
				intent.putExtra("id", goods_id);
				intent.putExtra("isPromotePrice",isPromotePrice);
				mContext.startActivity(intent);

			}
		});
	}

	private void getDayOfWeek(int Time) {
		if (Time == 7) {
			item_fragment_shop_list_textview7.setText("星期天");
			//		item_fragment_shop_list_textview10.setText("星期天");
		} else if (Time == 1) {
			item_fragment_shop_list_textview7.setText("星期一");
		} else if (Time == 2) {
			item_fragment_shop_list_textview7.setText("星期二");
		} else if (Time == 3) {
			item_fragment_shop_list_textview7.setText("星期三");
		} else if (Time == 4) {
			item_fragment_shop_list_textview7.setText("星期四");
		} else if (Time == 5) {
			item_fragment_shop_list_textview7.setText("星期五");
		} else if (Time == 6) {
			item_fragment_shop_list_textview7.setText("星期六");
		}
	}
	private void getEndOfWeek(int Time) {
		if (Time == 7) {
//			item_fragment_shop_list_textview7.setText("星期天");
			item_fragment_shop_list_textview10.setText("星期天");
		} else if (Time == 1) {
			item_fragment_shop_list_textview10.setText("星期一");
		} else if (Time == 2) {
			item_fragment_shop_list_textview10.setText("星期二");
		} else if (Time == 3) {
			item_fragment_shop_list_textview10.setText("星期三");
		} else if (Time == 4) {
			item_fragment_shop_list_textview10.setText("星期四");
		} else if (Time == 5) {
			item_fragment_shop_list_textview10.setText("星期五");
		} else if (Time == 6) {
			item_fragment_shop_list_textview10.setText("星期六");
		}
	}

}
