package com.android.lehuitong.model;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SESSION;
import com.external.androidquery.callback.AjaxStatus;

public class PayModel extends BaseModel {

	public PayModel(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
		public void callback(String url, JSONObject jo, AjaxStatus status) {
			try {
				PayModel.this.callback(url, jo, status);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		};

	};

	/**
	 * 支付成功调用接口
	 * 
	 * @param order_sn
	 *            订单号
	 * 
	 * */

	public void paydone(String order_sn) {
		String url = ProtocolConst.FLOW_PAYDONE;
		SESSION session = SESSION.getInstance();
		JSONObject requestJsonObject = new JSONObject();
		Map<String, String> params = new HashMap<String, String>();
		try {
			requestJsonObject.put("session", session.toJson());
			requestJsonObject.put("order_sn", order_sn);
		} catch (JSONException e) {
		}
		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}
}
