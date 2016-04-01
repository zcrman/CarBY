package com.android.lehuitong.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.activity.StartActivity;
import com.android.lehuitong.protocol.SESSION;

public class EnterActivity extends BaseActivity {

	private SharedPreferences shared;
	private SharedPreferences sharedPreferences; 
	@SuppressWarnings("unused")
	private SharedPreferences.Editor editor;

	@SuppressLint("CommitPrefEdits") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		shared = getSharedPreferences("userInfo", 0);
		editor = shared.edit();
		sharedPreferences = getSharedPreferences("user", 0);
		SESSION session = SESSION.getInstance();
		session.sid = sharedPreferences.getString("sid", "");
		session.uid = sharedPreferences.getString("uid", "");
		ConnectivityManager connectMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectMgr.getActiveNetworkInfo();
		if (info !=null &&info.getType() == ConnectivityManager.TYPE_WIFI) {
			editor.putString("netType", "wifi");
		}else if (info !=null && info.getType() ==  ConnectivityManager.TYPE_MOBILE) {
			editor.putString("netType", "mobile");
		}
		editor.commit();
		boolean isFirstRun = shared.getBoolean("isFirstRun", true);
		if (!isFirstRun) {
			Intent it = new Intent(this, StartActivity.class);
			startActivity(it);
			finish();

		} else {
			Intent intent = new Intent(this, GalleryImageActivity.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
}
