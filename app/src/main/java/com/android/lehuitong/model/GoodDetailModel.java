package com.android.lehuitong.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.net.wifi.WifiConfiguration.Status;
import android.service.textservice.SpellCheckerService.Session;
import android.view.Gravity;
import android.widget.Toast;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.BeeFramework.view.MyProgressDialog;
import com.BeeFramework.view.ToastView;
import com.android.lehuitong.protocol.GOODS;
import com.android.lehuitong.protocol.PAGINATION;
import com.android.lehuitong.protocol.PHOTO;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SESSION;
import com.android.lehuitong.protocol.STATUS;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;

/**
 * 商品详情页相关
 * 
 * @author shenlw
 * 
 */
public class GoodDetailModel extends BaseModel {

//	public ArrayList<PHOTO> photoList = new ArrayList<PHOTO>();
	public int goodId;
	public GOODS goodDetail = new GOODS();
	private Context context;

	public GoodDetailModel(Context context) {
		super(context);
		this.context = context;
	}

	/**
	 * 商品详情
	 * @param goodId 商品id
	 */
	public void fetchGoodDetail(String goodId) {
		String url = ProtocolConst.GOODS;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {

				try {
					GoodDetailModel.this.callback(url, jo, status);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					STATUS responseStatus = STATUS.fromJson(jo
							.optJSONObject("status"));
					PAGINATION pagination = PAGINATION.fromJson(jo
							.optJSONObject("pagination"));
					if (responseStatus.succeed == 1) {
						JSONObject dataJSONObject = jo.optJSONObject("data");

						if (null != dataJSONObject) {
							GoodDetailModel.this.goodDetail = GOODS
									.fromJson(dataJSONObject);
							GoodDetailModel.this.OnMessageResponse(url, jo,
									status);
						}
					}
				} catch (JSONException e) {
				}
			}
		};
		SESSION session = SESSION.getInstance();
		JSONObject requestJsonObject = new JSONObject();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			requestJsonObject.put("goods_id", goodId);
			requestJsonObject.put("session", session.toJson());
		} catch (JSONException e) {
		}
		params.put("json", requestJsonObject.toString());
		cb.url(url).type(JSONObject.class).params(params);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);
	}

	public void seegoods(int goods_id) {
		String url = ProtocolConst.GOODS;
		BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				try {
					GoodDetailModel.this.callback(url, jo, status);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					STATUS status1 = STATUS
							.fromJson(jo.optJSONObject("status"));

					if (status1.succeed == 1) {
						GoodDetailModel.this.OnMessageResponse(url, jo, status);

					} else if (status1.succeed == 0) {
						Toast.makeText(context, "不存在商品的详细信息！",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		};

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goods_id", goods_id);
		cb.url(url).type(JSONObject.class).params(map);
		MyProgressDialog dialog = new MyProgressDialog(mContext, "请稍后...");
		aq.progress(dialog.mDialog).ajax(cb);
	}

}
