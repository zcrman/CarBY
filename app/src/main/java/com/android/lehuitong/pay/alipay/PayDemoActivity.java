package com.android.lehuitong.pay.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.lehuitong.activity.BuyCouponActivity;
import com.android.lehuitong.activity.KtvOrderDetailActivity;
import com.android.lehuitong.LehuitongApp;
import com.android.lehuitong.activity.LehuitongMainActivity;
import com.android.lehuitong.model.PayModel;
import com.funmi.lehuitong.R;

public class PayDemoActivity extends FragmentActivity {

	// 商户PID
	public static final String PARTNER = "2088911590636316";
	// 商户收款账号
	public static final String SELLER = "tibetlehuy@163.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAL3oVNg8tLpxjvhqMvZj6LB+iRMrvZ1FbKFk3tgHPTg7RCjT0iO/vs2qey2ZBbZL23Ueeugiv7xGIgWslxWY65UYP3NPMMMFY6aTY+csieaqInGez51v2/yI26RXcbgw/WkThFnHJwQGLPSxaj38kW38FrjRZE9skapOORoyrPJ1AgMBAAECgYBzt6QrdyIxEY0qtZuEI3DgSto/wkFyGaGN3qcjP5YAM6zeq+O6QNaE/8K7ECk44ZRCE2aVGMd4c8u7ZWVSlbHGRAaGCvdxNohPRHz7591lzp8+HKk6Xm58XnwhNF3BzvhWsvI/ZyVyMke6KK2VsDHfxe4PGTf0ZM8saMrvqW3dwQJBAPFgb9G6W60QPEnGvKuMhelWVI+KJKWlk4nh1HCoAghoXgHNgYJb0WfE7Ocv5QswG6cdquUCfkLeS1gGoLpjbvkCQQDJaaX50XVYi/8IkhK9pq7y/4waswJZqbft/A7MhifgpEoqq8PcgEMloa8xc+Ls8mSnlKU0biQCH+GimDhjKTJdAkEAietnmTfZwEaBnEoxbfhKX+yTPr1ZeUjlx6hBIent124DIRaSxLDAM4HjN6o3PrBLbK7YAijtijnIZVDMMBQ2yQJBAKHSp07bMkvtVv/c1P/ZHzEPjtKYxqehi6zC2mUK2JlNhmVYUeokh06B5E94S3eSnLpj3IQOhhHK6a+IW62YGOUCQQCny288RWLdTc7cT8ceAkL9PWp7bvfyysOqXimFZCXvNeqk/lr+XTi+L1u70KfqJD4zrjO0TdDqF8GU6v9ldBH9";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC96FTYPLS6cY74ajL2Y+iwfokTK72dRWyhZN7YBz04O0Qo09Ijv77NqnstmQW2S9t1HnroIr+8RiIFrJcVmOuVGD9zTzDDBWOmk2PnLInmqiJxns+db9v8iNukV3G4MP1pE4RZxycEBiz0sWo9/JFt/Ba40WRPbJGqTjkaMqzydQIDAQAB";

	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;

	private String number;
	// 布局定义
	private TextView product_subject;
	private TextView product_price;
	private TextView subject;
	private TextView product_number;
	 
	private PayModel model;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(PayDemoActivity.this, "支付成功",
							Toast.LENGTH_SHORT).show();
					model.paydone(number);
					Intent intent = new Intent(PayDemoActivity.this,
							LehuitongMainActivity.class);
					startActivity(intent);
					 
					
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PayDemoActivity.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(PayDemoActivity.this, "支付失败",
								Toast.LENGTH_SHORT).show();
						finish();
					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(PayDemoActivity.this, "检查结果为：" + msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};
	private String activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main);
//		 LehuitongApp.getInstance().addActivity(this);
		Intent intent = getIntent();
		activity = intent.getStringExtra("type");
		Bundle bundle = intent.getExtras();
		String subject1 = bundle.getString("name");
		String price1 = bundle.getString("price");
		String body1 = bundle.getString("subject");
		number = bundle.getString("number");
		product_subject = (TextView) findViewById(R.id.product_subject);
		product_price = (TextView) findViewById(R.id.product_price);
		subject = (TextView) findViewById(R.id.subject);
		product_number = (TextView) findViewById(R.id.product_number);
		product_subject.setText(subject1);
		product_price.setText(price1);
		subject.setText(body1);
		product_number.setText(number);
		model=new PayModel(this);
		LehuitongApp.getInstance().addActivity(PayDemoActivity.this);
	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(View v) {

		Intent intent = getIntent();
		
		Bundle bundle = intent.getExtras();
		String subject1 = bundle.getString("subject");
		String price1 = bundle.getString("price");
		String body1 = bundle.getString("name");
		 String number = bundle.getString("number");
		// 订单
		String orderInfo = getOrderInfo(subject1, body1, price1, number);

		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(PayDemoActivity.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check(View v) {
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask(PayDemoActivity.this);
				// 调用查询接口，获取查询结果
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price,
			String number) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + number + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
				+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
	if(keyCode == KeyEvent.KEYCODE_BACK){
	Toast.makeText(this, "您取消了支付", Toast.LENGTH_SHORT).show();
	}
	return super.onKeyDown(keyCode, event);
}
}
