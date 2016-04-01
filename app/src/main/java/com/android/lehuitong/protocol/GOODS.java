package com.android.lehuitong.protocol;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcelable;
import android.widget.ImageView;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

public class GOODS implements Serializable{

	public String promote_end_date;

	public int click_count;

	public String goods_sn;

	public String promote_start_date;

	public String goods_number;

	public ArrayList<PRICE> rank_prices = new ArrayList<PRICE>();

	public String img;

	public String brand_id;

	public ArrayList<PHOTO> pictures = new ArrayList<PHOTO>();

	public String goods_name;

	public ArrayList<PROPERTY> properties = new ArrayList<PROPERTY>();

	public String goods_weight;

	public String promote_price;

	public String formated_promote_price;

	public String integral;

	public int id;

	public String cat_id;

	public String is_shipping;

	public String shop_price;

	public String market_price;

	public int collected;
	public String goods_id;
	public String address;
	public String area;
	public String email;
	public String cat_name;

	public String goods_desc;

	public String shipping_fee;

	public String order_minimum;

	public String tel;

	public String phone;

	public String shop_name;

	public String seller_id;

	public String goods_thumb;

	public String goods_brief;

	public String sales_volume;

	public String name;
	public String available_start_date;
	public String available_end_date;
	public String is_hot;
	public String is_new;
	public String is_haitao;
	public String is_promote;
	public PHOTO photo;
	public int canBuy;
	public SHOP_SELLER shop_seller;
	
	public ArrayList<SPECIFICATION> specification = new ArrayList<SPECIFICATION>();

	public static GOODS fromJson(JSONObject jsonObject) throws JSONException {
		if (null == jsonObject) {
			return null;
		}

		GOODS localItem = new GOODS();

		JSONArray subItemArray;

		localItem.promote_end_date = jsonObject.optString("promote_end_date");

		localItem.click_count = jsonObject.optInt("click_count");

		localItem.goods_sn = jsonObject.optString("goods_sn");

		localItem.promote_start_date = jsonObject.optString("promote_start_date");

		localItem.goods_number = jsonObject.optString("goods_number");
		
		localItem.available_start_date = jsonObject.optString("available_start_date");
		
		localItem.available_end_date = jsonObject.optString("available_end_date");
		localItem.is_haitao=jsonObject.optString("is_haitao");
		localItem.canBuy=jsonObject.optInt("can_buy");
		localItem.is_hot=jsonObject.optString("is_hot");
		localItem.is_new=jsonObject.optString("is_new");
		localItem.is_promote=jsonObject.optString("is_promote");
		subItemArray = jsonObject.optJSONArray("rank_prices");
		
		if (null != subItemArray) {
			for (int i = 0; i < subItemArray.length(); i++) {
				JSONObject subItemObject = subItemArray.getJSONObject(i);
				PRICE subItem = PRICE.fromJson(subItemObject);
				localItem.rank_prices.add(subItem);
			}
		}

		localItem.img = jsonObject.optString("img");

		localItem.photo = PHOTO.fromJson(jsonObject.optJSONObject("img"));

		localItem.brand_id = jsonObject.optString("brand_id");

		subItemArray = jsonObject.optJSONArray("pictures");
		if (null != subItemArray) {
			for (int i = 0; i < subItemArray.length(); i++) {
				JSONObject subItemObject = subItemArray.getJSONObject(i);
				PHOTO subItem = PHOTO.fromJson(subItemObject);
				localItem.pictures.add(subItem);
			}
		}

		localItem.goods_name = jsonObject.optString("goods_name");

		subItemArray = jsonObject.optJSONArray("properties");
		if (null != subItemArray) {
			for (int i = 0; i < subItemArray.length(); i++) {
				JSONObject subItemObject = subItemArray.getJSONObject(i);
				PROPERTY subItem = PROPERTY.fromJson(subItemObject);
				localItem.properties.add(subItem);
			}
		}
		//商家信息
		localItem.shop_seller=SHOP_SELLER.fromjson(jsonObject.optJSONObject("seller"));
		
		localItem.goods_weight = jsonObject.optString("goods_weight");

		localItem.promote_price = jsonObject.optString("promote_price");

		localItem.formated_promote_price = jsonObject.optString("formated_promote_price");

		localItem.integral = jsonObject.optString("integral");

		localItem.id = jsonObject.optInt("id");

		localItem.cat_id = jsonObject.optString("cat_id");

		localItem.is_shipping = jsonObject.optString("is_shipping");

		localItem.shop_price = jsonObject.optString("shop_price");

		localItem.market_price = jsonObject.optString("market_price");

		localItem.collected = jsonObject.optInt("collected");

		subItemArray = jsonObject.optJSONArray("specification");
		if (null != subItemArray) {
			for (int i = 0; i < subItemArray.length(); i++) {
				JSONObject subItemObject = subItemArray.getJSONObject(i);
				SPECIFICATION subItem = SPECIFICATION.fromJson(subItemObject);
				localItem.specification.add(subItem);
			}
		}
		localItem.shop_name = jsonObject.optString("shop_name");
		localItem.goods_name = jsonObject.optString("goods_name");
		localItem.goods_desc = jsonObject.optString("goods_desc");
		localItem.shipping_fee = jsonObject.optString("shipping_fee");
		localItem.order_minimum = jsonObject.optString("order_minimum");
		localItem.tel = jsonObject.optString("tel");
		localItem.phone = jsonObject.optString("phone");
		localItem.seller_id = jsonObject.optString("seller_id");
		localItem.goods_id = jsonObject.optString("goods_id");
		localItem.address = jsonObject.optString("address");
		localItem.area = jsonObject.optString("area");
		localItem.email = jsonObject.optString("email");
		localItem.cat_name = jsonObject.optString("cat_name");
		localItem.goods_thumb = jsonObject.optString("goods_thumb");
		localItem.goods_brief = jsonObject.optString("goods_brief");
		localItem.sales_volume = jsonObject.optString("sales_volume");
		localItem.name = jsonObject.optString("name");

		return localItem;
	}

