
package com.android.lehuitong.protocol;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;
import com.igexin.sdk.PushManager;

@Table(name = "USER")
public class USER  extends Model
{

     @Column(name = "collection_num")
     public String collection_num;

     @Column(name = "USER_id",unique = true)
     public String id;

     @Column(name = "order_num")
     public ORDER_NUM   order_num;

     @Column(name = "rank_name")
     public String rank_name;

     @Column(name = "rank_level")
     public int rank_level;

     @Column(name = "name")
     public String name;
     
     @Column(name="rank_points")
     public String rank_points;
     
     @Column(name="min_points")
     public String min_points;
     
     @Column(name="max_points")
     public String max_points;
     
     
     /*个人资料修改*/   
     public String nickname;
     public String sex;
     public String birthday;
     public String head_img;
     public String user_cid;
     public String result;
     

 public static USER fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     USER   localItem = new USER();

     JSONArray subItemArray;

     localItem.collection_num = jsonObject.optString("collection_num");

     localItem.id = jsonObject.optString("id");
     localItem.order_num = ORDER_NUM.fromJson(jsonObject.optJSONObject("order_num"));

     localItem.name = jsonObject.optString("name");
     localItem.rank_name = jsonObject.optString("rank_name");
     localItem.rank_level = jsonObject.optInt("rank_level");
     localItem.rank_points = jsonObject.optString("rank_points","0");
     localItem.min_points = jsonObject.optString("min_points","0");
     localItem.max_points = jsonObject.optString("max_points","100");
     localItem.nickname=jsonObject.optString("nickname");
     localItem.sex=jsonObject.optString("sex");
     localItem.birthday=jsonObject.optString("birthday");
     localItem.head_img=jsonObject.optString("head_img");
     localItem.user_cid=jsonObject.optString("taskId");
     localItem.result=jsonObject.optString("result");
     
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("collection_num", collection_num);
     localItemObject.put("id", id);
     if(null!=order_num)
     {
       localItemObject.put("order_num", order_num.toJson());
     }
     localItemObject.put("name", name);
     localItemObject.put("rank_name",rank_name);
     localItemObject.put("rank_level",rank_level);
     localItemObject.put("rank_points",rank_points);
     localItemObject.put("min_points",min_points);
     localItemObject.put("nickname",nickname); 
     localItemObject.put("sex",sex); 
     localItemObject.put("birthday",birthday); 
     localItemObject.put("head_img",head_img); 
     localItemObject.put("user_cid", user_cid);
     localItemObject.put("result", result);
     return localItemObject;
 }

}
