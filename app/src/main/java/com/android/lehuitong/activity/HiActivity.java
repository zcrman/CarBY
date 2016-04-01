package com.android.lehuitong.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.LehuitongApp;
import com.android.lehuitong.adapter.KtvPackageChooseAdapter;
import com.android.lehuitong.model.GoodsListModel;
import com.android.lehuitong.model.KtvAndCouponsOrderModel;
import com.android.lehuitong.model.SellerModel;
import com.android.lehuitong.protocol.FILTER;
import com.android.lehuitong.protocol.GOODS;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SHOP;
import com.android.lehuitong.view.KtvPhoneDialog;
import com.android.lehuitong.view.KtvRoomChooseDialog;
import com.android.lehuitong.view.TimePickerDialog;
import com.android.lehuitong.view.TimePickerDialog.PickerDialogButtonListener;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;

/**
 * 嗨在乐汇页面
 * 
 * @author shenlw
 * 
 */
public class HiActivity extends BaseActivity implements OnClickListener, BusinessResponse {

	private TextView topTitle;
	private ImageView topBack;
	private TextView ktvLocation;
	private TextView ktvDate;
	private TextView ktvRoom;
	private TextView ktvMobile;
	private TextView on_off_color;

	private RelativeLayout locationLayout;
	private RelativeLayout dateLayout;
	private RelativeLayout roomLayout;
	private RelativeLayout mobileLayout;
	private LinearLayout order_details_layout;
	@SuppressWarnings("deprecation")
	private Gallery ktvPackageChoose;
	private Button nextBtn;
	private ImageView btn_switch;

	private KtvPhoneDialog ktvPhoneDialog;
	private KtvRoomChooseDialog ktvRoomChooseDialog;

	private KtvPackageChooseAdapter ktvPackageChooseAdapter;

	private GoodsListModel goodsListModel;

