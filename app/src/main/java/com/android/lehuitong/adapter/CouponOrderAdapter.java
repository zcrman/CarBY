package com.android.lehuitong.adapter;
import java.util.List;

import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.activity.BuyCouponActivity;
import com.android.lehuitong.activity.CouponOrderActivity;
import com.android.lehuitong.component.CouponOrderHolder;
import com.android.lehuitong.model.KtvAndCouponsOrderModel;
import com.android.lehuitong.protocol.KTV_ORDER;
import com.android.lehuitong.view.AddressDialog;
import com.funmi.lehuitong.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
public class CouponOrderAdapter extends BaseAdapter {
	private List<KTV_ORDER> list;
	private Context context;
	private AddressDialog dialog;
	private KtvAndCouponsOrderModel orderModel;
	public CouponOrderAdapter(Context context,KtvAndCouponsOrderModel orderModel) {
		this.context = context;
		this.orderModel=orderModel;
	}

	public void getdata(List<KTV_ORDER> list) {
		this.list = list;
	}
	@Override
	public int getCount() {

		return list.size();
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
	public View getView(int position, View contView, ViewGroup parent) {
		CouponOrderHolder holder;
		final KTV_ORDER orderlist = list.get(position);
		if (contView == null) {
			holder = new CouponOrderHolder(context);
			contView = LayoutInflater.from(context).inflate(
					R.layout.item_coupon_order, null);
			holder.coupon_sn = (TextView) contView.findViewById(R.id.coupon_sn);
			holder.coupon_price = (TextView) contView
					.findViewById(R.id.coupon_price);
			holder.coupon_name = (TextView) contView
					.findViewById(R.id.coupon_name);
			holder.coupon_count = (TextView) contView
					.findViewById(R.id.coupon_count);
			holder.coupon_state = (TextView) contView
					.findViewById(R.id.coupon_state);
			holder.coupon_image = (WebImageView) contView
					.findViewById(R.id.coupon_image);
			holder.coupon_pay = (TextView) contView
					.findViewById(R.id.coupon_pay);
			holder.coupon_cancel = (TextView) contView
					.findViewById(R.id.coupon_cancel);
			holder.buy_agin = (TextView) contView.findViewById(R.id.buy_agin);
			holder.coupon_delete = (TextView) contView
					.findViewById(R.id.coupon_delete);

			contView.setTag(holder);
		} else {
			holder = (CouponOrderHolder) contView.getTag();
		}
		/** 取消预定 */
		holder.coupon_cancel.setOnClickListener(new OnClickListener() {
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
						@SuppressWarnings("unused")
						int i=Integer.parseInt(orderlist.order_id);
						 orderModel.orderCancle(orderlist.order_id);
						dialog.mDialog.dismiss();
					}
				};
				dialog.settitle("您确认要取消该订单吗？");
				dialog.mDialog.show();
			}
		});
		/** 删除预定 */
		holder.coupon_delete.setOnClickListener(new OnClickListener() {
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
		/**支付*/
		holder.coupon_pay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(CouponOrderActivity.activity, BuyCouponActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("order_sn", orderlist.order_sn);
				bundle.putString("price", orderlist.money);
				bundle.putString("coupon_name", orderlist.good.goods_name);
				bundle.putString("coupon_count", orderlist.good_num);
				bundle.putString("coupon_image", orderlist.good.goods_thumb);
				bundle.putString("coupon_image", orderlist.good.goods_thumb);
				bundle.putString("coupon_brief", orderlist.good.goods_brief);
				bundle.putString("order_type", orderlist.order_type);
				bundle.putString("shop_price", orderlist.good.goods_shop_price);
				bundle.putString("order_id", orderlist.order_id);
				intent.putExtras(bundle);
				CouponOrderActivity.activity.startActivity(intent);
			}
		});
		/**再次购买*/
		holder.buy_agin.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent=new Intent(CouponOrderActivity.activity, BuyCouponActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("order_sn", orderlist.order_sn);
				bundle.putString("price", orderlist.money);
				bundle.putString("coupon_name", orderlist.good.goods_name);
				bundle.putString("coupon_count", orderlist.good_num);
				bundle.putString("coupon_image", orderlist.good.goods_thumb);
				bundle.putString("coupon_image", orderlist.good.goods_thumb);
				bundle.putString("coupon_brief", orderlist.good.goods_brief);
				try{bundle.putString("order_type", orderlist.order_type);
				String aa = orderlist.order_id;
				bundle.putString("order_id", orderlist.order_id);
				}catch(Exception e){
					e.printStackTrace();
				}
				intent.putExtras(bundle);
				CouponOrderActivity.activity.startActivity(intent);
			}
		});
		
		String coupon_sn;
		String coupon_price;
		String coupon_name = null;
		String coupon_count = null;
		String coupon_state;
		String coupon_image = null;
		
		if (orderlist.good!=null) {
			 coupon_name=orderlist.good.goods_name;
			 coupon_image=orderlist.good.goods_thumb;
		}
		if (orderlist.order_status!=null) {
			coupon_state=orderlist.order_status;
		}else {
			coupon_state="0";
		}
		coupon_sn=orderlist.order_sn;
		coupon_price=orderlist.money;
		coupon_count=orderlist.good_num;
		holder.setInfo(coupon_sn, coupon_price, coupon_name, coupon_count, coupon_state, coupon_image);
		return contView;
	}

}
