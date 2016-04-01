package com.android.lehuitong.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Matrix.ScaleToFit;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.activity.MainActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.WebImageView;
import com.android.lehuitong.LehuitongApp;
import com.android.lehuitong.model.GoodDetailModel;
import com.android.lehuitong.model.ShoppingCartModel;
import com.android.lehuitong.protocol.GOODS;
import com.android.lehuitong.protocol.ProtocolConst;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;
import com.funmi.lehuitong.R.id;
import com.funmi.lehuitong.WebActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyPurchaseDetailActivity extends BaseActivity implements OnClickListener, OnPageChangeListener, BusinessResponse {
	private TextView title_text;
	private ImageView title_search;
	private LinearLayout layout;
	private LinearLayout promote_layout;
	private Button btn;
	private ImageView title_shop;
	private ImageView title_shop_gone;
	private ImageView title_shop_gone_ivon;
	private ImageView title_back;
	private TextView start_time;
	private TextView week;
	private TextView item_fragment_shop_list_textview6;
	private TextView item_fragment_shop_list_textview5;
	private Button btn_add_cart;
	private String time = null;
	private String monthStart = null;
	private String monthEnd = null;
	private String dateStart = null;
	private String dateEnd = null;
	private ShoppingCartModel shopCartModel;
	private int shoppingCartNumber = 0;

	private RelativeLayout goods_intro;
	private TextView goods_name;
	private TextView goods_promote_price;
	private TextView goods_formated_promote_price;
	private TextView goods_number;
	private ViewGroup group;
	private ImageView haitaopin_image;
	private TextView shop_address;
	private TextView shop_name;
	private TextView shop_phone;
	private DisplayImageOptions option;
	private ImageLoader imageLoader;
	//
	private GoodDetailModel detailModel;
	private ShoppingCartModel cartModel;

	private SharedPreferences shared;
	private int cartNumber = 0;
	private TextView cartNum;

	// add
	private ViewPager viewPaper;
	private ImageView[] tips;
	private List<ImageView> goodImgsList = new ArrayList<ImageView>();
	private int[] imgIdArray;

	private int good_Id;

	// countdownTimer
	private MyCount mc;
	private TextView tv;

	private static final int second = 60;
	private static final int min = 60 * 60;

	private long first = 0, twice = 0, third = 0,four = 0;
	private long mtmp = 0;
	private long mtmp2 = 0;
	private long mtmp3 = 0;
	private TextView haitaopin;
	private ImageView haitaoImage;
	private LinearLayout xinyu;
	private GOODS good;
	int i=0;
	private final int AUTO_MSG = 1;
	Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case AUTO_MSG:
				int current = viewPaper.getCurrentItem();
				if(current ==goodImgsList.size()-1){
					current = -1;
				}
				viewPaper.setCurrentItem(current+1);
				try{
				handler.sendEmptyMessageDelayed(AUTO_MSG, 5000);
				}catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		};
	};
	private String isPromotePrice;
	private long times;
	private long currentTime;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purchasedetail);
		init();
		LehuitongApp.getInstance().addActivity(this); 
	}

	private void init() {

		// countdowntime控件
		tv = (TextView) findViewById(R.id.countdownTimer);
		// mc = new MyCount(7000000, 1000);
		// mc.start();

		//
		Intent intent = getIntent();
		good_Id = intent.getExtras().getInt("id");
		String str = String.valueOf(good_Id);
		isPromotePrice=intent.getStringExtra("isPromotePrice");
		
		
		detailModel = new GoodDetailModel(this);
		detailModel.addResponseListener(this);
		detailModel.fetchGoodDetail(good_Id + "");
		//
		cartModel = new ShoppingCartModel(this);
		cartModel.addResponseListener(this);
		//
		shopCartModel = new ShoppingCartModel(this);
		shopCartModel.addResponseListener(this);
		shop_name = (TextView) findViewById(R.id.shop_name);
		shop_address = (TextView) findViewById(R.id.shop_address);
		shop_phone = (TextView) findViewById(R.id.shop_phone);
		xinyu=(LinearLayout) findViewById(R.id.xinyu_activity);
		haitaopin_image=(ImageView) findViewById(R.id.haitaopin_image);
		item_fragment_shop_list_textview5 = (TextView) findViewById(R.id.item_fragment_shop_list_textview5);
		goods_intro = (RelativeLayout) findViewById(R.id.goods_intro);
		week = (TextView) findViewById(R.id.week);
		// haitaoImage=(ImageView)findViewById(R.id.haitao);
		start_time = (TextView) findViewById(R.id.start_time);
		goods_name = (TextView) findViewById(R.id.item_fragment_shop_list_textview1);
//		haitaopin = (TextView) findViewById(R.id.item_fragment_shop_list_textview2);
		goods_promote_price = (TextView) findViewById(R.id.item_fragment_shop_list_textview3);
		goods_formated_promote_price = (TextView) findViewById(R.id.item_fragment_shop_list_textview6);
		// goods_number = (TextView)
		// findViewById(R.id.item_fragment_shop_list_textview5);
		option = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.default_image).showImageOnFail(R.drawable.default_image).resetViewBeforeLoading(false).delayBeforeLoading(300)
				.cacheOnDisc(true).cacheInMemory(true).bitmapConfig(Bitmap.Config.RGB_565).build();
		imageLoader = ImageLoader.getInstance();
		group = (ViewGroup) findViewById(R.id.viewGroup);
		viewPaper = (ViewPager) findViewById(R.id.item_fragment_shop_list_imageview1);
		//
		promote_layout=(LinearLayout)findViewById(R.id.promote_layout );
		title_search = (ImageView) findViewById(R.id.title_search);
		title_back = (ImageView) findViewById(R.id.title_back);
		title_text = (TextView) findViewById(R.id.title_text);
		title_shop = (ImageView) findViewById(R.id.title_search);
		title_shop_gone = (ImageView) findViewById(R.id.title_search_gone);
		title_shop_gone_ivon = (ImageView) findViewById(R.id.title_search_gone_ivon);
		btn = (Button) findViewById(R.id.btn_buy);
		layout = (LinearLayout) findViewById(R.id.linearlayout);
		// layout.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG
		// |Paint.ANTI_ALIAS_FLAG);
		item_fragment_shop_list_textview6 = (TextView) findViewById(R.id.item_fragment_shop_list_textview6);

