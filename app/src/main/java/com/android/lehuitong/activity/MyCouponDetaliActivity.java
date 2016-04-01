package com.android.lehuitong.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.adapter.MyCouponDetaliAdapter;
import com.android.lehuitong.model.KtvAndCouponsOrderModel;
import com.android.lehuitong.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.funmi.lehuitong.R;

/**
 * 优惠券详情
 * */
public class MyCouponDetaliActivity extends BaseActivity implements
		OnClickListener, BusinessResponse {
	private ListView cordelist;
	private ImageView title_back;
	private TextView title_text;
	private Intent intent;
	private KtvAndCouponsOrderModel orderModel;
	private String order_id;
	private View footview;// list下面的控件部分
	private TextView coupon_shop_name;
	private TextView coupon_shop_address;
	private TextView coupon_shop_phone;
	private TextView coupon__name;
	private TextView coupon_price;
	private TextView coupon_brief;
	private TextView couipon_date_able;
	private MyCouponDetaliAdapter adapter;
	private String order_status;
	private String name;
	private String address;
	private String number;
	private String type;
	private LinearLayout coupondetail_linearLayout;
	
	private TextView youhui;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_counpondetail);
		inint();
		setOnClickListener();
	}

	private void inint() {
		cordelist = (ListView) findViewById(R.id.code_list);
		youhui = (TextView) findViewById(R.id.youhuijuan_text);
		footview = LayoutInflater.from(this).inflate(
				R.layout.activity_coupondetail_footer, null);
		coupon_shop_name = (TextView) footview
				.findViewById(R.id.coupon_shop_name);
		coupon_shop_address = (TextView) footview
				.findViewById(R.id.coupon_shop_address);
		coupon_shop_phone = (TextView) footview
				.findViewById(R.id.coupon_shop_phone);
		coupon__name = (TextView) footview.findViewById(R.id.coupon__name);
		coupon_price = (TextView) footview.findViewById(R.id.coupon_price);
		coupon_brief = (TextView) footview.findViewById(R.id.coupon_brief);
//		couipon_date_able = (TextView) footview
//				.findViewById(R.id.couipon_date_able);
		title_back = (ImageView) findViewById(R.id.title_back);
		title_text = (TextView) findViewById(R.id.title_text);
		title_text.setText("优惠详情");
		cordelist.addFooterView(footview);
		order_status = getIntent().getStringExtra("order_status");
		order_id = getIntent().getStringExtra("order_id");
		name = getIntent().getStringExtra("shop_name");
		address = getIntent().getStringExtra("shop_address");
		number = getIntent().getStringExtra("shop_number");
		coupondetail_linearLayout = (LinearLayout)footview.findViewById(R.id.coupondetail_linearLayout);
		type = getIntent().getStringExtra("type");
		//判断是由那个界面传过来的值；youhuijuan为优惠卷订单传回来的
		if(type.equals("youhuijuan")){
			orderModel = new KtvAndCouponsOrderModel(this);
			orderModel.addResponseListener(this);
			orderModel.getOrderDetail(order_id);
			adapter = new MyCouponDetaliAdapter(this,orderModel);
		}//ktv为预定传回来的值
		else if(type.equals("ktv")){
			Log.i("order_status", order_status);
			if(order_status.equals("8")||
					order_status.equals("5")){
				coupon_shop_name.setText(name);
				coupon_shop_address.setText(address);
				coupon_shop_phone.setText(number);
				youhui.setVisibility(View.GONE);
				coupondetail_linearLayout.setVisibility(View.GONE);
				cordelist.setAdapter(null);
			}else if(order_status.equals("1")||order_status.equals("2")||
					order_status.equals("3")||order_status.equals("4")||order_status.equals("6")||
					order_status.equals("7")||order_status.equals("9")){
				orderModel = new KtvAndCouponsOrderModel(this);
				orderModel.addResponseListener(this);
				orderModel.getOrderDetail(order_id);
				adapter = new MyCouponDetaliAdapter(this,orderModel);
			}
		}
	}

	void setOnClickListener() {
		title_text.setOnClickListener(this);
		title_back.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;

		default:
			break;
		}
	}

	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.ORDER_TICKET_DETAIL)) {
			String code = orderModel.ktvOrderList.get(0).verify_code;
			String[] a = code.split(",");
			adapter.getdata(a);
			cordelist.setAdapter(adapter);
			coupon_shop_name.setText(orderModel.ktvOrderList.get(0).seller_name);
			coupon_shop_address.setText(orderModel.ktvOrderList.get(0).shop_address);
			coupon_shop_phone.setText(orderModel.ktvOrderList.get(0).shop_phone);
			coupon__name.setText(orderModel.ktvOrderList.get(0).good.goods_name);
			coupon_price.setText("￥"+orderModel.ktvOrderList.get(0).money);
			coupon_brief.setText(orderModel.ktvOrderList.get(0).good.goods_brief);
//			couipon_date_able.setText(orderModel.ktvOrderList.get(0).add_time.substring(0, 10));
		}

	}

}
