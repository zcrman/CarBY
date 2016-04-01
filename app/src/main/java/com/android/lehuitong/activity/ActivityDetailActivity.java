package com.android.lehuitong.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.adapter.ViewPagerAdapter;
import com.android.lehuitong.model.GoodDetailModel;
import com.android.lehuitong.model.ImageLoaderUtils;
import com.android.lehuitong.model.SellerModel;
import com.android.lehuitong.protocol.GOODS;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SHOP;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;
import com.funmi.lehuitong.WebActivity;

public class ActivityDetailActivity extends BaseActivity implements OnClickListener ,BusinessResponse,OnPageChangeListener{

	private TextView title_text;
	private TextView shop_enter;
	private ImageView title_back;
	private ImageView activity_phone_image;
	private String goods_id;
	private TextView activityNameTextView;
	private ViewPager activityShopImage;
	private TextView activityTime;
	private TextView activityPrivilege;
	private TextView activityShop;
	private TextView activityAddress;
	private TextView activityPhone;
	private TextView activityPrivilegeInfo;
	private GoodDetailModel goodDetailModel;
	private Button enter_button;
	private LinearLayout activity_detail;
	private ViewPagerAdapter adapter;
	private LinearLayout activity_time;
	private int status;
	private boolean isHomeEnter = false;
	private List<ImageView> goodImgsList = new ArrayList<ImageView>();
	int i=0;
	private final int AUTO_MSG = 1;
	Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case AUTO_MSG:
				if(i ==goodImgsList.size()){
					i = 0;
				}
				Log.i("11111", ""+i);
				activityShopImage.setCurrentItem(i++);
				handler.sendEmptyMessageDelayed(AUTO_MSG, 5000);
				break;
			default:
				break;
			}
		};
	};
	private GOODS goods;
	private String onlyActivity;
	private SellerModel sellerModel;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity);
		init();
	}

	private void init() {
		goods_id = getIntent().getStringExtra("goods_id");
		status =   getIntent().getIntExtra("status", 0);
		onlyActivity=getIntent().getStringExtra("onlyActivity");
		adapter=new ViewPagerAdapter(getApplication());
		activityNameTextView = (TextView) findViewById(R.id.activity_name);
		shop_enter = (TextView) findViewById(R.id.shop_enter);
		activityShopImage = (ViewPager) findViewById(R.id.activity_shop_image);
		activityTime = (TextView) findViewById(R.id.activity_valid_time);
		activityPrivilege = (TextView) findViewById(R.id.activity_privilege);
		activityShop = (TextView) findViewById(R.id.activity_shop);
		activityAddress = (TextView) findViewById(R.id.activity_address);
		activityPhone = (TextView) findViewById(R.id.activity_phone);
		activity_detail=(LinearLayout) findViewById(R.id.activity_detail);
		activity_phone_image = (ImageView) findViewById(R.id.activity_phone_image);
		title_text = (TextView) findViewById(R.id.title_text);
		activity_time=(LinearLayout) findViewById(R.id.activity_time);
		title_text.setText("活动详情");
		
		title_back = (ImageView) findViewById(R.id.title_back);
		if (status==1) {
			shop_enter.setVisibility(View.VISIBLE);
		}else {
			shop_enter.setVisibility(View.GONE);
		}
		goodDetailModel = new GoodDetailModel(this);
		goodDetailModel.addResponseListener(this);
		goodDetailModel.fetchGoodDetail(goods_id);
		OnClickListener();
	}

	private void OnClickListener() {
		title_back.setOnClickListener(this);
		activity_phone_image.setOnClickListener(this);
		activity_detail.setOnClickListener(this);
		shop_enter.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.activity_phone_image:
			try {
				intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+goodDetailModel.goodDetail.shop_seller.shop_phone));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				finish();
			}
			break;
		case R.id.activity_detail:
			intent=new Intent(this,WebActivity.class);
			intent.putExtra("shop_info", goods.goods_desc.toString());
			startActivity(intent);
			break;
		case R.id.shop_enter:
			sellerModel = new SellerModel(this);
			sellerModel.addResponseListener(this);
			sellerModel.getSellerInfo(goods.shop_seller.shop_id);
			
			
			
			break;
		}

	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
		
		if (url.endsWith(ProtocolConst.SELLER_INFO)) {
			SHOP shop = sellerModel.shopDetail;
			if(shop.can_order == 0){
			Intent intent=new Intent(this, ShopDetailActivity.class);
			intent.putExtra("shop_id", goods.shop_seller.shop_id);
			intent.putExtra("onlyActivity", onlyActivity);
			startActivity(intent);
			finish();
			}else{
				Intent intent=new Intent(this, HiActivity.class);
				intent.putExtra("shop_id", shop.shop_id);
				intent.putExtra("shop_name",shop.shop_name);
				intent.putExtra("shop_address", shop.shop_address);
				intent.putExtra("shop_phone", shop.shop_phone);
				intent.putExtra("type", "home");
				Bundle bundle = new Bundle();
				bundle.putSerializable("goods", shop.goods);
				Log.i("shop.goods", ""+shop.goods.size());
				intent.putExtra("goodsList", bundle);
				startActivity(intent);
				finish();
			}
			}
		
		if (url.endsWith(ProtocolConst.GOODS)) {
			goods = goodDetailModel.goodDetail;
			activityNameTextView.setText(goods.goods_name);
			if(goods.pictures.size()>0){
				for (int i = 0; i < goods.pictures.size(); i++) {
					ImageView shopImg = new ImageView(this);
					try{
					ImageLoaderUtils.displayNetworkImage(getApplication(), goods.pictures.get(i).thumb, ImageLoaderUtils.IMAGE_PATH, shopImg);
					}catch(Exception e){
						e.printStackTrace();
					}
					shopImg.setScaleType(ScaleType.FIT_XY);
					goodImgsList.add(shopImg);
				}
				adapter.bindData(goodImgsList);
				activityShopImage.setAdapter(adapter);
//				activityShopImage.setOnPageChangeListener(this);
				handler.sendEmptyMessageDelayed(AUTO_MSG, 5000);
				}
			if(goods.promote_start_date.length()==0&&goods.promote_end_date.length()==0){
				activityTime.setText("");
			}else{
			activityTime.setText(goods.promote_start_date.substring(5,7)+"月"+goods.promote_start_date.substring(8,10)+" 至 "+goods.promote_end_date.substring(5,7)+"月"+goods.promote_end_date.substring(8,10));
			}
			activityPrivilege.setText(goods.goods_brief);
			activityShop.setText(goods.shop_seller.shop_name);
			activityAddress.setText(goods.shop_seller.shop_address);
			activityPhone.setText(goods.shop_seller.shop_phone);
			
		}
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		try{
		if (arg0 ==1) {
			handler.removeMessages(AUTO_MSG);
		}else if(arg0 ==2) {
//			handler.sendEmptyMessageDelayed(AUTO_MSG, 5000);
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

}