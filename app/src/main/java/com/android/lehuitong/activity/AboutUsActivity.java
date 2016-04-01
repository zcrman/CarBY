 package com.android.lehuitong.activity;

import com.BeeFramework.activity.BaseActivity;
import com.funmi.lehuitong.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AboutUsActivity extends BaseActivity implements OnClickListener{
	private ImageView title_back;
	private TextView title_text;
	private RelativeLayout call_phone;
	private Intent intent;
	
	//huangchengda测试提交
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		init();
	}
	private void init() {
		call_phone=(RelativeLayout) findViewById(R.id.call_phone);
		call_phone.setOnClickListener(this);
		title_back = (ImageView) findViewById(R.id.title_back);
		title_back.setOnClickListener(this);
		title_text = (TextView) findViewById(R.id.title_text);
		title_text.setText("关于我们");
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.call_phone:
			intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:02861154876"));  
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);  
			break;
		default:
			break;
		}
	}

}
