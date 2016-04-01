package com.android.lehuitong.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.adapter.SpinnerAdapter;
import com.android.lehuitong.model.AddressModel;
import com.android.lehuitong.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;

public class SelectAddressActivity extends BaseActivity implements OnClickListener, BusinessResponse {
	private ImageView title_back;
	private TextView title;
	private ListView listView;
	private SpinnerAdapter spinnerAdapter;
	private AddressModel addressModel;
	private int i = 0;

	private String country_id = "";
	private String province_id = "";
	private String city_id = "";
	private String county_id = "";

	private String country_name = "";
	private String province_name = "";
	private String city_name = "";
	private String county_name = "";
	int positions;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_adress);
		init();

	}

	private void init() {

		listView = (ListView) findViewById(R.id.select_address_listview);
		title_back = (ImageView) findViewById(R.id.title_back);
		title = (TextView) findViewById(R.id.title_text);
		Resources resource = (Resources) getBaseContext().getResources();
		String scoun = resource.getString(R.string.addressb_country);
		title.setText(scoun);

		setOnClickListener();

		addressModel = new AddressModel(this);
		addressModel.addResponseListener(this);
		addressModel.region("0", i);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				positions=position;
				SharedPreferences sharedPreferences = getSharedPreferences("wujay", Context.MODE_PRIVATE); //私有数据
				Editor editor = sharedPreferences.edit();//获取编辑器
			
				if (i == 1) {
					country_id = addressModel.regionsList0.get(position).id;
					country_name = addressModel.regionsList0.get(position).name;
					editor.putString("country_id", "country_id");
					editor.commit();//提交修改
				} else if (i == 2) {
					province_id = addressModel.regionsList0.get(position).id;
					province_name = addressModel.regionsList0.get(position).name;
					editor.putString("province_id", "province_id");
					editor.commit();//提交修改
				} else if (i == 3) {
					city_id = addressModel.regionsList0.get(position).id;
					city_name = addressModel.regionsList0.get(position).name;
					editor.putString("city_id", "city_id");
					editor.commit();//提交修改
				} else if (i == 4) {
					county_id = addressModel.regionsList0.get(position).id;
					county_name = addressModel.regionsList0.get(position).name;
					editor.putString("county_id", "county_id");
					editor.commit();//提交修改
				}
				addressModel.region(addressModel.regionsList0.get(position).id, i);

			}
		});

	}

	public void setOnClickListener() {
		title_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			
			if (i == 1) {
				finish();
			} else if (i == 2) {
				province_id = addressModel.regionsList0.get(positions).id;
				province_name = addressModel.regionsList0.get(positions).name;
				addressModel.region("0", 0);
				i=0;
			} else if (i == 3) {
				city_id = addressModel.regionsList0.get(positions).id;
				city_name = addressModel.regionsList0.get(positions).name;
				addressModel.region(country_id, i-2);
				i=1;
			} else if (i == 4) {
				county_id = addressModel.regionsList0.get(positions).id;
				county_name = addressModel.regionsList0.get(positions).name;
				addressModel.region(province_id, i-2);
				i=2;
			}
			
			break;
		default:
			break;
		}
	}

	public void setCountry() {
		Resources resource = (Resources) getBaseContext().getResources();
		String spro = resource.getString(R.string.select_province);
		String scity = resource.getString(R.string.select_city);
		String sarea = resource.getString(R.string.select_area);

		if (addressModel.regionsList0.size() == 0) {
			Intent intent = new Intent();
			intent.putExtra("country_id", country_id);
			intent.putExtra("province_id", province_id);
			intent.putExtra("city_id", city_id);
			intent.putExtra("county_id", county_id);

			intent.putExtra("country_name", country_name);
			intent.putExtra("province_name", province_name);
			intent.putExtra("city_name", city_name);
			intent.putExtra("county_name", county_name);
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
		i++;
		if (i==1) {
			title.setText("选择国家");
		}else if (i == 2) {
			title.setText(spro);
		} else if (i == 3) {
			title.setText(scity);
		} else if (i == 4) {
			title.setText(sarea);
		}

		spinnerAdapter = new SpinnerAdapter(this, addressModel.regionsList0);
		listView.setAdapter(spinnerAdapter);

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			if (i == 1) {
				Intent intent2 = new Intent();
				setResult(Activity.RESULT_OK, intent2);
				finish();
			} else if (i == 2) {
				province_id = addressModel.regionsList0.get(positions).id;
				province_name = addressModel.regionsList0.get(positions).name;
				addressModel.region("0", 0);
				i=0;
			} else if (i == 3) {
				city_id = addressModel.regionsList0.get(positions).id;
				city_name = addressModel.regionsList0.get(positions).name;
				addressModel.region(country_id, i-2);
				i=1;
			} else if (i == 4) {
				county_id = addressModel.regionsList0.get(positions).id;
				county_name = addressModel.regionsList0.get(positions).name;
				addressModel.region(province_id, i-2);
				i=2;
			}
		}
		return true;
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
		if (url.endsWith(ProtocolConst.REGION)) {
			setCountry();
		}
	}
	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}
}
