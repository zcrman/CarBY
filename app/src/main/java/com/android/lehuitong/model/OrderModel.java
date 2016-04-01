package com.android.lehuitong.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.wifi.WifiConfiguration.Protocol;
import android.widget.Toast;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.lehuitong.protocol.ADDRESS;
import com.android.lehuitong.protocol.BONUS;
import com.android.lehuitong.protocol.GOODORDER;
import com.android.lehuitong.protocol.GOODS_LIST;
import com.android.lehuitong.protocol.ORDER_DETAIL;
import com.android.lehuitong.protocol.ORDER_INFO;
import com.android.lehuitong.protocol.PAGINATED;
import com.android.lehuitong.protocol.PAGINATION;
import com.android.lehuitong.protocol.PAYMENT;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SELECT_SHOPPING;
import com.android.lehuitong.protocol.SESSION;
import com.android.lehuitong.protocol.SHIPPING;
import com.android.lehuitong.protocol.STATUS;
import com.android.lehuitong.protocol.TOTAL;
import com.external.androidquery.callback.AjaxStatus;

/**
 * 订单相关
 * 
 * @author shenlw
 * 
 */
public class OrderModel extends BaseModel {

	private int page = 1;
	public PAGINATED paginated;
	public int allow_use_bonus;
	public ArrayList<GOODORDER> order_list = new ArrayList<GOODORDER>();
	public SELECT_SHOPPING select_SHOPPING;
	public ORDER_DETAIL detail;
	private int i = 0;
	// 生成订单
	public ADDRESS address;
	public ArrayList<GOODS_LIST> balance_goods_list = new ArrayList<GOODS_LIST>();
	public ArrayList<PAYMENT> payment_list = new ArrayList<PAYMENT>();
	public ArrayList<SHIPPING> shipping_list = new ArrayList<SHIPPING>();
	public ArrayList<BONUS> bonus_list = new ArrayList<BONUS>();

	public String orderInfoJsonString;
	public TOTAL total;
	public String order_id;
	public int shipping_id;
	// 确认生成订单
	public GOODORDER goodorder;
	public ORDER_INFO info;
	public BONUS bonus;
	public Context context;

