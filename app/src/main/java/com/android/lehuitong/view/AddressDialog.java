package com.android.lehuitong.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.lehuitong.pay.alipay.PayDemoActivity;
import com.funmi.lehuitong.R;

public class AddressDialog extends Dialog  implements android.view.View.OnClickListener{

	public Dialog mDialog;
	public TextView dialog_title;
	public TextView dialog_message;
	public TextView positive;
	public TextView negative;
	
	public AddressDialog(Context context) {
		super(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.dialog_layout, null);

		mDialog = new Dialog(context, R.style.dialog);
		mDialog.setContentView(view);
		mDialog.setCanceledOnTouchOutside(true);
		dialog_title = (TextView) view.findViewById(R.id.dialog_title);
		dialog_message = (TextView) view.findViewById(R.id.dialog_message);
		
		positive = (TextView) view.findViewById(R.id.yes);
		negative = (TextView) view.findViewById(R.id.no);
		positive.setOnClickListener(this);
		negative.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.yes:
			setCommit();
			break;
		case R.id.no:
			setCancel();
			break;
		default:
			break;
		}
	}

	/**
	 * 确认
	 */
	public void setCommit(){
	}
	
	/**
	 * 取消
	 */
	public void setCancel(){
		
	}
	
	public void settitle(String title){
		dialog_title.setText(title);
	}

	

		

	
}
