package com.android.lehuitong.protocol;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.widget.AbsListView.SelectionBoundsAdjuster;

import com.external.activeandroid.Model;

public class TOTALS extends Model implements Serializable{
	public double formated_goods_price;//商品价格
	public double pay_fee_formated;//运费
	public double amount_formated;//总价
	public double bonus_formated;//红包价格
	public static TOTALS  fromJson(JSONObject jsonObject)  throws JSONException{
		 if(null == jsonObject){
		       return null;
		      }
		 TOTALS shopping=new TOTALS();
		 shopping.formated_goods_price=jsonObject.getDouble("formated_goods_price");
		 shopping.pay_fee_formated=jsonObject.getDouble("pay_fee_formated");
		 shopping.amount_formated=jsonObject.getDouble("amount_formated");
		 shopping.bonus_formated=jsonObject.getDouble("bonus_formated");
		 return shopping;
}
}