	public OrderModel(Context context) {
		super(context);
		this.context = context;
	}
public void sendAddressId(String id){
	String url = ProtocolConst.FLOW_CHECKORDER;
	BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>(){
		@Override
		public void callback(String url, JSONObject object, AjaxStatus status) {
			
		}
	};
	SESSION session = SESSION.getInstance();

	JSONObject requestJsonObject = new JSONObject();
	
	Map<String, String> params = new HashMap<String, String>();
	try {
		requestJsonObject.put("session", session.toJson());
		requestJsonObject.put("goods_ids", id);
		
	} catch (JSONException e) {
	}

	params.put("json", requestJsonObject.toString());

	cb.url(url).type(JSONObject.class).params(params);
	ProgressDialog pd = new ProgressDialog(mContext);
	pd.setMessage("请稍后...");
	aq.progress(pd).ajax(cb);

}
	/**
	 * 获取订单列表
	 * 
	 * @param type
	 *            订单状态：await_pay(等待付款) finished(完成交易) unconfirmed(交易为确认)
	 */
	public void getOrder(String type,int total) {
		page = 1;
		String url = ProtocolConst.ORDER_LIST;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					OrderModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo
							.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						order_list.clear();
						JSONArray dataJsonArray = jo.optJSONArray("data");

						if (null != dataJsonArray && dataJsonArray.length() > 0) {
							
							for (int i = 0; i < dataJsonArray.length(); i++) {
								JSONObject orderJsonObject = dataJsonArray
										.getJSONObject(i);
								GOODORDER order_list_Item = GOODORDER
										.fromJson(orderJsonObject);
								order_list.add(order_list_Item);
							}
						}
						paginated = PAGINATED.fromJson(jo
								.optJSONObject("paginated"));
					}
					OrderModel.this.OnMessageResponse(url, jo, status);

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
			PAGINATION pagination = new PAGINATION();
			pagination.page = 1;
			pagination.count = 100;
			requestJsonObject.put("pagination", pagination.toJson());
			requestJsonObject.put("type", type);
		} catch (JSONException e) {

		}
		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "加载中...");
		aq.progress(dialog.mDialog).ajax(cb);

	}

	/**
	 * 获取订单列表更多
	 * 
	 * @param type
	 *            订单状态：await_pay(等待付款) finished(完成交易) unconfirmed(交易为确认)
	 */
	public void getOrderMore(String type) {

		String url = ProtocolConst.ORDER_LIST;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					OrderModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				try {
					STATUS responseStatus = STATUS.fromJson(jo
							.optJSONObject("status"));

					if (responseStatus.succeed == 1) {

						JSONArray dataJsonArray = jo.optJSONArray("data");

						if (null != dataJsonArray && dataJsonArray.length() > 0) {

							for (int i = 0; i < dataJsonArray.length(); i++) {
								JSONObject orderJsonObject = dataJsonArray
										.getJSONObject(i);
								GOODORDER order_list_Item = GOODORDER
										.fromJson(orderJsonObject);
								order_list.add(order_list_Item);
							}
						}

						paginated = PAGINATED.fromJson(jo
								.optJSONObject("paginated"));

					}

					OrderModel.this.OnMessageResponse(url, jo, status);

				} catch (JSONException e) {

					e.printStackTrace();
				}

			}

		};

		SESSION session = SESSION.getInstance();
		PAGINATION pagination = new PAGINATION();
		pagination.page = order_list.size() / 10 + 1;
		pagination.count = 10;

		JSONObject requestJsonObject = new JSONObject();

		Map<String, String> params = new HashMap<String, String>();
		try {
			requestJsonObject.put("session", session.toJson());
			requestJsonObject.put("pagination", pagination.toJson());
			requestJsonObject.put("type", type);
		} catch (JSONException e) {

		}

		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);

	}

	/**
	 * 生成订单（购物车）
	 * id 地址id
	 */
	public void checkOrder(List<Integer> goodsIds) {
		String url = ProtocolConst.FLOW_CHECKORDER;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					OrderModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo
							.optJSONObject("status"));
					if (responseStatus.succeed == 1) {

						JSONObject dataJsonObject = jo.getJSONObject("data");
						allow_use_bonus = dataJsonObject
								.optInt("allow_use_bonus");
						shipping_id = dataJsonObject.optInt("shipping_id");
						address = ADDRESS.fromJson(dataJsonObject
								.optJSONObject("consignee"));
						select_SHOPPING = SELECT_SHOPPING
								.fromJson(dataJsonObject.optJSONObject("total"));
						total = TOTAL.fromJson(dataJsonObject
								.optJSONObject("total"));
						JSONArray goodsArray = dataJsonObject
								.optJSONArray("goods_list");

						if (null != goodsArray && goodsArray.length() > 0) {

							balance_goods_list.clear();
							for (int i = 0; i < goodsArray.length(); i++) {
								JSONObject goodsJsonObject = goodsArray
										.getJSONObject(i);
								GOODS_LIST goods_list_Item = GOODS_LIST
										.fromJson(goodsJsonObject);
								balance_goods_list.add(goods_list_Item);
							}
						}

						orderInfoJsonString = dataJsonObject.toString();
						JSONArray shippingArray = dataJsonObject
								.optJSONArray("shipping_list");
						if (null != shippingArray && shippingArray.length() > 0) {
							shipping_list.clear();
							for (int i = 0; i < shippingArray.length(); i++) {
								JSONObject shippingJSONObject = shippingArray
										.getJSONObject(i);
								SHIPPING shipping = SHIPPING
										.fromJson(shippingJSONObject);
								shipping_list.add(shipping);
							}
						}

						JSONArray paymentArray = dataJsonObject
								.optJSONArray("payment_list");

						if (null != paymentArray && paymentArray.length() > 0) {
							payment_list.clear();
							for (int i = 0; i < paymentArray.length(); i++) {
								JSONObject paymentJsonObject = paymentArray
										.getJSONObject(i);
								PAYMENT payment_Item = PAYMENT
										.fromJson(paymentJsonObject);
								payment_list.add(payment_Item);
							}
						}
						JSONArray bonusArray = dataJsonObject
								.optJSONArray("bonus");
						if (null != bonusArray && bonusArray.length() > 0) {
							bonus_list.clear();
							for (int i = 0; i < bonusArray.length(); i++) {
								JSONObject bonusJsonObject = bonusArray
										.getJSONObject(i);
								bonus = BONUS.fromJson(bonusJsonObject);
								bonus_list.add(bonus);
							}

						}
						OrderModel.this.OnMessageResponse(url, jo, status);
					}

					else {
						Toast.makeText(context, "服务器错误请稍后再重试",
								Toast.LENGTH_LONG).show();
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};

		SESSION session = SESSION.getInstance();

		JSONObject requestJsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		for (int i = 0; i < goodsIds.size(); i++) {
			jsonArray.put(goodsIds.get(i) + "");
		
		}
		Map<String, String> params = new HashMap<String, String>();
		try {
			requestJsonObject.put("session", session.toJson());
			
			requestJsonObject.put("goods_ids", jsonArray);
			
		} catch (JSONException e) {
		}

		params.put("json", requestJsonObject.toString());

		cb.url(url).type(JSONObject.class).params(params);
		ProgressDialog pd = new ProgressDialog(mContext);
		pd.setMessage("请稍后...");
		aq.progress(pd).ajax(cb);

	}

	/**
	 * 确认下单
	 * 
	 * @param bonus_id
	 *            红包id
	 * @param shipping_id
	 *            送货方式id
	 * @param pay_id
	 *            支付方式id
	 * @param address_id
	 *            收货地址id
	 * @param goodsIds
	 *            商品id数组
	 */

	public void confirmOrder(int bonus_id, int shipping_id, int pay_id,
			int address_id, List<Integer> goodsIds) {

		String url = ProtocolConst.FLOW_DONE;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					OrderModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo
							.optJSONObject("status"));

					if (responseStatus.succeed == 1) {
						JSONObject json = jo.getJSONObject("data");
						order_id = json.getString("order_id");
						JSONObject goodorder = jo.optJSONObject("data");
						info = ORDER_INFO.fromJson(goodorder
								.optJSONObject("order_info"));
						OrderModel.this.OnMessageResponse(url, jo, status);
					} else if (responseStatus.succeed == 0) {
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		SESSION session = SESSION.getInstance();
		JSONObject requestJsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < goodsIds.size(); i++) {
			jsonArray.put(goodsIds.get(i) + "");
		}
		Map<String, String> params = new HashMap<String, String>();
		try {
			requestJsonObject.put("session", session.toJson());
			requestJsonObject.put("shipping_id", shipping_id);
			requestJsonObject.put("pay_id", pay_id);
			requestJsonObject.put("address_id", address_id);
			requestJsonObject.put("goods_ids", jsonArray);
			requestJsonObject.put("bonus_id", bonus_id);
		} catch (JSONException e) {
		}
		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);
	}

	/**
	 * 取消订单
	 * 
	 * @param order_id
	 *            订单号
	 */
	public void orderCancle(String order_id) {

		String url = ProtocolConst.ORDER_CANCEL;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					OrderModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo
							.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						OrderModel.this.OnMessageResponse(url, jo, status);
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
			requestJsonObject.put("order_id", order_id);
		} catch (JSONException e) {
		}
		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);

	}

	/**
	 * 删除订单
	 * 
	 * @param order_id
	 *            订单号
	 */
	public void orderDelect(String order_id) {

		String url = ProtocolConst.ORDER_DELETE;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					OrderModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo
							.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						OrderModel.this.OnMessageResponse(url, jo, status);
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
			requestJsonObject.put("order_id", order_id);
		} catch (JSONException e) {
		}
		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);

	}

	/**
	 * 确认收货
	 * 
	 * @param order_id
	 *            订单号
	 */
	public void affirmReceived(int order_id) {

		String url = ProtocolConst.ORDER_AFFIRMRECEIVED;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					OrderModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo
							.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						OrderModel.this.OnMessageResponse(url, jo, status);
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
			requestJsonObject.put("order_id", order_id);
		} catch (JSONException e) {
		}
		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);

	}

	/**
	 * 支付
	 * 
	 * @param order_id
	 */
	public void orderPay(int order_id) {

		String url = ProtocolConst.ORDER_PAY;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					OrderModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				try {
					STATUS responseStatus = STATUS.fromJson(jo
							.optJSONObject("status"));

					OrderModel.this.OnMessageResponse(url, jo, status);

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
			requestJsonObject.put("order_id", order_id);
		} catch (JSONException e) {
		}
		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);
	}

	/** 快递选择接口 */
	public void selectShipping(int shipping_id) {
		String url = ProtocolConst.FLOW_SELECT_SHIPPING;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					OrderModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				try {
					STATUS responseStatus = STATUS.fromJson(jo
							.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						select_SHOPPING = SELECT_SHOPPING.fromJson(jo
								.optJSONObject("data"));
						OrderModel.this.OnMessageResponse(url, jo, status);
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
			requestJsonObject.put("shipping_id", shipping_id);
		} catch (JSONException e) {
		}
		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}

	/** 红包选择接口 */
	public void selectBonus(int bonus_id) {
		String url = ProtocolConst.FLOW_SELECT_BONUS;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					OrderModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				try {
					STATUS responseStatus = STATUS.fromJson(jo
							.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						select_SHOPPING = SELECT_SHOPPING.fromJson(jo
								.optJSONObject("data"));
					}
					OrderModel.this.OnMessageResponse(url, jo, status);

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
			requestJsonObject.put("bonus_id", bonus_id);
		} catch (JSONException e) {
		}
		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}

	/** 查看详情 **/

	public void seeDetails(String order_id) {
		String url = ProtocolConst.ORDER_DATAIL;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					OrderModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo
							.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						JSONObject dataJsonObject = jo.getJSONObject("data");
						detail = ORDER_DETAIL.fromJson(dataJsonObject);
						OrderModel.this.OnMessageResponse(url, jo, status);
					}
					else {
						Toast.makeText(context, "服务器错误请稍后再重试",
								Toast.LENGTH_LONG).show();
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
			requestJsonObject.put("order_id", order_id);

		} catch (JSONException e) {
		}
		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		ProgressDialog pd = new ProgressDialog(mContext);
		pd.setMessage("请稍后...");
		aq.progress(pd).ajax(cb);

	}
}
