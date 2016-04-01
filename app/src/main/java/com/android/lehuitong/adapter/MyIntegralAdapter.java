package com.android.lehuitong.adapter;

import java.util.List;

import com.android.lehuitong.protocol.ARTICLE;
import com.funmi.lehuitong.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyIntegralAdapter extends BaseAdapter {
	private Context context;
	private List<ARTICLE> list;

	public MyIntegralAdapter(Context context) {
		this.context = context;
	}

	public void gedata(List<ARTICLE> list) {
		this.list = list;

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
		ViewHolder holder = null;
		ARTICLE itemlist = list.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_intergral, null);
			holder.title_integral = (TextView) convertView
					.findViewById(R.id.title_integral);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}
		holder.title_integral.setText(itemlist.title);
		return convertView;
	}

  class ViewHolder {
		TextView title_integral;
	}

}
