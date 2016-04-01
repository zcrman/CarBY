package com.android.lehuitong.component;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.BeeFramework.view.WebImageView;
import com.funmi.lehuitong.R;

public class ReserveOrederHolder {
	public WebImageView reserve_image;// 预定KTV头像
	public TextView reserve_nuber;// 预定号
	public TextView ktv_name;// ktv名称
	public TextView box_type;// 包厢类型
	public TextView reserve_time;// 预定时间
	public TextView cancel_reserve;// 取消预定
	public TextView reserve_success;// 预定成功
	public TextView reserve_pay;// 支付
	public TextView reserve_delete;// 预定删除
	public TextView order_status;
	public TextView reserve_price;

	private Context context;

	public ReserveOrederHolder(Context context) {
		this.context = context;
	}

	public void SetMyCouponsInfo(String reserve_nuber, String ktv_name, int box_type, String reserve_time, String good_img, String status,String reserve_price) {
		this.reserve_image.setImageWithURL(context, good_img, R.drawable.default_image);
		this.reserve_nuber.setText(reserve_nuber);
		this.ktv_name.setText(ktv_name);
		if (box_type==1) {
			
			this.box_type.setText("大包");
		}else if (box_type==2) {
			this.box_type.setText("中包");
		}else if (box_type==3) {
			this.box_type.setText("小包");
		}else {
			this.box_type.setText("您没有订包");
		}
		this.reserve_time.setText(reserve_time);
		// this.cancel_reserve.setText(cancel_reserve);
		// this.reserve_success.setText(reserve_success);
		this.reserve_price.setText("￥"+reserve_price);
		switch (Integer.valueOf(status).intValue()) {
		case 1:
			order_status.setText("未付款");
			reserve_delete.setVisibility(View.GONE);
			cancel_reserve.setVisibility(View.VISIBLE);
			reserve_pay.setVisibility(View.VISIBLE);
			break;
		case 2:
			order_status.setText("已付款");
			cancel_reserve.setVisibility(View.GONE);
			reserve_delete.setVisibility(View.GONE);
			reserve_pay.setVisibility(View.GONE);
			break;
		case 3:
			order_status.setText("已发货");
			reserve_pay.setVisibility(View.GONE);
			cancel_reserve.setVisibility(View.GONE);
			reserve_delete.setVisibility(View.GONE);
			break;
		case 4:
			order_status.setText("交易完成");
			reserve_pay.setVisibility(View.GONE);
			reserve_delete.setVisibility(View.VISIBLE);
			cancel_reserve.setVisibility(View.GONE);
			break;
		case 5:
			order_status.setText("预定已确定");
			reserve_pay.setVisibility(View.GONE);
			cancel_reserve.setVisibility(View.GONE);
			reserve_delete.setVisibility(View.GONE);
			break;
		case 7:
			order_status.setText("交易取消");
			reserve_pay.setVisibility(View.GONE);
			reserve_delete.setVisibility(View.VISIBLE);
			cancel_reserve.setVisibility(View.GONE);
			break;
		case 8:
			order_status.setText("预定待确认");
			reserve_pay.setVisibility(View.GONE);
			cancel_reserve.setVisibility(View.GONE);
			reserve_delete.setVisibility(View.GONE);
			break;
		case 9:
			order_status.setText("部分完成");
			reserve_pay.setVisibility(View.GONE);
			cancel_reserve.setVisibility(View.GONE);
			reserve_delete.setVisibility(View.GONE);
			break;
		case 0:
			order_status.setText("");
			break;
		}
	}
}
