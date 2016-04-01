package com.android.lehuitong.activity;

import java.io.Serializable;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.R.integer;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.LehuitongApp;
import com.android.lehuitong.adapter.TallyOrderAdapter;
import com.android.lehuitong.model.AddressModel;
import com.android.lehuitong.model.KtvAndCouponsOrderModel;
import com.android.lehuitong.model.OrderModel;
import com.android.lehuitong.pay.alipay.PayDemoActivity;
import com.android.lehuitong.pay.wechat.Constants;
import com.android.lehuitong.pay.wechat.MD5;
import com.android.lehuitong.pay.wechat.Util;
import com.android.lehuitong.protocol.ADDRESS;
import com.android.lehuitong.protocol.BONUS;
import com.android.lehuitong.protocol.GOODORDER;
import com.android.lehuitong.protocol.GOODS_LIST;
import com.android.lehuitong.protocol.ORDER_GOODS_LIST;
import com.android.lehuitong.protocol.ORDER_INFO;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SELECT_SHOPPING;
import com.android.lehuitong.protocol.SHIPPING;
import com.android.lehuitong.view.TallyOrderDialog;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.funmi.lehuitong.R;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 购物车订单结算页面
 * 
 * @author shenlw
 */
public class TallyOrderActivity extends BaseActivity implements
		OnClickListener, BusinessResponse {
	private ImageView title_back;
	private ImageView pay_wechat;
	private ImageView pay_alipay;
	private TextView title_text;

	private TextView order_name;
	private TextView order_number;
	private TextView order_address;
	private TextView coupons_state;
	private TextView good_price;
	private TextView ship_price;
	private TextView bonus_price;
	private Boolean flag = true;
	private TextView Receive_way_text;
	private LinearLayout self_recevie_layout;
	private RelativeLayout write_info;
	private RelativeLayout pay_coupons;
	private RelativeLayout Receive_way;
	private RelativeLayout pay_wechat_layout;
	private RelativeLayout pay_alipay_layout;
	private Button pay_now;
	private TallyOrderDialog dialog;
	private XListView orderList;
	private View footer;
	/** 红包是否可用 */
	private int couponStatus;
	private boolean hasAddress = true;
	private ORDER_INFO order_INFO;
	private ADDRESS address;
	private SELECT_SHOPPING total;
	// private List<BONUS> bonus=new ArrayList<BONUS>();
	private Serializable bonus;
	private List<GOODORDER> goodlist = new ArrayList<GOODORDER>();
	private List<GOODS_LIST> goodsOrderList = new ArrayList<GOODS_LIST>();
	private List<ORDER_GOODS_LIST> list = new ArrayList<ORDER_GOODS_LIST>();
	public List<SHIPPING> shipping_list = new ArrayList<SHIPPING>();
	private TallyOrderAdapter tallyOrderAdapter;
	/**送货地址id*/
	private int addressId;
	private OrderModel orderModel;
	private ArrayList<Integer> goodsIds = new ArrayList<Integer>();
	private double totalMoney = 0;// 商品总价
	private String body;// 商品名字
	private String nonce_str;// 介绍
	private String out_trade_no;// 订单号
	int status;
	private TextView totalMoneyTextView;
	private int shipping_id=0;
	/**默认收货ID*/
	private int default_shipid;
	/**红包id*/
	private int bonus_id;
	private Intent intent;
	boolean payway = true, canintent = true;
	private ImageView orderBack;
	private ImageView orderBack1;
	private ImageView order_back2;
	// 微信支付
	private static final String TAG = "MicroMsg.SDKSample.PayActivity";
	private PayReq req;
	final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
	private Map<String, String> resultunifiedorder;
	private StringBuffer sb;
	private TextView jianhao;
	private KtvAndCouponsOrderModel ktvorder;
	private String address_id;
	private AddressModel addressModel;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tally_order);
		init();
		LehuitongApp.getInstance().addActivity(this);
		req = new PayReq();
		sb = new StringBuffer();
		msgApi.registerApp(Constants.APP_ID);
		String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
	}
	@Override
	protected void onResume() {
		super.onResume();
		orderModel.selectShipping(shipping_id);
		order_address.setVisibility(View.VISIBLE);
		order_number.setVisibility(View.VISIBLE);
	}
	@SuppressWarnings("unchecked")
	private void init() {
		tallyOrderAdapter = new TallyOrderAdapter(this);
		orderList = (XListView) findViewById(R.id.order_list);
		title_back = (ImageView) findViewById(R.id.title_back);
		title_text = (TextView) findViewById(R.id.title_text);
		title_text.setText("订单结算");
		footer = LayoutInflater.from(this).inflate(R.layout.footer_tally_order,
				null);
		self_recevie_layout = (LinearLayout) footer
				.findViewById(R.id.self_recevie_layout);
		pay_wechat = (ImageView) footer.findViewById(R.id.pay_wechat);
		pay_alipay = (ImageView) footer.findViewById(R.id.pay_alipay);
		Receive_way_text = (TextView) footer
				.findViewById(R.id.Receive_way_text);
		pay_coupons = (RelativeLayout) footer.findViewById(R.id.pay_coupons);
		Receive_way = (RelativeLayout) footer.findViewById(R.id.Receive_way);
		pay_wechat_layout = (RelativeLayout) footer
				.findViewById(R.id.pay_wechat_layout);
		pay_alipay_layout = (RelativeLayout) footer
				.findViewById(R.id.pay_alipay_layout);
		write_info = (RelativeLayout) footer.findViewById(R.id.write_info);
		pay_now = (Button) footer.findViewById(R.id.pay_now);
		totalMoneyTextView = (TextView) footer.findViewById(R.id.order_money);
		order_name = (TextView) footer.findViewById(R.id.order_name);
		order_address = (TextView) footer.findViewById(R.id.order_address);
		order_number = (TextView) footer.findViewById(R.id.order_number);
		coupons_state = (TextView) footer.findViewById(R.id.coupons_state);
		good_price = (TextView) footer.findViewById(R.id.good_price);
		ship_price = (TextView) footer.findViewById(R.id.ship_price);
		bonus_price = (TextView) footer.findViewById(R.id.bonus_price);
		orderBack=(ImageView) footer.findViewById(R.id.order_back);
		orderBack1=(ImageView) footer.findViewById(R.id.order_back1);
		order_back2=(ImageView) footer.findViewById(R.id.order_back2);
//		jianhao=(TextView) footer.findViewById(R.id.jianhao);
		status = getIntent().getExtras().getInt("status");
		orderList.setPullLoadEnable(false);
		orderList.setPullRefreshEnable(false);
		orderList.addFooterView(footer);
		orderList.setAdapter(tallyOrderAdapter);
		orderModel = new OrderModel(this);
		orderModel.addResponseListener(this);
		if (status == 1) {
			orderBack.setVisibility(View.INVISIBLE);
			orderBack1.setVisibility(View.INVISIBLE);
			order_back2.setVisibility(View.INVISIBLE);

			String order_id=getIntent().getExtras().getString("order_id");
			orderModel.seeDetails(order_id);
			ktvorder = new KtvAndCouponsOrderModel(this);
			ktvorder.addResponseListener(this);
			ktvorder.getOrderDetail(order_id);
			
		} else if (status != 1) {
			goodsOrderList = (List<GOODS_LIST>) getIntent().getExtras()
					.getSerializable("order");
			goodsIds = getIntent().getIntegerArrayListExtra("goods_ids");
			address = (ADDRESS) getIntent().getExtras().getSerializable(
					"address");
			total=(SELECT_SHOPPING) getIntent().getExtras().getSerializable("total");
			default_shipid=getIntent().getExtras().getInt("shipping_id");
			totalMoney=total.amount_formated;
			totalMoneyTextView.setText(totalMoney+"元");
			good_price.setText(total.formated_goods_price+"元");
			ship_price.setText(total.pay_fee_formated+"元");	
			bonus_price.setText(total.bonus_formated+"元");
			bonus = getIntent().getExtras().getSerializable("bonus");
			couponStatus = getIntent().getExtras().getInt("statu");
			shipping_list=(List<SHIPPING>) getIntent().getExtras().getSerializable("shipping");
			if (address!=null&&default_shipid==9) {
				if (String.valueOf(address.address_id)!=null) {
					address_id=address.address_id;
				}
				order_address.setText(address.country_name + address.province_name
						+ address.city_name + address.district_name);
				order_name.setText(address.consignee);
				order_number.setText(address.mobile);
				Receive_way_text.setText("自提");
				shipping_id=default_shipid;
				canintent=false;
			}else if (address!=null&&default_shipid==4) {
				if (String.valueOf(address.address_id)!=null) {
					address_id=address.address_id;
				}
				Receive_way_text.setText("快递");
				shipping_id=default_shipid;
				canintent=true;
			}
			
			
		}
		if (goodsOrderList.size() > 0) {
			tallyOrderAdapter.bindData(goodsOrderList);
		} else {
			tallyOrderAdapter.bindDatas(list);
		}
		if (couponStatus == 1) {
			coupons_state.setText("有可用");
		}
		setOnClickListener();
		dialog = new TallyOrderDialog(TallyOrderActivity.this,
				R.style.customer_dialog) {
			public void choseway(String way) {
				super.choseway(way);
				Receive_way_text.setText(way);
				shipping_id= shipping_list.get(0).shipping_id;
				dialog.dismiss();
				order_address.setText("");
				order_number.setText("");
				order_name.setText("");
				orderModel.selectShipping(shipping_id);
				canintent=true;
			}
			public void choseway_self(String way) {
				super.choseway_self(way);
				Receive_way_text.setText(way);
				shipping_id = shipping_list.get(1).shipping_id;
				dialog.dismiss();
				if (address!=null) {
					order_address.setVisibility(View.VISIBLE);
					order_number.setVisibility(View.VISIBLE);
					order_address.setText(address.country_name + address.province_name
							+ address.city_name + address.district_name);
					order_name.setText(address.consignee);
					order_number.setText(address.mobile);
				}
				orderModel.selectShipping(shipping_id);
				canintent=false;
			}
		};
	}

	void setOnClickListener() {
		title_back.setOnClickListener(this);
		pay_now.setOnClickListener(this);
		pay_alipay_layout.setOnClickListener(this);
		pay_wechat_layout.setOnClickListener(this);
		if (status!=1) {
			
			Receive_way.setOnClickListener(this);
			pay_coupons.setOnClickListener(this);
		}
		write_info.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.pay_now:
			if(flag == false){	
				return;
			}else{
				flag = false;
			if (status == 1) {
				if (payway == true) {// 支付宝
					intent = new Intent(this, PayDemoActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("price", totalMoney + "");
					bundle.putString("name", body);
					bundle.putString("subject", nonce_str);
					bundle.putString("number", out_trade_no);
					intent.putExtras(bundle);
					startActivity(intent);
				} else if (payway == false) {// 微信
					GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
					getPrepayId.execute();
				}
			} else if (payway == true &&shipping_id!=0&&address_id!="") {
				if(order_name.getText().toString()!="您没有设置快递地址，点击这里新增地址"){
					orderModel.confirmOrder(bonus_id, shipping_id, 1, Integer.valueOf(address_id), goodsIds);
				}else{
					Toast.makeText(this, "请选择收货方式和地址", Toast.LENGTH_SHORT).show();
				}
			} else if (payway == false&&shipping_id!=0&&address_id!=""|order_name.getText().toString()!="您没有设置快递地址，点击这里新增地址") {
				if(order_name.getText().toString()!="您没有设置快递地址，点击这里新增地址"){
					orderModel.confirmOrder(bonus_id, shipping_id, 2, Integer.valueOf(address_id), goodsIds);
				}else{
					Toast.makeText(this, "请选择收货方式和地址", Toast.LENGTH_SHORT).show();
				}
			}
			}
			
			break;
		case R.id.pay_alipay_layout:
			pay_alipay
					.setImageResource(R.drawable.my_shopping_cart_choice_icon);
			pay_wechat
					.setImageResource(R.drawable.my_shopping_cart_not_selected_icon);
			payway = true;
			break;
		case R.id.pay_wechat_layout:
			pay_alipay
					.setImageResource(R.drawable.my_shopping_cart_not_selected_icon);
			pay_wechat
					.setImageResource(R.drawable.my_shopping_cart_choice_icon);
			payway = false;
			break;
		case R.id.Receive_way:
			dialog.show();
			break;
		case R.id.pay_coupons:
			if (couponStatus == 1) {
				intent = new Intent(this, MyCouponsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("bonus", bonus);
				bundle.putInt("status", 1);
				intent.putExtras(bundle);
				startActivityForResult(intent, 110);
			}
			break;
		case R.id.write_info:
			if (status!=1&&canintent==true) {
				if(orderModel.select_SHOPPING.address == null){
					intent = new Intent(this, NewAddressActivity.class);
					startActivity(intent);
				}else{
				intent = new Intent(TallyOrderActivity.this,
						AddressManagerActivity.class);
				intent.putExtra("isSelectAddress", true);
				startActivityForResult(intent, 111);
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			if (requestCode == 111) {
				
//				addressId = Integer.parseInt(data.getStringExtra("address_id"));
				order_address.setText(data.getStringExtra("address"));
				order_name.setText(data.getStringExtra("name"));
				order_number.setText(data.getStringExtra("mobile"));
				address_id = data.getStringExtra("id");
				if(TextUtils.isEmpty(order_name.getText().toString())){
					order_name.setText("您没有设置快递地址，点击这里新增地址");
//					intent = new Intent(this, NewAddressActivity.class);
//					startActivity(intent);
				}
			} else if (requestCode == 110) {
				if (data.getStringExtra("bonusName")!=null&&data.getStringExtra("bonusId")!=null) {
					bonus_price.setText(data.getStringExtra("bonusPrice"));
					coupons_state.setText(data.getStringExtra("bonusName"));
					bonus_id =Integer.parseInt(data.getStringExtra("bonusId")) ;
					orderModel.selectBonus(bonus_id);
				}
			}

		}
	}

	/**
	 * 生成签名
	 */

	private String genPackageSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);

		String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
		Log.e("orion", packageSign);
		return packageSign;
	}

	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);

		this.sb.append("sign str\n" + sb.toString() + "\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
		Log.e("orion", appSign);
		return appSign;
	}

	private String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<" + params.get(i).getName() + ">");

			sb.append(params.get(i).getValue());
			sb.append("</" + params.get(i).getName() + ">");
		}
		sb.append("</xml>");

		Log.e("orion", sb.toString());
		// return sb.toString();
		// 将得到的xml结果转码。否则订单描述存在汉字时 签名出错。
		try {
			return new String(sb.toString().getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	private class GetPrepayIdTask extends
			AsyncTask<Void, Void, Map<String, String>> {

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(TallyOrderActivity.this,
					getString(R.string.app_tip),
					getString(R.string.getting_prepayid));
		}

		@Override
		protected void onPostExecute(Map<String, String> result) {

			sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");

			resultunifiedorder = result;
			genPayReq();
			sendPayReq(dialog);
			finish();
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected Map<String, String> doInBackground(Void... params) {

			String url = String
					.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
			String entity = genProductArgs();

			Log.e("orion", entity);

			byte[] buf = Util.httpPost(url, entity);

			String content = new String(buf);
			Log.e("orion", content);
			Map<String, String> xml = decodeXml(content);

			return xml;
		}

	}

	public Map<String, String> decodeXml(String content) {

		try {
			Map<String, String> xml = new HashMap<String, String>();
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {

				String nodeName = parser.getName();
				switch (event) {
				case XmlPullParser.START_DOCUMENT:

					break;
				case XmlPullParser.START_TAG:

					if ("xml".equals(nodeName) == false) {
						// 实例化student对象
						xml.put(nodeName, parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				}
				event = parser.next();
			}

			return xml;
		} catch (Exception e) {
			Log.e("orion", e.toString());
		}
		return null;

	}

	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	/** 随机生成订单号 */
	private String genOutTradNo() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	//
	private String genProductArgs() {
		StringBuffer xml = new StringBuffer();
		int i = (int) (totalMoney * 100);
		String price = String.valueOf(i);

		SharedPreferences sp = TallyOrderActivity.this.getSharedPreferences(
				"number", Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putString("number", out_trade_no);
		edit.commit();
		try {
			String nonceStr = genNonceStr();
			xml.append("</xml>");
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams
					.add(new BasicNameValuePair("appid", Constants.APP_ID));
			packageParams.add(new BasicNameValuePair("body", body));
			packageParams
					.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
			packageParams.add(new BasicNameValuePair("notify_url",
					"http://121.40.35.3/test"));
			packageParams.add(new BasicNameValuePair("out_trade_no",
					out_trade_no));// 订单号
			packageParams.add(new BasicNameValuePair("spbill_create_ip",
					"127.0.0.1"));
			packageParams.add(new BasicNameValuePair("total_fee", price));
			packageParams.add(new BasicNameValuePair("trade_type", "APP"));
			String sign = genPackageSign(packageParams);
			packageParams.add(new BasicNameValuePair("sign", sign));
			String xmlstring = toXml(packageParams);
			return xmlstring;
		} catch (Exception e) {
			Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
			return null;
		}
	}

	private void genPayReq() {
		req.appId = Constants.APP_ID;
		req.partnerId = Constants.MCH_ID;
		req.prepayId = resultunifiedorder.get("prepay_id");
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());
		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
		req.sign = genAppSign(signParams);
		sb.append("sign\n" + req.sign + "\n\n");
		Log.e("orion", signParams.toString());
	}

	private void sendPayReq(ProgressDialog dialog) {
		boolean isApiSuccess = msgApi.sendReq(req);
		if (isApiSuccess && dialog != null) {
			dialog.dismiss();
		}
	}
	public static Double add(Double v1, Double v2) {  
		   BigDecimal b1 = new BigDecimal(v1.toString());  
		   BigDecimal b2 = new BigDecimal(v2.toString());  
		   return new Double(b1.add(b2).doubleValue());  
		}  
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.FLOW_DONE)) {
			for (int i = 0; i < goodsOrderList.size(); i++) {
				body = orderModel.info.subject;
				nonce_str = orderModel.info.desc;
				out_trade_no = orderModel.info.order_sn;
			}
			Toast.makeText(this, "下单成功", Toast.LENGTH_SHORT).show();
			if (payway == true) {// 支付宝
				intent = new Intent(this, PayDemoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("price", totalMoney + "");
				bundle.putString("name", body);
				bundle.putString("subject", nonce_str);
				bundle.putString("number", out_trade_no);
				intent.putExtras(bundle);
				startActivity(intent);
				
			} else if (payway == false) {// 微信
				GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
				getPrepayId.execute();
			}
		} else if (url.endsWith(ProtocolConst.FLOW_SELECT_SHIPPING)) {
			totalMoney=orderModel.select_SHOPPING.totals.amount_formated;
			totalMoneyTextView.setText(totalMoney+"元");
			good_price.setText(orderModel.select_SHOPPING.totals.formated_goods_price+"元");
			ship_price.setText(orderModel.select_SHOPPING.totals.pay_fee_formated+"元");
			if(orderModel.select_SHOPPING.address == null){
				order_name.setText("您没有设置快递地址，点击这里新增地址");
				order_address.setVisibility(View.GONE);
				order_number.setVisibility(View.GONE);
			}else{	
			String country_name=orderModel.select_SHOPPING.address.country_name;
			String province_name=orderModel.select_SHOPPING.address.province_name;
			String city_name=orderModel.select_SHOPPING.address.city_name;
			String district_name=orderModel.select_SHOPPING.address.district_name;
			address_id = orderModel.select_SHOPPING.address.address_id;
			order_address.setText(country_name+province_name+city_name+district_name);
			order_name.setText(orderModel.select_SHOPPING.address.consignee);
			order_number.setText(orderModel.select_SHOPPING.address.mobile);
			}
			
		}else if (url.endsWith(ProtocolConst.FLOW_SELECT_BONUS)) {
			good_price.setText(orderModel.select_SHOPPING.formated_goods_price+"元");
			ship_price.setText(orderModel.select_SHOPPING.pay_fee_formated+"元");
			totalMoney=orderModel.select_SHOPPING.amount_formated;
			totalMoneyTextView.setText(totalMoney+"元");
			jianhao.setVisibility(View.VISIBLE);
			if(orderModel.select_SHOPPING.bonus_formated>(orderModel.select_SHOPPING.pay_fee_formated+orderModel.select_SHOPPING.formated_goods_price)){
				bonus_price.setText(orderModel.select_SHOPPING.formated_goods_price+"元");
			}else{
			bonus_price.setText(orderModel.select_SHOPPING.bonus_formated+"元");
			}
		}else if (url.endsWith(ProtocolConst.ORDER_DATAIL)) {
			tallyOrderAdapter.bindDatas(orderModel.detail.goods_list);
			tallyOrderAdapter.getPrice(orderModel.detail.goods_amount_fee);
			orderList.setAdapter(tallyOrderAdapter);
			body = orderModel.detail.goods_list.get(0).name;
			nonce_str =orderModel.detail.goods_list.get(0).name;
			out_trade_no = orderModel.detail.order_sn;
			totalMoney=Double.valueOf(orderModel.detail.formated_total_fee);
			totalMoneyTextView.setText(orderModel.detail.formated_total_fee + "元");
			Receive_way_text.setText(orderModel.detail.shipping.shipping_name);
			order_name.setText(orderModel.detail.address.consignee);
			try {
				Log.i("orderModel.detail.bonus.type_name", orderModel.detail.bonus.type_name);
				if(orderModel.detail.bonus.type_name.equals(null)){
					coupons_state.setText("无可用");
				}else
				coupons_state.setText(orderModel.detail.bonus.type_name);
				
			} catch (Exception e) {
			}
			order_number.setText(orderModel.detail.address.mobile);
			order_address.setText(orderModel.detail.address.country_name
					+ orderModel.detail.address.province_name
					+ orderModel.detail.address.city_name
					+ orderModel.detail.address.district_name
					+ orderModel.detail.address.address);
		double price=0;
		for (int i = 0; i < orderModel.detail.goods_list.size(); i++) {
			price=add(price, Double.valueOf(orderModel.detail.goods_list.get(i).formated_shop_price)*Double.valueOf(orderModel.detail.goods_list.get(i).goods_number));
		}
		DecimalFormat format = new DecimalFormat("#####0.00");
		good_price.setText(format.format(price)+"");
//		good_price.setText(orderModel.detail.goods_amount_fee);
		ship_price.setText(orderModel.detail.shipping.shipping_fee);
		
			if(Double.valueOf(orderModel.detail.formated_bonus)>add(Double.valueOf(orderModel.detail.goods_amount_fee),Double.valueOf(orderModel.detail.shipping.shipping_fee))){
				bonus_price.setText(orderModel.detail.goods_amount_fee);
			}else{
			bonus_price.setText(orderModel.detail.formated_bonus);}
			jianhao.setVisibility(View.VISIBLE);
		}
		if(url.endsWith(ProtocolConst.ORDER_TICKET_DETAIL)){
			if (ktvorder.ktvOrderList.get(0).order_status.equals("1")) {
				coupons_state.setText("有可用");
			}else{
				coupons_state.setText("无可用");
			}
			if(ktvorder.ktvOrderList.get(0).bonus_name.equals("")){
				coupons_state.setText("无可用");
			}else{
				coupons_state.setText(ktvorder.ktvOrderList.get(0).bonus_name);
			}
		}
		
	}
	@Override
	protected void onPause() {
		super.onPause();
		shipping_id= shipping_list.get(0).shipping_id;
		if(flag == false){
			finish();
		}else{}
	}
}