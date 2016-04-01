package com.android.lehuitong.view;



import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.NoCopySpan.Concrete;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.funmi.lehuitong.R;

/**
 * 选择ktv包厢类型
 * @author shenlw
 *
 */
public class KtvRoomChooseDialog extends Dialog implements OnClickListener {

	private LayoutInflater layoutInflater;
	private Context mContext;
	
	private RelativeLayout bigRoomLayout;
	private RelativeLayout midRoomLayout;
	private RelativeLayout smallRoomLayout;
	
	private ImageView bigRoomIcon;
	private ImageView midRoomIcon;
	private ImageView smallRoomIcon;
	
	public KtvRoomChooseDialog(Context context, int theme) {
		super(context, theme);
		layoutInflater = LayoutInflater.from(context);
		this.mContext=context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layoutInflater.inflate(R.layout.dialog_ktv_room_choose, null));
		
		bigRoomLayout = (RelativeLayout) findViewById(R.id.big_room_layout);
		midRoomLayout = (RelativeLayout) findViewById(R.id.mid_room_layout);
		smallRoomLayout = (RelativeLayout) findViewById(R.id.small_room_layout);
		
		bigRoomIcon = (ImageView) findViewById(R.id.big_room_icon);
		midRoomIcon = (ImageView) findViewById(R.id.mid_room_icon);
		smallRoomIcon = (ImageView) findViewById(R.id.small_room_icon);
		
		bigRoomLayout.setOnClickListener(this);
		midRoomLayout.setOnClickListener(this);
		smallRoomLayout.setOnClickListener(this);
		
		
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.big_room_layout:
			bigRoomIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.personal_data_gender_choice_icon));
			midRoomIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.personal_data_gender_icon));
			smallRoomIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.personal_data_gender_icon));
			chooseRoom("大包");
			
			break;
		case R.id.mid_room_layout:
			midRoomIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.personal_data_gender_choice_icon));
			bigRoomIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.personal_data_gender_icon));
			smallRoomIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.personal_data_gender_icon));
			chooseRoom("中包");
			break;
		case R.id.small_room_layout:
			smallRoomIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.personal_data_gender_choice_icon));
			midRoomIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.personal_data_gender_icon));
			bigRoomIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.personal_data_gender_icon));
			chooseRoom("小包");
			break;

		default:
			break;
		}
		
	}
	public void  chooseRoom(String roomName){
		
	}

	

}
