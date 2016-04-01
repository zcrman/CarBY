package com.android.lehuitong.adapter;
import com.android.lehuitong.model.KtvAndCouponsOrderModel;
import com.funmi.lehuitong.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
public class MyCouponDetaliAdapter extends BaseAdapter {
	private Context context;
	String[] codelist;
	
	
	private KtvAndCouponsOrderModel orderModel;
	public MyCouponDetaliAdapter(Context context, KtvAndCouponsOrderModel orderModel) {
		this.context = context;
		this.orderModel=orderModel;
		
	}

	public void getdata(String[] codelist) {
		this.codelist = codelist;
	}

	@Override
	public int getCount() {
		@SuppressWarnings("unused")
		int a = codelist.length;
		return codelist.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder(orderModel, position);
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_coupon_code, null);
			holder.code_name = (TextView) convertView
					.findViewById(R.id.code_name);
			holder.coupon_status = (TextView) convertView
					.findViewById(R.id.code_status);
			holder.coupon_number = (TextView) convertView
					.findViewById(R.id.code_number);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.setInfo(codelist[position]);
		return convertView;
	}
	class Holder {
		public TextView coupon_number;
		public TextView coupon_status;
		public TextView code_name;
		private KtvAndCouponsOrderModel orderModel;
		int position;
		int statu;
		public Holder(KtvAndCouponsOrderModel orderModel,int position) {
			this.orderModel=orderModel;
			this.position=position;
		}
		@SuppressWarnings("null")
		public void setInfo(String coupon_number
				) {
			if (position<10) {
				this.code_name.setText("券码0"+position+":");
			}else {
				this.code_name.setText("券码"+position+":");
			}
			this.coupon_number.setText(coupon_number);
			String [] status=orderModel.ktvOrderList.get(0).is_verified_ticket.split(",");
				try {
					statu=Integer.parseInt(status[position]);
				} catch (Exception e) {
					
				}
			if (statu==0) {
				this.coupon_status.setText("未使用");
			}else if (statu==1) {
				this.coupon_status.setText("已使用");
			}else  {
				this.coupon_status.setText("");
			}
			
		}
	}

}
