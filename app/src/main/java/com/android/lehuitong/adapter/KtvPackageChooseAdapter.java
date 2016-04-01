package com.android.lehuitong.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.lehuitong.component.KtvPackageChooseHolder;
import com.android.lehuitong.protocol.GOODS;
import com.android.lehuitong.protocol.SIMPLEGOODS;
import com.funmi.lehuitong.R;

public class KtvPackageChooseAdapter extends BaseAdapter {

	private Context mContext;
	private int selectItem = -1;
	private List<GOODS> goodList=new ArrayList<GOODS>();;
	private int currentItem = -2;
	private List<SIMPLEGOODS> mlist=new ArrayList<SIMPLEGOODS>();
	private String title;
	private String price;
	private String brief;
	private int can_buy;;
	private String goods_id;
	private int position;
	public KtvPackageChooseAdapter(Context mContext) {
		this.mContext = mContext;

	}

	public void bindData(List<GOODS> goodList,String goods_id,int position) {
//		this.goodList = goodsList;
		this.goodList = goodList;
		this.goods_id = goods_id;
		this.position = position;
		}
	

	@Override
	public int getCount() {
		Log.i("good", ""+mlist.size());
//		if(mlist!=null){
			return goodList.size();
//		}else{
//		return goodList.size();
//		}
	}

	@Override
	public Object getItem(int position) {
//		if(mlist!=null){
			return goodList.get(position);
//		}else{
//		return goodList.get(position);
//		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		KtvPackageChooseHolder holder = null;
		if (convertView == null) {
			holder = new KtvPackageChooseHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ktv_package, null);
			holder.packLayout = (LinearLayout) convertView.findViewById(R.id.ktv_package_layout);
			holder.packSelectLayout = (RelativeLayout) convertView.findViewById(R.id.ktv_package_selected_layout);
			holder.packageHeader = (TextView) convertView.findViewById(R.id.ktv_package_header);
			holder.packagePrice = (TextView) convertView.findViewById(R.id.package_price);
			holder.goodsBrief = (TextView) convertView.findViewById(R.id.goods_brief);

			convertView.setTag(holder);

		} else {
			holder = (KtvPackageChooseHolder) convertView.getTag();
		}
		
			if (position == selectItem) {
				holder.packSelectLayout.setVisibility(View.VISIBLE);
			} 
			if (position == currentItem) {
				holder.packSelectLayout.setVisibility(View.GONE);
			}
		

			GOODS list = goodList.get(position);
			title = list.name;
			price = list.shop_price;
			brief = list.goods_brief;

			holder.setPackageInfo(title, price, brief);

		return convertView;
	}

	public void setSelectedItem(int position) {
		if (currentItem ==position) {
			currentItem =-1;
		}
		selectItem = position;
		notifyDataSetChanged();
	}

	public void setDoubleSelectedItem(int position) {
		currentItem = position;
		
		notifyDataSetChanged();
	}

}
