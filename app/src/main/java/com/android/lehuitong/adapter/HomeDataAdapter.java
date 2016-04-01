package com.android.lehuitong.adapter;

import com.android.lehuitong.activity.HiActivity;
import com.android.lehuitong.activity.LoginActivity;
import com.android.lehuitong.activity.ShopDetailActivity;
import com.android.lehuitong.component.HomeButtonHolder;
import com.android.lehuitong.component.HomePromoteActivitiesHolder;
import com.android.lehuitong.model.HomeModel;
import com.android.lehuitong.model.HomeTitleTextUtil;
import com.android.lehuitong.model.LvHeightUtil;
import com.android.lehuitong.protocol.PHOTO;
import com.android.lehuitong.protocol.SIMPLEGOODS;
import com.funmi.lehuitong.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class HomeDataAdapter extends BaseAdapter {
	private Context context;
	private HomeModel homeModel;
	private LayoutInflater inflater;
	TextView textView;
	private GridView aa;
	public HomeDataAdapter(Context context, HomeModel homeModel) {
		this.context = context;
		this.homeModel = homeModel;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if (homeModel.promoteActivitiesList == null || homeModel.promoteActivitiesList.size() == 0) {
			return 3;
		} else {

			return homeModel.promoteShopList.size()+6;
		}
	}

	@Override
	public Object getItem(int position) {

		return null;
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		HomeButtonHolder homeButtonHolder = null;
		HomePromoteActivitiesHolder homePromoteActivitiesHolder = null;
		if (position == 0) {
			convertView = inflater.inflate(R.layout.item_home_four_button, null);
			homeButtonHolder = new HomeButtonHolder(context);
			homeButtonHolder.initView(convertView);
			homeButtonHolder.setOnClickListener();
		} else if (position == 1) {
			//几个ktv
			convertView = inflater.inflate(R.layout.item_home_hi_activity, null);
			 aa=(GridView) convertView.findViewById(R.id.home_gridViwe);
			 HomeKtvAdapter adapter=new HomeKtvAdapter(context, homeModel.promoteShopList);
			 aa.setAdapter(adapter);
			aa.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if(homeModel.promoteShopList.get(position).can_order==0){
						Intent intent = new Intent(context,ShopDetailActivity.class);
						intent.putExtra("shop_id", homeModel.promoteShopList.get(position).shop_id);
						context.startActivity(intent);
					}else if(homeModel.promoteShopList.get(position).can_order==1){
										Intent intent = new Intent(context,HiActivity.class);
										//homeActivity为直接从主页进入到ktv里面
										intent.putExtra("type", "homeActivity");
										intent.putExtra("shop_id", homeModel.promoteShopList.get(position).shop_id);
										intent.putExtra("shop_name",homeModel.promoteShopList.get(position).shop_name);
										intent.putExtra("shop_address", homeModel.promoteShopList.get(position).shop_address);
										intent.putExtra("shop_phone", homeModel.promoteShopList.get(position).shop_phone);
										intent.putExtra("type", "home");
										Bundle bundle = new Bundle();
										bundle.putSerializable("goods", homeModel.promoteShopList.get(position).goods);
										intent.putExtra("goodsList", bundle);
//										intent.putExtra("shop_distance", homeModel.promoteShopList.get(position).distance);
										context.startActivity(intent);
					}
					
				}
			});
//			Log.i("222222222222222", ""+homeModel.promoteShopList.size());
			
		} else if (position == 2) {
			//超值推荐
			//			convertView = inflater.inflate(R.layout.item_home_promote_super, null);
			//			homePromoteSuperHolder = new HomePromoteSuperHolder(context);
			//			homePromoteSuperHolder.intiView(convertView);
			//			homePromoteSuperHolder.setInfo(homeModel.promoteSuperList);
			//活动推荐
			
			convertView = inflater.inflate(R.layout.item_home_promote_activities, null);
			
			homePromoteActivitiesHolder = new HomePromoteActivitiesHolder(context);
			homePromoteActivitiesHolder.initView(convertView);
			homePromoteActivitiesHolder.setInfo(homeModel.promoteActivitiesList);
		} else if(position == 3) {
			//美食推荐
			convertView = inflater.inflate(R.layout.home_list_view, null);
			ListView aa = (ListView) convertView.findViewById(R.id.home_list_view);
			if(homeModel.promoteFoodsList.size()==0){
				
			}else{
			addHeadAndFootView(context, HomeTitleTextUtil.FOOD, null, aa);
			HomeListAdapter adapter=new HomeListAdapter(context, homeModel.promoteFoodsList, null,null,HomeTitleTextUtil.FOOD_TYPE);
			aa.setAdapter(adapter);
			LvHeightUtil.setListViewHeightBasedOnChildren(aa);
			}
		}else if(position == 4){
			//购物推荐
			
			convertView = inflater.inflate(R.layout.home_list_view, null);
			
			ListView aa = (ListView) convertView.findViewById(R.id.home_list_view);
			if(homeModel.promoteBuyList.size()==0){
				
			}else{
			addHeadAndFootView(context, HomeTitleTextUtil.SHOP, null, aa);
			HomeListAdapter adapter=new HomeListAdapter(context, homeModel.promoteBuyList,null,null,HomeTitleTextUtil.SHOP_TYPE);
			aa.setAdapter(adapter);
			LvHeightUtil.setListViewHeightBasedOnChildren(aa);
			}
		}else if(position == 5){
			
			convertView = inflater.inflate(R.layout.home_list_view, null);
			
			ListView aa = (ListView) convertView.findViewById(R.id.home_list_view);
			if(homeModel.promoteHotelList.size()==0){
				
			}else{
			addHeadAndFootView(context, HomeTitleTextUtil.HOTEL, null, aa);
			HomeListAdapter adapter=new HomeListAdapter(context, homeModel.promoteHotelList, null,null,HomeTitleTextUtil.FOOD_TYPE);
			aa.setAdapter(adapter);
			LvHeightUtil.setListViewHeightBasedOnChildren(aa);
			}
		}
		else{
			if(position - 6 < homeModel.promoteShopList.size()){
				Log.i("aaaaaaaa", ""+homeModel.promoteShopList.size());
				convertView = inflater.inflate(R.layout.home_list_view, null);
				ListView aa = (ListView) convertView.findViewById(R.id.home_list_view);
				//判断该goods里面有没有活动，没有活动就不执行任何操作，不显示，有就显示出来
				if(homeModel.promoteShopList.get(position-6).goods.size()==0){
				}else{
				addHeadAndFootView(context,homeModel.promoteShopList.get(position-6).shop_name, null, aa);
				HomeListAdapter adapter=new HomeListAdapter(context,null,homeModel.promoteShopList.get(position-6).goods,homeModel.promoteShopList.get(position-6),HomeTitleTextUtil.JINGPINGDIAN_TUIJAIN_TYPE);
				aa.setAdapter(adapter);
				LvHeightUtil.setListViewHeightBasedOnChildren(aa);
				}
			}
		}
		return convertView;
	}

	private void addHeadAndFootView(Context context,String head,String foot,ListView view) {
		TextView textView=new TextView(context);
		textView.setPadding(10, 2, 10, 2);
		textView.setBackgroundColor(Color.rgb(255, 255, 255));
		TextView foot1=new TextView(context);
		foot1.setPadding(10, 0, 10, 0);
		foot1.setBackgroundColor(Color.rgb(244, 244, 244));
		textView.setText(head);
		foot1.setText(foot);
		foot1.setTextSize(5f);
		view.addHeaderView(textView);
		view.addFooterView(foot1);
	}

}
