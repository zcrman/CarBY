package com.android.lehuitong.component;

import com.funmi.lehuitong.R;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class BuyOrderEndHolder {

	public TextView orderStatus;
	public TextView orderPay;
	public TextView orderCancel;
	public TextView orderDelete;
	public TextView orderPayAgain;
	public Context context;
	public BuyOrderEndHolder(Context context) {
		this.context = context;
	}

	public void initView(View view) {
		orderStatus = (TextView) view.findViewById(R.id.order_state);
		orderPay = (TextView) view.findViewById(R.id.order_pay);
		orderCancel = (TextView) view.findViewById(R.id.order_cancel);
		orderDelete = (TextView) view.findViewById(R.id.order_delete);
		orderPayAgain = (TextView) view.findViewById(R.id.buy_agin);
	}
	public void setInfo(String order_status,String pay_status){
		
		switch (Integer.parseInt(order_status)) {
		case 1:
		orderStatus.setText("未付款");
		orderDelete.setVisibility(View.GONE);
		orderCancel.setVisibility(View.VISIBLE);
		orderPay.setVisibility(View.VISIBLE);
		orderPayAgain.setVisibility(View.GONE);
			break;
		case 2:
			orderStatus.setText("已付款");
			orderDelete.setVisibility(View.GONE);
			orderCancel.setVisibility(View.GONE);
			orderPay.setVisibility(View.GONE);
			orderPayAgain.setVisibility(View.GONE);
			break;
		case 3:
			orderStatus.setText("已发货");
			orderDelete.setVisibility(View.GONE);
			orderCancel.setVisibility(View.GONE);
			orderPay.setVisibility(View.GONE);
			orderPayAgain.setVisibility(View.GONE);
			break;
		case 4:
			orderStatus.setText("交易完成");
			orderDelete.setVisibility(View.VISIBLE);
			orderCancel.setVisibility(View.GONE);
			orderPay.setVisibility(View.GONE);
			orderPayAgain.setVisibility(View.GONE);
			break;
		case 5:
			orderStatus.setText("预定已确定");
			orderDelete.setVisibility(View.GONE);
			orderCancel.setVisibility(View.GONE);
			orderPay.setVisibility(View.GONE);
			orderPayAgain.setVisibility(View.GONE);
			break;
		case 7:
			orderStatus.setText("交易取消");
			orderDelete.setVisibility(View.VISIBLE);
			orderCancel.setVisibility(View.GONE);
			orderPay.setVisibility(View.GONE);
			orderPayAgain.setVisibility(View.GONE);
			break;
		case 8:
			orderStatus.setText("预定待确认");
			orderDelete.setVisibility(View.GONE);
			orderCancel.setVisibility(View.GONE);
			orderPay.setVisibility(View.GONE);
			orderPayAgain.setVisibility(View.GONE);
			break;
		case 9:
			orderStatus.setText("部分完成");
			orderDelete.setVisibility(View.GONE);
			orderCancel.setVisibility(View.GONE);
			orderPay.setVisibility(View.GONE);
			orderPayAgain.setVisibility(View.GONE);
			break;
		default:
			orderStatus.setText("");
			break;
		}
//		switch (Integer.parseInt(pay_status)) {
//		case 0:
//			orderDelete.setVisibility(View.GONE);
//			orderCancel.setVisibility(View.VISIBLE);
//			orderPay.setVisibility(View.VISIBLE);
//			orderPayAgain.setVisibility(View.GONE);
//			break;
//		case 2:
//			orderDelete.setVisibility(View.GONE);
//			orderCancel.setVisibility(View.GONE);
//			orderPay.setVisibility(View.GONE);
//			orderPayAgain.setVisibility(View.VISIBLE);
//			break;
//		default:
//			break;
//		}
		
	}

}
