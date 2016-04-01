package com.BeeFramework;

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

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

import com.BeeFramework.Utils.CustomExceptionHandler;
import com.BeeFramework.activity.DebugCancelDialogActivity;
import com.BeeFramework.activity.DebugTabActivity;
import com.BeeFramework.view.BeeInjector;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.external.activeandroid.app.Application;
import com.funmi.lehuitong.R;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class BeeFrameworkApp extends Application implements OnClickListener{
    private static BeeFrameworkApp instance;
    private ImageView bugImage;
    public Context currContext;
    
    private WindowManager wManager ;
    private boolean flag = true;
    
    public Handler messageHandler;
    
    private BeeInjector mInjector;
    public  double lat, lng;
	MyPosition position = new MyPosition();
    public static DisplayImageOptions options;		
	public static DisplayImageOptions options_head;		

    public static BeeFrameworkApp getInstance()
    {
        if (instance == null) {
            instance = new BeeFrameworkApp();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        SDKInitializer.initialize(this);
        LocationClient mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(position);
		 LocationClientOption option = new LocationClientOption();
	        option.setOpenGps(true);// 打开gps
	        option.setCoorType("bd09ll"); // 设置坐标类型
	        option.setScanSpan(1000);
	        mLocClient.setLocOption(option);
		mLocClient.start();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + AppConst.LOG_DIR_PATH;
        File storePath = new File(path);
        storePath.mkdirs();
        Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(path, null));
        
//        Intent intent = new Intent();
//        intent.setAction("com.BeeFramework.NetworkState.Service");
//        startService(intent);
        
        initImageLoader(this);
        
        options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.default_image)		
			.showImageForEmptyUri(R.drawable.default_image)	
			.showImageOnFail(R.drawable.default_image)			
			.cacheInMemory(true)						
			.cacheOnDisc(true)							
			//.displayer(new RoundedBitmapDisplayer(20))	
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();
    
        options_head = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.default_image)			
			.showImageForEmptyUri(R.drawable.default_image)	
			.showImageOnFail(R.drawable.default_image)		
			.cacheInMemory(true)					
			.cacheOnDisc(true)							
			.displayer(new RoundedBitmapDisplayer(10))	
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();	
        
    }

    public void showBug(final Context context)
    {
        BeeFrameworkApp.getInstance().currContext = context;
        wManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        wmParams.type = LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL |
                LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        wmParams.x = 0;
        wmParams.y = 0;

        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        if(bugImage != null) { 
        	wManager.removeView(bugImage);
        }
        
        bugImage = new ImageView(context);
        bugImage.setImageResource(R.drawable.bug);
        wManager.addView(bugImage, wmParams);
        bugImage.setOnClickListener(this);
        
        bugImage.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				DebugCancelDialogActivity.parentHandler = messageHandler;
				Intent intent = new Intent(BeeFrameworkApp.getInstance().currContext,DebugCancelDialogActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				flag = false;
				return false;
			}
		});
        
        messageHandler = new Handler() {

            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                	wManager.removeView(bugImage);
                	bugImage = null;
                }
            }
        };
    }

    public void onClick(View v) {
    	if(flag != false) {
    		Intent intent = new Intent(BeeFrameworkApp.getInstance().currContext,DebugTabActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
    	}
        flag = true;
    }
    
    public BeeInjector getInjector() {
		if (mInjector == null) {
			mInjector = BeeInjector.getInstance();
		}
		return mInjector;
	}
    
    public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 1)
				.threadPoolSize(3)
				.denyCacheImageMultipleSizesInMemory()
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				//.writeDebugLogs() // Remove for release app
				.memoryCache(new LruMemoryCache(4*1024*1024) )
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
    public class MyPosition implements BDLocationListener{
    	
    	@Override
    	public void onReceiveLocation(BDLocation arg0) {
    		lat = arg0.getLatitude();
    		lng = arg0.getLongitude();
    	}
    	
    }
    
}
