package com.android.lehuitong.view;

import com.funmi.lehuitong.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class TallyOrderDialog extends Dialog implements OnClickListener{
	private LayoutInflater layoutInflater;
	private Context mContext;
	
	private RelativeLayout maleLayout;
	private RelativeLayout femaleLayout;
	private ImageView maleIcon;
	private ImageView midRoomIcon;
	
	public TallyOrderDialog(Context context, int theme) {
		super(context, theme);
		layoutInflater = LayoutInflater.from(context);
		this.mContext=context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layoutInflater.inflate(R.layout.dialog_choose_receive, null));
		
		maleLayout = (RelativeLayout) findViewById(R.id.big_room_layout);
		femaleLayout = (RelativeLayout) findViewById(R.id.mid_room_layout);
	
		maleIcon = (ImageView) findViewById(R.id.self_way);
		midRoomIcon = (ImageView) findViewById(R.id.recive_way);
		maleLayout.setOnClickListener(this);
		femaleLayout.setOnClickListener(this);
			
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.big_room_layout:
			maleIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.personal_data_gender_choice_icon));
			midRoomIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.personal_data_gender_icon));		
			choseway_self("自提");
			break;
		case R.id.mid_room_layout:
			midRoomIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.personal_data_gender_choice_icon));
			maleIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.personal_data_gender_icon));
			choseway("快递");
			break;
		default:
			break;
		}
		
	}
	
	
	public void choseway(String way) {

	}
	public void choseway_self(String way) {
		
	}
	

}
