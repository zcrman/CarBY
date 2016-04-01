package com.android.lehuitong.component;

import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.protocol.ProtocolConst;
import com.funmi.lehuitong.R;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

public class KtvLocationChooseListHolder{

	public WebImageView image;
	public TextView  name;
	private Context mContext;
	
	public KtvLocationChooseListHolder(Context context){
		this.mContext=context;
	}
	public void setLocationListInfo(String imageUrl,String name){
		this.image.setImageWithURL(mContext,imageUrl, mContext.getResources().getDrawable(R.drawable.default_image));
		this.name.setText(name);
		
	}
	
}
