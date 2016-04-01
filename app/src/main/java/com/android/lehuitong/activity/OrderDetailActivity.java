package com.android.lehuitong.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.model.KtvAndCouponsOrderModel;
import com.android.lehuitong.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;

public class OrderDetailActivity extends BaseActivity implements OnClickListener,BusinessResponse{
	private TextView title_text;
	private ImageView title_back;
	private Intent intent;
	private TextView verificaty_code;
	private TextView seller_name;
	private TextView appointment_time;
	private TextView reservedphone;
	private TextView room_type;
	private String order_id;
	private KtvAndCouponsOrderModel ktvOrderModel;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);
		init();
	}

	private void init() {
		verificaty_code=(TextView) findViewById(R.id.verificaty_code);
		seller_name=(TextView) findViewById(R.id.seller_name);
		room_type=(TextView) findViewById(R.id.room_type);
		reservedphone=(TextView) findViewById(R.id.reservedphone);
		appointment_time=(TextView) findViewById(R.id.appointment_time);
		title_text=(TextView) findViewById(R.id.title_text);
		title_back=(ImageView) findViewById(R.id.title_back);
		title_text.setText("订单详情");
		title_back.setOnClickListener(this);
		order_id = getIntent().getStringExtra("order_id");
		ktvOrderModel = new KtvAndCouponsOrderModel(this);
		ktvOrderModel.addResponseListener(this);
		ktvOrderModel.getOrderDetail(order_id);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
		if (url.endsWith(ProtocolConst.ORDER_TICKET_DETAIL)) { 
			verificaty_code.setText(ktvOrderModel.ktvOrderList.get(0).verify_code);
//			room_type.setText(ktvOrderModel.orderDetail.)//订包类型，没有返回字段
			reservedphone.setText(ktvOrderModel.ktvOrderList.get(0).user_phone);
			seller_name.setText(ktvOrderModel.ktvOrderList.get(0).seller_name);
//			appointment_time.setText(ktvOrderModel.ktvOrderList.get(0).appointment_time);
		}
	}

	
	
}
