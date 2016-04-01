package com.android.lehuitong.protocol;

import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "ORDER_INFO")
public class ORDER_INFO extends Model implements Serializable
{
    @Column(name = "pay_code")
    public String pay_code;

    @Column(name = "order_amount")
    public String order_amount;

    @Column(name = "order_id")
    public int order_id;

    @Column(name = "order_sn")
    public String order_sn;

    @Column(name = "subject")
    public String subject;

    @Column(name = "desc")
    public String desc;

    public static ORDER_INFO fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return null;
        }

        ORDER_INFO   localItem = new ORDER_INFO();

        JSONArray subItemArray;

        localItem.pay_code = jsonObject.optString("pay_code");

        localItem.order_amount = jsonObject.optString("order_amount");

        localItem.order_id  = jsonObject.optInt("order_id");

        localItem.subject   = jsonObject.optString("subject");

        localItem.desc      = jsonObject.optString("desc");

        localItem.order_sn  = jsonObject.optString("order_sn");
        return localItem;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("pay_code", pay_code);
        localItemObject.put("order_amount",order_amount);
        localItemObject.put("order_id",order_id);
        localItemObject.put("subject",subject);
        localItemObject.put("desc",desc);
        localItemObject.put("order_sn",order_sn);
        return localItemObject;
    }


}
