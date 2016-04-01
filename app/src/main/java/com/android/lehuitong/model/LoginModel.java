package com.android.lehuitong.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
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
/**登陆Model*/
public class LoginModel extends BaseModel {
	private Context myContext;
	SharedPreferences sp ;
	Editor edit ;
	
	public  USER user;
	public LoginModel(Context context) {
		super(context);
		myContext = context;
	}
	String url = ProtocolConst.LOGIN;
	BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
		@Override
		public void callback(String url, JSONObject jo, AjaxStatus status) {
			 sp =myContext.getSharedPreferences("user", 0);
			 edit = sp.edit();
			try {
				LoginModel.this.callback(url, jo, status);
				JSONObject data = jo.optJSONObject("data");
				STATUS mStatus = STATUS.fromJson(jo.optJSONObject("status"));
				if (mStatus.succeed == 1) {
					SESSION session = SESSION.fromJson(data
							.optJSONObject("session"));
					SESSION se = SESSION.getInstance();
					user = USER.fromJson(data.optJSONObject("user"));
					edit.putString("name", user.name);
					edit.putString("sid", session.sid);
					edit.putString("uid", session.uid);
					edit.commit();
					LoginModel.this.OnMessageResponse(url, jo, status);
					Toast.makeText(mContext, "登录成功", Toast.LENGTH_LONG).show();
					CidModel cidModel=new CidModel(myContext);
					cidModel.sendCid();
				} else if (mStatus.succeed == 0){
					Toast.makeText(mContext, "用户名或密码错误",
							Toast.LENGTH_LONG).show();
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
	public void login(String tel, String pwd) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", tel);
		params.put("password", pwd);
		cb.url(url).params(params).type(JSONObject.class)
				.method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(myContext, "登录中....");
		aq.progress(dialog.mDialog).ajax(cb);
		
	}
	
}
