package com.android.lehuitong.component;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class KtvPackageChooseHolder {
	
	public TextView packageHeader;
	public TextView packagePrice;
	public LinearLayout packLayout;
	public RelativeLayout packSelectLayout;
	public TextView goodsBrief;
	
	public void setPackageInfo(String title,String price,String brief){
		this.packageHeader.setText(title);
		this.packagePrice.setText("¥ "+price);
		this.goodsBrief.setText(brief);
		
	}
	
	

}
