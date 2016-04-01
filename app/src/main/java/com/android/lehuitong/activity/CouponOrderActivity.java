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
import com.android.lehuitong.adapter.CouponOrderAdapter;
import com.android.lehuitong.model.KtvAndCouponsOrderModel;
import com.android.lehuitong.protocol.FILTER;
import com.android.lehuitong.protocol.KTV_ORDER;
import com.android.lehuitong.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.funmi.lehuitong.R;
/**
 * 优惠券订单页面
 * **/
public class CouponOrderActivity extends BaseActivity implements BusinessResponse,IXListViewListener{
	private XListView listView;
	private CouponOrderAdapter adapter;
	private KtvAndCouponsOrderModel model;
	private int i = 0;
	private boolean isPullToRefresh = true;
	private  List<KTV_ORDER> ktvOrderList = new ArrayList<KTV_ORDER>();
	private FILTER filter;
	private Boolean type = true;
	public static CouponOrderActivity activity;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupon_order);
		init();
		activity=this;
	}
	private void init() {
		listView=(XListView) findViewById(R.id.coupon_order_list);
		listView.setPullRefreshEnable(true);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this, 0);
		listView.setRefreshTime();
		model=new KtvAndCouponsOrderModel(this);
		
		model.getOrderList(1,true);
		model.addResponseListener(this);
		adapter=new CouponOrderAdapter(this,model);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.i("1111111111111", arg2+"");
				Intent intent = new Intent(CouponOrderActivity.this,
						MyCouponDetaliActivity.class);
				intent.putExtra("order_id", ktvOrderList.get(arg2-1).order_id);
				intent.putExtra("type", "youhuijuan");
				startActivity(intent);
				
			}
		});
	}
	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.ORDER_TICKET_LIST)) {
			ktvOrderList=model.ktvOrderList;
			listView.stopRefresh();
			listView.setRefreshTime();
			listView.stopLoadMore();
			adapter.getdata(ktvOrderList);
			listView.setAdapter(adapter);
			listView.setPullLoadEnable(true);
			if(ktvOrderList.size()<10){
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
			model.getOrderList(1,false);
			Toast.makeText(this, "取消成功", Toast.LENGTH_SHORT).show();
		}else if (url.endsWith(ProtocolConst.ORDER_TICKET_DELETE)) {
			model.getOrderList(1,false);
			Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	public void onRefresh(int id) {
		model.getOrderList(1,false);
		isPullToRefresh = true;
		i=0;
		
	}
	@Override
	public void onLoadMore(int id) {
		isPullToRefresh = false;
		model.getOrderList(1,true);
		
	}
}
