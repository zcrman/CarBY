package com.android.lehuitong.component;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.activity.HiActivity;
import com.android.lehuitong.activity.LehuitongMainActivity;
import com.android.lehuitong.activity.MyEatInlehuiActivity;
import com.android.lehuitong.activity.MyLeHuiActivity;
import com.android.lehuitong.activity.MyPurchaseActivity;
import com.android.lehuitong.activity.ShopDetailActivity;
import com.android.lehuitong.model.GoodsListModel;
import com.android.lehuitong.protocol.CATEGORYGOODS;
import com.android.lehuitong.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class HomeButtonHolder implements OnClickListener,BusinessResponse{

	public RelativeLayout homeEat;
	public RelativeLayout homeMy;
	public RelativeLayout homeHi;
	public RelativeLayout homePurchase;
	private Context context;
	private GoodsListModel goodModel;
	private ArrayList<String> catNameList = new ArrayList<String>();
	private ArrayList<String> catIdList = new ArrayList<String>();
	
	public HomeButtonHolder(Context context){
		this.context = context;
		
	}
	
	public void initView(View view){
		homeEat = (RelativeLayout) view.findViewById(R.id.home_eat);
		homeMy = (RelativeLayout) view.findViewById(R.id.home_my);
		homeHi = (RelativeLayout) view.findViewById(R.id.home_hi);
		homePurchase = (RelativeLayout) view.findViewById(R.id.home_purchase);
		goodModel = new GoodsListModel(context);
		goodModel.addResponseListener(this);
	}
	public void setOnClickListener(){
		homeEat.setOnClickListener(this);
		homeMy.setOnClickListener(this);
		homeHi.setOnClickListener(this);
		homePurchase.setOnClickListener(this);
		goodModel.getGoodCatList("999");
	}
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.home_eat:
			intent = new Intent(context, MyEatInlehuiActivity.class);
			intent.putExtra("type", "1");
			intent.putExtra("title", "美食");
			context.startActivity(intent);
			break;
		case R.id.home_my:
			intent = new Intent(context, MyLeHuiActivity.class);
			context.startActivity(intent);
			break;
		case R.id.home_hi:
			intent = new Intent(context, MyEatInlehuiActivity.class);
			intent.putExtra("type", "5");
			intent.putExtra("title", "酒店");
			context.startActivity(intent);
			break;
		case R.id.home_purchase:
			intent = new Intent(context, MyPurchaseActivity.class);
			intent.putStringArrayListExtra("catNameList", catNameList);
			intent.putStringArrayListExtra("catIdList", catIdList);
			context.startActivity(intent);
			break;

		default:
			break;
		}
		
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
		
		if (url.endsWith(ProtocolConst.CATEGORY_LIST)) {
//			catNameList.add("海淘品");
//			catNameList.add("精品");
//			catNameList.add("热销");
			for (int i = 0; i < goodModel.categoryList.size(); i++) {
				catNameList.add(goodModel.categoryList.get(i).name);
				
				catIdList.add(goodModel.categoryList.get(i).id+"");
			}
		}
	}
	
}
