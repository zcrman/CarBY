package com.funmi.lehuitong;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
@SuppressLint("SetJavaScriptEnabled")
public class WebActivity extends Activity {

	private WebView view;
	private final String SHOP_INFO="shop_info";
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		view=(WebView) findViewById(R.id.webView);
		WebSettings settings = view.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setLoadWithOverviewMode(true);
		settings.setUseWideViewPort(true);
		settings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
		//设置WebView可触摸放大缩小
		settings.setBuiltInZoomControls(true);
		settings.setTextSize(WebSettings.TextSize.LARGEST);
		Intent intent=getIntent();
		String data=intent.getStringExtra(SHOP_INFO);
		view.loadDataWithBaseURL(null, data, null, "utf-8", null);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if ((keyCode == KeyEvent.KEYCODE_BACK) && view.canGoBack())  
	        {  
	            view.goBack();//返回webView的上一页面      
	            return true;  
	        }  
		return super.onKeyDown(keyCode, event);
	}
}
