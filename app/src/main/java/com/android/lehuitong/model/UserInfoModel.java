package com.android.lehuitong.model;

import java.io.File;
import java.nio.MappedByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SESSION;
import com.android.lehuitong.protocol.STATUS;
import com.android.lehuitong.protocol.USER;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

/** 用户个人信息 Model*/

public class UserInfoModel extends BaseModel {
	private Context mContext;
	public USER user = new USER();

	public UserInfoModel(Context context) {
		super(context);
		this.mContext = context;

	}

	/** 我的乐汇 */
	public void mylehui() {

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					UserInfoModel.this.callback(url, jo, status);
				} catch (Exception e1) {

					e1.printStackTrace();
				}
				try {

					STATUS mStatus = STATUS
							.fromJson(jo.optJSONObject("status"));
					if (mStatus.succeed == 1) {
						user = USER.fromJson(jo.optJSONObject("data"));
						UserInfoModel.this.OnMessageResponse(url, jo, status);
					} else if (mStatus.succeed == 0) {
					}
				} catch (Exception e) {

					e.printStackTrace();
				}

			}
		};

		SESSION session = SESSION.getInstance();
		JSONObject requestJsonObject = new JSONObject();
		Map<String, String> params = new HashMap<String, String>();
		try {
			requestJsonObject.put("session", session.toJson());
		} catch (JSONException e) {
		}
		params.put("json", requestJsonObject.toString());
		cb.url(ProtocolConst.GET_USERINFO).params(params)
				.type(JSONObject.class).method(Constants.METHOD_POST);
		aq.ajax(cb);

	}

	/** 忘记密码 */
	public void forgetpswd() {
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					UserInfoModel.this.callback(url, jo, status);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		JSONObject requestJsonObject = new JSONObject();
		Map<String, String> params = new HashMap<String, String>();
		params.put("json", requestJsonObject.toString());
		cb.url(ProtocolConst.GET_USERINFO).params(params)
				.type(JSONObject.class).method(Constants.METHOD_POST);
		aq.ajax(cb);

	}

	/** 修改密码 */
	@SuppressWarnings("unchecked")
	public void changepswd() {
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					UserInfoModel.this.callback(url, jo, status);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
		Map<String, String> params = new HashMap<String, String>();
		cb.url(ProtocolConst.GET_USERINFO).params(params)
				.type(JSONObject.class).method(Constants.METHOD_POST);
		aq.ajax(cb);
	}
	
	/**
	 * 个人资料修改
	 * @param nickname
	 * @param sex
	 * @param birthday
	 * @param imgUrl
	 */
	 public  void changeuserinfo(String nickname,String sex,String birthday,String imgUrl) {
		 BeeCallback<JSONObject> cb=new BeeCallback<JSONObject>(){
			@Override
			public void callback(String url, JSONObject jo,
					AjaxStatus status) {
				try {
					UserInfoModel.this.callback(url, jo, status);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					STATUS mStatus = STATUS
							.fromJson(jo.optJSONObject("status"));
					if (mStatus.succeed==1) {
					}
					UserInfoModel.this.OnMessageResponse(url, jo, status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				
			}
		 };
		 String url=ProtocolConst.UODATEUSERINFO;
		 SESSION session=SESSION.getInstance();
		 USER user = new USER();
		 user.nickname = nickname;
		 user.sex = sex;
		 user.birthday = birthday;
		 Map<String, Object> params=new HashMap<String, Object>();
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("session", session.toJson());
			jsonObject.put("userInfo",user.toJson());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		params.put("json", jsonObject.toString());
		try {
			File file = new File(imgUrl);
			params.put("head_img",file);
		} catch (Exception e) {
			params.put("head_img", "");
		}
		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);
	}

}
