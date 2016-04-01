package com.android.lehuitong.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.adapter.KtvLocationListAdapter;
import com.android.lehuitong.model.SellerModel;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SHOP;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;

/**
 * ktv 门店选择页面
 * @author shenlw
 *
 */
public class KtvLocationChooseActivity extends BaseActivity implements OnClickListener,BusinessResponse {

	private TextView topTitle;
	private ImageView topBack;
	
	private ListView ktvLocationList;
	private KtvLocationListAdapter locationAdapter;
	
	private Intent intent;
	
	private SellerModel sellerModel;
	
	private List<SHOP> shopList = new ArrayList<SHOP>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ktv_location_choose);
		initView();
	}

	private void initView() {
		
		topTitle = (TextView) findViewById(R.id.title_text);
		topBack = (ImageView) findViewById(R.id.title_back);
		topTitle.setText("商户列表");
		topBack.setVisibility(View.VISIBLE);
		ktvLocationList = (ListView) findViewById(R.id.ktv_location_list);
		locationAdapter = new KtvLocationListAdapter(this);
		
		sellerModel = new SellerModel(this);
		sellerModel.addResponseListener(this);
		sellerModel.getSellerList(2+"","","", 0, 0);
		setOnClickListener();
		
		
	}

	private void setOnClickListener() {
		
		topBack.setOnClickListener(this);
		ktvLocationList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				intent=new Intent();
				intent.putExtra("shop_id", shopList.get(position).shop_id);
				intent.putExtra("shop_name",shopList.get(position).shop_name);
				intent.putExtra("shop_address", shopList.get(position).shop_address);
				intent.putExtra("shop_phone", shopList.get(position).shop_phone);
				intent.putExtra("seller_id", shopList.get(position).shop_id);
				setResult(Activity.RESULT_OK, intent);
				finish();
				
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.title_back:
			intent=new Intent(KtvLocationChooseActivity.this,CouponDetailActivity.class);
			setResult(Activity.RESULT_OK, intent);
			finish();
			break;

		default:
			break;
		}
		
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
		if (url.endsWith(ProtocolConst.SELLER_LIST)) {
			if (sellerModel.shopList.size()>0) {
				shopList = sellerModel.shopList;
				locationAdapter.bindData(sellerModel.shopList);
				ktvLocationList.setAdapter(locationAdapter);
			}else {
				Toast.makeText(this, "暂没有相关商家~", Toast.LENGTH_SHORT).show();
			}
		}
		
	}

	
}
