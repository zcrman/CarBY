package com.android.lehuitong.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ListView;

import com.BeeFramework.BeeFrameworkApp;
import com.BeeFramework.fragment.BaseFragment;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.activity.MyEatInlehuiActivity;
import com.android.lehuitong.activity.ShopDetailActivity;
import com.android.lehuitong.adapter.EatAdapter;
import com.android.lehuitong.component.Eatholder;
import com.android.lehuitong.model.SellerModel;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SHOP;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.external.activeandroid.util.Log;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;

public class PopularFramentTab extends BaseFragment implements BusinessResponse{

	LayoutInflater layoutInflater;
	private Context mContext;
	private View view;
	private ListView listview;
	private EatAdapter adapter;
	private int type;
	private SellerModel sellerModel;
	private String detail;
	private LinearLayout layout;
	Position position = new Position();
	public PopularFramentTab(int type,String detail) {
		this.type=type;
		this.detail=detail;
	}
	public PopularFramentTab(){}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LocationClient mLocClient = new LocationClient(getActivity());
		mLocClient.registerLocationListener(position);
		mLocClient.start();
		view = inflater.inflate(R.layout.fragment_eat_list, null);
		listview = (ListView) view.findViewById(R.id.fragment_eat_list_expandableListView);
		layout=(LinearLayout) view.findViewById(R.id.eat_background);
		sellerModel = new SellerModel(getActivity());
		sellerModel.addResponseListener(this);
		sellerModel.getSellerList(type+"","hot",detail,BeeFrameworkApp.getInstance().lng, BeeFrameworkApp.getInstance().lat);
		return view;
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
		if (url.endsWith(ProtocolConst.SELLER_LIST)) {
			if(sellerModel.shopList.size()>0){
				Log.i("ffffffffffffff", ""+sellerModel.shopList.size());
				adapter = new EatAdapter(getActivity(),sellerModel.shopList);
				this.listview.setAdapter(adapter);
			}else{
				listview.setVisibility(View.GONE);
				layout.setVisibility(View.VISIBLE);
			}
		}

	}
	class Position implements BDLocationListener{
		 double lat=0;
		 double lon=0;
		@Override
		public void onReceiveLocation(BDLocation arg0) {
			
			lat = arg0.getLatitude();
			lon = arg0.getLongitude();
		}
	}
}
