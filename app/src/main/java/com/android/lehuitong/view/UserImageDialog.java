package com.android.lehuitong.view;


import com.funmi.lehuitong.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 修改用户头像
 * @author shenlw
 *
 */
public class UserImageDialog extends Dialog implements OnClickListener {

	private LayoutInflater layoutInflater;
	
	private TextView takePhoto;
	private TextView uploadPhoto;
	
	public UserImageDialog(Context context, int theme) {
		super(context, theme);
		layoutInflater = LayoutInflater.from(context);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layoutInflater.inflate(R.layout.dialog_change_user_image, null));
		
		takePhoto = (TextView) findViewById(R.id.user_takephoto);
		uploadPhoto = (TextView) findViewById(R.id.upload_photo);
		
		takePhoto.setOnClickListener(this);
		uploadPhoto.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.user_takephoto:
			takePhoto();
			break;
		case R.id.upload_photo:
			uploadPhoto();
			break;

		default:
			break;
		}
		
	}
	
	/**
	 * 照相
	 */
	public void takePhoto(){
		
	}
	/**
	 *头像上传
	 */
	public void uploadPhoto(){
		
	}

}
