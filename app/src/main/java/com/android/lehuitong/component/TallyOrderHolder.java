package com.android.lehuitong.component;

import java.text.DecimalFormat;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.activity.TallyOrderActivity;
import com.funmi.lehuitong.R;

public class TallyOrderHolder {

	public WebImageView pay_image;
	public TextView pay_price;
	public TextView pay_name;
	public TextView pay_number;

	private Context context;

	public TallyOrderHolder(Context context) {
		this.context = context;
	}
	public void initView(View view) {
		pay_image = (WebImageView) view.findViewById(R.id.pay_image);
		pay_price = (TextView) view.findViewById(R.id.pay_price);
		pay_name = (TextView) view.findViewById(R.id.pay_name);
		pay_number = (TextView) view.findViewById(R.id.pay_number);
		
	}

	public void setOrderInfo(String name, String number, String price, String imgUrl) {
		double subprice=0;
		for (int i = 0; i<Integer.parseInt(number); i++) {
			subprice = TallyOrderActivity.add(subprice, Double.parseDouble(price));
		}
		DecimalFormat format = new DecimalFormat("#####0.00");
		pay_price.setText(format.format(subprice));
//		pay_price.setText(price);
		pay_number.setText(number);
		pay_name.setText(name);
		pay_image.setImageWithURL(context, imgUrl,R.drawable.default_image);
	}
}
