package com.android.lehuitong.component;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.activity.CouponDetailActivity;
import com.android.lehuitong.activity.ShopDetailActivity;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.view.GetSquareImg;
import com.funmi.lehuitong.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ShopDetailCousponHolder implements OnClickListener{

	public WebImageView cousponImg;
	public TextView cousponName;
	public TextView cousponPrice;
	public TextView cousponBrief;
	public TextView cousponSales;
	private Context context;
	private LinearLayout coupondetail;
	private String cousponId;
	private DisplayImageOptions option;
	private ImageLoader loader;
	
	public ShopDetailCousponHolder(Context context){
		this.context = context;
		loader = ImageLoader.getInstance();
		option = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.default_image)
				.showImageOnFail(R.drawable.default_image).resetViewBeforeLoading(false).delayBeforeLoading(300)
				.cacheOnDisc(true).cacheInMemory(true).bitmapConfig(Bitmap.Config.ARGB_8888).build();
	}
	/**
	 * 初始化控件
	 * @param convertView
	 * @param Img 图片
	 * @param name 名字
	 * @param price 价格
	 * @param brief 描述
	 * @param sales 销量
	 */
	public void initView(View convertView,int Img,int name,int price,int brief,int sales,int coupondetail){
		this.cousponImg = (WebImageView) convertView.findViewById(Img);
		this.cousponName = (TextView) convertView.findViewById(name);
		this.cousponPrice = (TextView) convertView.findViewById(price);
		this.cousponSales = (TextView) convertView.findViewById(sales);
		this.cousponBrief = (TextView) convertView.findViewById(brief);
		this.coupondetail=(LinearLayout)convertView.findViewById(coupondetail);
	}

	/**
	 * 设置详细信息
	 * @param imgUrl 图片路径
	 * @param name 名字
	 * @param price 价格
	 * @param brief 描述
	 * @param sales 销量
	 */
	public void setCousponInfo(String imgUrl,String name,String price,String brief,String sales,String id){
		
		cousponId =id;
		loader.displayImage(imgUrl, cousponImg, option, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				((WebImageView) view).setImageBitmap(GetSquareImg.centerSquareScaleBitmap(loadedImage, loadedImage.getHeight()));
			}
		});
		this.cousponName.setText(name);
		this.cousponPrice.setText("¥ "+price);
		this.cousponSales.setText("已售  "+sales);
		this.cousponBrief.setText(brief);
		this.coupondetail.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.coupondetail:
			intent=new Intent(context,CouponDetailActivity.class);
			intent.putExtra("goods_id", cousponId);
			intent.putExtra("seller_id", ShopDetailActivity.shop_id);
			context.startActivity(intent); 
			break;
		default:
			break;
		}
		
	}

}
