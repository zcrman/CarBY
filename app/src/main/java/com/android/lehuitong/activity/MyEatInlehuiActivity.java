package com.android.lehuitong.activity;

import android.app.Notification.Action;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.android.lehuitong.LehuitongApp;
import com.android.lehuitong.fragment.EatFragmentTab;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.external.activeandroid.util.Log;
import com.funmi.lehuitong.R;

public class MyEatInlehuiActivity extends BaseActivity implements
		OnClickListener {
	private ImageView title_back;
	private TextView title_text;
	private ImageView title_search;
	private EatFragmentTab eatFragmentTab;
	private String strKeyword;
	private String provider = LocationManager.GPS_PROVIDER;
	private Intent intent;
	private String str = "";
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eat);
		
		intent = getIntent();
		initView();
//		getGps();
		LehuitongApp.getInstance().addActivity(this);
		eatFragmentTab = (EatFragmentTab) getFragmentManager()
				.findFragmentById(R.id.tabs_fragment);
		eatFragmentTab.setNetworkModel(this,
				Integer.parseInt(intent.getStringExtra("type")), strKeyword);

	}

	private void initView() {
		title_back = (ImageView) findViewById(R.id.title_back);
		title_search = (ImageView) findViewById(R.id.title_search);
		title_text = (TextView) findViewById(R.id.title_text);
		title_text.setText(intent.getStringExtra("title"));
		
		
		setOnClickListener();
	}

	private void setOnClickListener() {
		title_back.setOnClickListener(this);
		title_search.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.title_search:
			Intent intent = new Intent(MyEatInlehuiActivity.this,
					MyEatSearchActivity.class);
			startActivityForResult(intent, 1);
			break;

		}

	}

	//
	// 第一个参数为请求码，即调用startActivityForResult()传递过去的值
	// 第二个参数为结果码，结果码用于标识返回数据来自哪个新Activity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case -2:// 关键字
			strKeyword = data.getExtras().getString("keyword");
			if (!strKeyword.equals("")) {
				title_text.setText(strKeyword);
				eatFragmentTab.setNetworkModel(this,
						Integer.parseInt(intent.getStringExtra("type")),
						strKeyword);
			} else {
				if (intent.getStringExtra("type") == "1") {
					title_text.setText("美食");
				} else if (intent.getStringExtra("type") == "5") {
					title_text.setText("酒店");
				}
				eatFragmentTab.setNetworkModel(this,
						Integer.parseInt(intent.getStringExtra("type")), str);
			}
			break;
		default:
			break;
		}

	}

	public String getStrKeyword() {
		return strKeyword;
	}

	public void setStrKeyword(String strKeyword) {
		this.strKeyword = strKeyword;
	}

	public String KeywordSend() {
		return strKeyword;
	}


}
