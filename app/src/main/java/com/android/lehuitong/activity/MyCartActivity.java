package com.android.lehuitong.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.android.lehuitong.LehuitongApp;
import com.android.lehuitong.adapter.MyCartAdapter;
import com.android.lehuitong.model.GoodsListModel;
import com.android.lehuitong.model.OrderModel;
import com.android.lehuitong.model.ShoppingCartModel;
import com.android.lehuitong.protocol.GOODS_LIST;
import com.android.lehuitong.protocol.ProtocolConst;
import com.android.lehuitong.protocol.SESSION;
import com.android.lehuitong.view.AddressDialog;
import com.external.androidquery.callback.AjaxStatus;
import com.funmi.lehuitong.R;

@SuppressLint("UseSparseArrays")
public class MyCartActivity extends BaseActivity implements OnClickListener, BusinessResponse {
	private List<GOODS_LIST> list = new ArrayList<GOODS_LIST>();
	private ListView my_cart_listview;
	private MyCartAdapter adapter;
	private ImageView title_back;
	private ImageView image_select;
	private LinearLayout Settlement_button;
	private Button cart_delete_button;
	private TextView edit_button;
	private TextView finish_button;
	private TextView title_text;
	private TextView cart_price;
	private TextView shop_btn;
	private Intent intent;
	public static int flag = 0;
	private boolean select = true;
	private ShoppingCartModel shopCartModel;
	private List<GOODS_LIST> goodList = new ArrayList<GOODS_LIST>();
	private int updateCount = 0;
	private OrderModel orderModel;
	private ArrayList<Integer> recIdList;
	private Handler handler;
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	private int shoppingCartNumber = 0;
	private RelativeLayout no_net;
	private GoodsListModel goodModel;
	private ArrayList<String> catNameList = new ArrayList<String>();
	private ArrayList<String> catIdList = new ArrayList<String>();
	
