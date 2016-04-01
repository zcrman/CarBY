package com.android.lehuitong.protocol;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class SELLER implements Serializable{

	public String seller_name;
	public String seller_pic;
	public String seller_id;
	public String seller_address;
	public String seller_phone;
	public String shop_name;
	public String shop_pic_url;
	public String shop_address;
	public String shop_number;
	public String can_order;
	public static SELLER fromJson(JSONObject jsonObject) throws JSONException{
		
		 if(null == jsonObject){
		       return null;
		      }
		 SELLER localItem = new SELLER();
		 
		 localItem.seller_name = jsonObject.optString("seller_name");
		 localItem.seller_pic = jsonObject.optString("seller_pic");
		 localItem.seller_id = jsonObject.optString("seller_id");
		 localItem.seller_address = jsonObject.optString("seller_address");
		 localItem.seller_phone = jsonObject.optString("seller_phone");
		 localItem.shop_name = jsonObject.optString("shop_name");
		 localItem.shop_pic_url = jsonObject.optString("shop_pic_url");
		 localItem.shop_address = jsonObject.optString("shop_address");
		 localItem.shop_number = jsonObject.optString("shop_phone");
		 localItem.can_order = jsonObject.optString("can_order");
		 return localItem;

	}
	
	public JSONObject toJson() throws JSONException{
		
		JSONObject localJsonObject = new JSONObject();
		
		localJsonObject.put("seller_name",seller_name);
		localJsonObject.put("seller_pic",seller_pic);
		localJsonObject.put("seller_id",seller_id);
		localJsonObject.put("seller_address",seller_address);
		localJsonObject.put("seller_phone",seller_phone);
		localJsonObject.put("shop_pic_url", shop_pic_url);
		localJsonObject.put("shop_phone", shop_number);
		localJsonObject.put("shop_address", shop_address);
		return localJsonObject;
		
		
	}
	
	
	
}
