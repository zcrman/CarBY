package com.android.lehuitong.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.adapter.ReserveOrederAdapter;
import com.android.lehuitong.model.KtvAndCouponsOrderModel;
import com.android.lehuitong.protocol.KTV_ORDER;
import com.android.lehuitong.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.funmi.lehuitong.R;

public class ReserveOrederTable extends BaseActivity implements
BusinessResponse,IXListViewListener {
	private XListView listView;
	private ReserveOrederAdapter reserveOrederAdapter;
	private KtvAndCouponsOrderModel orderModel;
	private List<KTV_ORDER> orderList = new ArrayList<KTV_ORDER>();
	public static  ReserveOrederTable mactivity;
	private int i = 0;
	private boolean isPullToRefresh = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reserve_order);
		init();
		mactivity=this;
	}
	private void init() {
		listView = (XListView) findViewById(R.id.reserve_order_list);
		listView.setPullRefreshEnable(true);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this, 0);
		listView.setRefreshTime();
		orderModel = new KtvAndCouponsOrderModel(this);
		orderModel.addResponseListener(this);
		orderModel.getOrderList(2,false);
		reserveOrederAdapter = new ReserveOrederAdapter(this, orderModel);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(ReserveOrederTable.this,
						MyCouponDetaliActivity.class);
				intent.putExtra("type", "ktv");
				if(orderList.get(arg2-1).order_status.equals("8")||
						orderList.get(arg2-1).order_status.equals("5")){
					intent.putExtra("shop_name", orderList.get(arg2-1).seller.shop_name);
					intent.putExtra("shop_address", orderList.get(arg2-1).seller.shop_address);
					intent.putExtra("shop_number", orderList.get(arg2-1).seller.shop_number);
					intent.putExtra("order_status", orderList.get(arg2-1).order_status);
					intent.putExtra("order_sn", orderList.get(arg2-1).order_sn);
					intent.putExtra("order_id", orderList.get(arg2-1).order_id);
				}else if(orderList.get(arg2-1).order_status.equals("1")||
						orderList.get(arg2-1).order_status.equals("2")||
						orderList.get(arg2-1).order_status.equals("3")||
						orderList.get(arg2-1).order_status.equals("4")||
						orderList.get(arg2-1).order_status.equals("6")||
						orderList.get(arg2-1).order_status.equals("7")||
						orderList.get(arg2-1).order_status.equals("9")){
					intent.putExtra("order_status", orderList.get(arg2-1).order_status);
					intent.putExtra("order_sn", orderList.get(arg2-1).order_sn);
					intent.putExtra("order_id", orderList.get(arg2-1).order_id);
				}
				startActivity(intent);
			}
		});
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.ORDER_TICKET_LIST)) {
			listView.stopRefresh();
			listView.setRefreshTime();
			listView.stopLoadMore();
			listView.setPullLoadEnable(true);
			orderList = orderModel.ktvOrderList;
			reserveOrederAdapter.bindData(orderModel.ktvOrderList);
			listView.setAdapter(reserveOrederAdapter);
			if(orderList.size()<10){
				listView.setPullLoadEnable(false);
			}
			if (!isPullToRefresh) {
				i++;
				listView.setSelection(10*i);
			if(jo.optJSONObject("paginated").optInt("more")==0){
				listView.setPullLoadEnable(false);
				Toast.makeText(this, "已加载全部", Toast.LENGTH_SHORT).show();
			}
			}
		} else if (url.endsWith(ProtocolConst.ORDER_TICKET_CANCEL)) {
			Toast.makeText(this, "取消成功", Toast.LENGTH_SHORT).show();
			orderModel.getOrderList(2,false);
		}else if (url.endsWith(ProtocolConst.ORDER_TICKET_DELETE)) {
			Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
			orderModel.getOrderList(2,false);
		}

	}
	@Override
	public void onRefresh(int id) {
		orderModel.getOrderList(2,false);
		isPullToRefresh = true;
		i=0;
		
	}
	@Override
	public void onLoadMore(int id) {
		isPullToRefresh = false;
		orderModel.getOrderList(2,true);
		
	}

}