	private AddressDialog dialog;
	Map<Integer, Boolean> Delete = new HashMap<Integer, Boolean>();
	private RelativeLayout null_car;
	private TextView shopping;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_cart);
		init();
		LehuitongApp.getInstance().addActivity(this); 
		handler = new Handler() {
			public void handleMessage(Message msg) {

				String message = (String) msg.obj;// obj不一定是String类，可以是别的类，看用户具体的应用
				// 根据message中的信息对主线程的UI进行改动
				// …… }
				if (message == "price") {
					Map<Integer, String> price = new HashMap<Integer, String>();
					price = adapter.getPrice();
					Log.i("aaaaaaaa", price.size()+"");
					if (!price.isEmpty()) {
						double pricenumber = 0;
						for (int i = 0; i < goodList.size(); i++) {
							if (price.get(i) != null) {
//								String number = price.get(i).substring(0, price.get(i).length() - 3);
								String number =price.get(i);
								double everyprice=Double.parseDouble(number);
								pricenumber= add(everyprice, pricenumber);
							}
						}
						cart_price.setText(String.valueOf(pricenumber));

					} else {
						String pricenumber = "0.0";
						cart_price.setText(pricenumber);
					}
				}
				if (message == "false") {
					image_select.setImageResource(R.drawable.my_hopping_cart_not_selected_icon);
					select = true;
				} else if(msg.obj == "true"){
					image_select.setImageResource(R.drawable.my_shopping_cart_choice_icon);
					select = false;
				}
			}
		};

	}
	/**小数点类型相加*/
	public static double add(double v1, double v2)  
    {  
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return b1.add(b2).doubleValue();  
    }  

	private void init() {
		goodModel = new GoodsListModel(this);
		goodModel.addResponseListener(this);
		finish_button = (TextView) findViewById(R.id.finish_button);
		shop_btn = (TextView) findViewById(R.id.shop_btn);
		image_select = (ImageView) findViewById(R.id.image_select);
		edit_button = (TextView) findViewById(R.id.edit_button);
		edit_button.setVisibility(View.VISIBLE);
		title_back = (ImageView) findViewById(R.id.title_back);
		title_text = (TextView) findViewById(R.id.title_text);
		cart_delete_button = (Button) findViewById(R.id.cart_delete_button);
		Settlement_button = (LinearLayout) findViewById(R.id.Settlement_button);
		title_text.setText("我的购物车");
		my_cart_listview = (ListView) findViewById(R.id.my_cart_listview);
		null_car=(RelativeLayout)findViewById(R.id.null_car);
		cart_price = (TextView) findViewById(R.id.cart_price);// 结算总价格
		shopping=(TextView)findViewById(R.id.shopping);
//		no_net=(RelativeLayout)findViewById(R.id.no_net);
		orderModel = new OrderModel(this);
		orderModel.addResponseListener(this);
		setOnClickListener();
		
	}

	private void setOnClickListener() {
		// TODO Auto-generated method stub
		finish_button.setOnClickListener(this);
		shop_btn.setOnClickListener(this);
		image_select.setOnClickListener(this);
		title_back.setOnClickListener(this);
		Settlement_button.setOnClickListener(this);
		edit_button.setOnClickListener(this);
		cart_delete_button.setOnClickListener(this);
		shopCartModel = new ShoppingCartModel(this);
		shopCartModel.addResponseListener(this);
		shopCartModel.cartList();
		adapter = new MyCartAdapter(this, false, false, false, handler);

	}

	@SuppressLint("UseSparseArrays")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.title_back:
			if (flag == 0) {
				// 第一次单击触发的事件
				if (adapter.isB() == true) {
					adapter = new MyCartAdapter(this, false, adapter.isSelect(), false, handler);
					action();
					adapter.notifyDataSetChanged();
					cart_delete_button.setVisibility(View.GONE);
					Settlement_button.setVisibility(View.VISIBLE);
					edit_button.setVisibility(View.VISIBLE);
					finish_button.setVisibility(View.GONE);
				} else {
					finish();
				}
				flag = 1;
			} else {
				// 第二次单击button.text改变触发的事件
				finish();

				flag = 0;
			}

			break;
		case R.id.Settlement_button:

			Map<Integer, Boolean> pay = new HashMap<Integer, Boolean>();
			pay = adapter.getDelete();
			if (!pay.isEmpty()) {
				recIdList = new ArrayList<Integer>();
				for (int i = 0; i < goodList.size(); i++) {
					if (pay.get(i) != null && pay.get(i) == true) {
						recIdList.add(goodList.get(i).goods_id);
					}
				}
				if(recIdList.size() == 0){
					Toast.makeText(this, "您还没有选择商品结算", Toast.LENGTH_SHORT).show();
				}else{
				orderModel.checkOrder(recIdList);
				}
			} else {
				Toast.makeText(this, "您还没有选择商品结算", Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.cart_delete_button:
			Delete = adapter.getDelete();
			if (!Delete.isEmpty()) {
				dialog=new  AddressDialog(this){
					@Override
					public void setCancel() {
						super.setCancel();
						dialog.mDialog.dismiss();
					}
					@Override
					public void setCommit() {
						super.setCommit();
						ArrayList<Integer> good_id = new ArrayList<Integer>();
						for (int i = 0; i < goodList.size(); i++) {
							if (Delete.get(i) != null && Delete.get(i) == true) {
								good_id.add(goodList.get(i).rec_id);
							}
						}
						shopCartModel.deleteGoods(good_id);
						dialog.mDialog.dismiss();
					}
				};
				dialog.settitle("你确定要删除该商品吗？");
				dialog.mDialog.show();
			} else {
				Toast.makeText(this, "您还没有选择需要删除的商品！", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.image_select:
			if (select) {
				adapter = new MyCartAdapter(this, adapter.isB(), true, false, handler);
				action();
				image_select.setImageResource(R.drawable.my_shopping_cart_choice_icon);
				adapter.notifyDataSetChanged();
				select = false;
			} else {
				adapter = new MyCartAdapter(this, adapter.isB(), false, false, handler);
				action();
				image_select.setImageResource(R.drawable.my_shopping_cart_not_selected_icon);
				adapter.notifyDataSetChanged();
				select = true;
			}
			break;
		case R.id.edit_button:
			MyCartAdapter.setNumberCount = 0;
			edit_button.setVisibility(View.GONE);
			finish_button.setVisibility(View.VISIBLE);
			cart_delete_button.setVisibility(View.VISIBLE);
			Settlement_button.setVisibility(View.GONE);
			adapter = new MyCartAdapter(this, true, adapter.isSelect(), false, handler);
			action();
			adapter.notifyDataSetChanged();
			flag = 0;
			break;
		case R.id.finish_button:
			Map<Integer, Integer> number = new HashMap<Integer, Integer>();
			number = adapter.getAddNumberMap();
			finish_button.setVisibility(View.GONE);
			cart_delete_button.setVisibility(View.GONE);
			Settlement_button.setVisibility(View.VISIBLE);
			edit_button.setVisibility(View.VISIBLE);
			ArrayList<Integer> number_change = new ArrayList<Integer>();
			ArrayList<Integer> good_id = new ArrayList<Integer>();
			for (int i = 0; i < goodList.size(); i++) {
				if (number.get(i) != null) {
					int itemNumber = number.get(i);
					number_change.add(itemNumber);
					good_id.add(goodList.get(i).rec_id);

				}

			}
			if (!number_change.isEmpty() && !good_id.isEmpty()) {

				shopCartModel.updateGoods(good_id, number_change);
			} else {
				adapter = new MyCartAdapter(this, false, adapter.isSelect(), false, handler);
				action();
				adapter.notifyDataSetChanged();
			}
			break;
			case R.id.shop_btn:
				goodModel.getGoodCatList("999");
		default:
			break;
		}
	}
	public void action() {
		adapter.bindData(shopCartModel.goods_list);
		my_cart_listview.setAdapter(adapter);
	}
	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
		if (url.endsWith(ProtocolConst.CART_LIST)) {
			goodList = shopCartModel.goods_list;
			if(goodList.size()<1){
				my_cart_listview.setVisibility(View.GONE);
				null_car.setVisibility(View.VISIBLE);
				null_car.setOnClickListener(this);
				cart_delete_button.setClickable(false);
				cart_delete_button.setBackgroundResource(R.drawable.rounded_unclick_button);
				String pricenumber = "0.0";
				cart_price.setText(pricenumber);
			}else{
				cart_delete_button.setClickable(true);
				cart_delete_button.setBackgroundResource(R.drawable.rounded_button);
			adapter = new MyCartAdapter(this, false, adapter.isSelect(), false, handler);
			adapter.bindData(shopCartModel.goods_list);
			my_cart_listview.setAdapter(adapter);
			String price = adapter.getTotalprice();
			cart_price.setText(price);
			adapter.notifyDataSetChanged();
			}
			
			
		} else if (url.endsWith(ProtocolConst.CART_UPDATE)) {
			shopCartModel.cartList();
		} else if (url.endsWith(ProtocolConst.CART_DELETE)) {
//			finish_button.setVisibility(View.GONE);
//			edit_button.setVisibility(View.VISIBLE);
			shopCartModel.cartList();
			Delete.clear();
			Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
		} else if (url.endsWith(ProtocolConst.FLOW_CHECKORDER)) {
			intent = new Intent(MyCartActivity.this, TallyOrderActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("order", orderModel.balance_goods_list);
			bundle.putSerializable("address", orderModel.address);
			Log.i("orderModel.address", ""+orderModel.address);
			bundle.putSerializable("total", orderModel.select_SHOPPING);
			bundle.putSerializable("shipping", orderModel.shipping_list);
			bundle.putSerializable("bonus", orderModel.bonus_list);
			bundle.putInt("statu", orderModel.allow_use_bonus);
			bundle.putInt("shipping_id", orderModel.shipping_id);//收货方式
			intent.putIntegerArrayListExtra("goods_ids", recIdList);
			intent.putExtra("total_price", cart_price.getText());
			intent.putExtras(bundle);
			startActivity(intent);
		}
		else if (url.endsWith(ProtocolConst.CATEGORY_LIST)) {
			for (int i = 0; i < goodModel.categoryList.size(); i++) {
				catNameList.add(goodModel.categoryList.get(i).name);
				catIdList.add(goodModel.categoryList.get(i).id+"");
			}
			intent = new Intent(this, MyPurchaseActivity.class);
			intent.putStringArrayListExtra("catNameList", catNameList);
			intent.putStringArrayListExtra("catIdList", catIdList);
			for (int i = 0; i < LehuitongApp.activitys.size(); i++) {
				LehuitongApp.activitys.get(i);
				if (LehuitongApp.activitys.get(i)==MyPurchaseActivity.myActivity) {
					LehuitongApp.activitys.get(i).finish();
				}
			}
			startActivity(intent);
			finish();
		}

	}
	@Override
	protected void onResume() {
		super.onResume();
        SESSION session = SESSION.getInstance();
		
		if (session.sid!=null&&session.uid!=null) {
			
			shopCartModel.cartList();
	
	}}
@Override
protected void onStop() {
	super.onStop();
try{
	recIdList.clear();
	}catch(Exception e){
		e.printStackTrace();
	}
}
}
