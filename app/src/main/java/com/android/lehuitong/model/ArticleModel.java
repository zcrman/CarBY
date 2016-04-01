package com.android.lehuitong.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.Editable;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.lehuitong.protocol.ARTICLE;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SESSION;
import com.android.lehuitong.protocol.STATUS;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

/** 积分问答相关model */
public class ArticleModel extends BaseModel {

	public Context context;
	public ARTICLE article;
	public ArrayList<ARTICLE> articleArray = new ArrayList<ARTICLE>();

	public ArticleModel(Context context) {
		super(context);
		this.context = context;

	}

	public void getarticle() {
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					ArticleModel.this.callback(url, jo, status);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					STATUS mStatus = STATUS
							.fromJson(jo.optJSONObject("status"));
					JSONArray data = jo.optJSONArray("data");
					if (mStatus.succeed == 1) {
						for (int i = 0; i < data.length(); i++) {
							JSONObject jsonObject = data.getJSONObject(i);
							article = ARTICLE.fromJson(jsonObject);
							articleArray.add(article);
						}

						ArticleModel.this.OnMessageResponse(url, jo, status);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};
		String url = ProtocolConst.ARTICLE;
		cb.url(url).type(JSONObject.class).method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);

	}

	public void feedback(String suggestion) {
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					ArticleModel.this.callback(url, jo, status);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					STATUS status2 = STATUS
							.fromJson(jo.optJSONObject("status"));
					if (status2.succeed == 1) {
						ArticleModel.this.OnMessageResponse(url, jo, status);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};

		};
		String url = ProtocolConst.ADD_SUGGESTION;
		SESSION session = SESSION.getInstance();
		JSONObject jsonObject = new JSONObject();
		Map<String, String> parms = new HashMap<String, String>();
		try {
			jsonObject.put("session", session.toJson());
			jsonObject.put("content", suggestion);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parms.put("json", jsonObject.toString());
		cb.url(url).params(parms).type(JSONObject.class).method(Constants.METHOD_POST);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);
	}
}