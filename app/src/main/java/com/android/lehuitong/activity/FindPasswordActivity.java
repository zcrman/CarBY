package com.android.lehuitong.activity;

import org.json.JSONException;
import org.json.JSONObject;

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

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.model.RegistrModel;
import com.android.lehuitong.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;

public class FindPasswordActivity extends BaseActivity implements
		OnClickListener, BusinessResponse {

	private EditText phone;
	private EditText verficationCode;
	private Button sendCodeBtn;
	private EditText password;
	private EditText passwordConfirm;
	private Button commit;
	private TextView topTitle;
	private ImageView topBack;
	private RegistrModel registrModel;
	private CountDownTimer countDownTimer;
	private SharedPreferences shared;
	@SuppressWarnings("unused")
	private SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_password);
		initView();
	}

	private void initView() {

		phone = (EditText) findViewById(R.id.user_phone);
		verficationCode = (EditText) findViewById(R.id.user_verfication_code);
		sendCodeBtn = (Button) findViewById(R.id.send_code_btn);
		password = (EditText) findViewById(R.id.user_password);
		passwordConfirm = (EditText) findViewById(R.id.user_password_confirm);
		commit = (Button) findViewById(R.id.commit_btn);
		topTitle = (TextView) findViewById(R.id.title_text);
		topBack = (ImageView) findViewById(R.id.title_back);
		topTitle.setText("找回密码");
		topBack.setVisibility(View.VISIBLE);
		registrModel = new RegistrModel(this);
		registrModel.addResponseListener(this);
		shared = getSharedPreferences("user", MODE_PRIVATE);
		editor = shared.edit();
		phone.setText(shared.getString("name", ""));
		countDownTimer = new CountDownTimer(60 * 1000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				sendCodeBtn.setText(millisUntilFinished / 1000 + "秒后重试");
				sendCodeBtn.setClickable(false);
				sendCodeBtn.setTextColor(Color.WHITE);
				sendCodeBtn
						.setBackgroundResource(R.drawable.user_send_code_btn_gray);

			}

			@Override
			public void onFinish() {
				sendCodeBtn.setText("获取验证码");
				sendCodeBtn.setClickable(true);
				sendCodeBtn.setTextColor(Color.WHITE);
				sendCodeBtn
						.setBackgroundResource(R.drawable.user_send_code_btn);
			}
		};

		setOnClickListener();

	}

	private void setOnClickListener() {

		sendCodeBtn.setOnClickListener(this);
		commit.setOnClickListener(this);
		topBack.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		String code = verficationCode.getText().toString();
		String telRegex = "[1][358]\\d{9}";
		String register_phone = phone.getText().toString();
		String register_Password = password.getText().toString();
		String register_PasswordConfirm = passwordConfirm.getText().toString();
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.send_code_btn:
			if (mNetworkInfo == null) {
				Toast.makeText(this, "无网络连接", Toast.LENGTH_LONG).show();
			} else if (register_phone.equals("")) {
				Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();

			} else if (register_phone.length() != 11) {
				Toast.makeText(this, "请输入11位手机号", Toast.LENGTH_SHORT).show();
			} else if (!register_phone.matches(telRegex)) {
				Toast.makeText(getApplicationContext(), "请输入正确的手机号",
						Toast.LENGTH_SHORT).show();
			} else {
				registrModel.getcode(register_phone, 3,countDownTimer);
			}
			break;
		case R.id.commit_btn:
			if (mNetworkInfo == null) {
				Toast.makeText(this, "无网络连接", Toast.LENGTH_LONG).show();
			} else if (register_phone.equals("")) {
				Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();

			} else if (register_phone.length() != 11) {
				Toast.makeText(this, "请输入11位手机号", Toast.LENGTH_SHORT).show();
			} else if (!register_phone.matches(telRegex)) {
				Toast.makeText(getApplicationContext(), "请输入正确的手机号",
						Toast.LENGTH_SHORT).show();
			} else if (code == null) {
				Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
			}

			else if (register_Password.equals("")) {
				Toast.makeText(getApplicationContext(), "请输入密码",
						Toast.LENGTH_SHORT).show();
			} else if (register_Password.length() < 6) {
				Toast.makeText(getApplicationContext(), "密码位数不少于6位",
						Toast.LENGTH_SHORT).show();

			} else if (register_PasswordConfirm.equals("")) {
				Toast.makeText(getApplicationContext(), "请输入确认密码",
						Toast.LENGTH_SHORT).show();

			} else if (register_Password.equals(register_PasswordConfirm)) {

				registrModel.findpsw(register_phone, register_Password, code);
				SharedPreferences sp = FindPasswordActivity.this
						.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
				Editor edit = sp.edit();
				edit.putString("name", register_phone);
				edit.putString("password", register_Password);
				edit.commit();
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
		if (url.endsWith(ProtocolConst.FORGOTTEN_PWD)) {
			Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(FindPasswordActivity.this,
					LoginActivity.class);
			startActivity(intent);
			finish();
		} else if (url.endsWith(ProtocolConst.GETCODE)) {
			Toast.makeText(this, "验证码已发送，请留意手机短信！", Toast.LENGTH_LONG).show();
		}

	}

}
