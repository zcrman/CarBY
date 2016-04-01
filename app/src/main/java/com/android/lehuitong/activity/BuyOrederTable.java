package com.android.lehuitong.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.adapter.BuyOrderExpandableAdapter;
import com.android.lehuitong.model.OrderModel;
import com.android.lehuitong.protocol.GOODORDER;
import com.android.lehuitong.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;

public class BuyOrederTable extends BaseActivity implements BusinessResponse {
	private ExpandableListView listView;
	private BuyOrderExpandableAdapter buyOrederAdapter;
	private OrderModel orderModel;
	private List<GOODORDER> orderList = new ArrayList<GOODORDER>();
	public static BuyOrederTable mactivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buy_order);
		init();
		mactivity=this;
	}

	private void init() {
		listView = (ExpandableListView) findViewById(R.id.buy_order_list);
		orderModel = new OrderModel(this);
		orderModel.addResponseListener(this);
		orderModel.getOrder("",0);
		buyOrederAdapter = new BuyOrderExpandableAdapter(this, orderModel);

		listView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				return true;
			}
		});

		listView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				if (orderList.get(groupPosition).goods_list.size() == childPosition) {
					return true;
				} else {
					Intent intent = new Intent(BuyOrederTable.this, BuyOrderDetailActivity.class);
//					intent.putExtra("id", Integer.valueOf(orderList.get(groupPosition).goods_list.get(childPosition).goods_id).intValue());
					intent.putExtra("order_id", orderList.get(groupPosition).order_id);
					startActivity(intent);
					return false;
				}
			}
		});

	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {

		if (url.endsWith(ProtocolConst.ORDER_LIST)) {
			orderList = orderModel.order_list;
			buyOrederAdapter.bindData(orderModel.order_list);
			listView.setAdapter(buyOrederAdapter);
			for (int i = 0; i < listView.getCount(); i++) {
				listView.expandGroup(i);
			}
		}else if (url.endsWith(ProtocolConst.ORDER_CANCEL)) {
			Toast.makeText(this, "取消成功", Toast.LENGTH_SHORT).show();
			orderModel.getOrder("",0);
		}else if (url.endsWith(ProtocolConst.ORDER_DELETE)) {
			Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
			orderModel.getOrder("",0);
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		orderModel.getOrder("",0);
	}

}
