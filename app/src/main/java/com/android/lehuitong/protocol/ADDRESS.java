
package com.android.lehuitong.protocol;
import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;
/**
 * 地址
 * @author shenlw
 *
 */

public class ADDRESS  extends Model implements Serializable
{
     public int default_address;

     public String sign_building;

     public String city_name;

     public String consignee;


     public String tel;

     public String zipcode;

     public String country_name;

     public String country;

     public String city;

     public String id;

     public String province_name;

     public String district_name;

     public String email;

     public String address;

     public String province;


     public String district;


     public String best_time;

     public String mobile;

     public String address_id; 
 public static ADDRESS fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     ADDRESS   localItem = new ADDRESS();

     JSONArray subItemArray;

     localItem.default_address = jsonObject.optInt("default_address");

     localItem.sign_building = jsonObject.optString("sign_building");

     localItem.city_name = jsonObject.optString("city_name");

     localItem.consignee = jsonObject.optString("consignee");

     localItem.tel = jsonObject.optString("tel");

     localItem.zipcode = jsonObject.optString("zipcode");

     localItem.country_name = jsonObject.optString("country_name");

     localItem.country = jsonObject.optString("country");

     localItem.city = jsonObject.optString("city");

     localItem.id = jsonObject.optString("id");

     localItem.province_name = jsonObject.optString("province_name");

     localItem.district_name = jsonObject.optString("district_name");

     localItem.email = jsonObject.optString("email");

     localItem.address = jsonObject.optString("address");

     localItem.province = jsonObject.optString("province");

     localItem.district = jsonObject.optString("district");

     localItem.best_time = jsonObject.optString("best_time");
     
     localItem.mobile = jsonObject.optString("mobile");
     
     localItem.address_id =jsonObject.optString("address_id");
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("default_address", default_address);
     localItemObject.put("sign_building", sign_building);
     localItemObject.put("city_name", city_name);
     localItemObject.put("consignee", consignee);
     localItemObject.put("tel", tel);
     localItemObject.put("zipcode", zipcode);
     localItemObject.put("country_name", country_name);
     localItemObject.put("country", country);
     localItemObject.put("city", city);
     localItemObject.put("id", id);
     localItemObject.put("province_name", province_name);
     localItemObject.put("district_name", district_name);
     localItemObject.put("email", email);
     localItemObject.put("address", address);
     localItemObject.put("province", province);
     localItemObject.put("district", district);
     localItemObject.put("best_time", best_time);
     localItemObject.put("mobile", mobile);
     return localItemObject;
 }

}
