
package com.android.lehuitong.protocol;
import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "SIMPLEGOODS")
public class SIMPLEGOODS  extends Model implements Serializable
{

     @Column(name = "SIMPLEGOODS_id",unique = true)
     public int id;

     @Column(name = "shop_price")
     public String shop_price;

     @Column(name = "market_price")
     public String market_price;

     @Column(name = "name")
     public String name;

     @Column(name = "goods_id")
     public int goods_id;

     @Column(name = "img")
     public PHOTO   img;

     @Column(name = "brief")
     public String brief;

     @Column(name = "promote_price")
     public String promote_price;
     
     @Column(name = "location")
     public String location;
     @Column(name = "purchase_id")
     public int purchase_id;
     
     @Column(name = "phone")
     public String phone;
     
     @Column(name = "num")
     public  String num;
     
     @Column(name="create_time")
     public String create_time;
     
     @Column(name="max_price")
     public String max_price;
     
     public String cat_id;
     
     public String parent_id;
     
     public String promote_start_date;
     
     public String promote_end_date;
     public String sales_volume;
     public String is_hot;
     public String is_haitao;
     public String is_new;
     public String is_promote;
     public int can_buy;
     public String goods_brief;
     @Column(name="promote_valume")
     public String promote_valume;
 public static SIMPLEGOODS fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     SIMPLEGOODS   localItem = new SIMPLEGOODS();

     JSONArray subItemArray;
     localItem.goods_brief = jsonObject.optString("goods_brief");
     localItem.id = jsonObject.optInt("id");
     localItem.purchase_id = jsonObject.optInt("purchase_id");
     

     localItem.shop_price = jsonObject.optString("shop_price");

     localItem.market_price = jsonObject.optString("market_price");

     localItem.name = jsonObject.optString("name");

     localItem.goods_id = jsonObject.optInt("goods_id");
     
     localItem.img = PHOTO.fromJson(jsonObject.optJSONObject("img"));

     localItem.brief = jsonObject.optString("brief");

     localItem.promote_price = jsonObject.optString("promote_price");
     
     localItem.location = jsonObject.optString("location");
     
     localItem.phone = jsonObject.optString("phone");
     
     localItem.num=jsonObject.optString("num");
     
     localItem.create_time=jsonObject.optString("create_time");
     
     localItem.max_price=jsonObject.optString("max_price");
     
     localItem.cat_id=jsonObject.optString("cat_id");
     
     localItem.parent_id=jsonObject.optString("parent_id");
     
     localItem.promote_start_date=jsonObject.optString("promote_start_date");
     
     localItem.promote_end_date=jsonObject.optString("promote_end_date");
     localItem.sales_volume=jsonObject.optString("sales_volume");
     localItem.is_haitao=jsonObject.optString("is_haitao");
     localItem.is_new=jsonObject.optString("is_new");
     localItem.is_hot=jsonObject.optString("is_hot");
     localItem.is_promote=jsonObject.optString("is_promote");
     localItem.promote_valume=jsonObject.optString("sales_volume");
     localItem.can_buy=jsonObject.optInt("can_buy");
     
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     
     localItemObject.put("id", id);
     localItemObject.put("shop_price", shop_price);
     localItemObject.put("market_price", market_price);
     localItemObject.put("name", name);
     localItemObject.put("goods_id", goods_id);    
     localItemObject.put("brief", brief);
     localItemObject.put("promote_price", promote_price);
     localItemObject.put("location", location);
     localItemObject.put("purchase_id", purchase_id);
     localItemObject.put("phone", phone);
     localItemObject.put("num",num);
     localItemObject.put("create_time",create_time);
     localItemObject.put("max_price", max_price);
     localItemObject.put("cat_id", cat_id);
     localItemObject.put("parent_id", parent_id);
     localItemObject.put("is_haitao", is_haitao);
     localItemObject.put("is_new", is_new);
     localItemObject.put("is_hot", is_hot);
     localItemObject.put("is_promote", is_promote);
     localItemObject.put("promote_valume", promote_valume);
     if(null!=img)
     {
    	 localItemObject.put("img", img.toJson());
     }

     return localItemObject;
 }

}
