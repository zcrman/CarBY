package com.android.lehuitong.adapter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;

public class ViewPagerAdapter extends PagerAdapter {
	private int mPreviousState = ViewPager.SCROLL_STATE_IDLE;
	private  List<ImageView> list;
	int current;
	private Context context;
	private View mTouchTarget;
	private Handler hand;
	public ViewPagerAdapter() {
		// TODO Auto-generated constructor stub
	}
	public ViewPagerAdapter(Context context) {
		this.context=context;
	}
	public void bindData(List<ImageView> list){
		this.list = list;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {

		return arg0==arg1;
	}
	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(list.get(position));

	}
	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(list.get(position));
		return list.get(position);
	}

}
