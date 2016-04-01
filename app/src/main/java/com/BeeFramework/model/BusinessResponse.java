package com.BeeFramework.model;

import com.external.androidquery.callback.AjaxStatus;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * 一个接口
 * @author Administrator
 *
 */
public interface BusinessResponse
{   
	/** 
	 * 响应的接口
	 * @param url
	 * @param jo
	 * @param status
	 * @throws JSONException
	 */
	public abstract void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException;
}
