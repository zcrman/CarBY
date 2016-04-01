package com.android.lehuitong.adapter;

import java.util.List;

import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.activity.FindPasswordActivity;
import com.android.lehuitong.component.BuyOrederHolder;
import com.android.lehuitong.component.MyCouponsHolder;
import com.android.lehuitong.component.ReserveOrederHolder;
import com.android.lehuitong.protocol.GOODORDER;
import com.android.lehuitong.view.AddressDialog;
import com.funmi.lehuitong.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BuyOrederAdapter extends BaseAdapter {
	private List<GOODORDER> list;
	private Context context;
	private AddressDialog dialog;
	public BuyOrederAdapter(Context context,List<GOODORDER> list) {
		this.context = context;
		this.list=list;
	}
	

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		GOODORDER orderlist= list.get(position);
		BuyOrederHolder holder=null;
		if (convertView == null) {
			holder =new BuyOrederHolder();
			final LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_buy_order, null);
			holder.oreder_image=(WebImageView) convertView.findViewById(R.id.oreder_image);
			holder.order_number=(TextView) convertView.findViewById(R.id.order_nuber);
			holder.order_price=(TextView) convertView.findViewById(R.id.order_price);
			holder.order_name=(TextView) convertView.findViewById(R.id.order_name);
			holder.buy_count=(TextView) convertView.findViewById(R.id.buy_count);
			holder.order_cancel=(TextView) convertView.findViewById(R.id.order_cancel);
			holder.order_pay=(TextView) convertView.findViewById(R.id.order_pay);
			holder.order_delete=(TextView) convertView.findViewById(R.id.order_delete);
			holder.buy_agin=(TextView) convertView.findViewById(R.id.buy_agin);
			holder.order_state=(TextView) convertView.findViewById(R.id.order_state);
			convertView.setTag(holder);
		} 
			else {
			holder = (BuyOrederHolder) convertView.getTag();
		}
		holder.order_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog=new AddressDialog(context){
					@Override
					public void setCancel() {
						
						super.setCancel();
						dialog.mDialog.dismiss();
					}
					@Override
					public void setCommit() {
						
						super.setCommit();
						dialog.mDialog.dismiss();
					}
				};
				dialog.settitle("你确定要取消该订单吗？");
				dialog.mDialog.show();
			}
		});
		holder.order_pay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog=new AddressDialog(context){
					@Override
					public void setCancel() {
						
						super.setCancel();
						dialog.mDialog.dismiss();
					}
					@Override
					public void setCommit() {
						
						super.setCommit();
						dialog.mDialog.dismiss();
					}
				};
				dialog.settitle("你确定要购买该订单吗？");
				dialog.mDialog.show();
			}
		});
		holder.buy_agin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog=new AddressDialog(context){
					@Override
					public void setCancel() {
						
						super.setCancel();
						dialog.mDialog.dismiss();
					}
					@Override
					public void setCommit() {
						
						super.setCommit();
						dialog.mDialog.dismiss();
					}
				};
				dialog.settitle("你确定要再次购买该订单吗？");
				dialog.mDialog.show();
			}
		});
		holder.order_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog=new AddressDialog(context){
					@Override
					public void setCancel() {
						
						super.setCancel();
						dialog.mDialog.dismiss();
					}
					@Override
					public void setCommit() {
						
						super.setCommit();
						dialog.mDialog.dismiss();
					}
				};
				dialog.settitle("你确定要删除该订单吗？");
				dialog.mDialog.show();
			}
		});
		String buy_count=orderlist.goods_list.get(1).goods_number;
		String order_number=orderlist.order_sn;
		String order_price=orderlist.goods_list.get(1).formated_shop_price;
		String order_name= orderlist.goods_list.get(0).name;
		holder.SetMyCouponsInfo(order_number, order_price, order_name, buy_count, null, null, null);
		return convertView;
	}


}
