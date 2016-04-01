package com.android.lehuitong.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.adapter.AddressManagerAdapter;
import com.android.lehuitong.model.AddressModel;
import com.android.lehuitong.protocol.ADDRESS;
import com.android.lehuitong.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;

public class AddressManagerActivity extends BaseActivity implements OnClickListener, BusinessResponse {
	private ImageView title_back;
	private TextView title_text;
	private ListView address_manager_listview;
	private AddressManagerAdapter adapter;
	private RelativeLayout address_empty;
	private Button add_address_button;
	private Intent intent;
	private boolean flag = true;
	public List<ADDRESS> addressList = new ArrayList<ADDRESS>();
	int i = 1;
	public AddressModel addressModel;
	private boolean isSelectAddress=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_manager);
		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
		addressModel.getAddressList();
	}

	private void init() {
		address_empty=(RelativeLayout) findViewById(R.id.address_empty);
		address_manager_listview = (ListView) findViewById(R.id.address_address_listview);
		add_address_button = (Button) findViewById(R.id.add_address_button);
		title_back = (ImageView) findViewById(R.id.title_back);
		title_text = (TextView) findViewById(R.id.title_text);
		title_text.setText("地址管理");
		setOnClickListener();
		addressModel = new AddressModel(this);
		addressModel.addResponseListener(this);
		adapter = new AddressManagerAdapter(this, addressModel);
		isSelectAddress = getIntent().getBooleanExtra("isSelectAddress", false);
		if (isSelectAddress) {
			title_text.setText("收货地址");
		}
	}

	private void setOnClickListener() { 
		title_back.setOnClickListener(this);
		add_address_button.setOnClickListener(this);
		address_manager_listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (isSelectAddress) {
					intent = new Intent();
					if(addressList.size()<1){
						intent.putExtra("no_address", "no_address");
					}else{
					intent.putExtra("address_id", addressList.get(position).id);
					intent.putExtra("address",addressList.get(position).country_name+addressList.get(position).province_name+addressList.get(position).city_name+addressList.get(position).district_name+addressList.get(position).address);
					intent.putExtra("mobile", addressList.get(position).mobile);
					intent.putExtra("name", addressList.get(position).consignee);
					intent.putExtra("id", addressList.get(position).id);
					setResult(Activity.RESULT_OK, intent);
					finish();
					}
//					addressModel.addressDefault(addressList.get(position).id+"");//设置默认地址
				}else {
				intent = new Intent(AddressManagerActivity.this, UpdateAddressActivity.class);
				intent.putExtra("address_id", addressList.get(position).id + "");
				startActivity(intent);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.add_address_button:
			intent = new Intent(AddressManagerActivity.this, NewAddressActivity.class);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
	}
	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
		if (url.endsWith(ProtocolConst.ADDRESS_LIST)) {
			addressList = addressModel.addressList;
			adapter.bindData(addressList);
			address_manager_listview.setAdapter(adapter);
			adapter.notifyDataSetChanged();	
			if(flag == false){
			if(i == 1){
				addressModel.addressDefault(addressList.get(0).id);
				i++;
			}
			}
			if (addressList!=null&&addressList.size()>0) {
				address_empty.setVisibility(View.GONE);
			}else {
				address_empty.setVisibility(View.VISIBLE);
			}
		} else if (url.endsWith(ProtocolConst.ADDRESS_SETDEFAULT)) {
			Toast.makeText(AddressManagerActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
			addressModel.getAddressList();
		} else if (url.endsWith(ProtocolConst.ADDRESS_DELETE)) {
			Toast.makeText(AddressManagerActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
			i = 1;
			flag = false;
			addressModel.getAddressList();
			
		}

	}

}
