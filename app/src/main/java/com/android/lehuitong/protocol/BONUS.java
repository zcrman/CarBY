package com.android.lehuitong.protocol;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.external.activeandroid.Model;

public class BONUS extends Model implements Serializable{
	
	public String type_id;
	public String type_name;
	public String type_money;
	public String bonus_id;
	public String min_goods_amount;
	public String use_start_date;
	public String use_end_date;
	public String seller_id;
	public SELLER seller;
	public String bonus_money_formated;
	public String can_order;
	public static BONUS fromJson (JSONObject jsonObject) throws JSONException{
		BONUS bonus=new BONUS();
		bonus.type_id=jsonObject.optString("type_id");
		bonus.type_money=jsonObject.optString("type_money");
		if (jsonObject.optString("type_name")!=null) {
			bonus.type_name=jsonObject.optString("type_name");
		}
		bonus.bonus_id=jsonObject.optString("bonus_id");
		bonus.min_goods_amount=jsonObject.optString("min_goods_amount");
		bonus.use_start_date=jsonObject.optString("use_start_date");
		bonus.use_end_date=jsonObject.optString("use_end_date");
		bonus.seller_id=jsonObject.optString("seller_id");
		bonus.can_order = jsonObject.optString("can_order");
		bonus.bonus_money_formated=jsonObject.optString("bonus_money_formated");
		if (jsonObject.optJSONObject("seller") != null) {
			bonus.seller = SELLER.fromJson(jsonObject.optJSONObject("seller"));
		}
		return bonus;
		
	}

}
