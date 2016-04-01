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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.model.RegistrModel;
import com.android.lehuitong.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;

/**
 * 注册页面
 * 
 * @author shenlw
 * 
 */
public class RegisterActivity extends BaseActivity implements OnClickListener,
		BusinessResponse {

	private EditText registerPhone;
	private EditText registerCode;
	private Button sendCodeBtn;
	private EditText registerPassword;
	private EditText registerPasswordConfirm;
	private TextView lehuitongService;
	private Button registerBtn;
	private LinearLayout user_agree;
	private TextView topTitle;
	private ImageView topBack;
	private CountDownTimer countDownTimer;
	private RegistrModel registrModel;
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
	}

	private void initView() {
		user_agree=(LinearLayout) findViewById(R.id.user_agree);
		topTitle = (TextView) findViewById(R.id.title_text);
		topBack = (ImageView) findViewById(R.id.title_back);
		topBack.setVisibility(View.VISIBLE);
		topTitle.setText("注册");
		registerPhone = (EditText) findViewById(R.id.register_phone);
		registerPassword = (EditText) findViewById(R.id.register_password);
		registerPasswordConfirm = (EditText) findViewById(R.id.register_password_confirm);
		registerCode = (EditText) findViewById(R.id.register_verfication_code);
		sendCodeBtn = (Button) findViewById(R.id.register_send_code_btn);
		registerBtn = (Button) findViewById(R.id.register_btn);
		lehuitongService = (TextView) findViewById(R.id.lehuitong_service);
		registrModel = new RegistrModel(this);
		registrModel.addResponseListener(this);

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
		user_agree.setOnClickListener(this);
		sendCodeBtn.setOnClickListener(this);
		topBack.setOnClickListener(this);
		lehuitongService.setOnClickListener(this);
		registerBtn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		String code = registerCode.getText().toString();
		String abc = System.currentTimeMillis() + "@qq.com";
		String telRegex = "[1][358]\\d{9}";
		String register_phone = registerPhone.getText().toString();
		String register_Password = registerPassword.getText().toString();
		String register_PasswordConfirm = registerPasswordConfirm.getText()
				.toString();
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.register_send_code_btn:

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
				registrModel.getcode(registerPhone.getText().toString(),1,countDownTimer);

			}
			break;
		case R.id.register_btn:

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

				registrModel.signup(register_phone, register_Password, code);
				SharedPreferences sp = RegisterActivity.this
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
		case R.id.lehuitong_service:

			break;
		case R.id.user_agree:
			Intent intent=new Intent(this,UserAgreeActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}

	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.REGISTER)) {
			shared = getSharedPreferences("user", MODE_PRIVATE);
			editor = shared.edit();
			editor.putString("name", registerPhone.getText().toString());
			editor.commit();
			Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(RegisterActivity.this,
					LehuitongMainActivity.class);
			startActivity(intent);
			finish();
		}
		else if (url.endsWith(ProtocolConst.GETCODE)) {
			Toast.makeText(this, "验证码已发送，请留意手机短信！", Toast.LENGTH_LONG).show();
		}
	}

}
