package com.android.lehuitong.protocol;

import java.util.jar.JarException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.external.activeandroid.Model;

/**
 * 积分问答
 * */
@SuppressWarnings("unused")
public class ARTICLE extends Model {
	public int id;
	public String title;
	public String content;

	public static ARTICLE fromJson(JSONObject jsonObject) throws JSONException {

		if (jsonObject == null) {
			return null;
		}
		ARTICLE localItem = new ARTICLE();
		JSONArray subItemArray;
		localItem.id = jsonObject.optInt("id");
		localItem.title = jsonObject.optString("title");
		localItem.content = jsonObject.optString("content");
		return localItem;
	}

	public JSONObject toJson() throws JSONException {

		JSONObject localItemObject = new JSONObject();
		JSONArray subItemArray = new JSONArray();
		localItemObject.put("id", id);
		localItemObject.put("title", title);
		localItemObject.put("content", content);
		return localItemObject;
	}
}
