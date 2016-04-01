package com.android.lehuitong.component;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.view.WebCircleImageView;
import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.activity.ActivityDetailActivity;
import com.android.lehuitong.model.HomeTitleTextUtil;
import com.android.lehuitong.protocol.SIMPLEGOODS;
import com.android.lehuitong.view.CircularImage;
import com.android.lehuitong.view.GetSquareImg;
import com.funmi.lehuitong.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class HomePromoteActivitiesHolder implements OnClickListener {

	public RelativeLayout oneLayout;
	public RelativeLayout twoLayout;
	public RelativeLayout threeLayout;
	public RelativeLayout fourLayout;

	public TextView oneName;
	public TextView twoName;
	public TextView threeName;
	public TextView fourName;

	public CircularImage oneImg;
	public CircularImage twoImg;
	public CircularImage threeImg;
	public CircularImage fourImg;

	private SharedPreferences shared;
	private SharedPreferences.Editor editor;

	private Context context;

	private List<SIMPLEGOODS> ativityList;

	private DisplayImageOptions option;
	private ImageLoader loader;

	public HomePromoteActivitiesHolder(Context context) {
		this.context = context;
		loader = ImageLoader.getInstance();
		option = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.default_image) // resource
																					// drawable
				.showImageOnFail(R.drawable.default_image).resetViewBeforeLoading(false) // default
				.delayBeforeLoading(300).cacheOnDisc(true).cacheInMemory(true).bitmapConfig(Bitmap.Config.RGB_565).build();

	}

	public void initView(View view) {
		oneLayout = (RelativeLayout) view.findViewById(R.id.activity_one);
		twoLayout = (RelativeLayout) view.findViewById(R.id.activity_two);
		threeLayout = (RelativeLayout) view.findViewById(R.id.activity_three);
		fourLayout = (RelativeLayout) view.findViewById(R.id.activity_four);

		oneName = (TextView) view.findViewById(R.id.one_name);
		twoName = (TextView) view.findViewById(R.id.two_name);
		threeName = (TextView) view.findViewById(R.id.three_name);
		fourName = (TextView) view.findViewById(R.id.four_name);

		oneImg = (CircularImage) view.findViewById(R.id.one_img);
		twoImg = (CircularImage) view.findViewById(R.id.two_img);
		threeImg = (CircularImage) view.findViewById(R.id.three_img);
		fourImg = (CircularImage) view.findViewById(R.id.four_img);
	}

	public void setInfo(List<SIMPLEGOODS> activityList) {
		this.ativityList = activityList;
		shared = context.getSharedPreferences("userInfo", 0);
		editor = shared.edit();
		String imageType = shared.getString("imageType", "mind");

		if (activityList.size() > 0) {
			SIMPLEGOODS oneSimplegoods = activityList.get(0);
			oneName.setText(oneSimplegoods.name);
			oneLayout.setOnClickListener(this);

			String netType = shared.getString("netType", "wifi");
			if (netType.equals("wifi")) {
				loader.displayImage(oneSimplegoods.img.thumb, oneImg, option, new SimpleImageLoadingListener() {
					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						((CircularImage) view).setImageBitmap(loadedImage);

					}
				});
			} else {
				loader.displayImage(oneSimplegoods.img.small, oneImg, option, new SimpleImageLoadingListener() {
					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						((CircularImage) view).setImageBitmap(loadedImage);
					}
				});
			}

			if (activityList.size() > 1) {

				SIMPLEGOODS twoSimplegoods = activityList.get(2);
				twoName.setText(twoSimplegoods.name);
				twoLayout.setOnClickListener(this);

				if (netType.equals("wifi")) {
					loader.displayImage(twoSimplegoods.img.thumb, twoImg, option, new SimpleImageLoadingListener() {
						@Override
						public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
							((CircularImage) view).setImageBitmap(loadedImage);

						}
					});
				} else {
					loader.displayImage(twoSimplegoods.img.small, twoImg, option, new SimpleImageLoadingListener() {
						@Override
						public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
							((CircularImage) view).setImageBitmap(loadedImage);

						}
					});
				}

				if (activityList.size() > 2) {

					SIMPLEGOODS threeSimplegoods = activityList.get(1);
					threeName.setText(threeSimplegoods.name);
					threeLayout.setOnClickListener(this);

					if (netType.equals("wifi")) {
						loader.displayImage(threeSimplegoods.img.thumb, threeImg, option, new SimpleImageLoadingListener() {
							@Override
							public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
								((CircularImage) view).setImageBitmap(loadedImage);

							}
						});
					} else {
						loader.displayImage(threeSimplegoods.img.small, threeImg, option, new SimpleImageLoadingListener() {
							@Override
							public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
								((CircularImage) view).setImageBitmap(loadedImage);

							}
						});
					}

					if (activityList.size() > 3) {

						SIMPLEGOODS fourSimplegoods = activityList.get(3);
						fourName.setText(fourSimplegoods.name);
						fourLayout.setOnClickListener(this);

						if (netType.equals("wifi")) {
							loader.displayImage(fourSimplegoods.img.thumb, fourImg, option, new SimpleImageLoadingListener() {
								@Override
								public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
									((CircularImage) view).setImageBitmap(loadedImage);

								}
							});

						} else {
							loader.displayImage(fourSimplegoods.img.small, fourImg, option, new SimpleImageLoadingListener() {
								@Override
								public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
									((CircularImage) view).setImageBitmap(loadedImage);

								}
							});
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
		case R.id.activity_one:
			intent = new Intent(context, ActivityDetailActivity.class);
			intent.putExtra("goods_id", ativityList.get(0).id + "");
			if (ativityList.get(0).id==199) {
				intent.putExtra("onlyActivity", HomeTitleTextUtil.KTV);	
			}
			intent.putExtra("status", 1);
			context.startActivity(intent);
			break;
		case R.id.activity_two:
			intent = new Intent(context, ActivityDetailActivity.class);
			intent.putExtra("goods_id", ativityList.get(2).id + "");
			intent.putExtra("status", 1);
			if (ativityList.get(2).id==199) {
				intent.putExtra("onlyActivity", HomeTitleTextUtil.KTV);	
			}
			context.startActivity(intent);
			break;
		case R.id.activity_three:
			intent = new Intent(context, ActivityDetailActivity.class);
			intent.putExtra("goods_id", ativityList.get(1).id + "");
			if (ativityList.get(1).id==199) {
				intent.putExtra("onlyActivity", HomeTitleTextUtil.KTV);	
			}
			intent.putExtra("status", 1);
			context.startActivity(intent);
			break;
		case R.id.activity_four:
			intent = new Intent(context, ActivityDetailActivity.class);
			intent.putExtra("goods_id", ativityList.get(3).id + "");
			if (ativityList.get(3).id==199) {
				intent.putExtra("onlyActivity", HomeTitleTextUtil.KTV);	
			}
			intent.putExtra("status", 1);
			context.startActivity(intent);
			break;

		}

	}
}
