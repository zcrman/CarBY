package com.android.lehuitong;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;

import com.BeeFramework.BeeFrameworkApp;
import com.igexin.sdk.PushManager;
import com.umeng.update.UmengUpdateAgent;

public class LehuitongApp extends BeeFrameworkApp {

	public static final String MASTERSECRET = "MyrxOp0HQi5VG5GwSaNq16";
	public static String appkey = "";
	public static String appsecret = "";
	public static String appid = "";
	public static List<Activity> activitys = new LinkedList<Activity>();
	public static LehuitongApp instance;

	@Override
	public void onCreate() {
		super.onCreate();
		PushManager.getInstance().initialize(getApplicationContext());
//		PushManager.getInstance().turnOnPush(getApplicationContext());
	}
	 /**
	     * 单例模式中获取唯一的LehuitongApp实例
	     * 
	     * @return
	     */
	    public static LehuitongApp getInstance() {
	        if (null == instance) {
	            instance = new LehuitongApp();
	        }
	        return instance;
	 
	    }
	 
	    // 添加Activity到容器中
	public void addActivity(Activity activity) {
	        if (activitys != null && activitys.size() > 0) {
	            if(!activitys.contains(activity)){
	                activitys.add(activity);
	            }
	        }else{
	            activitys.add(activity);
	        }
	         
	    }
	 
	    // 遍历所有Activity并finish
	    public void exit() {
	        if (activitys != null && activitys.size() > 0) {
	            for (Activity activity : activitys) {
	                activity.finish();
	            }
	        }
//	        System.exit(0);
	    }
}
