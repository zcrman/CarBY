package com.android.lehuitong.view;

import com.funmi.lehuitong.R;
import com.funmi.lehuitong.R.layout;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 修改昵称对话框
 * @author shenlw
 *
 */
public class ChangeUserNameDialog extends Dialog implements android.view.View.OnClickListener{

	private LayoutInflater layoutInflater;
	
	private EditText phone;
	private TextView commit;
	private TextView cancel;
	
	public ChangeUserNameDialog(Context context) {
		super(context);
		layoutInflater=LayoutInflater.from(context);
	}

	public ChangeUserNameDialog(Context context,int theme){
		super(context,theme);
		layoutInflater=LayoutInflater.from(context);
		}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layoutInflater.inflate(R.layout.dialog_change_user_name, null));
		phone = (EditText) findViewById(R.id.ktv_phone);
		commit = (TextView) findViewById(R.id.commit_btn);
		cancel = (TextView) findViewById(R.id.cancel_btn);
		
		commit.setOnClickListener(this);
		cancel.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.commit_btn:
			commitChange(phone.getText().toString());
			break;
		case R.id.cancel_btn:
			
			dismiss();
			break;

		default:
			break;
		}
		
	}

	public void commitChange(String phoneStr){
		phoneStr=phone.getText().toString();
	}
	
}
