package com.android.lehuitong.component;

import com.funmi.lehuitong.R;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Administrator
 * 
 */
public class AddressManagerHolder {
	public Button address_delete_button;
	public ImageView address_select;
	public TextView myself_name;
	public TextView my_phone;
	public TextView my_address;

	public void SetMyCouponsInfo(String myself_name, String my_phone,
			String my_address,int defaultAddress) {
		if (defaultAddress==1) {
			address_select.setImageResource(R.drawable.address_management_the_default_icon);
		}else if (defaultAddress==0) {
			address_select.setImageResource(R.drawable.my_shopping_cart_not_selected_icon);
		}
		this.myself_name.setText(myself_name);
		this.my_phone.setText(my_phone);
		if (my_address=="") {
			this.my_address.setText("未知");
		}else {
			this.my_address.setText(my_address);
			
		}
	}

}
