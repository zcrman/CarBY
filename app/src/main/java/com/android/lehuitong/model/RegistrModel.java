package com.android.lehuitong.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SESSION;
import com.android.lehuitong.protocol.STATUS;
import com.android.lehuitong.protocol.USER;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

/** 注册Model */
public class RegistrModel extends BaseModel {

	/** 注册接口地址 */
	private Context myContext;
	public STATUS responseStatus;

	public RegistrModel(Context context) {
		super(context);
		this.myContext = context;

	}

	/** 注册 */
	public void signup(String name, String password, String code) {
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					RegistrModel.this.callback(url, jo, status);
					responseStatus = STATUS
							.fromJson(jo.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						JSONObject data = jo.optJSONObject("data");
						SESSION session = SESSION.fromJson(data
								.optJSONObject("session"));
						USER user = USER.fromJson(data.optJSONObject("user"));
						RegistrModel.this.OnMessageResponse(url, jo, status);
					} else if (responseStatus.succeed == 0) {
						Toast.makeText(mContext, responseStatus.error_desc, Toast.LENGTH_SHORT).show();
					}

				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		};

		String url = ProtocolConst.REGISTER;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("password", password);
		params.put("verCode", code);
		MyProgressDialog dialog = new MyProgressDialog(myContext, "请稍后....");
		cb.url(url).type(JSONObject.class).params(params)
				.method(Constants.METHOD_POST);
		aq.progress(dialog.mDialog).ajax(cb);

	}

	/** 验证码 */
	@SuppressWarnings("unchecked")
	public void getcode(String phone,int type,final CountDownTimer timer) {
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					RegistrModel.this.callback(url, jo, status);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					responseStatus = STATUS
							.fromJson(jo.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						timer.start();
						RegistrModel.this.OnMessageResponse(url, jo, status);
					} else if (responseStatus.succeed == 0) {
						Toast.makeText(myContext, responseStatus.error_desc+"，请重新获取",
								Toast.LENGTH_SHORT).show();
						timer.cancel();

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
		
		String url = ProtocolConst.GETCODE;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("phone", phone);
		params.put("type", type);
		
		cb.url(url).type(JSONObject.class).params(params)
				.method(Constants.METHOD_POST);
		aq.ajax(cb);
	}
	/**找回密码*/
	public void findpsw(String name,String newpassword,String verCode){
		BeeCallback<JSONObject> cb=new BeeCallback<JSONObject>(){
			@Override
			public void callback(String url, JSONObject jo,
					AjaxStatus status) {
				try {
					RegistrModel.this.callback(url, jo, status);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					responseStatus= STATUS.fromJson(jo.optJSONObject("status"));
					if (responseStatus.succeed==1) {
						RegistrModel.this.OnMessageResponse(url, jo, status);
					}else {
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		String url=ProtocolConst.FORGOTTEN_PWD;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("newpassword", newpassword);
		params.put("verCode", verCode);
		MyProgressDialog dialog = new MyProgressDialog(myContext, "请稍后....");
		cb.url(url).type(JSONObject.class).params(params)
				.method(Constants.METHOD_POST);
		aq.progress(dialog.mDialog).ajax(cb);
	}
	/**修改密码
	 * @return */
	public void Modify_PSW(String password,String newpassword,String repassword){
		
		BeeCallback<JSONObject> cb=new BeeCallback<JSONObject>(){
			@Override
			public void callback(String url, JSONObject jo,
					AjaxStatus status) {
				try {
					RegistrModel.this.callback(url, jo, status);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					responseStatus= STATUS.fromJson(jo.optJSONObject("status"));
					if (responseStatus.succeed==1) {
						RegistrModel.this.OnMessageResponse(url, jo, status);
					}else if (responseStatus.succeed==0) {
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		String url=ProtocolConst.CHANGE_PWD;
		SESSION session = SESSION.getInstance();
		Map<String, Object> params = new HashMap<String, Object>();
		JSONObject request = new JSONObject();
		try {
			request.put("session", session.toJson());
			request.put("oldpassword", password);
			request.put("newpassword", newpassword);
			request.put("repassword", repassword);
		} catch (Exception e) {	
		}
		params.put("json",request.toString());
		MyProgressDialog dialog = new MyProgressDialog(myContext, "请稍后....");
		cb.url(url).type(JSONObject.class).params(params)
				.method(Constants.METHOD_POST);
		aq.progress(dialog.mDialog).ajax(cb);
	}

}
