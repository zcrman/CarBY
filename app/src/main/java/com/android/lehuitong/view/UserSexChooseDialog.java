package com.android.lehuitong.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.funmi.lehuitong.R;

/**
 * 选择用户的性别
 * 
 * @author shenlw
 * 
 */
public class UserSexChooseDialog extends Dialog implements OnClickListener {

	private LayoutInflater layoutInflater;
	private Context mContext;

	private RelativeLayout maleLayout;
	private RelativeLayout femaleLayout;
	private ImageView recive_way;
	private ImageView self_way;
	private ImageView maleIcon;
	private ImageView midRoomIcon;

	public UserSexChooseDialog(Context context, int theme) {
		super(context, theme);
		layoutInflater = LayoutInflater.from(context);
		this.mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layoutInflater.inflate(R.layout.dialog_choose_sex, null));

		maleLayout = (RelativeLayout) findViewById(R.id.big_room_layout);
		femaleLayout = (RelativeLayout) findViewById(R.id.mid_room_layout);

		maleIcon = (ImageView) findViewById(R.id.big_room_icon);
		midRoomIcon = (ImageView) findViewById(R.id.mid_room_icon);
		recive_way = (ImageView) findViewById(R.id.recive_way);
		self_way = (ImageView) findViewById(R.id.self_way);
		maleLayout.setOnClickListener(this);
		femaleLayout.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.big_room_layout:
			maleIcon.setImageDrawable(mContext.getResources().getDrawable(
					R.drawable.personal_data_gender_choice_icon));
			midRoomIcon.setImageDrawable(mContext.getResources().getDrawable(
					R.drawable.personal_data_gender_icon));
			chooseSex("男");

			break;
		case R.id.mid_room_layout:
			midRoomIcon.setImageDrawable(mContext.getResources().getDrawable(
					R.drawable.personal_data_gender_choice_icon));
			maleIcon.setImageDrawable(mContext.getResources().getDrawable(
					R.drawable.personal_data_gender_icon));
			chooseSex("女");
			break;

		default:
			break;
		}

	}

	public void chooseSex(String sexName) {

	}

}
