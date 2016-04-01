package com.android.lehuitong.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.LehuitongApp;
import com.android.lehuitong.adapter.ViewPagerAdapter;
import com.android.lehuitong.model.GoodDetailModel;
import com.android.lehuitong.model.ImageLoaderUtils;
import com.android.lehuitong.model.KtvAndCouponsOrderModel;
import com.android.lehuitong.protocol.GOODS;
import com.android.lehuitong.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;
import com.funmi.lehuitong.WebActivity;

@SuppressLint("SimpleDateFormat") public class CouponDetailActivity extends BaseActivity implements OnClickListener,BusinessResponse,OnPageChangeListener {

	private TextView title_text;
	private ImageView title_back;
	private ImageView coupondetail_phone_image;
	private RelativeLayout coupondetail_shop;
	private Button btn_coupondetail;
	private String goods_id,seller_id;
	private KtvAndCouponsOrderModel orderModel;
	private GoodDetailModel goodDetailModel;
	private LinearLayout time_layout;
	private TextView couponName;
	private TextView couponBrief;
	private ViewPager couponImg;
	private TextView saleNum;
	private TextView couponPrice;
	private TextView useTime;
	private TextView shopName;
	private TextView shopAddress;
	private TextView shopPhone;
	private RelativeLayout couponDetailInfo;
	private ViewPagerAdapter adapter;
	
//	private String pTime;
	private List<ImageView> goodImgsList = new ArrayList<ImageView>();
	int i=0;
	private final int AUTO_MSG = 0;
	private final int TOUCH_MSG = 1;
	private final int AFTER_TOUCH_MSG = 2;
	Handler handler=new Handler(){

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case AUTO_MSG:
				if(i ==goodImgsList.size()){
					i = 0;
				}
				Log.i("22222", ""+i);
				couponImg.setCurrentItem(i++);
				handler.sendEmptyMessageDelayed(AUTO_MSG, 5000);
				
				break;
			case TOUCH_MSG:
				handler.removeMessages(AUTO_MSG);
				break;
			case AFTER_TOUCH_MSG:
				i = couponImg.getCurrentItem();
				if(i==goodImgsList.size()){
					i=0;
				}
				couponImg.setCurrentItem(i++);
					handler.sendEmptyMessageDelayed(AUTO_MSG, 5000);
			}
		};
	};
	private GOODS good;
	private Message msg;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupondetail);
		init();
		LehuitongApp.getInstance().addActivity(this); 
	}
	private void init() {
		 adapter=new ViewPagerAdapter(getApplication());
		time_layout=(LinearLayout) findViewById(R.id.time_layout);
		btn_coupondetail = (Button) findViewById(R.id.btn_coupondetail);
		coupondetail_shop = (RelativeLayout) findViewById(R.id.coupondetail_shop);
		coupondetail_phone_image = (ImageView) findViewById(R.id.coupondetail_phone_image);
		title_text = (TextView) findViewById(R.id.title_text);
		title_text.setText("优惠券详情");
		title_back = (ImageView) findViewById(R.id.title_back);
		couponName = (TextView) findViewById(R.id.coupondetail_name);
		couponBrief = (TextView) findViewById(R.id.coupondetail_context);
		saleNum = (TextView) findViewById(R.id.coupondetail_sell_num);
		couponPrice = (TextView) findViewById(R.id.coupondetail_price);
		useTime = (TextView) findViewById(R.id.coupondetail_valid_time);
		shopName = (TextView) findViewById(R.id.shop_name);
		shopAddress = (TextView) findViewById(R.id.coupondetail_address);
		shopPhone = (TextView) findViewById(R.id.coupondetail_phone);
		couponDetailInfo = (RelativeLayout) findViewById(R.id.coupondetail_detail);
		couponImg = (ViewPager) findViewById(R.id.coupon_img);
		goods_id = getIntent().getStringExtra("goods_id");
		seller_id=getIntent().getStringExtra("seller_id");
		orderModel = new KtvAndCouponsOrderModel(this);
		orderModel.addResponseListener(this);
		goodDetailModel = new GoodDetailModel(this);
		goodDetailModel.addResponseListener(this);
		goodDetailModel.fetchGoodDetail(goods_id);
		OnClickListener();
	}

	private void OnClickListener() {
		btn_coupondetail.setOnClickListener(this);
		coupondetail_phone_image.setOnClickListener(this);
		coupondetail_shop.setOnClickListener(this);
		title_back.setOnClickListener(this);
		couponDetailInfo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_coupondetail:
			orderModel.checkOrder(goods_id,seller_id,1);

			break;
//		case R.id.coupondetail_shop:
//			intent = new Intent(CouponDetailActivity.this, KtvLocationChooseActivity.class);
//			startActivity(intent);
//			break;
		case R.id.title_back:
			finish();
			break;	
		case R.id.coupondetail_phone_image:
			intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ goodDetailModel.goodDetail.shop_seller.shop_phone));
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;

		case R.id.coupondetail_detail:
			intent=new Intent(this,WebActivity.class);
			intent.putExtra("shop_info", good.goods_desc.toString());
			startActivity(intent);
			break;
		}

	}
	/** 获得预售时间 */
	@SuppressWarnings("unused")
	public String gettime(String time) {
		
		String[] date = time.split("/");
		String year = null;
		String month = null;
		String mdate = null;
		for (int i = 0; i < date.length; i++) {
			year = date[0];
			month = date[1];
			mdate = date[2];
		}

		String month2 = month.substring(0, 2);
		String date2 = mdate.substring(0, 2);
//		pTime = year + "-" + month + "-" + date2;
		String times = year+"年"+month2 + "月" + date2 + "日";
		return times;
		

	}
	

	

	/** 根据日期计算星期 */
	public static int dayForWeek(String pTime) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(pTime));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
		if (url.endsWith(ProtocolConst.GOODS)) {
			good = goodDetailModel.goodDetail;
			Spanned goodsDesc = Html.fromHtml(goodDetailModel.goodDetail.goods_desc)	;		
			couponName.setText(goodDetailModel.goodDetail.goods_name);
			couponBrief.setText(goodDetailModel.goodDetail.goods_brief);
			couponPrice.setText(goodDetailModel.goodDetail.shop_price);
			saleNum.setText(goodDetailModel.goodDetail.sales_volume);
			shopName.setText(goodDetailModel.goodDetail.shop_seller.shop_name);
			shopAddress.setText(goodDetailModel.goodDetail.shop_seller.shop_address);
			shopPhone.setText(goodDetailModel.goodDetail.shop_seller.shop_phone);
			if(good.pictures.size()>0){
				for (int i = 0; i < good.pictures.size(); i++) {
					ImageView shopImg = new ImageView(this);
					try{
					ImageLoaderUtils.displayNetworkImage(getApplication(), good.pictures.get(i).thumb, ImageLoaderUtils.IMAGE_PATH, shopImg);
					}catch(Exception e){
						e.printStackTrace();
					}
					shopImg.setScaleType(ScaleType.FIT_XY);
					goodImgsList.add(shopImg);
				}
				adapter.bindData(goodImgsList);
				couponImg.setAdapter(adapter);
//				couponImg.setOnPageChangeListener(this);
				handler.sendEmptyMessageDelayed(AUTO_MSG, 5000);
				
				}
			if (good.is_promote.equals("1")) {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
				try {
					long date=sdf.parse(good.promote_end_date).getTime();
					long currentTime=System.currentTimeMillis();
					int time=(int) (new Long(currentTime).longValue()-(new Long(date).longValue())/1000*3600);
					if(time<=0){
						couponPrice.setText(good.shop_price);
						useTime.setText(gettime(good.promote_start_date)+" 至 "+gettime(good.promote_end_date));
						}else{
							couponPrice.setText(good.shop_price);
							time_layout.setVisibility(View.GONE);	
						}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else {
				time_layout.setVisibility(View.GONE);
			}
		} else if (url.endsWith(ProtocolConst.FLOW_TICKET_CHECKORDER)) {
			Intent intent1 = new Intent(CouponDetailActivity.this, BuyCouponActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("order", orderModel.ktv_ORDER);
			bundle.putInt("statu", orderModel.ktv_ORDER.allow_use_bonus);
			intent1.putExtras(bundle);
			startActivity(intent1);
			
		}
	}
	@Override
	public void onPageScrollStateChanged(int arg0) {
		try{
		if (arg0 ==1) {
			handler.removeMessages(AUTO_MSG);
		}else if(arg0 ==2) {
//			handler.sendEmptyMessageDelayed(AFTER_TOUCH_MSG, 5000);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}
	@Override
	public void onPageSelected(int arg0) {
		
		

}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
