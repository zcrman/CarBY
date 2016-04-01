package com.android.lehuitong.adapter;

import java.util.ArrayList;
import java.util.List;

import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.activity.HiActivity;
import com.android.lehuitong.activity.MyPurchaseDetailActivity;
import com.android.lehuitong.activity.ShopDetailActivity;
import com.android.lehuitong.model.ImageLoaderUtils;
import com.android.lehuitong.protocol.GOODS;
import com.android.lehuitong.protocol.SHOP;
import com.funmi.lehuitong.R;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SearchViewCompat.OnCloseListenerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeKtvAdapter extends BaseAdapter implements OnClickListener{
	private List<SHOP>list;
	private Context context;
	private View view;
	private int can_order;
	private String shop_id;
	private String shop_name;
	private String shop_address;
	private String shop_phone;
	private String distance;

	private SHOP shop;
	ImageView image;
	TextView name;
	LinearLayout click;
	View view2;
	public HomeKtvAdapter(Context context,List<SHOP>list){
		this.list=list;
		this.context=context;
	}
	
	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@SuppressWarnings("static-access")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			view=convertView.inflate(context, R.layout.home_item_ktv, null);
			holder = new ViewHolder();
			holder.initView(view);
			view.setTag(holder);
		}else{
			holder=(ViewHolder) view.getTag();
		}
		shop=list.get(position);
		can_order=shop.can_order;
		shop_id=shop.shop_id;
		shop_name=shop.shop_name;
		shop_address=shop.shop_address;
		shop_phone=shop.shop_phone;
		distance=shop.shop_distance;
		
		ImageLoaderUtils.displayNetworkImage(context, shop.shop_logo, ImageLoaderUtils.IMAGE_PATH, holder.image);
		holder.name.setText(list.get(position).shop_brief_name);
		holder.click.setOnClickListener(this);
		return view;
	}
	class ViewHolder{
		ImageView image;
		TextView name;
		RelativeLayout click;

		public void initView(View view){
			click=(RelativeLayout) view.findViewById(R.id.home_ktv_click);
			image=(WebImageView) view.findViewById(R.id.home_ktv_view);
			name=(TextView) view.findViewById(R.id.home_ktv_name);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_ktv_click:
			if(can_order==0){
				Intent intent = new Intent(context,ShopDetailActivity.class);
				intent.putExtra("shop_id", shop_id);
				context.startActivity(intent);
			}else if(can_order==1){
								Intent intent = new Intent(context,HiActivity.class);
								intent.putExtra("shop_id", shop_id);
								intent.putExtra("shop_name",shop_name);
								intent.putExtra("shop_address", shop_address);
								intent.putExtra("shop_phone", shop_phone);
								intent.putExtra("shop_distance", distance);
								context.startActivity(intent);
			}
			break;
		default:
			break;
		}
		

	}

}
