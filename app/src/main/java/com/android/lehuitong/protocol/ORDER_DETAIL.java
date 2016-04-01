package com.android.lehuitong.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.external.activeandroid.Model;

public class ORDER_DETAIL extends Model{
	public String order_status;//订单状态 
	public String pay_status;//付款状态
	public String order_time;//下单时间
	public String formated_shipping_fee;//运费
	public String formated_total_fee;//总价
    public String formated_bonus;//红包
    public String order_sn;//订单SN
    public String order_id;//订单id
	public ADDRESS address;	
	public BONUS bonus;
	public PAYMENT payment;
	public SHIPPING shipping;
	public String goods_amount_fee;
	public ArrayList<ORDER_GOODS_LIST> goods_list = new ArrayList<ORDER_GOODS_LIST>();
	public static ORDER_DETAIL fromJson(JSONObject jsonObject)  throws JSONException
	 {
		if(null == jsonObject){
		       return null;
		      }
		 JSONArray subItemArray;
		ORDER_DETAIL detail=new ORDER_DETAIL();
		detail.address=ADDRESS.fromJson(jsonObject.optJSONObject("address"));
		detail.payment=PAYMENT.fromJson(jsonObject.optJSONObject("payment"));
		detail.shipping=SHIPPING.fromJson(jsonObject.optJSONObject("shipping"));
		if (jsonObject.optJSONObject("bonus")!=null) {
			detail.bonus=BONUS.fromJson(jsonObject.optJSONObject("bonus"));		
		}
		detail.order_status= jsonObject.optString("order_status");
		detail.pay_status= jsonObject.optString("pay_status");
		detail.order_time= jsonObject.optString("order_time");
		detail.formated_shipping_fee=jsonObject.optString("formated_shipping_fee");
		detail.formated_total_fee=jsonObject.optString("formated_total_fee");
		detail.formated_bonus=jsonObject.optString("formated_bonus");
		detail.order_id=jsonObject.optString("order_id");
		detail.order_sn=jsonObject.optString("order_sn");
		detail.goods_amount_fee = jsonObject.optString("goods_amount_fee");
		 subItemArray = jsonObject.optJSONArray("goods_list");
		
	     if(null != subItemArray&&subItemArray.length()>0)
	      {
	         for(int i = 0;i < subItemArray.length();i++)
	          {
	             JSONObject subItemObject = subItemArray.getJSONObject(i);
	             ORDER_GOODS_LIST subItem = ORDER_GOODS_LIST.fromJson(subItemObject);
	             detail.goods_list.add(subItem);
	         }
	     }
		return detail;
		
	 }
}