//		btn_add_cart = (Button) findViewById(R.id.add_cart);

		cartNum = (TextView) findViewById(R.id.shoppingcart_num);

		shared = getSharedPreferences("shoppingCartNum", MODE_PRIVATE);
		cartNumber = shared.getInt("num", 0);
		if (cartNumber == 0) {
			cartNum.setVisibility(View.GONE);

		} else {

			cartNum.setText(cartNumber + "");
		}

		setOnClickListener();

	}

	public void setPic() {

		tips = new ImageView[goodImgsList.size()];
		for (int i = 0; i < tips.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(10, 10));
			tips[i] = imageView;
			if (i == 0) {
				tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}

			group.addView(imageView);
		}

		viewPaper.setAdapter(new MyAdapter());
		viewPaper.setOnPageChangeListener(this);
		viewPaper.setCurrentItem(0);
		handler.sendEmptyMessageDelayed(AUTO_MSG, 5000);
	}

	class MyCount extends CountDownTimer {

		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}
		@Override
		public void onTick(long millisUntilFinished) {
			/**
			 * first:秒数
			 * second：60（判断）
			 * twice：分钟数
			 */
			
			long ss = millisUntilFinished / 1000;
			//多少天
			int day = (int) (ss/(3600*24));
			//剩余多少小时
			long rest_hour=ss%(3600*24);
			//小时候面剩余的分
			int rest_min  = (int) (rest_hour%3600);
			//多少小时
			int hour= (int) (rest_hour/3600);
			//分剩余多少秒
			int  rest_ss= rest_min%60;
			//多少分
			int min = rest_min/60;
			if(day == 0){
				tv.setText(hour+"小时"+min+"分"+rest_ss+"秒");
				if(hour == 0){
					tv.setText(min+"分"+rest_ss+"秒");
					if(min == 0){
						tv.setText(rest_ss+"秒");
					}
				}
			}else{
				
			tv.setText(day+"天"+hour+"小时"+min+"分"+rest_ss+"秒");
			}
		}
		@Override
		public void onFinish() {
			tv.setText("finish");
			promote_layout.setVisibility(View.GONE);
			goods_promote_price.setText(good.shop_price);
		}
	}

	public class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {

			return goodImgsList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(goodImgsList.get(position));

		}

		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(goodImgsList.get(position));
			return goodImgsList.get(position);
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
//		if (arg0 ==1) {
//			handler.removeMessages(AUTO_MSG);
//		}else if(arg0 ==2) {
//			handler.sendEmptyMessageDelayed(AUTO_MSG, 5000);
//		}
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int arg0) {
		setImageBackground(arg0 % goodImgsList.size());
	}

	/**
	 * 
	 * 
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems) {
		for (int i = 0; i < tips.length; i++) {
			if (i == selectItems) {
				tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
		}
	}

	private void setOnClickListener() {

		title_text.setText("商品详情");
		item_fragment_shop_list_textview6.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		title_back.setOnClickListener(this);
		btn.setOnClickListener(this);
		title_search.setOnClickListener(this);
		goods_intro.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.title_search:
			intent = new Intent(MyPurchaseDetailActivity.this, MyCartActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_buy:
			title_shop.setVisibility(View.VISIBLE);
			cartModel.Cartcrate(good_Id, 1);
			title_shop_gone.setVisibility(View.INVISIBLE);
			title_shop_gone_ivon.setVisibility(View.INVISIBLE);
			break;
		case R.id.title_back:
			finish();
			break;
		case R.id.title_search_gone:
			intent = new Intent(MyPurchaseDetailActivity.this, MyCartActivity.class);
			startActivity(intent);
			break;
		case R.id.goods_intro:
			intent=new Intent(MyPurchaseDetailActivity.this,WebActivity.class);
			intent.putExtra("shop_info", good.goods_desc);				
			startActivity(intent);
			break;
		}

	}

	/** 获得预售时间 */
	public String gettime(String time) {
		GOODS good = detailModel.goodDetail;
		String[] date = time.split("/");
		String year = null;
		String month = null;
		String mdate = null;
		for (int i = 0; i < date.length; i++) {
			year = date[0];
			month = date[1];
			mdate = date[2];
		}

		monthStart = month.substring(1, 2);
		dateStart = mdate.substring(0, 2);
		time = year + "/" + month + "/" + dateStart;
		
		String times = monthStart + "月" + dateStart + "日";
		return times;
	}

	/** 预售结束时间 */
	public String gettimeend() {
		GOODS good = detailModel.goodDetail;
		String[] date = good.promote_end_date.split("/");
		String year = null;
		String month = null;
		String mdate = null;
		for (int i = 0; i < date.length; i++) {
			year = date[0];
			month = date[1];
			mdate = date[2];
		}

		monthEnd = month.substring(1, 2);
		dateEnd = mdate.substring(0, 2);
		time = year + "/" + month + "/" + dateEnd;
		
		String times = monthEnd + "月" + dateEnd + "日";
		return times;
	}

	/** 根据日期计算星期 */
	public static int dayForWeek(String pTime) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(pTime));
		
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		
		return dayForWeek;
	}
	
	public String getWeek(){
		int weeks = 0;
		String week = null;
		try {
			weeks = dayForWeek(time);
			if (weeks == 7) {
				week="星期天";
			} else if (weeks == 1) {
				week="星期一";
			} else if (weeks == 2) {
				week="星期二";
			} else if (weeks == 3) {
				week="星期三";
			} else if (weeks == 4) {
				week="星期四";
			} else if (weeks == 5) {
				week="星期五";
			} else if (weeks == 6) {
				week="星期六";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return week;
		
	}

	
	
	@SuppressWarnings("unused")
	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
		if (url.endsWith(ProtocolConst.GOODS)) {
			int nowmonth;
			good = detailModel.goodDetail;
			goods_name.setText(good.goods_name);
			if (good.is_promote.equals("1")) {
				
				goods_promote_price.setText(good.promote_price);
				
			} else {
				goods_promote_price.setText(good.shop_price);
				
			}if(good.is_haitao!=null&&good.is_haitao.equals("0")){
				haitaopin_image.setVisibility(View.GONE);
				xinyu.setVisibility(View.GONE);
			}else{
				haitaopin_image.setVisibility(View.VISIBLE);
				xinyu.setVisibility(View.VISIBLE);
			}
			goods_formated_promote_price.setText(good.market_price);
			item_fragment_shop_list_textview5.setText(good.sales_volume);
			if (good.pictures.size() > 0) {
				for (int i = 0; i < good.pictures.size(); i++) {

					ImageView shopImg = new ImageView(this);
					imageLoader.displayImage(good.pictures.get(i).thumb, shopImg, option);
					shopImg.setScaleType(ScaleType.FIT_XY);
					goodImgsList.add(shopImg);
				}
				setPic();
			}
			// 商家信息
			shop_phone.setText(good.shop_seller.shop_phone);
			shop_address.setText(good.shop_seller.shop_address);
			shop_name.setText(good.shop_seller.shop_name);
			if (!good.promote_start_date.isEmpty()) {
				start_time.setText(gettime(good.promote_start_date)+"\t至\t"+gettime(good.promote_end_date));
			} else {
				start_time.setText("暂时没有预售！");
				week.setVisibility(View.GONE);
				promote_layout.setVisibility(View.GONE);
			}

			if (good.promote_price != null && good.promote_start_date != null && good.promote_end_date != null) {
				gettimeend();
			
				Date date=new Date();
				DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String bjtime=format.format(date);
				long promoteTime;
				try {
					promoteTime = format.parse(good.promote_end_date.substring(0, 19)).getTime();
					Log.i("aaaaaa",good.promote_end_date.substring(0, 19));
					currentTime=System.currentTimeMillis();
					int times = (int) ((new Long(promoteTime).longValue()-new Long(currentTime).longValue())/1000);
					if(times<0){
						promote_layout.setVisibility(View.GONE);
						goods_promote_price.setText(good.shop_price);
					}else{
						goods_promote_price.setText(good.promote_price);
						mc = new MyCount(times*1000l, 1000);
						mc.start();
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				
//				String month=bjtime.substring(0,2);
//				String day=bjtime.substring(3, 5);
//				String min= bjtime.substring(9, 11);
//				int nowmin=Integer.parseInt(min);
//				String miao= bjtime.substring(12, 14);
//				int nowmiao= Integer.parseInt(miao);
//				int nowday=Integer.parseInt(day);
//				nowmonth=Integer.parseInt(month);
//			int times =	(Integer.parseInt(monthEnd)-nowmonth)*30*24*3600 + (Integer.parseInt(dateEnd)-nowday)*24*3600-nowmin*60-nowmiao;
			
			
			}
			
			
		} else if (url.endsWith(ProtocolConst.CART_CREATE)) {
			Toast.makeText(this, "成功加入购物车", Toast.LENGTH_SHORT).show();
			cartNumber +=1;
			cartNum.setVisibility(View.VISIBLE);
			cartNum.setText(cartNumber+"");
		}else if (url.endsWith(ProtocolConst.CART_LIST)) {
			if (url.endsWith(ProtocolConst.CART_LIST)) {
				if (shopCartModel.goods_list.size() > 0) {
					shoppingCartNumber = 0;
					for (int i = 0; i < shopCartModel.goods_list.size(); i++) {
						shoppingCartNumber += Integer.valueOf(
								shopCartModel.goods_list.get(i).goods_number)
								.intValue();
					}	
					cartNum.setVisibility(View.VISIBLE);
					cartNum.setText(shoppingCartNumber + "");
				} else {
					cartNum.setVisibility(View.GONE);

				}
			}
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		shopCartModel.cartList();
	}
//	@Override
//	protected void onStop() {
//		super.onStop();
//		if (mc!=null) {
//			mc.cancel();
//		}
//	}
//	@Override
//	protected void onPause() {
//		super.onPause();
//		mc.start();
//	}
}