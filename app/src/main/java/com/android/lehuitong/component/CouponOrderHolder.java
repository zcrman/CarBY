package com.android.lehuitong.component;

import com.BeeFramework.view.WebImageView;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * 优惠券holder
 * */
public class CouponOrderHolder {
	public TextView coupon_sn;// 订单号
	public TextView coupon_price;// 优惠券价格
	public TextView coupon_name;// 名称
	public TextView coupon_count;// 数量
	public TextView coupon_pay;// 支付
	public TextView buy_agin;// 再次购买
	public TextView coupon_delete;// 删除
	public TextView coupon_cancel;// 取消
	public TextView coupon_state;// 支付状态
	public WebImageView coupon_image;// 图片
	public Context context;
	public CouponOrderHolder(Context context) {
		this.context=context;
	}
	/** 设置控件属性 */
	public void setInfo(String coupon_sn, String coupon_price,
			String coupon_name, String coupon_count, String state,
			String coupon_image) {
		this.coupon_sn.setText(coupon_sn);
		this.coupon_price.setText(coupon_price);
		this.coupon_name.setText(coupon_name);
		this.coupon_count.setText(coupon_count);
		this.coupon_image.setImageWithURL(context, coupon_image);
		switch (Integer.valueOf(state).intValue()) {
		case 1:
			coupon_state.setText("未付款");
			coupon_delete.setVisibility(View.GONE);
			coupon_pay.setVisibility(View.VISIBLE);
			coupon_cancel.setVisibility(View.VISIBLE);
			buy_agin.setVisibility(View.GONE);
			break;
		case 2:
			coupon_state.setText("已付款");
			coupon_delete.setVisibility(View.GONE);
			buy_agin.setVisibility(View.GONE);
			coupon_cancel.setVisibility(View.GONE);
			coupon_pay.setVisibility(View.GONE);
			break;
		case 3:
			coupon_state.setText("已发货");
			coupon_delete.setVisibility(View.GONE);
			buy_agin.setVisibility(View.GONE);
			coupon_cancel.setVisibility(View.GONE);
			coupon_pay.setVisibility(View.GONE);
			break;
		case 4:
			coupon_state.setText("交易完成");
			coupon_delete.setVisibility(View.VISIBLE);
			buy_agin.setVisibility(View.GONE);
			coupon_cancel.setVisibility(View.GONE);
			coupon_pay.setVisibility(View.GONE);
			break;
		case 5:
			coupon_state.setText("预定已确定");
			coupon_delete.setVisibility(View.GONE);
			buy_agin.setVisibility(View.GONE);
			coupon_cancel.setVisibility(View.GONE);
			coupon_pay.setVisibility(View.GONE);
			break;
		case 7:
			coupon_state.setText("交易取消");
			coupon_delete.setVisibility(View.VISIBLE);
			coupon_cancel.setVisibility(View.GONE);
			coupon_pay.setVisibility(View.GONE);
			break;
		case 8:
			coupon_state.setText("预定待确认");
			coupon_delete.setVisibility(View.GONE);
			buy_agin.setVisibility(View.GONE);
			coupon_cancel.setVisibility(View.GONE);
			coupon_pay.setVisibility(View.GONE);
			break;
		case 9:
			coupon_state.setText("部分完成");
			coupon_delete.setVisibility(View.GONE);
			buy_agin.setVisibility(View.GONE);
			coupon_cancel.setVisibility(View.GONE);
			coupon_pay.setVisibility(View.GONE);
			break;
		case 0:
			coupon_state.setText("");
			break;
		}
	}
}
