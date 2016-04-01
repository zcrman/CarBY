package com.android.lehuitong.component;

import android.R.raw;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.activity.ActivityDetailActivity;
import com.android.lehuitong.activity.CouponDetailActivity;
import com.android.lehuitong.activity.HiActivity;
import com.android.lehuitong.activity.MyCouponDetaliActivity;
import com.android.lehuitong.activity.MyPurchaseActivity;
import com.android.lehuitong.activity.MyPurchaseDetailActivity;
import com.android.lehuitong.model.HomeModel;
import com.android.lehuitong.model.HomeTitleTextUtil;
import com.android.lehuitong.model.ImageLoaderUtils;
import com.android.lehuitong.protocol.GOODS;
import com.android.lehuitong.protocol.PHOTO;
import com.android.lehuitong.protocol.SHOP;
import com.funmi.lehuitong.R;

public class HomePromoteGoodsHolder {
	public WebImageView goodsImg;
	public TextView goodsName;
	public TextView goodsBrief;
	public LinearLayout itemLayout;
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	private Context context;
	private String goods_id;
	private TextView goodsPrice;
	private TextView goodsPromote;
	private int type;
	private TextView title;
	private View view2;
	private ImageView money_pic;
	public HomePromoteGoodsHolder(Context context,int type) {
		this.context = context;
		this.type=type;
	}

	public void initView(View view) {

		goodsImg = (WebImageView) view.findViewById(R.id.goods_img);
		goodsName = (TextView) view.findViewById(R.id.goods_name);
		goodsBrief = (TextView) view.findViewById(R.id.goods_brief);
		itemLayout = (LinearLayout)view.findViewById(R.id.item_layout);
		goodsPrice=(TextView) view.findViewById(R.id.home_price);
		goodsPromote=(TextView) view.findViewById(R.id.home_promote);
		title=(TextView) view.findViewById(R.id.title);
		view2=view.findViewById(R.id.below_title);
		money_pic=(ImageView) view.findViewById(R.id.money_pic);
	}

	/**
	 * 
	 * @param name 商品名字
	 * @param brief 简介
	 * @param img 图片路径
	 * @param goods_id 商品id
	 * @param price 商品价格
	 * @param promote 销售数量
	 */
	public void setInfo(String name, String brief, PHOTO img,final String goods_id,String price,String promote,final int type) {

		this.goods_id = goods_id;
		shared = context.getSharedPreferences("userInfo", 0);
		editor = shared.edit();
		String imageType = shared.getString("imageType", "mind");
		if (imageType.equals("high")) {
			goodsImg.setImageWithURL(context, img.thumb, R.drawable.default_image);
			//			ImageLoaderUtils.displayNetworkImage(context, img.thumb, ImageLoaderUtils.IMAGE_PATH, goodsImg);
		} else if (imageType.equals("low")) {
			goodsImg.setImageWithURL(context, img.small, R.drawable.default_image);
			//			ImageLoaderUtils.displayNetworkImage(context, img.small, ImageLoaderUtils.IMAGE_PATH, goodsImg);
		} else {
			String netType = shared.getString("netType", "wifi");
			if (netType.equals("wifi")) {
				goodsImg.setImageWithURL(context, img.thumb, R.drawable.default_image);
				//				ImageLoaderUtils.displayNetworkImage(context, img.thumb, ImageLoaderUtils.IMAGE_PATH, goodsImg);
			} else {
				goodsImg.setImageWithURL(context, img.small, R.drawable.default_image);
				//				ImageLoaderUtils.displayNetworkImage(context, img.small, ImageLoaderUtils.IMAGE_PATH, goodsImg);
			}
		}

		goodsName.setText(name);
		goodsBrief.setText(brief);
		goodsPrice.setText(price);
		goodsPromote.setText("已售 :"+promote);
		itemLayout.setOnClickListener(new OnClickListener() {
			private Intent intent;

			@Override
			public void onClick(View v) {
				if (type==HomeTitleTextUtil.FOOD_TYPE) {
					intent=new Intent(context,CouponDetailActivity.class);
					intent.putExtra("goods_id", goods_id);
					context.startActivity(intent);
				}else if(type==HomeTitleTextUtil.SHOP_TYPE){
					intent = new Intent(context,MyPurchaseDetailActivity.class);
					intent.putExtra("id", Integer.valueOf(goods_id).intValue());
					context.startActivity(intent);
				}else if(type==HomeTitleTextUtil.HOTEL_TYPE){

				}else if(type==HomeTitleTextUtil.JINGPINGDIAN_TUIJAIN_TYPE){
					intent=new Intent(context,CouponDetailActivity.class);
					intent.putExtra("goods_id", goods_id);
					context.startActivity(intent);
				}{

				}

			}
		});


	}
	public void setInfo(String name, String brief, String img,final String goods_id,String price,String promote,int type,String titleName,final int canBuy,String startTime,String endTime,final SHOP shop,final String goodsId) {
//		title.setVisibility(View.VISIBLE);
//		view2.setVisibility(View.VISIBLE);
//		title.setText(titleName);
		this.goods_id = goods_id;
		goodsImg.setImageWithURL(context, img, R.drawable.default_image);
		if(canBuy == 1){
			goodsPrice.setText(price);
		}else if(canBuy == 0){
			money_pic.setVisibility(View.GONE);
			if(startTime.length()==0 && endTime.length()==0){
				goodsPrice.setText("");
			}else{
				goodsPrice.setText(startTime.substring(5, 7)+"月"+startTime.substring(8, 10)+"日"+"-"+endTime.substring(5, 7)+"月"+endTime.substring(8, 10)+"日");
			}
		}
		//		ImageLoaderUtils.displayNetworkImage(context, img, ImageLoaderUtils.IMAGE_PATH, goodsImg);
		goodsName.setText(name);
		goodsBrief.setText(brief);
		goodsPromote.setText("已售 :"+promote);
		itemLayout.setOnClickListener(new OnClickListener() {
			private Intent intent;

			@Override
			public void onClick(View v) {
				if(shop.can_order == 1){
					if(canBuy==0){
						intent=new Intent(context,ActivityDetailActivity.class);
						intent.putExtra("goods_id", goods_id);
						intent.putExtra("status", 1);
						intent.putExtra("onlyActivity", HomeTitleTextUtil.KTV);
						context.startActivity(intent);
						}else{ 
						intent = new Intent(context,HiActivity.class);
						intent.putExtra("shop_id", shop.shop_id);
						intent.putExtra("goods_id", goodsId);
						intent.putExtra("type", "ktv");
						context.startActivity(intent);
						}
				}else if(shop.can_order == 0){
				if(canBuy==0){
				intent=new Intent(context,ActivityDetailActivity.class);
				intent.putExtra("goods_id", goods_id);
				intent.putExtra("status", 1);
				intent.putExtra("onlyActivity", HomeTitleTextUtil.KTV);
				context.startActivity(intent);
				}else{ 
				intent = new Intent(context,CouponDetailActivity.class);
				intent.putExtra("goods_id", goods_id);
				context.startActivity(intent);
				}
				}
			}
		});


	}
}
