package com.android.lehuitong.adapter;

import java.util.List;

import org.w3c.dom.Text;

import com.android.lehuitong.activity.HiActivity;
import com.android.lehuitong.activity.LehuitongMainActivity;
import com.android.lehuitong.activity.ShopDetailActivity;
import com.android.lehuitong.model.ImageLoaderUtils;
import com.android.lehuitong.protocol.SHOP;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class KtvAdapter implements OnClickListener{
	Context context;
	List<SHOP>list;
	int i;
public KtvAdapter(Context context,List<SHOP>list,int i) {
		this.context=context;
		this.list=list;
		this.i=i;
		
	}

public View getLayOut(final Context context,final List<SHOP>list,int i){
	LinearLayout layout=new LinearLayout(context);
	ImageView imageView=new ImageView(context);
	imageView.setPadding(5, 5, 5, 5);
	TextView textView=new TextView(context);
	textView.setPadding(5, 0, 5, 5);
	ImageLoaderUtils.displayNetworkImage(context, list.get(i).shop_logo, ImageLoaderUtils.IMAGE_PATH, imageView);
	textView.setText(list.get(i).shop_name);
	layout.addView(imageView);
	layout.addView(textView);
	layout.setOnClickListener(this);
	return layout;
}

@Override
public void onClick(View v) {
	if(list.get(i).can_order==0){
		Intent intent = new Intent(context,ShopDetailActivity.class);
		intent.putExtra("shop_id", list.get(i).shop_id);
		context.startActivity(intent);
	}else if(list.get(i).can_order==1){
		Intent intent = new Intent(context,HiActivity.class);
		intent.putExtra("shop_id", list.get(i).shop_id);
		intent.putExtra("shop_name",list.get(i).shop_name);
		intent.putExtra("shop_address", list.get(i).shop_address);
		intent.putExtra("shop_phone", list.get(i).shop_phone);
//		intent.putExtra("shop_distance", list.get(i).distance);
		context.startActivity(intent);
	}
	
}
}
