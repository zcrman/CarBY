package com.android.lehuitong.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.LehuitongApp;
import com.android.lehuitong.model.AddressModel;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.view.KtvPhoneDialog;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;

public class NewAddressActivity extends BaseActivity implements OnClickListener ,BusinessResponse{
	private ImageView title_back;
	private TextView title_text;
	private Button save_address;
	private RelativeLayout select_address;
	private RelativeLayout nameLayout;
	private RelativeLayout phoneLayout;
	private RelativeLayout detaiLayout;
	private String country;
	private String city;
	private String province;
	private String county;
	private TextView name;
	private TextView phone;
	private TextView location;
	private TextView addressDetail;

	private KtvPhoneDialog dialog;

	private AddressModel addressModel;

	Intent intent;
	private String consignee;
	private String mobile;
	private String address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_address);
		init();
	}

	private void init() {
		select_address = (RelativeLayout) findViewById(R.id.select_address);
		nameLayout = (RelativeLayout) findViewById(R.id.my_coupons);
		phoneLayout = (RelativeLayout) findViewById(R.id.user_phone_layout);
		detaiLayout = (RelativeLayout) findViewById(R.id.user_address_layout);
		select_address.setOnClickListener(this);
		nameLayout.setOnClickListener(this);
		phoneLayout.setOnClickListener(this);
		detaiLayout.setOnClickListener(this);
		title_back = (ImageView) findViewById(R.id.title_back);
		title_back.setOnClickListener(this);
		title_text = (TextView) findViewById(R.id.title_text);
		title_text.setText("新建地址");
		save_address = (Button) findViewById(R.id.save_address);
		save_address.setOnClickListener(this);

		name = (TextView) findViewById(R.id.address_name);
		phone = (TextView) findViewById(R.id.address_phone);
		location = (TextView) findViewById(R.id.address_location);
		addressDetail = (TextView) findViewById(R.id.address_detail);
		
		addressModel = new AddressModel(this);
		addressModel.addResponseListener(this);
		
	

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.save_address:
			
			consignee = name.getText().toString();
			mobile =phone.getText().toString();
			address = addressDetail.getText().toString();
			if(TextUtils.isEmpty(consignee)){
				Toast.makeText(getApplication(), "地址不能为空", 0).show();
				break;
			}else if(TextUtils.isEmpty(mobile)){
				Toast.makeText(getApplication(), "电话不能为空", 0).show();
				break;
			}else if(TextUtils.isEmpty(address)){
				Toast.makeText(getApplication(), "收货地址不能为空", 0).show();
				break;
			}else if(TextUtils.isEmpty(location.getText().toString())){
				Toast.makeText(getApplication(), "请选择地区", 0).show();
				break;
				}else{
			addressModel.addAddress(consignee, "", "", mobile, "", address, country, province, city, county);
			finish();
				}
		case R.id.select_address:
			intent = new Intent(NewAddressActivity.this, SelectAddressActivity.class);
			startActivityForResult(intent, 2);
			break;
		case R.id.my_coupons:

			dialog = new KtvPhoneDialog(this,R.style.customer_dialog,"收货人", "请输入收货人名字") {
				@Override
				public void commitPhone(String phoneStr) {
					super.commitPhone(phoneStr);
					if (phoneStr.length() > 0) {
						name.setText(phoneStr);
						dialog.dismiss();
					} else {
						Toast.makeText(NewAddressActivity.this, "不可为空", Toast.LENGTH_SHORT).show();
					}
				}
			};
			dialog.show();

			break;
		case R.id.user_phone_layout:

			
			dialog = new KtvPhoneDialog(this,R.style.customer_dialog,"手机号码", "请输入11位手机号码",InputType.TYPE_CLASS_NUMBER,11) {
				@Override
				public void commitPhone(String phoneStr) {
					super.commitPhone(phoneStr);
					if (phoneStr.length() == 11&&phoneStr.matches("[1][358]\\d{9}")) {
						phone.setText(phoneStr);
						dialog.dismiss();
					} else {
						Toast.makeText(NewAddressActivity.this, "请输入11位有效电话号码", Toast.LENGTH_SHORT).show();
					}
				}
			};
			dialog.show();

			break;
		case R.id.user_address_layout:

			dialog = new KtvPhoneDialog(this,R.style.customer_dialog,"详细地址", "请输入详细地址") {
				@Override
				public void commitPhone(String phoneStr) {
					super.commitPhone(phoneStr);
					if (phoneStr.length() > 0) {
						addressDetail.setText(phoneStr);
						dialog.dismiss();
					} else {
						Toast.makeText(NewAddressActivity.this, "不可为空", Toast.LENGTH_SHORT).show();
					}
				}
			};
			dialog.show();

			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (data!=null) {
			if (requestCode == 2) {
				String area=data.getStringExtra("area");
				country = data.getStringExtra("country_id");
				province = data.getStringExtra("province_id");
				city = data.getStringExtra("city_id");
				county = data.getStringExtra("county_id");
				if(country==null||province==null||city==null||county==null){
					location.setText("");
				}else{
					StringBuffer sbf = new StringBuffer();
					sbf.append(data.getStringExtra("country_name") + " ");
					sbf.append(data.getStringExtra("province_name") + " ");
					sbf.append(data.getStringExtra("city_name") + " ");
					sbf.append(data.getStringExtra("county_name") + " ");
					location.setText(sbf);
				}
			}
		}
	}

	
	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
		if (url.endsWith(ProtocolConst.ADDRESS_ADD)) {
			Toast.makeText(NewAddressActivity.this, "添加成功",Toast.LENGTH_SHORT).show();
			startActivity(new Intent(this,AddressManagerActivity.class));
			finish();
		}
		
	}

}
