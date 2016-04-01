package com.android.lehuitong.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.android.lehuitong.protocol.CATEGORYGOODS;
import com.android.lehuitong.protocol.FILTER;
import com.android.lehuitong.protocol.GOODS;
import com.android.lehuitong.protocol.PAGINATED;
import com.android.lehuitong.protocol.PAGINATION;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SIMPLEGOODS;
import com.android.lehuitong.protocol.STATUS;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;

/**
 * 商品相关
 * 
 * @author shenlw
 *
 */
public class GoodsListModel extends BaseModel {

	public ArrayList<SIMPLEGOODS> simplegoodsList = new ArrayList<SIMPLEGOODS>();
	public ArrayList<GOODS> goodsList = new ArrayList<GOODS>();
	public ArrayList<CATEGORYGOODS> categoryList = new ArrayList<CATEGORYGOODS>();

	public static String PRICE_DESC = "price_desc";
	public static String PRICE_ASC = "price_asc";
	public static String IS_HOT = "is_hot";

	public GoodsListModel(Context context) {
		super(context);
	}

	/**
	 * 通过分类获取商品列表
	 * 
	 * @param filter
	 *            查询条件
	 * @param intro
	 *            商品种类
	 */
	public void fetchPreSearch(FILTER filter, String intro,String count) {
		String url = ProtocolConst.SEARCH;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					GoodsListModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					PAGINATED paginated = PAGINATED.fromJson(jo.optJSONObject("paginated"));

					if (responseStatus.succeed == 1) {
						JSONArray simpleGoodsJsonArray = jo.optJSONArray("data");

						simplegoodsList.clear();
						goodsList.clear();
						if (null != simpleGoodsJsonArray && simpleGoodsJsonArray.length() > 0) {
							simplegoodsList.clear();
							for (int i = 0; i < simpleGoodsJsonArray.length(); i++) {
								JSONObject simpleGoodsJsonObject = simpleGoodsJsonArray.getJSONObject(i);
								SIMPLEGOODS simplegoods = SIMPLEGOODS.fromJson(simpleGoodsJsonObject);
								simplegoodsList.add(simplegoods);
								GOODS goods = GOODS.fromJson(simpleGoodsJsonObject);
								goodsList.add(goods);
							}
						}

						GoodsListModel.this.OnMessageResponse(url, jo, status);

					}

				} catch (JSONException e) {

				}

			}

		};

		PAGINATION pagination = new PAGINATION();
		pagination.page = 1;
		pagination.count =10;

		JSONObject requestJsonObject = new JSONObject();

		Map<String, String> params = new HashMap<String, String>();
		try {
			requestJsonObject.put("filter", filter.toJson());
			requestJsonObject.put("pagination", pagination.toJson());
		} catch (JSONException e) {
		}

		params.put("json", requestJsonObject.toString());

		cb.url(url).type(JSONObject.class).params(params);
		ProgressDialog pd = new ProgressDialog(mContext);

		Resources resource = mContext.getResources();
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);

	}

	/**
	 * 通过分类获取商品列表更多
	 * 
	 * @param filter
	 */
	public void fetchPreSearchMore(FILTER filter) {
		String url = ProtocolConst.SEARCH;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					GoodsListModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					PAGINATED paginated = PAGINATED.fromJson(jo.optJSONObject("paginated"));

					if (responseStatus.succeed == 1) {
						JSONArray simpleGoodsJsonArray = jo.optJSONArray("data");
						
						if (null != simpleGoodsJsonArray && simpleGoodsJsonArray.length() > 0) {
							for (int i = 0; i < simpleGoodsJsonArray.length(); i++) {
								JSONObject simpleGoodsJsonObject = simpleGoodsJsonArray.getJSONObject(i);
								SIMPLEGOODS simplegoods = SIMPLEGOODS.fromJson(simpleGoodsJsonObject);
								
								simplegoodsList.add(simplegoods);
							}
						}
						GoodsListModel.this.OnMessageResponse(url, jo, status);
					}
				} catch (JSONException e) {
				}
			}
		};
		PAGINATION pagination = new PAGINATION();
		pagination.page = (int) Math.ceil((double) simplegoodsList.size() * 1.0 / 10) + 1;
		pagination.count = 10;
		JSONObject requestJsonObject = new JSONObject();
		Map<String, String> params = new HashMap<String, String>();
		try {
			requestJsonObject.put("filter", filter.toJson());
			requestJsonObject.put("pagination", pagination.toJson());
		} catch (JSONException e) {
		}

		params.put("json", requestJsonObject.toString());

		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);
	}

	/**
	 * 获取商品分类
	 * 
	 * @param cat_id
	 *            根据顶级分类获取子分类
	 */
	public void getGoodCatList(String cat_id) {
		String url = ProtocolConst.CATEGORY_LIST;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					GoodsListModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						categoryList.clear();
						JSONArray jsonArray = jo.optJSONArray("data");
						if (jsonArray!=null&jsonArray.length()>0) {
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject itemCat = jsonArray.getJSONObject(i);
								CATEGORYGOODS categorygoods = CATEGORYGOODS.fromJson(itemCat);
								categoryList.add(categorygoods);
							}
							GoodsListModel.this.OnMessageResponse(url, jo, status);
							}

					}

				} catch (JSONException e) {

				}

			}

		};
		JSONObject requestJsonObject = new JSONObject();
		Map<String, String> params = new HashMap<String, String>();
		try {

			requestJsonObject.put("cat_id", cat_id);
		} catch (JSONException e) {
		}

		params.put("json", requestJsonObject.toString());

		cb.url(url).type(JSONObject.class).params(params);
		ProgressDialog pd = new ProgressDialog(mContext);

		Resources resource = mContext.getResources();
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.ajax(cb);
	}
	

}
