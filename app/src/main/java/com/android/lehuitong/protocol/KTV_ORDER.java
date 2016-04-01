package com.android.lehuitong.protocol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class KTV_ORDER implements Serializable{

	public String consignee;
	public String user_phone;
	public ArrayList<PAYMENT> paymentList = new ArrayList<PAYMENT>();
	public ArrayList<BONUS> bonuslist = new ArrayList<BONUS>();
	public GOOD good;
	public SELLER seller;
	public String order_id;
	public String good_id;
	public String effective_time_start;
	public String effective_time_end;
	public String order_status;
	public String order_sn;
	public String verify_code;
	public String appointment_time;
	public String seller_name;
	public String money;
	public String good_num;
	public String shop_address;
	public String shop_phone;
	public String is_verified_ticket;
	public int allow_use_bonus;
	public String box_type;
	public int cat_id;
	public String order_type;
	public String bonus_name;
	public String add_time;
	public String shop_pic_url;
	public static KTV_ORDER fromJson(JSONObject jsonObject) throws JSONException {
		if (jsonObject == null) {
			return null;
		}
		KTV_ORDER ktvOrder = new KTV_ORDER();
		ktvOrder.consignee = jsonObject.optString("consignee");
		ktvOrder.user_phone = jsonObject.optString("user_phone");
		ktvOrder.order_id = jsonObject.optString("order_id");
		ktvOrder.good_id = jsonObject.optString("good_id");
		ktvOrder.effective_time_start = jsonObject.optString("effective_time_start");
		ktvOrder.effective_time_end = jsonObject.optString("effective_time_end");
		ktvOrder.order_status = jsonObject.optString("order_status");
		ktvOrder.order_sn = jsonObject.optString("order_sn");
		ktvOrder.verify_code = jsonObject.optString("verify_code");
		ktvOrder.appointment_time = jsonObject.optString("appointment_time");
		ktvOrder.seller_name = jsonObject.optString("seller_name");
		ktvOrder.money=jsonObject.optString("money");
		ktvOrder.good_num=jsonObject.optString("good_num");
		ktvOrder.shop_address=jsonObject.optString("shop_address");
		ktvOrder.shop_phone=jsonObject.optString("shop_phone");
		ktvOrder.is_verified_ticket=jsonObject.optString("is_verified_ticket");
		ktvOrder.allow_use_bonus=jsonObject.optInt("allow_use_bonus");
		ktvOrder.cat_id=jsonObject.optInt("cat_id");
		ktvOrder.box_type=jsonObject.optString("type");
		ktvOrder.order_type = jsonObject.optString("order_type");
		ktvOrder.add_time = jsonObject.optString("add_time");
		ktvOrder.shop_pic_url = jsonObject.optString("shop_pic_url");
		if (jsonObject.optString("bonus_name") != null) {
		ktvOrder.bonus_name = jsonObject.optString("bonus_name");
		}
		if (jsonObject.optJSONObject("good") != null) {
			ktvOrder.good = GOOD.fromJson(jsonObject.optJSONObject("good"));

		}
		
		
		if (jsonObject.optJSONObject("seller") != null) {
			ktvOrder.seller = SELLER.fromJson(jsonObject.optJSONObject("seller"));
		}
		JSONArray jsonArray;

		jsonArray = jsonObject.optJSONArray("payment_list");
		if (jsonArray!=null) {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject itemJsonObject = jsonArray.getJSONObject(i);
				PAYMENT payment = PAYMENT.fromJson(itemJsonObject);
				ktvOrder.paymentList.add(payment);
			}
		}
		jsonArray =jsonObject.optJSONArray("bonus");
		if (jsonArray!=null) {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject itemJsonObject = jsonArray.getJSONObject(i);
				BONUS bonus = BONUS.fromJson(itemJsonObject);
				ktvOrder.bonuslist.add(bonus);
			}
		}

		return ktvOrder;

	}

	

}
