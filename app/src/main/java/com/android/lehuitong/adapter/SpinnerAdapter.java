package com.android.lehuitong.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.lehuitong.protocol.REGIONS;
import com.funmi.lehuitong.R;

public class SpinnerAdapter extends BaseAdapter {

	private Context context;
	private List<REGIONS> list;
	
	private LayoutInflater inflater;
	
	public SpinnerAdapter(Context context, List<REGIONS> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.city_item, null);
			holder.name = (TextView) convertView.findViewById(R.id.city_item_name);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		REGIONS regions = list.get(position);
		holder.name.setText(regions.name);
		
		return convertView;
	}
	
	class ViewHolder {
		private TextView name;
		
	}

}
