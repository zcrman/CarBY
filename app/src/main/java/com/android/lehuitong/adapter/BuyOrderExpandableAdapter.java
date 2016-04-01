package com.android.lehuitong.adapter;

import java.util.List;

import com.BeeFramework.activity.StartActivity;
import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.activity.BuyOrederTable;
import com.android.lehuitong.activity.TallyOrderActivity;
import com.android.lehuitong.component.BuyOrderChildHolder;
import com.android.lehuitong.component.BuyOrderEndHolder;
import com.android.lehuitong.component.BuyOrderParentHolder;
import com.android.lehuitong.model.OrderModel;
import com.android.lehuitong.pay.alipay.PayDemoActivity;
import com.android.lehuitong.pay.wechat.PayActivity;
import com.android.lehuitong.protocol.GOODORDER;
import com.android.lehuitong.protocol.ORDER_GOODS_LIST;
import com.android.lehuitong.view.AddressDialog;
import com.funmi.lehuitong.R;
import com.funmi.lehuitong.R.layout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class BuyOrderExpandableAdapter extends BaseExpandableListAdapter {
	private Context context;
	private List<GOODORDER> list;
	private AddressDialog dialog;
	private OrderModel orderModel;
	private String name;
	private String subject;
	Intent intent;
	public BuyOrderExpandableAdapter(Context context, OrderModel orderModel) {
		this.context = context;
		this.orderModel = orderModel;
	}

	public void bindData(List<GOODORDER> list) {
		this.list = list;
	}

	@Override
	public int getGroupCount() {

		return list.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {

		return list.get(groupPosition).goods_list.size() + 1;
	}

	@Override
	public Object getGroup(int groupPosition) {

		return list.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {

		return list.get(groupPosition).goods_list.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {

		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {

		return childPosition;
	}

	@Override
	public boolean hasStableIds() {

		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GOODORDER orderList = list.get(groupPosition);
		BuyOrderParentHolder holder = null;
		if (convertView == null) {
			holder = new BuyOrderParentHolder();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(
					R.layout.item_buy_order_parent_header, null);
			holder.orderSn = (TextView) convertView
					.findViewById(R.id.order_nuber);
			holder.orderMoney = (TextView) convertView
					.findViewById(R.id.order_price);
			convertView.setTag(holder);
		} else {
			holder = (BuyOrderParentHolder) convertView.getTag();
		}

		// 加载数据
		subject=orderList.order_info.subject;
		String order_sn = orderList.order_sn;
		String order_amount = orderList.order_info.order_amount;
		holder.setOrderParentInfo(order_sn, order_amount);
		return convertView;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		BuyOrderChildHolder childerHolder;
		BuyOrderEndHolder endHolder;
		if (childPosition < list.get(groupPosition).goods_list.size()) {
			ORDER_GOODS_LIST childList = list.get(groupPosition).goods_list
					.get(childPosition);
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_buy_order_child, null);
			childerHolder = new BuyOrderChildHolder(context);
			childerHolder.buy_count = (TextView) convertView
					.findViewById(R.id.buy_count);
			childerHolder.order_name = (TextView) convertView
					.findViewById(R.id.order_name);
			childerHolder.goodsImg = (WebImageView) convertView
					.findViewById(R.id.oreder_image);
			// 加载数据
			name = childList.name;
			String goods_number = childList.goods_number;
			String goods_imgUrl = childList.img.thumb;
			childerHolder.setChildInfo(name, goods_number, goods_imgUrl);
		} else {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_buy_order_parent_end, null);
			endHolder = new BuyOrderEndHolder(context);
			endHolder.initView(convertView);
			convertView.setClickable(false);
			String status= list.get(groupPosition).order_status;
			String pay_status=list.get(groupPosition).pay_status;
			endHolder.setInfo(status, pay_status);
			endHolder.orderCancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog = new AddressDialog(context) {
						@Override
						public void setCancel() {

							super.setCancel();
							dialog.mDialog.dismiss();
						}
						@Override
						public void setCommit() {
							super.setCommit();
							orderModel.orderCancle(list.get(groupPosition).order_id);
							dialog.mDialog.dismiss();
						}
					};
					dialog.settitle("你确定要取消该订单吗？");
					dialog.mDialog.show();
				}
			});
			endHolder.orderPay.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

//					dialog = new AddressDialog(context) {
//						@Override
//						public void setCancel() {
//
//							super.setCancel();
//							dialog.mDialog.dismiss();
//						}
//
//						@Override
//						public void setCommit() {
//
//							super.setCommit();
//							dialog.mDialog.dismiss();
//							
//						}
//					};
//					dialog.settitle("你确定要购买该订单吗？");
//					dialog.mDialog.show();
					intent = new Intent(
							BuyOrederTable.mactivity,
							TallyOrderActivity.class);
					GOODORDER orderList = list.get(groupPosition);
					Bundle bundle = new Bundle();      
					bundle.putInt("status", 1);
//					bundle.putSerializable("orders", list.get(groupPosition).goods_list);
//					bundle.putSerializable("order_info", list.get(groupPosition).order_info);
					bundle.putString("order_id", list.get(groupPosition).order_id);
					intent.putExtras(bundle);
					BuyOrederTable.mactivity.startActivity(intent);
				}
			});
			endHolder.orderPayAgain.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					intent = new Intent(
							BuyOrederTable.mactivity,
							TallyOrderActivity.class);
					GOODORDER orderList = list.get(groupPosition);
					Bundle bundle = new Bundle();      
					bundle.putInt("status", 1);
					bundle.putSerializable("orders", list.get(groupPosition).goods_list);
					bundle.putSerializable("order_info", list.get(groupPosition).order_info);
					bundle.putString("order_id", list.get(groupPosition).order_id);
					intent.putExtras(bundle);
					BuyOrederTable.mactivity.startActivity(intent);
				}
			});
			endHolder.orderDelete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog = new AddressDialog(context) {
						@Override
						public void setCancel() {

							super.setCancel();
							dialog.mDialog.dismiss();
						}

						@Override
						public void setCommit() {

							super.setCommit();
							orderModel.orderDelect(list.get(groupPosition).order_id);
							dialog.mDialog.dismiss();
						}
					};
					dialog.settitle("你确定要删除该订单吗？");
					dialog.mDialog.show();
				}
			});

		}

		return convertView;
	}
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;

	}

}
