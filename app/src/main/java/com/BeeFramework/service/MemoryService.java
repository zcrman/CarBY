package com.BeeFramework.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PixelFormat;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.BeeFrameworkApp;
import com.BeeFramework.Utils.LinuxUtils;
import com.BeeFramework.model.BeeCallback;
import com.funmi.lehuitong.R;

/*
 *	 ______    ______    ______
 *	/\  __ \  /\  ___\  /\  ___\
 *	\ \  __<  \ \  __\_ \ \  __\_
 *	 \ \_____\ \ \_____\ \ \_____\
 *	  \/_____/  \/_____/  \/_____/
 *
 *
 *	Copyright (c) 2013-2014, {Bee} open source community
 *	http://www.bee-framework.com
 *
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a
 *	copy of this software and associated documentation files (the "Software"),
 *	to deal in the Software without restriction, including without limitation
 *	the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *	and/or sell copies of the Software, and to permit persons to whom the
 *	Software is furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in
 *	all copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *	FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 *	IN THE SOFTWARE.
 */

@SuppressLint("NewApi")
public class MemoryService extends Service {

	private float downX;
	private float downY;
	private LayoutParams mLayoutParams;
	
	private float upX;
	private float upY;
	
	private float moveX;
	private float moveY;
	
	private float actionDownX;
	private float actionDownY;
	
	private WindowManager wManager ;
	
	private View view;
	
	private Timer timer;
	
	private ActivityManager mActivityManager = null ; 
	
	private SharedPreferences mPref;
	private Editor mEditor;
	
	private long _memSize;
	
	private TextView total;
	private TextView avail; 
	private TextView low; 
	private TextView memSize; 
	private TextView pid; 
	private TextView cpuUsage; 
	private TextView processName;

    private TextView networkUsage;
    private TextView networkWakeupTime;
    private TextView networkLastSecondUsage;
    private TextView networkLimitBandwidth;
	
	private ImageView logo;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		mPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
		
		BeeFrameworkApp.getInstance().currContext = this;
        
        wManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        mActivityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        wmParams.type = LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL |
                LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.TOP|Gravity.LEFT ;
        
        wmParams.x = mPref.getInt("x", 0);
        wmParams.y = mPref.getInt("y", 0);

        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mLayoutParams = wmParams;
        
        LayoutInflater inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.memory, null);

		total = (TextView) view.findViewById(R.id.f_memory_total);
		avail = (TextView) view.findViewById(R.id.f_memory_avail);
		low = (TextView) view.findViewById(R.id.f_memory_low);
		memSize = (TextView) view.findViewById(R.id.f_memory_memSize);
		pid = (TextView) view.findViewById(R.id.f_memory_pid);
		cpuUsage = (TextView) view.findViewById(R.id.f_memory_cpuUsage);
		processName = (TextView) view.findViewById(R.id.f_memory_processName);
        networkUsage = (TextView)view.findViewById(R.id.network_usage);
        networkWakeupTime = (TextView)view.findViewById(R.id.network_wakeuptime);
        networkLastSecondUsage = (TextView)view.findViewById(R.id.network_lastSecondUsage);
        networkLimitBandwidth = (TextView)view.findViewById(R.id.network_limit_bandwidth);
		
        logo = (ImageView) view.findViewById(R.id.f_logo);
        
        logo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction("com.BeeFramework.MemoryService");
				stopService(intent);
			}
		});
		
        wManager.addView(view, wmParams);
        
        view.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int ea=event.getAction();

				switch(ea){
				case MotionEvent.ACTION_DOWN:
					downX = mLayoutParams.x;
					downY = mLayoutParams.y;
					
					actionDownX = event.getX();
					actionDownY = event.getY();
					break;
				case MotionEvent.ACTION_MOVE:
					moveX = event.getRawX();
					moveY = event.getRawY() - 25; 
					updateViewPosition(false);
					break;
				}
				return true;
			}
			
        });
        
        
        timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message(); 
                msg.what = 1;
                handler.sendMessage(msg);
			} 
        };
        
        timer.schedule(timerTask, 0, 250);
		
	}
	
	private void updateViewPosition(boolean isActionUp) {
		if (!isActionUp) {
			mLayoutParams.x = (int) (moveX - actionDownX);
			mLayoutParams.y = (int) (moveY - actionDownY);
		} else {// MotionEvent.ACTION_UP
			if (!(Math.abs(upX - downX) > 50 || Math.abs(upY - downY) > 50)) {
			
				mLayoutParams.x = (int) (downX);
				mLayoutParams.y = (int) (downY);
			}
		}
		wManager.updateViewLayout(view, mLayoutParams);
	}
	
    Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what) {
			case 1:
				setContent();
				break;
			case 2:
				DecimalFormat df = new DecimalFormat("#.##");
				cpuUsage.setText("CPU"+df.format(f)+"%");
				break;
			}
		}
    };
    
    public void setContent() {
    	
		MemoryInfo memoryInfo = new MemoryInfo() ;  
		mActivityManager.getMemoryInfo(memoryInfo) ;  
        
		total.setText(""+formateFileSize(memoryInfo.totalMem));
		avail.setText(""+formateFileSize(memoryInfo.availMem));
		low.setText(""+memoryInfo.lowMemory);
        networkUsage.setText(""+ BeeCallback.averageBandwidthUsedPerSecond+"bytes");

        if (null != BeeCallback.throttleWakeUpTime  && BeeCallback.throttleWakeUpTime.after(new Date()))
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            networkWakeupTime.setText(""+format.format(BeeCallback.throttleWakeUpTime));

        }
        else
        {
            networkWakeupTime.setText("");
        }
        networkLastSecondUsage.setText(""+BeeCallback.bandwidthUsedInLastSecond+"bytes");
        networkLimitBandwidth.setText(""+BeeCallback.maxBandwidthPerSecond+"bytes");

		
		getRunningAppProcessInfo();
		
    }
    
    private String formateFileSize(long size){  
        return Formatter.formatFileSize(this, size);   
    }
    
    private float f; 
	private void getRunningAppProcessInfo() {

	
		List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager.getRunningAppProcesses();
		
		for(int i=0;i < appProcessList.size();i++) {
			
			if(appProcessList.get(i).processName.equals("com.BeeFramework.example")) {
				final int _pid = appProcessList.get(i).pid;
				int _uid = appProcessList.get(i).uid;
				String _processName = appProcessList.get(i).processName;
				int[] myMempid = new int[] { _pid };
				Debug.MemoryInfo[] memoryInfo = mActivityManager.getProcessMemoryInfo(myMempid);
				_memSize = memoryInfo[0].dalvikPrivateDirty;
				
				pid.setText(""+_pid);
				
			    processName.setText(""+_processName);
			    memSize.setText(""+formateFileSize(_memSize*1024));
			    
			    new Thread() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						super.run();
						LinuxUtils linux = new LinuxUtils();
						f = linux.syncGetProcessCpuUsage(_pid);
						Message msg = new Message(); 
		                msg.what = 2;
		                handler.sendMessage(msg);
					}
					
				}.start();
			    
				break;
			}
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		timer.cancel();
		wManager.removeView(view);
		
		mEditor = mPref.edit();
		mEditor.putInt("x", mLayoutParams.x);
		mEditor.putInt("y", mLayoutParams.y);
		mEditor.commit();
	}

}
