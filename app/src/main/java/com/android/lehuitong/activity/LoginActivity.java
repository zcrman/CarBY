package com.android.lehuitong.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.LehuitongApp;

import com.android.lehuitong.LehuitongApp;
import com.android.lehuitong.model.CidModel;
import com.android.lehuitong.model.LoginModel;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SESSION;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;

/**
 * 登录页面
 * 
 * @author shenlw
 * 
 */
public class LoginActivity extends BaseActivity implements OnClickListener,
		BusinessResponse {

	private EditText userPhone;
	private EditText userPassword;
	private Button loginBtn;
	private ImageView userResigter;
	private ImageView userFindPassword;
	private TextView topTitle;
	private ImageView topBack;
	private LoginModel loginModel;
	private String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
    private long mExitTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();

		// debug模式
		// BeeFrameworkApp.getInstance().showBug(this);
	}

	private void initView() {
		
		userPhone = (EditText) findViewById(R.id.user_phone);
		userPassword = (EditText) findViewById(R.id.user_password);
		loginBtn = (Button) findViewById(R.id.login_btn);
		userResigter = (ImageView) findViewById(R.id.user_register);
		userFindPassword = (ImageView) findViewById(R.id.user_find_password);
		loginModel = new LoginModel(this);
		loginModel.addResponseListener(this);
		
		shared = getSharedPreferences("user", MODE_PRIVATE);
		editor = shared.edit();
		userPhone.setText(shared.getString("name", ""));
		//userPassword.setText(shared.getString("password",""));
		setOnClickListener();
	}

	private void setOnClickListener() {
		loginBtn.setOnClickListener(this);
		userResigter.setOnClickListener(this);
		userFindPassword.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		Intent intent;
		String strUser = userPhone.getText().toString();
		String strPassword = userPassword.getText().toString();
		switch (v.getId()) {
		case R.id.login_btn:
			ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = connectivityManager
					.getActiveNetworkInfo();

			if (mNetworkInfo == null) {
				Toast.makeText(LoginActivity.this, "无网络连接", Toast.LENGTH_LONG)
						.show();
			} else {
				if (strUser.equals("")) {
					Toast.makeText(getApplicationContext(), "请输入手机号",
							Toast.LENGTH_LONG).show();
				} else if (strUser.length() != 11) {
					Toast.makeText(getApplicationContext(), "请输入11位手机号",
							Toast.LENGTH_LONG).show();
				} else if (!strUser.matches(telRegex)) {
					Toast.makeText(getApplicationContext(), "请输入正确的手机号",
							Toast.LENGTH_LONG).show();
				} else if (strPassword.equals("")) {
					Toast.makeText(getApplicationContext(), "请输入密码",
							Toast.LENGTH_LONG).show();
				} else if (strPassword.length() < 6) {
					Toast.makeText(getApplicationContext(), "请输入至少6位密码",
							Toast.LENGTH_LONG).show();

				} else if (strPassword.length() >= 6) {
					loginModel.login(strUser,
							strPassword);
					
					
				}
			}
			break;
		case R.id.user_register:
			intent = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.user_find_password:
			intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}

	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.LOGIN)) {
			String login = null;
			editor.putString("name", userPhone.getText().toString());
			editor.putString("password", userPassword.getText().toString());
			editor.commit();
//			CidModel cidModel=new CidModel(this);
//			cidModel.sendCid(this);
			try {
				login=getIntent().getExtras().getString("agin_login");
				
			} catch (Exception e) {
				// TODO: handle exception
			}	
			if (login!=null&&Integer.parseInt(login)==1) {
				Intent intent=new Intent(this, LehuitongMainActivity.class);
				startActivity(intent);
			}
			finish();

		}

	}
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
         if (keyCode == KeyEvent.KEYCODE_BACK) {
                 if ((System.currentTimeMillis() - mExitTime) > 2000) {
                         Object mHelperUtils;
                         Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                         mExitTime = System.currentTimeMillis();

                 } else {
                	 LehuitongApp.getInstance().exit();
                	 System.gc();
                	 System.exit(0);
                 }
                 return true;
         }
         return super.onKeyDown(keyCode, event);
 }
}
