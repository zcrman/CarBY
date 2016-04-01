package com.android.lehuitong.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LOCATION extends Model
{

    @Column(name = "lat")
    public String lat;

    @Column(name = "lng")
    public String lng;
    
    @Column(name = "location")
    public String location;

    public static LOCATION fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return null;
        }

        LOCATION   localItem = new LOCATION();

        JSONArray subItemArray;

        localItem.lat = jsonObject.optString("lat");

        localItem.lng = jsonObject.optString("lng");
        
        localItem.location = jsonObject.optString("location");
        return localItem;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("lat", lat);
        localItemObject.put("lng", lng);
        localItemObject.put("location", location);
        return localItemObject;
    }
}