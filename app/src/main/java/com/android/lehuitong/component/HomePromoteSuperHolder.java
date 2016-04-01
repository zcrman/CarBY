package com.android.lehuitong.component;

import java.util.List;

import com.BeeFramework.activity.StartActivity;
import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.activity.CouponDetailActivity;
import com.android.lehuitong.activity.ShopDetailActivity;
import com.android.lehuitong.protocol.SIMPLEGOODS;
import com.funmi.lehuitong.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomePromoteSuperHolder implements OnClickListener {

	public LinearLayout oneLayout;
	public LinearLayout twoLayout;
	public LinearLayout threeLayout;

	public TextView oneName;
	public TextView twoName;
	public TextView threeName;

	public WebImageView oneImg;
	public WebImageView twoImg;
	public WebImageView threeImg;

	private SharedPreferences shared;
	private SharedPreferences.Editor editor;

	private Context context;
	private List<SIMPLEGOODS> superList;

	public HomePromoteSuperHolder(Context context) {
		this.context = context;
	}

	public void intiView(View view) {
		oneLayout = (LinearLayout) view.findViewById(R.id.super_layout1);
		twoLayout = (LinearLayout) view.findViewById(R.id.super_layout2);
		threeLayout = (LinearLayout) view.findViewById(R.id.super_layout3);

		oneName = (TextView) view.findViewById(R.id.one_name);
		twoName = (TextView) view.findViewById(R.id.two_name);
		threeName = (TextView) view.findViewById(R.id.three_name);

		oneImg = (WebImageView) view.findViewById(R.id.one_img);
		twoImg = (WebImageView) view.findViewById(R.id.two_img);
		threeImg = (WebImageView) view.findViewById(R.id.three_img);
	}

	public void setInfo(List<SIMPLEGOODS> superList) {
		this.superList = superList;
		shared = context.getSharedPreferences("userInfo", 0);
		editor = shared.edit();
		String imageType = shared.getString("imageType", "mind");

		if (superList.size() > 0) {
			SIMPLEGOODS oneSimplegoods = superList.get(0);
			oneName.setText(oneSimplegoods.name);
			oneLayout.setOnClickListener(this);

			if (imageType.equals("high")) {
				oneImg.setImageWithURL(context, oneSimplegoods.img.thumb, R.drawable.default_image);
			} else if (imageType.equals("low")) {
				oneImg.setImageWithURL(context, oneSimplegoods.img.small, R.drawable.default_image);
			} else {
				String netType = shared.getString("netType", "wifi");
				if (netType.equals("wifi")) {
					oneImg.setImageWithURL(context, oneSimplegoods.img.thumb, R.drawable.default_image);
				} else {
					oneImg.setImageWithURL(context, oneSimplegoods.img.small, R.drawable.default_image);
				}
			}
			if (superList.size() > 1) {

				SIMPLEGOODS twoSimplegoods = superList.get(1);
				twoName.setText(twoSimplegoods.name);
				twoLayout.setOnClickListener(this);

				if (imageType.equals("high")) {
					twoImg.setImageWithURL(context, twoSimplegoods.img.thumb, R.drawable.default_image);
				} else if (imageType.equals("low")) {
					twoImg.setImageWithURL(context, twoSimplegoods.img.small, R.drawable.default_image);
				} else {
					String netType = shared.getString("netType", "wifi");
					if (netType.equals("wifi")) {
						twoImg.setImageWithURL(context, twoSimplegoods.img.thumb, R.drawable.default_image);
					} else {
						twoImg.setImageWithURL(context, twoSimplegoods.img.small, R.drawable.default_image);
					}
				}

				if (superList.size() > 2) {

					SIMPLEGOODS threeSimplegoods = superList.get(2);
					threeName.setText(threeSimplegoods.name);
					threeLayout.setOnClickListener(this);

					if (imageType.equals("high")) {
						threeImg.setImageWithURL(context, threeSimplegoods.img.thumb, R.drawable.default_image);
					} else if (imageType.equals("low")) {
						threeImg.setImageWithURL(context, threeSimplegoods.img.small, R.drawable.default_image);
					} else {
						String netType = shared.getString("netType", "wifi");
						if (netType.equals("wifi")) {
							threeImg.setImageWithURL(context, threeSimplegoods.img.thumb, R.drawable.default_image);
						} else {
							threeImg.setImageWithURL(context, threeSimplegoods.img.small, R.drawable.default_image);
						}
					}
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.super_layout1:
			if (superList.get(0).parent_id.equals("996")) {
				intent = new Intent(context, CouponDetailActivity.class);
				intent.putExtra("goods_id", superList.get(0).id + "");
				context.startActivity(intent);
			} else if (superList.get(0).parent_id.equals("998")) {
				// 此处有问题
				intent = new Intent(context, ShopDetailActivity.class);
				intent.putExtra("shop_id", superList.get(0).id + "");
				context.startActivity(intent);
			}
			break;
		case R.id.super_layout2:

			if (superList.get(1).parent_id.equals("996")) {
				intent = new Intent(context, CouponDetailActivity.class);
				intent.putExtra("goods_id", superList.get(1).id + "");
				context.startActivity(intent);
			} else if (superList.get(1).parent_id.equals("998")) {
				// 此处有问题
				intent = new Intent(context, ShopDetailActivity.class);
				intent.putExtra("shop_id", superList.get(1).id + "");
				context.startActivity(intent);
			}

			break;
		case R.id.super_layout3:

			if (superList.get(2).parent_id.equals("996")) {
				intent = new Intent(context, CouponDetailActivity.class);
				intent.putExtra("goods_id", superList.get(2).id + "");
				context.startActivity(intent);
			} else if (superList.get(2).parent_id.equals("998")) {
				// 此处有问题
				intent = new Intent(context, ShopDetailActivity.class);
				intent.putExtra("shop_id", superList.get(2).id + "");
				context.startActivity(intent);
			}

			break;

		}

	}

}