	private String shopName;
	private String shopId = "";
	private String shopAddress;
	private String shopPhone;
	private String shopDistance;
	private int selectedPackageId = -1;
	private KtvAndCouponsOrderModel ktvOrderModel;
	private List<GOODS> goodsList = new ArrayList<GOODS>();
	private String goods_id = "";
	private String telRegex = "[1][358]\\d{9}";
	private TextView distance;
	boolean on_off = true;
	private int order_bag = 1;
	private SellerModel sellerModel;
	private String goodsId;
	private boolean isSelected = false;
	private List<GOODS> goods = new ArrayList<GOODS>();
	private SharedPreferences shared;
	private TimePickerDialog timePickerDialog;
	private Date chose;
	private Date current;
	private TextView Address;
	private String type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hi);
		initView();
		LehuitongApp.getInstance().addActivity(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@SuppressWarnings("unchecked")
	private void initView() {
		
		on_off_color = (TextView) findViewById(R.id.on_off_color);
		topTitle = (TextView) findViewById(R.id.title_text);
		topBack = (ImageView) findViewById(R.id.title_back);
		
		topBack.setVisibility(View.VISIBLE);
		btn_switch = (ImageView) findViewById(R.id.btn_switch);
		ktvLocation = (TextView) findViewById(R.id.ktv_location_text);
		ktvDate = (TextView) findViewById(R.id.ktv_calendar_text);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		ktvDate.setText(sdf.format(new Date()));
		ktvRoom = (TextView) findViewById(R.id.ktv_room_text);
		ktvMobile = (TextView) findViewById(R.id.ktv_mobile_text);
		Address=(TextView)findViewById(R.id.shop_address);
		order_details_layout = (LinearLayout) findViewById(R.id.order_details_layout);
		locationLayout = (RelativeLayout) findViewById(R.id.ktv_location_layout);
		dateLayout = (RelativeLayout) findViewById(R.id.ktv_calendar_layout);
		roomLayout = (RelativeLayout) findViewById(R.id.ktv_room_layout);
		mobileLayout = (RelativeLayout) findViewById(R.id.ktv_mobile_layout);
//		distance=(TextView) findViewById(R.id.distance);
		ktvPackageChoose = (Gallery) findViewById(R.id.ktv_package_choose);
		nextBtn = (Button) findViewById(R.id.ktv_next_btn);
		
		Intent intent=getIntent();
		type = intent.getStringExtra("type");
		if(type.equals("ktv")){
			on_off_color.setTextColor(this.getResources().getColor(R.color.color_off));
			order_details_layout.setVisibility(View.GONE);
			btn_switch.setBackgroundResource(R.drawable.butto_off);
			on_off = false;
			order_bag = 0;// 不订包
			goodsId = getIntent().getStringExtra("goods_id");
		}
		shopId = intent.getStringExtra("shop_id");
		FILTER filter = new FILTER();
		filter.seller_id = shopId;
		filter.category_id = "998";
		goodsListModel = new GoodsListModel(this);
		goodsListModel.addResponseListener(this);
		goodsListModel.fetchPreSearch(filter, "","");
		ktvRoomChooseDialog = new KtvRoomChooseDialog(this, R.style.customer_dialog) {
			@Override
			public void chooseRoom(String roomName) {
				super.chooseRoom(roomName);
				ktvRoom.setText(roomName);
				ktvRoomChooseDialog.dismiss();
			}
		};
		ktvPackageChooseAdapter = new KtvPackageChooseAdapter(this);

		goodsListModel = new GoodsListModel(this);
		goodsListModel.addResponseListener(this);

		ktvOrderModel = new KtvAndCouponsOrderModel(this);
		ktvOrderModel.addResponseListener(this);

		sellerModel = new SellerModel(this);
		sellerModel.addResponseListener(this);
		sellerModel.getSellerList(2+"", "",shopName,0,0);
		
		setOnClickListener();


		shared = getSharedPreferences("user", MODE_PRIVATE);
		ktvMobile.setText(shared.getString("name", ""));
		
//		ktvPackageChooseAdapter.bindData(goods,null);
//		ktvPackageChoose.setAdapter(ktvPackageChooseAdapter);
	}

	private void setOnClickListener() {
		btn_switch.setOnClickListener(this);
		topBack.setOnClickListener(this);
		locationLayout.setOnClickListener(this);
		dateLayout.setOnClickListener(this);
		roomLayout.setOnClickListener(this);
		mobileLayout.setOnClickListener(this);
		nextBtn.setOnClickListener(this);
		ktvPackageChoose.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				if (selectedPackageId == position) {
					goods_id = "";
					ktvPackageChooseAdapter.setDoubleSelectedItem(position);
					selectedPackageId = -1;
				} else {
					ktvPackageChooseAdapter.setSelectedItem(position);
					goods_id = goodsListModel.goodsList.get(position).goods_id;
					selectedPackageId = position;

				}
			}
		});

	}

	@Override
	public void onClick(View v) {
		Intent intent1;
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.btn_switch:
			if (on_off) {
				on_off_color.setTextColor(this.getResources().getColor(R.color.color_off));
				order_details_layout.setVisibility(View.GONE);
				btn_switch.setBackgroundResource(R.drawable.butto_off);
				on_off = false;
				order_bag = 0;// 不订包
			} else {
				on_off_color.setTextColor(this.getResources().getColor(R.color.color_on));
				order_details_layout.setVisibility(View.VISIBLE);
				btn_switch.setBackgroundResource(R.drawable.button_on);
				on_off = true;
				order_bag = 1;// 订包
			}

			break;
//		case R.id.ktv_location_layout:
//			intent1 = new Intent(HiActivity.this, KtvLocationChooseActivity.class);
//			startActivityForResult(intent1, 1);
//			break;
		case R.id.ktv_calendar_layout:
			timePickerDialog = new TimePickerDialog(HiActivity.this, R.style.SampleTheme_Light, new PickerDialogButtonListener() {
				@Override
				public void onClick(View v) {
					ktvDate.setText(timePickerDialog.getTime());
					timePickerDialog.dismiss();
				}
			},1);
			timePickerDialog.show();
			//是一个String类型的数
			Log.i("---------", timePickerDialog.getTime());
			break;
		case R.id.ktv_room_layout:
			ktvRoomChooseDialog.show();
			break;
		case R.id.ktv_mobile_layout:
			ktvPhoneDialog = new KtvPhoneDialog(this, R.style.customer_dialog, "预留手机号", "请输入11位手机号码", InputType.TYPE_CLASS_NUMBER, 11) {
				@Override
				public void commitPhone(String phoneStr) {
					super.commitPhone(phoneStr);
					if (phoneStr.length() == 11 && phoneStr.matches(telRegex)) {
						ktvMobile.setText(phoneStr);
						ktvPhoneDialog.dismiss();
					} else {
						Toast.makeText(HiActivity.this, "请输入11位有效手机号", Toast.LENGTH_SHORT).show();
					}
				}
			};
			ktvPhoneDialog.show();
			break;
		case R.id.ktv_next_btn:
//			if(type.equals("ktv")){
//				goods_id = goodsId;
//				}
			if (!on_off && goods_id.equals("")) {
				Toast.makeText(this, "请选择套餐", Toast.LENGTH_SHORT).show();
			} else {
								long time=90;
								if(getCalaner()<0){
									Toast.makeText(getApplication(), "预定时间错误", 0).show();
								}else if(getCalaner()>time){
									Toast.makeText(getApplication(), "预定时间不能超过90天", 0).show();
								}else{
				ktvOrderModel.checkOrder(goods_id, shopId,2);
								}

			}
			break;

		default:
			break;
		}

	}

	@SuppressLint("UseValueOf")
	private int getCalaner() {
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		try {
		if(timePickerDialog.getTime()!=null){
				chose=format.parse(timePickerDialog.getTime());
		}
		current=format.parse(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH));
			} catch (Exception e) {
				e.printStackTrace();
				try {
					current=format.parse(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH));
					chose=format.parse(format.format(new Date()));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		long choiceTime = chose.getTime();
		long currentTime=current.getTime();

		return (int) ((new Long(choiceTime).longValue()-new Long(currentTime).longValue())/(1000L*60*60*24));

	}



	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
		if(url.endsWith(ProtocolConst.SELLER_INFO)){
			SHOP shop = sellerModel.shopDetail;
			ktvLocation.setText(shop.shop_name);
			topTitle.setText(shop.shop_name);
			Address.setText(shop.shop_address);
		}else if (url.endsWith(ProtocolConst.SEARCH)) {
			Log.i("goodsListModel.goodsList", ""+goodsListModel.goodsList.size());
			ktvPackageChooseAdapter.bindData(goodsListModel.goodsList,goodsId,1);
			ktvPackageChoose.setAdapter(ktvPackageChooseAdapter);
			if(type.equals("ktv")) {
			int pos = 0;
			for (int i = 0; i < goodsListModel.goodsList.size(); i++) {
				if (goodsId.equals(goodsListModel.goodsList.get(i).goods_id)) {
					pos = i;
					break;
				}
			}
			if (selectedPackageId == pos) {
				goods_id = "";
				ktvPackageChooseAdapter.setDoubleSelectedItem(pos);
				selectedPackageId = -1;
			} else {
				ktvPackageChooseAdapter.setSelectedItem(0);
				goodsListModel.goodsList.add(0, goodsListModel.goodsList.get(pos));
				goodsListModel.goodsList.remove(pos+1);
				goods_id = goodsListModel.goodsList.get(0).goods_id;
				selectedPackageId = pos;
				

			}
			}
			
		} else if (url.endsWith(ProtocolConst.FLOW_TICKET_CHECKORDER)) {
//			Address.setText(sellerModel.shopList);
			Intent intent = new Intent(HiActivity.this, KtvOrderDetailActivity.class);
			if (order_bag == 0 && !goods_id.equals("")) {
				intent.putExtra("i", 4);// 订套餐不订包
			} else if (order_bag == 0 && goods_id.equals("")) {
				intent.putExtra("i", 3);// 不订包不订套餐
			} else if (order_bag == 1 && goods_id.equals("")) {
				intent.putExtra("i", 2);// 订包不订套餐
			} else if (order_bag == 1 && !goods_id.equals("")) {
				intent.putExtra("i", 1);// 订包订套餐
			}
			intent.putExtra("date", ktvDate.getText().toString());
			intent.putExtra("phone", ktvMobile.getText().toString());
			intent.putExtra("room", ktvRoom.getText().toString());
			Bundle bundle = new Bundle();
			bundle.putSerializable("order", ktvOrderModel.ktv_ORDER);
			intent.putExtras(bundle);
			startActivity(intent);

		} else if (url.endsWith(ProtocolConst.SELLER_LIST)) {
				
				
				FILTER filter = new FILTER();
				filter.category_id = "998";
				filter.seller_id = shopId;
				goodsListModel.fetchPreSearch(filter, "","");
				
				sellerModel = new SellerModel(this);
				sellerModel.addResponseListener(this);
				sellerModel.getSellerInfo(shopId);
		}
	}
}