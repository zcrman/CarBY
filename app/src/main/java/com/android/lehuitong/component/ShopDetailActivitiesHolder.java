package com.android.lehuitong.component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.activity.ActivityDetailActivity;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.view.CircularImage;
import com.android.lehuitong.view.GetSquareImg;
import com.funmi.lehuitong.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ShopDetailActivitiesHolder implements OnClickListener {

	public WebImageView activityImg;
	public TextView activityName;
	public TextView activityPrice;
	public TextView activitySales;
	public TextView activityTime;
	private Context context;
	private LinearLayout activitydetail;
	private String activityId;

	private DisplayImageOptions option;
	private ImageLoader loader;
	private SimpleDateFormat sdf;

	public ShopDetailActivitiesHolder(Context context) {
		this.context = context;
		loader = ImageLoader.getInstance();
		option = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.default_image)
				.showImageOnFail(R.drawable.default_image).resetViewBeforeLoading(false).delayBeforeLoading(300)
				.cacheOnDisc(true).cacheInMemory(true).bitmapConfig(Bitmap.Config.ARGB_8888).build();
	}

	/**
	 * 初始化控件
	 * 
	 * @param convertView
	 * @param Img
	 *            图片
	 * @param name
	 *            名字
	 * @param price
	 *            价格
	 * @param brief
	 *            描述
	 * @param time
	 *            开始时间
	 */
	public void initView(View convertView, int Img, int name, int price, int time, int sales, int activitydetail) {
		this.activityImg = (WebImageView) convertView.findViewById(Img);
		this.activityName = (TextView) convertView.findViewById(name);
		this.activityPrice = (TextView) convertView.findViewById(price);
		this.activitySales = (TextView) convertView.findViewById(sales);
		this.activityTime = (TextView) convertView.findViewById(time);
		this.activitydetail = (LinearLayout) convertView.findViewById(R.id.activitydetail);
	sdf=new SimpleDateFormat("MM/dd");
	}

	/**
	 * 设置详细信息
	 * 
	 * @param imgUrl
	 *            图片路径
	 * @param name
	 *            名字
	 * @param price
	 *            价格
	 * @param sales
	 *            销量
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param id
	 *            活动id
	 */
	public void setActivityInfo(String imgUrl, String name, String price, String sales, String startTime, String endTime, String id) {

		activityId = id;
		
		loader.displayImage(imgUrl, activityImg, option, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				((WebImageView) view).setImageBitmap(GetSquareImg.centerSquareScaleBitmap(loadedImage, loadedImage.getHeight()));
			}
		});
		this.activityName.setText(name);
		this.activityPrice.setText(price);
		// this.activitySales.setText("已售  "+sales);
		this.activitySales.setVisibility(View.GONE);
		if(startTime.length()==0&&endTime.length()==0){
			this.activityTime.setText("");
		}else{
		this.activityTime.setText(startTime.substring(5,7)+"月"+startTime.substring(8,10)+"日"+"	至	" + endTime.substring(5,7)+"月"+endTime.substring(8,10)+"日");
		}
		this.activitydetail.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(context, ActivityDetailActivity.class);
		intent.putExtra("goods_id", activityId);
		context.startActivity(intent);
	}

}