	public JSONObject toJson() throws JSONException {
		JSONObject localItemObject = new JSONObject();
		JSONArray itemJSONArray = new JSONArray();
		localItemObject.put("promote_end_date", promote_end_date);
		localItemObject.put("click_count", click_count);
		localItemObject.put("goods_sn", goods_sn);
		localItemObject.put("promote_start_date", promote_start_date);
		localItemObject.put("goods_number", goods_number);

		for (int i = 0; i < rank_prices.size(); i++) {
			PRICE itemData = rank_prices.get(i);
			JSONObject itemJSONObject = itemData.toJson();
			itemJSONArray.put(itemJSONObject);
		}
		localItemObject.put("rank_prices", itemJSONArray);
		if (null != img) {
			localItemObject.put("img", img);
		}
		localItemObject.put("brand_id", brand_id);

		for (int i = 0; i < pictures.size(); i++) {
			PHOTO itemData = pictures.get(i);
			JSONObject itemJSONObject = itemData.toJson();
			itemJSONArray.put(itemJSONObject);
		}
		localItemObject.put("pictures", itemJSONArray);
		localItemObject.put("goods_name", goods_name);

		for (int i = 0; i < properties.size(); i++) {
			PROPERTY itemData = properties.get(i);
			JSONObject itemJSONObject = itemData.toJson();
			itemJSONArray.put(itemJSONObject);
		}
		localItemObject.put("properties", itemJSONArray);
		localItemObject.put("goods_weight", goods_weight);
		localItemObject.put("promote_price", promote_price);
		localItemObject.put("formated_promote_price", formated_promote_price);
		localItemObject.put("integral", integral);
		localItemObject.put("id", id);
		localItemObject.put("cat_id", cat_id);
		localItemObject.put("is_shipping", is_shipping);
		localItemObject.put("shop_price", shop_price);
		localItemObject.put("market_price", market_price);
		localItemObject.put("collected", collected);

		for (int i = 0; i < specification.size(); i++) {
			SPECIFICATION itemData = specification.get(i);
			JSONObject itemJSONObject = itemData.toJson();
			itemJSONArray.put(itemJSONObject);
		}
		localItemObject.put("specification", itemJSONArray);

		localItemObject.put("shop_name", shop_name);
		localItemObject.put("goods_desc", goods_desc);
		localItemObject.put("shipping_fee", shipping_fee);
		localItemObject.put("order_minimum", order_minimum);
		localItemObject.put("tel", tel);
		localItemObject.put("phone", phone);
		localItemObject.put("seller_id", seller_id);
		localItemObject.put("goods_id", goods_id);
		localItemObject.put("address", address);
		localItemObject.put("area", area);
		localItemObject.put("email", email);
		localItemObject.put("cat_name", cat_name);
		localItemObject.put("goods_thumb", goods_thumb);
		localItemObject.put("goods_brief", goods_brief);
		localItemObject.put("sales_volume", sales_volume);
		localItemObject.put("name", name);
		localItemObject.put("is_hot", is_hot);
		localItemObject.put("is_new", is_new);
		localItemObject.put("is_haitao", is_haitao);
		localItemObject.put("is_promote", is_promote);
		localItemObject.put("can_buy", canBuy);
		return localItemObject;
	}

}
