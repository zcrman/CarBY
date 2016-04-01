package com.android.lehuitong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.funmi.lehuitong.R;

public class HelpActivity extends BaseActivity implements OnClickListener {
	private ImageView title_back;
	private TextView title_text;
	private TextView getintegral;
	private TextView getintegral_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		init();
	}

	public void init() {
		getintegral = (TextView) findViewById(R.id.getintegral);
		getintegral_content = (TextView) findViewById(R.id.getintegral_content);
		title_back = (ImageView) findViewById(R.id.title_back);
		title_back.setOnClickListener(this);
		title_text = (TextView) findViewById(R.id.title_text);
		title_text.setText("帮助");
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String title = bundle.getString("title");
		String content = bundle.getString("content");
		getintegral.setText(title);
		getintegral_content.setText(Html.fromHtml(content.toString()));

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
