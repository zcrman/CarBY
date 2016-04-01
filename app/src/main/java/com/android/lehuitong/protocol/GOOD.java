package com.android.lehuitong.protocol;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class GOOD  implements Serializable{

	public String good_name;
	public String good_id;
	public String good_price;
	public String good_brief;
	public String good_thumb;
	public String goods_name;
	public String goods_id;
	public String goods_price;
	public String goods_brief;
	public String goods_thumb;
	public String goods_shop_price;
	public static GOOD fromJson(JSONObject jsonObject) {
		GOOD good = new GOOD();
		good.good_name = jsonObject.optString("good_name");
		good.good_id = jsonObject.optString("good_id");
		good.good_price = jsonObject.optString("good_price");
		good.good_brief = jsonObject.optString("good_brief");
		good.good_thumb = jsonObject.optString("good_thumb");
		good.goods_name = jsonObject.optString("goods_name");
		good.goods_id = jsonObject.optString("goods_id");
		good.goods_price = jsonObject.optString("goods_price");
		good.goods_brief = jsonObject.optString("goods_brief");
		good.goods_thumb = jsonObject.optString("goods_thumb");
		good.goods_shop_price = jsonObject.optString("shop_price");
		return good;

	}

	public JSONObject toJson() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("good_name", good_name);
		jsonObject.put("good_id", good_id);
		jsonObject.put("good_price", good_price);
		jsonObject.put("good_brief", good_brief);
		jsonObject.put("good_thumb", good_thumb);
		jsonObject.put("goods_name", goods_name);
		jsonObject.put("goods_id", goods_id);
		jsonObject.put("goods_price", goods_price);
		jsonObject.put("goods_brief", goods_brief);
		jsonObject.put("goods_thumb", goods_thumb);
		jsonObject.put("goods_shop_price", goods_shop_price);
		return jsonObject;

	}

}
