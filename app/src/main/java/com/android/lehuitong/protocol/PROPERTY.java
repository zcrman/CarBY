
package com.android.lehuitong.protocol;
import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "PROPERTY")
public class PROPERTY  extends Model implements Serializable
{

     @Column(name = "name")
     public String name;

     @Column(name = "value")
     public String value;

 public static PROPERTY fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return null;
      }

     PROPERTY   localItem = new PROPERTY();

     JSONArray subItemArray;

     localItem.name = jsonObject.optString("name");

     localItem.value = jsonObject.optString("value");
     return localItem;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("name", name);
     localItemObject.put("value", value);
     return localItemObject;
 }

}
