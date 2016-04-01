
package com.android.lehuitong.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "FILTER")
public class FILTER  extends Model
{

     @Column(name = "keywords")
     public String keywords;

     @Column(name = "sort_by")
     public String sort_by;

     @Column(name = "brand_id")
     public String brand_id;

     @Column(name = "category_id")
     public String category_id;

     @Column(name = "price_range")
     public PRICE_RANGE price_range;
     
     @Column(name = "location")
     public LOCATION location;
     
     @Column(name = "address")
     public String address;
     
     @Column(name="seller_id")
     public String seller_id;
     public String count;
     public String page;

 public static FILTER fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     FILTER   localItem = new FILTER();

     JSONArray subItemArray;

     localItem.keywords = jsonObject.optString("keywords");

     localItem.sort_by = jsonObject.optString("sort_by");

     localItem.brand_id = jsonObject.optString("brand_id");

     localItem.category_id = jsonObject.optString("category_id");
     
     localItem.address = jsonObject.optString("address");
     
     localItem.seller_id = jsonObject.optString("seller_id");
     
     localItem.price_range = PRICE_RANGE.fromJson(jsonObject.optJSONObject("price_range"));
     
     localItem.location = LOCATION.fromJson(jsonObject.optJSONObject("location"));
     localItem.count = jsonObject.optString("count");
     localItem.page = jsonObject.optString("page");
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("keywords",keywords);
     localItemObject.put("sort_by", sort_by);
     localItemObject.put("brand_id", brand_id);
     localItemObject.put("category_id", category_id);
     localItemObject.put("address", address);
     localItemObject.put("seller_id", seller_id);
     localItemObject.put("count", count);
     localItemObject.put("page", page);
     if (null != price_range)
     {
         localItemObject.put("price_range",price_range.toJson());
     }
     
     if (null != location)
     {
         localItemObject.put("location",location.toJson());
     }

     return localItemObject;
 }

}
