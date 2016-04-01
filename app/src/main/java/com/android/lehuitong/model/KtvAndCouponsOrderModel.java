package com.android.lehuitong.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.BeeFramework.activity.StartActivity;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.lehuitong.activity.LoginActivity;
import com.android.lehuitong.protocol.FILTER;
import com.android.lehuitong.protocol.GOODS;
import com.android.lehuitong.protocol.KTV_ORDER;
import com.android.lehuitong.protocol.ORDER_GOODS_LIST;
import com.android.lehuitong.protocol.ORDER_INFO;
import com.android.lehuitong.protocol.PAGINATED;
import com.android.lehuitong.protocol.PAGINATION;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SESSION;
import com.android.lehuitong.protocol.SHOP;
import com.android.lehuitong.protocol.STATUS;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.Constants;

/**
 * KTV/优惠券订单相关
 * 
 * @author shenlw
 *
 */
public class KtvAndCouponsOrderModel extends BaseModel {

	public KTV_ORDER ktv_ORDER;

	public List<KTV_ORDER> ktvOrderList = new ArrayList<KTV_ORDER>();

	public KTV_ORDER orderDetail=new KTV_ORDER();;

	public  Context context;

	public ORDER_INFO info;
	Intent intent;
	public PAGINATED pagination;
	public KtvAndCouponsOrderModel(Context context) {

		super(context);
		this.context=context;
	}
	public void payDown( String order_sn){
		String url = ProtocolConst.FLOW_PAYDONE;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>(){
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					KtvAndCouponsOrderModel.this.callback(url, jo, status);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						ktv_ORDER = KTV_ORDER.fromJson(jo.optJSONObject("data"));
						KtvAndCouponsOrderModel.this.OnMessageResponse(url, jo, status);
					} else if (responseStatus.succeed == 0) {
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		SESSION session = SESSION.getInstance();
		JSONObject requestJsonObject = new JSONObject();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			requestJsonObject.put("session", session.toJson());
			requestJsonObject.put("order_sn", order_sn);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);
	}
	/**
	 * 生成订单
	 * 
	 * @param goods_id
	 *            优惠券/ktv套餐id
	 */
	public void checkOrder(String goods_id,String seller_id,int type) {

		String url = ProtocolConst.FLOW_TICKET_CHECKORDER;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					KtvAndCouponsOrderModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						ktv_ORDER = KTV_ORDER.fromJson(jo.optJSONObject("data"));
						KtvAndCouponsOrderModel.this.OnMessageResponse(url, jo, status);
					} else if (responseStatus.succeed == 0) {
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		SESSION session = SESSION.getInstance();
		JSONObject requestJsonObject = new JSONObject();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			requestJsonObject.put("session", session.toJson());
			requestJsonObject.put("goods_id", goods_id);
			requestJsonObject.put("seller_id", seller_id);
			requestJsonObject.put("type", type);
		} catch (JSONException e) {
		}
		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);
	}

	/**
	 * 确认订单生成
	 * 
	 * @param user_phone
	 *            联系人电话号码
	 * @param consignee
	 *            联系人名字
	 * @param pay_name
	 *            付款中文名字
	 * @param appointment_time
	 *            预约时间
	 * @param seller_id
	 *            卖家id
	 * @param seller_name
	 *            买家名字
	 * @param goods_id
	 *            商品id
	 * @param pay_id
	 *            支付方式id
	 * @param type
	 * 			订单类型           
	 * @param count
	 * 			商品数量      
	 * @param cat_id
	 * 			包厢类型
	 *            
	 */
	public void flowOrderDone(int bonus_id, String user_phone, String consignee, String pay_name, String appointment_time, String seller_id, String seller_name, String goods_id, String pay_id,String type,String count,int cat_id) {

		String url = ProtocolConst.FLOW_TICKET_DONE;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					KtvAndCouponsOrderModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						JSONObject json = jo.getJSONObject("data");
						info=ORDER_INFO.fromJson(jo.optJSONObject("data"));
						KtvAndCouponsOrderModel.this.OnMessageResponse(url, jo, status);
					} else if (responseStatus.succeed == 0) {
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		SESSION session = SESSION.getInstance();
		JSONObject requestJsonObject = new JSONObject();
		Map<String, String> params = new HashMap<String, String>();
		try {
			requestJsonObject.put("session", session.toJson());
			requestJsonObject.put("user_phone", user_phone);
			requestJsonObject.put("consignee", consignee);
			requestJsonObject.put("pay_name", pay_name);
			requestJsonObject.put("appointment_time", appointment_time);
			requestJsonObject.put("seller_id", seller_id);
			requestJsonObject.put("seller_name", seller_name);
			requestJsonObject.put("goods_id", goods_id);
			requestJsonObject.put("pay_id", pay_id);
			requestJsonObject.put("type", type);
			requestJsonObject.put("goods_num", count);
			requestJsonObject.put("bonus_id", bonus_id);
			requestJsonObject.put("cat_id", cat_id);

		} catch (JSONException e) {
		}
		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);
	}

	/**
	 * 确认订单生成(直接预约）
	 * 
	 * @param user_phone
	 *            联系人电话号码
	 * @param consignee
	 *            联系人名字
	 * @param appointment_time
	 *            预约时间
	 * @param seller_id
	 *            卖家id
	 * @param seller_name
	 *            买家名字
	 */
	public void flowOrder(String user_phone, String consignee, String appointment_time, String seller_id, String seller_name) {

		String url = ProtocolConst.FLOW_TICKET_DONE;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					KtvAndCouponsOrderModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						JSONObject json = jo.getJSONObject("data");

						KtvAndCouponsOrderModel.this.OnMessageResponse(url, jo, status);
					} else if (responseStatus.succeed == 0) {
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		SESSION session = SESSION.getInstance();
		JSONObject requestJsonObject = new JSONObject();
		Map<String, String> params = new HashMap<String, String>();
		try {
			requestJsonObject.put("session", session.toJson());
			requestJsonObject.put("user_phone", user_phone);
			requestJsonObject.put("consignee", consignee);
			requestJsonObject.put("appointment_time", appointment_time);
			requestJsonObject.put("seller_id", seller_id);
			requestJsonObject.put("seller_name", seller_name);

		} catch (JSONException e) {
		}
		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);
	}

	/**
	 * 获取订单列表
	 * 
	 * @param type
	 *            订单类型：1，优惠券；2，ktv
	 * @param          
	 */
	public void getOrderList(int type,final Boolean flag) {
		String url = ProtocolConst.ORDER_TICKET_LIST;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					KtvAndCouponsOrderModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					pagination = PAGINATED.fromJson(jo.optJSONObject("paginated"));
					
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						if(flag == false){
							ktvOrderList.clear();
						}
						JSONArray jsonArray = jo.optJSONArray("data");
						if (jsonArray.length() > 0 && jsonArray != null) {
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject orderItem = jsonArray.getJSONObject(i);
								KTV_ORDER itemOrder = KTV_ORDER.fromJson(orderItem);
								System.out.println(itemOrder.toString());
								ktvOrderList.add(itemOrder);
							}
						}
						KtvAndCouponsOrderModel.this.OnMessageResponse(url, jo, status);
					} else if (responseStatus.succeed == 0) {
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		SESSION session = SESSION.getInstance();
		JSONObject requestJsonObject = new JSONObject();
		Map<String, String> params = new HashMap<String, String>();
		try {
			requestJsonObject.put("session", session.toJson());
			requestJsonObject.put("type", type);
			PAGINATION pagination = new PAGINATION();
			if(flag == false){
				pagination.page =  1;
			}else{
				pagination.page = (int) Math.ceil((double) ktvOrderList.size() * 1.0 / 10) + 1;
			}
			
			pagination.count = 10;
				requestJsonObject.put("pagination", pagination.toJson());
			
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
		String url = ProtocolConst.ORDER_TICKET_CANCEL;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					KtvAndCouponsOrderModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						KtvAndCouponsOrderModel.this.OnMessageResponse(url, jo, status);
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
		aq.ajax(cb);

	}

	/**
	 * 查看详情
	 * 
	 * @param order_id
	 *            订单id
	 */
	public void getOrderDetail(String order_id) {

		String url = ProtocolConst.ORDER_TICKET_DETAIL;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					KtvAndCouponsOrderModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
//						JSONObject json = jo.getJSONObject("data");
//						orderDetail = KTV_ORDER.fromJson(json);
//						orderDetail=KTV_ORDER.fromJson(jo.optJSONObject("data"));
						JSONArray jsonArray = jo.optJSONArray("data");
						if (jsonArray.length() > 0 && jsonArray != null) {
							ktvOrderList.clear();
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject orderItem = jsonArray.getJSONObject(i);
								KTV_ORDER itemOrder = KTV_ORDER.fromJson(orderItem);
								System.out.println(itemOrder.toString());
								ktvOrderList.add(itemOrder);
							}
						}
						KtvAndCouponsOrderModel.this.OnMessageResponse(url, jo, status);
					} else if (responseStatus.succeed == 0) {

					}
				} catch (Exception e) {
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
		cb.url(url).type(JSONObject.class).params(params).method(Constants.METHOD_POST);;
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);
	}

	/**
	 * 删除订单
	 * @param order_id 订单id
	 */
	public void deleteOrder(int order_id) {

		String url = ProtocolConst.ORDER_TICKET_DELETE;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					KtvAndCouponsOrderModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						KtvAndCouponsOrderModel.this.OnMessageResponse(url, jo, status);
					} else if (responseStatus.succeed == 0) {

					}
				} catch (Exception e) {
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
		aq.ajax(cb);
	}

}
