package com.android.lehuitong.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.LehuitongApp;
import com.android.lehuitong.adapter.ShopDetailActivitiesAdapter;
import com.android.lehuitong.model.SellerModel;
import com.android.lehuitong.model.ShoppingCartModel;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SHOP;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.funmi.lehuitong.R;

/**
 * 商家详情页面
 * 
 * @author shenlw
 * 
 */

public class ShopDetailActivity extends BaseActivity implements
		OnClickListener, BusinessResponse {

	private TextView topTitle;
	private ImageView topBack;
	private SellerModel sellerModel;
	private XListView shopList;
	private View header;
	private View footer;
	private WebImageView shopImg;
	private TextView shopName;
	private TextView shopAddress;
	private ImageView shopCall;
	private TextView shopDistance;
	private TextView shopActivity;
	private TextView shopBrief;
	private String shopPhone;
	private String onlyActivity = "";
	public static String shop_id;
	private ShopDetailActivitiesAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_detail);
		initView();
		LehuitongApp.getInstance().addActivity(this); 
	}

	private void initView() {

		Intent intent = getIntent();
		 shop_id = intent.getStringExtra("shop_id");
		onlyActivity=intent.getStringExtra("onlyActivity");
		if (onlyActivity==null) {
			onlyActivity="";
		}
		header = LayoutInflater.from(this).inflate(
				R.layout.acitivity_shop_detail_header, null);
		footer = LayoutInflater.from(this).inflate(
				R.layout.acitivity_shop_detail_end, null);
		shopList = (XListView) findViewById(R.id.shop_detail_list);

		shopImg = (WebImageView) header.findViewById(R.id.shop_img);
		shopName = (TextView) header.findViewById(R.id.shop_name);
		shopAddress = (TextView) header.findViewById(R.id.shop_address);
		shopCall = (ImageView) header.findViewById(R.id.shop_call_phone);
		// shopDistance = (TextView) header.findViewById(R.id.shop_distances);
		shopActivity = (TextView) header.findViewById(R.id.shop_activities);

		shopBrief = (TextView) footer.findViewById(R.id.shop_brief);

		adapter = new ShopDetailActivitiesAdapter(this);

		sellerModel = new SellerModel(this);
		sellerModel.addResponseListener(this);
		sellerModel.getSellerInfo(shop_id);
		topTitle = (TextView) findViewById(R.id.title_text);
		topTitle.setText("商家详情");
		topBack = (ImageView) findViewById(R.id.title_back);
		shopList.addHeaderView(header);
		shopList.addFooterView(footer);
		shopList.setPullLoadEnable(false);
		shopList.setPullRefreshEnable(false);

		setOnClickListener();
	}

	private void setOnClickListener() {
		topBack.setOnClickListener(this);
		shopCall.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		Intent intent = null;
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.shop_call_phone:
try {
	intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
			+ shopPhone));
	startActivity(intent);
} catch (Exception e) {
}finally{
	finish();
}
			break;
		default:
			break;
		}
	}
	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.SELLER_INFO)) {
			SHOP shop = sellerModel.shopDetail;
			if (sellerModel.shopDetail.goods.size() > 0) {
				adapter.bindData(sellerModel.shopDetail.goods,onlyActivity);
				shopList.setAdapter(adapter);
				shopActivity.setText("优惠活动  (" + shop.goods.size() + ")");
			} else {
				shopActivity.setText("优惠活动  (" + "0" + ")");
				shopList.setAdapter(null);
			}

			shopName.setText(shop.shop_name);

			
				shopAddress.setText(shop.shop_address);
			
			shopBrief.setText(shop.shop_brief);
			shopImg.setImageWithURL(this,shop.shop_pic_url, R.drawable.default_image);
			shopPhone = shop.shop_phone;

		}
	}

}
