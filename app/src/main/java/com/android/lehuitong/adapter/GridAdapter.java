package com.android.lehuitong.adapter;

import java.util.ArrayList;

import com.BeeFramework.activity.StartActivity;
import com.android.lehuitong.activity.MyEatInlehuiActivity;
import com.android.lehuitong.activity.MyEatSearchActivity;
import com.funmi.lehuitong.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;


/**
 *@author fjw
 *
 *2015年6月12日下午2:22:48
 */
public class GridAdapter extends BaseAdapter{

	private Activity context;
	private String[] arrayList;
	private LayoutInflater minflater;
	public GridAdapter(Activity c,String [] str){
		this.context=c;
		this.arrayList=str;
	}
	@Override
	public int getCount() {
		return arrayList.length;
	}

	@Override
	public Object getItem(int position) {
		return arrayList[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		convertView=minflater.from(context).inflate(R.layout.list_eat_gridview, null);
		final Button btn=(Button)convertView.findViewById(R.id.btn_gridview);
		btn.setText(arrayList[position]);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(context,MyEatInlehuiActivity.class);
				intent.putExtra("key", btn.getText()+"");
				context.setResult(Activity.RESULT_OK, intent);
				context.finish();
			}
		});
		return convertView;
	}

}
