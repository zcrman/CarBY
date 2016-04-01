package com.android.lehuitong.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.model.RegistrModel;
import com.android.lehuitong.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PasswordManagerActivity extends BaseActivity implements
		OnClickListener,BusinessResponse {
	private ImageView title_back;
	private TextView title_text;
	private EditText old_psw;
	private EditText manager_psw;
	private EditText manager_pswagin;
	private Button manager_btn;
	private RegistrModel registrModel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_manager);
		init();
	}

	private void init() {
		manager_btn = (Button) findViewById(R.id.manager_btn);
		old_psw = (EditText) findViewById(R.id.old_psw);
		manager_psw = (EditText) findViewById(R.id.manager_psw);
		manager_pswagin = (EditText) findViewById(R.id.manager_pswagin);
		title_back = (ImageView) findViewById(R.id.title_back);

		title_text = (TextView) findViewById(R.id.title_text);
		title_text.setText("修改密码");
		registrModel=new RegistrModel(this);
		registrModel.addResponseListener(this);
//		countDownTimer = new CountDownTimer(60 * 1000, 1000) {
//			@Override
//			public void onTick(long millisUntilFinished) {
//				manager_codebtn.setText(millisUntilFinished / 1000 + "秒后重试");
//				manager_codebtn.setClickable(false);
//				manager_codebtn.setTextColor(Color.WHITE);
//				manager_codebtn
//						.setBackgroundResource(R.drawable.user_send_code_btn_gray);
//
//			}
//
//			@Override
//			public void onFinish() {
//				manager_codebtn.setText("获取验证码");
//				manager_codebtn.setClickable(true);
//				manager_codebtn.setTextColor(Color.WHITE);
//				manager_codebtn
//						.setBackgroundResource(R.drawable.user_send_code_btn);
//			}
//		};
		setOnClickListener();
	}

	public void setOnClickListener() {
		title_back.setOnClickListener(this);
		manager_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String telRegex = "[1][358]\\d{9}";
		String oldpsw =old_psw.getText().toString();
		String register_Password = manager_psw.getText().toString();
		String register_PasswordConfirm = manager_pswagin.getText().toString();
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
//		case R.id.manager_codebtn:
//			if (mNetworkInfo == null) {
//				Toast.makeText(this, "无网络连接", Toast.LENGTH_LONG).show();
//			} else if (register_phone.equals("")) {
//				Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
//
//			} else if (register_phone.length() != 11) {
//				Toast.makeText(this, "请输入11位手机号", Toast.LENGTH_SHORT).show();
//			} else if (!register_phone.matches(telRegex)) {
//				Toast.makeText(getApplicationContext(), "请输入正确的手机号",
//						Toast.LENGTH_SHORT).show();
//			} else {
//				registrModel.getcode(register_phone, 3,countDownTimer);
//			}
//			break;
		case R.id.manager_btn:
			if (mNetworkInfo == null) {
				Toast.makeText(this, "无网络连接", Toast.LENGTH_LONG).show();
			}
			else if (register_Password.equals("")) {
				Toast.makeText(getApplicationContext(), "请输入密码",
						Toast.LENGTH_SHORT).show();
			} else if (register_Password.length() < 6) {
				Toast.makeText(getApplicationContext(), "请输入6至25位的密码",
						Toast.LENGTH_SHORT).show();

			} else if (register_PasswordConfirm.equals("")) {
				Toast.makeText(getApplicationContext(), "请输入确认密码",
						Toast.LENGTH_SHORT).show();

			} else if (register_Password.equals(register_PasswordConfirm)) {

				registrModel.Modify_PSW(oldpsw, register_Password, register_PasswordConfirm);
//				SharedPreferences sp = FindPasswordActivity.this
//						.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
//				Editor edit = sp.edit();
//				edit.putString("name", register_phone);
//				edit.putString("password", register_Password);
//				edit.commit();
			} else {
				Toast.makeText(getApplicationContext(), "两次密码不一致",
						Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.CHANGE_PWD)) {
			Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(PasswordManagerActivity.this,
					LoginActivity.class);
			startActivity(intent);
			finish();
		} else if (url.endsWith(ProtocolConst.GETCODE)) {
			Toast.makeText(this, "验证码已发送，请留意手机短信！", Toast.LENGTH_LONG).show();
		}
		
	}

}
