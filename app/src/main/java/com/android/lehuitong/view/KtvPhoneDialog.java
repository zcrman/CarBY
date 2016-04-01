package com.android.lehuitong.view;

import com.funmi.lehuitong.R;
import com.funmi.lehuitong.R.layout;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.renderscript.Type;
import android.text.Editable.Factory;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 输入预留电话对话框
 * @author shenlw
 *
 */
public class KtvPhoneDialog extends Dialog implements android.view.View.OnClickListener{

	private LayoutInflater layoutInflater;
	
	private EditText phone;
	private TextView commit;
	private TextView cancel;
	private TextView title;
	private String titleString;
	private String hintString;
	private int inPutType;
	private int maxInputLength=0;
	
	
	public KtvPhoneDialog(Context context) {
		super(context);
		layoutInflater=LayoutInflater.from(context);
	}

	public KtvPhoneDialog(Context context,int theme){
		super(context,theme);
		layoutInflater=LayoutInflater.from(context);
		}
	public KtvPhoneDialog(Context context,int theme,String titleString,String hintString){
		super(context,theme);
		layoutInflater=LayoutInflater.from(context);
		this.titleString = titleString;
		this.hintString = hintString;
		}
	public KtvPhoneDialog(Context context,int theme,String titleString,String hintString,int inPutType,int maxInputLength){
		super(context,theme);
		layoutInflater=LayoutInflater.from(context);
		this.titleString = titleString;
		this.hintString = hintString;
		this.inPutType = inPutType;
		this.maxInputLength = maxInputLength;
		}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = layoutInflater.inflate(R.layout.dialog_ktv_phone, null);
		setContentView(view);
		phone = (EditText) view.findViewById(R.id.ktv_phone);
		commit = (TextView) view.findViewById(R.id.commit_btn);
		cancel = (TextView) view.findViewById(R.id.cancel_btn);
		title = (TextView) view.findViewById(R.id.dialog_title_text);	
		commit.setOnClickListener(this);
		cancel.setOnClickListener(this);
		if (titleString!=null&&hintString!=null) {	
			title.setText(titleString);
			phone.setHint(hintString);
			
		}
		if (maxInputLength!=0) {
			phone.setInputType(inPutType);
			phone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxInputLength)});
		}
		
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.commit_btn:
			commitPhone(phone.getText().toString());
			break;
		case R.id.cancel_btn:
			dismiss();
			break;

		default:
			break;
		}
		
	}
	

	public void commitPhone(String phoneStr){
		phoneStr=phone.getText().toString();
	}
	@SuppressWarnings("unused")
	public void type() {
		// TODO Auto-generated method stub
		
	}
}
