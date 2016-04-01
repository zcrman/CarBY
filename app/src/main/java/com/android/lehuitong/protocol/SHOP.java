package com.android.lehuitong.protocol;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SHOP implements Serializable{

	public String shop_id;
	public String shop_name;
	public String shop_address;
	public String shop_phone;
	public String shop_lng;
	public String shop_lat;
	public String shop_order_num;
	public String shop_brief;
	public String shop_pic_url;
	public String shop_distance;
	public String shop_logo;
	public int can_order;
	public String shop_brief_name;
	public ArrayList<GOODS> goods = new ArrayList<GOODS>();

	public static SHOP fromJson(JSONObject jsonObject) throws JSONException {

		SHOP localItem = new SHOP();
		JSONArray subItemArray;

		localItem.shop_id = jsonObject.optString("shop_id");
		localItem.shop_name = jsonObject.optString("shop_name");
		localItem.shop_brief_name = jsonObject.optString("shop_brief_name");
		localItem.shop_address = jsonObject.optString("shop_address");
		localItem.shop_phone = jsonObject.optString("shop_phone");
		localItem.shop_lng = jsonObject.optString("shopp_lng");
		localItem.shop_lat = jsonObject.optString("sho_lat");
		localItem.shop_order_num = jsonObject.optString("shop_order_num");
		localItem.shop_brief = jsonObject.optString("shop_brief");
		localItem.shop_pic_url = jsonObject.optString("shop_pic_url");
		localItem.shop_distance=jsonObject.optString("distance");
		localItem.shop_logo=jsonObject.optString("shop_logo");
		localItem.can_order=jsonObject.optInt("can_order");
		subItemArray = jsonObject.optJSONArray("goods");
		
		if (null != subItemArray) {
			for (int i = 0; i < subItemArray.length(); i++) {
				JSONObject subItemObject = subItemArray.getJSONObject(i);
				GOODS subItem = GOODS.fromJson(subItemObject);
				localItem.goods.add(subItem);
			}
		}

		return localItem;

	}

	public JSONObject toJson() throws JSONException {

		JSONObject localItemObject = new JSONObject();
		JSONArray itemJSONArray = new JSONArray();
		localItemObject.put("shop_id", shop_id);
		localItemObject.put("shop_name", shop_name);
		localItemObject.put("shop_address", shop_address);
		localItemObject.put("shop_phone", shop_phone);
		localItemObject.put("shop_lng", shop_lng);
		localItemObject.put("shop_lat", shop_lat);
		localItemObject.put("shop_order_num", shop_order_num);
		localItemObject.put("shop_brief", shop_brief);
		localItemObject.put("shop_pic_url", shop_pic_url);
		localItemObject.put("shop_distance", shop_distance);
		localItemObject.put("shop_logo", shop_logo);
		localItemObject.put("can_order", can_order);
		for (int i = 0; i < goods.size(); i++) {
			GOODS itemData = goods.get(i);
			JSONObject itemJSONObject = itemData.toJson();
			itemJSONArray.put(itemJSONObject);
		}
		localItemObject.put("goods", itemJSONArray);

		return localItemObject;

	}

}
