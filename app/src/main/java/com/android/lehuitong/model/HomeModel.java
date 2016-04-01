package com.android.lehuitong.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.android.lehuitong.protocol.CATEGORYGOODS;
import com.android.lehuitong.protocol.GOOD;
import com.android.lehuitong.protocol.GOODS;
import com.android.lehuitong.protocol.PLAYER;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SESSION;
import com.android.lehuitong.protocol.SHOP;
import com.android.lehuitong.protocol.SIMPLEGOODS;
import com.android.lehuitong.protocol.STATUS;
import com.external.androidquery.callback.AjaxStatus;

/**
 * 主页面Model
 * 
 * @author shenlw
 *
 */
public class HomeModel extends BaseModel {

	public ArrayList<SIMPLEGOODS> promoteFoodsList = new ArrayList<SIMPLEGOODS>();
	public ArrayList<SIMPLEGOODS> promoteSuperList = new ArrayList<SIMPLEGOODS>();
	public ArrayList<SIMPLEGOODS> promoteActivitiesList = new ArrayList<SIMPLEGOODS>();
	public ArrayList<SIMPLEGOODS> promotegoodsList = new ArrayList<SIMPLEGOODS>();
	public ArrayList<SIMPLEGOODS> promoteHotelList = new ArrayList<SIMPLEGOODS>();
	public ArrayList<SHOP> promoteShopList = new ArrayList<SHOP>();
	public ArrayList<SIMPLEGOODS> promoteBuyList = new ArrayList<SIMPLEGOODS>();
	public ArrayList<ArrayList<SIMPLEGOODS>> list=new ArrayList<ArrayList<SIMPLEGOODS>>();
	//	public ArrayList<SIMPLEGOODS> categorygoodsList = new ArrayList<SIMPLEGOODS>();
	public ArrayList<CATEGORYGOODS> categorygoodsList = new ArrayList<CATEGORYGOODS>();
	public ArrayList<PLAYER> playersList = new ArrayList<PLAYER>();

	String pkName;

	public String rootpath;

