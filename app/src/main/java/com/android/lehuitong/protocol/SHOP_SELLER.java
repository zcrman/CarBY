package com.android.lehuitong.protocol;

import java.io.Serializable;

import org.json.JSONObject;

import com.external.activeandroid.Model;

public class SHOP_SELLER extends Model implements Serializable{
	public String shop_id;
	public String shop_name;
	public String shop_address;
	public String shop_phone;
	public String shop_lng;
	public String shop_lat;
	public String shop_brief;
	public String shop_order_num;
	
	public static SHOP_SELLER fromjson (JSONObject jsonObject){
		if (jsonObject==null) {
			return null;
			
			
		}
		SHOP_SELLER localItem=new SHOP_SELLER();
		localItem.shop_id=jsonObject.optString("shop_id");
		localItem.shop_name=jsonObject.optString("shop_name");
		localItem.shop_address=jsonObject.optString("shop_address");
		localItem.shop_phone=jsonObject.optString("shop_phone");
		localItem.shop_lng=jsonObject.optString("shop_lng");
		localItem.shop_lat=jsonObject.optString("shop_lat");
		localItem.shop_brief=jsonObject.optString("shop_brief");
		localItem.shop_order_num=jsonObject.optString("shop_order_num");
		return localItem;
		
	}
	

}
