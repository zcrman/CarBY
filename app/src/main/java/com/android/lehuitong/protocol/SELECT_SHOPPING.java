package com.android.lehuitong.protocol;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.external.activeandroid.Model;
/**快递接口数据返回处理Model*/
public class SELECT_SHOPPING extends Model implements Serializable{
	public double formated_goods_price;//商品价格
	public double pay_fee_formated;//运费
	public double amount_formated;//总价
	public double bonus_formated;//红包价格
	public ADDRESS address;
	public TOTALS totals;
	public boolean flag = true;
	public static SELECT_SHOPPING  fromJson(JSONObject jsonObject)  throws JSONException{
		
		 if(null == jsonObject){
		       return null;
		      }
		 SELECT_SHOPPING shopping=new SELECT_SHOPPING();
		 shopping.formated_goods_price=jsonObject.optDouble("formated_goods_price");
		 shopping.pay_fee_formated=jsonObject.optDouble("pay_fee_formated");
		 shopping.amount_formated=jsonObject.optDouble("amount_formated");
		 shopping.bonus_formated=jsonObject.optDouble("bonus_formated");
		 if (jsonObject.optJSONObject("consignee")!=null) {
			
			 shopping.address=ADDRESS.fromJson(jsonObject.optJSONObject("consignee"));
		}
		 if(jsonObject.optString("consignee") != null){
			 shopping.flag = jsonObject.optBoolean("consignee");
		 }else{
			 shopping.flag = true;
		 }
		 
		 if (jsonObject.optJSONObject("total")!=null) {
			 
			 shopping.totals=TOTALS.fromJson(jsonObject.optJSONObject("total"));
		 }
		return shopping;
		
	} 
}
