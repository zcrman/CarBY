package com.android.lehuitong.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.BeeFramework.activity.BaseActivity;
import com.android.lehuitong.adapter.GridAdapter;
import com.funmi.lehuitong.R;

public class MyEatSearchActivity extends BaseActivity implements
		OnClickListener {

	private TextView eat_search_cancel;
	// private ImageView eat_search;
	private EditText search_search;
	private String str = null;
	private GridView gridView;
	private GridAdapter gridAdapter;
	private String[] string = { "迟疑", "下尴尬刚", "钢卡改变", "法定分", "打打", "嘎嘎",
			"爱和人身", "啊嘎嘎", "发发发" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eat_search);
		initView();
	}

	private void initView() {
		gridView = (GridView) findViewById(R.id.gridview);
//		gridAdapter = new GridAdapter(this, string);
//		gridView.setAdapter(gridAdapter);

		eat_search_cancel = (TextView) findViewById(R.id.eat_search_cancel1);
		search_search = (EditText) findViewById(R.id.search_search1);
		setOnClickListener();

	}

	private void setOnClickListener() {
		eat_search_cancel.setOnClickListener(this);
		search_search.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					// 先隐藏键盘
					((InputMethodManager) search_search.getContext()
							.getSystemService(Context.INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(MyEatSearchActivity.this
									.getCurrentFocus().getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
					// 跳转activity

					str = search_search.getText().toString();
					Intent intent = new Intent();
					intent.putExtra("keyword", str);
					intent.putExtra("type", "1");
					MyEatSearchActivity.this.setResult(-2, intent);
					MyEatSearchActivity.this.finish();
					return true;
				}
				return false;
			}
		});

	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.eat_search_cancel1:
//			intent = new Intent(MyEatSearchActivity.this,
//					MyEatInlehuiActivity.class);
			intent=new Intent();
			String couponName=search_search.getText().toString();
			intent.putExtra("keyword", couponName);
			setResult(-2, intent);
			finish();
			break;
		}
	}

}
