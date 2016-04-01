package com.android.lehuitong.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;

import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SESSION;
import com.android.lehuitong.protocol.STATUS;
import com.android.lehuitong.protocol.USER;
import com.android.lehuitong.tuisongdemo.GetuiSdkHttpPost;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;
import com.igexin.sdk.PushManager;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.widget.Toast;

public class CidModel extends BaseModel{
	
	Context context;
	public CidModel(Context context) {
		super(context);
		this.context=context;
	}
	public void sendCid() {
		String url=ProtocolConst.USER_BLINDPUSHER;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
					USER user = USER.fromJson(jo.optJSONObject("data"));
						String packageName = context.getPackageName();
						try {
							ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
									packageName, PackageManager.GET_META_DATA);
							if (appInfo.metaData != null) {
//								LehuitongApp.appid = appInfo.metaData.getString("PUSH_APPID");
//								LehuitongApp.appsecret = appInfo.metaData.getString("PUSH_APPSECRET");
//								LehuitongApp.appkey = (appInfo.metaData.get("PUSH_APPKEY") != null) ? appInfo.metaData
//										.get("PUSH_APPKEY").toString() : null;
							}
						} catch (NameNotFoundException e) {
							e.printStackTrace();
						}
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("action", "pushmessage"); // pushSpecifyMessage为接口名，注意大小写
						/*---以下代码用于设定接口相应参数---*/
//						param.put("appkey", LehuitongApp.appkey);
						param.put("type", 2); // 推送类型： 2为消息
						param.put("pushTitle", "通知栏测试"); // pushTitle请填写您的应用名称

						// 推送消息类型，有TransmissionMsg、LinkMsg、NotifyMsg三种，此处以LinkMsg举例
						param.put("pushType", "LinkMsg");

						param.put("offline", true); // 是否进入离线消息
				
						param.put("offlineTime", 72); // 消息离线保留时间
						param.put("priority", 1); // 推送任务优先级
						
						param.put("tokenMD5List", user.user_cid);
						
						// 生成Sign值，用于鉴权，需要MasterSecret，请务必填写
//						param.put("sign", GetuiSdkHttpPost.makeSign(LehuitongApp.MASTERSECRET, param));
						// LinkMsg消息实体
//						Map<String, Object> linkMsg = new HashMap<String, Object>();
//						linkMsg.put("linkMsgIcon", "push.png"); // 消息在通知栏的图标
//						linkMsg.put("linkMsgTitle", "通知栏测试"); // 推送消息的标题
						// linkMsg.put("linkMsgContent", "您收到一条测试消息，点击访问www.igetui.com！"); //
						// 推送消息的内容
						// linkMsg.put("linkMsgUrl", "http://www.igetui.com/"); // 点击通知跳转的目标网页
//						param.put("msg", linkMsg);
						GetuiSdkHttpPost.httpPost(param);
						Log.i("ok","ok");
					}
				} catch (JSONException e) {
					e.printStackTrace();

				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(mContext, "网络或服务器错误，请稍后再试", Toast.LENGTH_LONG)
							.show();
				}
			}
		};
		USER user=new USER();
		Map<String, String> params = new HashMap<String, String>();
		SESSION se=SESSION.getInstance();
		JSONObject requestJsonObject = new JSONObject();
		try {
			requestJsonObject.put("session", se.toJson());
			requestJsonObject.put("clientID", PushManager.getInstance().getClientid(context));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.put("json", requestJsonObject.toString());
		cb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		aq.ajax(cb);
		
		
		
	}
}
