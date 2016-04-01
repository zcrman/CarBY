package com.android.lehuitong.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.activity.MyPurchaseActivity;
import com.android.lehuitong.adapter.PurchaseAdapter;
import com.android.lehuitong.model.GoodsListModel;
import com.android.lehuitong.model.SellerModel;
import com.android.lehuitong.protocol.FILTER;
import com.android.lehuitong.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.funmi.lehuitong.R;

public class PurchaseFragment extends Fragment implements BusinessResponse, IXListViewListener {
	private XListView listview;
	private PurchaseAdapter adapter;
	private GoodsListModel goodsListModel;
	private String str;

	private FILTER filter;
	private int i = 0;
	private boolean isPullToRefresh = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_shop_list, container, false);
		Bundle bundle = getArguments();
		str = bundle.getString(MyPurchaseActivity.ARGUMENTS_NAME, "");

		listview = (XListView) rootView.findViewById(R.id.shop_list);
		listview.setPullRefreshEnable(true);
		listview.setPullLoadEnable(true);
		listview.setXListViewListener(this, 0);
		listview.setRefreshTime();

		goodsListModel = new GoodsListModel(getActivity());
		goodsListModel.addResponseListener(this);
		filter = new FILTER();
		filter.category_id = str;
		goodsListModel.fetchPreSearch(filter, "",6+"");

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
		if (url.endsWith(ProtocolConst.SEARCH)) {
			adapter = new PurchaseAdapter(getActivity(), goodsListModel.simplegoodsList);
			listview.stopRefresh();
			listview.setRefreshTime();
			listview.stopLoadMore();
			listview.setAdapter(adapter);
			listview.setPullLoadEnable(true);
			if(goodsListModel.simplegoodsList.size()<10){
				listview.setPullLoadEnable(false);
			}
			if (!isPullToRefresh) {
				i++;
				listview.setSelection(10*i);
			if(jo.optJSONObject("paginated").optInt("more")==0){
				listview.setPullLoadEnable(false);
				Toast.makeText(getActivity(), "已加载全部", Toast.LENGTH_SHORT).show();
			}
			}
			
		}

	}

	@Override
	public void onRefresh(int id) {
		filter = new FILTER();
		filter.category_id = str;
		goodsListModel.fetchPreSearch(filter, "",10+"");
		isPullToRefresh = true;
		i=0;
		
	}

	@Override
	public void onLoadMore(int id) {
		filter = new FILTER();
		filter.category_id = str;
		goodsListModel.fetchPreSearchMore(filter);
		isPullToRefresh = false;
		
	}
}
