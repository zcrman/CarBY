package com.android.lehuitong.activity;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
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
import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.LehuitongApp;
import com.android.lehuitong.model.KtvAndCouponsOrderModel;
import com.android.lehuitong.pay.alipay.PayDemoActivity;
import com.android.lehuitong.pay.wechat.Constants;
import com.android.lehuitong.pay.wechat.MD5;
import com.android.lehuitong.pay.wechat.Util;
import com.android.lehuitong.protocol.KTV_ORDER;
import com.android.lehuitong.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 订包详情
 * 
 * @author shenlw
 * 
 */
public class KtvOrderDetailActivity extends BaseActivity implements
		OnClickListener, BusinessResponse {

	private TextView topTitle;
	private ImageView topBack;

	private RelativeLayout hi_pay_coupons;
	private WebImageView shopImage;
	private TextView shopName;
	private TextView shopPhone;
	private TextView shopAddress;

	private TextView orderDate;
	private TextView orderRoom;
	private TextView orderPhone;
	private TextView orderPackage;
	private TextView orderMoney;
	private Boolean flag = true;
	private ImageView wechatPay;
	private ImageView alipayPay;
	private RelativeLayout wetchatLayout;
	private RelativeLayout alipayLayout;
	private LinearLayout no_pakege_layout;
	private LinearLayout order_house;
	private LinearLayout no_room_layout;
	private TextView tip;
	private TextView hi_coupons_state;

	private Button payNowBtn;

	private Intent intent;
	private String shop_name;
	private String shop_id;
	private String shop_address;
	private String shop_phone;
	private int selectedPackageId = 0;
	private String date;
	private String room;
	private String phone;
  private 	String number=null;
	private String money;
	private String package_name;
	private String consignee;
	private String goods_id = "";
	/** 红包是否可用 */
	private int couponStatus;
	private int bonus_id;
	private KTV_ORDER ktv_order;
	private int i = 0;// 订单类型
	private int status;
	private boolean payway = true;
	/**红包价格*/
	private String bonusPrice;
	private KtvAndCouponsOrderModel ktvOrderModel;
	private String order_id;
	private static final String TAG = "MicroMsg.SDKSample.PayActivity";
	
	
	PayReq req;
	final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
	Map<String, String> resultunifiedorder;
	StringBuffer sb;
	private String type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hiorder_detail);
		initView();
		LehuitongApp.getInstance().addActivity(this);
		req = new PayReq();
		sb = new StringBuffer();
		msgApi.registerApp(Constants.APP_ID);
		String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
	}

	private void initView() {
		hi_pay_coupons = (RelativeLayout) findViewById(R.id.hi_pay_coupons);
		no_room_layout = (LinearLayout) findViewById(R.id.no_room_layout);
		order_house = (LinearLayout) findViewById(R.id.order_house);
		no_pakege_layout = (LinearLayout) findViewById(R.id.no_pakege_layout);
		topTitle = (TextView) findViewById(R.id.title_text);
		hi_coupons_state = (TextView) findViewById(R.id.hi_coupons_state);
		topBack = (ImageView) findViewById(R.id.title_back);
		topTitle.setText("订包详情");
		topBack.setVisibility(View.VISIBLE);

		shopImage = (WebImageView) findViewById(R.id.shop_image);
		shopName = (TextView) findViewById(R.id.shop_name);
		shopPhone = (TextView) findViewById(R.id.shop_phone);
		shopAddress = (TextView) findViewById(R.id.shop_address);

		orderDate = (TextView) findViewById(R.id.order_date);
		orderRoom = (TextView) findViewById(R.id.order_room);
		orderPhone = (TextView) findViewById(R.id.order_phone);
		orderPackage = (TextView) findViewById(R.id.order_package);
		orderMoney = (TextView) findViewById(R.id.order_money);
		wechatPay = (ImageView) findViewById(R.id.order_wechat);
		alipayPay = (ImageView) findViewById(R.id.order_alipay);
		wetchatLayout = (RelativeLayout) findViewById(R.id.ktv_wechat_layout);
		alipayLayout = (RelativeLayout) findViewById(R.id.ktv_alipay_layout);

		payNowBtn = (Button) findViewById(R.id.order_pay_now);
		status = getIntent().getExtras().getInt("status");
		order_id=getIntent().getExtras().getString("order_id");
		intent = getIntent();
		i = intent.getIntExtra("i", 0);
		ktv_order = (KTV_ORDER) getIntent().getExtras()
				.getSerializable("order");
		if (ktv_order.seller != null
				&& ktv_order.seller.seller_id.equals("null")) {
			shop_id = "";
		} else if (ktv_order.seller != null) {
			shop_id = ktv_order.seller.seller_id;
			shop_name = ktv_order.seller.seller_name;
			shop_address = ktv_order.seller.seller_address;
			shop_phone = ktv_order.seller.seller_phone;
		}
		if (status==1&&ktv_order.appointment_time.length()>0) {
			String[] date=ktv_order.appointment_time.split("/");
			 String year = null;
			 String month = null;
			 String mdate = null;
			 for (int i = 0; i < date.length; i++) {
			 year= date[0];
			 month= date[1];
			 mdate= date[2];
			 }
			 String date2=mdate.substring(0, 2);
			 String time= year+"-"+month+"-"+date2;
			orderDate.setText(time);
		}else {
			
			date = intent.getStringExtra("date");
		}
		phone = intent.getStringExtra("phone");
		room = intent.getStringExtra("room");
		if (status==1) {
			number=ktv_order.order_sn;
			 int cat_id = ktv_order.cat_id;
			phone = ktv_order.user_phone;
			orderPhone.setText(phone);
			if (cat_id==1) {
				this.orderRoom.setText("大包");
				room="大包";
			}else if (cat_id==2) {
				this.orderRoom.setText("中包");
				room="中包";
			}else if (cat_id==3) {
				this.orderRoom.setText("小包");
				room="小包";
			}else {
				this.orderRoom.setText("您没有订包");
				room="您没有订包";
			}
		}
		consignee = ktv_order.consignee;
		couponStatus = ktv_order.allow_use_bonus;
		if (couponStatus == 1) {
			hi_coupons_state.setText("有可用");
		}
		if (ktv_order.good != null) {
			package_name = ktv_order.good.good_name;
			money = ktv_order.good.good_price;
			goods_id = ktv_order.good.good_id;
		}

		if (i == 2) {// 订包不订套餐
			shopName.setText(shop_name);
			shopPhone.setText(shop_phone);
			shopAddress.setText(shop_address);
			orderDate.setText(date);
			orderRoom.setText(room);
			orderPhone.setText(phone);
			shopImage.setImageWithURL(this,ktv_order.seller.seller_pic, R.drawable.default_image);
			no_pakege_layout.setVisibility(View.GONE);
			payNowBtn.setText("立即预定");
//			tip.setVisibility(View.GONE);
		} else if (i == 3) {// 不订包不订套餐
			shopName.setText(shop_name);
			shopPhone.setText(shop_phone);
			shopAddress.setText(shop_address);
			shopImage.setImageWithURL(this, ktv_order.seller.seller_pic, R.drawable.default_image);
			order_house.setVisibility(View.GONE);
		} else if (i == 4) {// 订套餐不订包
			shopName.setText(shop_name);
			shopPhone.setText(shop_phone);
			shopAddress.setText(shop_address);
			shopImage.setImageWithURL(this,  ktv_order.seller.seller_pic, R.drawable.default_image);
			orderPackage.setText(package_name);
			orderMoney.setText("￥"+money);
			no_room_layout.setVisibility(View.GONE);
		} else if (i == 1) {
			shopName.setText(shop_name);
			shopPhone.setText(shop_phone);
			shopAddress.setText(shop_address);
			orderDate.setText(date);
			orderRoom.setText(room);
			orderPhone.setText(phone);
			orderPackage.setText(package_name);
			orderMoney.setText("￥"+money);
			shopImage.setImageWithURL(this,  ktv_order.seller.seller_pic, R.drawable.default_image);
		}

		ktvOrderModel = new KtvAndCouponsOrderModel(this);
		ktvOrderModel.addResponseListener(this);
		if (order_id!=null) {
			ktvOrderModel.getOrderDetail(order_id);
		}
		setOnClickListener();

	}

	private void setOnClickListener() {

		wetchatLayout.setOnClickListener(this);
		alipayLayout.setOnClickListener(this);
		payNowBtn.setOnClickListener(this);
		topBack.setOnClickListener(this);
		hi_pay_coupons.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.hi_pay_coupons:
			if (couponStatus == 1) {
				intent = new Intent(this, MyCouponsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("bonus", ktv_order.bonuslist);
				bundle.putInt("status", 1);
				intent.putExtras(bundle);
				startActivityForResult(intent, 110);

			}
			break;
		case R.id.ktv_wechat_layout:
			wechatPay.setImageDrawable(getResources().getDrawable(
					R.drawable.my_shopping_cart_choice_icon));
			alipayPay.setImageDrawable(getResources().getDrawable(
					R.drawable.my_shopping_cart_not_selected_icon));
			payway = false;
			break;
		case R.id.ktv_alipay_layout:
			alipayPay.setImageDrawable(getResources().getDrawable(
					R.drawable.my_shopping_cart_choice_icon));
			wechatPay.setImageDrawable(getResources().getDrawable(
					R.drawable.my_shopping_cart_not_selected_icon));
			payway = true;
			break;
		case R.id.order_pay_now:
			
			if(flag == false){
			}else{
				flag = false;
			int type = 0;
			String a=orderRoom.getText().toString();
			if (a.equals("大包")) {
				type=1;
			}else if (a.equals("中包")) {
				type=2;
			}else if (a.equals("小包")) {
				type=3;
			}
			if (i == 1 || i == 4&&Double.valueOf(money)>0) {
				if (payway == true) {// 支付宝支付
					ktvOrderModel.flowOrderDone(bonus_id, phone, consignee,
							"支付宝", date, shop_id, shop_name, goods_id, "1",
							"2", "", type);
				} else if (payway == false) {// 微信支付
					ktvOrderModel.flowOrderDone(bonus_id, phone, consignee,
							"微信", date, shop_id, shop_name, goods_id, "2",
							"2", "", type);
				}
			} else if (status == 1) {
				if (payway == true) {// 支付宝支付
					intent = new Intent(this, PayDemoActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("price", money);
					bundle.putString("name", package_name);
					bundle.putString("subject", room);
					bundle.putString("number", number);
					intent.putExtras(bundle);
					startActivity(intent);
				} else if (payway == false) {// 微信支付
					if (money != null) {
						GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
						getPrepayId.execute();
					}
				}
			}else if (i == 2 || i == 3) {
				ktvOrderModel.flowOrderDone(bonus_id, phone, consignee,
						"支付宝", date, shop_id, shop_name, goods_id, "2",
						"2", "",type );
				finish();
			}else if (Double.valueOf(money)<=0) {
				ktvOrderModel.flowOrderDone(bonus_id, phone, consignee,
						"", date, shop_id, shop_name, goods_id, "",
						"2", "",type );
			}
			}
			break;

		default:
			break;
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

			e.printStackTrace();
		}
		return "";
	}

	private class GetPrepayIdTask extends
			AsyncTask<Void, Void, Map<String, String>> {

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(KtvOrderDetailActivity.this,
					getString(R.string.app_tip),
					getString(R.string.getting_prepayid));
		}

		@Override
		protected void onPostExecute(Map<String, String> result) {
			if (dialog != null) {
				dialog.dismiss();
			}
			sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");

			resultunifiedorder = result;
			// 微信接口调起
			genPayReq();
			sendPayReq();

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

	private String genOutTradNo() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	//
	private String genProductArgs() {
		StringBuffer xml = new StringBuffer();
		double m = Float.parseFloat(money) * 100;
		int i = (int) m;
		if (status!=1) {
			number = ktvOrderModel.info.order_sn;
		}
		
		String price = String.valueOf(i);
		SharedPreferences sp = KtvOrderDetailActivity.this
				.getSharedPreferences("number", Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putString("number", number);
		edit.commit();
		try {
			String nonceStr = genNonceStr();
			xml.append("</xml>");
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams
					.add(new BasicNameValuePair("appid", Constants.APP_ID));
			packageParams.add(new BasicNameValuePair("body", package_name));
			packageParams
					.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
			packageParams.add(new BasicNameValuePair("notify_url",
					"http://121.40.35.3/test"));
			packageParams.add(new BasicNameValuePair("out_trade_no", number));
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

	private void sendPayReq() {
		msgApi.registerApp(Constants.APP_ID);
		msgApi.sendReq(req);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			if (requestCode == 110) {
				hi_coupons_state.setText(data.getStringExtra("bonusName"));
				bonus_id = Integer.parseInt(data.getStringExtra("bonusId"));
				bonusPrice=data.getStringExtra("bonusPrice");
				orderMoney.setText(sub(Double.valueOf(money),Double.valueOf(bonusPrice))+"");
				money=String.valueOf(sub(Double.valueOf(money),Double.valueOf(bonusPrice)));
			}else if(requestCode == 200){
				bonus_id = 0 ;
			}
		}
	}
	/**double类型相减*/
	 public static double sub(double v1, double v2)  
	    {  
		 double number;
	        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
	        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
	        if (b1.subtract(b2).doubleValue()>0) {
				
	        	number= b1.subtract(b2).doubleValue();  
			}else {
				number= 0; 
			}
	        return number;
	    }  
	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if (url.endsWith(ProtocolConst.FLOW_TICKET_DONE)) {
			if (i == 1 || i == 4&&Double.valueOf(money)>0){
				Toast.makeText(this, "下单成功", Toast.LENGTH_SHORT).show();
			if (payway == true) {
				// 支付宝接口调用
				intent = new Intent(this, PayDemoActivity.class);
				intent.putExtra("type", "Ktv");
				Bundle bundle = new Bundle();
				bundle.putString("price", money);
				bundle.putString("name", package_name);
				bundle.putString("subject", room);
				bundle.putString("number", ktvOrderModel.info.order_sn);
				intent.putExtras(bundle);
				startActivity(intent);

			} else if (payway == false) {// 微信支付
				if (money != null) {
					GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
					getPrepayId.execute();
					
				}
			}}else if (i == 3 || i == 2) {
				Toast.makeText(this, "预定成功", Toast.LENGTH_SHORT).show();
			}else if (Double.valueOf(money)<=0) {
				Toast.makeText(this, "下单成功", Toast.LENGTH_SHORT).show();
			}
		}else if (url.endsWith(ProtocolConst.ORDER_TICKET_DETAIL)) {
			money=ktvOrderModel.ktvOrderList.get(0).money;
			package_name=ktvOrderModel.ktvOrderList.get(0).good.goods_name;
			shopName.setText(ktvOrderModel.ktvOrderList.get(0).seller_name);
			shopAddress.setText(ktvOrderModel.ktvOrderList.get(0).shop_address);
			shopPhone.setText(ktvOrderModel.ktvOrderList.get(0).shop_phone);
			orderPackage.setText(ktvOrderModel.ktvOrderList.get(0).good.goods_name);
			orderMoney.setText("￥"+ktvOrderModel.ktvOrderList.get(0).money);
			if(ktvOrderModel.ktvOrderList.get(0).bonus_name.equals("")){
				hi_coupons_state.setText("无可用");
			}else{
			hi_coupons_state.setText(ktvOrderModel.ktvOrderList.get(0).bonus_name);
			}
			shopImage.setImageWithURL(this,  ktvOrderModel.ktvOrderList.get(0).shop_pic_url, R.drawable.default_image);
		}

	}
	@Override
	protected void onPause() {
		super.onPause();
		if(flag == false){
			finish();
		}else{}
	}
}