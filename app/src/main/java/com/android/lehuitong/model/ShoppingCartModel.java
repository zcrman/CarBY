package com.android.lehuitong.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.lehuitong.protocol.ADDRESS;
import com.android.lehuitong.protocol.GOODS_LIST;
import com.android.lehuitong.protocol.PAYMENT;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.REC_IDS;
import com.android.lehuitong.protocol.SESSION;
import com.android.lehuitong.protocol.SHIPPING;
import com.android.lehuitong.protocol.STATUS;
import com.android.lehuitong.protocol.TOTAL;
import com.external.androidquery.callback.AjaxStatus;

public class ShoppingCartModel extends BaseModel {

	public ArrayList<GOODS_LIST> goods_list = new ArrayList<GOODS_LIST>();
	public TOTAL total;
	public int goods_num;

	// 结算（提交订单前的订单预览）
	public ADDRESS address;
	public ArrayList<GOODS_LIST> balance_goods_list = new ArrayList<GOODS_LIST>();
	public ArrayList<PAYMENT> payment_list = new ArrayList<PAYMENT>();
	public ArrayList<SHIPPING> shipping_list = new ArrayList<SHIPPING>();
	public Context context;
	public String orderInfoJsonString;
	public STATUS status2;
	private static ShoppingCartModel instance;

	public static ShoppingCartModel getInstance() {
		return instance;
	}

	public ShoppingCartModel(Context context) {
		super(context);
		instance = this;
		this.context = context;
	}

	/**
	 * 查看购物车中的商品列表
	 */
	public void cartList() {
		String url = ProtocolConst.CART_LIST;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					ShoppingCartModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));

					if (responseStatus.succeed == 1) {
						JSONObject dataJsonObject = jo.optJSONObject("data");

						total = TOTAL.fromJson(dataJsonObject.optJSONObject("total"));
						JSONArray dataJsonArray = dataJsonObject.optJSONArray("goods_list");

						goods_list.clear();
						ShoppingCartModel.this.goods_num = 0;
						if (null != dataJsonArray && dataJsonArray.length() > 0) {
							for (int i = 0; i < dataJsonArray.length(); i++) {
								JSONObject goodsJsonObject = dataJsonArray.getJSONObject(i);
								GOODS_LIST goods_list_Item = GOODS_LIST.fromJson(goodsJsonObject);
								goods_list.add(goods_list_Item);
								ShoppingCartModel.this.goods_num += Integer.valueOf(goods_list_Item.goods_number);
							}
						}
						ShoppingCartModel.this.OnMessageResponse(url, jo, status);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};

		SESSION session = SESSION.getInstance();
		JSONObject requestJsonObject = new JSONObject();
		Map<String, String> params = new HashMap<String, String>();
		try {
			requestJsonObject.put("session", session.toJson());
		} catch (JSONException e) {
		}

		params.put("json", requestJsonObject.toString());

		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.ajax(cb);

	}

	/**
	 * 删除购物车中的商品
	 * 
	 * @param good_id
	 */
	public void deleteGoods(ArrayList<Integer> good_id) {
		String url = ProtocolConst.CART_DELETE;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					ShoppingCartModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if (responseStatus.succeed == 1) {

						ShoppingCartModel.this.OnMessageResponse(url, jo, status);

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};

		SESSION session = SESSION.getInstance();
		JSONObject requestJsonObject = new JSONObject();
		REC_IDS rec_ids = new REC_IDS();
		
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			requestJsonObject.put("session", session.toJson());
			requestJsonObject.put("rec_ids", rec_ids.toJson(good_id));
		} catch (JSONException e) {
		}
		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);

	}

	/**
	 * 修改购物车中的商品数量
	 * 
	 * @param rec_id
	 *            购物车中商品id
	 * @param number_change
	 *            商品的新数量
	 */
	public void updateGoods(ArrayList<Integer> good_id, ArrayList<Integer> number_change) {
		String url = ProtocolConst.CART_UPDATE;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					ShoppingCartModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if (responseStatus.succeed == 1) {

						ShoppingCartModel.this.OnMessageResponse(url, jo, status);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};
		SESSION session = SESSION.getInstance();
		REC_IDS rec_ids=new REC_IDS();
		JSONObject requestJsonObject = new JSONObject();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			requestJsonObject.put("session", session.toJson());
			requestJsonObject.put("rec_ids", rec_ids.toJson(good_id, number_change));
		} catch (JSONException e) {
		}
		params.put("json", requestJsonObject.toString());

		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);

	}

	/**
	 * 添加商品到购物车
	 * 
	 * @param i
	 *            商品ID
	 * @param number
	 *            商品数量
	 */
	public void Cartcrate(int i, int number) {
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					ShoppingCartModel.this.callback(url, jo, status);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					status2 = STATUS.fromJson(jo.optJSONObject("status"));
					if (status2.succeed == 1) {
						ShoppingCartModel.this.OnMessageResponse(url, jo, status);
					} else if (status2.succeed == 0) {
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		};

		String url = ProtocolConst.CART_CREATE;
		SESSION session = SESSION.getInstance();
		JSONObject requestJsonObject = new JSONObject();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			requestJsonObject.put("session", session.toJson());
			requestJsonObject.put("goods_id", i);
			requestJsonObject.put("number", number);
		} catch (JSONException e) {
		}
		params.put("json", requestJsonObject.toString());

		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);
	}

}
