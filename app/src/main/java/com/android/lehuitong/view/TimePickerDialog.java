package com.android.lehuitong.view;

import java.util.Calendar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import net.simonvt.numberpicker.NumberPicker;
import net.simonvt.numberpicker.NumberPicker.OnScrollListener;

import com.funmi.lehuitong.R;


public class TimePickerDialog extends Dialog implements android.view.View.OnClickListener{
	private Context mContext;
	
	private Button positiveBtn;
	private Button negativeBtn;
	
	private NumberPicker dayPicker;
	private NumberPicker yearPicker;
	private NumberPicker monthPicker;
	private int time;
	private String str;
	
	private int maxDays;
	private int maxFebDays;
	private int currentday;
	private int currentYear;
	private int currentMonth;
	
	public int getCurrentday() {
		return currentday;
	}

	public void setCurrentday(int currentday) {
		this.currentday = currentday;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}

	public int getCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(int currentMonth) {
		this.currentMonth = currentMonth;
	}

	private Calendar calendar;
	
	private PickerDialogButtonListener mListener;
	
	public interface PickerDialogButtonListener{
		public void onClick(View v);
	}
	
	public TimePickerDialog(Context context) {
		super(context);
		mContext = context;
	}
	
	public TimePickerDialog(Context context, PickerDialogButtonListener listener) {
		super(context);
		mContext = context;
		mListener = listener;
	}

	public TimePickerDialog(Context context, int theme, PickerDialogButtonListener listener){
		super(context, theme);
		mContext = context;
		mListener = listener;
	}
	public TimePickerDialog(Context context, int theme, PickerDialogButtonListener listener,int time){
		super(context, theme);
		mContext = context;
		mListener = listener;
		this.time=time;
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_timepicker);
		
		initCalendar();

		initView();
		initDialog();
	}

	private void initView(){
		positiveBtn = (Button)findViewById(R.id.dialog_btn_positive);
		negativeBtn = (Button)findViewById(R.id.dialog_btn_negative);
		
		dayPicker = (NumberPicker)findViewById(R.id.time_day_picker);
		dayPicker.setMinValue(1);
		dayPicker.setMaxValue(calendar.getActualMaximum(Calendar.DATE));
		dayPicker.setValue(currentday);
		dayPicker.setEditTextEnabled(false);
		
		yearPicker = (NumberPicker)findViewById(R.id.time_year_picker);
		yearPicker.setMinValue(currentYear-time);
		yearPicker.setMaxValue(currentYear+time);
		yearPicker.setValue(currentYear);
		yearPicker.setEditTextEnabled(false);
		yearPicker.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChange(NumberPicker view, int scrollState) {
				if(view.getValue() % 100 != 0 && view.getValue() % 4 == 0){
					maxFebDays = 29;
				}else if(view.getValue() % 400 ==0){
					maxFebDays = 29;
				}else{
					maxFebDays = 28;
				}
			}
		});
		
		monthPicker = (NumberPicker)findViewById(R.id.time_month_picker);
		monthPicker.setMinValue(1);
		monthPicker.setMaxValue(12);
		monthPicker.setValue(currentMonth);
		monthPicker.setEditTextEnabled(false);
		monthPicker.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChange(NumberPicker view, int scrollState) {
				if(scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE){
					calendar.set(Calendar.MONTH, view.getValue() - 1);
					
					if(dayPicker.getValue() > calendar.getActualMaximum(Calendar.DATE)){
						dayPicker.setValue(calendar.getActualMaximum(Calendar.DATE));
					}
					
					dayPicker.setMaxValue(calendar.getActualMaximum(Calendar.DATE));
				}
			}
		});
		
		setClickListener();
	}
	
	private void initDialog(){
		Window dialogWindow = this.getWindow();
		WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		
		dialogWindow.setGravity(Gravity.CENTER);
		
		lp.width = (int)(display.getWidth()*0.8);
		lp.height = (int)(LayoutParams.WRAP_CONTENT);
		dialogWindow.setAttributes(lp);
	}
	
	private void initCalendar(){
		calendar = Calendar.getInstance();
		
		currentYear = calendar.get(Calendar.YEAR);
		currentMonth = calendar.get(Calendar.MONTH) + 1;
		currentday = calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	private void setClickListener(){
		positiveBtn.setOnClickListener(this);
		negativeBtn.setOnClickListener(this);
	}
	
	public String getTime(){
		str = yearPicker.getValue() + "-" + monthPicker.getValue() + "-" + dayPicker.getValue();
		return str;
	}
	public int getYear(){
		return yearPicker.getValue();
	}
	public int getMonth(){
		return monthPicker.getValue();
	}
	public int getDay(){
		return dayPicker.getValue();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_btn_positive:
			mListener.onClick(v);
			break;
			
		case R.id.dialog_btn_negative:
			this.dismiss();
		}
	}
}