	public HomeModel(Context context) {
		super(context);
		list.add(promoteBuyList);
		list.add(promoteFoodsList);
		list.add(promoteHotelList);
		pkName = mContext.getPackageName();

		rootpath = context.getCacheDir() + "/Lehuitong/cache";

		readHomeDataCache();
		readGoodsDataCache();
	}
	public void readHomeDataCache() {

		String path = rootpath + "/" + pkName + "/homeData.dat";
		File f1 = new File(path);
		if (f1.exists()) {
			try {
				InputStream is = new FileInputStream(f1);
				InputStreamReader input = new InputStreamReader(is, "UTF-8");
				BufferedReader bf = new BufferedReader(input);

				homeDataCache(bf.readLine());

				bf.close();
				input.close();
				is.close();

			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	public String homeDataCache() {
		String path = rootpath + "/" + pkName + "/homeData.dat";
		File f1 = new File(path);
		String s = null;
		if (f1.exists()) {
			try {
				InputStream is = new FileInputStream(f1);
				InputStreamReader input = new InputStreamReader(is, "UTF-8");
				BufferedReader bf = new BufferedReader(input);

				s = bf.readLine();

				bf.close();
				input.close();
				is.close();

			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return s;
	}

	public void readGoodsDataCache() {
		String path = rootpath + "/" + pkName + "/goodsData.dat";
		File f1 = new File(path);
		if (f1.exists()) {
			try {
				InputStream is = new FileInputStream(f1);
				InputStreamReader input = new InputStreamReader(is, "UTF-8");
				BufferedReader bf = new BufferedReader(input);

				goodsDataCache(bf.readLine());

				bf.close();
				input.close();
				is.close();

			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	public void homeDataCache(String result) {

		try {
			if (result != null) {
				JSONObject jsonObject = new JSONObject(result);

				STATUS responseStatus = STATUS.fromJson(jsonObject.optJSONObject("status"));
				if (responseStatus.succeed == 1) {
					JSONObject dataJsonObject = jsonObject.optJSONObject("data");
					if (null != dataJsonObject) {
						JSONArray playerJSONArray = dataJsonObject.optJSONArray("player");
						if (null != playerJSONArray && playerJSONArray.length() > 0) {
							playersList.clear();
							for (int i = 0; i < playerJSONArray.length(); i++) {
								JSONObject playerJsonObject = playerJSONArray.getJSONObject(i);
								PLAYER playItem = PLAYER.fromJson(playerJsonObject);
								playersList.add(playItem);
							}
						}

						JSONArray superJsonArray = dataJsonObject.optJSONArray("promote_super");

						if (null != superJsonArray && superJsonArray.length() > 0) {
							promoteSuperList.clear();
							for (int i = 0; i < superJsonArray.length(); i++) {
								JSONObject superGoodsJsonObject = superJsonArray.getJSONObject(i);
								SIMPLEGOODS supergoods = SIMPLEGOODS.fromJson(superGoodsJsonObject);
								promoteSuperList.add(supergoods);
							}
						}

						JSONArray activitiesArray = dataJsonObject.optJSONArray("promote_activities");

						if (null != activitiesArray && activitiesArray.length() > 0) {
							promoteActivitiesList.clear();
							for (int i = 0; i < activitiesArray.length(); i++) {
								JSONObject activityJsonObject = activitiesArray.getJSONObject(i);
								SIMPLEGOODS activitygoods = SIMPLEGOODS.fromJson(activityJsonObject);
								promoteActivitiesList.add(activitygoods);
							}
						}

						JSONArray simpleGoodsJsonArray = dataJsonObject.optJSONArray("promote_goods");

						if (null != simpleGoodsJsonArray && simpleGoodsJsonArray.length() > 0) {
							promotegoodsList.clear();
							for (int i = 0; i < simpleGoodsJsonArray.length(); i++) {
								JSONObject simpleGoodsJsonObject = simpleGoodsJsonArray.getJSONObject(i);
								SIMPLEGOODS simplegoods = SIMPLEGOODS.fromJson(simpleGoodsJsonObject);
								promotegoodsList.add(simplegoods);
							}
						}
						JSONArray shopsJsonArray = dataJsonObject.optJSONArray("promote_shops");
						if (null != shopsJsonArray && shopsJsonArray.length() > 0) {
							promoteShopList.clear();
							for (int i = 0; i < shopsJsonArray.length(); i++) {
								JSONObject superGoodsJsonObject = shopsJsonArray.getJSONObject(i);
								SHOP supergoods = SHOP.fromJson(superGoodsJsonObject);
								promoteShopList.add(supergoods);
								
							}
						}else {
							promoteShopList.clear();
						}

					}

				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void goodsDataCache(String result) {

		try {
			if (result != null) {
				JSONObject jsonObject = new JSONObject(result);

				STATUS responseStatus = STATUS.fromJson(jsonObject.optJSONObject("status"));
				if (responseStatus.succeed == 1) {
					JSONArray categorygoodJsonArray = jsonObject.optJSONArray("data");
					if (null != categorygoodJsonArray && categorygoodJsonArray.length() > 0) {
						categorygoodsList.clear();
						for (int i = 0; i < categorygoodJsonArray.length(); i++) {
							JSONObject categoryGoodsJsonObject = categorygoodJsonArray.getJSONObject(i);
							CATEGORYGOODS simplegoods = CATEGORYGOODS.fromJson(categoryGoodsJsonObject);
							categorygoodsList.add(simplegoods);
						}

					}
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void fetchHotSelling() {
		String url = ProtocolConst.HOME_PAGE_LOAD_DATA;

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						fileSave(jo.toString(), "homeData");

						JSONObject dataJsonObject = jo.optJSONObject("data");
						if (null != dataJsonObject) {
							JSONArray playerJSONArray = dataJsonObject.optJSONArray("player");
							if (null != playerJSONArray && playerJSONArray.length() > 0) {
								playersList.clear();
								for (int i = 0; i < playerJSONArray.length(); i++) {
									JSONObject playerJsonObject = playerJSONArray.getJSONObject(i);
									PLAYER playItem = PLAYER.fromJson(playerJsonObject);
									playersList.add(playItem);
								}
							}

							JSONArray activitiesArray = dataJsonObject.optJSONArray("promote_activities");

							if (null != activitiesArray && activitiesArray.length() > 0) {
								promoteActivitiesList.clear();
								for (int i = 0; i < activitiesArray.length(); i++) {
									JSONObject activityJsonObject = activitiesArray.getJSONObject(i);
									SIMPLEGOODS activitygoods = SIMPLEGOODS.fromJson(activityJsonObject);
									promoteActivitiesList.add(activitygoods);
								}
							}
							JSONArray hotelsJsonArray = dataJsonObject.optJSONArray("promote_hotels");

							if (null != hotelsJsonArray && hotelsJsonArray.length() > 0) {
								promoteHotelList.clear();
								for (int i = 0; i < hotelsJsonArray.length(); i++) {
									JSONObject superGoodsJsonObject = hotelsJsonArray.getJSONObject(i);
									SIMPLEGOODS supergoods = SIMPLEGOODS.fromJson(superGoodsJsonObject);
									promoteHotelList.add(supergoods);
								}
							}else {
								promoteHotelList.clear();
							}
							


							JSONArray shopsJsonArray = dataJsonObject.optJSONArray("promote_shops");
							if (null != shopsJsonArray && shopsJsonArray.length() > 0) {
								promoteShopList.clear();
								for (int i = 0; i < shopsJsonArray.length(); i++) {
									JSONObject superGoodsJsonObject = shopsJsonArray.getJSONObject(i);
									SHOP supergoods = SHOP.fromJson(superGoodsJsonObject);
									promoteShopList.add(supergoods);
									
								}
							}else {
								promoteShopList.clear();
							}
							
							

							JSONArray foodArray = dataJsonObject.optJSONArray("promote_foods");

							if (null != foodArray && foodArray.length() > 0) {
								promoteFoodsList.clear();
								
								for (int i = 0; i < foodArray.length(); i++) {
									JSONObject activityJsonObject = foodArray.getJSONObject(i);
									SIMPLEGOODS activitygoods = SIMPLEGOODS.fromJson(activityJsonObject);
									promoteFoodsList.add(activitygoods);
								}
							}else {
								promoteFoodsList.clear();
								
							}
							


							JSONArray simpleGoodsJsonArray = dataJsonObject.optJSONArray("promote_buys");

							if (null != simpleGoodsJsonArray && simpleGoodsJsonArray.length() > 0) {
								promoteBuyList.clear();
								for (int i = 0; i < simpleGoodsJsonArray.length(); i++) {
									JSONObject simpleGoodsJsonObject = simpleGoodsJsonArray.getJSONObject(i);
									SIMPLEGOODS simplegoods = SIMPLEGOODS.fromJson(simpleGoodsJsonObject);
									promoteBuyList.add(simplegoods);
								}
							} else {
								promoteBuyList.clear();
							}

							HomeModel.this.OnMessageResponse(url, jo, status);

						}

					}

				} catch (JSONException e) {

				}

			}

		};

		cb.url(url).type(JSONObject.class);

		aq.ajax(cb);

	}

	public void fetchCategoryGoods() {
		// String url = ProtocolConst.CATEGORYGOODS;
		String url = "";

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				done(url, jo, status);
				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					if (responseStatus.succeed == 1) {
						fileSave(jo.toString(), "goodsData");
						JSONArray categorygoodJsonArray = jo.optJSONArray("data");
						if (null != categorygoodJsonArray && categorygoodJsonArray.length() > 0) {
							categorygoodsList.clear();
							for (int i = 0; i < categorygoodJsonArray.length(); i++) {
								JSONObject categoryGoodsJsonObject = categorygoodJsonArray.getJSONObject(i);
								CATEGORYGOODS simplegoods = CATEGORYGOODS.fromJson(categoryGoodsJsonObject);
								categorygoodsList.add(simplegoods);
							}
							HomeModel.this.OnMessageResponse(url, jo, status);
						} else {
							categorygoodsList.clear();
						}

					}

				} catch (JSONException e) {
				}

			}

		};

		cb.url(url).type(JSONObject.class);
		aq.ajax(cb);

	}

	protected void done(String url, JSONObject jo, AjaxStatus status) {
		String localUrl = url;
		JSONObject result = jo;
	}

	public String web;

	public void helpArticle(int article_id) {

		// String url = ProtocolConst.ARTICLE;
		String url = "";

		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					HomeModel.this.callback(url, jo, status);
				} catch (Exception e1) {

					e1.printStackTrace();
				}

				try {
					STATUS responseStatus = STATUS.fromJson(jo.optJSONObject("status"));
					web = jo.getString("data").toString();

					HomeModel.this.OnMessageResponse(url, jo, status);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};

		SESSION session = SESSION.getInstance();

		JSONObject requestJsonObject = new JSONObject();

		Map<String, String> params = new HashMap<String, String>();
		try {
			requestJsonObject.put("session", session.toJson());
			requestJsonObject.put("article_id", article_id);
		} catch (JSONException e) {

		}

		params.put("json", requestJsonObject.toString());

		cb.url(url).type(JSONObject.class).params(params);
		aq.ajax(cb);

	}

	// 缓存数据
	private PrintStream ps = null;

	public void fileSave(String result, String name) {

		String path = rootpath + "/" + pkName;

		File filePath = new File(path);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}

		File file = new File(filePath + "/" + name + ".dat");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			ps = new PrintStream(fos);
			ps.print(result);
			ps.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
