package com.android.lehuitong.activity;




import com.funmi.lehuitong.R;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MyOrderActivity extends TabActivity implements OnClickListener{
	private ImageView title_back;
	private TextView title_text;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order);	
		init();
		TabHost th = getTabHost();
		th.setup();
		View view1 = LayoutInflater.from(this).inflate(R.layout.tab_reserve_order, null);
		View view2 = LayoutInflater.from(this).inflate(R.layout.tab_buy_order, null);
		View view3 =LayoutInflater.from(this).inflate(R.layout.tab_coupon_order, null);
		th.addTab(th.newTabSpec("Tab1").setIndicator(view1)
				.setContent(new Intent(MyOrderActivity.this, ReserveOrederTable.class)));
		th.addTab(th.newTabSpec("Tab2").setIndicator(view2)
				.setContent(new Intent(MyOrderActivity.this, BuyOrederTable.class)));
		th.addTab(th.newTabSpec("Tab3").setIndicator(view3)
				.setContent(new Intent(MyOrderActivity.this, CouponOrderActivity.class)));
	}

	private void init() {
		title_back=(ImageView) findViewById(R.id.title_back);
		title_back.setOnClickListener(this);
		 title_text=(TextView) findViewById(R.id.title_text);
		 title_text.setText("我的订单");
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

}
