package com.android.lehuitong.adapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

public class TextViewAdapter extends TextView{

	public TextViewAdapter(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public TextViewAdapter(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TextViewAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
@Override
public boolean dispatchTouchEvent(MotionEvent event) {
	return false;
}
@Override
public boolean onTouchEvent(MotionEvent event) {
	// TODO Auto-generated method stub
	return super.onTouchEvent(event);
}
}
