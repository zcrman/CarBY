package com.android.lehuitong.fragment;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.fragment.BaseFragment;
import com.funmi.lehuitong.R;

public class EatFragmentTab extends BaseFragment implements OnClickListener{
	private LinearLayout eat_default;
	private LinearLayout eat_popular;
	private LinearLayout eat_distance;
	private TextView eat_default_textview,eat_popular_textview,eat_distance_textview;
//	private ImageView eat_popular_image,eat_distance_image;
	private DefaultFragmentTab defaultFragmentTab;
	private DistanceFragmentTab distanceFragmentTab;
	private PopularFramentTab popularFramentTab;
	private Context myContext;
	private int type;
	public static final int dfault = 0;
	public static final int popular = 1;
	public static final int distance = 2;
	private String detail;
	/**
	 * 
	 * @param context 上下文
	 * @param type  接收的类型，酒店，吃等；
	 * @param detail 从搜索里面传递回来的值，并且传到每一个frament里面去
	 */
	public void setNetworkModel(Context context,int type,String detail){
	this.type=type;
	this.detail=detail;
		initFragment();
	}
	private void initFragment(){
		defaultFragmentTab = new DefaultFragmentTab(type,detail);
		distanceFragmentTab = new DistanceFragmentTab(type,detail);
		popularFramentTab = new PopularFramentTab(type,detail);
		selectTab(dfault);
		
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View tabsView = inflater.inflate(R.layout.fragment_eat_tab, container, false);
		
		initView(tabsView);
		return tabsView;
	}

	private void initView(View tabsView){
		eat_default=(LinearLayout)tabsView.findViewById(R.id.eat_default);
		eat_popular=(LinearLayout)tabsView.findViewById(R.id.eat_popular);
		eat_distance=(LinearLayout)tabsView.findViewById(R.id.eat_distance);
		eat_default_textview=(TextView)tabsView.findViewById(R.id.eat_default_textview);
		eat_popular_textview=(TextView)tabsView.findViewById(R.id.eat_popular_textview);
		eat_distance_textview=(TextView)tabsView.findViewById(R.id.eat_distance_textview);
//		 eat_popular_image=(ImageView)tabsView.findViewById(R.id.eat_popular_image);
//		 eat_distance_image=(ImageView)tabsView.findViewById(R.id.eat_distance_image);
		
		 eat_default.setOnClickListener(this);
		 eat_popular.setOnClickListener(this);
		 eat_distance.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.eat_default:
			selectTab(dfault);
			break;
			
		case R.id.eat_popular:
			selectTab(popular);
			break;
			
		case R.id.eat_distance:
			selectTab(distance);
			break;
		}
	}
	
	private void selectTab(int tab){
		
		switch (tab) {
		case dfault:
			showFragment(dfault);
			break;
		case popular:
			showFragment(popular);
			break;
		case distance:
			showFragment(distance);
			break;
		default:
			break;
		}
	}
	public  final void showFragment(int type){
		FragmentTransaction localFragmentTransaction;
		switch(type){
		case dfault:
			if(defaultFragmentTab == null){
				defaultFragmentTab = new DefaultFragmentTab(type,detail);
				
			}
			localFragmentTransaction = getFragmentManager()
					.beginTransaction();
			localFragmentTransaction.replace(R.id.manage_client_container,
					defaultFragmentTab, "tab_one");
		eat_default_textview.setTextColor(Color.RED);
		eat_popular_textview.setTextColor(Color.BLACK);
		eat_distance_textview.setTextColor(Color.BLACK);
//		eat_popular_image.setBackgroundResource(R.drawable.eat_tab_icon);
//		eat_distance_image.setBackgroundResource(R.drawable.eat_tab_icon);
		localFragmentTransaction.commit();
		break;
		case popular:
			if(popularFramentTab==null){
				popularFramentTab=new PopularFramentTab(type,detail);
			}
			localFragmentTransaction=getFragmentManager().beginTransaction();
			localFragmentTransaction.replace(R.id.manage_client_container,
					popularFramentTab,"tab_two");
			eat_popular_textview.setTextColor(Color.RED);
			eat_default_textview.setTextColor(Color.BLACK);
			eat_distance_textview.setTextColor(Color.BLACK);
//			eat_popular_image.setBackgroundResource(R.drawable.eat_tab_selected_icon);
//			eat_distance_image.setBackgroundResource(R.drawable.eat_tab_icon);
			localFragmentTransaction.commit();
			break;
		case distance:
			if(distanceFragmentTab==null){
				distanceFragmentTab=new DistanceFragmentTab(type,detail);
			}
			localFragmentTransaction=getFragmentManager().beginTransaction();
			localFragmentTransaction.replace(R.id.manage_client_container, distanceFragmentTab,"tab_two");
			eat_distance_textview.setTextColor(Color.RED);
			eat_default_textview.setTextColor(Color.BLACK);
			eat_popular_textview.setTextColor(Color.BLACK);
//			eat_popular_image.setBackgroundResource(R.drawable.eat_tab_icon);
//			eat_distance_image.setBackgroundResource(R.drawable.eat_tab_selected_icon);
			localFragmentTransaction.commit();
			break;
	}
}



}
