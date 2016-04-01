package com.android.lehuitong.adapter;

import java.util.List;
import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.activity.KtvOrderDetailActivity;
import com.android.lehuitong.activity.ReserveOrederTable;
import com.android.lehuitong.component.MyCouponsHolder;
import com.android.lehuitong.component.ReserveOrederHolder;
import com.android.lehuitong.model.KtvAndCouponsOrderModel;
import com.android.lehuitong.model.OrderModel;
import com.android.lehuitong.protocol.GOODORDER;
import com.android.lehuitong.protocol.GOODS_LIST;
import com.android.lehuitong.protocol.KTV_ORDER;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.view.AddressDialog;
import com.funmi.lehuitong.R;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


@SuppressWarnings("unused")
public class ReserveOrederAdapter extends BaseAdapter {
	private List<KTV_ORDER> list;
	private Context context;
	private AddressDialog dialog;
	private KtvAndCouponsOrderModel orderModel;
	public ReserveOrederAdapter(Context context, KtvAndCouponsOrderModel orderModel) {
		this.context = context;
		this.orderModel = orderModel;
	}

	public void bindData(List<KTV_ORDER> list) {
		this.list = list;
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
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ReserveOrederHolder holder = null;
		final KTV_ORDER orderlist = list.get(position);
		if (convertView == null) {
			holder = new ReserveOrederHolder(context);
			final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_reservet_order, null);
			holder.reserve_image = (WebImageView) convertView.findViewById(R.id.reserve_image);
			holder.reserve_nuber = (TextView) convertView.findViewById(R.id.reserve_nuber);
			holder.ktv_name = (TextView) convertView.findViewById(R.id.ktv_name);
			holder.box_type = (TextView) convertView.findViewById(R.id.box_type);
			holder.reserve_time = (TextView) convertView.findViewById(R.id.reserve_time);
			holder.cancel_reserve = (TextView) convertView.findViewById(R.id.cancel_reserve);
			holder.reserve_delete = (TextView) convertView.findViewById(R.id.reserve_delete);
			holder.order_status = (TextView) convertView.findViewById(R.id.reservet_state);
			holder.reserve_pay = (TextView) convertView.findViewById(R.id.reserve_pay);
			holder.reserve_price = (TextView) convertView.findViewById(R.id.resever_price);
			convertView.setTag(holder);
		} else {
			holder = (ReserveOrederHolder) convertView.getTag();
		}
		/** 取消预定 */
		holder.cancel_reserve.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog = new AddressDialog(context) {
					@Override
					public void setCancel() {
						super.setCancel();
						dialog.mDialog.dismiss();
					}
					@Override
					public void setCommit() {
						super.setCommit();
						orderModel.orderCancle(orderlist.order_id);
						dialog.mDialog.dismiss();
					}
				};
				dialog.settitle("您确认要取消该订单吗？");
				dialog.mDialog.show();

			}
		});
		/** 删除预定 */
		holder.reserve_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog = new AddressDialog(context) {
					@Override
					public void setCancel() {

						super.setCancel();
						dialog.mDialog.dismiss();
					}

					@Override
					public void setCommit() {

						super.setCommit();
						orderModel.deleteOrder(Integer.parseInt(orderlist.order_id));
						dialog.mDialog.dismiss();
					}
				};
				dialog.settitle("你确定要删除该订单吗？");
				dialog.mDialog.show();
			}
		});
		holder.reserve_pay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(ReserveOrederTable.mactivity, KtvOrderDetailActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("order_id", list.get(position).order_id);
				bundle.putInt("status", 1);
				bundle.putSerializable("order", orderlist);
				intent.putExtras(bundle);
				ReserveOrederTable.mactivity.startActivity(intent);
			}
		});
		String name = "";
		String number = "";
		String goods_img ="";
		String status;
		String price="";
		String time="";
		 int cat_id = orderlist.cat_id;
		 String reserve_time=orderlist.appointment_time;
		 if (reserve_time.length()>0) {
			
			 String[] date=reserve_time.split("/");
			 String year = null;
			 String month = null;
			 String mdate = null;
			 for (int i = 0; i < date.length; i++) {
				 year= date[0];
				 month= date[1];
				 mdate= date[2];
			 }
			 String date2=mdate.substring(0, 2);
			  time= year+"-"+month+"-"+date2;
		}
		if (orderlist.good != null) {
			name = orderlist.good.goods_name;
			goods_img = orderlist.good.goods_thumb;
		}else if(orderlist.seller != null){
			name = orderlist.seller.shop_name;
			goods_img = orderlist.seller.shop_pic_url;
		}
		number = orderlist.order_sn;
		price =orderlist.money;
		if (orderlist.order_status != null) {
			status = orderlist.order_status;
		} else {
			status = "0";
		}
//		if (!goods_img.isEmpty()) {
//			goods_img=orderlist.good.goods_thumb;
//		}
		holder.SetMyCouponsInfo(number, name, cat_id, time, goods_img, status, price);
		return convertView;
	}

}
