package com.android.lehuitong.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.android.lehuitong.protocol.BONUS;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SESSION;
import com.android.lehuitong.protocol.STATUS;
import com.external.androidquery.callback.AjaxStatus;
/**红包model*/
public class BonusModel extends BaseModel{
	BONUS bonus;
	public ArrayList<BONUS> bonusArray = new ArrayList<BONUS>();
	public BonusModel(Context context) {
		super(context);
	}
	/**红包*/
 public void Bonus(){
	 String url=ProtocolConst.BONUS;
	 BeeCallback<JSONObject> cb=new BeeCallback<JSONObject>(){
		 @Override
		public void callback(String url, JSONObject jo, AjaxStatus status) {
			 try {
				 BonusModel.this.callback(url, jo, status);
			} catch (Exception e) {
			}
			 try {
				STATUS mStatus = STATUS
							.fromJson(jo.optJSONObject("status"));
				JSONArray data = jo.optJSONArray("data");
				if (mStatus.succeed == 1) {
					for (int i = 0; i < data.length(); i++) {
						JSONObject jsonObject = data.getJSONObject(i);
						bonus = BONUS.fromJson(jsonObject);
						bonusArray.add(bonus);
					}
					BonusModel.this.OnMessageResponse(url, jo, status);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 };
	 SESSION session=SESSION.getInstance();
	 JSONObject requestJsonObject = new JSONObject();
	 Map<String, String> params = new HashMap<String, String>();
		try {
			requestJsonObject.put("session", session.toJson());
		} catch (JSONException e) {
		}
		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	 
 }
	
	
	
}
