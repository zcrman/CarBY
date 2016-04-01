package com.android.lehuitong.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.lehuitong.protocol.ADDRESS;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.REGIONS;
import com.android.lehuitong.protocol.SESSION;
import com.android.lehuitong.protocol.STATUS;
import com.external.androidquery.callback.AjaxStatus;

/**
 * 地址管理
 * 
 * @author shenlw
 * 
 */
public class AddressModel extends BaseModel {

	public ArrayList<ADDRESS> addressList = new ArrayList<ADDRESS>();
	public ArrayList<REGIONS> regionsList0 = new ArrayList<REGIONS>();
	public ArrayList<REGIONS> regionsList1 = new ArrayList<REGIONS>();
	public ArrayList<REGIONS> regionsList2 = new ArrayList<REGIONS>();
	public ArrayList<REGIONS> regionsList3 = new ArrayList<REGIONS>();
	public ADDRESS address;

	public AddressModel(Context context) {
		super(context);

	}

	/**
	 * 获取收货地址列表
	 */
	public void getAddressList() {
		String url = ProtocolConst.ADDRESS_LIST;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					AddressModel.this.callback(url, jo, status);
				} catch (Exception e1) {

					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo
							.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						JSONArray dataJsonArray = jo.optJSONArray("data");
						addressList.clear();
						if (null != dataJsonArray && dataJsonArray.length() > 0) {
							for (int i = 0; i < dataJsonArray.length(); i++) {
								JSONObject addressJsonObject = dataJsonArray
										.getJSONObject(i);
								ADDRESS addressItem = ADDRESS
										.fromJson(addressJsonObject);
								addressList.add(addressItem);

							}
						}
						AddressModel.this.OnMessageResponse(url, jo, status);
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
	 * 添加收货地址
	 * 
	 * @param consignee
	 * @param tel
	 * @param email
	 * @param mobile
	 * @param zipcode
	 * @param address
	 * @param country
	 * @param province
	 * @param city
	 * @param district
	 */
	public void addAddress(String consignee, String tel, String email,
			String mobile, String zipcode, String address, String country,
			String province, String city, String district) {
		String url = ProtocolConst.ADDRESS_ADD;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					AddressModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo
							.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						JSONArray dataJsonArray = jo.optJSONArray("data");
						if (null != dataJsonArray && dataJsonArray.length() > 0) {
							addressList.clear();
							for (int i = 0; i < dataJsonArray.length(); i++) {
								JSONObject addressJsonObject = dataJsonArray
										.getJSONObject(i);
								ADDRESS addressItem = ADDRESS
										.fromJson(addressJsonObject);
								addressList.add(addressItem);
							}
						}
					}
					AddressModel.this.OnMessageResponse(url, jo, status);

				} catch (JSONException e) {

					e.printStackTrace();
				}
			}

		};

		SESSION session = SESSION.getInstance();
		ADDRESS add = new ADDRESS();
		add.consignee = consignee;
		add.tel = tel;
		add.email = email;
		add.mobile = mobile;
		add.zipcode = zipcode;
		add.address = address;
		add.country = country;
		add.province = province;
		add.city = city;
		add.district = district;

		JSONObject requestJsonObject = new JSONObject();

		Map<String, String> params = new HashMap<String, String>();
		try {
			requestJsonObject.put("session", session.toJson());
			requestJsonObject.put("address", add.toJson());
		} catch (JSONException e) {
		}

		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);

	}

	/**
	 * 获取地址详细信息
	 * 
	 * @param address_id
	 *            地址id
	 */
	public void getAddressInfo(String address_id) {
		String url = ProtocolConst.ADDRESS_INFO;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					AddressModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				try {
					STATUS responseStatus = STATUS.fromJson(jo
							.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						address = ADDRESS.fromJson(jo.optJSONObject("data"));

						AddressModel.this.OnMessageResponse(url, jo, status);
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
			requestJsonObject.put("address_id", address_id);
		} catch (JSONException e) {
		}
		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);

	}

	/**
	 * 设置默认收货地址
	 * 
	 * @param address_id
	 *            地址id
	 */
	public void addressDefault(String address_id) {
		String url = ProtocolConst.ADDRESS_SETDEFAULT;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					AddressModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				try {
					STATUS responseStatus = STATUS.fromJson(jo
							.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						AddressModel.this.OnMessageResponse(url, jo, status);
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
			requestJsonObject.put("address_id", address_id);
		} catch (JSONException e) {
		}

		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);
	}

	/**
	 * 删除收货地址
	 * 
	 * @param address_id
	 *            地址id
	 */
	public void addressDelete(String address_id) {
		String url = ProtocolConst.ADDRESS_DELETE;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					AddressModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				try {
					STATUS responseStatus = STATUS.fromJson(jo
							.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						AddressModel.this.OnMessageResponse(url, jo, status);
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
			requestJsonObject.put("address_id", address_id);
		} catch (JSONException e) {
		}

		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);

	}

	/**
	 * 修改收货地址
	 * 
	 * @param address_id
	 * @param consignee
	 * @param tel
	 * @param email
	 * @param mobile
	 * @param zipcode
	 * @param address
	 * @param country
	 * @param province
	 * @param city
	 * @param district
	 */
	public void addressUpdate(String address_id, String consignee, String tel,
			String email, String mobile, String zipcode, String address,
			String country, String province, String city, String district) {
		String url = ProtocolConst.ADDRESS_UPDATE;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					AddressModel.this.callback(url, jo, status);
				} catch (Exception e1) {

					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo
							.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						JSONArray dataJsonArray = jo.optJSONArray("data");
						if (null != dataJsonArray && dataJsonArray.length() > 0) {
							addressList.clear();
							for (int i = 0; i < dataJsonArray.length(); i++) {
								JSONObject addressJsonObject = dataJsonArray
										.getJSONObject(i);
								ADDRESS addressItem = ADDRESS
										.fromJson(addressJsonObject);
								addressList.add(addressItem);
							}
						}
						AddressModel.this.OnMessageResponse(url, jo, status);
					}

				} catch (JSONException e) {

					e.printStackTrace();
				}
			}

		};

		SESSION session = SESSION.getInstance();
		ADDRESS add = new ADDRESS();
		add.consignee = consignee;
		add.tel = tel;
		add.email = email;
		add.mobile = mobile;
		add.zipcode = zipcode;
		add.address = address;
		add.country = country;
		add.province = province;
		add.city = city;
		add.district = district;

		JSONObject requestJsonObject = new JSONObject();

		Map<String, String> params = new HashMap<String, String>();
		try {
			requestJsonObject.put("session", session.toJson());
			requestJsonObject.put("address_id", address_id);
			requestJsonObject.put("address", add.toJson());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		params.put("json", requestJsonObject.toString());

		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);

	}

	/**
	 * 获取地区城市
	 * 
	 * @param parent_id
	 * @param i
	 */
	public void region(String parent_id, final int i) {
		String url = ProtocolConst.REGION;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					AddressModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				try {
					STATUS responseStatus = STATUS.fromJson(jo
							.optJSONObject("status"));

					if (responseStatus.succeed == 1) {

						JSONObject data = jo.optJSONObject("data");
						JSONArray regionsJsonArray = data
								.optJSONArray("regions");
						regionsList0.clear();
						if (null != regionsJsonArray
								&& regionsJsonArray.length() > 0) {
							regionsList0.clear();
							for (int i = 0; i < regionsJsonArray.length(); i++) {
								JSONObject regionsJsonObject = regionsJsonArray
										.getJSONObject(i);
								REGIONS regions = REGIONS
										.fromJson(regionsJsonObject);
								regionsList0.add(regions);
							}
						}

						if (i == 0) {
							if (null != regionsJsonArray
									&& regionsJsonArray.length() > 0) {
								regionsList0.clear();
								for (int i = 0; i < regionsJsonArray.length(); i++) {
									JSONObject regionsJsonObject = regionsJsonArray
											.getJSONObject(i);
									REGIONS regions = REGIONS
											.fromJson(regionsJsonObject);
									regionsList0.add(regions);
								}
							}
						} else if (i == 1) {
							if (null != regionsJsonArray
									&& regionsJsonArray.length() > 0) {
								regionsList1.clear();
								for (int i = 0; i < regionsJsonArray.length(); i++) {
									JSONObject regionsJsonObject = regionsJsonArray
											.getJSONObject(i);
									REGIONS regions = REGIONS
											.fromJson(regionsJsonObject);
									regionsList1.add(regions);
								}
							}
						} else if (i == 2) {
							if (null != regionsJsonArray
									&& regionsJsonArray.length() > 0) {
								regionsList2.clear();
								for (int i = 0; i < regionsJsonArray.length(); i++) {
									JSONObject regionsJsonObject = regionsJsonArray
											.getJSONObject(i);
									REGIONS regions = REGIONS
											.fromJson(regionsJsonObject);
									regionsList2.add(regions);
								}
							}
						} else if (i == 3) {
							if (null != regionsJsonArray
									&& regionsJsonArray.length() > 0) {
								regionsList3.clear();
								for (int i = 0; i < regionsJsonArray.length(); i++) {
									JSONObject regionsJsonObject = regionsJsonArray
											.getJSONObject(i);
									REGIONS regions = REGIONS
											.fromJson(regionsJsonObject);
									regionsList3.add(regions);
								}
							}
						}
						AddressModel.this.OnMessageResponse(url, jo, status);
					}

				} catch (JSONException e) {

					e.printStackTrace();
				}
			}

		};

		Map<String, String> params = new HashMap<String, String>();
		params.put("parent_id", parent_id);
		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);

	}

}
