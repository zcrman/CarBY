package com.android.lehuitong.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.adapter.MyCouponsAdapter;
import com.android.lehuitong.model.BonusModel;
import com.android.lehuitong.model.KtvAndCouponsOrderModel;
import com.android.lehuitong.model.OrderModel;
import com.android.lehuitong.protocol.BONUS;
import com.android.lehuitong.protocol.KTV_ORDER;
import com.android.lehuitong.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MyCouponsActivity extends BaseActivity implements OnClickListener, BusinessResponse {
	private ListView listView;
	private MyCouponsAdapter couponsadapter;
	private ImageView title_back;
	private TextView title_text;
	private Intent intent;
	private LinearLayout coupons_use_btn;
	private BonusModel bonusModel;
	private List<BONUS> bonusList = new ArrayList<BONUS>();
	public static MyCouponsActivity activity;
	private  int status;
	private String bonusName;
	private String bonusId;
	private String bonusPrice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_coupons);
		init();
		activity=this;
	}

	@SuppressWarnings("unchecked")
	private void init() {
		title_back = (ImageView) findViewById(R.id.title_back);
		title_text = (TextView) findViewById(R.id.title_text);
		title_text.setText("我的红包");
		listView = (ListView) findViewById(R.id.my_coupons_listview);
		coupons_use_btn=(LinearLayout) findViewById(R.id.coupons_use_btn);
		bonusModel = new BonusModel(this);
		bonusModel.addResponseListener(this);
		try {
			status= getIntent().getExtras().getInt("status");
		} catch (Exception e) {
		}
		if (status==1) {
			coupons_use_btn.setVisibility(View.VISIBLE);
			couponsadapter = new MyCouponsAdapter(this, true);
			bonusList = (List<BONUS>) getIntent().getExtras().getSerializable("bonus");
			couponsadapter.bindData(bonusList);
			listView.setAdapter(couponsadapter);
		}else {
			couponsadapter = new MyCouponsAdapter(this, false);
			bonusModel.Bonus();
		}
		setOnClickListener();
	}
	private void setOnClickListener() {
		title_back.setOnClickListener(this);
		coupons_use_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.coupons_use_btn:
			Map<Integer, Boolean> useBonus =new HashMap<Integer, Boolean>();
			useBonus=couponsadapter.getIsSelect();
			if (useBonus!=null) {
				for (int i = 0; i < bonusList.size(); i++) {
					if(useBonus.get(i)!=null){
						bonusId=bonusList.get(i).bonus_id;
						bonusName=bonusList.get(i).type_name;
						bonusPrice=bonusList.get(i).type_money;
					}
				}
				intent=new Intent();
				intent.putExtra("bonusPrice",bonusPrice);
				intent.putExtra("bonusId", bonusId);
				intent.putExtra("bonusName", bonusName);
				intent.putExtra("type", false);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
		if (url.endsWith(ProtocolConst.BONUS)) {
			bonusList = bonusModel.bonusArray;
			couponsadapter.bindData(bonusList);
			listView.setAdapter(couponsadapter);
		}
	}

}
