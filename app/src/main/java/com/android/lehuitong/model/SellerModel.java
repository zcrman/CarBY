package com.android.lehuitong.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SHOP;
import com.android.lehuitong.protocol.STATUS;
import com.external.androidquery.callback.AjaxStatus;

public class SellerModel extends BaseModel {

	public List<SHOP> shopList = new ArrayList<SHOP>();
	public SHOP shopDetail = new SHOP();

	public SellerModel(Context context) {
		super(context);
	}

	/**
	 * 获取商家列表
	 * 
	 * @param shop_type
	 *            商家类型：1:吃 ，2:ktv，3:购物
	 * @param order_by 排序（默认为空）
	 * 
	 * @param lng  
	 * 			用户经度
	 * @param lat 
	 * 			用户纬度
	 */
	public void getSellerList(String shop_type,String order_by,String keywords,double lng,double lat) {

		String url = ProtocolConst.SELLER_LIST;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					SellerModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						try {
						JSONArray data = jo.optJSONArray("data");
						if (data.length() >= 0 && data != null) {
							
							for (int i = 0; i < data.length(); i++) {
								SHOP itemShop = SHOP.fromJson(data.getJSONObject(i));
								shopList.add(itemShop);
							}
						}
						} catch (Exception e) {
							shopList.clear();
						}
						SellerModel.this.OnMessageResponse(url, jo, status);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					
				}
			}
		};
		JSONObject requestJsonObject = new JSONObject();
		Map<String, String> params = new HashMap<String, String>();
		try {
			requestJsonObject.put("shop_type", shop_type);
			requestJsonObject.put("order_by", order_by);
			requestJsonObject.put("keywords", keywords);
			requestJsonObject.put("lng", lng);
			requestJsonObject.put("lat", lat);
		} catch (JSONException e) {
		}
		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);

	}

	/*
	 * keyword
	 */
//	public void getSellerList(String shop_type,String order_by) {
//
//		String url = ProtocolConst.SELLER_LIST;
//
//		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
//
//			@Override
//			public void callback(String url, JSONObject jo, AjaxStatus status) {
//
//				try {
//					SellerModel.this.callback(url, jo, status);
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//
//				try {
//					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
//					if (responseStatus.succeed == 1) {
//						JSONArray data = jo.optJSONArray("data");
//						if (data.length() >= 0 && data != null) {
//							shopList.clear();
//							for (int i = 0; i < data.length(); i++) {
//								SHOP itemShop = SHOP.fromJson(data.getJSONObject(i));
//								shopList.add(itemShop);
//							}
//						}
//						SellerModel.this.OnMessageResponse(url, jo, status);
//					}
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		};
//		JSONObject requestJsonObject = new JSONObject();
//		Map<String, String> params = new HashMap<String, String>();
//		try {
//			requestJsonObject.put("shop_type", shop_type);
//			requestJsonObject.put("order_by", order_by);
//		} catch (JSONException e) {
//		}
//		params.put("json", requestJsonObject.toString());
//		cb.url(url).type(JSONObject.class).params(params);
//		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
//		aq.progress(dialog.mDialog).ajax(cb);
//
//	}
	/**
	 * 获取商家详情
	 * 
	 * @param seller_id
	 *            商家id
	 */
	public void getSellerInfo(String seller_id) {

		String url = ProtocolConst.SELLER_INFO;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					SellerModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						JSONObject data = jo.optJSONObject("data");
						shopDetail = SHOP.fromJson(data);
						SellerModel.this.OnMessageResponse(url, jo, status);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		JSONObject requestJsonObject = new JSONObject();
		Map<String, String> params = new HashMap<String, String>();
		try {
			requestJsonObject.put("seller_id", seller_id);
		} catch (JSONException e) {
		}
		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);

	}

}
