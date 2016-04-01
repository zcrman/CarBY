package com.android.lehuitong.protocol;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

//购物车id
public class REC_IDS {
	
	public JSONObject toJson(ArrayList<Integer> good_id) throws JSONException{
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < good_id.size(); i++) {
			jsonObject.put(i+"", good_id.get(i));
		}
		return jsonObject;
	}
	public JSONObject toJson(ArrayList<Integer> good_id,ArrayList<Integer> number_change)throws JSONException{
		JSONObject jsonObject=new  JSONObject();
		for (int i = 0; i < good_id.size(); i++) {
			jsonObject.put(good_id.get(i)+"", number_change.get(i));
		}
		return jsonObject;
		
	}
}
